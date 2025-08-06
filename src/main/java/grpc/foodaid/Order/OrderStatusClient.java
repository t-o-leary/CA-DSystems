package grpc.foodaid.Order;

import grpc.foodaid.Order.OrderStatusRequest;
import grpc.foodaid.Order.OrderStatusUpdate;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import grpc.foodaid.Order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrderStatusClient {
	
	
	private final ManagedChannel channel;
	private final OrderServiceGrpc.OrderServiceBlockingStub blockingStub;

	public OrderStatusClient(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		this.blockingStub = OrderServiceGrpc.newBlockingStub(channel);
	}
	
	public Iterator<OrderStatusUpdate> StreamOrderUpdates(OrderStatusRequest statusRequest) {
		return blockingStub.streamOrderUpdates(statusRequest); 
				
				
	}
	
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
	
	
	public static void main(String[] x,String host, Integer port) throws Exception {
	    OrderStatusClient client = new OrderStatusClient(host, port);
	    try {
	        OrderStatusRequest statusRequest = OrderStatusRequest.newBuilder().setOrderId("123").build();
	        Iterator<OrderStatusUpdate> updates = client.StreamOrderUpdates(statusRequest);

	        while (updates.hasNext()) {
	            OrderStatusUpdate update = updates.next();
	            System.out.println("Order " + update.getOrderId() + " status: " + update.getStatus());
	        }
	    } finally {
	        client.shutdown();
	    }
	}
	
	
}


/*
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

		OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);

		OrderStatusRequest statusRequest = OrderStatusRequest.newBuilder().setOrderId("123").build();

		stub.streamOrderUpdates(statusRequest).forEachRemaining(update -> {
			System.out.println("Order " + update.getOrderId() + " status: " + update.getStatus());
		});

		channel.shutdown();
	}
}
*/