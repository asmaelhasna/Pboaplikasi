package com.siskema.gryffindor;

import javax.swing.SwingUtilities;
import com.siskema.gryffindor.ui.LoginFrame;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
