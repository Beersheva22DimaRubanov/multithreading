package telran.multithread.game;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Race {
	private List<String> participants = new ArrayList<>();
	private Instant start;
	private int id = 1;

	public void start() {
		start = Instant.now();
	}

	synchronized public void addParticipant(String name) {
			String res = String.format("Place: %d, %s, time: %d", id++, name,
					ChronoUnit.MILLIS.between(start, Instant.now()));
			participants.add(res);
	}

	public List<String> getParticipants() {
		return participants;
	}
}
