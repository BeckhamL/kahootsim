package com.fdmgroup.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_CHOICE")
public class Choice implements IStorable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHOICE_ID_GEN")
	@SequenceGenerator(name = "CHOICE_ID_GEN", sequenceName = "CHOICE_ID_SEQ")
	private int id;
	private String choiceContent;
	private boolean isCorrect;
	private int choiceIndex;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private Question question;

	public Choice() {
		super();
	}

	public Choice(String choiceContent, boolean isCorrect, int choiceIndex, Question question) {
		super();
		this.choiceContent = choiceContent;
		this.isCorrect = isCorrect;
		this.choiceIndex = choiceIndex;
		this.question = question;
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
		Choice other = (Choice) obj;
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

	public String getChoiceContent() {
		return choiceContent;
	}

	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}

	public boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public int getChoiceIndex() {
		return choiceIndex;
	}

	public void setChoiceIndex(int choiceIndex) {
		this.choiceIndex = choiceIndex;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
