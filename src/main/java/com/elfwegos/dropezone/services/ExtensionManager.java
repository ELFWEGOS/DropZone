package com.elfwegos.dropezone.services;

import com.elfwegos.dropezone.models.ExtensionRule;
import com.elfwegos.dropezone.utils.LogTypes;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ExtensionManager {
    static ExtensionManager instance;
    public static ExtensionManager getInstance(){
        if (instance != null){return instance;}
        instance = new ExtensionManager();
        return instance;
    }

    int exId = 0;
    ArrayList<ExtensionRule> extensionRules = new ArrayList<>();
    ArrayList<HBox> extensionRulesHbox = new ArrayList<>();
    ArrayList<String> directoryNames = new ArrayList<>();
    LogsManager logsManager = LogsManager.getInstance();

    public void addExtension(String folderName, String extension){
        ExtensionRule extensionRule = new ExtensionRule(exId,folderName,extension);
        extensionRules.add(extensionRule);
        exId++;
        logsManager.addLog(LogTypes.SUCCESS,"the extension ["+extension+" -> "+folderName+"] has been added");
    }
    public void addExtensionHbox(HBox hBox){extensionRulesHbox.add(hBox);}
    public void addFolderName(String name){
        directoryNames.add(name);
        logsManager.addLog(LogTypes.SUCCESS,"the directory ["+name+"] has been added");
    }
    public void deleteExtension(ExtensionRule extensionRule){
        logsManager.addLog(LogTypes.SUCCESS,"the extension ["+extensionRule.getExtension()+" -> "+extensionRule.getFolderName()+"] has been deleted");
        extensionRules.remove(extensionRule);
    }
    public void deleteExtensionHbox(HBox hbox){
        extensionRulesHbox.remove(hbox);
    }


//UI
    //CREATED UI
    public HBox getUiBc(){
        HBox hBox = new HBox();
        String idLabelText = String.valueOf(exId);
        Label idLabel = new Label(idLabelText);
        TextField extensionNameTextField = new TextField();
        extensionNameTextField.setPromptText("PUT HERE THE EXTENSION EX: .exe");
        ChoiceBox<String> folderChoiceBox = new ChoiceBox<>();
        folderChoiceBox.getItems().addAll(directoryNames);
        folderChoiceBox.setValue("TEST");
        Button applyButton = new Button("APPLY");
        Button deleteButton = new Button("X");

        hBox.getChildren().addAll(idLabel,extensionNameTextField,folderChoiceBox,applyButton,deleteButton);
        return hBox;
    }
    //LOADED UI
    public void loadHBox(){
        for (ExtensionRule extensionRule : extensionRules){
            HBox hBox = new HBox();
            String idLabelText = String.valueOf(extensionRule.getId());
            Label idLabel = new Label(idLabelText);
            TextField extensionNameTextField = new TextField();
            extensionNameTextField.setText(extensionRule.getExtension());
            extensionNameTextField.setPromptText("PUT HERE THE EXTENSION EX: .exe");
            ChoiceBox<String> folderChoiceBox = new ChoiceBox<>();
            folderChoiceBox.getItems().addAll(directoryNames);
            folderChoiceBox.setValue(extensionRule.getFolderName());
            Button applyButton = new Button("APPLY");
            Button deleteButton = new Button("X");
            hBox.getChildren().addAll(idLabel,extensionNameTextField,folderChoiceBox,applyButton,deleteButton);
            extensionRulesHbox.add(hBox);
        }
    }

    //GETTERS & SETTERS
    public ArrayList<ExtensionRule> getExtensionRules() {
        return this.extensionRules;
    }

    public ArrayList<HBox> getExtensionRulesHbox() {
        return extensionRulesHbox;
    }

    public ArrayList<String> getDirectoryNames() {return directoryNames;}


    public void setDirectoryNames(ArrayList<String> directoryNames) {this.directoryNames = directoryNames;}

    public void setExtensionRules(ArrayList<ExtensionRule> extensionRules) {this.extensionRules = extensionRules;}

    public void setExId(int exId) {this.exId = exId;}

    //SEARCH
    public ExtensionRule getExtension(int exId){
        for(ExtensionRule extensionRule : extensionRules){
            if (extensionRule.getId() == exId ){
                return extensionRule;
            }
        }
        return null;
    }

}
