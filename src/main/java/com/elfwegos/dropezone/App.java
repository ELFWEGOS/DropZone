package com.elfwegos.dropezone;

import com.elfwegos.dropezone.services.SaveService;
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
        String title = "Drope Zone";
        boolean isResizable = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainSceneView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainSceneView.css").toExternalForm());
        root.getStyleClass().add("root");
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(isResizable);
        stage.show();
    }
    public static void main(String[] args) throws IOException {
        SaveService saveService = SaveService.getInstance();
        saveService.initSaveService();
        saveService.loadAll();
        launch();
    }

}
