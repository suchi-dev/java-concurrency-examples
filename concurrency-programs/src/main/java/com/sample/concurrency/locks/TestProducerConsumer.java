package com.sample.concurrency.locks;

import java.util.Random;

public class TestProducerConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyBlockingQueue<Item> myQueue = new MyBlockingQueue<Item>(10);
		Runnable producerTask = (()-> {
			for(int i = 1; i < 20; i++) {
				Item item = new Item(i, "Chocolates-" + Thread.currentThread().getName() +i);		
				System.out.println("Thread :"+Thread.currentThread().getName()+" produced item: " +item);
				myQueue.produce(item);
			}
		});
		
		Thread producerThread1 = new Thread(producerTask);
		Thread producerThread2 = new Thread(producerTask);
		producerThread1.start();
		producerThread2.start();
		
		
		Runnable consumerTask=(()-> {
			while (true) {
				Item i = myQueue.consume();
				Random random = new Random();
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println("Thread :"+Thread.currentThread().getName()+" consumed item: " +i);
			}
		});
	
		Thread consumerThread1 = new Thread(consumerTask);
		Thread consumerThread2 = new Thread(consumerTask);
		consumerThread1.start();
		consumerThread2.start();
		
	}

}
