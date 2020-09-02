package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CTextField;
import com.engine.JComponents.RButton;
import com.engine.JComponents.RLabel;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;

import static com.engine.EngineHelpers.EFLOATS.*;
import static com.engine.Utilities.InputGuard.stringNotNull;

public class ParticleSizeSeed {
    private static ParticleSizeSeed particleSizeSeed = null;

    public JFrame frame;
    public CTextField minTextField, maxTextField;

    //public static void main(String[] args) {getInstance(0);}

    public static ParticleSizeSeed getInstance(int type) {
        if (particleSizeSeed == null)
            particleSizeSeed = new ParticleSizeSeed(type);

        particleSizeSeed.frame.toFront();

        return particleSizeSeed;
    }

    // Type == 0 : singleClickSize; Type == 1 : fireworksSize; Type == 2 : dragSize
    private ParticleSizeSeed(int type) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame(setTitle(type));
        frame.setIconImage(Settings.iconImage);
        frame.setSize(402, 215);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));
        frame.setLocationRelativeTo(OptionsMenu.getInstance().frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        RLabel minSize = new RLabel("Min Size(Integer or Float)", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        minSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minSize, minSize.gridBagConstraints);

        minTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        minTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minTextField, minTextField.gridBagConstraints);

        RLabel maxSize = new RLabel("Max Size(Integer or Float)", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 2);
        maxSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxSize, maxSize.gridBagConstraints);

        maxTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 3, 0, 10, 0, 0);
        maxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxTextField, maxTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 4, 98, 10, 0, 0);
        ok.addActionListener(e -> handleSeed(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 4, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);
        frame.setVisible(true);
    }

    private static String setTitle(int type) {
        if (type == 0) return "Single Click Size Seed";
        else if (type == 1) return "Fireworks Size Seed";
        else if (type == 2) return "Drag Size Seed";
        return null;
    }

    private void handleSeed(int type) {
        try {
            if (type == 0) {
                SINGLE_CLICK_SIZE_MIN.setValue(stringNotNull(minTextField.getText()) ? Float.parseFloat(minTextField.getText()) : SINGLE_CLICK_SIZE_MIN.value());
                SINGLE_CLICK_SIZE_MAX.setValue(stringNotNull(maxTextField.getText()) ? Float.parseFloat(maxTextField.getText()) : SINGLE_CLICK_SIZE_MAX.value());
            } else if (type == 1) {
                FIREWORKS_SIZE_MIN.setValue(stringNotNull(minTextField.getText()) ? Float.parseFloat(minTextField.getText()) : FIREWORKS_SIZE_MIN.value());
                FIREWORKS_SIZE_MAX.setValue(stringNotNull(maxTextField.getText()) ? Float.parseFloat(maxTextField.getText()) : FIREWORKS_SIZE_MAX.value());
            } else if (type == 2) {
                PARTICLE_DRAG_SIZE_MIN.setValue(stringNotNull(minTextField.getText()) ? Float.parseFloat(minTextField.getText()) : PARTICLE_DRAG_SIZE_MIN.value());
                PARTICLE_DRAG_SIZE_MAX.setValue(stringNotNull(maxTextField.getText()) ? Float.parseFloat(maxTextField.getText()) : PARTICLE_DRAG_SIZE_MAX.value());
            }
        } catch (Exception e) {
            EException.append(e);
        } finally {
            close();
        }
    }

    private void close() {
        frame.dispose();
        particleSizeSeed = null;
    }
}