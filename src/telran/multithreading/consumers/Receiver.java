package telran.multithreading.consumers;

import java.util.concurrent.atomic.AtomicInteger;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	public static AtomicInteger counter = new AtomicInteger();
	MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
		super();
		this.messageBox = messageBox;
		counter.set(0);
	}
	
	private void display(String message) {
		System.out.printf("Thread: %s; received message: %s\n", getName(), message);
		counter.incrementAndGet();
		
	}

	@Override
	public void run() {
		String message = "";
		while (true) {
			try {
				message = messageBox.take();
				display(message);

			} catch (InterruptedException e) {
				do {
					message = messageBox.get();
					if(message != null) {
						display(message);
					}
					
				} while (message != null);
				break;
			}
		}
	}
}
