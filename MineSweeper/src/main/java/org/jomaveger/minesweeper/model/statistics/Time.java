package org.jomaveger.minesweeper.model.statistics;

import java.util.Date;

public class Time {
	
	private Date date;
	private int time;

	public Time(int t, Date d) {
		time = t;
		date = d;
	}

	public Date getDateValue() {
		return date;
	}

	public int getTimeValue() {
		return time;
	}
}    