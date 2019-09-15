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
import com.fdmgroup.model.AcademyAdmin;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.util.IdGenerator;

public class JpaAcademyAdminDaoTest {
	private JpaAcademyAdminDao dao;
	private List<Quiz> quizzes;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new JpaAcademyAdminDao();
		quizzes = new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
	}

	// NOTE: This is the only test that needs to be verified by a human
	// The id generator is used to fulfill the unique constraint
	@Test
	public void testCreate() {
		String unq = Integer.toString(IdGenerator.generate());
		AcademyAdmin a = new AcademyAdmin(unq, "Blank", "Last", "123", "Filler", "Academy Admin", quizzes, LocalDate.now());
		dao.create(a);
	}
	
	// NOTE: FindById is assumed to be working if the following 2 tests pass
	@Test
	public void testFindById() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		AcademyAdmin a = new AcademyAdmin(unq, "Blank", "Last", "123", "Filler", "Academy Admin", quizzes, LocalDate.now());
		dao.create(a);
		assertEquals(a, dao.findById(a.getId()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByIdWithNoMatch() throws NoSuchDatabaseEntryException {
		// id cannot be negative
		dao.findById(-1);
	}

	@Test
	public void testFindByEmail() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		AcademyAdmin a = new AcademyAdmin(unq, "Blank", "Last", "123", "Filler", "Academy Admin", quizzes, LocalDate.now());
		dao.create(a);
		assertEquals(a, dao.findByEmail(a.getEmail()));
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void failOnFindByEmailWithNoMatch() throws NoSuchDatabaseEntryException {
		// an email should have no spaces
		dao.findByEmail("- -");
	}
	
	@Test
	public void testUpdate() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		AcademyAdmin a = new AcademyAdmin(unq, "Blank", "Last", "123", "Filler", "Academy Admin", quizzes, LocalDate.now());
		dao.create(a);
		
		String unq2 = Integer.toString(IdGenerator.generate());
		a.setEmail(unq2);
		a.setPassword("321");
		a.setFirstName("Another");
		a.setLastName("Name");
		a.setLocation("Place");
		a.setDesignation("AA");
		// an admin has no quizzes
		LocalDate newTime = LocalDate.now();
		a.setJoinDate(newTime);
		dao.update(a);
		
		AcademyAdmin res = dao.findById(a.getId());
		assertEquals(unq2, res.getEmail());
		assertEquals("321", res.getPassword());
		assertEquals("Another", res.getFirstName());
		assertEquals("Name", res.getLastName());
		assertEquals("Place", res.getLocation());
		assertEquals("AA", res.getDesignation());
		assertEquals(newTime, res.getJoinDate());
	}
	
	@Test (expected = NoSuchDatabaseEntryException.class)
	public void testRemove() throws NoSuchDatabaseEntryException {
		String unq = Integer.toString(IdGenerator.generate());
		AcademyAdmin a = new AcademyAdmin(unq, "Blank", "Last", "123", "Filler", "Academy Admin", quizzes, LocalDate.now());
		dao.create(a);
		assertTrue(dao.remove(a));
		dao.findById(a.getId());
	}
	
	@Test
	public void failRemoveOnNoMatch() {
		String unq = Integer.toString(IdGenerator.generate());
		AcademyAdmin a = new AcademyAdmin(unq, "Blank", "Last", "123", "Filler", "Academy Admin", quizzes, LocalDate.now());
		assertFalse(dao.remove(a));
	}

}
