// src/main/java/GUI/initialiseService1.java
package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import grpc.foodaid.Surplus.SurplusAcknowledge;
import grpc.foodaid.Surplus.SurplusClient;
import grpc.foodaid.Surplus.SurplusRequest;
import grpc.foodaid.Surplus.SurplusServer;

public class InitialiseService1 {
    public JTextField txtFoodType;
    public JTextField txtQuantity;
    public JTextField txtLocation;
    public JTextField txtGrade;
    public JTextField txtDepo;
    public JLabel lblFoodType;
    public JLabel lblQuantity;
    public JLabel lblNutrisionGrad;
    public JLabel lblLocation;
    public JLabel lblDepo;
    public JLabel lblS1Response;
    public JPanel groupPanelService1;
    public JPanel groupPanelService1_btn;
    public JButton btnStartService1;
    public JButton btnSubmit;

    public InitialiseService1(JFrame frame, int portS1) {
        txtFoodType = new JTextField();
        txtFoodType.setBounds(195, 63, 211, 20);
        txtFoodType.setColumns(10);

        txtQuantity = new JTextField();
        txtQuantity.setColumns(10);
        txtQuantity.setBounds(195, 94, 211, 20);

        txtLocation = new JTextField();
        txtLocation.setColumns(10);
        txtLocation.setBounds(195, 156, 211, 20);

        txtGrade = new JTextField();
        txtGrade.setColumns(10);
        txtGrade.setBounds(195, 125, 211, 20);

        txtDepo = new JTextField();
        txtDepo.setColumns(10);
        txtDepo.setBounds(195, 187, 211, 20);

        lblS1Response = new JLabel("");
        lblS1Response.setBounds(39, 218, 514, 77);

        lblFoodType = new JLabel("Food Type");
        lblFoodType.setBounds(87, 66, 120, 14);

        lblQuantity = new JLabel("Quantity");
        lblQuantity.setBounds(87, 97, 120, 14);

        lblNutrisionGrad = new JLabel("Nutrition grade");
        lblNutrisionGrad.setBounds(87, 128, 120, 14);

        lblLocation = new JLabel("Location");
        lblLocation.setBounds(87, 159, 120, 14);

        lblDepo = new JLabel("Depo");
        lblDepo.setBounds(87, 193, 120, 14);

        btnSubmit = new JButton("Send");
        btnSubmit.setBackground(Color.GREEN);
        btnSubmit.setBounds(317, 11, 89, 23);

        btnStartService1 = new JButton("Start Surplus Service");
        btnStartService1.setBackground(Color.RED);
        btnStartService1.setBounds(178, 11, 179, 23);

        groupPanelService1 = new JPanel();
        groupPanelService1.setLayout(null);
        groupPanelService1.setBounds(0, 50, 591, 314);

        groupPanelService1.add(txtFoodType);
        groupPanelService1.add(txtQuantity);
        groupPanelService1.add(txtLocation);
        groupPanelService1.add(txtGrade);
        groupPanelService1.add(txtDepo);
        groupPanelService1.add(lblS1Response);
        groupPanelService1.add(lblFoodType);
        groupPanelService1.add(lblQuantity);
        groupPanelService1.add(lblNutrisionGrad);
        groupPanelService1.add(lblLocation);
        groupPanelService1.add(lblDepo);
        groupPanelService1.add(btnSubmit);

        groupPanelService1_btn = new JPanel();
        groupPanelService1_btn.setLayout(null);
        groupPanelService1_btn.setBounds(0, 11, 591, 38);
        groupPanelService1_btn.add(btnStartService1);

        groupPanelService1.setVisible(false);

        btnStartService1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStartService1.setText("Starting Surplus Service...");
                new Thread(() -> {
                    SwingUtilities.invokeLater(() -> btnStartService1.setText("Surplus Service Started"));
                    SurplusServer.main(new String[] { String.valueOf(portS1) });
                }).start();
                btnStartService1.setEnabled(false);
                btnStartService1.setBackground(Color.GRAY);
                groupPanelService1.setVisible(true);
            }
        });

        frame.getContentPane().add(groupPanelService1);
        frame.getContentPane().add(groupPanelService1_btn);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String foodType = txtFoodType.getText();
                int quantity = Integer.parseInt(txtQuantity.getText());
                int grade = Integer.parseInt(txtGrade.getText());
                String location = txtLocation.getText();
                int depo = Integer.parseInt(txtDepo.getText());

                SurplusRequest request = SurplusRequest.newBuilder()
                        .setFoodType(foodType)
                        .setQuantityFood(quantity)
                        .setNutritionalGrade(grade)
                        .setLocation(location)
                        .setDepo(depo)
                        .build();

                SurplusClient client = new SurplusClient("localhost", portS1);
                try {
                    SurplusAcknowledge response = client.recordSurplus(request);
                    lblS1Response.setText("Server response: " + response.getMessage());
                } catch (Exception ex) {
                    lblS1Response.setText("Error: " + ex.getMessage());
                } finally {
                    try {
                        client.shutdown();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
    }
}