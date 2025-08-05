package grpc.foodaid.Surplus;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

import grpc.foodaid.Surplus.SurplusServiceGrpc.SurplusServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
//import io.grpc.stub.StreamObserver;

public class SurplusServer extends SurplusServiceGrpc.SurplusServiceImplBase {
	private static final Logger logger = Logger.getLogger(SurplusServer.class.getName());

	public static String main(String[] port) {

		// ANSI escape code for red text
		String redText = "\u001B[31m";
		// ANSI escape code to reset text color
		String resetText = "\u001B[0m";
		String returnServer = "";

		SurplusServer SurplusService = new SurplusServer();

		int iPort ;
		
		if (port[0] == null || port[0] == "0" || port.length == 0 ) {
	          
			 iPort = 50051;
			 
			 System.out.println("No port specified, using default port: " + port);
			} else {
				iPort = Integer.parseInt(port[0]);
				System.out.println("Using specified port: " + iPort);
			}

		if (isPortAvailable(iPort)) {
			System.out.println("Port " + iPort + " is available.");

			try {

				System.out.println("Starting server...");
				Server server = ServerBuilder.forPort(iPort).addService(SurplusService).build().start();
				System.out.println("Server started on port " + iPort);
				logger.info("Server started, listening on " + iPort);
				server.awaitTermination();

			} catch (IOException e) {

				returnServer = "Failed to start server: ";
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
			returnServer = "Server started on port " + iPort;

			logger.info(returnServer);

		} else {

			returnServer = "Port " + iPort + " is already in use.";

			System.out.println(redText + returnServer + resetText);
		}
		return returnServer;

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
	public void surplusRecord(SurplusRequest request, StreamObserver<SurplusAcknowledge> responseObserver) {
		SurplusAcknowledge response = SurplusAcknowledge.newBuilder()

				.setAccept(true).setMessage("Surplus request received for food type: " + request.getFoodType()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

}
