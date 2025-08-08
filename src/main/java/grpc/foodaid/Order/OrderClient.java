/*------------------------------------------------------------------------------------------------------------------
 * SurplusServiceRegistrar class
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
 * The OrderClient class acts as a gRPC client for the Order service, enabling remote order placement
 * and handling responses from the server. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Order;

// import Libraries
import java.util.concurrent.TimeUnit;// for TimeUnit

import io.grpc.ManagedChannel;// for gRPC ManagedChannel
import io.grpc.ManagedChannelBuilder;// for building the gRPC channel
import io.grpc.StatusRuntimeException;// for handling gRPC status exceptions

public class OrderClient {

	private final ManagedChannel channel; // gRPC channel for communication with the server
	private final OrderServiceGrpc.OrderServiceBlockingStub blockingStub;// Blocking stub for synchronous gRPC calls

	public OrderClient(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();// Create a gRPC channel
		this.blockingStub = OrderServiceGrpc.newBlockingStub(channel);// Create a blocking stub for the Order service
	}

	public OrderConfirmation placeOrder(OrderRequest request) {// Method to place an order
		try {
			return blockingStub.placeOrder(request);// Call the placeOrder method on the blocking stub
		} catch (StatusRuntimeException e) {// Handle gRPC status exceptions
			System.err.println("gRPC error: " + e.getStatus().getDescription());//	 Print the error message
			return OrderConfirmation.newBuilder().setConfirmed(false)// Create a new OrderConfirmation with failure status
					.setMessage("Failed to place order: " + e.getStatus().getDescription()).build();// Set the failure message
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());// Print unexpected error message
			return OrderConfirmation.newBuilder().setConfirmed(false).setMessage("Unexpected error occurred.").build();// Set a generic failure message
		}
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);// Shutdown the channel and wait for termination
	}

	public static void main(String[] x, String host, Integer port) throws Exception {
		OrderClient client = new OrderClient(host, port);// Create an instance of OrderClient with specified host and port
		try {
			OrderRequest request = OrderRequest.newBuilder().setRecipient("John Doe").setFoodType("Rice")// Create a SAMPLE OrderRequest with recipient, food type, quantity, and delivery address
					.setQuantity(10).setDeliveryAddress("123 Main St").build();

			OrderConfirmation response = client.placeOrder(request); // Place the order using the client
			System.out.println("Order confirmed: " + response.getConfirmed() + ", message: " + response.getMessage());
		} finally {
			client.shutdown();
		}
	}

}
