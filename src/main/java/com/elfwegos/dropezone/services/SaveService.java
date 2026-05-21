package com.elfwegos.dropezone.services;

import com.elfwegos.dropezone.models.ExtensionRule;
import com.elfwegos.dropezone.models.Log;
import com.elfwegos.dropezone.utils.LogTypes;
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
import javafx.collections.ObservableList;


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
    String logs ="";

    Path savePath = Paths.get(appdata,elfwegosCorpo,appName);
    Path directoryNamesPath = savePath.resolve("directoryNames.json");
    Path extensionsPath = savePath.resolve("extensions.json");
    Path logsPath = savePath.resolve("logs.json");

    Gson gson;
    ExtensionManager extensionManager = ExtensionManager.getInstance();
    LogsManager logsManager = LogsManager.getInstance();

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
        if (Files.notExists(logsPath)){
            Files.createFile(logsPath);
        }
    }

    public void saveDirectoryFoldersNames() {
        ArrayList<String> directoryNames = extensionManager.getDirectoryNames();
        String json = gson.toJson(directoryNames);
        try {
            Files.writeString(directoryNamesPath,json);
        } catch (IOException e) {
            logsManager.addLog(LogTypes.ERROR,"failed to save folder names");
            throw new RuntimeException(e);
        }

        logsManager.addLog(LogTypes.SUCCESS,"folder names have been saved");
    }
    public void loadDirectoryFoldersNames() {
        String readFile = null;
        try {
            readFile = Files.readString(directoryNamesPath);
        } catch (IOException e) {
            logsManager.addLog(LogTypes.ERROR,"failed to load folders names");
            throw new RuntimeException(e);
        }
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> directoryNames = gson.fromJson(readFile,type);
        if (directoryNames != null){
            extensionManager.setDirectoryNames(directoryNames);
        }
        logsManager.addLog(LogTypes.SUCCESS,"folders names have been loaded");
    }

    public void saveExtensions() {
        ArrayList<ExtensionRule> extensionRules = extensionManager.getExtensionRules();
            String json = gson.toJson(extensionRules);
        try {
            Files.writeString(extensionsPath,json);
        } catch (IOException e) {
            logsManager.addLog(LogTypes.ERROR,"failed to save extensions");
            throw new RuntimeException(e);
        }
        logsManager.addLog(LogTypes.SUCCESS,"extensions have been saved");
    }
    public void loadExtensions() {
        String json = null;
        try {
            json = Files.readString(extensionsPath);
        } catch (IOException e) {
            logsManager.addLog(LogTypes.ERROR,"failed to load extensions");
            throw new RuntimeException(e);
        }
        Type type = new TypeToken<ArrayList<ExtensionRule>>(){}.getType();
        ArrayList<ExtensionRule> extensionRules = gson.fromJson(json,type);
        if (!extensionRules.isEmpty()){
            extensionManager.setExtensionRules(extensionRules);
            extensionManager.loadHBox();
            //ID
            ExtensionRule lastExtensionRule = extensionRules.get(extensionRules.toArray().length-1);
            extensionManager.setExId(lastExtensionRule.getId()+1);
        }
        logsManager.addLog(LogTypes.SUCCESS,"extensions have been loaded");
    }

    public void saveLogs() {
        ArrayList<Log> logsList = new ArrayList<>(logsManager.getLogsList());
        for (Log log : logsList){
            logs = logs+"\n"+log.toString();
        }
        String json = gson.toJson(logs);
        try {
            Files.writeString(logsPath,json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadLogs() throws IOException {
        String json = Files.readString(logsPath);
        logs = gson.fromJson(json, String.class);
    }

    public void saveAll() throws IOException {
        saveDirectoryFoldersNames();
        saveExtensions();
    }
    public void loadAll() throws IOException {
        loadExtensions();
        loadDirectoryFoldersNames();
        loadLogs();
    }
}
