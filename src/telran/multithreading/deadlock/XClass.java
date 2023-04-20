package telran.multithreading.deadlock;

public class XClass extends Thread {
	final static Object res1 = new Object();
	final static Object res2 = new Object();
	final static Object res3 = new Object();
	
	private void m1() {
		synchronized (res1) {
			synchronized (res2) {
				
			}
		}
	}
	
	private void m2() {
		synchronized (res2) {
			synchronized (res1) {
				
			}
		}
	}
	
	private void m3() {
		synchronized (res3) {
			
		}
	}
	
	@Override
	public void run() {
		for(int i=0; i< 10000; i++) {
			m1();
			m2();
			m3();
		}
	}

}
