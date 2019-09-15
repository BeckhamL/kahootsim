package com.fdmgroup.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_QUESTION")
public class Question implements IStorable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_ID_GEN")
	@SequenceGenerator(name = "QUESTION_ID_GEN", sequenceName = "QUESTION_ID_SEQ")
	private int id;
	private String questionContent;
	private int questionIndex;
	private double timeLimit;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private List<Quiz> quizzes;
	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private QuestionImage image;
	@OneToMany(mappedBy = "question")
	@OrderBy("choiceIndex")
	private List<Choice> choices;

	public Question() {
		super();
	}

	public Question(String questionContent, int questionIndex, double timeLimit, List<Quiz> quizzes,
			QuestionImage image, List<Choice> choices) {
		super();
		this.questionContent = questionContent;
		this.questionIndex = questionIndex;
		this.timeLimit = timeLimit;
		this.quizzes = quizzes;
		this.image = image;
		this.choices = choices;
	}

	public void addQuiz(Quiz quiz) {		
		
		if (!quizzes.contains(quiz)) {
			quizzes.add(quiz);
		}
	}

	public void removeQuiz(Quiz quiz) {
		quizzes.remove(quiz);
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
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void addChoice(Choice choice) {
		choices.add(choice);
	}

	public void removeChoice(Choice choice) {
		choices.remove(choice);
	}
	
	public int getNextChoiceIndex() {
		int max = 0;
		for (Choice c : choices) {
			if (c.getChoiceIndex() > max) {
				max = c.getChoiceIndex();
			}
		}
		return max + 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public double getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(double timeLimit) {
		this.timeLimit = timeLimit;
	}

	public QuestionImage getImage() {
		return image;
	}

	public void setImage(QuestionImage image) {
		this.image = image;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", questionContent=" + questionContent + ", questionIndex=" + questionIndex
				+ ", timeLimit=" + timeLimit + ", quizzes=" + quizzes + ", image=" + image + ", choices=" + choices
				+ "]";
	}
	
	
}
