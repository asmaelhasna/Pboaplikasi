package com.siskema.gryffindor.ui;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedTextField extends JTextField {

    private int arc = 16;

    public RoundedTextField() {
        super();
        setOpaque(false);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(8, 10, 8, 10));
        setFont(UIConstants.FONT_NORMAL);
        setForeground(UIConstants.COLOR_TEXT_DARK);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if (d.height < 38) d.height = 38;
        return d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(210, 210, 210));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
    }
}
