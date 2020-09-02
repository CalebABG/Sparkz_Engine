package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.Utilities.ColorUtility;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.engine.EngineHelpers.EConstants.EFrame;
import static com.engine.Utilities.H5Util.HCenter;
import static com.engine.Utilities.Settings.convertColors;

public class ColorTimeMachine {
    public static ColorTimeMachine timeMachine = null;

    private static Font font = new Font(Font.SERIF, Font.BOLD, 13);

    public JFrame frame;
    public int index = 0;
    public final String title = "Color Time Machine";
    public CLabel[] labels = new CLabel[5];
    public JToggleButton colorsInfoToggleButton;
    public List<String> colorList = new ArrayList<>();

    //public static void main(String[] args) {getInstance();}

    public static ColorTimeMachine getInstance() {
        if (timeMachine == null)
            timeMachine = new ColorTimeMachine();

        timeMachine.frame.toFront();

        return timeMachine;
    }

    private ColorTimeMachine() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame(title + " - Colors Seen: " + colorList.size());
        frame.setIconImage(Settings.iconImage);
        frame.setSize(688, 206);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(ColorEditor.getInstance() == null ? EFrame : ColorEditor.getInstance().frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel container = new JPanel();
        scrollPane.setViewportView(container);
        container.setLayout(new BorderLayout(0, 0));

        JPanel buttons_panel = new JPanel();
        container.add(buttons_panel, BorderLayout.SOUTH);

        JButton presets = new JButton("Save Colors");
        presets.addActionListener(e -> saveColors());
        presets.setFont(font);
        buttons_panel.add(presets);

        JButton set_colors = new JButton("Set Colors");
        set_colors.setFont(font);
        set_colors.addActionListener(e -> setColors());
        buttons_panel.add(set_colors);

        colorsInfoToggleButton = new JToggleButton("Show Values");
        colorsInfoToggleButton.setFont(font);
        colorsInfoToggleButton.addItemListener(e -> showColorValues());
        buttons_panel.add(colorsInfoToggleButton);

        JButton last = new JButton("Last Color");
        last.addActionListener(e -> colorPager(0));
        last.setFont(font);
        buttons_panel.add(last);

        JButton next = new JButton("Next Color");
        next.setFont(font);
        next.addActionListener(e -> colorPager(1));
        buttons_panel.add(next);

        JButton clear = new JButton("Clear Colors");
        clear.setFont(font);
        clear.addActionListener(e -> clearColors());
        buttons_panel.add(clear);

        JPanel panel = new JPanel();
        container.add(panel, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();
        JPanel panel_4 = new JPanel();
        JPanel panel_5 = new JPanel();

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(GroupLayout.Alignment.LEADING, gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(panel_5, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                                .addContainerGap())
        );
        panel_5.setLayout(new BorderLayout(0, 0));

        labels[4] = new CLabel(font, null, null);
        labels[4].setHorizontalAlignment(SwingConstants.CENTER);
        panel_5.add(labels[4], BorderLayout.CENTER);
        panel_4.setLayout(new BorderLayout(0, 0));

        labels[3] = new CLabel(font, null, null);
        labels[3].setHorizontalAlignment(SwingConstants.CENTER);
        panel_4.add(labels[3], BorderLayout.CENTER);
        panel_3.setLayout(new BorderLayout(0, 0));

        labels[2] = new CLabel(font, null, null);
        labels[2].setHorizontalAlignment(SwingConstants.CENTER);
        panel_3.add(labels[2], BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        labels[1] = new CLabel(font, null, null);
        labels[1].setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(labels[1], BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        labels[0] = new CLabel(font, null, null);
        labels[0].setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(labels[0], BorderLayout.CENTER);
        panel.setLayout(gl_panel);

        updateLabelsBackgroundColors();

        frame.setVisible(true);
    }

    private void setColors() {
        if (colorList.size() > 0) SCChoices.setPresetColors(convertColors(index, colorList));
    }

    private void colorPager(int dir) {
        handleButtons(dir);
        if (colorsInfoToggleButton.isSelected()) updateColorValues();
    }

    private void showColorValues() {
        if (colorsInfoToggleButton.isSelected()) updateColorValues();
        else for (CLabel label : labels) label.setText("");
    }

    private void clearColors() {
        String msg = HCenter("h4", "Clear All Seen Colors?");
        Object[] options = {"Yes, please", "No, cancel!"};
        int n = JOptionPane.showOptionDialog(frame, msg, "Clear Colors", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if (n == JOptionPane.YES_OPTION) {
            colorList.clear();
            index = 0;
            updateLabelsBackgroundColors();
            if (colorsInfoToggleButton.isSelected()) updateColorValues();
            frame.setTitle(title + " - Colors Seen: " + colorList.size());
        }
    }

    private void saveColors() {
        if (colorList == null || colorList.size() <= 0) Settings.saveColors(null);
        else Settings.saveColors(ColorUtility.getThinkingParticlesStrings(convertColors(index, colorList)));
    }

    //Resource: http://www.nbdtech.com/Blog/archive/2008/04/27/Calculating-the-Perceived-Brightness-of-a-Color.aspx
    public static int isDark(Color c) {
        return ((c.getRed() << 5) + (c.getGreen() << 6) + (c.getBlue() << 2)) / 100;
    }

    public void updateColorValues() {
        for (CLabel label : labels) {
            Color lableBGColor = label.getBackground();
            String labelBGText = label.getBGColorText();

            if (isDark(lableBGColor) >= 55) {
                label.setText(labelBGText);
                label.setForeground(Color.black);
            } else {
                label.setText(labelBGText);
                label.setForeground(Color.white);
            }
        }
    }

    private void handleButtons(int dir) {
        //Last color
        if (dir == 0) {
            if (colorList.size() > 0) {
                index--;
                if (index <= 0) index = 0;
                frame.setTitle(title + " - Colors Seen: " + colorList.size() + " - Index: " + index);
                setLabelsBackgroundColor(convertColors(index, colorList));
            }
        }
        //Next color
        else {
            if (colorList.size() > 0) {
                index++;
                if (index >= colorList.size()) index = colorList.size() - 1;
                frame.setTitle(title + " - Colors Seen: " + colorList.size() + " - Index: " + index);
                setLabelsBackgroundColor(convertColors(index, colorList));
            }
        }
    }

    public void addColor(Color[] colors) {
        colorList.add(ColorUtility.getThinkingParticlesStrings(colors));
        index = colorList.size() - 1;

        if (timeMachine != null && labels != null) {
            updateLabelsBackgroundColors();
            frame.setTitle(title + " - Colors Seen: " + colorList.size());
        }
    }

    private void setLabelsBackgroundColor(Color[] colors) {
        labels[0].setBackground(colors[0]);
        labels[1].setBackground(colors[1]);
        labels[2].setBackground(colors[2]);
        labels[3].setBackground(colors[3]);
        labels[4].setBackground(colors[4]);
    }

    private void updateLabelsBackgroundColors() {
        if (colorList.size() > 0) {
            setLabelsBackgroundColor(convertColors(index, colorList));
        } else {
            setLabelsBackgroundColor(ThinkingColors.COLORS);
        }
    }

    private void close() {
        frame.dispose();
        timeMachine = null;
    }
}
