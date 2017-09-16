package com.samples;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadExecutors {
	
	public static void main(String[] args) {
		ExecutorService ex = Executors.newSingleThreadExecutor();
		Callable<Object> callable = () -> {
			//Call a service and then return
			return "123";		};
		
		ex.submit(callable);
		
		try{
			ex.shutdown();
			ex.awaitTermination(5, TimeUnit.SECONDS);
		}catch(InterruptedException e){
			System.out.println(e.getMessage());
		}
	}

}
