package telran.multithreading;

public class MessageBox {
	String message;
	synchronized public void put(String message) {
		this.message = message;
		this.notify();
	}
	
	synchronized public String take() throws InterruptedException {
		while(message == null) {
			wait();
		}
		String res = message;
		message = null;
		return res;
	}
}
