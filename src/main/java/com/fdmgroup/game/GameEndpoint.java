package com.fdmgroup.game;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fdmgroup.util.Log;
import com.google.gson.Gson;

@ServerEndpoint(value="/game/{username}", 
decoders = { CommandDecoder.class, PlayerDecoder.class, AnswerDecoder.class, ReportDecoder.class }, 
encoders = { CommandEncoder.class, PlayerEncoder.class, AnswerEncoder.class, ReportEncoder.class } )
public class GameEndpoint {
  
	//The session for the user who connected to the server.
    private Session session;
    
    //A HashMap of usernames of people connected to the server. 
    //Session id is the key, and the username is the value.
    private static HashMap<String, String> users = new HashMap<>();
    
    //A HashMap of games. The pin is the key and the value is the Game object.
    private static HashMap<String, Game> games = new HashMap<>();
    
    //The pin that the user is connected to.
    private String pin;
    
    //Whether or not this connection is a person hosting a game.
    private boolean isHost;
 
    @OnOpen
    public void onOpen(
      Session session, 
      @PathParam("username") String username) throws IOException, EncodeException {
    	Log.debug("User " + username + " has connected!");
        if (!users.containsValue(username)) {
        	//Initialize the session and add the username to the HashMap of users.
            this.session = session;
            users.put(session.getId(), username);       	
        } else {
        	Log.debug("close sesh");
        	session.close();
        }
        
    }
 
    /**
     * The client send a command to the server in the form of GameCommand.
     * @param session
     * @param command
     * @throws IOException
     * @throws EncodeException
     */
    @OnMessage
    public void onMessage(Session session, GameCommand command) 
      throws IOException, EncodeException {
    	Log.debug("Server Received Message: " + command.getCommand());
    	//Determine which command the server will execute.
    	switch(command.getCommand()) {
    		case "initGame":
    			initGame((String)command.getValue());
    			break;
    		case "checkPin":
    			checkPin((String)command.getValue());
    			break;
    		case "startGame":
    			Log.debug("size: " + games.get(pin).getPlayers().size());
    			if (games.get(pin).getPlayers().size() > 0) {
    				games.get(pin).setGameState(Game.IN_PROGRESS);
    				startGame();
    			}
    			break;
    		case "checkNickname":
    			checkNickname((String)command.getValue());
    			break;
    		case "submitAnswer":
    			submitAnswer((String)command.getValue());
    			break;
    		case "forceEndQuestion":
    			endQuestion();
    			break;
    		case "endGame":
    			endGame();
    			break;
    		case "generateReport":
    			generateReport();
    			break;
    		case "removeNickname":
    			removeNickname((String)command.getValue());
    			break;
    		case "nextQuestion":
    			nextQuestion();
    	}
    }
    
    private void generateReport() throws IOException, EncodeException {
    	Log.debug("Generate report");
    	Report report = new Report();
    	report.setPlayers(games.get(pin).getAllInfo());
    	report.setQuestions(games.get(pin).getQuestions());
    	report.setQuizId(games.get(pin).getQuizId());
    	report.generateResults();
		GameCommand gc = new GameCommand("displayReport", report);
		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
		Log.debug("sent report report");
    }
    
    /**
     * 
     */
    private void endGame() {
    	//Remove the Game from the list of games in the HashMap games
    	games.remove(pin);
    }
    
    private void endQuestion() throws IOException, EncodeException {
    	games.get(pin).submitEmptyAnswer();
		//Send host results of the round
		GameCommand gc = new GameCommand("roundStats", games.get(pin).getAllInfo());
		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
		
		//Send each player their result information from the game.
    	List<Player> players = games.get(pin).getPlayers();
    	for (Player p : players) {
    		printStatus(p.getInfo());
    		GameCommand gc2 = new GameCommand("displaySingleResult", p.getInfo());
			p.getSession().getBasicRemote().sendObject(gc2);
		}
    }
    
