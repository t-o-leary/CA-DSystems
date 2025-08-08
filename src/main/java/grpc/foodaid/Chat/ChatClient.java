/*------------------------------------------------------------------------------------------------------------------
 * ChatClient class
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
 * This class registers the Surplus gRPC service on the local network using jmDNS for service discovery. 
 * It creates a jmDNS instance, registers the service with a specified name and port, logs the registration status, 
 * and keeps the service available for discovery for a set duration. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Chat;

import io.grpc.stub.StreamObserver;
import grpc.foodaid.Chat.ChatServiceGrpc.ChatServiceStub;
import io.grpc.ManagedChannel;

import java.util.LinkedHashSet;
import java.util.logging.Logger;

public class ChatClient extends ChatServiceGrpc.ChatServiceImplBase {

	private ManagedChannel channel;
	public ChatServiceStub chatStub;
	private static final Logger logger = Logger.getLogger(ChatServer.class.getName());

	private static LinkedHashSet<StreamObserver<ChatResponse>> observers = new LinkedHashSet<>();

	public ChatClient(String host, int port) {
		try {
			this.channel = io.grpc.ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();// Create a gRPC
																										// channel
			this.chatStub = ChatServiceGrpc.newStub(channel);// Create a stub for the Chat service
			logger.info("Chat Client is starting...");
		} catch (Exception e) {
			logger.severe("Failed to start Chat Client: " + e.getMessage());// Log any errors during client
																			// initialization

		}
	}

	@Override
	public StreamObserver<ChatRequest> liveChat(StreamObserver<ChatResponse> responseObserver) {

		observers.add(responseObserver);
		return new StreamObserver<ChatRequest>() {
			@Override
			public void onNext(ChatRequest request) {
				String message = request.getMessage();
				System.out.println("Received message: " + message);
				logger.info("Received message: " + message);

				// Create a response
				ChatResponse response = ChatResponse.newBuilder().setResponseMessage("Echo: " + message).build();

				responseObserver.onNext(response); // Send the response back to the client
			}

			@Override
			public void onError(Throwable t) {
				System.err.println("Error occurred: " + t.getMessage());
				logger.severe("Error occurred: " + t.getMessage());
				observers.remove(responseObserver);
			}

			@Override
			public void onCompleted() {
				System.out.println("Chat session completed.");
				logger.info("Chat session completed.");
				observers.remove(responseObserver);
				responseObserver.onCompleted(); // Signal that the chat session is complete
			}
		};
	}

	public void sendMessage(ChatRequest request, StreamObserver<ChatResponse> responseObserver) {
		try {
			chatStub.liveChat(responseObserver).onNext(request); // Send the ChatRequest to the server
		} catch (Exception e) {
			logger.severe("Failed to send message: " + e.getMessage());// Log any errors during message sending
			responseObserver.onError(e);
		}
	}

	public void shutdown() {
		if (channel != null) {// Check if the channel is not null
			try {
				channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);// Shutdown the channel
																								// gracefully
			} catch (InterruptedException e) {
				logger.severe("Channel shutdown interrupted: " + e.getMessage());// Log any errors during shutdown
				Thread.currentThread().interrupt();// Restore the interrupted status
			}
		}
	}
}

/*
 * 
 * public ChatClient(String host, int port) { this.channel =
 * ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
 * this.chatStub =ChatServiceGrpc.newStub(channel);
 * System.out.println("Chat Client is starting..."); }
 * 
 * 
 * public static void main(String host, int port) throws InterruptedException {
 * // Start the Chat client here. // This will involve setting up the gRPC
 * channel, // creating a stub for the Chat service, // and sending messages to
 * the server.
 * 
 * 
 * 
 * 
 * // ManagedChannel channel = ManagedChannelBuilder.forAddress(host,
 * port).usePlaintext().build();
 * 
 * 
 * 
 * 
 * //ChatServiceGrpc.ChatServiceStub chatStub =
 * ChatServiceGrpc.newStub(channel);
 * 
 * 
 * ChatClient chatClient = new ChatClient(host, port); ChatServiceStub chatStub
 * = chatClient.chatStub; ChatRequest request =
 * ChatRequest.newBuilder().setSenderName("User1")
 * .setMessage("Hello, this is a test message!").build();
 * 
 * 
 * //chatStub.liveChat(new StreamObserver<ChatResponse>() {
 * StreamObserver<ChatResponse> responseObserver = new
 * StreamObserver<ChatResponse>() {
 * 
 * @Override public void onNext(ChatResponse response) {
 * System.out.println("Received response: " + response.getResponseMessage());
 * logger.info("Received response: " + response.getResponseMessage()); }
 * 
 * @Override public void onError(Throwable t) {
 * System.err.println("Error occurred: " + t.getMessage()); }
 * 
 * @Override public void onCompleted() {
 * System.out.println("Message sending completed."); } };
 * 
 * 
 * StreamObserver<ChatRequest> requestObserver =
 * chatStub.liveChat(responseObserver); requestObserver.onNext(request); // Send
 * your ChatRequest requestObserver.onCompleted(); // Signal end of requests
 * 
 * } }
 * 
 * /* // Example usage of the ChatClient ChatClient chatClient = new
 * ChatClient();
 * 
 * // Create a request ChatRequest request =
 * ChatRequest.newBuilder().setMessage("Hello, World!").build();
 * 
 * // Send the message chatClient.sendMessage(request, new
 * StreamObserver<ChatResponse>() {
 * 
 * @Override public void onNext(ChatResponse response) {
 * System.out.println("Received response: " + response.getResponseMessage()); }
 * 
 * @Override public void onError(Throwable t) {
 * System.err.println("Error occurred: " + t.getMessage()); }
 * 
 * @Override public void onCompleted() {
 * System.out.println("Message sending completed."); } });
 * 
 * } }
 * 
 * 
 * 
 * @Override public void sendMessage(ChatRequest request,
 * StreamObserver<ChatResponse> responseObserver) { // Implement the logic to
 * send a chat message to the server String message = request.getMessage(); //
 * Process the message (e.g., send it to the server, display it, etc.)
 * 
 * // Create a response ChatResponse response =
 * ChatResponse.newBuilder().setResponseMessage("Sent: " + message).build();
 * 
 * // Send the response back to the server responseObserver.onNext(response);
 * responseObserver.onCompleted(); }
 * 
 * }
 */