package com.fdmgroup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.User;
import com.fdmgroup.util.JpaUtil;

public class JpaQuizDao implements IQuizDao {
	@Qualifier("questionDao")
	@Autowired
	private IQuestionDao questionDao;

	@Override
	public boolean create(Quiz quiz) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Quiz.class, quiz.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(quiz);

		// update all the questions contained in this quiz
		if (quiz.getQuestions() != null) {
			quiz.getQuestions().forEach(question -> {
				question.addQuiz(quiz);
				questionDao.noTransactionUpdate(em, question);
			});
		}

		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Quiz findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Quiz res = em.find(Quiz.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Quiz with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Quiz quiz) {
		Quiz res = em.find(Quiz.class, quiz.getId());
		if (res == null) {
			em.persist(quiz);
		} else {
			em.merge(quiz);
		}

		// update all the questions contained in this quiz
		if (quiz.getQuestions() != null) {
			quiz.getQuestions().forEach(question -> {
				question.addQuiz(quiz);
				questionDao.noTransactionUpdate(em, question);
			});
		}
	}

	@Override
	public boolean remove(Quiz quiz) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Quiz res = em.find(Quiz.class, quiz.getId());
		if (res == null) {
			em.close();
			return false;
		}

		// questions can remain even if the quiz is removed
		if (res.getQuestions() != null) {
			res.getQuestions().forEach(question -> {
				question.removeQuiz(quiz);
				questionDao.update(question);
			});
		}

		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public List<Quiz> findUserQuizzesInModule(User owner, Module moduleName) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Quiz.findUserQuizzesInModule");
		query.setParameter(1, owner);
		query.setParameter(2, moduleName);
		@SuppressWarnings("unchecked")
		List<Quiz> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res;
	}
	
	/**
	 * Return a list of quiz by giving module name
	 * 
	 * @return A list of quizzes
	 */
	@Override
	public List<Quiz> findQuizzesbyModule(Module moduleName) throws NoSuchDatabaseEntryException{
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Quiz.findByModule");
		query.setParameter(1, moduleName);
		@SuppressWarnings("unchecked")
		List<Quiz> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res;
	}

	/**
	 * Return a list of all quizzes in the database
	 * 
	 * @return A list of quizzes
	 */
	@Override
	public List<Quiz> findAllQuiz() throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Quiz.findAll");
		@SuppressWarnings("unchecked")
		List<Quiz> res = query.getResultList();
		em.close();
		if (res.size() == 0)
			throw new NoSuchDatabaseEntryException();
		return res;
	}

	public IQuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	}
}
