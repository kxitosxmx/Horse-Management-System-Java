package org.example.javacwui;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class RecordController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    public void initialize() {
        // Set the gap between categories on the X-axis
        barChart.setCategoryGap(20); // Gap between categories (horse names)
        barChart.setBarGap(10); // Gap between bars (race times)
    }

    @FXML
    public void showButtonClick() {
        try (BufferedReader reader = new BufferedReader(new FileReader("race_results.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("Race Results:")) {
                    continue;
                }
                // Split the line into position, horse name, and race time
                String[] parts = line.split(", Race Time: ");
                if (parts.length == 2) {
                    String horseName = parts[0].substring(parts[0].indexOf(":") + 2).trim(); // Extract horse name
                    int raceTime = Integer.parseInt(parts[1].trim()); // Extract race time
                    addToChart(horseName, raceTime);
                } else {
                    // Handle unexpected format of the line
                    System.err.println("Unexpected format of line: " + line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToChart(String horseName, int raceTime) {
        // Check if a series for the horse already exists
        XYChart.Series<String, Number> series = findSeries(horseName);
        // If a series does not exist, create a new one
        if (series == null) {
            series = new XYChart.Series<>();
            series.setName(horseName);
            barChart.getData().add(series);
        }
        // Add the race time to the series
        series.getData().add(new XYChart.Data<>(horseName, raceTime));
    }

    private XYChart.Series<String, Number> findSeries(String horseName) {
        // Search for a series with the given horse name
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            if (series.getName().equals(horseName)) {
                return series;
            }
        }
        return null;
    }

    @FXML
    private void ExitButtonClicked() {
        System.exit(0);
    }
}


