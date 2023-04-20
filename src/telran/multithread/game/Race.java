package telran.multithread.game;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Race {
	private int distance;
	private int minSleep;
	private int maxSleep;
	private ArrayList<Runner> resultsTable;
	private Instant startTime;
	public Lock lock = new ReentrantLock(true);
	
	
	public ArrayList<Runner> getResultsTable() {
		return resultsTable;
	}
	public Instant getStartTime() {
		return startTime;
	}
	public Race(int distance, int minSleep, int maxSleep, ArrayList<Runner> resultsTable, Instant startTime) {
		this.distance = distance;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		this.resultsTable = resultsTable;
		this.startTime = startTime;
	}
	
	public int getDistance() {
		return distance;
	}
	public int getMinSleep() {
		return minSleep;
	}
	public int getMaxSleep() {
		return maxSleep;
	}
}