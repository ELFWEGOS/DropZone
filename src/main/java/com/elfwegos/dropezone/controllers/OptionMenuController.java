package com.elfwegos.dropezone.controllers;

import com.elfwegos.dropezone.models.ExtensionRule;
import com.elfwegos.dropezone.services.ExtensionManager;
import com.elfwegos.dropezone.services.SaveService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OptionMenuController {

    @FXML
    private VBox extensionContainer;
    @FXML
    Button addExtensionButton;
    @FXML
    private Button folderCreationSaveButton;
    @FXML
    private TextField folderCreationTextField;

    ExtensionManager extensionManager = ExtensionManager.getInstance();
    SaveService saveService = SaveService.getInstance();

    @FXML
    private void initialize() {
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
        System.out.println("Chargemment des 'Save'");
    }

    public void addExtensionBc(ActionEvent event) throws IOException {
        addExtensionButton.setDisable(true);
        HBox hBox = extensionManager.getUiBc();
        Button applyButton = (Button) hBox.getChildren().get(3);
        configureApplyButton(applyButton,hBox);
        Button deleteButton = (Button) hBox.getChildren().get(4);
        configureDeleteButton(deleteButton,hBox);
        extensionContainer.getChildren().add(hBox);

    }

    public void saveFolderCreation(ActionEvent event) throws IOException {
        String folderName = folderCreationTextField.getText();
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
                try {
                    saveService.saveExtensions();
                } catch (IOException e) {
                    System.out.println("NOOON");
                    throw new RuntimeException(e);
                }

                folderChoiceBox.setDisable(true);
                extensionTextField.setDisable(true);
                applyButton.setDisable(true);
                addExtensionButton.setDisable(false);
                System.out.println("all disable");

                extensionManager.addExtensionHbox(hBox);
            }else {
                System.out.println("NON");
            }
        });
    }
    public void configureDeleteButton(Button deleteButton , HBox hBox){
        deleteButton.setOnMouseClicked(event -> {
            int exId =  Integer.parseInt(((Label) hBox.getChildren().get(0)).getText());
            ExtensionRule extensionRule = extensionManager.getExtension(exId);
            extensionManager.deleteExtension(extensionRule);
            extensionContainer.getChildren().remove(hBox);
            extensionManager.deleteExtensionHbox(hBox);
            try {
                saveService.saveExtensions();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(addExtensionButton.isDisable()){
                addExtensionButton.setDisable(false);
            }
        });
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elfwegos/dropezone/MainSceneView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
