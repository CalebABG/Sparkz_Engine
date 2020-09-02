package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;

import javax.swing.*;

import static com.engine.ThinkingParticles.SCChoices.*;

public class ParticlePresets {
    private static ParticlePresets scPresetsUI = null;
    private JFrame frame;
    private JButton[] presetButtons = new JButton[15];

    public static ParticlePresets getInstance() {
        if (scPresetsUI == null)
            scPresetsUI = new ParticlePresets();

        scPresetsUI.frame.toFront();

        return scPresetsUI;
    }

    private ParticlePresets() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Color Presets :D");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(323, 290);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ColorEditor.getInstance().frame);

        presetButtons[0] = new JButton("Propane");
        presetButtons[0].setBounds(10, 11, 89, 39);
        presetButtons[0].addActionListener(e -> setPresetColors(propaneColor()));
        frame.add(presetButtons[0]);

        presetButtons[1] = new JButton("Maritime");
        presetButtons[1].setBounds(219, 11, 89, 39);
        presetButtons[1].addActionListener(e -> setPresetColors(maritime()));
        frame.add(presetButtons[1]);

        presetButtons[2] = new JButton("Day & Night");
        presetButtons[2].setBounds(109, 11, 100, 39);
        presetButtons[2].addActionListener(e -> setPresetColors(dayAndNight()));
        frame.add(presetButtons[2]);

        presetButtons[3] = new JButton("Plant Life");
        presetButtons[3].setBounds(10, 61, 89, 39);
        presetButtons[3].addActionListener(e -> setPresetColors(plantLife()));
        frame.add(presetButtons[3]);

        presetButtons[4] = new JButton("Coffee");
        presetButtons[4].setBounds(109, 61, 100, 39);
        presetButtons[4].addActionListener(e -> setPresetColors(fancyCoffee()));
        frame.add(presetButtons[4]);

        presetButtons[5] = new JButton("Cinnamon");
        presetButtons[5].setBounds(219, 61, 89, 39);
        presetButtons[5].addActionListener(e -> setPresetColors(cinnamonSpice()));
        frame.add(presetButtons[5]);

        presetButtons[6] = new JButton("Pastels");
        presetButtons[6].setBounds(10, 111, 89, 39);
        presetButtons[6].addActionListener(e -> setPresetColors(pastels()));
        frame.add(presetButtons[6]);

        presetButtons[7] = new JButton("Exotic Berry");
        presetButtons[7].setBounds(109, 111, 100, 39);
        presetButtons[7].addActionListener(e -> setPresetColors(exoticBerries()));
        frame.add(presetButtons[7]);

        presetButtons[8] = new JButton("Citrus");
        presetButtons[8].setBounds(219, 111, 89, 39);
        presetButtons[8].addActionListener(e -> setPresetColors(citrus()));
        frame.add(presetButtons[8]);

        presetButtons[9] = new JButton("Candy");
        presetButtons[9].setBounds(10, 161, 89, 39);
        presetButtons[9].addActionListener(e -> setPresetColors(candy()));
        frame.add(presetButtons[9]);

        presetButtons[10] = new JButton("Ocean");
        presetButtons[10].setBounds(109, 161, 100, 39);
        presetButtons[10].addActionListener(e -> setPresetColors(oceanBlues()));
        frame.add(presetButtons[10]);

        presetButtons[11] = new JButton("Hazy Day");
        presetButtons[11].setBounds(219, 161, 89, 39);
        presetButtons[11].addActionListener(e -> setPresetColors(hazyday()));
        frame.add(presetButtons[11]);

        presetButtons[12] = new JButton("Retro");
        presetButtons[12].setBounds(10, 211, 89, 39);
        presetButtons[12].addActionListener(e -> setPresetColors(retro()));
        frame.add(presetButtons[12]);

        presetButtons[13] = new JButton("Shadow");
        presetButtons[13].setBounds(109, 211, 100, 39);
        presetButtons[13].addActionListener(e -> setPresetColors(shadowslate()));
        frame.add(presetButtons[13]);

        presetButtons[14] = new JButton("Onyx");
        presetButtons[14].setBounds(219, 211, 89, 39);
        presetButtons[14].addActionListener(e -> setPresetColors(onyximpact()));
        frame.add(presetButtons[14]);
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
        scPresetsUI = null;
    }
}
