package com.fdmgroup.game;

public class GameCommand {
	private String command;
	private Object value;
	public GameCommand() {
		super();
	}
	public GameCommand(String command, Object value) {
		super();
		this.command = command;
		this.value = value;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "GameCommand [command=" + command + ", value=" + value + "]";
	}
	
	
}
