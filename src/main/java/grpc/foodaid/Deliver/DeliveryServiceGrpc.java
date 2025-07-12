package grpc.foodaid.Deliver;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Client‐streaming RPC: submit delivery orders
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: S3DeliveryService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DeliveryServiceGrpc {

  private DeliveryServiceGrpc() {}

  public static final String SERVICE_NAME = "foodaid.delivery.DeliveryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.foodaid.Deliver.DeliveryOrder,
      grpc.foodaid.Deliver.DeliveryPlan> getSubmitDeliveriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitDeliveries",
      requestType = grpc.foodaid.Deliver.DeliveryOrder.class,
      responseType = grpc.foodaid.Deliver.DeliveryPlan.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.foodaid.Deliver.DeliveryOrder,
      grpc.foodaid.Deliver.DeliveryPlan> getSubmitDeliveriesMethod() {
    io.grpc.MethodDescriptor<grpc.foodaid.Deliver.DeliveryOrder, grpc.foodaid.Deliver.DeliveryPlan> getSubmitDeliveriesMethod;
    if ((getSubmitDeliveriesMethod = DeliveryServiceGrpc.getSubmitDeliveriesMethod) == null) {
      synchronized (DeliveryServiceGrpc.class) {
        if ((getSubmitDeliveriesMethod = DeliveryServiceGrpc.getSubmitDeliveriesMethod) == null) {
          DeliveryServiceGrpc.getSubmitDeliveriesMethod = getSubmitDeliveriesMethod =
              io.grpc.MethodDescriptor.<grpc.foodaid.Deliver.DeliveryOrder, grpc.foodaid.Deliver.DeliveryPlan>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitDeliveries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.Deliver.DeliveryOrder.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.Deliver.DeliveryPlan.getDefaultInstance()))
              .setSchemaDescriptor(new DeliveryServiceMethodDescriptorSupplier("SubmitDeliveries"))
              .build();
        }
      }
    }
    return getSubmitDeliveriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DeliveryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DeliveryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DeliveryServiceStub>() {
        @java.lang.Override
        public DeliveryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DeliveryServiceStub(channel, callOptions);
        }
      };
    return DeliveryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DeliveryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DeliveryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DeliveryServiceBlockingStub>() {
        @java.lang.Override
        public DeliveryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DeliveryServiceBlockingStub(channel, callOptions);
        }
      };
    return DeliveryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DeliveryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DeliveryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DeliveryServiceFutureStub>() {
        @java.lang.Override
        public DeliveryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DeliveryServiceFutureStub(channel, callOptions);
        }
      };
    return DeliveryServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Client‐streaming RPC: submit delivery orders
   * </pre>
   */
  public static abstract class DeliveryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.foodaid.Deliver.DeliveryOrder> submitDeliveries(
        io.grpc.stub.StreamObserver<grpc.foodaid.Deliver.DeliveryPlan> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSubmitDeliveriesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubmitDeliveriesMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                grpc.foodaid.Deliver.DeliveryOrder,
                grpc.foodaid.Deliver.DeliveryPlan>(
                  this, METHODID_SUBMIT_DELIVERIES)))
          .build();
    }
  }

  /**
   * <pre>
   * Client‐streaming RPC: submit delivery orders
   * </pre>
   */
  public static final class DeliveryServiceStub extends io.grpc.stub.AbstractAsyncStub<DeliveryServiceStub> {
    private DeliveryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeliveryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DeliveryServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.foodaid.Deliver.DeliveryOrder> submitDeliveries(
        io.grpc.stub.StreamObserver<grpc.foodaid.Deliver.DeliveryPlan> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSubmitDeliveriesMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Client‐streaming RPC: submit delivery orders
   * </pre>
   */
  public static final class DeliveryServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<DeliveryServiceBlockingStub> {
    private DeliveryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeliveryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DeliveryServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * Client‐streaming RPC: submit delivery orders
   * </pre>
   */
  public static final class DeliveryServiceFutureStub extends io.grpc.stub.AbstractFutureStub<DeliveryServiceFutureStub> {
    private DeliveryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeliveryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DeliveryServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBMIT_DELIVERIES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DeliveryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DeliveryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBMIT_DELIVERIES:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.submitDeliveries(
              (io.grpc.stub.StreamObserver<grpc.foodaid.Deliver.DeliveryPlan>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DeliveryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DeliveryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.foodaid.Deliver.FoodaidImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DeliveryService");
    }
  }

  private static final class DeliveryServiceFileDescriptorSupplier
      extends DeliveryServiceBaseDescriptorSupplier {
    DeliveryServiceFileDescriptorSupplier() {}
  }

  private static final class DeliveryServiceMethodDescriptorSupplier
      extends DeliveryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DeliveryServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DeliveryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DeliveryServiceFileDescriptorSupplier())
              .addMethod(getSubmitDeliveriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
