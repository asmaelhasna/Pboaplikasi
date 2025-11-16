package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationFrame extends JFrame {

    public RegistrationFrame() {
        setTitle("Siskema Gryffindor - Daftar Akun Baru");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 750); 
        setLocationRelativeTo(null);
        setResizable(true); // <-- RESPONSIVE: Diubah menjadi TRUE
        getContentPane().setBackground(UIConstants.COLOR_BACKGROUND);

        JPanel root = new JPanel(new GridBagLayout());
        root.setOpaque(false);
        setContentPane(root);

        JPanel card = createCardPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Tidak perlu weight/fill agar kartu tetap di tengah saat resize
        root.add(card, gbc);
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.COLOR_CARD);
        // Hapus setPreferredSize kaku
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(32, 40, 32, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 40);

        // Kiri: Logo
        gbc.gridx = 0;
        gbc.weightx = 0.35;
        card.add(createLeftPanel(), gbc);

        // Kanan: Form Pendaftaran (menggunakan JScrollPane)
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        gbc.insets = new Insets(0, 0, 0, 0);

        JScrollPane scrollPane = new JScrollPane(createRightPanel());
        // Set ukuran awal, tapi komponen di dalamnya yang akan meregang
        scrollPane.setPreferredSize(new Dimension(450, 600)); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        card.add(scrollPane, gbc);

        return card;
    }

    private JPanel createLeftPanel() {
        // Implementasi Left Panel sama dengan LoginFrame
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(UIConstants.COLOR_CARD);
        leftPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel shield = new JLabel("🛡️", SwingConstants.CENTER);
        shield.setFont(new Font("SansSerif", Font.PLAIN, 64));
        gbc.gridy = 0;
        leftPanel.add(shield, gbc);

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
        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIConstants.COLOR_CARD);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(0, 20, 0, 20)); 

        // Judul Halaman
        JLabel titleLabel = new JLabel("Daftar Akun Baru");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(titleLabel);
        
        JLabel loginLink = new JLabel("<html>Sudah punya akun? <a href='#'>Silahkan login</a></html>");
        loginLink.setFont(UIConstants.FONT_SMALL);
        loginLink.setForeground(UIConstants.COLOR_PRIMARY);
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLink.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // --- NAVIGASI KE LOGIN ---
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        
        formPanel.add(loginLink);
        formPanel.add(Box.createVerticalStrut(25));


        // --- Informasi Pribadi (RESPONSIVE: menggunakan MaximumSize) ---
        formPanel.add(createSubtitle("Informasi Pribadi"));
        formPanel.add(createInputField("NIM", "Masukkan NIM"));
        formPanel.add(createInputField("Nama Lengkap", "Masukkan Nama Lengkap"));
        formPanel.add(createInputField("Email", "Masukkan Email Universitas"));
        formPanel.add(createInputField("Nomor Telepon", "Masukkan Nomor Telepon"));
        formPanel.add(Box.createVerticalStrut(25));

        // --- Informasi Akademik ---
        formPanel.add(createSubtitle("Informasi Akademik"));
        formPanel.add(createInputField("Fakultas", "Masukkan Fakultas"));
        formPanel.add(createInputField("Program Studi", "Masukkan Program Studi"));
        formPanel.add(Box.createVerticalStrut(25));
        
        // --- Password ---
        formPanel.add(createSubtitle("Password"));
        formPanel.add(createPasswordField("Password", "Masukkan Password"));
        formPanel.add(Box.createVerticalStrut(40));

        // Tombol Daftar
        RoundedButton registerButton = new RoundedButton("Daftar");
        registerButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        // --- NAVIGASI KE LOGIN SETELAH DAFTAR ---
        registerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pendaftaran Berhasil! Silahkan Login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        });
        
        formPanel.add(registerButton);
        formPanel.add(Box.createVerticalStrut(20));

        return formPanel;
    }

    private JLabel createSubtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.FONT_SUBTITLE);
        label.setForeground(UIConstants.COLOR_TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createInputField(String labelText, String placeholder) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.FONT_NORMAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedTextField field = new RoundedTextField();
        // RESPONSIVE: Memastikan field meregang secara horizontal
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalStrut(15));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        return panel;
    }
    
    private JPanel createPasswordField(String labelText, String placeholder) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.FONT_NORMAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPasswordField field = new RoundedPasswordField();
        // RESPONSIVE: Memastikan field meregang secara horizontal
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalStrut(15));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        return panel;
    }
}