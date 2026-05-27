package com.elfwegos.dropezone.services;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UpdateService {
    static UpdateService instance;
    public static UpdateService getInstance(){
        if(instance == null){
            instance = new UpdateService();
        }
        return instance;
    }

    SaveService saveService = SaveService.getInstance();
    double localVersion = Double.parseDouble(saveService.loadVersion());
    String rawUrl = "https://raw.githubusercontent.com/ELFWEGOS/DropZone/refs/heads/main/version.txt";
    double cloudVersion;
    Boolean isUpdated;

    public UpdateService(){
        setCloudVersion();
        isUpdated = !isThereAnUpdate();
        System.out.println("CLOUD VERSION : "+cloudVersion);
        System.out.println("IS UPDATED : "+isUpdated);
    }

    public Boolean isThereAnUpdate(){
        if(localVersion<cloudVersion){
            return true;
        }else if (localVersion == cloudVersion){
            return false;
        }else {
            return null;
        }
    }

    private void setCloudVersion(){
        try{
            URI uri = URI.create(rawUrl);
            URL url = uri.toURL();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))){
                String line;
                while ((line = reader.readLine()) != null){
                    this.cloudVersion = Double.parseDouble(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception ignored){}
    }

    public void initUpdatePopup(Stage primaryStage){
        String stageName = "NEW UPDATE";
        Platform.runLater(() -> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elfwegos/dropezone/UpdatePopUpView.fxml"));
            Parent root;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/com/elfwegos/dropezone/UpdatePopUpView.css").toExternalForm());
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle(stageName);
                stage.setAlwaysOnTop(true);
                stage.setOnCloseRequest(Event::consume);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(primaryStage);
                stage.show();
            } catch (IOException ignored) {}
        });
    }

    public void updateApp(){
        //LANCER LE UPDATER ET FERMER DROPE ZONE
    }

    public double getLocalVersion() {return localVersion;}
    public double getCloudVersion() {return cloudVersion;}
}
