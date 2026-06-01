package com.elfwegos.dropezone.controllers;

import com.elfwegos.dropezone.App;
import com.elfwegos.dropezone.models.Log;
import com.elfwegos.dropezone.services.Directory;
import com.elfwegos.dropezone.services.LogsManager;
import com.elfwegos.dropezone.services.UpdateService;
import com.elfwegos.dropezone.utils.LogTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;

public class MainSceneController {

    @FXML
    TextField pathTextField;
    @FXML
    Button pathButton;
    @FXML
    TextArea logsTextArea;
    @FXML
    Label versionLabel;

    Directory directory;
    LogsManager logsManager = LogsManager.getInstance();
    UpdateService updateService = UpdateService.getInstance();

    @FXML
    private void initialize() {
        versionLabel.setText("version : "+updateService.getLocalVersion());
        logsTextArea.setEditable(false);
        logsManager.getLogsList().addListener((javafx.collections.ListChangeListener<Log>) change -> {
            while (change.next()){
                if (change.wasAdded()){
                    for (Log log : change.getAddedSubList()){
                        addLog(log.toString());
                    }
                    logsTextArea.setScrollTop(Double.MAX_VALUE);
                    logsTextArea.setScrollLeft(Double.MIN_VALUE);
                }
            }
        });
        //DES LE LANCEMMENT DE LAPP
        for (Log log : logsManager.getLogsList()) {
            logsTextArea.appendText(log + "\n");
        }
    }

    public void manageFileBrowser(ActionEvent event){
        String directoryChooserTitle = "Directory to organise";
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(directoryChooserTitle);

        File choosenFile = directoryChooser.showDialog(new Stage());
        if(choosenFile == null){
            logsManager.addLog(LogTypes.WARNING,"PLS CHOOSE A FILE");
            return;
        }
        String absoluteDirectoryPath = choosenFile.getAbsolutePath();
        if(absoluteDirectoryPath.isBlank()){
            logsManager.addLog(LogTypes.ERROR,"Please choose a folder to organize");
            return;
        }
        pathTextField.setText(absoluteDirectoryPath);

        directory = new Directory(absoluteDirectoryPath);
    }
    public void organiseDirectory(ActionEvent event) throws IOException {
        directory.init();
        directory.organiseFolder();
    }
    public void addLog(String text){
        logsTextArea.appendText("\n"+text);
    }

    public void switchToOptionMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elfwegos/dropezone/OptionMenuView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
