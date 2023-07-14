package net.bamboo.combat.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ConfigFile {
    
    private static File file;
    private static File folder = new File("config");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Config INSTANCE;

    public static void initialize() {
        INSTANCE = new Config();
        generate();
        read();
        write();
    }  

    private static void generate() {
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (folder.isDirectory()) {
            file = new File(folder, "bamboo_combat.json");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    INSTANCE = new Config();
                    String json = gson.toJson(INSTANCE);
                    FileWriter writer = new FileWriter(file);
                    writer.write(json);
                    writer.close();
                } catch (IOException exception) {
                    throw new IllegalStateException("Can't create the config file!", exception);
                }
            }
        } else {
            throw new IllegalStateException("The config folder doesn't exist!");
        }

    }

    private static void read() {
        try {
            INSTANCE = gson.fromJson(new FileReader(file), Config.class);
            if (INSTANCE == null) {
                throw new IllegalStateException("The config is null!");
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (JsonSyntaxException exception) {
            exception.printStackTrace();
        } catch (JsonIOException exception) {
            exception.printStackTrace();
        }
    }
    
    private static void write() {
        try {
            String json = gson.toJson(INSTANCE);
            FileWriter writer = new FileWriter(file, false);
            writer.write(json);
            writer.close();
        } catch (IOException exception) {
            throw new IllegalStateException("Can't update the config file!", exception);
        }
    }

}
