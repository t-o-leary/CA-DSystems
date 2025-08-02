package grpc.foodaid.Surplus;

import grpc.foodaid.Surplus.OrderServiceGrpc;
import grpc.foodaid.Surplus.SurplusRequest;
import grpc.foodaid.Surplus.OrderAcknowledge;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

public class SurplusClient {

    private final ManagedChannel channel;
    private final OrderServiceGrpc.SurplusServiceBlockingStub blockingStub;

    public SurplusClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.blockingStub = OrderServiceGrpc.newBlockingStub(channel);
    }

    public OrderAcknowledge recordSurplus(SurplusRequest request) {
        return blockingStub.surplusRecord(request);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        SurplusClient client = new SurplusClient("localhost", 50053);
        try {
            // Build a sample SurplusRequest 
            SurplusRequest request = SurplusRequest.newBuilder()
                     .setId(1)
                     .setFoodType("Vegetables")
                     .setQuantityFood(100)
                     .setNutritionalGrade(7)
                    .setLocation("Dublin")
                    .setDepo(2)
                     .build();

            OrderAcknowledge response = client.recordSurplus(request);
            System.out.println("Server response: " + response);
        } finally {
            client.shutdown();
        }
    }
    }