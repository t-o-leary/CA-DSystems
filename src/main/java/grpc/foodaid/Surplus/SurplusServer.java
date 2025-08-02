package grpc.foodaid.Surplus;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

import grpc.foodaid.Surplus.OrderServiceGrpc.SurplusServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
//import io.grpc.stub.StreamObserver;

public class SurplusServer extends OrderServiceGrpc.SurplusServiceImplBase {
	private static final Logger logger = Logger.getLogger(SurplusServer.class.getName());

	public static void main(String[] args) {
		
		// ANSI escape code for red text
        String redText = "\u001B[31m";
        // ANSI escape code to reset text color
        String resetText = "\u001B[0m";

		SurplusServer SurplusService = new SurplusServer();

        
       
        
        
		int port = 50053;
		
		
		if (isPortAvailable(port)) {
		    System.out.println("Port "+port+" is available.");
		

		try {

			System.out.println("Starting server...");
			Server server = ServerBuilder.forPort(port).addService(SurplusService).build().start();
			System.out.println("Server started on port "+ port);
			logger.info("Server started, listening on " + port);
			server.awaitTermination();

		} catch (IOException e) {
			 
			// Log the exception
			logger.severe("Failed to start server: " + e.getMessage()+"\n");
		
			
			// Print the stack trace for debugging	
			e.printStackTrace();

		} catch (InterruptedException e) {
			// Log the exception
			logger.severe("Server interrupted: " + e.getMessage());
			// Print the stack trace for debugging
			e.printStackTrace();
		}

		logger.info("Server started, listening on " + port);
		
		} else {
		    System.out.println(redText+"Port "+port+" is already in use."+resetText);
		}

	
		
		
	}
	
	   
	
	public static boolean isPortAvailable(int port) {
	    try (ServerSocket serverSocket = new ServerSocket(port)) {
	        serverSocket.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	        return false;
	    }
	}

	


	 @Override
	public void surplusRecord(SurplusRequest request, StreamObserver<OrderAcknowledge> responseObserver) {
	    OrderAcknowledge response = OrderAcknowledge.newBuilder()
	        //.setMessage("Surplus recorded")
	        //.build();
	    		.setAccept(true)
	    		.setMessage("Surplus request received for food type: " + request.getFoodType()).build();
	    responseObserver.onNext(response);
	    responseObserver.onCompleted();
	}
	
	

	
	}


