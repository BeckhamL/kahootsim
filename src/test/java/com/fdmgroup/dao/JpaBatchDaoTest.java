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
import com.fdmgroup.model.Batch;
import com.fdmgroup.model.Stream;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.util.IdGenerator;

public class JpaBatchDaoTest {
	private JpaBatchDao dao;
	private JpaTraineeDao traineeDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaBatchDao();
		traineeDao = new JpaTraineeDao();
		dao.setTraineeDao(traineeDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		Batch b = new Batch(null, Stream.JAVA);
		dao.create(b);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		Batch b = new Batch(null, Stream.JAVA);
		dao.create(b);
		assertEquals(b, dao.findById(b.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		Batch b = new Batch(null, Stream.JAVA);
		dao.create(b);
		
		List<Trainee> trainees = new ArrayList<>();
		b.setTrainees(trainees);
		b.setStreamName(Stream.ITSM);
		dao.update(b);
		
		Batch res = dao.findById(b.getId());
		assertEquals(0, res.getTrainees().size());
		assertEquals(Stream.ITSM, res.getStreamName());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		Batch b = new Batch(null, Stream.JAVA);
		dao.create(b);
		assertTrue(dao.remove(b));
		dao.findById(b.getId());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemoveRemovesTrainees() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		Trainee trainee = new Trainee(unq, "First", "Last", "123", "loc", "d", null, null, null, null, null, null);
		List<Trainee> trainees = new ArrayList<>();
		trainees.add(trainee);
		Batch b = new Batch(trainees, Stream.JAVA);
		dao.create(b);
		assertTrue(dao.remove(b));
		traineeDao.findById(trainee.getId());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		Batch b = new Batch(null, Stream.JAVA);
		assertFalse(dao.remove(b));
	}

}
