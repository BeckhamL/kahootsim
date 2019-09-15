package com.fdmgroup.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.QuestionImage;
import com.fdmgroup.util.JpaUtil;

public class JpaQuestionDao implements IQuestionDao {
	@Qualifier("imageDao")
	@Autowired
	private IQuestionImageDao imageDao;
	@Qualifier("choiceDao")
	@Autowired
	private IChoiceDao choiceDao;
	@Qualifier("quizDao")
	@Autowired
	private IQuizDao quizDao;

	@Override
	public boolean create(Question question) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Question.class, question.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(question);

		// update all the choices contained in this question
		if (question.getChoices() != null) {
			question.getChoices().forEach(choice -> {
				choice.setQuestion(question);
				choiceDao.noTransactionUpdate(em, choice);
			});
		}

		// update the image associated with this question
		QuestionImage image = question.getImage();
		if (image != null) {
			imageDao.noTransactionUpdate(em, image);
		}
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Question findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Question res = em.find(Question.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Question with given ID found");
		return res;
	}

	public void update(Question question) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		em.getTransaction().begin();
		noTransactionUpdate(em, question);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Question question) {
		Question res = em.find(Question.class, question.getId());
		if (res == null) {
			em.persist(question);
		} else {
			// merge the new question in
			em.merge(question);
		}

		// update all the choices contained in this question
		if (question.getChoices() != null) {
			question.getChoices().forEach(choice -> {
				choice.setQuestion(question);
				choiceDao.noTransactionUpdate(em, choice);
			});
		}

		// update the image associated with this question
		QuestionImage image = question.getImage();
		if (image != null) {
			imageDao.noTransactionUpdate(em, image);
		}
	}

	@Override
	public boolean remove(Question question) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Question res = em.find(Question.class, question.getId());
		if (res == null) {
			em.close();
			return false;
		}
		
		// remove all choices
		if (res.getChoices() != null) {
			res.getChoices().forEach(choice -> choiceDao.remove(choice));
		}
		// remove self from corresponding quizzes
		if (res.getQuizzes() != null) {
			res.getQuizzes().forEach(quiz -> {
				quiz.removeQuestion(res);
				quizDao.update(quiz);
			});
		}

		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public IQuestionImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(IQuestionImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public IChoiceDao getChoiceDao() {
		return choiceDao;
	}

	public void setChoiceDao(IChoiceDao choiceDao) {
		this.choiceDao = choiceDao;
	}

	public IQuizDao getQuizDao() {
		return quizDao;
	}

	public void setQuizDao(IQuizDao quizDao) {
		this.quizDao = quizDao;
	}
}
