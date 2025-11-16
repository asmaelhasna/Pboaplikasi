package com.siskema.gryffindor;

import javax.swing.SwingUtilities;
import javax.swing.UIManager; // <-- IMPORT YANG HILANG
import com.siskema.gryffindor.ui.LoginFrame;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Setel Look and Feel ke system default untuk tampilan yang lebih modern
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Frame yang pertama kali muncul
            new LoginFrame().setVisible(true);
        });
    }
}