    private void removeNickname(String nickname) throws IOException, EncodeException {
    	Log.debug("Remove nickname");
    	
		for (Player p : games.get(pin).getPlayers()) {
			if (p.getInfo().getNickname().equals(nickname)) {
	    		GameCommand gc2 = new GameCommand("kicked", "");
	    		p.getSession().getBasicRemote().sendObject(gc2);
				games.get(pin).getPlayers().remove(p);
				users.remove(p.getSession().getId());
				p.getSession().close();
				break;
			}
		}
		List<PlayerDetails> details = new ArrayList<PlayerDetails>();
		for (Player p : games.get(pin).getPlayers()) {
			details.add(p.getInfo());
		}
		
		Log.debug("send host addToLobby cmd");
		//Send command so that the host updates the lobby view.
		GameCommand gc = new GameCommand("addToLobby", details);
		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
		
    }
    
    
    private void checkNickname(String nickname) throws IOException, EncodeException {
    	Log.debug("CHECK NICKNAME");
    	
    	if (!games.get(pin).hasNickname(nickname)) {
    		Log.debug("usable nickname");
    		Player player = getPlayer();
    		player.getInfo().setNickname(nickname);
    		
    		
    		Log.debug("get new list of players...");
    		//Get a list of Player information to send to the trainer/host to display an
    		//updated view of the lobby.
    		List<PlayerDetails> details = new ArrayList<PlayerDetails>();
    		for (Player p : games.get(pin).getPlayers()) {
				details.add(p.getInfo());
			}
    		
    		Log.debug("send host addToLobby cmd");
    		//Send command so that the host updates the lobby view.
    		GameCommand gc = new GameCommand("addToLobby", details);
    		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
    		
    		Log.debug("sned user foundGame cmd");
    		//Let the user know that they found a game.
    		GameCommand gc2 = new GameCommand("foundGame", "");
    		session.getBasicRemote().sendObject(gc2);
    	} else {
    		Log.debug("nickname taken...");
    		GameCommand gc2 = new GameCommand("takenNickname", "");
    		session.getBasicRemote().sendObject(gc2);
    	}
    }
    
    /**
     * Move onto the next question in the quiz.
     * @throws EncodeException 
     * @throws IOException 
     */
    private void nextQuestion() throws IOException, EncodeException {
    	if (games.get(pin).hasNextQuestion()) {
    		games.get(pin).nextQuestion();
    		startGame();
    	} else {
    		games.get(pin).setGameState(Game.FINISHED);
    		
    		//Host displays results
    		GameCommand gc = new GameCommand("finishedGame", games.get(pin).getAllInfo());
    		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
    		
    		//Players displays their rank
        	List<Player> players = games.get(pin).getPlayers();
        	for (Player p : players) {
        		p.getInfo().setPlace(calculatePlace(games.get(pin).getAllInfo(), p.getInfo().getScore()));
        		GameCommand gc2 = new GameCommand("finishedGame", p.getInfo());
    			p.getSession().getBasicRemote().sendObject(gc2);
    		}
        	//Remove game from list of ongoing games on the server.
        	//games.remove(pin);
    	}
    }
    
    private int calculatePlace(List<PlayerDetails> allInfo, int score) {
		int place = 0;
		int prevScore = -1;
		for (PlayerDetails playerDetails : allInfo) {
			if (prevScore != playerDetails.getScore()) {
				place++;
			}
			if (playerDetails.getScore() == score) {
				return place;
			}
			prevScore = playerDetails.getScore();
		}
		return 0;
	}
    
