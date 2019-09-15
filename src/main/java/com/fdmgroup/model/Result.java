package com.fdmgroup.model;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fdmgroup.dao.IQuizDao;
import com.fdmgroup.dao.JpaQuizDao;
import com.fdmgroup.exception.NoSuchDatabaseEntryException;

@Entity
@Table(name = "FDM_RESULT")
@NamedQueries({
	@NamedQuery(name = "Result.findByQuizId", query ="SELECT r FROM Result r WHERE r.quizId = ?1")
})
public class Result implements IStorable {
	// used to retrieve the quiz corresponding to this result
	private int quizId;
	@Transient
	private IQuizDao quizDao;
	@Transient
	private Quiz quiz;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_ID_GEN")
	@SequenceGenerator(name = "RESULT_ID_GEN", sequenceName = "RESULT_ID_SEQ")
	private int id;
	private int score;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private Trainee trainee;
	@OneToMany(mappedBy = "result")
	@OrderBy("resultAnswerIndex")
    @MapKey(name="questionId")
	private Map<Integer, ResultAnswer> answers;

	public Result() {
		super();
	}

	public Result(Trainee trainee, int quizId, int score, Map<Integer, ResultAnswer> answers) {
		super();
		this.trainee = trainee;
		this.quizId = quizId;
		this.score = score;
		this.answers = answers;
	}

	/**
	 * Calculates the score based on the answers in this Result object and both
	 * sets and returns the score.
	 * 
	 * @return The score of this Result
	 */
	public int calculateResult() {
		// a variable to calculate the current answer streak
		int streak = 1;
		int totalScore = 0;
		// iterate through each answer
		for (Integer key : answers.keySet()) {
			ResultAnswer answer = answers.get(key);
			if (answer.getIsCorrect()) {
				// if the quiz has not been retrieved yet, do so
				if (quiz == null) {
					ApplicationContext context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
					try {
						quizDao = (JpaQuizDao) context.getBean("quizDao");
						quiz = quizDao.findById(quizId);
					} catch (NoSuchDatabaseEntryException e) {
						// represents a failure
						return 0;
					} finally {
						((ClassPathXmlApplicationContext) context).close();
					}
				}
				// get the associated question
				Question question = quiz.getQuestion(key);
				// the multipliers to calculate the final score
				double timeMultiplier = (question.getTimeLimit() - answer.getTimeTaken()) / question.getTimeLimit();
				double streakMultiplier = 1 + Math.log(streak);
				totalScore += Math.round(1000 * timeMultiplier * streakMultiplier);
			}
		}
		return totalScore;
	}

	public void addAnswer(int questionId, ResultAnswer answer) {
		answers.put(questionId, answer);
	}

	public void removeAnswer(int questionId, ResultAnswer answer) {
		answers.remove(questionId);
	}
	
	public ResultAnswer getAnswer(int questionId) {
		return answers.get(questionId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Map<Integer, ResultAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(Map<Integer, ResultAnswer> answers) {
		this.answers = answers;
	}

	public Quiz getQuiz() {
		// if the quiz has not been retrieved yet, do so
		if (quiz == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
			try {
				quizDao = (JpaQuizDao) context.getBean("quizDao");
				quiz = quizDao.findById(quizId);
			} catch (NoSuchDatabaseEntryException e) {
				// represents a failure
				return null;
			} finally {
				((ClassPathXmlApplicationContext) context).close();
			}
		}
		return quiz;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public IQuizDao getQuizDao() {
		return quizDao;
	}

	public void setQuizDao(IQuizDao quizDao) {
		this.quizDao = quizDao;
	}

}
