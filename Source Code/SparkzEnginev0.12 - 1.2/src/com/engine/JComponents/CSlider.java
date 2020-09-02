package com.engine.JComponents;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CSlider extends JSlider {

    public CSlider(int min, int max, int init_val, Rectangle bounds,
                   int minor_tick_spacing, int major_tick_spacing,
                   boolean paint_ticks, boolean paint_labels, boolean set_border) {

        super(min, max, init_val);

        setBounds(bounds);
        setMinorTickSpacing(minor_tick_spacing);
        setMajorTickSpacing(major_tick_spacing);
        setPaintTicks(paint_ticks);
        setPaintLabels(paint_labels);

        if (set_border)
            setBorder(new CompoundBorder());
    }
}
