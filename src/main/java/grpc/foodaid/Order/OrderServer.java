/*------------------------------------------------------------------------------------------------------------------
 * OrderServer class
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
 * The OrderServer class implements the gRPC Order service for handling order placement
 * and streaming order status updates. It starts a gRPC server on a specified port,
 * provides methods for placing orders and streaming updates.
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Order;

// import libraries
import java.io.IOException; // for IOException
import java.net.ServerSocket; // for ServerSocket
import java.util.logging.Logger; // for logging

import io.grpc.Server; // for gRPC Server
import io.grpc.ServerBuilder; // for building the gRPC Server
import io.grpc.stub.StreamObserver; // for StreamObserver

public class OrderServer extends OrderServiceGrpc.OrderServiceImplBase {

	private static final Logger logger = Logger.getLogger(OrderServer.class.getName()); // Logger for logging messages

	public static void main(String[] port) {

		OrderServer OrderService = new OrderServer();// Create an instance of the OrderServer class

		int iPort; // Variable to hold the port number

		if (port[0] == null || port[0] == "0" || port.length == 0) { // Check if port is not specified or is invalid

			iPort = 50052;

			logger.info("No port specified, using default port: " + iPort); // Log the default port usage

		} else {
			iPort = Integer.parseInt(port[0]);
			logger.info("Using specified port: " + iPort); // Log the specified port usage
		}

		if (isPortAvailable(iPort)) {

			logger.info("Port " + iPort + " is available, starting server..."); // Log if the port is available

			try {

				logger.info("Starting server on port " + iPort); // Log the server start
				Server server = ServerBuilder.forPort(iPort).addService(OrderService).build().start();// Build and start
																										// the gRPC
																										// server

				logger.info("Server started, listening on " + iPort);// Log the server start confirmation
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

			logger.info("Server started, listening on " + iPort);

		} else {

			logger.warning("Port " + iPort + " is already in use. Please choose a different port."); // Log if the port
																										// is already in
																										// use
			// Log if the port is already in use
		}

	}

	public static boolean isPortAvailable(int iPort) {
		try (ServerSocket serverSocket = new ServerSocket(iPort)) { // Try to create a ServerSocket on the specified
																	// port
			serverSocket.setReuseAddress(true);// Allow address reuse
			return true;
		} catch (IOException e) {// If an IOException occurs, the port is not available
			return false;
		}
	}

	@Override
	public void placeOrder(OrderRequest request, StreamObserver<OrderConfirmation> responseObserver) {// Method to
																										// handle order
																										// placement
		try {

			OrderConfirmation confirmation = OrderConfirmation.newBuilder().setConfirmed(true)// Create an
																								// OrderConfirmation
																								// response
					.setMessage("Order placed successfully").build();
			responseObserver.onNext(confirmation);// Send the confirmation response to the client
			responseObserver.onCompleted();// Complete the response stream
		} catch (Exception e) {
			logger.severe("Error placing order: " + e.getMessage());// Log any exception that occurs during order
																	// placement
			responseObserver.onError(io.grpc.Status.INTERNAL.withDescription("Failed to place order: " + e.getMessage())// Create
																														// an
																														// error
																														// response
					.withCause(e).asRuntimeException());
		}
	}

	@Override
	public void streamOrderUpdates(OrderStatusRequest request, StreamObserver<OrderStatusUpdate> responseObserver) {// Method
																													// to
																													// stream
																													// order
																													// updates
		try {
			String[] orderStatus = { "Order Placed", "Order Confirmed", "Processing Order", "Order Shipped", // Array of
																												// order
																												// status
																												// messages
					"Order Delivered" };
			for (int i = 0; i < 5; i++) { // Loop through the order status messages
				Thread.sleep(1000);// Sleep for 1 second to simulate delay in updates
				OrderStatusUpdate update = OrderStatusUpdate.newBuilder().setOrderId(request.getOrderId())// Create an
																											// OrderStatusUpdate
																											// response
						.setStatus(orderStatus[i]).build();
				responseObserver.onNext(update);// Send the update to the client
			}
			responseObserver.onCompleted();// Complete the response stream
		} catch (InterruptedException e) {
			logger.severe("Stream interrupted: " + e.getMessage());// Log if the stream is interrupted
			responseObserver.onError(io.grpc.Status.CANCELLED.withDescription("Streaming interrupted").withCause(e)
					.asRuntimeException());// Create an error response for interrupted stream
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			logger.severe("Error streaming order updates: " + e.getMessage());// Log any exception that occurs during
																				// streaming
			responseObserver.onError(
					io.grpc.Status.INTERNAL.withDescription("Failed to stream order updates: " + e.getMessage())
							.withCause(e).asRuntimeException()); // Create an error response for failed streaming
		}
	}
}
