// src/main/java/GUI/initialiseService1.java
package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;

import grpc.foodaid.Deliver.DeliveryClient;
import grpc.foodaid.Deliver.DeliveryOrder;
import grpc.foodaid.Deliver.DeliveryPlan;
import grpc.foodaid.Deliver.DeliveryServer;
import io.grpc.stub.StreamObserver;



public class InitialiseService3 {
	public JTextField txtFoodType;
	public JTextField txtQuantity;
	public JTextField txtRecipient;
	public JTextField txtDeliveryAddress;
	//public JTextField txtOrderId;
	public JLabel lblFoodType;
	public JLabel lblQuantity;
	public JLabel lblRecipient;
	public JLabel lblDeliveryAddress;
	//public JLabel lblOrderId;
	public JLabel lblS3Response;
	//public JLabel lblS3Response2;
	//public JLabel lblS3ResponseSub;
	public JPanel groupPanelService3;
	public JPanel groupPanelService3_btn;
	public JButton btnStartService3;
	public JButton btnSubmitS3;
	public JButton btnCompleteS3;

	public InitialiseService3(JFrame frame, int portS3) {
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

		lblS3Response = new JLabel("");
		lblS3Response.setBounds(55, 12, 514, 77);
		lblS3Response.setFont(lblS3Response.getFont().deriveFont(16f));
		lblS3Response.setForeground(Color.BLUE);
		
		//lblS3Response2 = new JLabel("XXXXXXXXXXXXXXXXXXXXXX");
		//lblS3Response2.setBounds(39, 235, 514, 77);
		//lblS3Response2.setForeground(Color.BLUE);
			
		lblRecipient = new JLabel("Recipient");
		lblRecipient.setBounds(87, 66, 120, 14);

		lblFoodType = new JLabel("Food Type");
		lblFoodType.setBounds(87, 97, 120, 14);

		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(87, 128, 120, 14);

		lblDeliveryAddress = new JLabel("Delivery Address");
		lblDeliveryAddress.setBounds(87, 159, 120, 14);

		btnSubmitS3 = new JButton("Send");
		btnSubmitS3.setBackground(Color.GREEN);
		btnSubmitS3.setBounds(317, 11, 89, 23);

	//	txtOrderId = new JTextField();
	//	txtOrderId.setColumns(10);
	//	txtOrderId.setBounds(195, 217, 211, 20);

	//	lblOrderId = new JLabel("Order ID");
	//	lblOrderId.setBounds(87, 220, 120, 14);

		btnCompleteS3 = new JButton("Complete");

		btnCompleteS3.setBackground(Color.ORANGE);
		btnCompleteS3.setBounds(300, 187, 89, 23);

	//	lblS3ResponseSub = new JLabel("");
	//	lblS3ResponseSub.setForeground(Color.BLUE);
	//	lblS3ResponseSub.setBounds(39, 235, 514, 77);

		btnStartService3 = new JButton("Start Delivery Service");
		btnStartService3.setBackground(Color.RED);
		btnStartService3.setBounds(178, 11, 179, 23);

		groupPanelService3 = new JPanel();
		groupPanelService3.setLayout(null);
		groupPanelService3.setBounds(0, 50, 591, 314);

		groupPanelService3.add(txtFoodType);
		groupPanelService3.add(txtQuantity);
		groupPanelService3.add(txtRecipient);
		groupPanelService3.add(txtDeliveryAddress);

		groupPanelService3.add(lblS3Response);
		//groupPanelService3.add(lblS3Response2);
		groupPanelService3.add(lblFoodType);
		groupPanelService3.add(lblQuantity);
		groupPanelService3.add(lblRecipient);
		groupPanelService3.add(lblDeliveryAddress);

		groupPanelService3.add(btnSubmitS3);
		//groupPanelService3.add(txtOrderId);
		//groupPanelService3.add(lblOrderId);
		groupPanelService3.add(btnCompleteS3);
		//groupPanelService3.add(lblS3ResponseSub);

		groupPanelService3_btn = new JPanel();
		groupPanelService3_btn.setLayout(null);
		groupPanelService3_btn.setBounds(0, 11, 591, 38);
		groupPanelService3_btn.add(btnStartService3);

		groupPanelService3.setVisible(false);
		frame.getContentPane().add(groupPanelService3);
		frame.getContentPane().add(groupPanelService3_btn);

		btnStartService3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartService3.setText("Starting Delivery Service...");
				new Thread(() -> {
					SwingUtilities.invokeLater(() -> btnStartService3.setText("Order Delivery Started"));
					DeliveryServer.main(new String[] { String.valueOf(portS3) });
				}).start();
				btnStartService3.setEnabled(false);
				btnStartService3.setBackground(Color.GRAY);
				groupPanelService3.setVisible(true);
			}
		});

		
		
		 DeliveryClient client = new DeliveryClient("localhost", portS3);
        StreamObserver<DeliveryPlan> responseObserver = new StreamObserver<DeliveryPlan>() {
            @Override
            public void onNext(DeliveryPlan value) {
				lblS3Response.setText("Delivery order submitted successfully.");
				

            }

            @Override
            public void onError(Throwable t) {
            	lblS3Response.setText("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                lblS3Response.setText("Stream completed.");
            }
        };

        StreamObserver<DeliveryOrder> requestObserver = client.asyncStub.submitDeliveries(responseObserver);
		
		btnSubmitS3.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                
		    	
				 String quantityText = txtQuantity.getText();
			        if (quantityText == null || quantityText.trim().isEmpty()) {
			            lblS3Response.setText("Quantity cannot be empty.");
			            return;
			        }
			        try {
			            int quantity = Integer.parseInt(quantityText.trim());
			            DeliveryOrder order = DeliveryOrder.newBuilder()
			                .setRecipient(txtRecipient.getText())
			                .setDeliveryAddress(txtDeliveryAddress.getText())
			                .setFoodType(txtFoodType.getText())
			                .setQuantity(quantity)
			                .build();
			            requestObserver.onNext(order);
			            lblS3Response.setText("Submitting delivery order for " + txtRecipient.getText());													
			            txtRecipient.setText("");
			            txtFoodType.setText("");
			            txtQuantity.setText("");
			            txtDeliveryAddress.setText("");
			        } catch (NumberFormatException ex) {
			            lblS3Response.setText("Quantity must be a valid number.");
			        } catch (Exception ex) {
			            lblS3Response.setText("Error: " + ex.getMessage());
			        }
			    }
			});
		
		
		btnCompleteS3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                requestObserver.onCompleted();
                
                lblS3Response.setText("Delivery stream completed.");
				
            }
        });
		
		
	}
}
		    /*	
		    	String recipient = txtRecipient.getText();
                int quantity;
                try {
                    quantity = Integer.parseInt(txtQuantity.getText());
                } catch (NumberFormatException ex) {
                    lblS3Response.setText("Please enter a valid number for quantity.");
                    return;
                }
                String deliveryAddress = txtDeliveryAddress.getText();
                String foodType = txtFoodType.getText();

                DeliveryOrder request = DeliveryOrder.newBuilder()
                    .setRecipient(recipient)
                    .setDeliveryAddress(deliveryAddress)
                    .setFoodType(foodType) 
                    .setQuantity(quantity)
                    .build();

                DeliveryClient client = new DeliveryClient("localhost", portS3);
                try {
                	// Submit the delivery order
                	DeliveryPlan response = client.submitDeliveries(request);
                	lblS3Response.setText("Delivery submitted. Server response: " + response.getTotalDeliveries() + " deliveries planned.");
                
                } catch (Exception ex) {
                    lblS3Response.setText("Error: " + ex.getMessage());
                } finally {
                    try {
                        client.shutdown();
                    } catch (InterruptedException ignored) {}
                }
		    }

		    	
		    	
		    	
		    }
		});
	}
		    	/*
		    	
		    	String recipient = txtRecipient.getText();
		        
		        int quantity;
		        try {
		            quantity = Integer.parseInt(txtQuantity.getText());
		        } catch (NumberFormatException ex) {
		            lblS3Response.setText("Please enter a valid number for quantity.");
		            return;
		        }
		        String deliveryAddress = txtDeliveryAddress.getText();

		        String foodType = txtFoodType.getText();
		        DeliveryOrder request = DeliveryOrder.newBuilder()
		            .setRecipient(recipient)
		            .setDeliveryAddress(deliveryAddress)
		            .setFoodType(foodType) 
		            .setQuantity(quantity)
		            
		            .build();

		        DeliveryClient client = new DeliveryClient("localhost", portS3);
		        try {
		                                	// Submit the delivery order
		        	DeliveryPlan response = client.submitDeliveries();
		        	lblS3Response.setText("Delivery submitted."); //		
		        	lblS3Response.setText("Server response: " + response.getTotalDeliveries() + " deliveries planned.");
		        			
		        } catch (Exception ex) {
		            lblS3Response.setText("Error: " + ex.getMessage());
		        } finally {
		            try {
		                client.shutdown();
		            } catch (InterruptedException ignored) {}
		        }
		    }
		});

	
			
		

	}
}*/