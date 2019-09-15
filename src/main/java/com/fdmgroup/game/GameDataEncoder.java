package com.fdmgroup.game;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fdmgroup.util.Log;
import com.google.gson.Gson;

public class GameDataEncoder implements Encoder.Text<GameData> {
	 
    private static Gson gson = new Gson();
 
    @Override
    public String encode(GameData message) throws EncodeException {
    	Log.debug("ENCODING GameData FROM CLIENT");
    	Log.debug(gson.toJson(message));
        return gson.toJson(message);
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
