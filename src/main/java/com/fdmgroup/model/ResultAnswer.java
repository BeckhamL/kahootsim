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
@Table(name = "FDM_RESULT_ANSWER")
public class ResultAnswer implements IStorable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_ANSWER_ID_GEN")
	@SequenceGenerator(name = "RESULT_ANSWER_ID_GEN", sequenceName = "RESULT_ANSWER_ID_SEQ")
	private int id;
	private boolean isCorrect;
	private double timeTaken;
	private int resultAnswerIndex;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private Result result;
	private int questionId;

	public ResultAnswer() {
		super();
	}

	public ResultAnswer(boolean isCorrect, double timeTaken, int resultAnswerIndex, Result result, int questionId) {
		super();
		this.isCorrect = isCorrect;
		this.timeTaken = timeTaken;
		this.resultAnswerIndex = resultAnswerIndex;
		this.result = result;
		this.questionId = questionId;
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
		ResultAnswer other = (ResultAnswer) obj;
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

	public boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public double getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}

	public int getResultAnswerIndex() {
		return resultAnswerIndex;
	}

	public void setResultAnswerIndex(int resultAnswerIndex) {
		this.resultAnswerIndex = resultAnswerIndex;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}
