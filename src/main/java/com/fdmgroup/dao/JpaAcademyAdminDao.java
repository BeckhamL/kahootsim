package com.fdmgroup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.AcademyAdmin;
import com.fdmgroup.util.JpaUtil;

public class JpaAcademyAdminDao implements IAcademyAdminDao {
	@Override
	public boolean create(AcademyAdmin academyAdmin) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(AcademyAdmin.class, academyAdmin.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(academyAdmin);
		// an admin should have no associated quizzes
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public AcademyAdmin findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		AcademyAdmin res = em.find(AcademyAdmin.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No AcademyAdmin with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, AcademyAdmin academyAdmin) {
		AcademyAdmin res = em.find(AcademyAdmin.class, academyAdmin.getId());
		if (res == null) {
			em.persist(academyAdmin);
		} else {
			em.merge(academyAdmin);
		}
		// an admin should have no associated quizzes
	}

	@Override
	public boolean remove(AcademyAdmin academyAdmin) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		AcademyAdmin res = em.find(AcademyAdmin.class, academyAdmin.getId());
		if (res == null) {
			em.close();
			return false;
		}

		// an admin should have no associated quizzes

		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public AcademyAdmin findByEmail(String email) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("AcademyAdmin.findByEmail");
		query.setParameter(1, email);
		@SuppressWarnings("unchecked")
		List<AcademyAdmin> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res.get(0);
	}

}
