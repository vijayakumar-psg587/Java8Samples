package com.samples;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProducerConsumerBlockingQueueExample {
	
	public static void main(String[] args) {
		
		ExecutorService ex = Executors.newCachedThreadPool();
		
		final BlockingQueue<String>  prodConsQueue = new LinkedBlockingDeque<String>();;
		Callable<String> producerCallable = () -> {
			
			//Call a service and get hold of the value
			int i=0;
			while(i<5){
				System.out.println("Producing details to queue");
				prodConsQueue.put("ProdValue:"+new Integer(i).toString());
				i++;
			}
			String returnedString = new String("");
			prodConsQueue.stream().forEach(s-> {
				System.out.println("value of s:"+s);
				returnedString.concat("Prod:"+s);});
			return returnedString;
		};
		
		
		Callable<String> consumerCallable = ()->{
			TimeUnit.SECONDS.sleep(5);
			while(Optional.of(prodConsQueue).isPresent()){
				System.out.println("Consumer consuming it:"+prodConsQueue.take());
			}
			String returnedString = new String("");
			prodConsQueue.stream().forEach(s-> returnedString.concat(s));
			return returnedString;
		};
		
		Future<String> prodFuture = ex.submit(producerCallable);
		Future<String> consFuture = ex.submit(consumerCallable);
		
		try{
		 System.out.println("Is prod future done:"+prodFuture.isDone());
		 
			 System.out.println("ProdFuture value:"+prodFuture.get(30, TimeUnit.SECONDS));
			 
		}catch(ExecutionException e){
			System.out.println("Exception while executing prodCallable:"+e.getCause());
		}catch(InterruptedException e){
			System.out.println("Itn Exception while executing prodCallalbe: "+e.getCause());
		}catch(TimeoutException e){
			System.out.println("Timedout");
		}
		 
		 
		 
		try{
			ex.shutdown();
			ex.awaitTermination(1, TimeUnit.MINUTES);
		}catch(InterruptedException e){
			System.out.println(e.getCause());
		}
	}

}
