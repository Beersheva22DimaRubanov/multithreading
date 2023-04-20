package telran.multithread.game;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import telran.view.*;

public class RaceAppl {
	//игра в таракана с использованием Lock для точного результата 

	private static final int MAX_THREADS = 20000;
	private static final int MIN_DISTANCE = 10;
	private static final int MAX_DISTANCE = 100000;
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	public static void main(String[] args) {
		InputOutput io = new StandartInputOutput();
		Item[] items = getItems();
		Menu menu = new Menu("Race Game", items);
		menu.perform(io);

	}

	private static Item[] getItems() {
		Item[] res = {
				Item.of("Start new game", RaceAppl::startGame),
				Item.exit()
		};
		return res;
	}
	static void startGame(InputOutput io) {
		int nThreads = io.readInt("Enter number of the runners","Wrong number of the runners", 2, MAX_THREADS);
		int distance = io.readInt("Enter distance", "Wrong Distance",MIN_DISTANCE, MAX_DISTANCE);
		Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP, new ArrayList<Runner>(), Instant.now());
		Runner[] runners = new Runner[nThreads];
		startRunners(runners, race);
		joinRunners(runners);
		displayResultsTable(race);
	}

	

	private static void joinRunners(Runner[] runners) {
		IntStream.range(0, runners.length).forEach(i -> {
			try {
				runners[i].join();
			} catch (InterruptedException e) {
				throw new IllegalStateException();
			}
		});
		
	}

	private static void startRunners(Runner[] runners, Race race) {
		IntStream.range(0, runners.length).forEach(i -> {
			runners[i] = new Runner(race, i + 1);
			runners[i].start();
		});
		
	}
	private static void displayResultsTable(Race race) {
		ArrayList<Runner> table = race.getResultsTable();
		Instant prev = table.get(0).getFinsishTime();
		int size = table.size();
		int count = 0;
		for(int i = 1; i < size; i++) {
			Instant current = table.get(i).getFinsishTime();
			if (current.isBefore(prev)) {
				count++;
			}
			prev = current;
		}
		System.out.printf("count of results violations is %d\n", count);
		
		
		
		
	}
	private static String toPrintedString(int index, Race race) {
		Runner runner = race.getResultsTable().get(index);
		return String.format("%3d\t%7d\t\t%d", index + 1, runner.getRunnerId(),
				ChronoUnit.MILLIS.between(race.getStartTime(), runner.getFinsishTime()));
	}

}