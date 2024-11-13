package org.example.javacwui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private final int width = 1280;
    private final int height = 700;

    private void switchScene(String fxmlFileName, MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RapidRaceApplication.class.getResource(fxmlFileName));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHome(MouseEvent event) throws IOException {
        switchScene("Home.fxml", event);
    }

    public void switchToHorses(MouseEvent event) throws IOException {
        switchScene("Horses.fxml", event);
    }

    public void switchToRace(MouseEvent event) throws IOException {
        switchScene("Race.fxml", event);
    }

    public void switchToViewHorse(MouseEvent event) throws IOException {
        switchScene("ViewHorse.fxml", event);
    }

    public void switchToRecord() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RapidRaceApplication.class.getResource("Record.fxml"));
        stage = new Stage();
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Visualize");
        stage.setScene(scene);
        stage.show();
    }


}
