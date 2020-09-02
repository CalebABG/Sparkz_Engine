package com.engine.EngineHelpers;

import com.engine.GUIWindows.EException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GUIText {
    public static final String EngineInstructions = getHtml("/HTML/EngineInstructions.html");
    public static final String GeneralOptions = getHtml("/HTML/GenOptions.html");
    public static final String ParticleDrawOptions = getHtml("/HTML/DrawOptions.html");
    public static final String ParticleGraphInstructions = getHtml("/HTML/GraphInstructions.html");
    public static final String ParticleGravitationOptions = getHtml("/HTML/GravOptions.html");
    public static final String ParticleSizeSeedOptions = getHtml("/HTML/ParticleSizeOptions.html");
    public static final String ParticleSpeedSeedOptions = getHtml("/HTML/ParticleSpeedOptions.html");
    public static final String FireworksOptions = getHtml("/HTML/FireworksOptions.html");


    public static String getHtml(String file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(GUIText.class.getResourceAsStream(file)))) {
            StringBuilder lines = new StringBuilder(1024);
            String line;
            while ((line = br.readLine()) != null) lines.append(line);
            return lines.toString();
        } catch (Exception e) {
            EException.append(e);
        }
        return null;
    }
}