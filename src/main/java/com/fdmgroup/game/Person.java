package com.fdmgroup.game;

public class Person {
	private String name;
	private int pin;
	private SocketQuiz quiz;
	public Person() {
		super();
	}
	public Person(String name, int pin, SocketQuiz quiz) {
		super();
		this.name = name;
		this.pin = pin;
		this.quiz = quiz;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	
	
	public SocketQuiz getQuiz() {
		return quiz;
	}
	public void setQuiz(SocketQuiz quiz) {
		this.quiz = quiz;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", pin=" + pin + "]";
	}
	
}
