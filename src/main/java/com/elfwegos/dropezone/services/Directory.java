package com.elfwegos.dropezone.services;

import com.elfwegos.dropezone.models.ExtensionRule;
import com.elfwegos.dropezone.utils.LogTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Directory {
    Path directoryPath;
    ArrayList<ExtensionRule> extensionRules;
    ExtensionManager extensionManager = ExtensionManager.getInstance();
    LogsManager logsManager = LogsManager.getInstance();

    public Directory(String directoryPath){
        this.directoryPath = Paths.get(directoryPath);
        this.extensionRules = new ArrayList<>();
        /*
        this.extensionRules.add(new ExtensionRule("Images",".png"));
        this.extensionRules.add(new ExtensionRule("Images",".jpg"));
        this.extensionRules.add(new ExtensionRule("Icones",".ico"));
        this.extensionRules.add(new ExtensionRule("Texts",".txt"));
        this.extensionRules.add(new ExtensionRule("Archives",".zip"));
        this.extensionRules.add(new ExtensionRule("Archives",".rar"));
        this.extensionRules.add(new ExtensionRule("Torrents",".torrent"));
        this.extensionRules.add(new ExtensionRule("IDM NON FINI",".crdownload"));
        this.extensionRules.add(new ExtensionRule("Executables",".exe"));
        this.extensionRules.add(new ExtensionRule("Documents",".pdf"));
        this.extensionRules.add(new ExtensionRule("FXML's",".fxml"));
        this.extensionRules.add(new ExtensionRule("Musique Bizzare",".m3u8"));
        */
    }

    public void organiseFolder(){
        try (Stream<Path> fluxChemins = Files.list(this.directoryPath)) {
            fluxChemins.forEach(chemin -> {
                File file = chemin.toFile();

                if (!file.isFile()) {
                    logsManager.addLog(LogTypes.WARNING , "["+file.getName()+"] is a directory not a file");
                    return;
                }
                String fileName = file.getName();

                String[] splitedFileName = fileName.split("\\.");

                String fileExtension = "." + splitedFileName[splitedFileName.length-1];

                for (ExtensionRule ext : this.extensionRules) {
                    if (ext.getExtension().equals(fileExtension)) {
                        try {
                            createDossier(ext.getFolderName(), directoryPath.toString());
                            Path source = Paths.get(directoryPath.toString() + "\\" + fileName);
                            Path destination = Paths.get(directoryPath.toString() + "\\" + ext.getFolderName() + "\\" + fileName);

                            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                            logsManager.addLog(LogTypes.SUCCESS,"["+fileName+"] have been moved to ["+ext.getFolderName()+"]");

                        } catch (IOException e) {
                            logsManager.addLog(LogTypes.ERROR,"error while moving the file");
                        }
                    }
                }
            });
        }catch (IOException e) {
            logsManager.addLog(LogTypes.ERROR,"error while reading the directory");
            throw new RuntimeException(e);
        }
    }
    public void createDossier(String name,String path) throws IOException {
        path += "\\"+name; //L'AJOUT DU SOUS DOSSIER AU PATH

        Path directoryPath = Paths.get(path);//TRANSFORMER LE String EN Path
        try{
            if(Files.notExists(directoryPath)){
                Files.createDirectory(directoryPath);//Cree Le Dossier
                logsManager.addLog(LogTypes.SUCCESS,"creation of the directory "+name+" was successful");
            }
        } catch (Exception e) {
            logsManager.addLog(LogTypes.ERROR,"creation of the directory "+name+" failed");
        }
    }
    public void init(){
        extensionRules = extensionManager.getExtensionRules();
    }

}
