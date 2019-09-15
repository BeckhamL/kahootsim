package com.fdmgroup.dao;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.Result;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.util.IdGenerator;

public class JpaTraineeDaoTest {
	private JpaTraineeDao dao;
	private JpaQuizDao quizDao;
	private JpaResultDao resultDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaTraineeDao();
		quizDao = new JpaQuizDao();
		resultDao = new JpaResultDao();
		dao.setQuizDao(quizDao);
		dao.setResultDao(resultDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		dao.create(t);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		dao.create(t);
		assertEquals(t, dao.findById(t.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}

	@Test
	public void testFindByEmail() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		dao.create(t);
		assertEquals(t, dao.findByEmail(t.getEmail()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByEmailWithNoMatch() throws NoSuchDatabaseEntryException {
		// an email should have no spaces
		dao.findByEmail("- -");
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		dao.create(t);
		
		String unq2 = Integer.toString(IdGenerator.generate());
		t.setEmail(unq2);
		t.setPassword("321");
		t.setFirstName("Another");
		t.setLastName("Name");
		t.setLocation("Place");
		t.setDesignation("AA");
		List<Quiz> quizzes = new ArrayList<>();
		Quiz quiz = new Quiz();
		quizzes.add(quiz);
		t.setQuizzes(quizzes);
		LocalDate newTime = LocalDate.now();
		t.setStartDate(newTime);
		dao.update(t);
		
		Trainee res = dao.findById(t.getId());
		assertEquals(unq2, res.getEmail());
		assertEquals("321", res.getPassword());
		assertEquals("Another", res.getFirstName());
		assertEquals("Name", res.getLastName());
		assertEquals("Place", res.getLocation());
		assertEquals("AA", res.getDesignation());
		assertEquals(quiz, res.getQuizzes().get(0));
		assertEquals(newTime, res.getStartDate());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		dao.create(t);
		assertTrue(dao.remove(t));
		dao.findById(t.getId());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemoveRemovesResult() throws NoSuchDatabaseEntryException {
		Result result = new Result();
		List<Result> results = new ArrayList<>();
		results.add(result);
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, results);
		dao.create(t);
		assertTrue(dao.remove(t));
		resultDao.findById(result.getId());
	}
	
	@Test
	public void testRemoveDoesNotRemoveQuizzes() throws NoSuchDatabaseEntryException {
		Quiz quiz = new Quiz();
		List<Quiz> quizzes = new ArrayList<>();
		quizzes.add(quiz);
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", quizzes, null, null, null, null, null);
		dao.create(t);
		assertNotNull(quizDao.findById(quiz.getId()).getOwner());
		assertTrue(dao.remove(t));
		assertNotNull(quizDao.findById(quiz.getId()));
		assertNull(quizDao.findById(quiz.getId()).getOwner());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee t = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		assertFalse(dao.remove(t));
	}

}
