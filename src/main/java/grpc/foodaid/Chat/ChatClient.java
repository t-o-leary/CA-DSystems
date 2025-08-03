package grpc.foodaid.Chat;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import grpc.foodaid.Chat.ChatResponse;
import grpc.foodaid.Chat.ChatRequest;
import io.grpc.ManagedChannel;

public class ChatClient {

	// This class will handle the client-side logic for the Chat service.
	// It will implement the methods defined in the Chat service interface.
	// The client will connect to the Chat server and send/receive messages.
	// Future enhancements may include adding user authentication,
	// message history, and real-time updates.
	// The client will be built using gRPC, which allows for efficient communication
	// between the client and server over the network.

	public static void main(String[] args) throws InterruptedException {
		// Start the Chat client here.
		// This will involve setting up the gRPC channel,
		// creating a stub for the Chat service,
		// and sending messages to the server.

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50054).usePlaintext().build();
		
		System.out.println("Chat Client is starting...");
		
		
		ChatServiceGrpc.ChatServiceStub chatStub = ChatServiceGrpc.newStub(channel);

		
		chatStub.liveChat(new StreamObserver<ChatResponse>() {
					@Override
					public void onNext(ChatResponse response) {
						System.out.println("Received response: " + response.getResponseMessage());
					}

					@Override
					public void onError(Throwable t) {
						System.err.println("Error occurred: " + t.getMessage());
					}

					@Override
					public void onCompleted() {
						System.out.println("Message sending completed.");
					}
				});
		
	}
}

	/*	
		// Example usage of the ChatClient
		ChatClient chatClient = new ChatClient();

		// Create a request
		ChatRequest request = ChatRequest.newBuilder().setMessage("Hello, World!").build();

		// Send the message
		chatClient.sendMessage(request, new StreamObserver<ChatResponse>() {
			@Override
			public void onNext(ChatResponse response) {
				System.out.println("Received response: " + response.getResponseMessage());
			}

			@Override
			public void onError(Throwable t) {
				System.err.println("Error occurred: " + t.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("Message sending completed.");
			}
		});

	}
}
	
	
	
@Override
public void sendMessage(ChatRequest request, StreamObserver<ChatResponse> responseObserver) {
	// Implement the logic to send a chat message to the server
	String message = request.getMessage();
	// Process the message (e.g., send it to the server, display it, etc.)

	// Create a response
	ChatResponse response = ChatResponse.newBuilder().setResponseMessage("Sent: " + message).build();

	// Send the response back to the server
	responseObserver.onNext(response);
	responseObserver.onCompleted();
	}

}
*/