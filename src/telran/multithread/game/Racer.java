package telran.multithread.game;

public class Racer extends Thread {
	private Race race;
	private int nRuns;
	private String str;

	public Racer(Race race, int nRuns, String str) {
		super();
		this.race = race;
		this.nRuns = nRuns;
		this.str = str;

	}

	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			System.out.println(str);
			try {
				long time = (long) (Math.random() * (5 - 2 + 1) + 2);
				sleep(time);
			} catch (InterruptedException e) {
			}
		}
		if (race.getWinner() == "") {
			race.setWinner(str);
		}
	}
}
