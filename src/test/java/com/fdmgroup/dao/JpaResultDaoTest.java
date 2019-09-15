package com.fdmgroup.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.Result;
import com.fdmgroup.model.ResultAnswer;

public class JpaResultDaoTest {
	private JpaResultDao dao;
	private JpaResultAnswerDao answerDao;
	private JpaQuizDao quizDao;

	private Quiz quiz;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		answerDao = new JpaResultAnswerDao();
		quizDao = new JpaQuizDao();
		dao = new JpaResultDao();
		dao.setAnswerDao(answerDao);
		quiz = new Quiz();
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		dao.create(r);
	}

	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		dao.create(r);
		assertEquals(r, dao.findById(r.getId()));
	}

	@Test(expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}

	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		r.setQuizDao(quizDao);
		dao.create(r);

		Quiz newQuiz = new Quiz("Test", "", false, null, null, null);
		quizDao.create(newQuiz);
		r.setQuizId(newQuiz.getId());
		r.setScore(10);
		ResultAnswer answer = new ResultAnswer();
		answer.setQuestionId(1);
		r.addAnswer(1, answer);
		dao.update(r);

		Result res = dao.findById(r.getId());
		assertEquals("Test", res.getQuiz().getTitle());
		assertEquals(10, res.getScore());
		assertNotNull(res.getAnswer(1));
	}

	@Test(expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		dao.create(r);
		assertTrue(dao.remove(r));
		dao.findById(r.getId());
	}

	@Test(expected = NoSuchDatabaseEntryException.class)
	public void testRemoveRemovesAnswers() throws NoSuchDatabaseEntryException {
		ResultAnswer answer = new ResultAnswer();
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		r.addAnswer(1, answer);
		dao.create(r);
		assertTrue(dao.remove(r));
		answerDao.findById(answer.getId());
	}

	@Test
	public void testRemoveDoesNotRemoveQuiz() throws NoSuchDatabaseEntryException {
		quizDao.create(quiz);
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		dao.create(r);
		assertTrue(dao.remove(r));
		assertNotNull(quizDao.findById(quiz.getId()));
	}

	@Test
	public void failRemoveOnNoMatch() {
		Result r = new Result(null, quiz.getId(), 0, new LinkedHashMap<Integer, ResultAnswer>());
		assertFalse(dao.remove(r));
	}
}
