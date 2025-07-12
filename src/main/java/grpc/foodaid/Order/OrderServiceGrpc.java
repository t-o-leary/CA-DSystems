package grpc.foodaid.Order;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service for placing food requests and streaming their status
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: S2FoodInsService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OrderServiceGrpc {

  private OrderServiceGrpc() {}

  public static final String SERVICE_NAME = "foodaid.order.OrderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.foodaid.Order.OrderRequest,
      grpc.foodaid.Order.OrderConfirmation> getPlaceOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PlaceOrder",
      requestType = grpc.foodaid.Order.OrderRequest.class,
      responseType = grpc.foodaid.Order.OrderConfirmation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.foodaid.Order.OrderRequest,
      grpc.foodaid.Order.OrderConfirmation> getPlaceOrderMethod() {
    io.grpc.MethodDescriptor<grpc.foodaid.Order.OrderRequest, grpc.foodaid.Order.OrderConfirmation> getPlaceOrderMethod;
    if ((getPlaceOrderMethod = OrderServiceGrpc.getPlaceOrderMethod) == null) {
      synchronized (OrderServiceGrpc.class) {
        if ((getPlaceOrderMethod = OrderServiceGrpc.getPlaceOrderMethod) == null) {
          OrderServiceGrpc.getPlaceOrderMethod = getPlaceOrderMethod =
              io.grpc.MethodDescriptor.<grpc.foodaid.Order.OrderRequest, grpc.foodaid.Order.OrderConfirmation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PlaceOrder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.Order.OrderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.Order.OrderConfirmation.getDefaultInstance()))
              .setSchemaDescriptor(new OrderServiceMethodDescriptorSupplier("PlaceOrder"))
              .build();
        }
      }
    }
    return getPlaceOrderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.foodaid.Order.OrderStatusRequest,
      grpc.foodaid.Order.OrderStatusUpdate> getStreamOrderUpdatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamOrderUpdates",
      requestType = grpc.foodaid.Order.OrderStatusRequest.class,
      responseType = grpc.foodaid.Order.OrderStatusUpdate.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.foodaid.Order.OrderStatusRequest,
      grpc.foodaid.Order.OrderStatusUpdate> getStreamOrderUpdatesMethod() {
    io.grpc.MethodDescriptor<grpc.foodaid.Order.OrderStatusRequest, grpc.foodaid.Order.OrderStatusUpdate> getStreamOrderUpdatesMethod;
    if ((getStreamOrderUpdatesMethod = OrderServiceGrpc.getStreamOrderUpdatesMethod) == null) {
      synchronized (OrderServiceGrpc.class) {
        if ((getStreamOrderUpdatesMethod = OrderServiceGrpc.getStreamOrderUpdatesMethod) == null) {
          OrderServiceGrpc.getStreamOrderUpdatesMethod = getStreamOrderUpdatesMethod =
              io.grpc.MethodDescriptor.<grpc.foodaid.Order.OrderStatusRequest, grpc.foodaid.Order.OrderStatusUpdate>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamOrderUpdates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.Order.OrderStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.Order.OrderStatusUpdate.getDefaultInstance()))
              .setSchemaDescriptor(new OrderServiceMethodDescriptorSupplier("StreamOrderUpdates"))
              .build();
        }
      }
    }
    return getStreamOrderUpdatesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OrderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderServiceStub>() {
        @java.lang.Override
        public OrderServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderServiceStub(channel, callOptions);
        }
      };
    return OrderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OrderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderServiceBlockingStub>() {
        @java.lang.Override
        public OrderServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderServiceBlockingStub(channel, callOptions);
        }
      };
    return OrderServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OrderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderServiceFutureStub>() {
        @java.lang.Override
        public OrderServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderServiceFutureStub(channel, callOptions);
        }
      };
    return OrderServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for placing food requests and streaming their status
   * </pre>
   */
  public static abstract class OrderServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Unary RPC: client places a new order
     * </pre>
     */
    public void placeOrder(grpc.foodaid.Order.OrderRequest request,
        io.grpc.stub.StreamObserver<grpc.foodaid.Order.OrderConfirmation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPlaceOrderMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server-streaming RPC: client subscribes to status updates
     * </pre>
     */
    public void streamOrderUpdates(grpc.foodaid.Order.OrderStatusRequest request,
        io.grpc.stub.StreamObserver<grpc.foodaid.Order.OrderStatusUpdate> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamOrderUpdatesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPlaceOrderMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                grpc.foodaid.Order.OrderRequest,
                grpc.foodaid.Order.OrderConfirmation>(
                  this, METHODID_PLACE_ORDER)))
          .addMethod(
            getStreamOrderUpdatesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                grpc.foodaid.Order.OrderStatusRequest,
                grpc.foodaid.Order.OrderStatusUpdate>(
                  this, METHODID_STREAM_ORDER_UPDATES)))
          .build();
    }
  }

  /**
   * <pre>
   * Service for placing food requests and streaming their status
   * </pre>
   */
  public static final class OrderServiceStub extends io.grpc.stub.AbstractAsyncStub<OrderServiceStub> {
    private OrderServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC: client places a new order
     * </pre>
     */
    public void placeOrder(grpc.foodaid.Order.OrderRequest request,
        io.grpc.stub.StreamObserver<grpc.foodaid.Order.OrderConfirmation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPlaceOrderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server-streaming RPC: client subscribes to status updates
     * </pre>
     */
    public void streamOrderUpdates(grpc.foodaid.Order.OrderStatusRequest request,
        io.grpc.stub.StreamObserver<grpc.foodaid.Order.OrderStatusUpdate> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamOrderUpdatesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service for placing food requests and streaming their status
   * </pre>
   */
  public static final class OrderServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<OrderServiceBlockingStub> {
    private OrderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC: client places a new order
     * </pre>
     */
    public grpc.foodaid.Order.OrderConfirmation placeOrder(grpc.foodaid.Order.OrderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPlaceOrderMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server-streaming RPC: client subscribes to status updates
     * </pre>
     */
    public java.util.Iterator<grpc.foodaid.Order.OrderStatusUpdate> streamOrderUpdates(
        grpc.foodaid.Order.OrderStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamOrderUpdatesMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service for placing food requests and streaming their status
   * </pre>
   */
  public static final class OrderServiceFutureStub extends io.grpc.stub.AbstractFutureStub<OrderServiceFutureStub> {
    private OrderServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC: client places a new order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.foodaid.Order.OrderConfirmation> placeOrder(
        grpc.foodaid.Order.OrderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPlaceOrderMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PLACE_ORDER = 0;
  private static final int METHODID_STREAM_ORDER_UPDATES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OrderServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OrderServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PLACE_ORDER:
          serviceImpl.placeOrder((grpc.foodaid.Order.OrderRequest) request,
              (io.grpc.stub.StreamObserver<grpc.foodaid.Order.OrderConfirmation>) responseObserver);
          break;
        case METHODID_STREAM_ORDER_UPDATES:
          serviceImpl.streamOrderUpdates((grpc.foodaid.Order.OrderStatusRequest) request,
              (io.grpc.stub.StreamObserver<grpc.foodaid.Order.OrderStatusUpdate>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class OrderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OrderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.foodaid.Order.FoodaidImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OrderService");
    }
  }

  private static final class OrderServiceFileDescriptorSupplier
      extends OrderServiceBaseDescriptorSupplier {
    OrderServiceFileDescriptorSupplier() {}
  }

  private static final class OrderServiceMethodDescriptorSupplier
      extends OrderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OrderServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OrderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OrderServiceFileDescriptorSupplier())
              .addMethod(getPlaceOrderMethod())
              .addMethod(getStreamOrderUpdatesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
