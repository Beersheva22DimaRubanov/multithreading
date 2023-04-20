package telran.multithreading.deadlock;

public class DeadlockThread extends Thread {
	static final Object obj1 = new Object();
	static final Object obj2 = new Object();
	
	private void method1() {
		synchronized (obj1) {
			System.out.println("some1");
			synchronized(obj2) {
				System.out.println("some");
			}
		}
	}
	
	private void method2() {
		synchronized (obj2) {
			System.out.println("some2");
			synchronized(obj1) {
				System.out.println("some");
			}
		}
	}
	
	@Override
	public void run() {
		method1();
		method2();
	}
	
}
