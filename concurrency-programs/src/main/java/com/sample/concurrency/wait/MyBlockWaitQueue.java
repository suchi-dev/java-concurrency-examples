package com.sample.concurrency.wait;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockWaitQueue<Item> {

	
	private int max=16;
	private Queue<Item> queue;
	private Object notEmpty = new Object();
	private Object notFull = new Object();
	
	
	public MyBlockWaitQueue(int size) {	
		queue = new LinkedList<>();
		this.max = size;
	}
	
	public synchronized void produce(Item e) {
		if(queue.size() == max) {
			System.out.println("Queue is full so I am blocked!!");
			try {
				notFull.wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		queue.add(e);
		notEmpty.notifyAll();
		
		
	}
	
	public synchronized Item consume() {
		Item item = null;
		while(queue.size() == 0) {
			System.out.println("Queue is empty, so I am blocked!");
			try {
				notEmpty.wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		item = queue.remove();
		notFull.notifyAll();
		return item;
	}
	
	



	
}
