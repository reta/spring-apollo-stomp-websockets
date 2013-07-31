package com.example.messaging;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.activemq.apollo.broker.Broker;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Starter  {
    public static void main( String[] args ) throws Exception {
    	try( ConfigurableApplicationContext context = new AnnotationConfigApplicationContext( AppConfig.class ) ) {
	        final Broker broker = context.getBean( Broker.class );  
	        System.out.println( "Press any key to terminate ..." );
	        System.in.read();    
	        
	        final CountDownLatch latch = new CountDownLatch( 1 );
	        broker.stop( new Runnable() {	
        		@Override
    			public void run() {		
    				System.out.println("The broker has been stopped.");
    				latch.countDown();
    			}
	        } );
	        
	        // Gracefully stop the broker
	        if( latch.await( 1, TimeUnit.SECONDS ) == false ) {
	        	System.out.println("The broker hasn't been stopped, exiting anyway ...");
	        }
    	}
    }
}
