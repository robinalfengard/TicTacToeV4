package com.example.tictactoev4;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    public ComboBox houseComboBoxForUser;
    public ComboBox houseComboBoxForOpponent;
    Model model = new Model();


    public Model getModel() {
        return model;
    }

    public void boxClicked(MouseEvent event) {
        ImageView boxClicked = (ImageView) event.getSource();
        String  boxId =  boxClicked.getId();
        model.userClick(boxId);
    }

    public void playAgain(MouseEvent event) {
        model.restartGame();
    }

    public void resetScore(MouseEvent event) {
        model.resetScore();
    }

    public void confirmHouseChangeForOpponent(ActionEvent actionEvent) {model.updateOpponentHouse(houseComboBoxForOpponent.getValue().toString());
    }

    public void confirmHouseChangeForUser(ActionEvent actionEvent) {model.updateUserHouse(houseComboBoxForUser.getValue().toString());
    }
}