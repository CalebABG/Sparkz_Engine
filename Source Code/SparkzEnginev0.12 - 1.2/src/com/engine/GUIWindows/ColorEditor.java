package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.ThinkingParticles.SCCycle;
import com.engine.ThinkingParticles.SCPicker;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EBOOLS.ENGINE_CYCLE_COLORS;

public class ColorEditor {
    private static ColorEditor thinkingParticlesUI = null;

    public Font font = new Font(Font.SERIF, Font.PLAIN, 18);
    public Font uiFont = new Font(Font.SERIF, Font.BOLD, 16);

    public JFrame frame;
    public CLabel[] labels = new CLabel[5];

    //public static void main(String[] args) {getInstance();}

    public static ColorEditor getInstance() {
        if (thinkingParticlesUI == null)
            thinkingParticlesUI = new ColorEditor();

        thinkingParticlesUI.frame.toFront();

        return thinkingParticlesUI;
    }

    private ColorEditor() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Thinking Particles Color Changer");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(950, 250);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(0, 0));
        scrollPane.setViewportView(container);

        JPanel buttons_panel = new JPanel();
        container.add(buttons_panel, BorderLayout.SOUTH);

        JButton presets = new JButton("Presets");
        presets.setFont(uiFont);
        presets.addActionListener(e -> ParticlePresets.getInstance());
        buttons_panel.add(presets);

        JButton random_colors = new JButton("Random Colors");
        random_colors.setFont(uiFont);
        random_colors.addActionListener(e -> setRandomColors());
        buttons_panel.add(random_colors);

        JButton default_colors = new JButton("Default Colors");
        default_colors.setFont(uiFont);
        default_colors.addActionListener(e -> SCChoices.setPresetColors(SCChoices.defaultColor()));
        buttons_panel.add(default_colors);

        JButton cycle_colors = new JButton("Cycle Colors: " + EngineMethods.getCycle());
        cycle_colors.setFont(uiFont);
        cycle_colors.addActionListener(e -> handleColorCycle(cycle_colors));
        buttons_panel.add(cycle_colors);

        JButton save_colors = new JButton("Save Colors");
        save_colors.setFont(uiFont);
        save_colors.addActionListener(e -> Settings.saveColors(null));
        buttons_panel.add(save_colors);

        JButton load_colors = new JButton("Load Colors");
        load_colors.setFont(uiFont);
        load_colors.addActionListener(e -> loadColors());
        buttons_panel.add(load_colors);

        JButton time_machine = new JButton("Time Machine");
        time_machine.addActionListener(e -> ColorTimeMachine.getInstance());
        time_machine.setFont(uiFont);
        buttons_panel.add(time_machine);

        JPanel colors_panel = new JPanel();
        container.add(colors_panel, BorderLayout.CENTER);

        JPanel color_1_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_2_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_3_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_4_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_5_panel = new JPanel(new BorderLayout(0, 0));

