package org.example.javacwui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HomeController extends SceneController{

    @FXML
    public void switchToHorses(MouseEvent event) throws IOException {
        super.switchToHorses(event);
    }
    @FXML
    public void switchToRace(MouseEvent event) throws IOException {
        super.switchToRace(event);
    }

}