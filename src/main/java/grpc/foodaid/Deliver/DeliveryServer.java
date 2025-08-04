package grpc.foodaid.Deliver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import grpc.foodaid.Deliver.DeliveryServiceGrpc.DeliveryServiceImplBase;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

// This file is part of the FoodAid project.
//
public class DeliveryServer extends DeliveryServiceGrpc.DeliveryServiceImplBase {

	private static final Logger logger = Logger.getLogger(DeliveryServer.class.getName());

	// This class will handle the server-side logic for the Deliver service.
	// It will implement the methods defined in the Deliver service interface.
	// The server will listen for incoming requests and respond accordingly.
	// The implementation will include methods for handling delivery requests,
	// updating delivery statuses, and managing delivery-related data.
	// Future enhancements may include integrating with a database for persistent
	// storage,
	// adding authentication and authorization, and implementing error handling.
	// implementing authentication and authorization, and adding error handling.
	// The server will be built using gRPC, which allows for efficient communication
	// between the client and server over the network.
	// The DeliverServer will be responsible for processing delivery requests,
	// managing delivery statuses, and providing updates to clients.
	public static void main(String[] args) {
		// Start the Deliver server here.
		// This will involve setting up the gRPC server,
		// registering the Deliver service implementation,
		// and starting the server to listen for incoming requests.
		// System.out.println("Deliver Server is starting...");

		// public static void main(String[] args) {

		// ANSI escape code for red text
		String redText = "\u001B[31m";
		// ANSI escape code to reset text color
		String resetText = "\u001B[0m";

		DeliveryServer DeliverService = new DeliveryServer();

		int port = 50052;

		if (isPortAvailable(port)) {
			System.out.println("Port " + port + " is available.");

			try {

				System.out.println("Starting server...");
				Server server = ServerBuilder.forPort(port).addService(DeliverService).build().start();
				System.out.println("Server started on port " + port);
				logger.info("Server started, listening on " + port);
				server.awaitTermination();

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

			logger.info("Server started, listening on " + port);

		} else {
			System.out.println(redText + "Port " + port + " is already in use." + resetText);
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
	    public StreamObserver<DeliveryOrder> submitDeliveries(final StreamObserver<DeliveryPlan> responseObserver) {
	        return new StreamObserver<DeliveryOrder>() {
	            List<DeliveryOrder> orders = new ArrayList<>();

	            @Override
	            public void onNext(DeliveryOrder order) {
	                orders.add(order);
	                logger.info("Received order: " + order.getRecipient());
	            }

	            @Override
	            public void onError(Throwable t) {
	                logger.severe("Error receiving orders: " + t.getMessage());
	            }

	            @Override
	            public void onCompleted() {
	                // Build a DeliveryPlan response
	                DeliveryPlan.Builder planBuilder = DeliveryPlan.newBuilder();
	                for (DeliveryOrder order : orders) {
	                    planBuilder.addOrders(order);
	                }
	                planBuilder.setTotalDeliveries(orders.size());
	                planBuilder.setDeliveryAgent("Agent007"); // Example agent)
	                planBuilder.setStatus("All deliveries received and planned.");
	                responseObserver.onNext(planBuilder.build());
	                responseObserver.onCompleted();
	            }
	        };
	    }
	}