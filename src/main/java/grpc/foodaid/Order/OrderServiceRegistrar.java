package grpc.foodaid.Order;


import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;


public class OrderServiceRegistrar {

	  public static void main(String[] args) throws InterruptedException {

	        try {
	            // Create a JmDNS instance
	            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
				System.out.println("Registration: InetAddress.getLocalHost():" + InetAddress.getLocalHost());

	            // Register a service
	            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "OrderService", 500052, "path=index.html");
	            jmdns.registerService(serviceInfo);
	            Logger.getLogger(OrderServiceRegistrar.class.getName()).info("OrderService registered with jmDNS");
	            
	            // Wait a bit
	            Thread.sleep(60000);

	            // Unregister all services
	            //jmdns.unregisterAllServices();
	            

	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	}
