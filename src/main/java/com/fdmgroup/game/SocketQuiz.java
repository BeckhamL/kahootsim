package com.fdmgroup.game;

import java.util.List;
import com.fdmgroup.game.SocketQuestion;

public class SocketQuiz {
	private int id;
	private String title;
	private List<SocketQuestion> questions;
	public SocketQuiz() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SocketQuiz(int id, String title, List<SocketQuestion> questions) {
		super();
		this.id = id;
		this.title = title;
		this.questions = questions;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<SocketQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<SocketQuestion> questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		return "SocketQuiz [id=" + id + ", title=" + title + ", questions=" + questions + "]";
	}
	
}
