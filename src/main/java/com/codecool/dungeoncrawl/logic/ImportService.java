package com.codecool.dungeoncrawl.logic;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

public class ImportService {
    public void importJSON(Stage stage){

        FileChooser fileChooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();

        fileChooser.setInitialDirectory(new File(currentPath));

        fileChooser.setTitle("Open dialog");
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("json file", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        String expected = "json";
        if (file != null){
            String fileType = file.getName().substring(file.getName().length() - 4);
            while (!fileType.equals(expected)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Unfortunately the given file is in wrong format. Please try another one!");
                alert.showAndWait();
                file = fileChooser.showOpenDialog(null);
                fileType = file.getName().substring(file.getName().length() - 4);
            }
            try {
                InputStream inputStream = new FileInputStream(file);
                JSONTokener tokener = new JSONTokener(inputStream);
                JSONObject jsonObject = new JSONObject(tokener);
                System.out.println(jsonObject);
            } catch (FileNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }
}