        GroupLayout gl_colors_panel = new GroupLayout(colors_panel);
        gl_colors_panel.setHorizontalGroup(
                gl_colors_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_colors_panel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(color_1_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_2_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_3_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_4_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_5_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_colors_panel.setVerticalGroup(
                gl_colors_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_colors_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_colors_panel.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(color_5_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_4_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_3_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_2_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_1_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                                .addContainerGap())
        );

        JButton color_5_button = new JButton("Change Color");
        color_5_button.setFont(uiFont);
        color_5_button.addActionListener(e -> SCPicker.particleColor(4));
        color_5_panel.add(color_5_button, BorderLayout.SOUTH);

        JPanel color_5_inner_panel = new JPanel();
        color_5_panel.add(color_5_inner_panel, BorderLayout.CENTER);
        color_5_inner_panel.setLayout(new BorderLayout(0, 0));

        Color fgColor = Color.white;
        labels[4] = new CLabel(font, fgColor, ThinkingColors.COLORS[4]);
        labels[4].setHorizontalAlignment(SwingConstants.CENTER);
        color_5_inner_panel.add(labels[4], BorderLayout.CENTER);

        JButton color_4_button = new JButton("Change Color");
        color_4_button.setFont(uiFont);
        color_4_button.addActionListener(e -> SCPicker.particleColor(3));
        color_4_panel.add(color_4_button, BorderLayout.SOUTH);

        JPanel color_4_inner_panel = new JPanel();
        color_4_panel.add(color_4_inner_panel, BorderLayout.CENTER);
        color_4_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[3] = new CLabel(font, fgColor, ThinkingColors.COLORS[3]);
        labels[3].setHorizontalAlignment(SwingConstants.CENTER);
        color_4_inner_panel.add(labels[3], BorderLayout.CENTER);

        JButton color_3_button = new JButton("Change Color");
        color_3_button.setFont(uiFont);
        color_3_button.addActionListener(e -> SCPicker.particleColor(2));
        color_3_panel.add(color_3_button, BorderLayout.SOUTH);

        JPanel color_3_inner_panel = new JPanel();
        color_3_panel.add(color_3_inner_panel, BorderLayout.CENTER);
        color_3_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[2] = new CLabel(font, fgColor, ThinkingColors.COLORS[2]);
        labels[2].setHorizontalAlignment(SwingConstants.CENTER);
        color_3_inner_panel.add(labels[2], BorderLayout.CENTER);

        JButton color_2_button = new JButton("Change Color");
        color_2_button.setFont(uiFont);
        color_2_button.addActionListener(e -> SCPicker.particleColor(1));
        color_2_panel.add(color_2_button, BorderLayout.SOUTH);

        JPanel color_2_inner_panel = new JPanel();
        color_2_panel.add(color_2_inner_panel, BorderLayout.CENTER);
        color_2_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[1] = new CLabel(font, fgColor, ThinkingColors.COLORS[1]);
        labels[1].setHorizontalAlignment(SwingConstants.CENTER);
        color_2_inner_panel.add(labels[1], BorderLayout.CENTER);

        JButton color_1_button = new JButton("Change Color");
        color_1_button.setFont(uiFont);
        color_1_button.addActionListener(e -> SCPicker.particleColor(0));
        color_1_panel.add(color_1_button, BorderLayout.SOUTH);

        JPanel color_1_inner_panel = new JPanel();
        color_1_panel.add(color_1_inner_panel, BorderLayout.CENTER);
        color_1_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[0] = new CLabel(font, fgColor, ThinkingColors.COLORS[0]);
        labels[0].setHorizontalAlignment(SwingConstants.CENTER);
        color_1_inner_panel.add(labels[0], BorderLayout.CENTER);
        colors_panel.setLayout(gl_colors_panel);

        frame.setVisible(true);
    }

    private void loadColors() {
        if (Settings.colorsFileExists()) SavedColorsLoader.getInstance();
        else
            JOptionPane.showConfirmDialog(frame, "<html><h3>Save a Color First</h3></html>", "No Colors Saved", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    private void setRandomColors() {
        Color[] colors = SCChoices.randomColor();

        SCChoices.setPresetColors(colors);
        ColorTimeMachine.getInstance().addColor(colors);

        if (ColorTimeMachine.timeMachine != null && ColorTimeMachine.getInstance().colorsInfoToggleButton.isSelected())
            ColorTimeMachine.getInstance().updateColorValues();
    }

    public void handleColorCycle(JButton cycle_colors) {
        ENGINE_CYCLE_COLORS.toggleValue();

        if (ENGINE_CYCLE_COLORS.value()) {
            SCCycle.startCycle();
            cycle_colors.setText("Cycle Colors: On");
        } else {
            SCCycle.stopCycle();
            cycle_colors.setText("Cycle Colors: Off");
        }
    }

    public static void setBackgroundColor(Color[] colors) {
        if (thinkingParticlesUI != null && thinkingParticlesUI.labels != null) {
            thinkingParticlesUI.labels[0].setBackground(colors[0]);
            thinkingParticlesUI.labels[1].setBackground(colors[1]);
            thinkingParticlesUI.labels[2].setBackground(colors[2]);
            thinkingParticlesUI.labels[3].setBackground(colors[3]);
            thinkingParticlesUI.labels[4].setBackground(colors[4]);
        }
    }

    public void close() {
        frame.dispose();
        thinkingParticlesUI = null;
    }
}
