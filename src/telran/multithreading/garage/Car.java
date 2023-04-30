package telran.multithreading.garage;

import java.util.concurrent.ThreadLocalRandom;

public class Car {
	private static final long MIN_VALUE = 30;
	private static final long MAX_VALUE = 480;
	private long repairTime;
	private int id;

	public Car(int id) {
		repairTime = ThreadLocalRandom.current().nextLong(MIN_VALUE, MAX_VALUE);
		this.id = id;
	}

	public long getRepairTime() {
		return repairTime;
	}

	@Override
	public String toString() {
		return "Car " + id;
	}
	
	
}
