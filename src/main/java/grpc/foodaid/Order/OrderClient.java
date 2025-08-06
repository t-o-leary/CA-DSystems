package grpc.foodaid.Order;

import grpc.foodaid.Order.OrderRequest;

import java.util.concurrent.TimeUnit;

import grpc.foodaid.Order.OrderConfirmation;
import grpc.foodaid.Order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrderClient {
	
	private final ManagedChannel channel;
	private final OrderServiceGrpc.OrderServiceBlockingStub blockingStub;

	public OrderClient(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		this.blockingStub = OrderServiceGrpc.newBlockingStub(channel);
	}
	
	
	//public static void main(String[] args) {
		//ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();

		//OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);

	public OrderConfirmation placeOrder(OrderRequest request) {
		return blockingStub.placeOrder(request);
	}
	
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
	
	
	public static void main(String[] x, String  host, Integer port ) throws Exception {
	
		OrderClient client = new OrderClient(host, port);
		try {
		OrderRequest request = OrderRequest.newBuilder()//.setOrderId(123)
				.setRecipient("John Doe").setFoodType("Rice")
				.setQuantity(10).setDeliveryAddress("123 Main St").build();

		OrderConfirmation response = client.placeOrder(request);
		System.out
				.println("Order confirmed: " + response.getConfirmed() + ", message: " + response.getMessage());
		} finally {
		client.shutdown();
		}
	}
}
