package com.engine.Utilities;

import static com.engine.EngineHelpers.EConstants.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ColorConverter {
    private static Color c = new Color(63, 138, 242);

    public static Color HEXtoRGB(String hex) {
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16),
                Integer.parseInt(hex.substring(3, 5), 16),
                Integer.parseInt(hex.substring(5, 7), 16));
    }

    public static String RGBtoHEX(int r, int g, int b) {
        return "#" +
                String.format("%02X", r).toLowerCase() +
                String.format("%02X", g).toLowerCase() +
                String.format("%02X", b).toLowerCase();
    }

    public static Color HEXAtoRGBA(String s) {
        return new Color(
                Integer.parseInt(s.substring(1, 3), 16),
                Integer.parseInt(s.substring(3, 5), 16),
                Integer.parseInt(s.substring(5, 7), 16),
                Integer.parseInt(s.substring(7, 9), 16)
        );
    }

    /* Custom RGBA To HEX-A*/
    public static String RGBAtoHEXA(int r, int g, int b, int a) {
        return "#" +
                String.format("%02X", r).toLowerCase() +
                String.format("%02X", g).toLowerCase() +
                String.format("%02X", b).toLowerCase() +
                String.format("%02X", a).toLowerCase();
    }

    public static String RGBAtoHEXA(int[] indexes) {
        return "#" +
                String.format("%02X", (indexes[0])).toLowerCase() +
                String.format("%02X", (indexes[1])).toLowerCase() +
                String.format("%02X", (indexes[2])).toLowerCase() +
                String.format("%02X", (indexes[3])).toLowerCase();
    }

    //-------------------------- Helper Functions ------------------------------------//
    public static Color setAlpha(Color c, int alpha) {return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);}

    public static Color getColor() {
        return c;
    }
    public static void setColor() {
        Color l = JColorChooser.showDialog(EFrame, "Particle Color", null); c = (l != null) ? l : c;
    }
    public static void giveBackgroundColor() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.getCause();}
        Color f = JColorChooser.showDialog(EFrame, "Background Color", null); BGColor = (f != null) ? f : BGColor;
    }

    public static Color randHSLColor(){
        Random random = new Random();
        float hue = random.nextFloat();
        // Saturation between 0.1 and 0.3
        float saturation = (random.nextInt(2000) + 1000) / 10000f, luminance = 0.9f;
        return Color.getHSBColor(hue, saturation, luminance);
    }

    public static Color randHSLColor(int minSat, int maxSat, double luminance){
        Random random = new Random();
        float hue = random.nextFloat();
        float saturation = (random.nextInt(maxSat) + minSat) / 10000f;
        return Color.getHSBColor(hue, saturation, (float) luminance);
    }

    public static Color randRGBColor(){return new Color((int)(Math.random() * 0x1000000));}
}