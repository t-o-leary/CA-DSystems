/*------------------------------------------------------------------------------------------------------------------
 * ChatServiceRegistrar class
 * 
 * Author Tim O’Leary
 * Student ID: 23287021 
 * Version 1.0
 * Program: Higher Diploma in Science in Computing
 * Module: Distributed Systems  (HDSDEV_JAN25)
 * Lecturer: Sheresh Zahoor
 * Project: CA
 * Submission Date 08-August-2025
 * 
 * This class registers the Chat gRPC service on the local network using jmDNS for service discovery. 
 * It creates a jmDNS instance, registers the service with a specified name and port, logs the registration status, 
 * and keeps the service available for discovery for a set duration. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Chat;

//iMPORT Libraries for jmDNS
import java.io.IOException;// Import for IO operations
import java.net.InetAddress;//	 Import for network address operations
import java.util.logging.Logger;// Import for logging

import javax.jmdns.JmDNS;// Import for JmDNS service discovery
import javax.jmdns.ServiceInfo;// Import for service information

public class ChatServiceRegistrar {

    public static void main(String[] args) {
        JmDNS jmdns = null;
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            Logger.getLogger(ChatServiceRegistrar.class.getName()).info("JmDNS instance created for local host"); // Log creation of JmDNS instance

            ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", "ChatService", 50054, "path=index.html");// Create service info for the ChatService
            jmdns.registerService(serviceInfo);// Register the ChatService with JmDNS
            Logger.getLogger(ChatServiceRegistrar.class.getName()).info("ChatService registered with jmDNS");// Log service registration

            Thread.sleep(60000);// Keep the service available for discovery for 60 seconds

        } catch (IOException e) {
            Logger.getLogger(ChatServiceRegistrar.class.getName()).severe("I/O error: " + e.getMessage());// Log I/O errors
            System.err.println("I/O error occurred during service registration.");//		
        } catch (InterruptedException e) {
            Logger.getLogger(ChatServiceRegistrar.class.getName()).severe("Thread interrupted: " + e.getMessage());// // Log thread interruption
            System.err.println("Operation interrupted.");
            Thread.currentThread().interrupt();// Restore interrupted status
        } finally {
            if (jmdns != null) {
                try {
                    jmdns.close();// Close the JmDNS instance to release resources
                } catch (IOException e) {
                    Logger.getLogger(ChatServiceRegistrar.class.getName()).severe("Failed to close JmDNS: " + e.getMessage());// Log closing errors
                }
            }
        }
    }
}