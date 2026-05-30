package com.elfwegos.dropezone.controllers;

import com.elfwegos.dropezone.App;
import com.elfwegos.dropezone.services.UpdateService;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class UpdatePopUpController {
    @FXML
    private Button noButton;
    @FXML
    private Label versionLabel;
    @FXML
    private Button yesButton;

    UpdateService updateService = UpdateService.getInstance();

    @FXML
    private void initialize() {
        String cloudVersion = String.valueOf(updateService.getCloudVersion());
        versionLabel.setText(cloudVersion);
    }

    public void onYesButtonClicked(ActionEvent event) throws URISyntaxException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage primaryStage = (Stage) stage.getOwner();
        String cheminDeLancement = App.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        Path pathDeLancement = Paths.get(cheminDeLancement).getParent();
        Path pathDeUpdater = pathDeLancement.resolve("Updater.jar");
        ProcessBuilder processBuilder = new ProcessBuilder("java","-jar",String.valueOf(pathDeUpdater));
        try{
            Process process = processBuilder.start();
        }catch (IOException ignored){}
        primaryStage.close();
        Platform.exit();
        System.exit(0);
    }
    public void onNoButtonClicked(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