    /**
     * The player submits their answer to the server in the form of an index in the
     * Question's list of answers.
     * @param ans
     * @throws IOException
     * @throws EncodeException
     */
    private void submitAnswer(String ans) throws IOException, EncodeException {
    	int answer = Integer.parseInt(ans);
    	Player player = getPlayer();
    	
    	//Retrieve the Answer object using the submitted answer index.
    	Answer submittedAnswer = new Answer();
    	submittedAnswer.setAnswer(games.get(pin).getNextQuestion().getChoices().get(answer).getAnswer());
    	submittedAnswer.setCorrect(games.get(pin).getNextQuestion().getChoices().get(answer).isCorrect());
    	submittedAnswer.setOption(answer);
    	
    	//Calculate time taken and points if the answer is correct.
    	long questionTime = games.get(pin).getNextQuestion().getTimeLimit() * 1000;
    	long answerTime =  System.currentTimeMillis() - games.get(pin).getAskTime();
    	Log.debug("Time Elapsed Answering: " + answerTime);
		double timeMultiplier = ((double) questionTime - (double) answerTime) / (double) questionTime;
		double points = Math.round(1000 * timeMultiplier);
		
    	DecimalFormat df = new DecimalFormat("#.##");
    	double seconds = Double.parseDouble(df.format(answerTime / 1000.0));
    	submittedAnswer.setTimeTaken(seconds);
    	if (submittedAnswer.isCorrect()) {
    		player.getInfo().addPoints((int)points);
    	}
    	player.getInfo().getAnswers().add(submittedAnswer);
    	
    	
    	//Check to see if everyone in the game submitted an answer.
    	if (games.get(pin).finishedAnswering()) {
    		Log.debug("The last answer was submitted.");
    		//Send host results of the round
    		GameCommand gc = new GameCommand("roundStats", games.get(pin).getAllInfo());
    		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
    		
    		//Send each player their result information from the game.
        	List<Player> players = games.get(pin).getPlayers();
        	for (Player p : players) {
        		printStatus(p.getInfo());
        		GameCommand gc2 = new GameCommand("displaySingleResult", p.getInfo());
    			p.getSession().getBasicRemote().sendObject(gc2);
    		}
    	} else {
    		Log.debug("Received an answer, but still waiting on more.");
    		GameCommand gc = new GameCommand("receivedAnswer", "");
    		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
    	}
    }
    
    private void printStatus(PlayerDetails p) {
    	List<PlayerDetails> players = games.get(pin).getAllInfo();
    	int index = 0;
    	int previousScore = 0;
    	int pointsBehind = 0;
    	String previousNickname = "";
    	int topScore = players.get(0).getScore();
    	p.setPlace(calculatePlace(games.get(pin).getAllInfo(), p.getScore()));
    	for (PlayerDetails player : players) {
    		if (p.getUsername().equals(player.getUsername())) {
    			if (index == 0) {
    				p.setAnswerComment("You're leading the pack!");
    				Log.debug(p.getNickname() + " is in first place!");
    				break;
    			} else {
    				if (p.getScore() == topScore) {
    					p.setAnswerComment("You're tied in first!");
    					break;
    				} else if (previousScore == p.getScore()) {
        				for (int i = index - 2; i > 0; i--) {
        					if (players.get(i).getScore() != p.getScore()) {
        						pointsBehind = players.get(i).getScore() - p.getScore();
        						p.setAnswerComment("You're " + pointsBehind + " points behind " + players.get(i).getNickname());
        						Log.debug("You're " + pointsBehind + " points behind " + players.get(i).getNickname());
        						break;
        					}
        				}
        				break;
        			} else {
	        			pointsBehind = previousScore - p.getScore();
	        			p.setAnswerComment("You're " + pointsBehind + " points behind " + previousNickname);
	        			Log.debug("You're " + pointsBehind + " points behind " + previousNickname);
	        			break;
        			}
        		}
    		} else { 
	    		previousScore = player.getScore();
	    		previousNickname = player.getNickname();
				index++;
    		}
		}
    }
    
    /**
     * Return the Player object for this session's user.
     * @return The Player
     */
    private Player getPlayer() {
    	for (Player player : games.get(pin).getPlayers()) {
			if (player.getInfo().getUsername().equals(users.get(session.getId()))) {
				return player;
			}
		}
    	return null;
    }
    
    /**
     * Display the next question in the game.
     * @throws IOException
     * @throws EncodeException
     */
    private void startGame() throws IOException, EncodeException {
    	Log.debug("Display a question");
    	
    	games.get(pin).setAskTime();
    
    	//Send the players the question.
    	List<Player> players = games.get(pin).getPlayers();
    	for (Player player : players) {
    		GameCommand gc = new GameCommand("displayQuestion", games.get(pin).getNextQuestion());
			player.getSession().getBasicRemote().sendObject(gc);
		}
    	
    	//Send the trainer the question.
    	GameCommand gc = new GameCommand("displayQuestion", games.get(pin).getNextQuestion());
    	session.getBasicRemote().sendObject(gc);
    }
    
