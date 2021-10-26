package org.jomaveger.minesweeper.model.statistics;

import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Score {

	private List<Time> bestTimes;

	private int gamesPlayed;
	private int gamesWon;

	private int longestWinningStreak;
	private int longestLosingStreak;

	private int currentStreak;

	private int currentWinningStreak;
	private int currentLosingStreak;

	public Score() {
		gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
		bestTimes = new ArrayList<>();
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public int getWinPercentage() {
		double gP = gamesPlayed;
		double gW = gamesWon;

		double percentage = ceil((gW / gP) * 100);

		return (int) percentage;
	}

	public int getLongestWinningStreak() {
		return longestWinningStreak;
	}

	public int getLongestLosingStreak() {
		return longestLosingStreak;
	}

	public int getCurrentStreak() {
		return currentStreak;
	}

	public int getCurrentLosingStreak() {
		return currentLosingStreak;
	}

	public int getCurrentWinningStreak() {
		return currentWinningStreak;
	}

	public void incGamesWon() {
		gamesWon++;
	}

	public void incGamesPlayed() {
		gamesPlayed++;
	}

	public void incCurrentStreak() {
		currentStreak++;
	}

	public void incCurrentLosingStreak() {
		currentLosingStreak++;

		if (longestLosingStreak < currentLosingStreak) {
			longestLosingStreak = currentLosingStreak;
		}
	}

	public void incCurrentWinningStreak() {
		currentWinningStreak++;

		if (longestWinningStreak < currentWinningStreak) {
			longestWinningStreak = currentWinningStreak;
		}
	}

	public void decCurrentStreak() {
		currentStreak--;
	}

	public void resetScore() {
		gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
	}

	public List<Time> getBestTimes() {
		return bestTimes;
	}

	public void addTime(int time, Date date) {
		bestTimes.add(new Time(time, date));
		Collections.sort(bestTimes, new TimeComparator());

		if (bestTimes.size() > 5)
			bestTimes.remove(bestTimes.size() - 1);
	}
}