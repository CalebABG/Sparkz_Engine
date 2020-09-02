package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;

public class SavedColorsLoader {
    private static SavedColorsLoader savedColorsLoaderUI = null;

    public JFrame frame;
    private int lastIndex = 0;
    private JSlider colorSlider;
    private CLabel[] labels = new CLabel[5];

    //public static void main(String[] args) {getInstance();}

    public static SavedColorsLoader getInstance() {
        if (savedColorsLoaderUI == null) savedColorsLoaderUI = new SavedColorsLoader();

        return savedColorsLoaderUI;
    }

    private SavedColorsLoader() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame();
        frame.setIconImage(Settings.iconImage);
        frame.setSize(523, 155);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ColorEditor.getInstance().frame);

        Settings.loadColors();
        SCChoices.setPresetColors(Settings.convertColors(lastIndex, Settings.userSavedColors));
        frame.setTitle("You have " + Settings.userSavedColors.size() + " Saved Colors :D");

        int offsetX = 69;
        Color fgColor = Color.white;
        Font font = new Font(Font.SERIF, Font.PLAIN, 17);
        labels[0] = new CLabel(new Rectangle(5, 10, 64, 45), font, fgColor, ThinkingColors.COLORS[0]);
        labels[1] = new CLabel(new Rectangle(offsetX + 5, 10, 64, 45), font, fgColor, ThinkingColors.COLORS[1]);
        labels[2] = new CLabel(new Rectangle(2 * offsetX + 5, 10, 64, 45), font, fgColor, ThinkingColors.COLORS[2]);
        labels[3] = new CLabel(new Rectangle(3 * offsetX + 5, 10, 64, 45), font, fgColor, ThinkingColors.COLORS[3]);
        labels[4] = new CLabel(new Rectangle(4 * offsetX + 5, 10, 64, 45), font, fgColor, ThinkingColors.COLORS[4]);
        addComps(frame, labels[0], labels[1], labels[2], labels[3], labels[4]);

        JButton button = new JButton("<html><body style='font-size: 12px'>Refresh Colors</body></html>");
        button.setBounds(350, 15, 160, 40);
        button.setVisible(true);
        button.addActionListener(e -> refreshButton());
        frame.add(button);

        colorSlider = new JSlider(0, Settings.userSavedColors.size() - 1, lastIndex);
        colorSlider.setPaintTicks(true);
        colorSlider.setPaintLabels(true);
        colorSlider.setMinorTickSpacing(setMinorTicks());
        colorSlider.setMajorTickSpacing(setMajorTicks());
        colorSlider.setBounds(10, frame.getHeight() - 85, 500, 43);
        colorSlider.addChangeListener(e -> colorSliderChangeColor());

        frame.add(colorSlider);
        frame.setVisible(true);
    }

    private void colorSliderChangeColor() {
        int sliderIndex = colorSlider.getValue();
        lastIndex = sliderIndex;

        Color[] selectedColors = Settings.convertColors(sliderIndex, Settings.userSavedColors);
        SCChoices.setPresetColors(selectedColors, ThinkingColors.COLORS);
        setLabelColors(selectedColors);
    }

    private void refreshButton() {
        Settings.loadColors();
        colorSlider.setMaximum(Settings.userSavedColors.size() - 1);
        frame.setTitle("You have " + Settings.userSavedColors.size() + " Saved Colors :D");
    }

    private void setLabelColors(Color[] colors) {
        labels[0].setBackground(colors[0]);
        labels[1].setBackground(colors[1]);
        labels[2].setBackground(colors[2]);
        labels[3].setBackground(colors[3]);
        labels[4].setBackground(colors[4]);
    }

    private int setMinorTicks() {
        if (Settings.userSavedColors.size() < 25) {
            return 1;
        } else if (Settings.userSavedColors.size() % 15 == 0) {
            return Settings.userSavedColors.size() / 15;
        } else {
            return Settings.userSavedColors.size() / 25;
        }
    }

    private int setMajorTicks() {
        if (Settings.userSavedColors.size() >= 25) return Settings.userSavedColors.size() / 5;
        return 1;
    }

    private void addComps(JFrame root, JComponent... components) {
        for (JComponent comps : components) root.add(comps);
    }

    private void close() {
        frame.dispose();
        savedColorsLoaderUI = null;
    }
}
