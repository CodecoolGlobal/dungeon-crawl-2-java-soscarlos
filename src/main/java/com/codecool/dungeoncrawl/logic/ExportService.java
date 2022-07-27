package com.codecool.dungeoncrawl.logic;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportService {
    public void exportJSON(JSONArray data) {
        Stage exportStage = new Stage();

        JSONObject object = data.getJSONObject(0);
        String payLoad = object.toString();

        Label info = new Label("Do you want to export your game progress?");
        Button btnSave = new Button("Export");

        btnSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(exportStage);

            if (file != null) {
                saveTextToFile(payLoad, file);
                exportStage.close();
            }
        });
        VBox vBox = new VBox(info, btnSave);
        vBox.setAlignment(Pos.CENTER);
        exportStage.setScene(new Scene(vBox, 800, 300));
        exportStage.setTitle("export");
        exportStage.show();
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
