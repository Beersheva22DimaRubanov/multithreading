package telran.multithread.game;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class GameImpl {
	public static void main(String[] args) {
		StandartInputOutput st = new StandartInputOutput();
		Menu menu = new Menu("Game", Item.of("Start", t -> gameStart(st)),
				Item.exit());
		menu.perform(st);
	}

	private static void gameStart(InputOutput t) {
		int distance = t.readInt("Enter distance", "Wrong number" );
		int threads = t.readInt("Enter threads number", "Wrong number");
		Game game = new Game(distance, threads);
		try {
			game.makeThreads();
			System.out.println(game.threads.size());
			game.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
