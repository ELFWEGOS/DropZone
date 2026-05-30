package com.elfwegos.dropezone;

import com.elfwegos.dropezone.services.LogsManager;
import com.elfwegos.dropezone.services.SaveService;
import com.elfwegos.dropezone.services.UpdateService;
import com.elfwegos.dropezone.utils.LogTypes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SaveService saveService = SaveService.getInstance();
        LogsManager logsManager = LogsManager.getInstance();

        String title = "Drope Zone";
        System.out.println("==========================");
        System.out.println("         DROPZONE         ");
        System.out.println("==========================");

        boolean isResizable = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainSceneView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainSceneView.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(isResizable);
        stage.setOnCloseRequest(event -> {
            logsManager.addLog(LogTypes.INFO,"APP CLOSED");
            saveService.saveLogs();
        });
        stage.show();
        UpdateService updateService = UpdateService.getInstance();
        if (updateService.isThereAnUpdate()){
            updateService.initUpdatePopup(stage);
        }
    }
    public static void main(String[] args) throws IOException {
        LogsManager logsManager = LogsManager.getInstance();
        SaveService saveService = SaveService.getInstance();
        logsManager.addLog(LogTypes.INFO,"APP LAUNCHED");
        saveService.loadAll();

        launch();
    }

}
