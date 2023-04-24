package telran.multithreading;

import telran.multithreading.consumers.Receiver;

public class SenderReceiversAppl {
	private static final int N_MESSAGES = 21;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) {
		MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		for(int i = 0; i< N_RECEIVERS; i++) {
			new Receiver(messageBox).start();
		}
	}
}
