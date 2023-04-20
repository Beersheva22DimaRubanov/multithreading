package telran.multithreading.deadlock;

public class Deadlock {
	
	//deadLock making
//	public static void main(String[] args) {
//		DeadlockThread thr1 = new DeadlockThread();
//		DeadlockThread thr2 = new DeadlockThread();
//		thr1.start();
//		thr2.start();
//	}
	
	public static void main(String[] args) {
		XClass x1 = new XClass();
		XClass x2 = new XClass();
		x1.start();
		x2.start();
	}
}
