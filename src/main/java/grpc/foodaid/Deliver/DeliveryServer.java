/*------------------------------------------------------------------------------------------------------------------
 * DeliveryServer class
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
 * The DeliveryServer class implements the gRPC server for the Deliver service, handling incoming delivery order streams
 * and responding with delivery plans. 
 *  
------------------------------------------------------------------------------------------------------------------*/
package grpc.foodaid.Deliver;

//// Import necessary libraries
import java.io.IOException;// Import the IOException class for handling input/output exceptions
import java.net.ServerSocket;// Import the ServerSocket class for creating a server socket
import java.util.ArrayList;// Import the ArrayList class for managing lists of delivery orders
import java.util.List;// Import the List interface for managing lists of delivery orders
import java.util.logging.Logger;// Import the Logger class for logging

import io.grpc.Server;//	 Import the Server class for creating a gRPC server
import io.grpc.ServerBuilder;// Import the ServerBuilder class for building a gRPC server
import io.grpc.stub.StreamObserver;//	// Import the StreamObserver interface for handling asynchronous streaming

public class DeliveryServer extends DeliveryServiceGrpc.DeliveryServiceImplBase {// Extend the generated
																					// DeliveryServiceImplBase class to
																					// implement the Deliver service

	private static final Logger logger = Logger.getLogger(DeliveryServer.class.getName());// Create a Logger instance
																							// for logging events

	public static void main(String[] port) {

		DeliveryServer DeliverService = new DeliveryServer();

		int iPort;

		if (port[0] == null || port[0] == "0" || port.length == 0) {// Check if the port is not specified or is zero

			iPort = 50052;
			Logger.getLogger(DeliveryServer.class.getName()).info("No port specified, using default port: " + iPort);// Log
																														// a
																														// message
																														// indicating
																														// the
																														// default
																														// port
																														// is
																														// being
																														// used
		} else {
			iPort = Integer.parseInt(port[0]);// Parse the specified port from the command line arguments
			Logger.getLogger(DeliveryServer.class.getName()).info("Using specified port: " + iPort);// Log a message
																									// indicating the
																									// specified port is
																									// being used
		}

		if (isPortAvailable(iPort)) {
			Logger.getLogger(DeliveryServer.class.getName()).info("Port " + iPort + " is available");// Log a message
																										// indicating
																										// the port is
																										// available

			try {

				Logger.getLogger(DeliveryServer.class.getName()).info("Starting server on port " + iPort);// Log a
																											// message
																											// indicating
																											// the
																											// server is
																											// starting
				Server server = ServerBuilder.forPort(iPort).addService(DeliverService).build().start();
				Logger.getLogger(DeliveryServer.class.getName()).info("Server started on port " + iPort);// Log a
																											// message
																											// indicating
																											// the
																											// server
																											// has
																											// started

				server.awaitTermination();// Wait for the server to terminate

			} catch (IOException e) {

				// Log the exception
				logger.severe("Failed to start server: " + e.getMessage() + "\n");

				// Print the stack trace for debugging
				e.printStackTrace();

			} catch (InterruptedException e) {
				// Log the exception
				logger.severe("Server interrupted: " + e.getMessage());
				// Print the stack trace for debugging
				e.printStackTrace();
			}

		} else {
			Logger.getLogger(DeliveryServer.class.getName()).severe("Port " + iPort + " is already in use");// Log a
																											// message
																											// indicating
																											// the port
																											// is
																											// already
																											// in use
		}

	}

	public static boolean isPortAvailable(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {// Create a ServerSocket to check if the port is
																	// available
			serverSocket.setReuseAddress(true);// Set the reuse address option to true to allow the port to be reused
			return true;// Return true if the port is available
		} catch (IOException e) {
			return false;// Return false if an IOException occurs, indicating the port is not available
		}
	}

	@Override
	public StreamObserver<DeliveryOrder> submitDeliveries(final StreamObserver<DeliveryPlan> responseObserver) { // Override
																													// the
																													// submitDeliveries
																													// method
																													// to
																													// handle
																													// incoming
																													// delivery
																													// orders
		return new StreamObserver<DeliveryOrder>() {// Implement the StreamObserver interface to handle incoming
													// DeliveryOrder messages
			List<DeliveryOrder> orders = new ArrayList<>();// Create a list to store incoming delivery orders

			@Override
			public void onNext(DeliveryOrder order) {
				try {
					orders.add(order);// Add the incoming delivery order to the list
					logger.info("Received order: " + order.getRecipient());// Log the received delivery order
				} catch (Exception e) {
					logger.severe("Error processing order: " + e.getMessage());// Log any exceptions that occur while
																				// processing the order
					responseObserver.onError(io.grpc.Status.INTERNAL.withDescription("Server error processing order")
							.withCause(e).asRuntimeException());// Send an error response to the client if an exception
																// occurs
				}
			}

			@Override
			public void onError(Throwable t) {
				logger.severe("Error receiving orders: " + t.getMessage());// Log any errors that occur while receiving
																			// orders
			}

			@Override
			public void onCompleted() {// Called when all delivery orders have been received
				try {
					DeliveryPlan.Builder planBuilder = DeliveryPlan.newBuilder();// Create a new DeliveryPlan builder
					for (DeliveryOrder order : orders) {
						planBuilder.addOrders(order);// Add each received delivery order to the DeliveryPlan
					}
					planBuilder.setTotalDeliveries(orders.size());// Set the total number of deliveries in the
																	// DeliveryPlan
					planBuilder.setDeliveryAgent("Agent007");// Set the delivery agent for the DeliveryPlan
					planBuilder.setStatus("All deliveries received and planned.");
					logger.info("Sending DeliveryPlan: " + planBuilder.build());
					responseObserver.onNext(planBuilder.build());// Send the constructed DeliveryPlan back to the client
					responseObserver.onCompleted();
				} catch (Exception e) {
					logger.severe("Error building/sending DeliveryPlan: " + e.getMessage());// Log any exceptions that
																							// occur while building or
																							// sending the DeliveryPlan
					responseObserver.onError(io.grpc.Status.INTERNAL
							.withDescription("Server error building delivery plan").withCause(e).asRuntimeException());
				}
			}
		};
	}
}