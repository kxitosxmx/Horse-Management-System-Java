package org.example.javacwui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceController extends SceneController {

    @FXML
    private TextArea selectionOutput;
    @FXML
    private TextArea RaceOutput;

    private List<Horse> horseList = new ArrayList<>();
    private List<Horse> groupAList = new ArrayList<>();
    private List<Horse> groupBList = new ArrayList<>();
    private List<Horse> groupCList = new ArrayList<>();
    private List<Horse> groupDList = new ArrayList<>();

    private List<Horse> bestTimedHorses = new ArrayList<>();
    protected List<Horse> top3Horses = new ArrayList<>();
    protected List<Integer> raceTimes = new ArrayList<>();

    @FXML
    public void switchToHome(MouseEvent event) throws IOException {
        super.switchToHome(event);
    }

    @FXML
    public void initialize() {
        // Load horse data from the file and populate the TextArea
        horseList = loadHorseData("load.txt");

        // Iterate over the horseList and add horses to the corresponding group lists
        for (Horse horse : horseList) {
            String group = horse.getGroup();
            switch (group) {
                case "A":
                    groupAList.add(horse);
                    break;
                case "B":
                    groupBList.add(horse);
                    break;
                case "C":
                    groupCList.add(horse);
                    break;
                case "D":
                    groupDList.add(horse);
                    break;
                default:
                    // Handle invalid group (optional)
                    System.err.println("Invalid group: " + group);
                    break;
            }
        }
    }

    @FXML
    public void selectButtonClicked() {
        // Clear the TextArea before displaying new output
        selectionOutput.clear();

        // Display the race times for each group and find the least time
        bestTimedHorses.add(displayAndFindLeastTime(groupAList, "A"));
        bestTimedHorses.add(displayAndFindLeastTime(groupBList, "B"));
        bestTimedHorses.add(displayAndFindLeastTime(groupCList, "C"));
        bestTimedHorses.add(displayAndFindLeastTime(groupDList, "D"));

        RaceOutput.appendText("Following horses are ready to race:\n");
        RaceOutput.appendText("Horse ID | Name | Jockey | Age | Breed | Wins | Total Race | Group\n");

        // Display the details of the best-time horses (excluding the image)
        for (Horse horse : bestTimedHorses) {
            RaceOutput.appendText(horse.getHorseId() + " | " + horse.getName() + " | " +
                    horse.getJockey() + " | " + horse.getAge() + " | " + horse.getBreed() +
                    " | " + horse.getRaceWon() + " | " + horse.getTotalRace() + " | " + horse.getGroup() + "\n");
        }
    }

    private Horse displayAndFindLeastTime(List<Horse> groupList, String groupName) {
        // Variables to store the least time and corresponding horse for the group
        int leastTime = Integer.MAX_VALUE;
        Horse leastTimeHorse = null;

        // Display the group name
        selectionOutput.appendText("Group " + groupName + ":\n");

        // List to store already generated race times for this group
        List<Integer> raceTimes = new ArrayList<>();

        // Select the least timed horse from the group
        for (Horse horse : groupList) {
            int raceTime = generateUniqueRandomRaceTime(raceTimes);
            selectionOutput.appendText(horse.getHorseId() + " | " + horse.getName() + " | " + raceTime + "\n");
            if (raceTime < leastTime) {
                leastTime = raceTime;
                leastTimeHorse = horse;
            }
        }

        // Display the least time for the group
        selectionOutput.appendText("Least time in Group " + groupName + ": " +
                leastTimeHorse.getHorseId() + " | " + leastTimeHorse.getName() + " | " + leastTime + "\n\n");

        return leastTimeHorse;
    }

    private int generateUniqueRandomRaceTime(List<Integer> raceTimes) {
        Random random = new Random();
        int raceTime;
        do {
            raceTime = random.nextInt(7) * 10 + 30; // Random multiples of 10, ranging from 30 to 90
        } while (raceTimes.contains(raceTime)); // Check if the generated time is already in the list
        raceTimes.add(raceTime); // Add the generated time to the list
        return raceTime;
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

    // Race button

    @FXML
    public void raceButtonClicked() {
        // Clear the RaceOutput before displaying new output
        RaceOutput.clear();

        // Generate new race times for the best-time horses
        List<Integer> raceTimes = generateUniqueRandomRaceTimes(bestTimedHorses.size());

        // Manual sorting of the best-time horses based on their new race times
        sortBestTimedHorses(bestTimedHorses, raceTimes);

        // Display the ranking of the horses in the Race Output
        RaceOutput.appendText("Race Results:\n\n");

        // Start from the 3rd position (index 2) to get the top 3 horses
        int position = 3;
        for (int i = bestTimedHorses.size() - 2; i >= 0 && position > 0; i--) {
            Horse horse = bestTimedHorses.get(i);
            int time = raceTimes.get(i);
            String result = position + "rd horse: " + horse.getHorseId() + " | " + horse.getName() + " | " +
                    horse.getJockey() + " | " + horse.getAge() + " | " + horse.getBreed() +
                    " | " + horse.getRaceWon() + " | " + horse.getTotalRace() + " | " + horse.getGroup() +
                    " | Race Time: " + time + "\n\n";
            position--;

            // Append the race result to the RaceOutput TextArea
            appendWithDelay(result, 1000 * (bestTimedHorses.size() - i));

            // Add the horse to the top 3 list
            top3Horses.add(horse);
        }

        // Now the top3Horses list contains the top 3 horses
    }

    private void appendWithDelay(String text, int delayMillis) {
        // Use a Timeline to introduce a delay between appending texts
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delayMillis), event -> {
            RaceOutput.appendText(text);
        }));
        timeline.play();
    }

    private List<Integer> generateUniqueRandomRaceTimes(int numberOfHorses) {
        Random random = new Random();
        while (raceTimes.size() < numberOfHorses) {
            int raceTime = random.nextInt(9) * 10 + 10; // Random multiples of 10, ranging from 10 to 90
            if (!raceTimes.contains(raceTime)) {
                raceTimes.add(raceTime);
            }
        }
        return raceTimes;
    }

    private void sortBestTimedHorses(List<Horse> horses, List<Integer> raceTimes) {
        // Manual sorting based on race times
        for (int i = 0; i < horses.size() - 1; i++) {
            for (int j = i + 1; j < horses.size(); j++) {
                if (raceTimes.get(i) > raceTimes.get(j)) {
                    // Swap horses
                    Horse tempHorse = horses.get(i);
                    horses.set(i, horses.get(j));
                    horses.set(j, tempHorse);

                    // Swap race times
                    int tempRaceTime = raceTimes.get(i);
                    raceTimes.set(i, raceTimes.get(j));
                    raceTimes.set(j, tempRaceTime);
                }
            }
        }
    }

    // Vishualize button

    @FXML
    public void visualizeButtonClick() throws IOException {
        // Create a FileWriter and BufferedWriter to write to a text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("race_results.txt"))) {
            // Write the header
            writer.write("Race Results:\n");

            // Write the horse names and race times
            int position = 3; // Start from 3rd position
            for (int i = bestTimedHorses.size() - 2; i >= 0 && position > 0; i--) {
                Horse horse = bestTimedHorses.get(i);
                int time = raceTimes.get(i);
                String result = position + "rd horse: " + horse.getName() + " , Race Time: " + time + "\n";
                writer.write(result);
                position--;
            }
        }
        switchToRecord();
    }

    @FXML
    private void ExitButtonClicked() {
        System.exit(0);
    }


}
