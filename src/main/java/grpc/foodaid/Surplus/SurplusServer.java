/*------------------------------------------------------------------------------------------------------------------
 * SurplusServer class
 * 
 * Author Tim O’Leary
 * Student ID: 23287021 
 * Version 1.0
 * Program: Higher Diploma in Science in Computing
 * Module: Distributed Systems  (HDSDEV_JAN25)
 * Lecturer: Sheresh Zahoor
 * Project: CA
 * Submission Date 08-August-2025
 * 
 * This class implements a gRPC server for handling surplus food records. 
 * It starts the server,  checks port availability and logs server status. 
 *
 * It processes incoming requests, performs input validation, handles client cancellations, 
 * and returns appropriate gRPC statuses or responses.
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Surplus;


// Import libraries 
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

import io.grpc.Context; // Import gRPC Context for cancellation handling
import io.grpc.Server; // Import gRPC Server for creating the server
import io.grpc.ServerBuilder; // Import gRPC ServerBuilder for building the server
import io.grpc.Status; // Import gRPC Status for error handling
import io.grpc.stub.StreamObserver; // Import gRPC StreamObserver for handling responses

public class SurplusServer extends SurplusServiceGrpc.SurplusServiceImplBase {
	private static final Logger logger = Logger.getLogger(SurplusServer.class.getName()); // logging event

	public static String main(String[] port) { // Main method to start the server

		
		String returnServer = "";

		SurplusServer SurplusService = new SurplusServer();// Create an instance of SurplusServer

		int iPort;

		if (port[0] == null || port[0] == "0" || port.length == 0) {// Check if port is specified

			iPort = 50051;

			Logger.getLogger(SurplusServer.class.getName()).info("No port specified, using default port: " + iPort); // Log the default port usage
			
		} else {
			iPort = Integer.parseInt(port[0]);
			
			Logger.getLogger(SurplusServer.class.getName()).info("Using specified port: " + iPort); // Log the specified port usage
		}

		if (isPortAvailable(iPort)) { // Check if the port is available
			
			Logger.getLogger(SurplusServer.class.getName()).info("Port " + iPort + " is available."); // Log the port availability
			

			try {

				Logger.getLogger(SurplusServer.class.getName()).info("Starting server on port " + iPort); // Log the server startup
				
				Server server = ServerBuilder.forPort(iPort).addService(SurplusService).build().start();
				logger.info("Server started, listening on " + iPort); // Log the server start
				server.awaitTermination();// Wait for the server to terminate	
				
				
			} catch (IOException e) {

				returnServer = "Failed to start server: ";// Prepare the error message
				// Log the exception
				logger.severe(returnServer + e.getMessage() + "\n");

				// Print the stack trace for debugging
				e.printStackTrace();

			} catch (InterruptedException e) {
				// Log the exception
				logger.severe("Server interrupted: " + e.getMessage());
				// Print the stack trace for debugging
				e.printStackTrace();
			}
			returnServer = "Server started on port " + iPort;// Prepare the success message

			logger.info(returnServer);// Log the server start message

		} else {

			returnServer = "Port " + iPort + " is already in use."; // Prepare the error message

			Logger.getLogger(SurplusServer.class.getName()).warning(returnServer); // Log the port in use warning
		}
		return returnServer;

	}

	public static boolean isPortAvailable(int iPort) { // Method to check if a port is available
		try (ServerSocket serverSocket = new ServerSocket(iPort)) {// Attempt to bind to the port
			serverSocket.setReuseAddress(true);// Allow address reuse
			return true;
		} catch (IOException e) {// If an IOException occurs, the port is not available
			return false;
		}
	}

	@Override
	public void surplusRecord(SurplusRequest request, StreamObserver<SurplusAcknowledge> responseObserver) {// Override the surplusRecord method to handle incoming requests
		try {
			// Check for cancellation
			if (Context.current().isCancelled()) {
				responseObserver.onError(
						Status.CANCELLED.withDescription("Request was cancelled by client").asRuntimeException());// Handle client cancellation
				return;
			}

			// Input validation
			if (request.getFoodType() == null || request.getFoodType().isEmpty()) {
				responseObserver.onError(
						Status.INVALID_ARGUMENT.withDescription("Food type must be provided").asRuntimeException()); // Validate food type
				return;
			}

			// Process the surplus record request
			SurplusAcknowledge response = SurplusAcknowledge.newBuilder().setAccept(true) 
					.setMessage("Surplus request received for food type: " + request.getFoodType()).build(); // Create a response message
			responseObserver.onNext(response); 
			responseObserver.onCompleted(); // Complete the response
		} catch (Exception e) {
			responseObserver.onError(
					Status.INTERNAL.withDescription("Internal server error: " + e.getMessage()).asRuntimeException()); // Handle any exceptions and return an error
		}
	}
}
