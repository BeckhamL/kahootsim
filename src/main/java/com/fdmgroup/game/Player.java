package com.fdmgroup.game;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

public class Player {
	private PlayerDetails info;
	private Session session;

	public Player() {
		super();
	}

	public Player(PlayerDetails info, Session session) {
		super();
		this.info = info;
		this.session = session;
	}

	public PlayerDetails getInfo() {
		return info;
	}

	public void setInfo(PlayerDetails info) {
		this.info = info;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "Player [info=" + info + "]";
	}


	
}
