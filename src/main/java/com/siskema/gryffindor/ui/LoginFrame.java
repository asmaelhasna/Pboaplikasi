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
        card.setPreferredSize(new Dimension(820, 360));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(32, 40, 32, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 40);

        // kiri: logo
        gbc.gridx = 0;
        card.add(createLeftPanel(), gbc);

        // kanan: form
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(createRightPanel(), gbc);

        return card;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(UIConstants.COLOR_CARD);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(260, 260));

        leftPanel.add(Box.createVerticalGlue());

        JLabel shield = new JLabel("🛡️", SwingConstants.CENTER);
        shield.setAlignmentX(Component.CENTER_ALIGNMENT);
        shield.setFont(new Font("SansSerif", Font.PLAIN, 64));

        JLabel gryffindor = new JLabel("GRYFFINDOR");
        gryffindor.setAlignmentX(Component.CENTER_ALIGNMENT);
        gryffindor.setFont(new Font("Serif", Font.BOLD, 26));
        gryffindor.setForeground(UIConstants.COLOR_PRIMARY);

        leftPanel.add(shield);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(gryffindor);

        leftPanel.add(Box.createVerticalGlue());
        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(UIConstants.COLOR_CARD);
        rightPanel.setPreferredSize(new Dimension(360, 260));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Judul
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(UIConstants.COLOR_CARD);
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_DARK);
        titlePanel.add(titleLabel);
        rightPanel.add(titlePanel, gbc);

        // Email label
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel emailLabel = new JLabel("Email/NIM");
        emailLabel.setFont(UIConstants.FONT_NORMAL);
        rightPanel.add(emailLabel, gbc);

        // Email field
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new RoundedTextField();
        emailField.setPreferredSize(new Dimension(260, 40));
        rightPanel.add(emailField, gbc);

        // Password label
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(UIConstants.FONT_NORMAL);
        rightPanel.add(passwordLabel, gbc);

        // Password field
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new RoundedPasswordField();
        passwordField.setPreferredSize(new Dimension(260, 40));
        rightPanel.add(passwordField, gbc);

        // "Belum punya akun..."
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 4, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        JPanel helperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        helperPanel.setBackground(UIConstants.COLOR_CARD);

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
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel lupa = new JLabel("Lupa kata sandi?");
        lupa.setFont(UIConstants.FONT_SMALL);
        lupa.setForeground(UIConstants.COLOR_PRIMARY);
        rightPanel.add(lupa, gbc);

        // Spacer
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        rightPanel.add(spacer, gbc);

        // Tombol "Masuk"
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHEAST;

        RoundedButton loginButton = new RoundedButton("Masuk");
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
