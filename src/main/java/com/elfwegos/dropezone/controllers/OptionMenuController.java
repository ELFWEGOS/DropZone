package com.elfwegos.dropezone.controllers;

import com.elfwegos.dropezone.models.ExtensionRule;
import com.elfwegos.dropezone.services.ExtensionManager;
import com.elfwegos.dropezone.services.LogsManager;
import com.elfwegos.dropezone.services.SaveService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OptionMenuController {

    @FXML
    private VBox extensionContainer;
    @FXML
    private Button addExtensionButton;
    @FXML
    private Button folderCreationSaveButton;
    @FXML
    private TextField folderCreationTextField;
    @FXML
    private ScrollPane scrollPane;

    ExtensionManager extensionManager = ExtensionManager.getInstance();
    SaveService saveService = SaveService.getInstance();

    ChoiceBox currentChoiceBox;

    @FXML
    private void initialize() {
        loadHBox();
        configureFolderNamesListner();
    }

    public void addExtensionBc(ActionEvent event) throws IOException {
        addExtensionButton.setDisable(true);
        HBox hBox = extensionManager.getUiBc();
        currentChoiceBox = (ChoiceBox) hBox.getChildren().get(2);
        Button deleteButton = (Button) hBox.getChildren().get(4);
        configureDeleteButton(deleteButton,hBox);
        Button applyButton = (Button) hBox.getChildren().get(3);
        configureApplyButton(applyButton,hBox);
        extensionContainer.getChildren().add(hBox);
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }

    public void saveFolderCreation(ActionEvent event) throws IOException {
        String folderName = folderCreationTextField.getText();
        if (folderName.isBlank()){return;}
        extensionManager.addFolderName(folderName);
        folderCreationTextField.clear();
        saveService.saveDirectoryFoldersNames();
        System.out.println("ADDED");
    }

    public void configureApplyButton(Button applyButton , HBox hBox){
        applyButton.setOnMouseClicked(event -> {
            ChoiceBox folderChoiceBox = (ChoiceBox) hBox.getChildren().get(2);
            String choiceBoxValue = folderChoiceBox.getValue().toString();

            TextField extensionTextField = (TextField) hBox.getChildren().get(1);
            String extensionTextFieldValue = extensionTextField.getText();

            if (choiceBoxValue != null && !extensionTextFieldValue.isBlank()){
                extensionManager.addExtension(choiceBoxValue,extensionTextFieldValue);
                folderChoiceBox.setDisable(true);
                extensionTextField.setDisable(true);
                applyButton.setDisable(true);
                addExtensionButton.setDisable(false);

                extensionManager.addExtensionHbox(hBox);
            }
        });
    }
    public void configureDeleteButton(Button deleteButton , HBox hBox){
        deleteButton.setOnMouseClicked(event -> {
            int exId =  Integer.parseInt(((Label) hBox.getChildren().getFirst()).getText());
            ExtensionRule extensionRule = extensionManager.getExtension(exId);
            if (extensionRule != null) {
                extensionManager.deleteExtension(extensionRule);
            }
            extensionContainer.getChildren().remove(hBox);
            extensionManager.deleteExtensionHbox(hBox);
            if(addExtensionButton.isDisable()){
                addExtensionButton.setDisable(false);
            }
        });
    }

    public void loadHBox(){
        ArrayList<HBox> createdExtensionHbox = extensionManager.getExtensionRulesHbox();
        for (HBox hBox : createdExtensionHbox){
            extensionContainer.getChildren().add(hBox);
            Button applyButton = (Button) hBox.getChildren().get(3);
            configureApplyButton(applyButton,hBox);
            Button deleteButton = (Button) hBox.getChildren().get(4);
            configureDeleteButton(deleteButton,hBox);
            ChoiceBox folderChoiceBox = (ChoiceBox) hBox.getChildren().get(2);
            TextField extensionTextField = (TextField) hBox.getChildren().get(1);
            folderChoiceBox.setDisable(true);
            extensionTextField.setDisable(true);
            applyButton.setDisable(true);
            addExtensionButton.setDisable(false);
        }
    }
    public void configureFolderNamesListner(){
        extensionManager.getDirectoryNamesForController().addListener((javafx.collections.ListChangeListener<String>) change -> {
            ArrayList<HBox> hBoxes = extensionManager.getExtensionRulesHbox();
            while(change.next()){
                if (change.wasAdded()){
                    if (currentChoiceBox == null){return;}
                    for(String folderName : change.getAddedSubList()){
                        currentChoiceBox.getItems().add(folderName);
                        System.out.println(currentChoiceBox.getItems());
                    }
                }
            }
        });
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        saveService.saveExtensions();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elfwegos/dropezone/MainSceneView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
