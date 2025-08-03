package grpc.foodaid.Order;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

import grpc.foodaid.Order.OrderServiceGrpc.OrderServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
//import io.grpc.stub.StreamObserver;

public class OrderServer extends OrderServiceGrpc.OrderServiceImplBase {

	private static final Logger logger = Logger.getLogger(OrderServer.class.getName());

	public static void main(String[] args) {

		// ANSI escape code for red text
		String redText = "\u001B[31m";
		// ANSI escape code to reset text color
		String resetText = "\u001B[0m";

		OrderServer OrderService = new OrderServer();

		int port = 50051;

		if (isPortAvailable(port)) {
			System.out.println("Port " + port + " is available.");

			try {

				System.out.println("Starting server...");
				Server server = ServerBuilder.forPort(port).addService(OrderService).build().start();
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
	public void placeOrder(OrderRequest request, StreamObserver<OrderConfirmation> responseObserver) {
		// Build response
		OrderConfirmation confirmation = OrderConfirmation.newBuilder().setConfirmed(true)
				.setMessage("Order placed successfully").build();
		responseObserver.onNext(confirmation);
		responseObserver.onCompleted();
	}

	@Override
	public void streamOrderUpdates(OrderStatusRequest request, StreamObserver<OrderStatusUpdate> responseObserver) {

		String[] orderStatus = { "Order Placed", "Order Confirmed", "Processing Order", "Order Shipped",
				"Order Delivered" };

		// Simulate streaming orders
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000); // Simulate delay
			} catch (InterruptedException e) {
				logger.severe("Stream interrupted: " + e.getMessage());
				responseObserver.onError(e);
				return;
			}

			OrderStatusUpdate update = OrderStatusUpdate.newBuilder().setOrderId(request.getOrderId())
					.setStatus("Order Status: " + orderStatus[i]).build();

			responseObserver.onNext(update);
		}
	}
}
