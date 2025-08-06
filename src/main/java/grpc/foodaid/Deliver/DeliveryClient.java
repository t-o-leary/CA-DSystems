package grpc.foodaid.Deliver;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DeliveryClient {

    private final ManagedChannel channel;
    public final DeliveryServiceGrpc.DeliveryServiceStub asyncStub;

    public DeliveryClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.asyncStub = DeliveryServiceGrpc.newStub(channel);
    }

    public void submitDeliveries() throws InterruptedException {
       
    	
		StreamObserver<DeliveryOrder> requestObserver = asyncStub.submitDeliveries(new StreamObserver<DeliveryPlan>() {
			
			@Override
			public void onNext(DeliveryPlan value) {
				System.out.println("Received DeliveryPlan: " + value);
			}

			@Override
			public void onError(Throwable t) {
				System.err.println("Error in stream: " + t.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("Stream completed.");
			}
		});

		// Example DeliveryOrder messages
		DeliveryOrder order1 = DeliveryOrder.newBuilder().setRecipient("John Doe")
				.setDeliveryAddress("123 Main St, Dublin").setFoodType("Vegetables").setQuantity(100).build();

		DeliveryOrder order2 = DeliveryOrder.newBuilder().setRecipient("Jane Smith")
				.setDeliveryAddress("456 Oak Ave, Cork").setFoodType("Fruit").setQuantity(50).build();

		requestObserver.onNext(order1);
		requestObserver.onNext(order2);

		// Complete the stream
		requestObserver.onCompleted();
    			//(new StreamObserver<>() 
    			{	
    				
    				 channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);// Shutdown the channel after completion
    	
    			}
    }
}

    	
    /*	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	CountDownLatch finishLatch = new CountDownLatch(1);

        
        StreamObserver<DeliveryPlan> responseObserver = new StreamObserver<DeliveryPlan>() {
            @Override
            public void onNext(DeliveryPlan value) {
                System.out.println("Received DeliveryPlan: " + value);
            }
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                finishLatch.countDown();
            }
            @Override
            public void onCompleted() {
                System.out.println("Stream completed.");
                finishLatch.countDown();
            }
        };

        StreamObserver<DeliveryOrder> requestObserver = asyncStub.submitDeliveries(responseObserver);

        // Send multiple DeliveryOrder messages
        DeliveryOrder order1 = DeliveryOrder.newBuilder()
                //.setId(1)
                .setRecipient("John Doe")
                .setDeliveryAddress("123 Main St, Dublin")
                .setFoodType("Vegetables")
                .setQuantity(100)
                .build();

        DeliveryOrder order2 = DeliveryOrder.newBuilder()
                //.setId(2)
                .setRecipient("Jane Smith")
                .setDeliveryAddress("456 Oak Ave, Cork")
                .setFoodType("Fruit")
                .setQuantity(50)
                .build();

        requestObserver.onNext(order1);
        requestObserver.onNext(order2);

        // Complete the stream
        requestObserver.onCompleted();

        // Wait for response
        finishLatch.await(1, TimeUnit.MINUTES);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] x, String  host, Integer port) throws Exception {
        DeliveryClient client = new DeliveryClient(host, port);
        try {
            client.submitDeliveries();
        } finally {
            client.shutdown();
        }
    }
}
    	*/