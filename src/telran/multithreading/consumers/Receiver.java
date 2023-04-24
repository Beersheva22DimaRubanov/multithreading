package telran.multithreading.consumers;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
		super();
		this.messageBox = messageBox;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String message = messageBox.take();
				System.out.printf("Thread: %s; received message: %s\n", getName(), message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
