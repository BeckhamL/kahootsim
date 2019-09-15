package com.fdmgroup.game;

public class GameData {
	private SocketQuiz quiz;
	private String pin;
	public GameData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameData(SocketQuiz quiz, String pin) {
		super();
		this.quiz = quiz;
		this.pin = pin;
	}
	public SocketQuiz getQuiz() {
		return quiz;
	}
	public void setQuiz(SocketQuiz quiz) {
		this.quiz = quiz;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	@Override
	public String toString() {
		return "GameData [quiz=" + quiz + ", pin=" + pin + "]";
	}
	
}
