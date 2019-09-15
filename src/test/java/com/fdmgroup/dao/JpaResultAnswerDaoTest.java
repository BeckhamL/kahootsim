package com.fdmgroup.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.ResultAnswer;

public class JpaResultAnswerDaoTest {
	private JpaResultAnswerDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaResultAnswerDao();
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		ResultAnswer r = new ResultAnswer(true, 0, 0, null, 1);
		dao.create(r);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		ResultAnswer r = new ResultAnswer(true, 0, 0, null, 1);
		dao.create(r);
		assertEquals(r, dao.findById(r.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		ResultAnswer r = new ResultAnswer(true, 0, 0, null, 0);
		dao.create(r);
		
		r.setIsCorrect(false);
		r.setTimeTaken(30);
		r.setResultAnswerIndex(1);
		r.setQuestionId(1);
		dao.update(r);
		
		ResultAnswer res = dao.findById(r.getId());
		assertFalse(res.getIsCorrect());
		assertEquals(30, res.getTimeTaken(), 0.0);
		assertEquals(1, res.getResultAnswerIndex());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		ResultAnswer r = new ResultAnswer(true, 0, 0, null, 1);
		dao.create(r);
		assertTrue(dao.remove(r));
		dao.findById(r.getId());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		ResultAnswer r = new ResultAnswer(true, 0, 0, null, 1);
		assertFalse(dao.remove(r));
	}

}
