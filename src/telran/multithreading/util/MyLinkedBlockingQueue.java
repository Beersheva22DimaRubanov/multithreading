package telran.multithreading.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {
	LinkedList<E> list;
	int limit = Integer.MAX_VALUE;
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	Lock readLock = lock.readLock();
	Lock writeLock = lock.writeLock();
	Condition waitingProducer = writeLock.newCondition();
	Condition waitingConsumer = writeLock.newCondition();

	public <E> MyLinkedBlockingQueue() {
		list = new LinkedList<>();
	}

	public <E> MyLinkedBlockingQueue(int limit) {
		list = new LinkedList<>();
		this.limit = limit;
	}

	@Override
	public E remove() {
		E res = null;
		writeLock.lock();
		try {
			if (!list.isEmpty()) {
				res = list.removeFirst();
				waitingConsumer.signal();
			}
		} finally {
			writeLock.unlock();
		}
		return res;
	}

	@Override
	public E poll() {
		E res = null;
		writeLock.lock();
		try {
			if (!list.isEmpty()) {
				res = list.removeFirst();
				waitingConsumer.signal();
			}
		} finally {
			writeLock.unlock();
		}
		return res;
	}

	@Override
	public E element() {
		readLock.lock();
		try {
				return list.getFirst();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public E peek() {
		readLock.lock();
		try {
			return list.getFirst();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int size() {
		readLock.lock();
		try {
				return list.size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		readLock.lock();
		try {
				return list.isEmpty();
			
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> res = null;
		readLock.lock();
		try {
			if (list != null) {
				res = list.iterator();
			}
		} finally {
			readLock.unlock();
		}
		return res;
	}

	@Override
	public Object[] toArray() {
		Object[] res = null;
		readLock.lock();
		try {
			if (list != null) {
				res = list.toArray();
			}
		} finally {
			readLock.unlock();
		}
		return res;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		readLock.lock();
		try {
			return list.toArray(a);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		readLock.lock();
		try {
			return list.containsAll(c);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean res = false;
		writeLock.lock();
		try {
			c.forEach(elem -> {
				try {
					put(elem);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			return true;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean res = false;
		writeLock.lock();
		try {
			res = list.removeAll(c);
			if (res) {
				waitingConsumer.signal();
			}
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		writeLock.lock();
		try {
			return list.retainAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			list.clear();
			waitingConsumer.notify();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		boolean res = false;
		writeLock.lock();
		try {
			if (list.size() < limit) {
				res = list.add(e);
				waitingProducer.signal();
			} else {
				throw new IllegalStateException();
			}

			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		boolean res = false;
		writeLock.lock();
		try {
			if (list.size() < limit) {
				res = add(e);

			}

			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		readLock.lock();
		try {
			while (list.size() == limit) {
				waitingConsumer.await();
			}
			writeLock.lock();
			try {
				list.add(e);
				waitingProducer.signal();
			} finally {
				writeLock.unlock();
			}
		} finally {
			readLock.lock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		boolean res = false;
		readLock.lock();
		try {
			while (list.size() == limit) {
				waitingConsumer.await(timeout, unit);
			}
			writeLock.lock();
			if (list.size() < limit) {
				try {
					list.add(e);
					waitingProducer.signal();

					res = true;
				} finally {
					writeLock.unlock();
				}
			}
			return res;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		E res = null;
		readLock.lock();
		try {
			while (list.size() == 0) {
				waitingProducer.await();
			}
			writeLock.lock();
			try {
				res = list.removeFirst();
			} finally {
				writeLock.unlock();
			}
		} finally {
			readLock.lock();
		}
		return res;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		E res = null;
		readLock.lock();
		try {
			while (list.size() == 0) {
				waitingProducer.await(timeout, unit);
			}
			writeLock.lock();
			if (list.size() < limit) {
				try {
					res = list.remove();
				} finally {
					writeLock.unlock();
				}
			}
			return res;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		readLock.lock();
		try {
			return limit - list.size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		writeLock.lock();
		try {
			return list.remove(o);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		readLock.lock();
		try {
			return list.contains(o);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

}
