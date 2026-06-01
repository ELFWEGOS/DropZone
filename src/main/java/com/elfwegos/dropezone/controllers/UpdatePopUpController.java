package com.elfwegos.dropezone.controllers;

import com.elfwegos.dropezone.App;
import com.elfwegos.dropezone.services.LogsManager;
import com.elfwegos.dropezone.services.UpdateService;
import com.elfwegos.dropezone.utils.LogTypes;
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
import java.net.URI;
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
    LogsManager logsManager = LogsManager.getInstance();
    @FXML
    private void initialize() {
        String cloudVersion = String.valueOf(updateService.getCloudVersion());
        versionLabel.setText(cloudVersion);
    }

    public void onYesButtonClicked(ActionEvent event) throws URISyntaxException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage primaryStage = (Stage) stage.getOwner();
        URI uri = null;
        uri = App.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI();
        Path pathDeLancement = Paths.get(uri).getParent();

        String batPath = pathDeLancement.resolve("startUpdater.bat").toString();
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c",batPath );
        processBuilder.directory(pathDeLancement.toFile());

        try {
            logsManager.addLog(LogTypes.INFO,"lancement de l'updater");
            Process process = processBuilder.start();
            logsManager.addLog(LogTypes.SUCCESS,"updater lancé");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        logsManager.addLog(LogTypes.INFO,"APP CLOSED");
        primaryStage.close();
        Platform.exit();
    }
    public void onNoButtonClicked(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
