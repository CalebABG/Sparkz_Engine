package com.engine.GUIWindows;

import com.engine.EngineHelpers.EModes;
import com.engine.EngineHelpers.EngineMethods;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import com.engine.Verlet.Vertex;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EINTS.*;

public class StatsPanel {
    private static StatsPanel statsUI = null;

    public Font font = new Font(Font.SERIF, Font.PLAIN, 25);
    public static int framesPerSec = 0;

    private JFrame frame;
    private Timer timer;
    public Color bgColor = new Color(20, 23, 25).brighter();
    public JPanel panel;
    private CLabel particleAmount, dragamount, ptMode, smartPt, connect, atm, ptFriction, fpsLabel, screenSize;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    //public static void main(String[] args) {getInstance();}

    public static StatsPanel getInstance() {
        if (statsUI == null) statsUI = new StatsPanel();

        return statsUI;
    }

    private StatsPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Stats Panel");
        frame.setSize(730, 450);
        frame.setIconImage(Settings.iconImage);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);

        JMenuBar menuBar = new JMenuBar() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(bgColor);
                g2d.fillRect(0, 0, getWidth() + 1, getHeight() + 1);
            }
        };
        menuBar.setBorder(BorderFactory.createLineBorder(bgColor, 2, false));
        frame.setJMenuBar(menuBar);

        JMenu mnStyleOptions = new JMenu("Style Options");
        mnStyleOptions.setForeground(Color.white);
        mnStyleOptions.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        menuBar.add(mnStyleOptions);

        JMenuItem mntmShowhideLeft = new JMenuItem("Show/Hide Right");
        mnStyleOptions.add(mntmShowhideLeft);

        JMenuItem mntmShowhideRight = new JMenuItem("Show/Hide Left");
        mnStyleOptions.add(mntmShowhideRight);

        JSplitPane split_pane = new JSplitPane();
        split_pane.setContinuousLayout(true);
        split_pane.setDividerSize(2);
        split_pane.setResizeWeight(0.5);

        split_pane.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void setBorder(Border border) {
                    }

                    public void paint(Graphics g) {
                        super.paint(g);
                        g.setColor(Color.black);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
            }
        });
        split_pane.setBorder(null);

        frame.getContentPane().add(split_pane, BorderLayout.CENTER);

        ActionListener actl = e -> {
            int loc = 0;
            JMenuItem source = (JMenuItem) e.getSource();
            if (split_pane.getLeftComponent().isVisible() && split_pane.getRightComponent().isVisible()) {
                split_pane.setDividerSize(0);
                split_pane.getLeftComponent().setVisible(source == mntmShowhideLeft);
                split_pane.getRightComponent().setVisible(source == mntmShowhideRight);
            } else {
                split_pane.getLeftComponent().setVisible(true);
                split_pane.getRightComponent().setVisible(true);
                split_pane.setDividerLocation(loc);
                split_pane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
            }
        };

        mntmShowhideLeft.addActionListener(actl);
        mntmShowhideRight.addActionListener(actl);

        JScrollPane stats_scrollpane = new JScrollPane();
        split_pane.setLeftComponent(stats_scrollpane);

        ///////////

        JPanel stats_panel = new JPanel();
        stats_panel.setBackground(bgColor.darker());
        stats_scrollpane.setViewportView(stats_panel);
        GridBagLayout gbl_stats_panel = new GridBagLayout();
        gbl_stats_panel.columnWidths = new int[]{0, 0};
        gbl_stats_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_stats_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_stats_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        stats_panel.setLayout(gbl_stats_panel);

        particleAmount = new CLabel("Particles: 0", font, Color.white, bgColor);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.weighty = 1.0;
        gbc_label.weightx = 1.0;
        gbc_label.ipady = 10;
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        stats_panel.add(particleAmount, gbc_label);

        dragamount = new CLabel("Drag Amount: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.insets = new Insets(0, 0, 5, 0);
        gbc_label_1.weighty = 1.0;
        gbc_label_1.weightx = 1.0;
        gbc_label_1.fill = GridBagConstraints.BOTH;
        gbc_label_1.ipady = 10;
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 1;
        stats_panel.add(dragamount, gbc_label_1);

        fpsLabel = new CLabel("Frames Per Second: " + ENGINE_FPS.value(), font, Color.white, bgColor);
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.insets = new Insets(0, 0, 5, 0);
        gbc_label_2.weighty = 1.0;
        gbc_label_2.weightx = 1.0;
        gbc_label_2.fill = GridBagConstraints.BOTH;
        gbc_label_2.ipady = 10;
        gbc_label_2.gridx = 0;
        gbc_label_2.gridy = 2;
        stats_panel.add(fpsLabel, gbc_label_2);

        ptMode = new CLabel("Particle Mode: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.insets = new Insets(0, 0, 5, 0);
        gbc_label_3.weighty = 1.0;
        gbc_label_3.weightx = 1.0;
        gbc_label_3.fill = GridBagConstraints.BOTH;
        gbc_label_3.ipady = 10;
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 3;
        stats_panel.add(ptMode, gbc_label_3);

        smartPt = new CLabel("Thinking Particles: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.insets = new Insets(0, 0, 5, 0);
        gbc_label_4.weighty = 1.0;
        gbc_label_4.weightx = 1.0;
        gbc_label_4.fill = GridBagConstraints.BOTH;
        gbc_label_4.ipady = 10;
        gbc_label_4.gridx = 0;
        gbc_label_4.gridy = 4;
        stats_panel.add(smartPt, gbc_label_4);

        connect = new CLabel("Link Mode: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.insets = new Insets(0, 0, 5, 0);
        gbc_label_5.weighty = 1.0;
        gbc_label_5.weightx = 1.0;
        gbc_label_5.fill = GridBagConstraints.BOTH;
        gbc_label_5.ipady = 10;
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 5;
        stats_panel.add(connect, gbc_label_5);

        atm = new CLabel("Mouse Attraction: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_6 = new GridBagConstraints();
        gbc_label_6.insets = new Insets(0, 0, 5, 0);
        gbc_label_6.weighty = 1.0;
        gbc_label_6.weightx = 1.0;
        gbc_label_6.fill = GridBagConstraints.BOTH;
        gbc_label_6.ipady = 10;
        gbc_label_6.gridx = 0;
        gbc_label_6.gridy = 6;
        stats_panel.add(atm, gbc_label_6);

        ptFriction = new CLabel("Particle Friction: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_7 = new GridBagConstraints();
        gbc_label_7.fill = GridBagConstraints.BOTH;
        gbc_label_7.weighty = 1.0;
        gbc_label_7.weightx = 1.0;
        gbc_label_7.ipady = 10;
        gbc_label_7.gridx = 0;
        gbc_label_7.gridy = 7;
        stats_panel.add(ptFriction, gbc_label_7);

        JScrollPane instructions_scrollpane = new JScrollPane();
        split_pane.setRightComponent(instructions_scrollpane);

        JPanel instructions_panel = new JPanel();
        instructions_panel.setBackground(bgColor.darker());
        instructions_scrollpane.setViewportView(instructions_panel);
        GridBagLayout gbl_instructions_panel = new GridBagLayout();
        gbl_instructions_panel.columnWidths = new int[]{0, 0};
        gbl_instructions_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_instructions_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_instructions_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        instructions_panel.setLayout(gbl_instructions_panel);

        CLabel shortCts = new CLabel("Press: (On Sparkz Engine)", new Font(Font.SERIF, Font.PLAIN, 26), Color.white, bgColor);
        GridBagConstraints gbc_label_8 = new GridBagConstraints();
        gbc_label_8.weighty = 1.0;
        gbc_label_8.weightx = 1.0;
        gbc_label_8.ipady = 10;
        gbc_label_8.fill = GridBagConstraints.BOTH;
        gbc_label_8.insets = new Insets(0, 0, 5, 0);
        gbc_label_8.gridx = 0;
        gbc_label_8.gridy = 0;
        instructions_panel.add(shortCts, gbc_label_8);

        CLabel a = new CLabel("Esc = Exit Program", font, Color.white, bgColor);
        GridBagConstraints gbc_label_9 = new GridBagConstraints();
        gbc_label_9.weighty = 1.0;
        gbc_label_9.weightx = 1.0;
        gbc_label_9.ipady = 10;
        gbc_label_9.fill = GridBagConstraints.BOTH;
        gbc_label_9.insets = new Insets(0, 0, 5, 0);
        gbc_label_9.gridx = 0;
        gbc_label_9.gridy = 1;
        instructions_panel.add(a, gbc_label_9);

        CLabel b = new CLabel("Q = Engine Instructions", font, Color.white, bgColor);
        GridBagConstraints gbc_label_10 = new GridBagConstraints();
        gbc_label_10.weighty = 1.0;
        gbc_label_10.weightx = 1.0;
        gbc_label_10.ipady = 10;
        gbc_label_10.fill = GridBagConstraints.BOTH;
        gbc_label_10.insets = new Insets(0, 0, 5, 0);
        gbc_label_10.gridx = 0;
        gbc_label_10.gridy = 2;
        instructions_panel.add(b, gbc_label_10);

        CLabel c = new CLabel("7 = Options Menu", font, Color.white, bgColor);
        GridBagConstraints gbc_label_11 = new GridBagConstraints();
        gbc_label_11.weighty = 1.0;
        gbc_label_11.weightx = 1.0;
        gbc_label_11.ipady = 10;
        gbc_label_11.fill = GridBagConstraints.BOTH;
        gbc_label_11.insets = new Insets(0, 0, 5, 0);
        gbc_label_11.gridx = 0;
        gbc_label_11.gridy = 3;
        instructions_panel.add(c, gbc_label_11);

        CLabel e = new CLabel("W = Particle Color Editor", font, Color.white, bgColor);
        GridBagConstraints gbc_label_12 = new GridBagConstraints();
        gbc_label_12.weighty = 1.0;
        gbc_label_12.weightx = 1.0;
        gbc_label_12.ipady = 10;
        gbc_label_12.fill = GridBagConstraints.BOTH;
        gbc_label_12.insets = new Insets(0, 0, 5, 0);
        gbc_label_12.gridx = 0;
        gbc_label_12.gridy = 4;
        instructions_panel.add(e, gbc_label_12);

        CLabel d = new CLabel("R = Change Background Color", font, Color.white, bgColor);
        GridBagConstraints gbc_label_13 = new GridBagConstraints();
        gbc_label_13.weighty = 1.0;
        gbc_label_13.weightx = 1.0;
        gbc_label_13.ipady = 10;
        gbc_label_13.fill = GridBagConstraints.BOTH;
        gbc_label_13.insets = new Insets(0, 0, 5, 0);
        gbc_label_13.gridx = 0;
        gbc_label_13.gridy = 5;
        instructions_panel.add(d, gbc_label_13);

        screenSize = new CLabel("Canvas Size: 0 x 0", font, Color.white, bgColor);
        GridBagConstraints gbc_label_14 = new GridBagConstraints();
        gbc_label_14.weighty = 1.0;
        gbc_label_14.weightx = 1.0;
        gbc_label_14.ipady = 15;
        gbc_label_14.fill = GridBagConstraints.BOTH;
        gbc_label_14.insets = new Insets(0, 0, 5, 0);
        gbc_label_14.gridx = 0;
        gbc_label_14.gridy = 6;
        instructions_panel.add(screenSize, gbc_label_14);

        startTimer();
        frame.setVisible(true);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                update();
            }
        }, 0, 16); // 16ms = 60 updates per second
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    public void update() {
        try {
            if (fpsLabel != null) fpsLabel.setText("Frames Per Second: " + framesPerSec);
            if (particleAmount != null) {
                if (ENGINE_MODE == EModes.ENGINE_MODES.RAGDOLL_MODE)
                    particleAmount.setText("Points: " + decimalFormat.format(Vertex.Vertices.size()));
                else particleAmount.setText("Particles: " + decimalFormat.format(
                        ParticlesArray.size() + GravityPointsArray.size() + FireworksArray.size() +
                                EmitterArray.size() + QEDArray.size() + IonArray.size() +
                                FluxArray.size() + EraserArray.size() + BlackHoleArray.size() +
                                DuplexArray.size() + PortalArray.size()));
            }
            if (dragamount != null)
                dragamount.setText("Drag Amount: " + decimalFormat.format(PARTICLE_DRAG_AMOUNT.value()));
            if (ptMode != null) ptMode.setText(EngineMethods.getModeText());
            if (smartPt != null) smartPt.setText(EngineMethods.getThinkingText());
            if (connect != null) connect.setText("Link Mode: " + EngineMethods.getConnectText());
            if (atm != null) atm.setText(EngineMethods.getMouseAttraction());
            if (ptFriction != null) ptFriction.setText(EngineMethods.getFrictionText());
            if (screenSize != null)
                screenSize.setText(String.format("Canvas Size: %d x %d", canvas.getWidth(), canvas.getHeight()));
        } catch (Exception ex) {
            EException.append(ex);
        }
    }

    private void close() {
        stopTimer();
        frame.dispose();
        statsUI = null;
    }
}