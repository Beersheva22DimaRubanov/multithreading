package telran.multithreading.scheduler;

public class PrinterScheduler extends Thread {
	private int portion;
	private PrinterScheduler nextPrinter;
	private int amount;
	private String name;

	public PrinterScheduler(int portion, PrinterScheduler nextPrinter, int amount, String name) {
		super();
		this.portion = portion;
		this.nextPrinter = nextPrinter;
		this.amount = amount;
		this.name = name;
	}

	public PrinterScheduler getNextPrinter() {
		return nextPrinter;
	}

	public void setNextPrinter(PrinterScheduler nextPrinter) {
		this.nextPrinter = nextPrinter;
	}

	@Override
	public void run() {
		while (amount!=0) {
			try {
				join();
			} catch (InterruptedException e) {
				if (amount != 0) {
					for (int i = 0; i < portion; i++) {
						System.out.print(name + " ");
						amount--;
					}
					System.out.println();
				}
				nextPrinter.interrupt();
			}
		}

	}

}
