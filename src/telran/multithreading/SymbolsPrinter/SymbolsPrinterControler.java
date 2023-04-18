package telran.multithreading.SymbolsPrinter;

import java.util.Scanner;

public class SymbolsPrinterControler {
	public static void main(String[] args) throws InterruptedException {
		SymbolsPrinter printer = new SymbolsPrinter("1234");
		printer.start();
		Scanner scanner = new Scanner(System.in);
		while(!scanner.nextLine().equalsIgnoreCase("q")) {
				printer.interrupt();
		}
		scanner.close();
	}

}
