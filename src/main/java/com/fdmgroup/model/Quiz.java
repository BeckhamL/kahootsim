package com.fdmgroup.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_QUIZ")
@NamedQueries({
	@NamedQuery(name = "Quiz.findUserQuizzesInModule", 
		query = "SELECT q FROM Quiz q WHERE q.owner = ?1 AND q.moduleName = ?2"),
	@NamedQuery(name="Quiz.findByModule", query="SELECT q FROM Quiz q WHERE q.moduleName LIKE ?1"),
	@NamedQuery(name="Quiz.findAll", query="SELECT q FROM Quiz q")
})
public class Quiz implements IStorable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUIZ_ID_GEN")
	@SequenceGenerator(name = "QUIZ_ID_GEN", sequenceName = "QUIZ_ID_SEQ")
	private int id;
	//required annotation not allowed
	private String title;
	private String description;
	private boolean isShareable;
	// enums do not require a JPA annotation
	private Module moduleName;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private User owner;
	@ManyToMany(mappedBy = "quizzes")
	@OrderBy("questionIndex")
	private List<Question> questions;

	public Quiz() {
		super();
	}

	public Quiz(String title, String description, boolean isShareable, Module moduleName, User owner,
			List<Question> questions) {
		super();
		this.title = title;
		this.description = description;
		this.isShareable = isShareable;
		this.moduleName = moduleName;
		this.owner = owner;
		this.questions = questions;
	}

	public Question getQuestion(int questionId) {
		for (Question q : questions) {
			if (q.getId() == questionId) {
				return q;
			}
		}
		return null;
	}

	public void addQuestion(Question question) {
		questions.add(question);
	}

	public void removeQuestion(Question question) {
		questions.remove(question);
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
		Quiz other = (Quiz) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public int getNextQuestionIndex() {
		int max = 0;
		for (Question q : questions) {
			if (q.getQuestionIndex() > max) {
				max = q.getQuestionIndex();
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsShareable() {
		return isShareable;
	}

	public void setIsShareable(boolean isShareable) {
		this.isShareable = isShareable;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Module getModuleName() {
		return moduleName;
	}

	public void setModuleName(Module moduleName) {
		this.moduleName = moduleName;
	}

}
