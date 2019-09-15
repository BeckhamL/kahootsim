package com.fdmgroup.game;

import java.util.ArrayList;
import java.util.List;

public class SocketQuestion {
	private List<Answer> choices = new ArrayList<>();
	private String question;
	private int id;
	private int timeLimit;
	
	public SocketQuestion() {
		super();
		this.timeLimit = 20;
	}
	public SocketQuestion(List<Answer> choices, String question) {
		super();
		this.choices = choices;
		this.question = question;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Answer> getChoices() {
		return choices;
	}
	public void setChoices(List<Answer> choices) {
		this.choices = choices;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	@Override
	public String toString() {
		return "Question [choices=" + choices + ", question=" + question + "]";
	}
}
