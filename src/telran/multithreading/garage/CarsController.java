package telran.multithreading.garage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CarsController extends Thread {
	private static final int TIME_PERIOD = 4800;
	public static AtomicInteger rejectedCars = new AtomicInteger();
	public static AtomicInteger totalCars = new AtomicInteger();
	private BlockingQueue<Car> queue;
	
	public CarsController(BlockingQueue<Car> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		for(int i =0; i < TIME_PERIOD; i++) {
				if(ThreadLocalRandom.current().nextInt(0, 100) <= 15) {
					Car car = new Car(i);
					totalCars.incrementAndGet();
					if(!queue.offer(car)) {
						rejectedCars.incrementAndGet();
					};
				}
		}
	}
	
	public void getRejectedCars() {
		System.out.println("Number of rejected cars: " + rejectedCars);
	}
}
