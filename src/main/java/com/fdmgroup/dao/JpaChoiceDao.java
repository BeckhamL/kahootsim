package com.fdmgroup.dao;

import javax.persistence.EntityManager;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Choice;
import com.fdmgroup.util.JpaUtil;

public class JpaChoiceDao implements IChoiceDao {
	@Override
	public boolean create(Choice choice) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Choice.class, choice.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(choice);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Choice findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Choice res = em.find(Choice.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Choice with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Choice choice) {
		Choice res = em.find(Choice.class, choice.getId());
		if (res == null) {
			em.persist(choice);
		} else {
			em.merge(choice);
		}
	}

	@Override
	public boolean remove(Choice choice) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Choice res = em.find(Choice.class, choice.getId());
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
