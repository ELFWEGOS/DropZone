package com.elfwegos.dropezone.services;

import com.elfwegos.dropezone.models.ExtensionRule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.GsonBuilder;


public class SaveService {
    static SaveService instance;
    public static SaveService getInstance() {
        if (instance != null){return instance;}
        instance = new SaveService();
        return instance;
    }

    String elfwegosCorpo = "ELFWEGOS-APPS";
    String appName = "DropeZone";
    String appdata = System.getenv("APPDATA");

    Path savePath = Paths.get(appdata,elfwegosCorpo,appName);
    Path directoryNamesPath = savePath.resolve("directoryNames.json");
    Path extensionsPath = savePath.resolve("extensions.json");

    Gson gson;
    ExtensionManager extensionManager = ExtensionManager.getInstance();

    public void initSaveService() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        this.gson = builder.create();
        try{
            if(Files.notExists(savePath)){
                Files.createDirectories(savePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (Files.notExists(directoryNamesPath)){
            Files.createFile(directoryNamesPath);
        }
        if (Files.notExists(extensionsPath)){
            Files.createFile(extensionsPath);
        }
    }

    public void saveDirectoryFoldersNames() throws IOException {
        ArrayList<String> directoryNames = extensionManager.getDirectoryNames();
        String json = gson.toJson(directoryNames);
        Files.writeString(directoryNamesPath,json);
    }
    public void loadDirectoryFoldersNames() throws IOException {
        String readFile = Files.readString(directoryNamesPath);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> directoryNames = gson.fromJson(readFile,type);
        if (directoryNames != null){
            extensionManager.setDirectoryNames(directoryNames);
        }
    }

    public void saveExtensions() throws IOException {
        ArrayList<ExtensionRule> extensionRules = extensionManager.getExtensionRules();
            String json = gson.toJson(extensionRules);
            Files.writeString(extensionsPath,json);
    }
    public void loadExtensions() throws IOException {
        String json = Files.readString(extensionsPath);
        Type type = new TypeToken<ArrayList<ExtensionRule>>(){}.getType();
        ArrayList<ExtensionRule> extensionRules = gson.fromJson(json,type);
        if (!extensionRules.isEmpty()){
            extensionManager.setExtensionRules(extensionRules);
            extensionManager.loadHBox();
            //ID
            ExtensionRule lastExtensionRule = extensionRules.get(extensionRules.toArray().length-1);
            extensionManager.setExId(lastExtensionRule.getId()+1);
        }
    }

    public void saveAll() throws IOException {
        saveDirectoryFoldersNames();
        saveExtensions();
    }
    public void loadAll() throws IOException {
        loadExtensions();
        loadDirectoryFoldersNames();
    }
}
