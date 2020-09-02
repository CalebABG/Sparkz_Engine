package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.engine.Utilities.InputGuard.floatTextfieldGuardDefault;

public class FlowFieldUI {
    public static FlowFieldUI flowFieldUI = null;

    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 18);

    public static float noiseX = 0.008f;
    public static float noiseY = 0.004f;
    public static float noiseZ = 0.002f;
    public static float scaleC1 = 5.0f;
    public static float scalceC2 = 0.6f;
    public static float startAngle = 0.0f;
    public static float velocityLimit = 4.3f;
    public static float velocityMagnitude = 0.2f;

    public JFrame frame;
    public JTextField[] fields = new JTextField[8];


    public static FlowFieldUI getInstance(JFrame parentFrame) {
        if (flowFieldUI == null)
            flowFieldUI = new FlowFieldUI(parentFrame);

        flowFieldUI.frame.toFront();

        return flowFieldUI;
    }

    private FlowFieldUI(JFrame parent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Flow Field Editor");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(350, 440);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JLabel lblFlowFieldEditor = new JLabel("Flow Field Editor");
        lblFlowFieldEditor.setFont(font);
        lblFlowFieldEditor.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setColumnHeaderView(lblFlowFieldEditor);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{147, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{37, 37, 37, 37, 37, 37, 36, 36, 49, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblNewLabel = new JLabel("Noise X Constant");
        lblNewLabel.setFont(font);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel.insets = new Insets(5, 5, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        fields[0] = new JTextField("" + noiseX);
        fields[0].setFont(font);
        GridBagConstraints gbc_noiseXField = new GridBagConstraints();
        gbc_noiseXField.gridwidth = 10;
        gbc_noiseXField.insets = new Insets(0, 0, 5, 0);
        gbc_noiseXField.fill = GridBagConstraints.BOTH;
        gbc_noiseXField.gridx = 1;
        gbc_noiseXField.gridy = 0;
        panel.add(fields[0], gbc_noiseXField);
        fields[0].setColumns(10);

        JLabel lblNoiseYConstant = new JLabel("Noise Y Constant");
        lblNoiseYConstant.setFont(font);
        GridBagConstraints gbc_lblNoiseYConstant = new GridBagConstraints();
        gbc_lblNoiseYConstant.fill = GridBagConstraints.BOTH;
        gbc_lblNoiseYConstant.insets = new Insets(0, 5, 5, 5);
        gbc_lblNoiseYConstant.gridx = 0;
        gbc_lblNoiseYConstant.gridy = 1;
        panel.add(lblNoiseYConstant, gbc_lblNoiseYConstant);

        fields[1] = new JTextField("" + noiseY);
        fields[1].setFont(font);
        fields[1].setColumns(10);
        GridBagConstraints gbc_noiseYField = new GridBagConstraints();
        gbc_noiseYField.gridwidth = 10;
        gbc_noiseYField.insets = new Insets(0, 0, 5, 0);
        gbc_noiseYField.fill = GridBagConstraints.BOTH;
        gbc_noiseYField.gridx = 1;
        gbc_noiseYField.gridy = 1;
        panel.add(fields[1], gbc_noiseYField);

        JLabel lblNoiseZConstant = new JLabel("Noise Z Constant");
        lblNoiseZConstant.setFont(font);
        GridBagConstraints gbc_lblNoiseZConstant = new GridBagConstraints();
        gbc_lblNoiseZConstant.anchor = GridBagConstraints.WEST;
        gbc_lblNoiseZConstant.insets = new Insets(0, 5, 5, 5);
        gbc_lblNoiseZConstant.gridx = 0;
        gbc_lblNoiseZConstant.gridy = 2;
        panel.add(lblNoiseZConstant, gbc_lblNoiseZConstant);

        fields[2] = new JTextField("" + noiseZ);
        fields[2].setFont(font);
        fields[2].setColumns(10);
        GridBagConstraints gbc_noiseZField = new GridBagConstraints();
        gbc_noiseZField.gridwidth = 10;
        gbc_noiseZField.insets = new Insets(0, 0, 5, 0);
        gbc_noiseZField.fill = GridBagConstraints.BOTH;
        gbc_noiseZField.gridx = 1;
        gbc_noiseZField.gridy = 2;
        panel.add(fields[2], gbc_noiseZField);

        JLabel lblScaleConstant = new JLabel("Scale Constant 1");
        lblScaleConstant.setFont(font);
        GridBagConstraints gbc_lblScaleConstant = new GridBagConstraints();
        gbc_lblScaleConstant.anchor = GridBagConstraints.WEST;
        gbc_lblScaleConstant.insets = new Insets(0, 5, 5, 5);
        gbc_lblScaleConstant.gridx = 0;
        gbc_lblScaleConstant.gridy = 3;
        panel.add(lblScaleConstant, gbc_lblScaleConstant);

        fields[3] = new JTextField("" + scaleC1);
        fields[3].setFont(font);
        fields[3].setColumns(10);
        GridBagConstraints gbc_scaleConstant1Field = new GridBagConstraints();
        gbc_scaleConstant1Field.gridwidth = 10;
        gbc_scaleConstant1Field.insets = new Insets(0, 0, 5, 0);
        gbc_scaleConstant1Field.fill = GridBagConstraints.BOTH;
        gbc_scaleConstant1Field.gridx = 1;
        gbc_scaleConstant1Field.gridy = 3;
        panel.add(fields[3], gbc_scaleConstant1Field);

        JLabel lblScaleConstant_1 = new JLabel("Scale Constant 2");
        lblScaleConstant_1.setFont(font);
        GridBagConstraints gbc_lblScaleConstant_1 = new GridBagConstraints();
        gbc_lblScaleConstant_1.anchor = GridBagConstraints.WEST;
        gbc_lblScaleConstant_1.insets = new Insets(0, 5, 5, 5);
        gbc_lblScaleConstant_1.gridx = 0;
        gbc_lblScaleConstant_1.gridy = 4;
        panel.add(lblScaleConstant_1, gbc_lblScaleConstant_1);

        fields[4] = new JTextField("" + scalceC2);
        fields[4].setFont(font);
        fields[4].setColumns(10);
        GridBagConstraints gbc_scaleConstant2Field = new GridBagConstraints();
        gbc_scaleConstant2Field.gridwidth = 10;
        gbc_scaleConstant2Field.insets = new Insets(0, 0, 5, 0);
        gbc_scaleConstant2Field.fill = GridBagConstraints.BOTH;
        gbc_scaleConstant2Field.gridx = 1;
        gbc_scaleConstant2Field.gridy = 4;
        panel.add(fields[4], gbc_scaleConstant2Field);

        JLabel lblStartAngle = new JLabel("Start Angle");
        lblStartAngle.setFont(font);
        GridBagConstraints gbc_lblStartAngle = new GridBagConstraints();
        gbc_lblStartAngle.anchor = GridBagConstraints.WEST;
        gbc_lblStartAngle.insets = new Insets(0, 5, 5, 5);
        gbc_lblStartAngle.gridx = 0;
        gbc_lblStartAngle.gridy = 5;
        panel.add(lblStartAngle, gbc_lblStartAngle);

        fields[5] = new JTextField("" + startAngle);
        fields[5].setFont(font);
        fields[5].setColumns(10);
        GridBagConstraints gbc_startAngleField = new GridBagConstraints();
        gbc_startAngleField.gridwidth = 10;
        gbc_startAngleField.insets = new Insets(0, 0, 5, 0);
        gbc_startAngleField.fill = GridBagConstraints.BOTH;
        gbc_startAngleField.gridx = 1;
        gbc_startAngleField.gridy = 5;
        panel.add(fields[5], gbc_startAngleField);

        JLabel lblVelocityLimit = new JLabel("Velocity Limit");
        lblVelocityLimit.setFont(font);
        GridBagConstraints gbc_lblVelocityLimit = new GridBagConstraints();
        gbc_lblVelocityLimit.anchor = GridBagConstraints.WEST;
        gbc_lblVelocityLimit.insets = new Insets(0, 5, 5, 5);
        gbc_lblVelocityLimit.gridx = 0;
        gbc_lblVelocityLimit.gridy = 6;
        panel.add(lblVelocityLimit, gbc_lblVelocityLimit);

        fields[6] = new JTextField("" + velocityLimit);
        fields[6].setFont(font);
        fields[6].setColumns(10);
        GridBagConstraints gbc_velocityLimitField = new GridBagConstraints();
        gbc_velocityLimitField.insets = new Insets(0, 0, 5, 0);
        gbc_velocityLimitField.gridwidth = 10;
        gbc_velocityLimitField.fill = GridBagConstraints.BOTH;
        gbc_velocityLimitField.gridx = 1;
        gbc_velocityLimitField.gridy = 6;
        panel.add(fields[6], gbc_velocityLimitField);

        JLabel lblVelocityMagnitude = new JLabel("Velocity Magnitude");
        lblVelocityMagnitude.setFont(font);
        GridBagConstraints gbc_lblVelocityMagnitude = new GridBagConstraints();
        gbc_lblVelocityMagnitude.anchor = GridBagConstraints.WEST;
        gbc_lblVelocityMagnitude.insets = new Insets(0, 5, 5, 5);
        gbc_lblVelocityMagnitude.gridx = 0;
        gbc_lblVelocityMagnitude.gridy = 7;
        panel.add(lblVelocityMagnitude, gbc_lblVelocityMagnitude);

        fields[7] = new JTextField("" + velocityMagnitude);
        fields[7].setFont(font);
        fields[7].setColumns(10);
        GridBagConstraints gbc_velocityMagnitudeField = new GridBagConstraints();
        gbc_velocityMagnitudeField.gridwidth = 10;
        gbc_velocityMagnitudeField.insets = new Insets(0, 0, 5, 0);
        gbc_velocityMagnitudeField.fill = GridBagConstraints.BOTH;
        gbc_velocityMagnitudeField.gridx = 1;
        gbc_velocityMagnitudeField.gridy = 7;
        panel.add(fields[7], gbc_velocityMagnitudeField);

        JButton setFlowBtn = new JButton("Set Flow");
        setFlowBtn.addActionListener(e -> setFlow());
        setFlowBtn.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        GridBagConstraints gbc_setFlowBtn = new GridBagConstraints();
        gbc_setFlowBtn.fill = GridBagConstraints.BOTH;
        gbc_setFlowBtn.gridwidth = 11;
        gbc_setFlowBtn.insets = new Insets(0, 5, 4, 5);
        gbc_setFlowBtn.gridx = 0;
        gbc_setFlowBtn.gridy = 8;
        panel.add(setFlowBtn, gbc_setFlowBtn);

        for (JTextField f : fields) {
            f.addKeyListener(new KAdapter.KeyReleased(e -> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    try {
                        setFlow();
                    } catch (Exception g) {
                        EException.append(g);
                    }
            }));
        }

        frame.setVisible(true);
    }

    private void setFlow() {
        noiseX = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), noiseX, fields[0].getText());
        noiseY = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), noiseY, fields[1].getText());
        noiseZ = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), noiseZ, fields[2].getText());
        scaleC1 = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), scaleC1, fields[3].getText());
        scalceC2 = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), scalceC2, fields[4].getText());
        startAngle = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), startAngle, fields[5].getText());
        velocityLimit = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), velocityLimit, fields[6].getText());
        velocityMagnitude = floatTextfieldGuardDefault(-(Float.MIN_VALUE + 1), velocityMagnitude, fields[7].getText());
    }

    private void close() {
        frame.dispose();
        flowFieldUI = null;
    }
}
