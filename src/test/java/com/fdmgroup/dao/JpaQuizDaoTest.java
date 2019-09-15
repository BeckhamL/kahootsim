package com.fdmgroup.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.Trainer;
import com.fdmgroup.util.IdGenerator;

public class JpaQuizDaoTest {
	private JpaQuizDao dao;
	private JpaQuestionDao questionDao;
	private JpaTrainerDao trainerDao;
	private JpaQuizDao quizDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaQuizDao();
		questionDao = new JpaQuestionDao();
		trainerDao = new JpaTrainerDao();
		quizDao = new JpaQuizDao();
		
		dao.setQuestionDao(questionDao);
		trainerDao.setQuizDao(quizDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		Quiz q = new Quiz("Title", "desc", true, Module.OTHER, null, null);
		dao.create(q);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		Quiz q = new Quiz("Title", "desc", true, Module.OTHER, null, null);
		dao.create(q);
		assertEquals(q, dao.findById(q.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}
	
	@Test
	public void testFindUserQuizzesInStream() throws NoSuchDatabaseEntryException {
		String unq1 = Integer.toString(IdGenerator.generate());
		String unq2 = Integer.toString(IdGenerator.generate());
		List<Quiz> quizzes1 = new ArrayList<>();
		List<Quiz> quizzes2 = new ArrayList<>();
		Trainer t1 = new Trainer(unq1, "First", "Last", "123", "loc", "d", quizzes1, null);
		Trainer t2 = new Trainer(unq2, "First", "Last", "123", "loc", "d", quizzes2, null);
		Quiz q1 = new Quiz("Q1", "", true, Module.JPA, null, null);
		Quiz q2 = new Quiz("Q2", "", true, Module.OTHER, null, null);
		Quiz q3 = new Quiz("Q3", "", true, Module.OTHER, null, null);
		Quiz q4 = new Quiz("Q4", "", true, Module.OTHER, null, null);
		Quiz q5 = new Quiz("Q4", "", true, Module.OTHER, null, null);
		t1.addQuiz(q1);
		t1.addQuiz(q2);
		t1.addQuiz(q3);
		t2.addQuiz(q4);
		t2.addQuiz(q5);
		trainerDao.create(t1);
		trainerDao.create(t2);
		List<Quiz> resQuizzes = dao.findUserQuizzesInModule(t1, Module.OTHER);
		
		assertEquals(2, resQuizzes.size());
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		Quiz q = new Quiz("Title", "desc", true, Module.OTHER, null, null);
		dao.create(q);

		q.setTitle("New");
		q.setDescription("Describe");
		q.setIsShareable(false);
		q.setModuleName(Module.OTHER);
		List<Question> questions = new ArrayList<>();
		q.setQuestions(questions);
		dao.update(q);
		
		Quiz res = dao.findById(q.getId());
		assertEquals("New", res.getTitle());
		assertEquals("Describe", res.getDescription());
		assertFalse(res.getIsShareable());
		assertEquals(Module.OTHER, res.getModuleName());
		assertEquals(0, res.getQuestions().size());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		Quiz q = new Quiz("Title", "desc", true, Module.OTHER, null, null);
		dao.create(q);
		assertTrue(dao.remove(q));
		dao.findById(q.getId());
	}
	
	@Test
	public void testRemoveDoesNotRemoveQuestions() throws NoSuchDatabaseEntryException {
		List<Quiz> quizzes = new ArrayList<>();
		Question question = new Question();
		question.setQuizzes(quizzes);
		List<Question> questions = new ArrayList<>();
		questions.add(question);
		
		Quiz q = new Quiz("Title", "desc", true, Module.OTHER, null, questions);
		dao.create(q);
		assertTrue(dao.remove(q));
		assertNotNull(questionDao.findById(question.getId()));
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		Quiz q = new Quiz("Title", "desc", true, Module.OTHER, null, null);
		assertFalse(dao.remove(q));
	}
	
}
