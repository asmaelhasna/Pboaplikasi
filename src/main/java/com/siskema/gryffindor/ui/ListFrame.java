package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ListFrame extends JFrame {
    
    public ListFrame(String title) {
        setTitle("Siskema Gryffindor - " + title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 720); 
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));
        setResizable(true); // <-- RESPONSIVE

        getContentPane().setBackground(UIConstants.COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }
    
    // Top Bar (Header)
    private JComponent createTopBar() {
        // ... (Implementasi sama dengan DashboardFrame)
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
        JPanel root = new JPanel(new BorderLayout(20, 0)); // Spasi antar kolom
        root.setBackground(UIConstants.COLOR_BACKGROUND);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Sidebar Kanan (Menu Navigasi)
        JPanel rightColumn = createRightMenu();
        // RESPONSIVE: Hapus setPreferredSize kaku. 
        // Biarkan komponen di dalamnya menentukan lebar minimal.
        root.add(rightColumn, BorderLayout.EAST);

        // Kolom Utama 
        JPanel centerColumn = createCenterColumn();
        root.add(centerColumn, BorderLayout.CENTER); // <-- Ini akan meregang

        return root;
    }
    
    // Menu Sidebar Kanan dengan Navigasi
    private JPanel createRightMenu() {
        JPanel menu = new JPanel();
        menu.setBackground(UIConstants.COLOR_PRIMARY);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(20, 20, 20, 20));
        // RESPONSIVE: Atur ukuran minimal agar menu tetap terlihat
        menu.setMinimumSize(new Dimension(180, 0));
        menu.setPreferredSize(new Dimension(200, 0)); // Ukuran default yang baik

        menu.add(createMenuItem("🏠", "Beranda", this instanceof DashboardFrame, "Dashboard"));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("🏫", "UKM", this instanceof UKMListFrame, "UKMList")); 
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("📅", "Kegiatan", this instanceof ActivityListFrame, "ActivityList")); 
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("🔔", "Notifikasi", false, "Notification")); // Belum diimplementasi
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("🚪", "Keluar", false, "Logout"));

        menu.add(Box.createVerticalGlue());
        return menu;
    }
    
    // Item Menu dengan logika Navigasi
    private JComponent createMenuItem(String iconText, String label, boolean selected, String target) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); 
        item.setOpaque(false);
        item.setBorder(new EmptyBorder(8, 5, 8, 5));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 20));
        icon.setForeground(Color.WHITE); 

        JLabel text = new JLabel(label);
        text.setFont(UIConstants.FONT_NORMAL);
        text.setForeground(Color.WHITE);

        item.add(icon);
        item.add(text);

        if (selected) {
            item.setBackground(UIConstants.COLOR_PRIMARY_DARK); // highlight
            item.setOpaque(true);
            item.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 0, Color.WHITE), 
                    new EmptyBorder(8, 5, 8, 5)
            ));
        }
        
        // --- LOGIKA NAVIGASI MENU ---
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (target) {
                    case "Dashboard":
                        if (!(ListFrame.this instanceof DashboardFrame)) {
                            new DashboardFrame().setVisible(true);
                            dispose();
                        }
                        break;
                    case "UKMList":
                        if (!(ListFrame.this instanceof UKMListFrame)) {
                            new UKMListFrame().setVisible(true);
                            dispose();
                        }
                        break;
                    case "ActivityList":
                        if (!(ListFrame.this instanceof ActivityListFrame)) {
                            new ActivityListFrame().setVisible(true);
                            dispose();
                        }
                        break;
                    case "Logout":
                        new LoginFrame().setVisible(true);
                        dispose();
                        break;
                    // Notifikasi dan menu lain belum diimplementasi framenya
                }
            }
        });

        return item;
    }
    
    // Abstract methods untuk diimplementasikan
    protected abstract JPanel createCenterColumn();
    protected abstract JPanel createCardContent(String title, String date, String participants, String status, boolean canRegister);
}