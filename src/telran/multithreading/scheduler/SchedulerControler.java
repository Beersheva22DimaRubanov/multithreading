package telran.multithreading.scheduler;

import java.util.Arrays;
import java.util.Scanner;

public class SchedulerControler {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input number of threads");
		int threads = scanner.nextInt();
		PrinterScheduler[] printers = new PrinterScheduler[threads];
		for (int i = 0; i < threads; i++) {
			printers[i] = new PrinterScheduler(5, null, 100, String.valueOf(i + 1));
		}
		printers[threads - 1].setNextPrinter(printers[0]);
		for (int i = 0; i < threads - 1; i++) {
			printers[i].setNextPrinter(printers[i + 1]);
		}
		Arrays.stream(printers).forEach(Thread::start);
		printers[0].interrupt();
		scanner.close();

	}
}
