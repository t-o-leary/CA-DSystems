package Discovery;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;


public class Discovery {

    public static void main(String[] args) throws IOException, InterruptedException {
        JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
        jmdns.addServiceListener("_grpc._tcp.local.", new ServiceListener() {
            @Override
            public void serviceAdded(ServiceEvent event) {
                System.out.println("Service added: " + event.getName());
                Logger.getLogger(Discovery.class.getName()).info("Service added: " + event.getName());
            }
            @Override
            public void serviceRemoved(ServiceEvent event) {
                System.out.println("Service removed: " + event.getName());
                Logger.getLogger(Discovery.class.getName()).info("Service removed: " + event.getName());
            }
            @Override
            public void serviceResolved(ServiceEvent event) {
                System.out.println("Service resolved: " + event.getInfo());
                Logger.getLogger(Discovery.class.getName()).info("Service resolved: " + event.getInfo());
            }
        });
        Thread.sleep(60000);
        jmdns.close();
    }
}
	
	
	
	
	
	
	
	/*private static class SampleListener implements ServiceListener {
		
		
		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added: " + event.getInfo());
		}
	
	
	public void serviceRemoved(ServiceEvent event) {
		System.out.println("Service removed: " + event.getInfo());
	}
	
	public void serviceResolved(ServiceEvent event) {
		System.out.println("Service resolved: " + event.getInfo());

        ServiceInfo info = event.getInfo();
        int port = info.getPort();
        String path = info.getNiceTextString().split("=")[1];
       
        String url =  "http://localhost:"+port+"/"+path;
        System.out.println(" --- sending request to " + url);
        
       // GetRequest.request(url);
}
	}


public static void main(String[] args) throws InterruptedException {
	try {
		
		// Create a JmDNS instance
		JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
		System.out.println("Client: InetAddress.getLocalHost():" + InetAddress.getLocalHost());
		// Add a service listener
		jmdns.addServiceListener("_http._tcp.local.", new SampleListener());

		// Wait a bit
        Thread.sleep(60000);
		
	} catch (UnknownHostException e) {
		System.out.println(e.getMessage());
	} catch (IOException e) {
		System.out.println(e.getMessage());
	}
}
}
*/
