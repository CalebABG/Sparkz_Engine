package com.engine.Utilities;

import com.engine.EngineHelpers.*;
import com.engine.GUIWindows.EException;
import com.engine.JComponents.CMenuBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.*;
import java.util.Map.Entry;
import static com.engine.EngineHelpers.EConstants.*;

public class EJsonHelpers {
    public static final String
            SETTINGS_JSON_OBJECT_KEY = "Settings",
            SETTINGS_JSON_INTS_KEY = "Ints",
            SETTINGS_JSON_DOUBLES_KEY = "Doubles",
            SETTINGS_JSON_BOOLEANS_KEY = "Booleans";

    public static final String
            // Add Properties as needed
            SETTINGS_ENGINE_MODE_PROPERTY = "ENGINE_MODE",
            SETTINGS_PARTICLE_TYPE_PROPERTY = "PARTICLE_TYPE",
            SETTINGS_PARTICLE_GRAVITATION_MODE_PROPERTY = "PARTICLE_GRAVITATION_MODE",
            SETTINGS_PARTICLE_RENDER_MODE_PROPERTY = "PARTICLE_RENDER_MODE",
            SETTINGS_FIREWORKS_RENDER_MODE_PROPERTY = "FIREWORKS_RENDER_MODE";


    public static void writeEngineSettingsJson(){
        String settings = getEngineSettingsJson();
        
        try(FileOutputStream out = new FileOutputStream(Settings.settings_file_path, false);
            Writer writer = new OutputStreamWriter(out, "UTF-8"))
        {
            writer.write(settings);
        }
        catch (Exception e){EException.append(e);}
    }

    public static String getEngineSettingsJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject settingsJson = new JsonObject();
        JsonObject settingsObject = new JsonObject();
        JsonObject ints = new JsonObject();
        JsonObject doubles = new JsonObject();
        JsonObject bools = new JsonObject();

        // Add Enum<Integers> Before others
        ints.addProperty(SETTINGS_ENGINE_MODE_PROPERTY, ENGINE_MODE.getValue());
        ints.addProperty(SETTINGS_PARTICLE_TYPE_PROPERTY, PARTICLE_TYPE.getValue());
        ints.addProperty(SETTINGS_PARTICLE_GRAVITATION_MODE_PROPERTY, PARTICLE_GRAVITATION_MODE.getValue());
        ints.addProperty(SETTINGS_PARTICLE_RENDER_MODE_PROPERTY, PARTICLE_RENDER_MODE.getValue());
        ints.addProperty(SETTINGS_FIREWORKS_RENDER_MODE_PROPERTY, FIREWORKS_RENDER_MODE.getValue());

        // Add other Enums
        EINTS[] eints = EINTS.values();
        for (int i = 0; i < eints.length; i++) {
            if (!eints[i].exclude) {
                EINTS intVar = eints[i];
                ints.addProperty(intVar.name(), intVar.value());
            }
        }

        EFLOATS[] efloats = EFLOATS.values();
        for (int i = 0; i < efloats.length; i++) {
            if (!efloats[i].exclude) {
                EFLOATS floatVar = efloats[i];
                doubles.addProperty(floatVar.name(), floatVar.value());
            }
        }

        EBOOLS[] ebools = EBOOLS.values();
        for (int i = 0; i < ebools.length; i++) {
            if (!ebools[i].exclude) {
                EBOOLS boolVar = ebools[i];
                bools.addProperty(boolVar.name(), boolVar.value());
            }
        }

        settingsObject.add(SETTINGS_JSON_INTS_KEY, ints);
        settingsObject.add(SETTINGS_JSON_DOUBLES_KEY, doubles);
        settingsObject.add(SETTINGS_JSON_BOOLEANS_KEY, bools);
        settingsJson.add(SETTINGS_JSON_OBJECT_KEY, settingsObject);

        return gson.toJson(settingsJson);
    }

    public static boolean loadEngineSettingsJson(){
        //  Try Parsing and setting engine properties
        try(FileInputStream fin = new FileInputStream(Settings.settings_file_path);
            BufferedReader br   = new BufferedReader(new InputStreamReader(fin))) {
            Gson g = new Gson();

            JsonObject settingsJsonObject = g.fromJson(br, JsonObject.class).getAsJsonObject(SETTINGS_JSON_OBJECT_KEY);

            JsonObject intsKey = settingsJsonObject.getAsJsonObject(SETTINGS_JSON_INTS_KEY);
            JsonObject floatsKey = settingsJsonObject.getAsJsonObject(SETTINGS_JSON_DOUBLES_KEY);
            JsonObject boolsKey = settingsJsonObject.getAsJsonObject(SETTINGS_JSON_BOOLEANS_KEY);

            // Set Enum Variables First
            int emode = constrain(ENGINE_MODE.getValue(), intsKey.get(SETTINGS_ENGINE_MODE_PROPERTY).getAsInt(), 0, EModes.ENGINE_MODES.values().length - 1);
            ENGINE_MODE = EModes.ENGINE_MODES.values()[emode];

            int ptype = constrain(PARTICLE_TYPE.getValue(), intsKey.get(SETTINGS_PARTICLE_TYPE_PROPERTY).getAsInt(), 0, EModes.PARTICLE_TYPES.values().length - 1);
            PARTICLE_TYPE = EModes.PARTICLE_TYPES.values()[ptype];

            int pgrav = constrain(PARTICLE_GRAVITATION_MODE.getValue(), intsKey.get(SETTINGS_PARTICLE_GRAVITATION_MODE_PROPERTY).getAsInt(), 0, EModes.GRAVITATION_MODES.values().length - 1);
            PARTICLE_GRAVITATION_MODE = EModes.GRAVITATION_MODES.values()[pgrav];

            int prender = constrain(PARTICLE_RENDER_MODE.getValue(), intsKey.get(SETTINGS_PARTICLE_RENDER_MODE_PROPERTY).getAsInt(), 0, EModes.MOLECULE_RENDER_MODES.values().length - 1);
            PARTICLE_RENDER_MODE = EModes.MOLECULE_RENDER_MODES.values()[prender];

            int frender = constrain(FIREWORKS_RENDER_MODE.getValue(), intsKey.get(SETTINGS_FIREWORKS_RENDER_MODE_PROPERTY).getAsInt(), 0, EModes.MOLECULE_RENDER_MODES.values().length - 1);
            FIREWORKS_RENDER_MODE = EModes.MOLECULE_RENDER_MODES.values()[frender];


            // Set Integer Variables
            int i = 0;
            for (Entry<String, JsonElement> element : intsKey.entrySet()) {
                i++;
                // Offset Setting Properties because indexes 0-5
                if (i > 5){
                    EINTS.valueOf(element.getKey())
                            .setValue(element.getValue().getAsInt());
                }
            }

            // Set Float Variables
            for (Entry<String, JsonElement> element : floatsKey.entrySet()) {
                EFLOATS.valueOf(element.getKey())
                        .setValue(element.getValue().getAsFloat());
            }

            // Set Boolean Variables
            for (Entry<String, JsonElement> element : boolsKey.entrySet()) {
                EBOOLS.valueOf(element.getKey())
                      .setValue(element.getValue().getAsBoolean());
            }

        } catch (Exception e){EException.append(e); return false;}

        //  Set Global Variables
        setGlobalEngineVariables();
        return true;
    }

    public static void setGlobalEngineVariables(){
        EngineMethods.setEngineTitleState();
        menuBar.updateAllRadios();
    }

    public static int constrain(int default_, int value, int min, int max){
        if (value >= min && value <= max) return value;
        else return default_;
    }
}
