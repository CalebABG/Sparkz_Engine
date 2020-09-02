package com.engine.EngineHelpers;

import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.engine.EngineHelpers.EConstants.toolkit;

public class EngineSplash extends JWindow {
    private int duration;
    private Point mpt = new Point();

    public EngineSplash(int d) {
        duration = d;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {mpt.x = e.getX(); mpt.y = e.getY(); }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation((e.getXOnScreen() - mpt.x), (e.getYOnScreen() - mpt.y));
            }
        });
    }

    public void display() {
        SplashPanel content = new SplashPanel();
        setContentPane(content);
        int w = 434;
        int h = 536;
        Dimension screen =  toolkit.getScreenSize();
        int x = (screen.width - w) / 2;
        int y = (screen.height - h) / 2;
        setBounds(x, y, w, h);
        setVisible(true);
        try {Thread.sleep(duration);}
        catch (Exception e) {EException.append(e);}
        dispose();
    }

    static class SplashPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (Settings.splashImage != null)
                g.drawImage(Settings.splashImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
