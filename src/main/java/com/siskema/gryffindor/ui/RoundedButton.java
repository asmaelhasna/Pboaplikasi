package com.siskema.gryffindor.ui;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedButton extends JButton {

    private int arc = 20;

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setBackground(UIConstants.COLOR_PRIMARY);
        setFont(UIConstants.FONT_SUBTITLE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(8, 24, 8, 24));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if (d.height < 44) d.height = 44;
        if (d.width < 140) d.width = 140;
        return d;
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();

        int textWidth  = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = (getWidth()  - textWidth)  / 2;
        int y = (getHeight() - textHeight) / 2 + fm.getAscent();

        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
