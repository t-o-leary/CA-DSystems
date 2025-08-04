package grpc.foodaid.Order;

import grpc.foodaid.Order.OrderRequest;
import grpc.foodaid.Order.OrderConfirmation;
import grpc.foodaid.Order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrderClient {
	public static void main(String[] args) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

		OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);

		OrderRequest request = OrderRequest.newBuilder()//.setOrderId(123)
				.setRecipient("John Doe").setFoodType("Rice")
				.setQuantity(10).setDeliveryAddress("123 Main St").build();

		OrderConfirmation confirmation = stub.placeOrder(request);
		System.out
				.println("Order confirmed: " + confirmation.getConfirmed() + ", message: " + confirmation.getMessage());

		channel.shutdown();
	}
}
