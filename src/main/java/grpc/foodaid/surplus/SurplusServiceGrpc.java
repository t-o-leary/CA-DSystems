package grpc.foodaid.surplus;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Unary RPC: service to record food surplus
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: S1SurplusService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SurplusServiceGrpc {

  private SurplusServiceGrpc() {}

  public static final String SERVICE_NAME = "foodaid.Surplus.SurplusService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.foodaid.surplus.SurplusRequest,
      grpc.foodaid.surplus.SurplusAcknowledge> getSurplusRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SurplusRecord",
      requestType = grpc.foodaid.surplus.SurplusRequest.class,
      responseType = grpc.foodaid.surplus.SurplusAcknowledge.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.foodaid.surplus.SurplusRequest,
      grpc.foodaid.surplus.SurplusAcknowledge> getSurplusRecordMethod() {
    io.grpc.MethodDescriptor<grpc.foodaid.surplus.SurplusRequest, grpc.foodaid.surplus.SurplusAcknowledge> getSurplusRecordMethod;
    if ((getSurplusRecordMethod = SurplusServiceGrpc.getSurplusRecordMethod) == null) {
      synchronized (SurplusServiceGrpc.class) {
        if ((getSurplusRecordMethod = SurplusServiceGrpc.getSurplusRecordMethod) == null) {
          SurplusServiceGrpc.getSurplusRecordMethod = getSurplusRecordMethod =
              io.grpc.MethodDescriptor.<grpc.foodaid.surplus.SurplusRequest, grpc.foodaid.surplus.SurplusAcknowledge>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SurplusRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.surplus.SurplusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.foodaid.surplus.SurplusAcknowledge.getDefaultInstance()))
              .setSchemaDescriptor(new SurplusServiceMethodDescriptorSupplier("SurplusRecord"))
              .build();
        }
      }
    }
    return getSurplusRecordMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SurplusServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SurplusServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SurplusServiceStub>() {
        @java.lang.Override
        public SurplusServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SurplusServiceStub(channel, callOptions);
        }
      };
    return SurplusServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SurplusServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SurplusServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SurplusServiceBlockingStub>() {
        @java.lang.Override
        public SurplusServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SurplusServiceBlockingStub(channel, callOptions);
        }
      };
    return SurplusServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SurplusServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SurplusServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SurplusServiceFutureStub>() {
        @java.lang.Override
        public SurplusServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SurplusServiceFutureStub(channel, callOptions);
        }
      };
    return SurplusServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Unary RPC: service to record food surplus
   * </pre>
   */
  public static abstract class SurplusServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void surplusRecord(grpc.foodaid.surplus.SurplusRequest request,
        io.grpc.stub.StreamObserver<grpc.foodaid.surplus.SurplusAcknowledge> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSurplusRecordMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSurplusRecordMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                grpc.foodaid.surplus.SurplusRequest,
                grpc.foodaid.surplus.SurplusAcknowledge>(
                  this, METHODID_SURPLUS_RECORD)))
          .build();
    }
  }

  /**
   * <pre>
   * Unary RPC: service to record food surplus
   * </pre>
   */
  public static final class SurplusServiceStub extends io.grpc.stub.AbstractAsyncStub<SurplusServiceStub> {
    private SurplusServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SurplusServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SurplusServiceStub(channel, callOptions);
    }

    /**
     */
    public void surplusRecord(grpc.foodaid.surplus.SurplusRequest request,
        io.grpc.stub.StreamObserver<grpc.foodaid.surplus.SurplusAcknowledge> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSurplusRecordMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Unary RPC: service to record food surplus
   * </pre>
   */
  public static final class SurplusServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SurplusServiceBlockingStub> {
    private SurplusServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SurplusServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SurplusServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.foodaid.surplus.SurplusAcknowledge surplusRecord(grpc.foodaid.surplus.SurplusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSurplusRecordMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Unary RPC: service to record food surplus
   * </pre>
   */
  public static final class SurplusServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SurplusServiceFutureStub> {
    private SurplusServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SurplusServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SurplusServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.foodaid.surplus.SurplusAcknowledge> surplusRecord(
        grpc.foodaid.surplus.SurplusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSurplusRecordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SURPLUS_RECORD = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SurplusServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SurplusServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SURPLUS_RECORD:
          serviceImpl.surplusRecord((grpc.foodaid.surplus.SurplusRequest) request,
              (io.grpc.stub.StreamObserver<grpc.foodaid.surplus.SurplusAcknowledge>) responseObserver);
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

  private static abstract class SurplusServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SurplusServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.foodaid.surplus.SurplusImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SurplusService");
    }
  }

  private static final class SurplusServiceFileDescriptorSupplier
      extends SurplusServiceBaseDescriptorSupplier {
    SurplusServiceFileDescriptorSupplier() {}
  }

  private static final class SurplusServiceMethodDescriptorSupplier
      extends SurplusServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SurplusServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SurplusServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SurplusServiceFileDescriptorSupplier())
              .addMethod(getSurplusRecordMethod())
              .build();
        }
      }
    }
    return result;
  }
}