    /**
     * Check the pin that the player submits to the server.
     * @param pin
     * @throws IOException
     * @throws EncodeException
     */
    private void checkPin(String pin) throws IOException, EncodeException {
    	if (games.containsKey(pin) && games.get(pin).getGameState() == Game.WAITING) {
    		//TODO: TAKE PIN AS ONE COMMAND AND THEN SEND TO THE USER TO UPDATE SCREEN AND THEN TAKE NICKNAME AND THEN JOIN LOBBY
    		Log.debug("Add user + " + users.get(session.getId()) + " to game on pin - " + pin);
    		this.pin = pin;
    		isHost = false;
    		//Create a new Player to add to the Game object.
    		Player player = new Player();
    		player.setSession(session);
    		PlayerDetails info = new PlayerDetails();
    		info.setScore(0);
    		info.setUsername(users.get(session.getId()));
    		player.setInfo(info);
    		games.get(pin).getPlayers().add(player);
    		
    		//Send command to the player to show that they've found a game.
    		GameCommand gc2 = new GameCommand("validPin", "");
    		session.getBasicRemote().sendObject(gc2);
    	} else if (games.containsKey(pin) && games.get(pin).getGameState() != Game.WAITING) {
    		GameCommand gc2 = new GameCommand("pinInProgress", "");
    		session.getBasicRemote().sendObject(gc2);
    		users.remove(session.getId());
    	} else {
    		Log.debug("The user submitted an invalid pin.");
    		GameCommand gc2 = new GameCommand("invalidPin", "");
    		session.getBasicRemote().sendObject(gc2);
    		users.remove(session.getId());
    	}
    }
    
    /**
     * The trainer/host initializes the game for players to join.
     * @param pin
     * @throws IOException
     * @throws EncodeException
     */
    private void initGame(String gameDataString) throws IOException, EncodeException {
    	Log.debug("Initializing Game - initGame");
    	Log.debug("GAMEDATA: " + gameDataString);
    	Gson gson = new Gson();
    	GameData gameData = gson.fromJson(gameDataString, GameData.class);
    	//Make sure there are no games with the entered pin.
    	if (!games.containsKey(gameData.getPin())) {
    		//Create the Game
    		this.pin = gameData.getPin();
    		isHost = true;
    		Game g = new Game();
    		g.setQuizId(gameData.getQuiz().getId());
    		g.setQuestions(gameData.getQuiz().getQuestions());
    		g.setHostSession(session);
    		
    		//Add the game to the server's HashMap of games.
    		games.put(pin, g);

    		//Tell the host's client that they've created a game.
    		GameCommand gc = new GameCommand("createdGame", pin);
    		session.getBasicRemote().sendObject(gc);
    	} else {
    		Log.debug("The entered pin, " + pin + ", is already in use.");
    	}
    }
    
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
    	Log.debug("Closed Connection! - Has Pin " + pin);
    	if (pin != null) {
    		if (games.containsKey(pin) && !isHost) {
    			Log.debug("Player closed the connection while in a game.");
    			//Remove the disconnected player from the Game's list of players.
    			List<Player> players = games.get(pin).getPlayers();
    			for (Player player : players) {
					if (player.getInfo().getUsername().equals(users.get(session.getId()))) {
						players.remove(player);
						if (games.get(pin).getGameState() == Game.WAITING) {
							//Create a new list of player information to send to the trainer
							//if the game is in the lobby so that the list of players is updated.
				    		List<PlayerDetails> details = new ArrayList<PlayerDetails>();
				    		for (Player p : games.get(pin).getPlayers()) {
								details.add(p.getInfo());
							}
				    		GameCommand gc = new GameCommand("addToLobby", details);
				    		games.get(pin).getHostSession().getBasicRemote().sendObject(gc);
						}
						break;
					}
				}
    		} else if (games.containsKey(pin) && isHost) {
    			Log.debug("Host Closed the Connection owning a game.");
    			//Tell all players in the game that the game was cancelled.
    	    	List<Player> players = games.get(pin).getPlayers();
    	    	for (Player player : players) {
    	    		GameCommand gc = new GameCommand("cancelGame", "");
    				player.getSession().getBasicRemote().sendObject(gc);
    			}
    			//Remove the Game from the list of games in the HashMap games
    	    	games.remove(pin);
    	    	
    		}
    	}
    	users.remove(session.getId());
       
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
 
}