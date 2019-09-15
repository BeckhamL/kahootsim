package com.fdmgroup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Trainer;
import com.fdmgroup.util.JpaUtil;

public class JpaTrainerDao implements ITrainerDao {
	@Qualifier("quizDao")
	@Autowired
	IQuizDao quizDao;

	@Override
	public boolean create(Trainer trainer) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Trainer.class, trainer.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(trainer);

		// update all quizzes stored in the trainer
		if (trainer.getQuizzes() != null) {
			trainer.getQuizzes().forEach(quiz -> {
				quiz.setOwner(trainer);
				quizDao.noTransactionUpdate(em, quiz);
			});
		}

		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Trainer findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Trainer res = em.find(Trainer.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Trainer with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Trainer trainer) {
		Trainer res = em.find(Trainer.class, trainer.getId());
		if (res == null) {
			em.persist(trainer);
		} else {
			em.merge(trainer);
		}

		// update all quizzes stored in the trainer
		if (trainer.getQuizzes() != null) {
			trainer.getQuizzes().forEach(quiz -> {
				quiz.setOwner(trainer);
				quizDao.noTransactionUpdate(em, quiz);
			});
		}
	}

	@Override
	public boolean remove(Trainer trainer) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Trainer res = em.find(Trainer.class, trainer.getId());
		if (res == null) {
			em.close();
			return false;
		}

		// remove self from associated quizzes
		if (res.getQuizzes() != null) {
			res.getQuizzes().forEach(quiz -> {
				quiz.setOwner(null);
			});
		}

		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Trainer findByEmail(String email) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Trainer.findByEmail");
		query.setParameter(1, email);
		@SuppressWarnings("unchecked")
		List<Trainer> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res.get(0);
	}
	
	@Override
	public long numberOfTrainers() {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Trainer.countAll");
		long res = (long) query.getSingleResult();
		em.close();
		return res;
	}

	public IQuizDao getQuizDao() {
		return quizDao;
	}

	public void setQuizDao(IQuizDao quizDao) {
		this.quizDao = quizDao;
	}

}
