package com.elfwegos.dropezone.controllers;

import com.elfwegos.dropezone.App;
import com.elfwegos.dropezone.services.Directory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    Directory directory;

    @FXML
    private void initialize() {}

    public void manageFileBrowser(ActionEvent event){
        String directoryChooserTitle = "Directory to organise";
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(directoryChooserTitle);

        File choosenFile = directoryChooser.showDialog(new Stage());
        String absoluteDirectoryPath = choosenFile.getAbsolutePath();

        pathTextField.setText(absoluteDirectoryPath);

        directory = new Directory(absoluteDirectoryPath);
    }
    public void organiseDirectory(ActionEvent event) throws IOException {
        directory.init();
        directory.organiseFolder();
    }

    public void switchToOptionMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elfwegos/dropezone/OptionMenuView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
