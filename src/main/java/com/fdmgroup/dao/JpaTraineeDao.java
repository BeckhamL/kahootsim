package com.fdmgroup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.util.JpaUtil;

public class JpaTraineeDao implements ITraineeDao {
	@Qualifier("resultDao")
	@Autowired
	IResultDao resultDao;
	@Qualifier("quizDao")
	@Autowired
	IQuizDao quizDao;

	@Override
	public boolean create(Trainee trainee) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Trainee.class, trainee.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(trainee);

		// update all the results stored in this trainee
		if (trainee.getResults() != null) {
			trainee.getResults().forEach(result -> {
				result.setTrainee(trainee);
				resultDao.noTransactionUpdate(em, result);
			});
		}

		// update all quizzes stored in this trainee
		if (trainee.getQuizzes() != null) {
			trainee.getQuizzes().forEach(quiz -> {
				quiz.setOwner(trainee);
				quizDao.noTransactionUpdate(em, quiz);
			});
		}

		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Trainee findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Trainee res = em.find(Trainee.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Trainee with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Trainee trainee) {
		Trainee res = em.find(Trainee.class, trainee.getId());
		if (res == null) {
			em.persist(trainee);
		} else {
			em.merge(trainee);
		}

		// update all the results stored in this trainee
		if (trainee.getResults() != null) {
			trainee.getResults().forEach(result -> {
				result.setTrainee(trainee);
				resultDao.noTransactionUpdate(em, result);
			});
		}

		// update all quizzes stored in this trainee
		if (trainee.getQuizzes() != null) {
			trainee.getQuizzes().forEach(quiz -> {
				quiz.setOwner(trainee);
				quizDao.noTransactionUpdate(em, quiz);
			});
		}
	}

	@Override
	public boolean remove(Trainee trainee) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Trainee res = em.find(Trainee.class, trainee.getId());
		if (res == null) {
			em.close();
			return false;
		}

		// first remove the Results
		if (res.getResults() != null) {
			res.getResults().forEach(result -> resultDao.remove(result));
		}
		// remove self from corresponding quizzes
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
	public Trainee findByEmail(String email) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Trainee.findByEmail");
		query.setParameter(1, email);
		@SuppressWarnings("unchecked")
		List<Trainee> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res.get(0);
	}
	
	@Override
	public long numberOfTrainees() {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Trainee.countAll");
		long res = (long) query.getSingleResult();
		em.close();
		return res;
	}

	public IResultDao getResultDao() {
		return resultDao;
	}

	public void setResultDao(IResultDao resultDao) {
		this.resultDao = resultDao;
	}

	public IQuizDao getQuizDao() {
		return quizDao;
	}

	public void setQuizDao(IQuizDao quizDao) {
		this.quizDao = quizDao;
	}

}
