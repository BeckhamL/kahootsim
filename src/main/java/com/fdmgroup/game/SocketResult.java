package com.fdmgroup.game;

import java.util.List;

public class SocketResult {
	private String username;
	private int quizId;
	private int score;
	private List<Answer> answers;
	public SocketResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SocketResult(String username, int quizId, int score, List<Answer> answers) {
		super();
		this.username = username;
		this.quizId = quizId;
		this.score = score;
		this.answers = answers;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	@Override
	public String toString() {
		return "SocketResult [username=" + username + ", quizId=" + quizId + ", score=" + score + ", answers=" + answers
				+ "]";
	}
	
	
}
