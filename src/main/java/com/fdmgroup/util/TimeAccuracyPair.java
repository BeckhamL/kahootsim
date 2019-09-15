package com.fdmgroup.util;

import java.text.DecimalFormat;

public class TimeAccuracyPair {
	private int questionIndex;
	private double time;
	private double accuracy;
	public TimeAccuracyPair(int questionIndex, double time, double accuracy) {
		super();
		this.questionIndex = questionIndex;
		this.time = time;
		this.accuracy = accuracy;
	}
	public int getQuestionIndex() {
		return questionIndex;
	}
	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	public String getRoundedAccuracy() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMinimumFractionDigits(2);
    	return df.format(accuracy);		
	}
	
	public String getRoundedTime() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMinimumFractionDigits(2);
    	return df.format(time);
	}
}
