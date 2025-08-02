package grpc.foodaid.Order;


import grpc.foodaid.Order.OrderStatusRequest;
import grpc.foodaid.Order.OrderStatusUpdate;
import grpc.foodaid.Order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrderStatusClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);

        OrderStatusRequest statusRequest = OrderStatusRequest.newBuilder()
                .setOrderId("123")
                .build();

        stub.streamOrderUpdates(statusRequest).forEachRemaining(update -> {
            System.out.println("Order " + update.getOrderId() + " status: " + update.getStatus());
        });

        channel.shutdown();
    }
}
