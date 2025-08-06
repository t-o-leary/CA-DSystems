// src/main/java/GUI/ConsoleInterface.java
package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import grpc.foodaid.Surplus.SurplusAcknowledge;
import grpc.foodaid.Surplus.SurplusClient;
import grpc.foodaid.Surplus.SurplusRequest;
import grpc.foodaid.Surplus.SurplusServer;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ConsoleInterface {

	public JFrame frame;

	public JMenuBar menuBar;
	public JMenu Services;
	public JMenuItem service1, service2, service3, service4;

	private InitialiseService1 service1Panel;
	private InitialiseService2 service2Panel;
	private InitialiseService3 service3Panel;
	//private InitialiseService4 service4Panel;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ConsoleInterface window = new ConsoleInterface();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ConsoleInterface() {

		int portS1 = 50051; // Port for Surplus Service
		int portS2 = 50052; // Port for Order Service
		int portS3 = 50053; // Port for Deliver Service
		int portS4 = 50054; // Port for Chat Service

		initialiseFrame();
		service1Panel = new InitialiseService1(frame, portS1);
		service2Panel = new InitialiseService2(frame, portS2);
		service3Panel = new InitialiseService3(frame, portS3);
		// service4Panel = new InitialiseService4(frame, portS4);
		// initialiseService2(portS2);

		service1.addActionListener(e -> {
			// Show Service 1 panel when the menu item is clicked

			service1Panel.groupPanelService1_btn.setVisible(true);
			
			service2Panel.groupPanelService2.setVisible(false);
			service2Panel.groupPanelService2_btn.setVisible(false);
			service3Panel.groupPanelService3.setVisible(false);
			service3Panel.groupPanelService3_btn.setVisible(false);
			//service4Panel.groupPanelService4.setVisible(false);
			//service4Panel.groupPanelService4.setVisible(false);
			if (isPortAvailable(portS1)) {

				// Set the service started flag

				service1Panel.groupPanelService1.setVisible(false);

			} else {
				service1Panel.groupPanelService1.setVisible(true);

			}

		});
		service2.addActionListener(e -> {
			
			service2Panel.groupPanelService2_btn.setVisible(true);		
			// Hide Service 1 panel when the menu item is clicked
			service1Panel.groupPanelService1.setVisible(false);
			service1Panel.groupPanelService1_btn.setVisible(false);
			service3Panel.groupPanelService3.setVisible(false);
			service3Panel.groupPanelService3_btn.setVisible(false);
			//service4Panel.groupPanelService4.setVisible(false);
			//service4Panel.groupPanelService4.setVisible(false);
			// Show Service 2 panel
			if (isPortAvailable(portS2)) {

				// Set the service started flag

				service2Panel.groupPanelService2.setVisible(false);

			} else {
				service2Panel.groupPanelService2.setVisible(true);

			}
			});
		
		
		
		service3.addActionListener(e -> {
			// Hide Service 1 panel when the menu item is clicked
			
			service3Panel.groupPanelService3_btn.setVisible(true);
			
			service1Panel.groupPanelService1.setVisible(false);
			service1Panel.groupPanelService1_btn.setVisible(false);
			service2Panel.groupPanelService2.setVisible(false);
			service2Panel.groupPanelService2_btn.setVisible(false);
			//service4Panel.groupPanelService4.setVisible(false);
			//service4Panel.groupPanelService4_btn.setVisible(false);
			
			
			// Show Service 2 panel
			// groupPanelService2.setVisible(true);
			
			if (isPortAvailable(portS4)) {

				// Set the service started flag

				service3Panel.groupPanelService3.setVisible(false);

			} else {
				service3Panel.groupPanelService3.setVisible(true);

			}
			});
		

		service4.addActionListener(e -> {
			// Hide Service 1 panel when the menu item is clicked
			
			//service4Panel.groupPanelService4_btn.setVisible(true);
			
			service1Panel.groupPanelService1.setVisible(false);
			service1Panel.groupPanelService1_btn.setVisible(false);
			service2Panel.groupPanelService2.setVisible(false);
			service2Panel.groupPanelService2_btn.setVisible(false);
			service3Panel.groupPanelService3.setVisible(false);
			service3Panel.groupPanelService3_btn.setVisible(false);
			
			
			// Show Service 2 panel
			// groupPanelService2.setVisible(true);
			
			if (isPortAvailable(portS4)) {

				// Set the service started flag

				//service4Panel.groupPanelService4.setVisible(false);

			} else {
				//service4Panel.groupPanelService4.setVisible(true);

			}
			});
		



	}

	public void initialiseFrame() {

		frame = new JFrame("Food Aid Console Interface");
		frame.setBounds(100, 100, 607, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		menuBar = new JMenuBar();
		Services = new JMenu("Services Menu");
		service1 = new JMenuItem("Surplus Service");
		service2 = new JMenuItem("Order Service");
		service3 = new JMenuItem("Deliver Service");
		service4 = new JMenuItem("Chat Service");

		Services.add(service1);
		Services.add(service2);
		Services.add(service3);
		Services.add(service4);

		menuBar.add(Services);
		frame.setJMenuBar(menuBar);

	}

	public static boolean isPortAvailable(int iPort) {
		try (ServerSocket serverSocket = new ServerSocket(iPort)) {
			serverSocket.setReuseAddress(true);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}