package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActivityListFrame extends ListFrame {

    public ActivityListFrame() {
        super("Daftar Kegiatan");
    }

    @Override
    protected JPanel createCenterColumn() {
        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setBackground(UIConstants.COLOR_BACKGROUND);

        JLabel title = new JLabel("Daftar Kegiatan");
        title.setFont(UIConstants.FONT_TITLE);
        center.add(title, BorderLayout.NORTH);

        // Search & Filter Panel (RESPONSIVE: TextField dibuat meregang)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchPanel.setBackground(UIConstants.COLOR_BACKGROUND);
        
        RoundedTextField searchField = new RoundedTextField();
        searchField.setText("  Cari Kegiatan..."); 
        searchField.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        searchField.setPreferredSize(new Dimension(350, 38)); // Ukuran default

        String[] fakultas = {"Semua Fakultas", "Fakultas Teknik", "Fakultas Ekonomi"};
        JComboBox<String> fakultasFilter = new JComboBox<>(fakultas);
        fakultasFilter.setFont(UIConstants.FONT_NORMAL);
        fakultasFilter.setPreferredSize(new Dimension(150, 38));

        searchPanel.add(searchField);
        searchPanel.add(fakultasFilter);
        
        // List Kegiatan 
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(UIConstants.COLOR_BACKGROUND);
        listPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        listPanel.add(createCardContent("Webinar Teknologi Terkini", "12 November 2025", "50 orang", "Aktif", true));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(createCardContent("Lomba Desain Grafis", "20 November 2025", "100 orang", "Aktif", true));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(createCardContent("Workshop Penulisan Artikel", "26 November 2025", "45 orang", "Selesai", false));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(Box.createVerticalGlue()); 

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        center.add(contentPanel, BorderLayout.CENTER);

        return center;
    }
    
    @Override
    protected JPanel createCardContent(String title,
                                       String date,
                                       String participants,
                                       String status,
                                       boolean canRegister) {
        // ... (Implementasi card activity)
        JPanel activity = new JPanel(new GridBagLayout()); 
        activity.setBackground(UIConstants.COLOR_CARD);
        activity.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(15, 20, 15, 20) 
        ));
        // RESPONSIVE: Memastikan card meregang horizontal
        activity.setMaximumSize(new Dimension(Integer.MAX_VALUE, activity.getPreferredSize().height));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(UIConstants.COLOR_CARD);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        // ... (Label Title, Date, Status)
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_SUBTITLE);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_DARK);
        
        JLabel dateLabel = new JLabel(date + " | " + participants);
        dateLabel.setFont(UIConstants.FONT_SMALL);
        dateLabel.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(UIConstants.FONT_SMALL);
        statusLabel.setForeground(
                "Selesai".equalsIgnoreCase(status) ?
                        new Color(150, 150, 150) : new Color(0, 150, 0)
        );
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(dateLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(statusLabel);
        activity.add(infoPanel, gbc);

        // Button Panel
        gbc.gridx = 1;
        gbc.weightx = 0.0; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.EAST; 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(UIConstants.COLOR_CARD);
        
        JButton detailButton = new JButton("Lihat Detail");
        styleButton(detailButton, UIConstants.COLOR_BUTTON_GRAY, UIConstants.COLOR_TEXT_DARK);
        
        // --- NAVIGASI KE DETAIL KEGIATAN ---
        detailButton.addActionListener(e -> {
            new ActivityDetailFrame().setVisible(true);
            dispose(); // Tutup frame list kegiatan
        });
        
        JButton daftarButton = new JButton("Daftar");
        styleButton(daftarButton, UIConstants.COLOR_PRIMARY, Color.WHITE);
        daftarButton.setEnabled(canRegister);

        buttonPanel.add(detailButton);
        buttonPanel.add(daftarButton);

        activity.add(buttonPanel, gbc);

        return activity;
    }
    
    // Utility untuk styling tombol
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setFont(UIConstants.FONT_SMALL);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
    }
}