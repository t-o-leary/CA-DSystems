/*------------------------------------------------------------------------------------------------------------------
 * SurplusClient class
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
 * This class implements a gRPC client for sending surplus food records to the server. It establishes a 
 * connection to the gRPC server, builds and sends a SurplusRequest, and receives a SurplusAcknowledge response. 
 *  
------------------------------------------------------------------------------------------------------------------*/



package grpc.foodaid.Surplus;

// 	Import libraries
import io.grpc.ManagedChannel;// Import gRPC ManagedChannel for communication
import io.grpc.ManagedChannelBuilder;// Import gRPC ManagedChannelBuilder for building the channel
import java.util.concurrent.TimeUnit;// Import TimeUnit for managing time-related operations
import java.util.logging.Logger;

public class SurplusClient {

	private final ManagedChannel channel; // gRPC channel for communication with the server
	private final SurplusServiceGrpc.SurplusServiceBlockingStub blockingStub; // gRPC blocking stub for synchronous communication

	public SurplusClient(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();// Create a channel to the server using the specified host and port
		this.blockingStub = SurplusServiceGrpc.newBlockingStub(channel); // Create a blocking stub for synchronous communication with the server
	}

	public SurplusAcknowledge recordSurplus(SurplusRequest request) { 
		return blockingStub.surplusRecord(request); // Send a SurplusRequest to the server and receive a SurplusAcknowledge response
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS); // Shutdown the channel and wait for termination
	}

	public static void main(String[] args) throws Exception {
		SurplusClient client = new SurplusClient("localhost", 50051);	// Create a new SurplusClient instance with the server address and port
		try {
			// Build a sample SurplusRequest
			SurplusRequest request = SurplusRequest.newBuilder()
					.setFoodType("Vegetables").setQuantityFood(100)
					.setNutritionalGrade(7).setLocation("Dublin").setDepo(2).build();

			SurplusAcknowledge response = client.recordSurplus(request); // Send the request to the server and receive the response
			Logger.getLogger(SurplusClient.class.getName()).info("Surplus recorded: " + response.getMessage()); // Log the response message
		} finally {
			client.shutdown();// Shutdown the client channel
		}
	}
}