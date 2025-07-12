package grpc.foodaid.surplus;

import java.io.IOException;
import java.util.logging.Logger;

import grpc.foodaid.surplus.SurplusServiceGrpc.SurplusServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class SurplusServer extends SurplusServiceImplBase  {
	private static final Logger logger = Logger.getLogger(SurplusServer.class.getName());

	public static void main(String[] args) {
		
		SurplusServer greeterserver = new SurplusServer();
		
		int port = 50053;
	    
		try {
			Server server = ServerBuilder.forPort(port)
			    .addService(greeterserver)
			    .build()
			    .start();
			 logger.info("Server started, listening on " + port);
			 server.awaitTermination();

			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    logger.info("Server started, listening on " + port);
	    		    
	   
	}
	
	
	    
}

	
	  
	  
	  