/*------------------------------------------------------------------------------------------------------------------
 * InitialiseService2 class
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
 * The InitialiseService2 class provides a GUI for interacting with the Order gRPC service.
 * It allows users to submit new food orders and subscribe to real-time order status updates.
 * The class manages user input fields, response labels, and action buttons, and handles remote
 * invocations to the gRPC server with robust error handling and user-friendly error messaging. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package GUI;

// import libraries
import java.awt.Color; // for Colour
import java.awt.event.ActionEvent; // for ActionEvent
import java.awt.event.ActionListener;// for ActionListener
import java.util.Iterator;// for Iterator

import javax.swing.*;// for GUI components

import grpc.foodaid.Order.OrderClient;// for OrderClient
import grpc.foodaid.Order.OrderConfirmation;// for OrderConfirmation
import grpc.foodaid.Order.OrderRequest;// for OrderRequest
import grpc.foodaid.Order.OrderServer;//	
import grpc.foodaid.Order.OrderStatusClient;// for OrderStatusClient
import grpc.foodaid.Order.OrderStatusRequest;// for OrderStatusRequest
import grpc.foodaid.Order.OrderStatusUpdate;// for OrderStatusUpdate
import io.grpc.StatusRuntimeException;// for handling gRPC status exceptions

public class InitialiseService2 {
	// GUI components for Service 2 (Order Service)
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

// Constructor for InitialiseService2
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
// Action to start the Order Service
		btnStartService2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartService2.setText("Starting Ordering Service...");// Update button text to indicate service start
				new Thread(() -> {// Start a new thread to run the OrderServer
					SwingUtilities.invokeLater(() -> btnStartService2.setText("Order Service Started"));// Update button
																										// text after
																										// starting
																										// service
					OrderServer.main(new String[] { String.valueOf(portS2) });// Start the OrderServer with the
																				// specified port
				}).start();// Start the thread to avoid blocking the GUI
				btnStartService2.setEnabled(false);// Disable the button to prevent multiple clicks
				btnStartService2.setBackground(Color.GRAY);// Change button color to indicate service is starting
				groupPanelService2.setVisible(true);// Make the service panel visible
			}
		});
// Add components to the frame
		frame.getContentPane().add(groupPanelService2);
		frame.getContentPane().add(groupPanelService2_btn);

		btnSubmitS2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// Action to handle order submission
				// Validate input fields
				// Get text from input fields and trim whitespace
				String recipient = txtRecipient.getText().trim();
				String foodType = txtFoodType.getText().trim();
				String quantityStr = txtQuantity.getText().trim();
				String deliveryAddress = txtDeliveryAddress.getText().trim();

				if (recipient.isEmpty() || foodType.isEmpty() || quantityStr.isEmpty() || deliveryAddress.isEmpty()) {// Check if any field is empty
					lblS2Response.setText("All fields must be filled in.");
					return;
				}

				int quantity;
				try {
					quantity = Integer.parseInt(quantityStr);// Parse quantity as an integer
					if (quantity <= 0) {
						lblS2Response.setText("Quantity must be a positive integer.");//	 Check if quantity is positive
						return;
					}
				} catch (NumberFormatException ex) {
					lblS2Response.setText("Quantity must be a valid integer.");// Handle invalid integer format
					return;
				}

				OrderRequest request = OrderRequest.newBuilder().setRecipient(recipient).setFoodType(foodType)// Create OrderRequest object with recipient, food type, quantity, and delivery address
						.setQuantity(quantity).setDeliveryAddress(deliveryAddress).build();

				OrderClient client = new OrderClient("localhost", portS2);// Create OrderClient instance
				try {
					OrderConfirmation response = client.placeOrder(request); // Place the order using the client
					lblS2Response.setText("Server response: " + response.getMessage());// Display server response
				} catch (StatusRuntimeException ex) {// Handle gRPC status exceptions
					lblS2Response.setText("gRPC error: " + ex.getStatus().getDescription());// Display gRPC error
																							// message
				} catch (Exception ex) {
					lblS2Response.setText("Unexpected error: " + ex.getMessage());// Display unexpected error message
				} finally {// Ensure client shutdown
					try {
						client.shutdown();// Shutdown the client to release resources
					} catch (InterruptedException ignored) {
					}

				}
			}
		});

		btnSubscribeS2.addActionListener(new ActionListener() { // Action to handle order status subscription

			public void actionPerformed(ActionEvent e) {
				String orderId = txtOrderId.getText().trim();// Get the order ID from the text field
				if (orderId.isEmpty()) {// Check if order ID is empty
					SwingUtilities.invokeLater(() -> lblS2ResponseSub.setText("Order ID must not be empty."));
					return;
				}
				new Thread(() -> {

					OrderStatusRequest request = OrderStatusRequest.newBuilder().setOrderId(orderId).build();// Create
																												// OrderStatusRequest
																												// object

					OrderStatusClient client2 = new OrderStatusClient("localhost", portS2);// Create OrderStatusClient
																							// instance
					StringBuilder statusMessages = new StringBuilder();// Initialize StringBuilder for status messages
					try {
						int count = 1;// Initialize count for message numbering
						Iterator<OrderStatusUpdate> updates = client2.StreamOrderUpdates(request);// Get order status
																									// updates from the
																									// client
						while (updates.hasNext()) {// Iterate through the updates
							OrderStatusUpdate update = updates.next();// Get the next update
							statusMessages.append((count++) + ". Order ").append(update.getOrderId())// Append the order
																										// ID to the
																										// status
																										// message
									.append(" status: ").append(update.getStatus()).append("<br>");// Append the status
																									// message to the
																									// StringBuilder
							SwingUtilities.invokeLater(() -> {
								lblS2ResponseSub.setText("<html>" + statusMessages.toString() + "</html>");// Update the
																											// label
																											// with the
																											// status
																											// messages
							});
						}
					} catch (StatusRuntimeException ex) {
						SwingUtilities.invokeLater(() -> {
							lblS2ResponseSub.setText("gRPC error: " + ex.getStatus().getDescription());// Display gRPC
																										// error message
						});
					} catch (Exception ex) {
						SwingUtilities.invokeLater(() -> {// Handle unexpected exceptions
							lblS2ResponseSub.setText("Unexpected error: " + ex.getMessage());
						});
					} finally {
						try {
							client2.shutdown();// Shutdown the client to release resources
						} catch (InterruptedException ignored) {
						}
					}
				}).start();// Start the thread to avoid blocking the GUI
			}
		});

	}
}