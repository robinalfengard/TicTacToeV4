package com.example.tictactoev4;

import javafx.scene.image.Image;

public class Service {
    Images images = new Images();
    public Image houseSelector(String input) {
        Image house;
        switch (input) {
            case "Ravenclaw" -> house = images.getRavenClawImage();
            case "Gryffindor" -> house = images.getGryffindorImage();
            case "Hufflepuff" -> house = images.getHufflePuffImage();
            case "Slytherin" -> house = images.getSlytherinImage();
            default -> throw new IllegalStateException("Unexpected value: " + input);
        }
        return house;
    }
}
