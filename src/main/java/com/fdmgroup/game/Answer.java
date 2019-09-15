package com.fdmgroup.game;

public class Answer {
	private boolean isCorrect;
	private String answer;
	private int option; //a b c or d
	private double timeTaken;
	
	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Answer(boolean isCorrect, String answer, int option) {
		super();
		this.isCorrect = isCorrect;
		this.answer = answer;
		this.option = option;
	}
	
	
	public double getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Answer [isCorrect=" + isCorrect + ", answer=" + answer + ", option=" + option + "]";
	}
	
}
