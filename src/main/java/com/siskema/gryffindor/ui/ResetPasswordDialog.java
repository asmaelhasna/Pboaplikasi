package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResetPasswordDialog extends JDialog {

    public ResetPasswordDialog(Frame parent) {
        super(parent, "Lupa Kata Sandi", true);
        setSize(450, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UIConstants.COLOR_CARD);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        contentPanel.setBackground(UIConstants.COLOR_CARD);

        JLabel title = new JLabel("Reset Kata Sandi");
        title.setFont(UIConstants.FONT_SUBTITLE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(10));
        
        JLabel instruction = new JLabel("Masukkan Username (NIM) Anda untuk menerima kode reset.");
        instruction.setFont(UIConstants.FONT_NORMAL);
        instruction.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(instruction);
        contentPanel.add(Box.createVerticalStrut(15));

        RoundedTextField usernameField = new RoundedTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, usernameField.getPreferredSize().height));

        JPanel inputHelper = new JPanel();
        inputHelper.setLayout(new BoxLayout(inputHelper, BoxLayout.Y_AXIS));
        inputHelper.setOpaque(false);
        inputHelper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        inputHelper.add(new JLabel("Username (NIM):") {{ setFont(UIConstants.FONT_SMALL); }});
        inputHelper.add(Box.createVerticalStrut(5));
        inputHelper.add(usernameField);
        
        contentPanel.add(inputHelper);
        contentPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIConstants.COLOR_CARD);
        buttonPanel.setBorder(new EmptyBorder(0, 20, 10, 20));

        RoundedButton kirimButton = new RoundedButton("Kirim Kode");
        kirimButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, 
                "Simulasi: Kode reset telah dikirim ke email terdaftar untuk: " + username, 
                "Kode Terkirim", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(kirimButton);

        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}