package telran.multithreading.garage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Worker extends Thread {
	private BlockingQueue<Car> cars;
	public static AtomicInteger counter = new AtomicInteger();
	private Instant start;
	public static AtomicLong workingTime = new AtomicLong();
	public static AtomicLong notWorkingTime = new AtomicLong();
	public static AtomicLong totalTime = new AtomicLong();
	
	public Worker(BlockingQueue<Car> cars) {
		this.cars = cars;
		start =Instant.now();
	}

	@Override
	public void run() {
		Car car;
		while (true) {
			try {
				car = cars.take();
				carRepare(car);
				sleep(car.getRepairTime());
				workingTime.getAndAdd(car.getRepairTime());
			} catch (InterruptedException e) {
				do {
					car = cars.poll();
					if (car != null) {
						carRepare(car);
						try {
							sleep(car.getRepairTime());
							workingTime.addAndGet (car.getRepairTime());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} while (car != null);
				totalTime.addAndGet(ChronoUnit.MILLIS.between(start, Instant.now()));
				setNotWorkingTime();
				break;
				
			}
		}
		
	}

	private void setNotWorkingTime() {
		notWorkingTime.addAndGet( totalTime.get() - workingTime.get());
	}

	private void carRepare(Car car) {
		System.out.printf("Worker: %s, repeared car: %s, repared time: %d \n", getName(),
				car.toString(), car.getRepairTime());
		counter.incrementAndGet();
	}
	
	public Instant getStartTime() {
		return start;
	}

}
