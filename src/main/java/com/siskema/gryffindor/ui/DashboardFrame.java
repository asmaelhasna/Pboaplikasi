package com.siskema.gryffindor.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Siskema Gryffindor - Beranda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        getContentPane().setBackground(UIConstants.COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

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
        JLabel burger = new JLabel("☰");
        burger.setFont(new Font("SansSerif", Font.BOLD, 24));
        right.add(burger);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        return top;
    }

    private JComponent createMainContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIConstants.COLOR_BACKGROUND);
        root.setBorder(new EmptyBorder(10, 20, 20, 20));

        JPanel leftColumn = createLeftColumn();
        leftColumn.setPreferredSize(new Dimension(320, 0));

        JPanel centerColumn = createCenterColumn();

        JPanel rightColumn = createRightMenu();
        rightColumn.setPreferredSize(new Dimension(200, 0));

        root.add(leftColumn, BorderLayout.WEST);
        root.add(centerColumn, BorderLayout.CENTER);
        root.add(rightColumn, BorderLayout.EAST);

        return root;
    }

    private JPanel createLeftColumn() {
        JPanel left = new JPanel();
        left.setBackground(UIConstants.COLOR_BACKGROUND);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        // Profile Card
        JPanel profileCard = new JPanel(new BorderLayout(0, 10)); 
        profileCard.setBackground(UIConstants.COLOR_PRIMARY);
        profileCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        profileCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, profileCard.getPreferredSize().height)); // Agar lebar maksimal

        JLabel halo = new JLabel("Halo, Asma Elhasna Hamid");
        halo.setForeground(Color.WHITE);
        halo.setFont(UIConstants.FONT_NORMAL);

        JLabel nim = new JLabel("2407134924");
        nim.setForeground(Color.WHITE);
        nim.setFont(UIConstants.FONT_NORMAL);

        JLabel prodi = new JLabel("Teknik Informatika | Fakultas Teknik");
        prodi.setForeground(Color.WHITE);
        prodi.setFont(UIConstants.FONT_SMALL);

        // Avatar
        JPanel avatarCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        avatarCircle.setOpaque(false);
        avatarCircle.setPreferredSize(new Dimension(50, 50));
        avatarCircle.setMaximumSize(new Dimension(50, 50));
        avatarCircle.setLayout(new GridBagLayout()); 
        JLabel avatarIcon = new JLabel("👤", SwingConstants.CENTER);
        avatarIcon.setFont(new Font("SansSerif", Font.PLAIN, 28)); 
        avatarCircle.add(avatarIcon); 

        JPanel textInfoPanel = new JPanel();
        textInfoPanel.setOpaque(false);
        textInfoPanel.setLayout(new BoxLayout(textInfoPanel, BoxLayout.Y_AXIS));
        textInfoPanel.add(halo);
        textInfoPanel.add(nim);
        textInfoPanel.add(prodi);

        profileCard.add(textInfoPanel, BorderLayout.CENTER);
        profileCard.add(avatarCircle, BorderLayout.EAST);

        left.add(profileCard);
        left.add(Box.createVerticalStrut(15));

        left.add(createStatCard("UKM diikuti", "3"));
        left.add(Box.createVerticalStrut(10));
        left.add(createStatCard("Event diikuti", "3"));
        left.add(Box.createVerticalStrut(10));
        left.add(createStatCard("Sertifikat dimiliki", "3"));

        left.add(Box.createVerticalGlue());
        return left;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout(15, 0)); 
        card.setBackground(UIConstants.COLOR_CARD);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel icon = new JLabel("🏛️");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 24));
        icon.setForeground(UIConstants.COLOR_TEXT_LIGHT); 

        JPanel textPanel = new JPanel();
        textPanel.setBackground(UIConstants.COLOR_CARD);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_NORMAL);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_DARK);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(UIConstants.FONT_SUBTITLE);
        valueLabel.setForeground(new Color(0, 130, 0));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(valueLabel);

        card.add(icon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createCenterColumn() {
        JPanel center = new JPanel();
        center.setBackground(UIConstants.COLOR_BACKGROUND);
        center.setLayout(new BorderLayout());

        JPanel card = new JPanel();
        card.setBackground(UIConstants.COLOR_CARD);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(createActivityPanel(
                "Webinar Teknologi Terkini",
                "12 November 2025",
                "50 orang",
                "Aktif",
                true
        ));
        card.add(Box.createVerticalStrut(15));
        card.add(createActivityPanel(
                "Lomba Desain Grafis",
                "20 November 2025",
                "100 orang",
                "Aktif",
                true
        ));
        card.add(Box.createVerticalStrut(15));
        card.add(createActivityPanel(
                "Workshop Penulisan Artikel",
                "26 November 2025",
                "45 orang",
                "Selesai",
                false
        ));

        center.add(card, BorderLayout.CENTER);
        return center;
    }

    private JPanel createActivityPanel(String title,
                                       String date,
                                       String participants,
                                       String status,
                                       boolean canRegister) {
        JPanel activity = new JPanel(new BorderLayout(15, 0)); 
        activity.setBackground(UIConstants.COLOR_CARD);
        activity.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(10, 15, 10, 15) 
        ));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(UIConstants.COLOR_CARD);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.FONT_NORMAL);
        titleLabel.setForeground(UIConstants.COLOR_TEXT_DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(UIConstants.FONT_SMALL);
        dateLabel.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel peopleLabel = new JLabel(participants);
        peopleLabel.setFont(UIConstants.FONT_SMALL);
        peopleLabel.setForeground(UIConstants.COLOR_TEXT_LIGHT);
        peopleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(UIConstants.FONT_SMALL);
        statusLabel.setForeground(
                "Selesai".equalsIgnoreCase(status) ?
                        new Color(150, 150, 150) : new Color(0, 150, 0)
        );
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(dateLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(peopleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(statusLabel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants.COLOR_CARD);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT); 

        JButton detailButton = new JButton("Lihat Detail");
        styleSecondaryButton(detailButton);
        detailButton.setAlignmentX(Component.RIGHT_ALIGNMENT); 

        JButton daftarButton = new JButton("Daftar");
        stylePrimaryGrayButton(daftarButton);
        daftarButton.setEnabled(canRegister);
        daftarButton.setAlignmentX(Component.RIGHT_ALIGNMENT); 

        buttonPanel.add(detailButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(daftarButton);

        activity.add(infoPanel, BorderLayout.CENTER);
        activity.add(buttonPanel, BorderLayout.EAST);

        return activity;
    }

    private JPanel createRightMenu() {
        JPanel menu = new JPanel();
        menu.setBackground(UIConstants.COLOR_PRIMARY);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(20, 20, 20, 20));

        menu.add(createMenuItem("🏠", "Beranda", true));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("🏫", "UKM", false));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("📅", "Kegiatan", false));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("🔔", "Notifikasi", false));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createMenuItem("🚪", "Keluar", false));

        menu.add(Box.createVerticalGlue());
        return menu;
    }

    private JComponent createMenuItem(String iconText, String label, boolean selected) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); 
        item.setOpaque(false);
        item.setBorder(new EmptyBorder(8, 5, 8, 5));

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 20));
        icon.setForeground(Color.WHITE); 

        JLabel text = new JLabel(label);
        text.setFont(UIConstants.FONT_NORMAL);
        text.setForeground(Color.WHITE);

        item.add(icon);
        item.add(text);

        if (selected) {
            item.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 0, Color.WHITE), 
                    new EmptyBorder(8, 5, 8, 5)
            ));
        }

        return item;
    }

    private void styleSecondaryButton(JButton button) {
        button.setFont(UIConstants.FONT_SMALL);
        button.setBackground(UIConstants.COLOR_BUTTON_GRAY);
        button.setForeground(UIConstants.COLOR_TEXT_DARK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true); 
    }

    private void stylePrimaryGrayButton(JButton button) {
        button.setFont(UIConstants.FONT_SMALL);
        button.setBackground(UIConstants.COLOR_BUTTON_GRAY);
        button.setForeground(UIConstants.COLOR_TEXT_DARK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true); 
    }
}
