package com.sample.concurrency.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<Item> {
	
	private Queue<Item> queue;
	private int max = 16;
	private ReentrantLock lock = new ReentrantLock(true);
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();
	
	
	public MyBlockingQueue(int size){
		queue = new LinkedList<Item>();
		max = size;
	}
	
	public void produce(Item item) {
		lock.lock();
		try {
			if(queue.size() == max) {
				System.out.println("Queue is full, so waiting for items to be removed");
				notFull.await();
			}
			
			queue.add(item);
			notEmpty.signalAll();
			//System.out.println("Item added to the queue: "+item.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public Item consume() {
		lock.lock();
		Item item = null;
		try {
			while(queue.size() == 0) {
				System.out.println("Queue is empty so waiting for items to be added");
				notEmpty.await();
			}
			item = (Item)queue.remove();
			notFull.signalAll();
			//System.out.println("Item removed is : "+item.toString());
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return item;
	}
	
	
	
}
