/*------------------------------------------------------------------------------------------------------------------
 * ChatServer class
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
 * This class implements a gRPC-based chat server for the Surplus Food Aid system.
 * It provides real-time chat functionality using bidirectional streaming, allowing multiple clients 
 * to send and receive messages.
 *  
------------------------------------------------------------------------------------------------------------------*/
package grpc.foodaid.Chat;

// Import necessary libraries
import java.io.IOException;// Import for IO operations
import java.net.ServerSocket;// Import for server socket operations

import java.util.LinkedHashSet;// Import for managing connected clients
import java.util.logging.Logger;// Import for logging

import grpc.foodaid.Chat.ChatServer;// Import for Chat server
import io.grpc.stub.StreamObserver;// Import for gRPC stream observer

public class ChatServer extends ChatServiceGrpc.ChatServiceImplBase {// This class extends the generated gRPC service base class

	private static LinkedHashSet<StreamObserver<ChatResponse>> observers = new LinkedHashSet<>();// Set to hold connected clients
	private static final Logger logger = Logger.getLogger(ChatServer.class.getName());// Logger for logging server events

	public static void main(String[] port) {//	 Main method to start the Chat server
		int iPort;
		try {
			if (port.length == 0 || port[0] == null || port[0].equals("0")) {// Check if a port is specified
				iPort = 50054;
				Logger.getLogger(ChatServer.class.getName()).info("No port specified, using default port: " + iPort);// Default port
				
			} else {
				iPort = Integer.parseInt(port[0]);
				Logger.getLogger(ChatServer.class.getName()).info("Using specified port: " + iPort);// Port specified by user
			}

			if (isPortAvailable(iPort)) {
				Logger.getLogger(ChatServer.class.getName()).info("Port " + iPort + " is available, starting server...");// Check if port is available
				try {
					logger.info("Starting Chat server...");// Log server start
					io.grpc.Server server = io.grpc.ServerBuilder.forPort(iPort).addService(new ChatServer()).build()
							.start();// Build and start the gRPC server
					logger.info("Chat Server started on port " + iPort);// Log server start success
					server.awaitTermination();// Wait for server termination
				} catch (Exception e) {
					logger.severe("Failed to start server: " + e.getMessage());
					e.printStackTrace();// Print stack trace for debugging
				}
			} else {
				String msg = "Port " + iPort + " is already in use.";
				System.out.println(msg);
				logger.severe(msg);
			}
		} catch (Exception ex) {
			logger.severe("Error in main: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static boolean isPortAvailable(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {// Attempt to bind to the specified port
			serverSocket.setReuseAddress(true);// Allow address reuse
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public StreamObserver<ChatRequest> liveChat(StreamObserver<ChatResponse> responseObserver) {// This method handles live chat functionality
		observers.add(responseObserver);// Add the response observer to the set of observers
		return new StreamObserver<ChatRequest>() {// Create a new StreamObserver for handling incoming chat requests
			@Override
			public void onNext(ChatRequest request) {//	 This method is called when a new ChatRequest is received
				try {
					logger.info("Received message from " + request.getSenderName());// Log the sender's name
					ChatResponse response = ChatResponse.newBuilder()// Create a new ChatResponse
							.setResponseMessage(request.getSenderName() + ":  " + request.getMessage()).build();// Build the response message
					for (StreamObserver<ChatResponse> observer : observers) {// For all connected observers
						try {
							observer.onNext(response);// Send the response to each observer
						} catch (Exception e) {
							observers.remove(observer);// Remove observer if sending fails
							logger.warning("Failed to send message to an observer: " + e.getMessage());// Log the failure
						}
					}
				} catch (Exception ex) {
					logger.severe("Error processing chat message: " + ex.getMessage());// Log any errors that occur while processing the chat message
					responseObserver.onError(io.grpc.Status.INTERNAL.withDescription("Server error: " + ex.getMessage())// Create an error status
							.asRuntimeException());// Send the error status to the response observer
				}
			}

			@Override
			public void onError(Throwable t) {
				observers.remove(responseObserver);// Remove the observer if an error occurs
				logger.warning("Client error: " + t.getMessage());// Log the error message
			}

			@Override
			public void onCompleted() {
				observers.remove(responseObserver);// Remove the observer when the chat session is completed
				responseObserver.onCompleted();// Signal that the chat session is complete
			}
		};
	}

}