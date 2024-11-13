package org.example.javacwui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class HorseController extends SceneController {

    @FXML
    public void initialize() {
        Agroup.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));
        Ugroup.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));

        loadButtonClicked();

        if (horseList != null) {
            // Populate the choice box with horse IDs from the horseList
            for (Horse horse : horseList) {
                UhorseChoiceBox.getItems().add(horse.getHorseId());
            }

            for (Horse horse : horseList) {
                DhorseChoiceBox.getItems().add(horse.getHorseId());
            }
        }

    }

    // Switch Scene

    @FXML
    public void switchToHome(MouseEvent event) throws IOException {
        super.switchToHome(event);
    }

    @FXML
    public void switchToRace(MouseEvent event) throws IOException {
        super.switchToRace(event);
    }


    @FXML
    public void switchToViewHorse(MouseEvent event) throws IOException {
        super.switchToViewHorse(event);
    }

    //                                  <--- ADDHORSE --->

    // Image loader

    @FXML
    private ImageView AimageView;



    @FXML
    void AbrowseButtonClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(addButton.getScene().getWindow());
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            this.AimageView.setImage(image);
            saveImagePathToTxt(imagePath);
        } else {
            showAlert("No Image Selected", "Please select an image.");
        }
    }

    private void saveImagePathToTxt(String imagePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("imagePath.txt"))) {
            writer.write(imagePath);
            System.out.println("Image path saved to imagePath.txt successfully.");
        } catch (IOException e) {
            System.err.println("Error saving image path to file: " + e.getMessage());
        }
    }

    private String readImagePathFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("imagePath.txt"))) {
            return reader.readLine(); // Read the first line which should contain the image path
        } catch (IOException e) {
            System.err.println("Error reading image path from file: " + e.getMessage());
            return null;
        }
    }

    // Adding

    @FXML
    protected TextField Ahid;

    @FXML
    protected TextField Aname;

    @FXML
    protected TextField Ajockey;

    @FXML
    protected TextField Aage;

    @FXML
    protected TextField Abreed;

    @FXML
    protected TextField AraceWon;

    @FXML
    protected TextField AtotalRaces;

    @FXML
    protected ChoiceBox<String> Agroup;

    @FXML
    private Button addButton;

    protected List<Horse> horseList = new ArrayList<>();

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void addButtonClicked() {
        String horseId = Ahid.getText().trim();
        String name = Aname.getText().trim();
        String jockey = Ajockey.getText().trim();
        String ageStr = Aage.getText().trim();
        String breed = Abreed.getText().trim();
        String racesWonStr = AraceWon.getText().trim();
        String totalRacesStr = AtotalRaces.getText().trim();
        String group = Agroup.getValue(); // Get the selected group from the ChoiceBox
        String imagePath = readImagePathFromFile();

        // Validate all fields
        if (validateHorseId(horseId) && validateName(name) && validateJockey(jockey) &&
                validateAge(ageStr) && validateBreed(breed) && validateRacesWon(racesWonStr) &&
                validateTotalRaces(totalRacesStr)) {

            // Convert validated strings to integers
            int age = Integer.parseInt(ageStr);
            int racesWon = Integer.parseInt(racesWonStr);
            int totalRaces = Integer.parseInt(totalRacesStr);

            // Create new Horse object
            Horse newHorse = new Horse(horseId, name, jockey, age, breed, racesWon, totalRaces, group, imagePath);
            horseList.add(newHorse);

            // Save the new horse data to another .txt file
            appendHorseData("newHorses.txt", newHorse);

            // Clear input fields after adding horse
            clearInputFields();
            clearImagePathFile();

            AimageView.setImage(null);
        }
    }

    // Validation methods
    protected static boolean validateHorseId(String horseId) {
        if (!horseId.matches("\\d+")) {
            showAlert("Error", "Invalid horse ID entered.");
            return false;
        }
        return true;
    }

    protected static boolean validateName(String name) {
        if (!name.matches("[a-zA-Z]+")) {
            showAlert("Error", "Invalid name entered.");
            return false;
        }
        return true;
    }

    protected static boolean validateJockey(String jockey) {
        if (!jockey.matches("[a-zA-Z ]+")) {
            showAlert("Error", "Invalid jockey entered.");
            return false;
        }
        return true;
    }

    protected static boolean validateAge(String ageStr) {
        if (!ageStr.matches("\\d+")) {
            showAlert("Error", "Invalid age entered.");
            return false;
        }
        return true;
    }

    protected static boolean validateBreed(String breed) {
        if (!breed.matches("[a-zA-Z ]+")) {
            showAlert("Error", "Invalid breed entered.");
            return false;
        }
        return true;
    }

    protected static boolean validateRacesWon(String racesWonStr) {
        if (!racesWonStr.matches("\\d+")) {
            showAlert("Error", "Invalid number of races won entered.");
            return false;
        }
        return true;
    }

    protected static boolean validateTotalRaces(String totalRacesStr) {
        if (!totalRacesStr.matches("\\d+")) {
            showAlert("Error", "Invalid total number of races entered.");
            return false;
        }
        return true;
    }

    protected void clearImagePathFile() {
        try (PrintWriter writer = new PrintWriter("ImagePath.txt")) {
            writer.print(""); // Clear the content of the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void appendHorseData(String filename, Horse horse) {
        String data = String.format("%s, %s, %s, %d, %s, %d, %d, %s, %s",
                horse.getHorseId(), horse.getName(), horse.getJockey(),
                horse.getAge(), horse.getBreed(), horse.getRaceWon(),
                horse.getTotalRace(), horse.getGroup(), horse.getImage());

        try (FileWriter writer = new FileWriter(filename, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Horse> loadHorseData(String filename) {
        List<Horse> loadedHorses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    String horseId = parts[0].trim();
                    String name = parts[1].trim();
                    String jockey = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    String breed = parts[4].trim();
                    int racesWon = Integer.parseInt(parts[5].trim());
                    int totalRaces = Integer.parseInt(parts[6].trim());
                    String group = parts[7].trim();
                    String imagePath = parts[8].trim();
                    Horse horse = new Horse(horseId, name, jockey, age, breed, racesWon, totalRaces, group, imagePath);
                    loadedHorses.add(horse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedHorses;
    }

    private void clearInputFields() {
        Ahid.clear();
        Aname.clear();
        Ajockey.clear();
        Aage.clear();
        Abreed.clear();
        AraceWon.clear();
        AtotalRaces.clear();
    }

    // loadButton

    @FXML
    public void loadButtonClicked() {
        horseList = loadHorseData("load.txt");

        // Clear the contents of the newHorses.txt file
        clearFileContents("newHorses.txt");

        // Save the loaded horse data to the newHorses.txt file
        for (Horse horse : horseList) {
            appendHorseData("newHorses.txt", horse);
        }
    }

    private void clearFileContents(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.write(""); // Writing an empty string clears the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // SaveButton

    public void saveButtonClicked() {
        copyFileContents("newHorses.txt", "load.txt");
    }

    private void copyFileContents(String sourceFilename, String destinationFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilename));
             FileWriter writer = new FileWriter(destinationFilename)) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write(System.lineSeparator()); // Write a new line after each line
            }
            System.out.println("Content copied from " + sourceFilename + " to " + destinationFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update Horse

    @FXML
    protected ChoiceBox<String> UhorseChoiceBox;

    @FXML
    protected TextField Uname;

    @FXML
    protected TextField Ujockey;

    @FXML
    protected TextField Uage;

    @FXML
    protected TextField Ubreed;

    @FXML
    protected TextField UraceWon;

    @FXML
    protected TextField UtotalRace;

    @FXML
    protected ChoiceBox<String> Ugroup;

    private String UimageLocation;

    @FXML
    private ImageView UimageView;

    @FXML
    void UbrowseButtonClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(addButton.getScene().getWindow());
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            this.UimageView.setImage(image);
            saveImagePathToTxt(imagePath);
        } else {
            showAlert("No Image Selected", "Please select an image.");
        }
    }

    private void saveAllHorseData(String filename, List<Horse> horses) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Horse horse : horses) {
                String data = String.format("%s, %s, %s, %d, %s, %d, %d, %s, %s",
                        horse.getHorseId(), horse.getName(), horse.getJockey(),
                        horse.getAge(), horse.getBreed(), horse.getRaceWon(),
                        horse.getTotalRace(), horse.getGroup(), horse.getImage());
                writer.println(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateButtonClicked() {
        String selectedHorseId = UhorseChoiceBox.getValue();
        String selectedGroup = Ugroup.getValue(); // Get the selected group value

        if (selectedHorseId != null) {
            // Find the horse with the selected ID
            for (Horse horse : horseList) {
                if (horse.getHorseId().equals(selectedHorseId)) {
                    // Update the horse details with the values from the text fields
                    if (Uname.getText() != null && !Uname.getText().trim().isEmpty()) {
                        String name = Uname.getText().trim();
                        if (name.matches("[a-zA-Z]+")) {
                            horse.setName(name);
                        } else {
                            showAlert("Error", "Invalid name entered.");
                            return;
                        }
                    }
                    if (Ujockey.getText() != null && !Ujockey.getText().trim().isEmpty()) {
                        String jockey = Ujockey.getText().trim();
                        if (jockey.matches("[a-zA-Z]+")) {
                            horse.setJockey(jockey);
                        } else {
                            showAlert("Error", "Invalid jockey entered.");
                            return;
                        }
                    }
                    if (Uage.getText() != null && !Uage.getText().trim().isEmpty()) {
                        String ageStr = Uage.getText().trim();
                        if (ageStr.matches("\\d+")) {
                            horse.setAge(Integer.parseInt(ageStr));
                        } else {
                            showAlert("Error", "Invalid age entered.");
                            return;
                        }
                    }
                    if (Ubreed.getText() != null && !Ubreed.getText().trim().isEmpty()) {
                        String breed = Ubreed.getText().trim();
                        if (breed.matches("[a-zA-Z]+")) {
                            horse.setBreed(breed);
                        } else {
                            showAlert("Error", "Invalid breed entered.");
                            return;
                        }
                    }
                    if (UraceWon.getText() != null && !UraceWon.getText().trim().isEmpty()) {
                        String racesWonStr = UraceWon.getText().trim();
                        if (racesWonStr.matches("\\d+")) {
                            horse.setRaceWon(Integer.parseInt(racesWonStr));
                        } else {
                            showAlert("Error", "Invalid number of races won entered.");
                            return;
                        }
                    }
                    if (UtotalRace.getText() != null && !UtotalRace.getText().trim().isEmpty()) {
                        String totalRacesStr = UtotalRace.getText().trim();
                        if (totalRacesStr.matches("\\d+")) {
                            horse.setTotalRace(Integer.parseInt(totalRacesStr));
                        } else {
                            showAlert("Error", "Invalid total number of races entered.");
                            return;
                        }
                    }
                    // Update the group value only if it is not null
                    if (selectedGroup != null) {
                        horse.setGroup(selectedGroup);
                    }

                    String imagePath = readImagePathFromFile();
                    if (imagePath != null) {
                        horse.setImage(imagePath);
                    }

                    saveAllHorseData("newHorses.txt", horseList);
                    // Display a success message
                    showAlert("Success", "Horse details updated successfully.");

                    //Print for checking

//                    StringBuilder sb = new StringBuilder();
//                    for (Horse hors : horseList) {
//                        sb.append(hors.getHorseId()).append(", ")
//                                .append(hors.getName()).append(", ")
//                                .append(hors.getJockey()).append(", ")
//                                .append(hors.getAge()).append(", ")
//                                .append(hors.getBreed()).append(", ")
//                                .append(hors.getRaceWon()).append(", ")
//                                .append(hors.getTotalRace()).append(", ")
//                                .append(hors.getGroup()).append(", ")
//                                .append(hors.getImage()).append("\n");
//                    }
//                    System.out.println(sb.toString());

                    // Clear input fields
                    clearUpdateFields();
                    UhorseChoiceBox.setValue(null);
                    Ugroup.setValue(null);
                    UimageView.setImage(null);
                    // Exit the method after updating the horse
                    return;
                }
            }
            // If the selected horse ID is not found in the horse list
            showAlert("Error", "Horse ID not found.");
        } else {
            // If no horse ID is selected
            showAlert("Error", "Please select a horse ID.");
        }
    }
    private void clearUpdateFields() {
        Uname.clear();
        Ujockey.clear();
        Uage.clear();
        Ubreed.clear();
        UraceWon.clear();
        UtotalRace.clear();
    }

    // Delete Horse

    @FXML
    protected ChoiceBox<String> DhorseChoiceBox;

    @FXML
    protected void deleteButtonClicked() {
        String selectedHorseId = DhorseChoiceBox.getValue();
        if (selectedHorseId != null) {
            // Find and remove the horse with the selected ID from the horseList
            for (Iterator<Horse> iterator = horseList.iterator(); iterator.hasNext();) {
                Horse horse = iterator.next();
                if (horse.getHorseId().equals(selectedHorseId)) {
                    iterator.remove(); // Remove the horse from the list
                    break; // Exit the loop once the horse is deleted
                }
            }

            // Save the updated horseList to newHorses.txt
            saveAllHorseData("newHorses.txt", horseList);

            // Remove the selected Horse ID from the ChoiceBox
            DhorseChoiceBox.getItems().remove(selectedHorseId);
        }
    }
}

