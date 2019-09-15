package com.fdmgroup.game;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetails {
	private String username;
	private List<Answer> answers = new ArrayList<>();
	private int score;
	private int place;
	private String nickname;
	private String answerComment;
	public PlayerDetails() {
		super();
	}
		
	public PlayerDetails(String username, List<Answer> answers, int score) {
		super();
		this.username = username;
		this.answers = answers;
		this.score = score;
	}
	
	public PlayerDetails(String username, List<Answer> answers, int score, int place, String nickname) {
		super();
		this.username = username;
		this.answers = answers;
		this.score = score;
		this.place = place;
		this.nickname = nickname;
	}	
	
	

	public String getAnswerComment() {
		return answerComment;
	}

	public void setAnswerComment(String answerComment) {
		this.answerComment = answerComment;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void addPoints(int amount) {
		score += amount;
	}
	
	
	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Player [username=" + username + ", answers=" + answers + ", score=" + score + "]";
	}

	public PlayerDetails(String username, List<Answer> answers, int score, int place, String nickname,
			String answerComment) {
		super();
		this.username = username;
		this.answers = answers;
		this.score = score;
		this.place = place;
		this.nickname = nickname;
		this.answerComment = answerComment;
	}

}
