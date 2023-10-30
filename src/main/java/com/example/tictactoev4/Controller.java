package com.example.tictactoev4;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    public ComboBox<String> houseComboBoxForUser;
    public ComboBox<String> houseComboBoxForOpponent;

    Model model = new Model();

    public void initialize() {
        houseComboBoxForOpponent.disableProperty().bind(getModel().gameRunningProperty());
        houseComboBoxForUser.disableProperty().bind(model.gameRunningProperty());
    }

    public Model getModel() {
        return model;
    }

    public void boxClicked(MouseEvent event) {
        ImageView boxClicked = (ImageView) event.getSource();
        String boxId = boxClicked.getId();
        model.userClick(boxId);
    }

    public void playAgain(MouseEvent event) {
        model.restartGame();
    }

    public void resetScore(MouseEvent event) {
        model.resetScore();
    }

    public void confirmHouseChangeForOpponent(ActionEvent actionEvent) {
        model.updateOpponentHouse(houseComboBoxForOpponent.getValue());
    }

    public void confirmHouseChangeForUser(ActionEvent actionEvent) {
        model.updateUserHouse(houseComboBoxForUser.getValue());
    }
}