package telran.multithreading.garage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import telran.multithreading.util.MyLinkedBlockingQueue;

public class GarageApp {

	private static final int QUEUE_LIMIT = 20;
	private static final int WORKER_LIMIT = 15;

	public static void main(String[] args) throws InterruptedException {
//		BlockingQueue<Car> cars = new LinkedBlockingQueue<>(QUEUE_LIMIT);
		MyLinkedBlockingQueue<Car> cars = new MyLinkedBlockingQueue<>(QUEUE_LIMIT);
		CarsController carsController = new CarsController(cars);
		carsController.start();
		List<Worker> workers = new ArrayList<>();
		for(int i = 0; i<WORKER_LIMIT; i++) {
			Worker worker = new Worker(cars);
			workers.add(worker);
			worker.start();
		}
		carsController.join();
		workers.forEach(t ->  t.interrupt());
		workers.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println("Total number of cars: " + CarsController.totalCars);
		System.out.println("Rejected cars: " + CarsController.rejectedCars);
		System.out.println("Repared cars: " + Worker.counter);
		System.out.println("Not working time: " + Worker.notWorkingTime.get()/WORKER_LIMIT);
	}

}
