package com.fdmgroup.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static JpaUtil instance;
	private static EntityManagerFactory emf;
	
	private JpaUtil() {
		emf = Persistence.createEntityManagerFactory("FastDailyMocks");
	}
	
	public static JpaUtil getInstance() {
		if (instance == null)
			instance = new JpaUtil();
		return instance;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public void close() {
		emf.close();
		instance = null;
	}
}
