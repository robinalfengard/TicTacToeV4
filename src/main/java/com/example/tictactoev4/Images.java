package com.example.tictactoev4;

import javafx.scene.image.Image;

import java.util.Objects;

public class Images {


    public Image hufflePuffImage = new Image(Objects.requireNonNull(getClass().getResource("Images/huff.jpeg")).toExternalForm());
    public Image slytherinImage = new Image(Objects.requireNonNull(getClass().getResource("Images/slytherin.jpeg")).toExternalForm());
    public Image ravenClawImage = new Image(Objects.requireNonNull(getClass().getResource("Images/ravenclaw.jpg")).toExternalForm());
    public Image gryffindorImage = new Image(Objects.requireNonNull(getClass().getResource("Images/gryffindor.jpg")).toExternalForm());
    public Image userMarker;
    public Image computerMarker;
    public Image availableSpace = new Image(Objects.requireNonNull(getClass().getResource("Images/available.jpeg")).toExternalForm());


    public Image getHufflePuffImage() {
        return hufflePuffImage;
    }

    public Image getSlytherinImage() {
        return slytherinImage;
    }

    public Image getRavenClawImage() {
        return ravenClawImage;
    }

    public Image getGryffindorImage() {
        return gryffindorImage;
    }

    public Image getUserMarker() {
        return userMarker;
    }

    public Image getComputerMarker() {
        return computerMarker;
    }

    public Image getAvailableSpace() {
        return availableSpace;
    }

    public void setUserMarker(Image userMarker) {
        this.userMarker = userMarker;
    }


    public void setOpponentMarker(Image computerMarker) {
        this.computerMarker = computerMarker;
    }
}
