package com.fdmgroup.game;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fdmgroup.util.Log;
import com.google.gson.Gson;

public class QuizDecoder implements Decoder.Text<SocketQuiz> {
	 
    private static Gson gson = new Gson();
 
    @Override
    public SocketQuiz decode(String s) throws DecodeException {
    	Log.debug("DECODE Quiz: " + s);
        return gson.fromJson(s, SocketQuiz.class);
    }
 
    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }
 
    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }
 
    @Override
    public void destroy() {
        // Close resources
    }

}
