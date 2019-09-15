package com.fdmgroup.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

public class Game {
	public static final int WAITING = 0;
	public static final int IN_PROGRESS = 1;
	public static final int FINISHED = 2;
	
	private int quizId;
	private List<SocketQuestion> questions;
	private List<Player> players;
	private int gameState;
	
	private int currentQuestion;
	
	private Session hostSession;
	
	private long askTime;
	public Game() {
		gameState = WAITING;
		currentQuestion = 0;
		players = new ArrayList<>();
		questions = new ArrayList<>();
		initQuestions();
	}
	
	
	
	public int getQuizId() {
		return quizId;
	}



	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}



	public Session getHostSession() {
		return hostSession;
	}

	public void setHostSession(Session hostSession) {
		this.hostSession = hostSession;
	}
	
	public boolean hasNickname(String nickname) {
		for (Player player : players) {
			if (player.getInfo().getNickname() != null && 
					player.getInfo().getNickname().equals(nickname)) {
				return true;
			}
		}
		return false;
	}

	private void initQuestions() {
		SocketQuestion q1 = new SocketQuestion();	
		Answer q1a1 = new Answer();
		q1a1.setAnswer("1");
		q1a1.setCorrect(false);
		Answer q1a2 = new Answer();
		q1a2.setAnswer("2");
		q1a2.setCorrect(true);
		Answer q1a3 = new Answer();
		q1a3.setAnswer("10");
		q1a3.setCorrect(false);
		Answer q1a4 = new Answer();
		q1a4.setAnswer("5");
		q1a4.setCorrect(false);
		List<Answer> q1Choices = new ArrayList<>();
		q1Choices.add(q1a1);
		q1Choices.add(q1a2);
		q1Choices.add(q1a3);
		q1Choices.add(q1a4);
		q1.setQuestion("What is 1 + 1?");
		q1.setChoices(q1Choices);
		q1.setTimeLimit(20);
		SocketQuestion q2 = new SocketQuestion();	
		Answer q2a1 = new Answer();
		q2a1.setAnswer("10");
		q2a1.setCorrect(false);
		Answer q2a2 = new Answer();
		q2a2.setAnswer("1");
		q2a2.setCorrect(false);
		Answer q2a3 = new Answer();
		q2a3.setAnswer("1000");
		q2a3.setCorrect(false);
		Answer q2a4 = new Answer();
		q2a4.setAnswer("100");
		q2a4.setCorrect(true);
		List<Answer> q2Choices = new ArrayList<>();
		q2Choices.add(q2a1);
		q2Choices.add(q2a2);
		q2Choices.add(q2a3);
		q2Choices.add(q2a4);
		q2.setQuestion("What is 10 * 10?");
		q2.setChoices(q2Choices);
		q2.setTimeLimit(60);
		questions.add(q1);
		questions.add(q2);
	}
	
	public Game(List<SocketQuestion> questions, List<Player> players, int gameState, int currentQuestion) {
		super();
		this.questions = questions;
		this.players = players;
		this.gameState = gameState;
		this.currentQuestion = currentQuestion;
	}
	
	public boolean finishedAnswering() {
		for (Player player : players) {
			//Check to see if everyone answered.
			if (player.getInfo().getAnswers().size() != currentQuestion + 1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * When the trainer goes to the next question or the timer runs out. This
	 * will submit an empty answer for the players who have not submitted an
	 * answer yet.
	 */
	public void submitEmptyAnswer() {
		for (Player player : players) {
			//Check to see if everyone answered.
			if (player.getInfo().getAnswers().size() != currentQuestion + 1) {
				Answer answer = new Answer();
				answer.setAnswer("Did not submit");
				answer.setOption(-1);
				answer.setCorrect(false);
				player.getInfo().getAnswers().add(answer);
			}
		}		
	}
	
	public void nextQuestion() {
		currentQuestion++;
	}
	
	public boolean hasNextQuestion() {
		if (currentQuestion == questions.size() - 1) {
			return false;
		}
		return true;
	}
	
	public List<PlayerDetails> getAllInfo() {
		List<PlayerDetails> info = new ArrayList<>();
		for (Player player : players) {
			info.add(player.getInfo());
		}
		Collections.sort(info, new Comparator<PlayerDetails>() {

			@Override
			public int compare(PlayerDetails o1, PlayerDetails o2) {
				return o2.getScore() - o1.getScore();
			}
			
		});
		return info;
	}

	public int getGameState() {
		return gameState;
	}
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public int getCurrentQuestion() {
		return currentQuestion;
	}
	
	public SocketQuestion getNextQuestion() {
		return questions.get(currentQuestion);
	}
	public void setCurrentQuestion(int currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	@Override
	public String toString() {
		return "Game [WAITING=" + WAITING + ", IN_PROGRESS=" + IN_PROGRESS + ", FINISHED=" + FINISHED + ", questions="
				+ questions + ", players=" + players + ", gameState=" + gameState + ", currentQuestion="
				+ currentQuestion + "]";
	}
	public List<SocketQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<SocketQuestion> questions) {
		this.questions = questions;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}



	public Game(List<SocketQuestion> questions, List<Player> players, int gameState, int currentQuestion,
			Session hostSession) {
		super();
		this.questions = questions;
		this.players = players;
		this.gameState = gameState;
		this.currentQuestion = currentQuestion;
		this.hostSession = hostSession;
	}

	public void setAskTime() {
		askTime = System.currentTimeMillis();
	}
	
	public long getAskTime() {
		return askTime;
	}
	
}
