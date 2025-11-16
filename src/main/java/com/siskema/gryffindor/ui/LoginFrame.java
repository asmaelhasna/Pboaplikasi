package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import com.siskema.gryffindor.service.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class LoginFrame extends JFrame {

    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private DataService dataService;

    private Image backgroundImage;

    public LoginFrame() {
        this.dataService = new DataService();

        setTitle("Siskema Gryffindor - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        try {
            backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("images/siskema_bg.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(UIConstants.COLOR_BACKGROUND);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        });
        getContentPane().setLayout(new GridBagLayout());

        JPanel card = createCardPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        getContentPane().add(card, gbc);
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(32, 40, 32, 40)
        ));

        JPanel contentContainer = new JPanel(new GridBagLayout());
        contentContainer.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 40);
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        contentContainer.add(createLeftPanel(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentContainer.add(createRightPanel(), gbc);

        GridBagConstraints cardGBC = new GridBagConstraints();
        cardGBC.gridx = 0;
        cardGBC.gridy = 0;
        cardGBC.fill = GridBagConstraints.NONE;
        contentContainer.setPreferredSize(new Dimension(820, 420));
        contentContainer.setMaximumSize(new Dimension(900, 600));
        card.add(contentContainer, cardGBC);

        cardGBC.weightx = 1.0;
        cardGBC.weighty = 1.0;
        cardGBC.fill = GridBagConstraints.BOTH;
        card.add(new JPanel() {{ setOpaque(false); }}, cardGBC);
        return card;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(UIConstants.COLOR_CARD);
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel logo = new JLabel();
        try {
            ImageIcon siskemaLogo = new ImageIcon(
                new ImageIcon(getClass().getClassLoader().getResource("images/siskema_logo.png"))
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
            );
            logo.setIcon(siskemaLogo);
        } catch (Exception e) {
            System.err.println("Error loading siskema_logo.png: " + e.getMessage());
            logo.setText("🛡️");
            logo.setFont(new Font("SansSerif", Font.PLAIN, 64));
        }
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        leftPanel.add(logo, gbc);

        JLabel title = new JLabel("SISKEMA", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(UIConstants.COLOR_PRIMARY);
        gbc.gridy = 1;
        leftPanel.add(title, gbc);

        JLabel subtitle = new JLabel("GRYFFINDOR", SwingConstants.CENTER);
        subtitle.setFont(new Font("Serif", Font.BOLD, 22));
        subtitle.setForeground(UIConstants.COLOR_TEXT_DARK);
        gbc.gridy = 2;
        leftPanel.add(subtitle, gbc);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(UIConstants.COLOR_CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        rightPanel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Username (NIM/ID)");
        emailLabel.setFont(UIConstants.FONT_NORMAL);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        rightPanel.add(emailLabel, gbc);

        emailField = new RoundedTextField();
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 15, 0);
        rightPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(UIConstants.FONT_NORMAL);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 0);
        rightPanel.add(passwordLabel, gbc);

        passwordField = new RoundedPasswordField();
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 15, 0);
        rightPanel.add(passwordField, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(5, 0, 5, 0);
        JPanel helperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        helperPanel.setOpaque(false);
        JLabel belum = new JLabel("Belum punya akun? ");
        belum.setFont(UIConstants.FONT_SMALL);
        JLabel daftar = new JLabel("Silahkan daftar");
        daftar.setFont(UIConstants.FONT_SMALL);
        daftar.setForeground(UIConstants.COLOR_PRIMARY);
        daftar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        daftar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegistrationFrame().setVisible(true);
                dispose();
            }
        });
        helperPanel.add(belum);
        helperPanel.add(daftar);
        rightPanel.add(helperPanel, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lupa = new JLabel("Lupa kata sandi?");
        lupa.setFont(UIConstants.FONT_SMALL);
        lupa.setForeground(UIConstants.COLOR_PRIMARY);
        lupa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lupa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ResetPasswordDialog(LoginFrame.this).setVisible(true);
            }
        });
        rightPanel.add(lupa, gbc);

        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHEAST;

        RoundedButton loginButton = new RoundedButton("Masuk");
        loginButton.addActionListener(e -> attemptLogin());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        rightPanel.add(buttonPanel, gbc);

        return rightPanel;
    }

    private void attemptLogin() {
        String username = emailField.getText();
        String password = new String(passwordField.getPassword());

        User user = dataService.authenticateUser(username, password);

        if (user != null) {
            SessionManager.getInstance().setCurrentUser(user);
            new DashboardFrame(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah.",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}