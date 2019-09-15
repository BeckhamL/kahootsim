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
import com.fdmgroup.model.Choice;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.QuestionImage;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.util.IdGenerator;

public class JpaQuestionDaoTest {
	private JpaQuestionDao dao;
	private List<Choice> choices;
	private List<Quiz> quizzes;
	
	private JpaQuestionImageDao imageDao;
	private JpaChoiceDao choiceDao;
	private JpaQuizDao quizDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		choices = new ArrayList<>();
		quizzes = new ArrayList<>();
		
		imageDao = new JpaQuestionImageDao();
		choiceDao = new JpaChoiceDao();
		quizDao = new JpaQuizDao();
		
		dao = new JpaQuestionDao();
		dao.setChoiceDao(choiceDao);
		dao.setImageDao(imageDao);
		dao.setQuizDao(quizDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		Question q = new Question("Empty", 1, 20, quizzes, null, choices);
		dao.create(q);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		Question q = new Question("Empty", 1, 20, quizzes, null, choices);
		dao.create(q);
		assertEquals(q, dao.findById(q.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		Question q = new Question("Empty", 1, 20, quizzes, null, choices);
		dao.create(q);
		
		q.setQuestionContent("Not Empty");
		q.setQuestionIndex(3);
		q.setTimeLimit(30);
		
		QuestionImage newImage = new QuestionImage();
		String unq = Integer.toString(IdGenerator.generate());
		newImage.setFileName(unq);
		newImage.setData("test".getBytes());
		q.setImage(newImage);
		
		q.getChoices().add(new Choice());
		dao.update(q);
		
		Question res = dao.findById(q.getId());
		assertEquals("Not Empty", res.getQuestionContent());
		assertEquals(3, res.getQuestionIndex());
		assertEquals(30, res.getTimeLimit(), 0.0);
		QuestionImage resImage = res.getImage();
		assertEquals(resImage, imageDao.findById(resImage.getId()));
		Choice resChoice = res.getChoices().get(0);
		assertEquals(resChoice, choiceDao.findById(resChoice.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		Question q = new Question("Empty", 1, 20, quizzes, null, choices);
		dao.create(q);
		assertTrue(dao.remove(q));
		dao.findById(q.getId());
	}
	
	@Test
	public void testRemoveDoesNotRemoveImage() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		byte[] data = new byte[10];
		QuestionImage qi = new QuestionImage(unq, data);
		
		Question q = new Question("Empty", 1, 20, quizzes, qi, choices);
		dao.create(q);
		assertTrue(dao.remove(q));
		assertNotNull(imageDao.findById(qi.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemoveRemovesChoices() throws NoSuchDatabaseEntryException {
		Choice choice = new Choice("", false, 0, null);
		choices.add(choice);
		Question q = new Question("Empty", 1, 20, quizzes, null, choices);
		dao.create(q);
		assertTrue(dao.remove(q));
		choiceDao.findById(choice.getId());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		Question q = new Question("Empty", 1, 20, quizzes, null, choices);
		assertFalse(dao.remove(q));
	}
}
