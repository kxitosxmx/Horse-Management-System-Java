package org.example.javacwui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewController extends SceneController{
    @FXML
    public void switchToHorses(MouseEvent event) throws IOException {
        super.switchToHorses(event);
    }

    @FXML
    public void switchToRace(MouseEvent event) throws IOException {
        super.switchToRace(event);
    }

    @FXML
    private TableView<Horse> table;

    @FXML
    private TableColumn<Horse, String> id;

    @FXML
    private TableColumn<Horse, String> name;

    @FXML
    private TableColumn<Horse, String> jockey;

    @FXML
    private TableColumn<Horse, Integer> age;

    @FXML
    private TableColumn<Horse, String> breed;

    @FXML
    private TableColumn<Horse, Integer> raceWon;

    @FXML
    private TableColumn<Horse, Integer> totalRace;

    @FXML
    private TableColumn<Horse, String> group;

    @FXML
    private TableColumn<Horse, String> image;

    // Instance variables
    private ObservableList<Horse> horseList;

    @FXML
    private ImageView VimageView;

    private Horse selectedHorse;

    @FXML
    public void initialize() {
        // Initialize table columns
        initializeTableColumns();

        // Load data into the table
        loadDataFromFile("Load.txt");

        // Add row selection listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedHorse = newSelection;
                showImage(selectedHorse.getImage());
            }
        });
    }

    private void initializeTableColumns() {
        id.setCellValueFactory(data -> data.getValue().horseIDProperty());
        name.setCellValueFactory(data -> data.getValue().nameProperty());
        jockey.setCellValueFactory(data -> data.getValue().jockeyProperty());
        age.setCellValueFactory(data -> data.getValue().ageProperty().asObject());
        breed.setCellValueFactory(data -> data.getValue().breedProperty());
        raceWon.setCellValueFactory(data -> data.getValue().racesWonProperty().asObject());
        totalRace.setCellValueFactory(data -> data.getValue().totalRacesProperty().asObject());
        group.setCellValueFactory(data -> data.getValue().groupProperty());
        image.setCellValueFactory(data -> data.getValue().imagePathProperty());
    }

    private void loadDataFromFile(String filename) {
        horseList = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Horse horse = new Horse();
                horse.setHorseId(parts[0].trim());
                horse.setName(parts[1].trim());
                horse.setJockey(parts[2].trim());
                horse.setAge(Integer.parseInt(parts[3].trim()));
                horse.setBreed(parts[4].trim());
                horse.setRaceWon(Integer.parseInt(parts[5].trim()));
                horse.setTotalRace(Integer.parseInt(parts[6].trim()));
                horse.setGroup(parts[7].trim());
                horse.setImage(parts[8].trim());
                horseList.add(horse);
            }
            table.setItems(horseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            VimageView.setImage(image);
        } else {
            // Clear the image view if no image path is available
            VimageView.setImage(null);
        }
    }

    @FXML
    public void sortByHorseId() {
        // Bubble sort algorithm
        for (int i = 0; i < horseList.size() - 1; i++) {
            for (int j = 0; j < horseList.size() - i - 1; j++) {
                // Convert horseIds to integers for comparison
                int id1 = Integer.parseInt(horseList.get(j).getHorseId());
                int id2 = Integer.parseInt(horseList.get(j + 1).getHorseId());

                // Compare horseIds and swap if necessary
                if (id1 > id2) {
                    Horse temp = horseList.get(j);
                    horseList.set(j, horseList.get(j + 1));
                    horseList.set(j + 1, temp);
                }
            }
        }

        // Update the TableView with the sorted list
        table.setItems(horseList);
    }

    @FXML
    public void sortByBreed() {
        // Bubble sort algorithm
        for (int i = 0; i < horseList.size() - 1; i++) {
            for (int j = 0; j < horseList.size() - i - 1; j++) {
                // Compare breeds and swap if necessary
                if (horseList.get(j).getBreed().compareTo(horseList.get(j + 1).getBreed()) > 0) {
                    Horse temp = horseList.get(j);
                    horseList.set(j, horseList.get(j + 1));
                    horseList.set(j + 1, temp);
                }
            }
        }

        // Update the TableView with the sorted list
        table.setItems(horseList);
    }

}

