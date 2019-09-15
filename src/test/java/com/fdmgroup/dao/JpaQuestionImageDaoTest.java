package com.fdmgroup.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.QuestionImage;
import com.fdmgroup.util.IdGenerator;

public class JpaQuestionImageDaoTest {
	private JpaQuestionImageDao dao;
	private byte[] data;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaQuestionImageDao();
		data = new byte[10];
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		String unq = Integer.toString(IdGenerator.generate());
		QuestionImage q = new QuestionImage(unq, data);
		dao.create(q);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		QuestionImage q = new QuestionImage(unq, data);
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
		String unq = Integer.toString(IdGenerator.generate());
		QuestionImage q = new QuestionImage(unq, data);
		dao.create(q);
		
		byte[] newData = {1, 2, 3};
		q.setData(newData);
		q.setFileName(unq + "0");
		dao.update(q);
		
		QuestionImage res = dao.findById(q.getId());
		assertEquals(1, res.getData()[0]);
		assertEquals(unq + "0", q.getFileName());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		QuestionImage q = new QuestionImage(unq, data);
		dao.create(q);
		assertTrue(dao.remove(q));
		dao.findById(q.getId());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		String unq = Integer.toString(IdGenerator.generate());
		QuestionImage q = new QuestionImage(unq, data);
		assertFalse(dao.remove(q));
	}

}
