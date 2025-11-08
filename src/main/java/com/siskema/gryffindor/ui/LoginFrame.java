package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;

    public LoginFrame() {
        setTitle("Siskema Gryffindor - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(UIConstants.COLOR_BACKGROUND);

        JPanel root = new JPanel(new GridBagLayout());
        root.setOpaque(false);
        setContentPane(root);

        JPanel card = createCardPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        root.add(card, gbc);
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.COLOR_CARD);
        card.setPreferredSize(new Dimension(820, 420)); 
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(32, 40, 32, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.weighty = 1.0; 
        gbc.insets = new Insets(0, 0, 0, 40);

        // kiri: logo
        gbc.gridx = 0;
        gbc.weightx = 0.4; 
        card.add(createLeftPanel(), gbc);

        // kanan: form
        gbc.gridx = 1;
        gbc.weightx = 0.6; 
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(createRightPanel(), gbc);

        return card;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(UIConstants.COLOR_CARD);
        leftPanel.setLayout(new GridBagLayout()); 
        leftPanel.setPreferredSize(new Dimension(260, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 0, 5, 0); 

        JLabel shield = new JLabel("🛡️", SwingConstants.CENTER);
        shield.setFont(new Font("SansSerif", Font.PLAIN, 64));
        gbc.gridy = 0;
        leftPanel.add(shield, gbc);

        JLabel gryffindor = new JLabel("GRYFFINDOR", SwingConstants.CENTER);
        gryffindor.setFont(new Font("Serif", Font.BOLD, 26));
        gryffindor.setForeground(UIConstants.COLOR_PRIMARY);
        gbc.gridy = 1;
        leftPanel.add(gryffindor, gbc);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(UIConstants.COLOR_CARD);
        rightPanel.setPreferredSize(new Dimension(360, 0)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Judul
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_DARK);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(UIConstants.COLOR_CARD);
        titlePanel.add(titleLabel);
        rightPanel.add(titlePanel, gbc);


        // Email label
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 5, 0);
        JLabel emailLabel = new JLabel("Email/NIM");
        emailLabel.setFont(UIConstants.FONT_NORMAL);
        rightPanel.add(emailLabel, gbc);

        // Email field
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        emailField = new RoundedTextField();
        rightPanel.add(emailField, gbc);

        // Password label
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 5, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(UIConstants.FONT_NORMAL);
        rightPanel.add(passwordLabel, gbc);

        // Password field
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        passwordField = new RoundedPasswordField();
        rightPanel.add(passwordField, gbc);

        // "Belum punya akun..."
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 4, 0);
        JPanel helperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        helperPanel.setOpaque(false); 
        JLabel belum = new JLabel("Belum punya akun?");
        belum.setFont(UIConstants.FONT_SMALL);
        belum.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        JLabel daftar = new JLabel("Silahkan daftar");
        daftar.setFont(UIConstants.FONT_SMALL);
        daftar.setForeground(UIConstants.COLOR_PRIMARY);
        helperPanel.add(belum);
        helperPanel.add(daftar);
        rightPanel.add(helperPanel, gbc);

        // "Lupa kata sandi?"
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lupa = new JLabel("Lupa kata sandi?");
        lupa.setFont(UIConstants.FONT_SMALL);
        lupa.setForeground(UIConstants.COLOR_PRIMARY);
        rightPanel.add(lupa, gbc);

        // Tombol "Masuk"
        gbc.gridy++;
        gbc.weighty = 1.0; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.SOUTHEAST; 

        RoundedButton loginButton = new RoundedButton("Masuk"); 
        loginButton.setPreferredSize(new Dimension(140, 44)); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        loginButton.addActionListener(e -> onLogin());

        rightPanel.add(buttonPanel, gbc);

        return rightPanel;
    }

    private void onLogin() {
        SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));
        dispose();
    }
}
