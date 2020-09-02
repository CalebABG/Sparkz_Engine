package com.engine.Utilities;

import com.engine.GUIWindows.EException;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.List;
import static com.engine.EngineHelpers.EConstants.toolkit;
import static com.engine.Utilities.ColorUtility.fromHex;

public class Settings {
    public static Image iconImage = toolkit.getImage(Settings.class.getResource("/EngineLogo.png"));
    public static Image splashImage = toolkit.getImage(Settings.class.getResource("/EngineSplash.png"));

    public static String folder_name = "SparkzEngineSettings";
    public static String settings_file_name = "EngineSettings.json";
    public static String colors_file_name = "SavedColors.txt";
    public static String spliceChar = ";";
    public static String engine_settings_folder_path = "." + Paths.get("/" + folder_name);
    public static String settings_file_path = engine_settings_folder_path + "/" + settings_file_name;
    public static String colors_file_path = engine_settings_folder_path + "/" + colors_file_name;

    public static final List<String> userSavedColors = new ArrayList<>(5000);

    public static String getProjectTimespan(){return Period.between(LocalDate.now(), LocalDate.of(2015, Month.NOVEMBER, 22)).toString();}
    public static boolean settingsFileExists(){return Files.exists(Paths.get(settings_file_path));}
    public static boolean colorsFileExists(){return Files.exists(Paths.get(colors_file_path));}

    /**
     * This method returns an array of 5 colors at a particular index of another color array
     *
     */
    public static Color[] convertColors(int index, List<String> list) {
        String[] split = list.get(index).split(spliceChar);
        return new Color[]{fromHex(split[0]), fromHex(split[1]), fromHex(split[2]), fromHex(split[3]), fromHex(split[4])};
    }

    /**
     * This method gets the 5 thinking colors for the particles, converts them to RGB format
     * and then appends them into a txt document.
     */
    public static void saveColors(String color_string) {
        try(Writer writer = new OutputStreamWriter(new FileOutputStream(colors_file_path, true), "UTF-8"))
        {
            new File("./" + folder_name).mkdir();
            if (color_string != null) writer.write(color_string + '\n');
            else writer.write(ColorUtility.getThinkingParticlesStrings(ThinkingColors.COLORS) + '\n');
        }
        catch (Exception e) {EException.append(e);}
    }

    /**
     * This method reads in the text file with particle colors(RGB)
     * It reads each line and splits the line based on the break char.
     */
    public static void loadColors() {
        userSavedColors.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(colors_file_path)))){
            String line;
            while ((line = br.readLine()) != null) userSavedColors.add(line);
        }
        catch (Exception e){EException.append(e);}
    }

    public static void saveSettings() {
        new File("./" + folder_name).mkdir();
        EJsonHelpers.writeEngineSettingsJson();
    }

    public static void loadSettings() {
        if (settingsFileExists()) EJsonHelpers.loadEngineSettingsJson();
        else saveSettings();
    }
}