package telran.multithreading.SymbolsPrinter;

public class SymbolsPrinter extends Thread {
	private String symbols;
	char symbol;
	private int index;
	
	public SymbolsPrinter(String symbols) {
		this.symbols = symbols;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		symbol = getSymbol();
		while (true) {
			System.out.println(symbol);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				run();
			} 
		}
	}
	
	private char getSymbol() {
		char[] chars = symbols.toCharArray();
		if(index == chars.length) {
			index = 0;
		}
		return (chars[index++]);
	}
}
