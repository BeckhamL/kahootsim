package com.fdmgroup.dao;

import javax.persistence.EntityManager;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.ResultAnswer;
import com.fdmgroup.util.JpaUtil;

public class JpaResultAnswerDao implements IResultAnswerDao {
	@Override
	public boolean create(ResultAnswer answer) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(ResultAnswer.class, answer.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(answer);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public ResultAnswer findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		ResultAnswer res = em.find(ResultAnswer.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No ResultAnswer with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, ResultAnswer answer) {
		ResultAnswer res = em.find(ResultAnswer.class, answer.getId());
		if (res == null) {
			em.persist(answer);
		} else {
			em.merge(answer);
		}
	}

	@Override
	public boolean remove(ResultAnswer answer) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		ResultAnswer res = em.find(ResultAnswer.class, answer.getId());
		if (res == null) {
			em.close();
			return false;
		}
		
		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}

}
