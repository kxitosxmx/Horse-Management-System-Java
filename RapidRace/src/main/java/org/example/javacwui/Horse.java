package org.example.javacwui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Horse {
    private String horseId;
    private String name;
    private String jockey;
    private int age;
    private String breed;
    private int raceWon;
    private int totalRace;
    private String group;
    private String image;

    public Horse(String horseId, String name, String jockey, int age, String breed, int raceWon, int totalRace,String group, String image) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
        this.breed = breed;
        this.raceWon = raceWon;
        this.totalRace = totalRace;
        this.group = group;
        this.image = image;
    }

    public Horse(String horseId, String name, String jockey, int age, String breed, int raceWon, int totalRace,String group) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
        this.breed = breed;
        this.raceWon = raceWon;
        this.totalRace = totalRace;
        this.group = group;
    }

    public Horse(String horseId, String name, String jockey, int age, String breed, int raceWon, int totalRace) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
        this.breed = breed;
        this.raceWon = raceWon;
        this.totalRace = totalRace;
    }

    public Horse(String horseId, String name,String jockey, int age, String breed, int raceWon) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
        this.breed = breed;
        this.raceWon = raceWon;
    }

    public Horse(String horseId, String name, String jockey, int age, String breed) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
        this.breed = breed;
    }

    public Horse(String horseId, String name, String jockey, int age) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
    }

    public Horse(String horseId, String name, String jockey) {
        this.horseId = horseId;
        this.name = name;
        this.jockey = jockey;
    }

    public Horse(String horseId, String name) {
        this.horseId = horseId;
        this.name = name;
    }

    public Horse(String horseId) {
        this.horseId = horseId;
    }

    public Horse() {

    }

    //Getters and Setters

    public String getHorseId() {
        return horseId;
    }

    public void setHorseId(String horseId) {
        this.horseId = horseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getRaceWon() {
        return raceWon;
    }

    public void setRaceWon(int raceWon) {
        this.raceWon = raceWon;
    }

    public int getTotalRace() {
        return totalRace;
    }

    public void setTotalRace(int totalRace) {
        this.totalRace = totalRace;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // Property Methods

    public StringProperty horseIDProperty() {
        return new SimpleStringProperty(horseId);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty jockeyProperty() {
        return new SimpleStringProperty(jockey);
    }

    public IntegerProperty ageProperty() {
        return new SimpleIntegerProperty(age);
    }

    public StringProperty breedProperty() {
        return new SimpleStringProperty(breed);
    }

    public IntegerProperty racesWonProperty() {
        return new SimpleIntegerProperty(raceWon);
    }

    public IntegerProperty totalRacesProperty() {
        return new SimpleIntegerProperty(totalRace);
    }

    public StringProperty imagePathProperty() {
        return new SimpleStringProperty(image);
    }

    public StringProperty groupProperty() {
        return new SimpleStringProperty(group);
    }


}

