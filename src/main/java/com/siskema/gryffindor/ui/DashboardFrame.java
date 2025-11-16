package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Sekarang mewarisi ListFrame untuk mendapatkan TopBar dan Sidebar navigasi
public class DashboardFrame extends ListFrame {

    public DashboardFrame() {
        super("Beranda");
        // Semua setup Frame ada di ListFrame constructor
    }
    
    // Implementasi dummy card untuk memenuhi abstract ListFrame
    @Override
    protected JPanel createCardContent(String title, String date, String participants, String status, boolean canRegister) {
        return new JPanel();
    }

    // Mengimplementasikan konten utama Dashboard (3 kolom)
    @Override
    protected JPanel createCenterColumn() {
        JPanel root = new JPanel(new BorderLayout(20, 0));
        root.setBackground(UIConstants.COLOR_BACKGROUND);

        // Kiri: Profile & Statistik
        JPanel leftColumn = createLeftColumn();
        leftColumn.setMinimumSize(new Dimension(280, 0)); // Minimal agar tidak terlalu kecil
        leftColumn.setPreferredSize(new Dimension(300, 0)); // Ukuran preferred
        root.add(leftColumn, BorderLayout.WEST);

        // Tengah: Kegiatan Terbaru (RESPONSIVE)
        JPanel centerColumn = createCenterDashboardContent();
        root.add(centerColumn, BorderLayout.CENTER);

        return root;
    }

    // Bagian Kiri Dashboard (Profile dan Statistik)
    private JPanel createLeftColumn() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(UIConstants.COLOR_BACKGROUND);
        
        // Kartu Profile (Contoh)
        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(UIConstants.COLOR_CARD);
        profileCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        profileCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel avatar = new JLabel("🧑‍💻");
        avatar.setFont(new Font("SansSerif", Font.PLAIN, 64));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCard.add(avatar);
        profileCard.add(Box.createVerticalStrut(10));

        JLabel name = new JLabel("Zhidan Iannov Saaba", SwingConstants.CENTER);
        name.setFont(UIConstants.FONT_SUBTITLE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCard.add(name);
        
        JLabel nim = new JLabel("2407134924", SwingConstants.CENTER);
        nim.setFont(UIConstants.FONT_NORMAL);
        nim.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        nim.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCard.add(nim);
        
        profileCard.add(Box.createVerticalStrut(20));

        // Statistik (Contoh)
        profileCard.add(createStatItem("Kegiatan Diikuti", "12", UIConstants.COLOR_PRIMARY));
        profileCard.add(Box.createVerticalStrut(5));
        profileCard.add(createStatItem("Total Poin", "450", new Color(0, 150, 0)));
        profileCard.add(Box.createVerticalStrut(5));
        profileCard.add(createStatItem("UKM Diikuti", "2", new Color(255, 165, 0)));
        
        leftPanel.add(profileCard);
        leftPanel.add(Box.createVerticalGlue()); // Untuk menahan ke atas
        
        return leftPanel;
    }

    private JPanel createStatItem(String label, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(UIConstants.FONT_NORMAL);
        labelComp.setForeground(UIConstants.COLOR_TEXT_DARK);
        panel.add(labelComp, BorderLayout.WEST);

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(UIConstants.FONT_SUBTITLE);
        valueComp.setForeground(color);
        panel.add(valueComp, BorderLayout.EAST);
        
        return panel;
    }

    // Bagian Tengah Dashboard (Daftar Kegiatan Terbaru)
    private JPanel createCenterDashboardContent() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(UIConstants.COLOR_BACKGROUND);

        JLabel title = new JLabel("Kegiatan Terbaru");
        title.setFont(UIConstants.FONT_TITLE);
        title.setForeground(UIConstants.COLOR_TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(15));
        
        // Kartu Kegiatan
        JPanel list = new JPanel(new GridLayout(0, 1, 0, 10)); // Layout vertikal, 1 kolom
        list.setOpaque(false);
        
        // Contoh data
        list.add(createSimpleActivityCard("Webinar UI/UX Design", "12 Nov 2025", "Fakultas Teknik"));
        list.add(createSimpleActivityCard("Lomba Karya Tulis Ilmiah", "20 Nov 2025", "BEM"));
        list.add(createSimpleActivityCard("Workshop Bahasa Inggris", "25 Nov 2025", "UKM Bahasa"));
        
        // RESPONSIVE: Menggunakan JScrollPane agar list bisa digulir jika terlalu panjang
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalGlue());

        return centerPanel;
    }
    
    private JPanel createSimpleActivityCard(String title, String date, String organization) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(UIConstants.COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(15, 20, 15, 20) 
        ));
        // RESPONSIVE: Memastikan card meregang horizontal
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));

        JLabel icon = new JLabel("📅");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 32));
        card.add(icon, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_SUBTITLE);
        JLabel subtitleLabel = new JLabel(date + " | Oleh: " + organization);
        subtitleLabel.setFont(UIConstants.FONT_NORMAL);
        subtitleLabel.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        info.add(titleLabel);
        info.add(subtitleLabel);
        card.add(info, BorderLayout.CENTER);

        JButton detailButton = new JButton("Detail");
        detailButton.setFont(UIConstants.FONT_NORMAL);
        detailButton.setBackground(UIConstants.COLOR_BUTTON_GRAY);
        detailButton.setFocusPainted(false);
        detailButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        detailButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        card.add(detailButton, BorderLayout.EAST);
        
        return card;
    }
}