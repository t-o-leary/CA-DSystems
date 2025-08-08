package grpc.foodaid.Surplus;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

// This code is adapted from https://github.com/jmdns/jmdns
public class SurplusServiceRegistrar  {

    public static void main(String[] args) throws InterruptedException {

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			System.out.println("Registration: InetAddress.getLocalHost():" + InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", "SurplusService", 50051, "path=index.html");
            jmdns.registerService(serviceInfo);
            Logger.getLogger(SurplusServiceRegistrar.class.getName()).info("SurplusService registered with jmDNS");
            
            // Wait a bit
            Thread.sleep(60000);

            // Unregister all services
            //jmdns.unregisterAllServices();
            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
