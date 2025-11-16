package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActivityDetailFrame extends JFrame {

    public ActivityDetailFrame() {
        setTitle("Siskema Gryffindor - Detail Kegiatan");
        // Gunakan DISPOSE_ON_CLOSE karena ini bukan frame utama
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));
        setResizable(true); // <-- RESPONSIVE

        getContentPane().setBackground(UIConstants.COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }
    
    // Top Bar (Sama seperti ListFrame)
    private JComponent createTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        left.setBackground(Color.WHITE);
        
        JLabel logo = new JLabel("🛡️");
        logo.setFont(new Font("SansSerif", Font.PLAIN, 32));

        JLabel title = new JLabel("Siskema Gryffindor");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(UIConstants.COLOR_TEXT_DARK);

        left.add(logo);
        left.add(title);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        right.setBackground(Color.WHITE);
        JLabel profileIcon = new JLabel("👤");
        profileIcon.setFont(new Font("SansSerif", Font.PLAIN, 24));
        right.add(profileIcon);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        return top;
    }

    private JComponent createMainContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIConstants.COLOR_BACKGROUND);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Webinar Teknologi Terkini");
        title.setFont(UIConstants.FONT_TITLE);
        title.setForeground(UIConstants.COLOR_TEXT_DARK);
        root.add(title, BorderLayout.NORTH);

        // Split Panel - Kiri (Detail) dan Kanan (Aksi/Tombol)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                createDetailPanel(), createActionPanel());
        splitPane.setDividerLocation(850); // RESPONSIVE: Divider location default
        splitPane.setDividerSize(10);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setOpaque(false);
        splitPane.setResizeWeight(1.0); // RESPONSIVE: Memberikan semua ruang ekstra ke panel kiri (detail)
        
        root.add(splitPane, BorderLayout.CENTER);

        return root;
    }
    
    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 0, 0, 15)); 

        JPanel card = new JPanel();
        card.setBackground(UIConstants.COLOR_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // ... Konten detail
        card.add(createInfoItem("Fakultas Pelaksana", "Fakultas Teknik", "👤"));
        card.add(Box.createVerticalStrut(15));
        card.add(createInfoItem("Waktu Pelaksanaan", "12 November 2025, 09.00 - 16.00", "⏱️"));
        card.add(Box.createVerticalStrut(15));
        card.add(createInfoItem("Lokasi", "Aula FT", "📍"));
        card.add(Box.createVerticalStrut(25));
        
        JLabel descTitle = new JLabel("Deskripsi");
        descTitle.setFont(UIConstants.FONT_SUBTITLE);
        descTitle.setForeground(UIConstants.COLOR_TEXT_DARK);
        descTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(descTitle);
        card.add(Box.createVerticalStrut(10));
        
        JTextArea descArea = new JTextArea("Seminar nasional akan membahas perkembangan teknologi terkini dan dampaknya terhadap aspek kehidupan. Acara ini terbuka untuk semua mahasiswa Universitas Riau.");
        descArea.setFont(UIConstants.FONT_NORMAL);
        descArea.setForeground(UIConstants.COLOR_TEXT_DARK);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        card.add(descArea);
        card.add(Box.createVerticalGlue()); // Agar konten meregang
        
        JScrollPane scrollPane = new JScrollPane(card);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createInfoItem(String title, String value, String iconText) {
        // Utility untuk info item
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        item.setOpaque(false);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        // ... (label & value logic)
        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 24));
        icon.setForeground(UIConstants.COLOR_PRIMARY);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_NORMAL);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(UIConstants.FONT_SUBTITLE);
        valueLabel.setForeground(UIConstants.COLOR_TEXT_DARK);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(valueLabel);
        
        item.add(icon);
        item.add(textPanel);
        return item;
    }


    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 0, 0)); 
        
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.COLOR_CARD);
        card.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Tombol Daftar Sekarang (CTA Utama)
        RoundedButton daftarButton = new RoundedButton("Daftar Sekarang");
        daftarButton.setPreferredSize(new Dimension(200, 50));
        card.add(daftarButton, gbc);

        // Tombol Kembali
        gbc.gridy++;
        JButton kembaliButton = new JButton("Kembali");
        styleSecondaryButton(kembaliButton);
        kembaliButton.setPreferredSize(new Dimension(200, 50));
        
        // --- NAVIGASI KEMBALI KE LIST KEGIATAN ---
        kembaliButton.addActionListener(e -> {
            new ActivityListFrame().setVisible(true);
            dispose();
        });
        
        card.add(kembaliButton, gbc);
        
        // Untuk menempatkan tombol di atas
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonContainer.setOpaque(false);
        buttonContainer.add(card);
        
        panel.add(buttonContainer, BorderLayout.NORTH);
        return panel;
    }
    
    private void styleSecondaryButton(JButton button) {
        button.setFont(UIConstants.FONT_SUBTITLE);
        button.setBackground(UIConstants.COLOR_BACKGROUND);
        button.setForeground(UIConstants.COLOR_TEXT_DARK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true); 
    }
}