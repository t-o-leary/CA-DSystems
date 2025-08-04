package grpc.foodaid.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

import grpc.foodaid.Chat.ChatServer;
import io.grpc.stub.StreamObserver;

public class ChatServer extends ChatServiceGrpc.ChatServiceImplBase {

	private static LinkedHashSet<StreamObserver<ChatResponse>> observers = new LinkedHashSet<>();
private static final Logger logger = Logger.getLogger(ChatServer.class.getName());
	
	public static void main(String[] args)  throws IOException, InterruptedException {
        // Start the Chat server here.
        // This will involve setting up the gRPC server,
        // registering the Chat service implementation,
        // and starting the server to listen for incoming requests.
        
        int port = 50054; // Example port number for the Chat service
        
        if (isPortAvailable(port)) {
            System.out.println("Port " + port + " is available.");
            try {
                System.out.println("Starting Chat server...");
                io.grpc.Server server = io.grpc.ServerBuilder.forPort(port).addService(new ChatServer()).build().start();
                System.out.println("Chat Server started on port " + port);
                logger.info("Chat Server started, listening on " + port);
                server.awaitTermination();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Port " + port + " is already in use.");
        }
    }
	
	
	
/*	
	@Override
	public void sendMessage(ChatRequest request, StreamObserver<ChatResponse> responseObserver) {
		// Implement the logic to handle incoming chat messages
		String message = request.getMessage();
		// Process the message (e.g., save it, broadcast it, etc.)

		// Create a response
		ChatResponse response = ChatResponse.newBuilder().setResponseMessage("Received: " + message).build();

		// Send the response back to the client
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
*/
	public static boolean isPortAvailable(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			serverSocket.setReuseAddress(true);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	@Override
	public StreamObserver<ChatRequest> liveChat(StreamObserver<ChatResponse> responseObserver) {
	    observers.add(responseObserver); // Add the observer to the list of observers
		return new StreamObserver<ChatRequest>() {
	        @Override
	        public void onNext(ChatRequest request) {
	        	Date d1 = new Date();
	        	
	        	for (StreamObserver<ChatResponse> observer : observers) {
					observer.onNext(ChatResponse.newBuilder()
							//.setDateTime(d1.toString())
							.setResponseMessage("Received: " + request.getMessage()).build());
	        	}
	        	
	        	
	        	
	     
	        }

	        @Override
	        public void onError(Throwable t) {
	        	 observers.remove(responseObserver);
	             responseObserver.onError(t);
	        }

	        @Override
	        public void onCompleted() {
	        	//observers.remove(responseObserver);
	          //  responseObserver.onCompleted();
	        }
	    };
	}
	}
// This class will handle


