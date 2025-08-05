package grpc.foodaid.Order;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
//import io.grpc.stub.StreamObserver;

public class OrderServer extends OrderServiceGrpc.OrderServiceImplBase {

	private static final Logger logger = Logger.getLogger(OrderServer.class.getName());

	public static void main(String[] port) {

		// ANSI escape code for red text
		String redText = "\u001B[31m";
		// ANSI escape code to reset text color
		String resetText = "\u001B[0m";

		OrderServer OrderService = new OrderServer();

		int iPort;

		if (port[0] == null || port[0] == "0" || port.length == 0) {

			iPort = 50052;

			System.out.println("No port specified, using default port: " + iPort);
		} else {
			iPort = Integer.parseInt(port[0]);
			System.out.println("Using specified port: " + iPort);
		}

		if (isPortAvailable(iPort)) {
			System.out.println("Port " + iPort + " is available.");

			try {

				System.out.println("Starting server...");
				Server server = ServerBuilder.forPort(iPort).addService(OrderService).build().start();
				System.out.println("Server started on port " + iPort);
				logger.info("Server started, listening on " + iPort);
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

			logger.info("Server started, listening on " + iPort);

		} else {
			System.out.println(redText + "Port " + iPort + " is already in use." + resetText);
		}

	}

	public static boolean isPortAvailable(int iPort) {
		try (ServerSocket serverSocket = new ServerSocket(iPort)) {
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
					.setStatus( orderStatus[i]).build();

			responseObserver.onNext(update);
		}
		responseObserver.onCompleted();
	}
}
