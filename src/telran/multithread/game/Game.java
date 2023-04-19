package telran.multithread.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	private int distance;
	private int nThreads;
	public Map<Integer, Racer> threads;
	private Race race;

	public Game(int distance, int nThreads) {
		super();
		this.distance = distance;
		this.nThreads = nThreads;
		race = new Race();
		threads = new HashMap<Integer, Racer>();
	}

	public void makeThreads() {
		for (int i = 0; i < nThreads; i++) {
			threads.put(i + 1, new Racer(race, distance, ("Thread: " + (i + 1))));
		}
	}

	public void start() throws InterruptedException {
		race.start();
		for (int i = 1; i <= nThreads; i++) {
			Racer printer = threads.get(i);
			printer.start();
		}

		for (int i = 1; i <= nThreads; i++) {
			Racer printer = threads.get(i);
			printer.join();
		}

		List<String> places = race.getParticipants();
		places.forEach(System.out::println);
	}
}
