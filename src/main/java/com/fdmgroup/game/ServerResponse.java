package com.fdmgroup.game;

public class ServerResponse<T> {
	private String responseType;
	private T responseObject;
	public ServerResponse() {
		super();
	}
	public ServerResponse(String responseType, T responseObject) {
		super();
		this.responseType = responseType;
		this.responseObject = responseObject;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public T getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(T responseObject) {
		this.responseObject = responseObject;
	}
	@Override
	public String toString() {
		return "ServerResponse [responseType=" + responseType + ", responseObject=" + responseObject + "]";
	}
	
	
}
