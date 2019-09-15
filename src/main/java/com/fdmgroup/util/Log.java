package com.fdmgroup.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	public static Logger logger = LogManager.getLogger("MyLogger");
	
	public static void debug(String msg) {
		logger.debug(msg);
	}
}
