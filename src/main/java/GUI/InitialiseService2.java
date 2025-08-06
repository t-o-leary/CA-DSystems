// src/main/java/GUI/initialiseService1.java
package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;

import grpc.foodaid.Order.OrderClient;
import grpc.foodaid.Order.OrderConfirmation;
import grpc.foodaid.Order.OrderRequest;
import grpc.foodaid.Order.OrderServer;
import grpc.foodaid.Order.OrderStatusClient;
import grpc.foodaid.Order.OrderStatusRequest;
import grpc.foodaid.Order.OrderStatusUpdate;

public class InitialiseService2 {
	public JTextField txtFoodType;
	public JTextField txtQuantity;
	public JTextField txtRecipient;
	public JTextField txtDeliveryAddress;
	public JTextField txtOrderId;
	public JLabel lblFoodType;
	public JLabel lblQuantity;
	public JLabel lblRecipient;
	public JLabel lblDeliveryAddress;
	public JLabel lblOrderId;
	public JLabel lblS2Response;
	public JLabel lblS2ResponseSub;
	public JPanel groupPanelService2;
	public JPanel groupPanelService2_btn;
	public JButton btnStartService2;
	public JButton btnSubmitS2;
	public JButton btnSubscribeS2;

	public InitialiseService2(JFrame frame, int portS2) {
		txtRecipient = new JTextField();
		txtRecipient.setBounds(195, 63, 211, 20);
		txtRecipient.setColumns(10);

		txtFoodType = new JTextField();
		txtFoodType.setColumns(10);
		txtFoodType.setBounds(195, 94, 211, 20);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(195, 125, 211, 20);

		txtDeliveryAddress = new JTextField();
		txtDeliveryAddress.setColumns(10);
		txtDeliveryAddress.setBounds(195, 156, 211, 20);

		lblS2Response = new JLabel("");
		lblS2Response.setBounds(55, 12, 514, 77);
		lblS2Response.setFont(lblS2Response.getFont().deriveFont(16f));
		lblS2Response.setForeground(Color.BLUE);

		lblRecipient = new JLabel("Recipient");
		lblRecipient.setBounds(87, 66, 120, 14);

		lblFoodType = new JLabel("Food Type");
		lblFoodType.setBounds(87, 97, 120, 14);

		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(87, 128, 120, 14);

		lblDeliveryAddress = new JLabel("Delivery Address");
		lblDeliveryAddress.setBounds(87, 159, 120, 14);

		btnSubmitS2 = new JButton("Send");
		btnSubmitS2.setBackground(Color.GREEN);
		btnSubmitS2.setBounds(317, 11, 89, 23);

		txtOrderId = new JTextField();
		txtOrderId.setColumns(10);
		txtOrderId.setBounds(195, 217, 211, 20);

		lblOrderId = new JLabel("Order ID");
		lblOrderId.setBounds(87, 220, 120, 14);

		btnSubscribeS2 = new JButton("Subscribe to Order Status");

		btnSubscribeS2.setBackground(Color.ORANGE);
		btnSubscribeS2.setBounds(300, 187, 89, 23);

		lblS2ResponseSub = new JLabel("");
		lblS2ResponseSub.setForeground(Color.BLUE);
		lblS2ResponseSub.setBounds(39, 235, 514, 77);

		btnStartService2 = new JButton("Start Order Service");
		btnStartService2.setBackground(Color.RED);
		btnStartService2.setBounds(178, 11, 179, 23);

		groupPanelService2 = new JPanel();
		groupPanelService2.setLayout(null);
		groupPanelService2.setBounds(0, 50, 591, 314);

		groupPanelService2.add(txtFoodType);
		groupPanelService2.add(txtQuantity);
		groupPanelService2.add(txtRecipient);
		groupPanelService2.add(txtDeliveryAddress);

		groupPanelService2.add(lblS2Response);
		groupPanelService2.add(lblFoodType);
		groupPanelService2.add(lblQuantity);
		groupPanelService2.add(lblRecipient);
		groupPanelService2.add(lblDeliveryAddress);

		groupPanelService2.add(btnSubmitS2);
		groupPanelService2.add(txtOrderId);
		groupPanelService2.add(lblOrderId);
		groupPanelService2.add(btnSubscribeS2);
		groupPanelService2.add(lblS2ResponseSub);

		groupPanelService2_btn = new JPanel();
		groupPanelService2_btn.setLayout(null);
		groupPanelService2_btn.setBounds(0, 11, 591, 38);
		groupPanelService2_btn.add(btnStartService2);

		groupPanelService2.setVisible(false);

		btnStartService2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartService2.setText("Starting Ordering Service...");
				new Thread(() -> {
					SwingUtilities.invokeLater(() -> btnStartService2.setText("Order Service Started"));
					OrderServer.main(new String[] { String.valueOf(portS2) });
				}).start();
				btnStartService2.setEnabled(false);
				btnStartService2.setBackground(Color.GRAY);
				groupPanelService2.setVisible(true);
			}
		});

		frame.getContentPane().add(groupPanelService2);
		frame.getContentPane().add(groupPanelService2_btn);

		btnSubmitS2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String recipient = txtRecipient.getText();
				String foodType = txtFoodType.getText();
				int quantity = Integer.parseInt(txtQuantity.getText());
				String deliveryAddress = txtDeliveryAddress.getText();

				OrderRequest request = OrderRequest.newBuilder().setRecipient(recipient).setFoodType(foodType)
						.setQuantity(quantity).setDeliveryAddress(deliveryAddress).build();

				OrderClient client = new OrderClient("localhost", portS2);
				try {

					OrderConfirmation response = client.placeOrder(request);
					lblS2Response.setText("Server response: " + response.getMessage());
				} catch (Exception ex) {
					lblS2Response.setText("Error: " + ex.getMessage());
				} finally {
					try {
						client.shutdown();
					} catch (InterruptedException ignored) {
					}

				}
			}
		});

		btnSubscribeS2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(() -> {
					String orderId = txtOrderId.getText();
					OrderStatusRequest request = OrderStatusRequest.newBuilder().setOrderId(orderId).build();

					OrderStatusClient client2 = new OrderStatusClient("localhost", portS2);
					StringBuilder statusMessages = new StringBuilder();
					try {
						int count = 1;
						Iterator<OrderStatusUpdate> updates = client2.StreamOrderUpdates(request);
						while (updates.hasNext()) {
							OrderStatusUpdate update = updates.next();
							statusMessages.append((count++) + ". Order ").append(update.getOrderId())
									.append(" status: ").append(update.getStatus()).append("<br>");
							SwingUtilities.invokeLater(() -> {
								lblS2ResponseSub.setText("<html>" + statusMessages.toString() + "</html>");
							});
						}
					} catch (Exception ex) {
						SwingUtilities.invokeLater(() -> {
							lblS2ResponseSub.setText("Error: " + ex.getMessage());
						});
					} finally {
						try {
							client2.shutdown();
						} catch (InterruptedException ignored) {
						}
					}
				}).start();
			}
		});

	}
}