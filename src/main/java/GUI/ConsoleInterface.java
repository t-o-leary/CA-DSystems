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

import grpc.foodaid.Surplus.OrderAcknowledge;
import grpc.foodaid.Surplus.SurplusClient;
import grpc.foodaid.Surplus.SurplusRequest;
import grpc.foodaid.Surplus.SurplusServer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ConsoleInterface {

	private JFrame frame;
	private JLabel lblResponse;
	private JTextField txtFoodType;
	private JTextField txtQuantity;
	private JTextField txtLocation;
	private JTextField txtGrade;
	private JTextField txtDepo;
	private JLabel lblNewLabel;
	private JLabel lblQuantity;
	private JLabel lblNutrisionGrad;
	private JLabel lblLocation;
	private JLabel lblDepo;
	private JMenuBar menuBar;
	private JMenu Services;
	private JMenuItem service1, service2, service3, service4;

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
		initialize();
	}

	private void initialize() {
		int portS1 = 50051; // Port for Surplus Service
		int portS2 = 50052; // Port for Order Service
		int portS3 = 50053; // Port for Deliver Service
		int portS4 = 50054; // Port for Chat Service
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 766, 663);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		menuBar = new JMenuBar();
		Services = new JMenu("Services");
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
		
		
		
		// Also, initialize lblResponse and add to frame
		lblResponse = new JLabel("");
		lblResponse.setBounds(0, 218, 279, 47);
		frame.getContentPane().add(lblResponse);

		JButton btnSubmit = new JButton("Send");
		btnSubmit.setBackground(Color.GREEN);
		btnSubmit.setBounds(199, 11, 89, 23);
		frame.getContentPane().add(btnSubmit);

		JButton btnStartService1 = new JButton("Start Surplus Service");
		btnStartService1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartService1.setText("Starting Surplus Service...");
				new Thread(() -> {
					// Update label to "Started" on the EDT after thread begins
					SwingUtilities.invokeLater(() -> btnStartService1.setText("Surplus Service Started"));
					SurplusServer.main(new String[] { String.valueOf(portS1) });
				}).start();
				btnStartService1.setEnabled(false);
			}
		});

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String foodType = txtFoodType.getText();
				int quantity = Integer.parseInt(txtQuantity.getText());
				int grade = Integer.parseInt(txtGrade.getText());
				String location = txtLocation.getText();
				int depo = Integer.parseInt(txtDepo.getText());

				SurplusRequest request = SurplusRequest.newBuilder().setFoodType(foodType)
						.setQuantityFood(quantity).setNutritionalGrade(grade).setLocation(location).setDepo(depo)
						.build();

				SurplusClient client = new SurplusClient("localhost", portS1);
				try {
					OrderAcknowledge response = client.recordSurplus(request);
					lblResponse.setText("Server response: " + response.getMessage());
				} catch (Exception ex) {
					lblResponse.setText("Error: " + ex.getMessage());
				} finally {
					try {
						client.shutdown();
					} catch (InterruptedException ignored) {
					}
				}
			}
		});

		btnStartService1.setBounds(10, 11, 179, 23);
		frame.getContentPane().add(btnStartService1);

		txtFoodType = new JTextField();
		txtFoodType.setBounds(109, 63, 179, 20);
		frame.getContentPane().add(txtFoodType);
		txtFoodType.setColumns(10);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(109, 94, 179, 20);
		frame.getContentPane().add(txtQuantity);

		txtLocation = new JTextField();
		txtLocation.setColumns(10);
		txtLocation.setBounds(109, 156, 179, 20);
		frame.getContentPane().add(txtLocation);

		txtGrade = new JTextField();
		txtGrade.setColumns(10);
		txtGrade.setBounds(109, 125, 179, 20);
		frame.getContentPane().add(txtGrade);

		txtDepo = new JTextField();
		txtDepo.setColumns(10);
		txtDepo.setBounds(109, 187, 179, 20);
		frame.getContentPane().add(txtDepo);
		
		JLabel lblS1Response = new JLabel("");
		lblS1Response.setBounds(10, 230, 264, 36);
		frame.getContentPane().add(lblS1Response);
		
		lblNewLabel = new JLabel("Food Type");
		lblNewLabel.setBounds(10, 66, 79, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(10, 97, 79, 14);
		frame.getContentPane().add(lblQuantity);
		
		lblNutrisionGrad = new JLabel("Nutrition grade");
		lblNutrisionGrad.setBounds(10, 128, 79, 14);
		frame.getContentPane().add(lblNutrisionGrad);
		
		lblLocation = new JLabel("Location");
		lblLocation.setBounds(10, 159, 79, 14);
		frame.getContentPane().add(lblLocation);
		
		lblDepo = new JLabel("Depo");
		lblDepo.setBounds(10, 193, 79, 14);
		frame.getContentPane().add(lblDepo);
	}
}