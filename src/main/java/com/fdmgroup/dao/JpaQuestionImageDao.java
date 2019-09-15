package com.fdmgroup.dao;

import javax.persistence.EntityManager;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.QuestionImage;
import com.fdmgroup.util.JpaUtil;

public class JpaQuestionImageDao implements IQuestionImageDao {
	@Override
	public boolean create(QuestionImage image) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(QuestionImage.class, image.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(image);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public QuestionImage findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		QuestionImage res = em.find(QuestionImage.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No QuestionImage with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, QuestionImage image) {
		QuestionImage res = em.find(QuestionImage.class, image.getId());
		if (res == null) {
			em.persist(image);
		} else {
			em.merge(image);
		}
	}

	@Override
	public boolean remove(QuestionImage image) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		QuestionImage res = em.find(QuestionImage.class, image.getId());
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
