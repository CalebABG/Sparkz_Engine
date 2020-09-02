package com.engine.ThinkingParticles;

import com.engine.GUIWindows.ColorEditor;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;

import javax.swing.*;
import java.awt.*;

public class SCPicker {
    private static void pickColor(JFrame parent, int scParticleIndex, String text) {
        Color color = JColorChooser.showDialog(parent, text, null);
        ThinkingColors.COLORS[scParticleIndex] = (color != null) ? color : ThinkingColors.COLORS[scParticleIndex];
        ColorEditor.getInstance().labels[scParticleIndex].setBackground(ThinkingColors.COLORS[scParticleIndex]);
    }

    public static void particleColor(int index) {
        pickColor(ColorEditor.getInstance().frame, index, "Color " + (index + 1));
    }
}
