package com.fdmgroup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Result;
import com.fdmgroup.model.ResultAnswer;
import com.fdmgroup.util.JpaUtil;

public class JpaResultDao implements IResultDao {
	@Qualifier("answerDao")
	@Autowired
	IResultAnswerDao answerDao;

	@Override
	public boolean create(Result result) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Result.class, result.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(result);

		// update all the choices contained in this question
		if (result.getAnswers() != null) {
			for (ResultAnswer answer : result.getAnswers().values()) {
				answer.setResult(result);
				answerDao.noTransactionUpdate(em, answer);
			}
		}

		// do not update the quiz associated with this result

		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Result findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Result res = em.find(Result.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Result with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Result result) {
		Result res = em.find(Result.class, result.getId());
		if (res == null) {
			em.persist(result);
		} else {
			em.merge(result);
		}

		// update all the choices contained in this question
		if (result.getAnswers() != null) {
			for (ResultAnswer answer : result.getAnswers().values()) {
				answer.setResult(result);
				answerDao.noTransactionUpdate(em, answer);
			}
		}

		// do not update the quiz associated with this result
	}

	@Override
	public boolean remove(Result result) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Result res = em.find(Result.class, result.getId());
		if (res == null) {
			em.close();
			return false;
		}

		// first remove all ResultAnswers
		if (res.getAnswers() != null) {
			for (ResultAnswer answer : result.getAnswers().values()) {
				answerDao.remove(answer);
			}
		}
		
		// do not remove the quiz associated with the result

		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public IResultAnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(IResultAnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	@Override
	public List<Result> findByQuizId(int quizId) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Result.findByQuizId");
		query.setParameter(1, quizId);
		@SuppressWarnings("unchecked")
		List<Result> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res;
	}

}
