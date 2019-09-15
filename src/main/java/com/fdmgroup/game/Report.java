package com.fdmgroup.game;

import java.util.ArrayList;
import java.util.List;

public class Report {
	private int quizId;
	private List<PlayerDetails> players;
	private List<SocketQuestion> questions;
	private List<SocketResult> results = new ArrayList<>();
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Report(int quizId, List<PlayerDetails> players, List<SocketQuestion> questions) {
		super();
		this.quizId = quizId;
		this.players = players;
		this.questions = questions;
	}
	
	public void generateResults() {
		for (PlayerDetails player : players) {
			SocketResult result = new SocketResult();
			result.setQuizId(quizId);
			result.setScore(player.getScore());
			result.setUsername(player.getUsername());
			result.setAnswers(player.getAnswers());
			results.add(result);
		}
	}
	
	public List<SocketResult> getResults() {
		return results;
	}
	public void setResults(List<SocketResult> results) {
		this.results = results;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public List<PlayerDetails> getPlayers() {
		return players;
	}
	public void setPlayers(List<PlayerDetails> players) {
		this.players = players;
	}
	public List<SocketQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<SocketQuestion> questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		return "Report";
	}
	
}
