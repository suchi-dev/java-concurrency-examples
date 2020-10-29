package com.sample.concurrency.wait;

import java.util.Random;

import com.sample.concurrency.locks.Item;

public class TestProducerConsumerWaits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyBlockWaitQueue<Item> myQueue = new MyBlockWaitQueue<>(10);
		Runnable producerTask =(()-> {
			for(int i =0; i < 10; i++) {
				Item item = new Item(i, "Cakes-" + Thread.currentThread().getName() +i);
				Random random = new Random();
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread :"+Thread.currentThread().getName()+" produced item: " +item);
				myQueue.produce(item);
			}
			
		});
		
		Thread prodThread1 = new Thread(producerTask);
		Thread prodThread2 = new Thread(producerTask);
		prodThread1.start();
		prodThread2.start();
		
		
		
		Runnable consumerTask = (()-> {
			while(true) {
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
	Thread conThread1 = new Thread(consumerTask);
	Thread conThread2 = new Thread(consumerTask);
	conThread1.start();
	conThread2.start();
		
	}
		
		
	

}
