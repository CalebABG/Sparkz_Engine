package com.engine.ThinkingParticles;

import com.engine.GUIWindows.ColorTimeMachine;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;
import com.engine.Utilities.Settings;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import static com.engine.EngineHelpers.EConstants.random;
import static com.engine.EngineHelpers.EINTS.ENGINE_COLOR_CYCLE_RATE;

public class SCCycle {
    public static Timer time;

    public synchronized static void startCycle() {
        if (Settings.colorsFileExists()) {
            Settings.loadColors();
        }
        // Needs Exception handling

        if (time == null)
            time = new Timer();

        time.scheduleAtFixedRate(new TimerTask() {public void run() {cycleColors();}}, 0, ENGINE_COLOR_CYCLE_RATE.value() * 1000);
    }

    public static void cycleColors() {
        if (!Settings.colorsFileExists()) {
            Color[] randColors = SCChoices.randomColor();
            SCChoices.setPresetColors(randColors);
            ColorTimeMachine.getInstance().addColor(randColors);
        } else regularCycle();
    }

    public static void stopCycle() {
        time.cancel();
        time.purge();
    }

    public static void regularCycle() {
        int index = random.nextInt(Settings.userSavedColors.size());
        SCChoices.setPresetColors(Settings.convertColors(index, Settings.userSavedColors), ThinkingColors.COLORS);
    }
}
