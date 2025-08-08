/*------------------------------------------------------------------------------------------------------------------
 * DeliveryClient class
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
 * The DeliveryClient class acts as a gRPC client for the Delivery service, enabling the submission of delivery orders
 * and handling delivery plans via asynchronous streaming. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Deliver;

//Import libraries 
import io.grpc.ManagedChannel;// Import the gRPC ManagedChannel class for communication
import io.grpc.ManagedChannelBuilder;// Import the gRPC ManagedChannelBuilder for building channels
import io.grpc.StatusRuntimeException;// Import the gRPC StatusRuntimeException for error handling
import io.grpc.stub.StreamObserver;// Import the gRPC StreamObserver for handling asynchronous responses	
import java.util.concurrent.TimeUnit;// Import the Java TimeUnit class for managing time-related operations
import java.util.logging.Logger;

import grpc.foodaid.Surplus.SurplusServer;

public class DeliveryClient {

	private static final Logger logger = Logger.getLogger(SurplusServer.class.getName()); // logging event
	private final ManagedChannel channel;// ManagedChannel is used to connect to the gRPC server
	public final DeliveryServiceGrpc.DeliveryServiceStub asyncStub;// DeliveryServiceStub is the asynchronous stub for
																	// the Delivery service

	public DeliveryClient(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();// Create a channel to the
																							// gRPC server using the
																							// specified host and port
		this.asyncStub = DeliveryServiceGrpc.newStub(channel);// Create an asynchronous stub for the Delivery service
																// using the channel
	}

	public void submitDeliveries() {
		try {
			StreamObserver<DeliveryOrder> requestObserver = asyncStub // Create a StreamObserver to handle the delivery
																		// orders
					.submitDeliveries(new StreamObserver<DeliveryPlan>() {// Implement the StreamObserver for
																			// DeliveryPlan responses
						@Override
						public void onNext(DeliveryPlan value) {

							logger.info("Received DeliveryPlan: " + value);// Log the received DeliveryPlan

						}

						@Override
						public void onError(Throwable t) {
							logger.severe("Error in stream: " + t.getMessage());// Log any errors that occur during the
																				// streaming process
						}

						@Override
						public void onCompleted() {
							logger.info("Stream completed successfully.");// Log when the stream is completed
																			// successfully
						}
					});

			// sample delivery orders to be submitted
			DeliveryOrder order1 = DeliveryOrder.newBuilder().setRecipient("John Doe")
					.setDeliveryAddress("123 Main St, Dublin").setFoodType("Vegetables").setQuantity(100).build();

			DeliveryOrder order2 = DeliveryOrder.newBuilder().setRecipient("Jane Smith")
					.setDeliveryAddress("456 Oak Ave, Cork").setFoodType("Fruit").setQuantity(50).build();

			requestObserver.onNext(order1);// Send the first delivery order to the server
			requestObserver.onNext(order2);// Send the second delivery order to the server

			requestObserver.onCompleted();
		} catch (StatusRuntimeException ex) {
			logger.severe("gRPC Status error: " + ex.getStatus().getDescription());// Log gRPC status errors
		} catch (Exception ex) {
			logger.severe("Unexpected error: " + ex.getMessage());// Log unexpected errors
		} finally {// Ensure that the channel is shut down gracefully
			try {
				channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);// Shutdown the channel and wait for
																			// termination
			} catch (InterruptedException ignored) {// Ignore any interruption exceptions during shutdown
				logger.warning("Channel shutdown interrupted: " + ignored.getMessage());
			}
		}
	}
}
