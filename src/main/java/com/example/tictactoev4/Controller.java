package com.example.tictactoev4;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    Model model = new Model();


    public Model getModel() {
        return model;
    }

    public void boxClicked(MouseEvent event) {
        ImageView boxClicked = (ImageView) event.getSource();
        String  boxId =  boxClicked.getId();
        System.out.println(event.getSource());
        model.userClick(boxId);
    }

    public void playAgain(MouseEvent event) {
        model.restartGame();
    }

    public void resetScore(MouseEvent event) {
        model.resetScore();
    }
}