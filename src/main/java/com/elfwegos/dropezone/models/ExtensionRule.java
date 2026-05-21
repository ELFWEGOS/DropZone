package com.elfwegos.dropezone.models;

public class ExtensionRule {
    public int exId;
    public String folderName;
    public String extension;

    //CONSTRUCTOR
    public ExtensionRule(int exId ,String folderName, String extension) {
        this.exId = exId;
        this.folderName = folderName;
        this.extension = extension;
    }

    //GETTERS <> SETTERS
    public String getFolderName() {return folderName;}
    public void setFolderName(String folderName) {this.folderName = folderName;}
    public String getExtension() {return extension;}
    public void setExtension(String extension) {this.extension = extension;}
    public int getId() {return exId;}
}
