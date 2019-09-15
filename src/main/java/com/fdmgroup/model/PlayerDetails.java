package com.fdmgroup.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetails {
	private String username;
	private List<Choice> answers = new ArrayList<>();
	private int score;
	public PlayerDetails() {
		super();
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Choice> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Choice> answers) {
		this.answers = answers;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Player [username=" + username + ", answers=" + answers + ", score=" + score + "]";
	}
	
	public PlayerDetails(String username, List<Choice> answers, int score) {
		super();
		this.username = username;
		this.answers = answers;
		this.score = score;
	}

}
