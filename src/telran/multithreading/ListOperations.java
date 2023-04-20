package telran.multithreading;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import javax.imageio.event.IIOReadUpdateListener;

public class ListOperations extends Thread {
	int probUpdate;
	List<Integer> list;
	Lock readLock;
	Lock writeLock;
	AtomicInteger count;
	int nRuns;

	public ListOperations(int probUpdate, List<Integer> list, Lock readLock, Lock writeLock,
			AtomicInteger count, int nRuns) {
		super();
		this.probUpdate = probUpdate;
		this.list = list;
		this.readLock = readLock;
		this.writeLock = writeLock;
		this.count = count;
		this.nRuns = nRuns;
	}

	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			ThreadLocalRandom tlr = ThreadLocalRandom.current();
			if (tlr.nextInt(0, 100) < probUpdate) {
				updateList();
			} else {
				readList();
			}
		}
	}

	private void readList() {
		lock(readLock);
		try {
			int size = list.size();
			for (int i = 0; i < 100; i++) {
				list.get(size - 1);
			}
		} finally {
			readLock.unlock();
		}
	}

	private void lock(Lock lock) {
		while(!lock.tryLock()) {
			count.incrementAndGet();
		}
	}

	private void updateList() {
		lock(writeLock);
		try {
			list.remove(0);
			list.remove(0);
			list.remove(0);
			list.add(100);
			list.add(100);
			list.add(100);
		}finally {
			writeLock.unlock();
		}
		

	}

}
