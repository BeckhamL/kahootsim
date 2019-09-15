package com.fdmgroup.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Choice;

public class JpaChoiceDaoTest {
	private JpaChoiceDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaChoiceDao();
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		Choice c = new Choice("Empty", true, 1, null);
		dao.create(c);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		Choice c = new Choice("Empty", true, 1, null);
		dao.create(c);
		assertEquals(c, dao.findById(c.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		Choice c = new Choice("Empty", true, 1, null);
		dao.create(c);
		
		c.setChoiceContent("Full");
		c.setIsCorrect(false);
		c.setChoiceIndex(2);
		dao.update(c);
		
		Choice res = dao.findById(c.getId());
		assertEquals("Full", res.getChoiceContent());
		assertFalse(res.getIsCorrect());
		assertEquals(2, res.getChoiceIndex());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		Choice c = new Choice("Empty", true, 1, null);
		dao.create(c);
		assertTrue(dao.remove(c));
		dao.findById(c.getId());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		Choice c = new Choice("Empty", true, 1, null);
		assertFalse(dao.remove(c));
	}

}
