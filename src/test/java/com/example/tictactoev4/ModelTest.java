package com.example.tictactoev4;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    Model model = new Model();

    @Test void resetScoreShouldResetScoreToZero(){
        model.setUserScore(20);
        model.resetScore();
        assertEquals(0, model.getUserScore());
    }

    @Test void winCheckShouldReturnFalse(){
        List<String> shouldNotWin = new ArrayList<>();
        shouldNotWin.add("box2");
        shouldNotWin.add("box3");
        shouldNotWin.add("box4");
        assertFalse(model.winCheck(shouldNotWin));
    }

    @Test void winCheckShouldReturnTrue(){
        List<String> shouldWin = new ArrayList<>();
        shouldWin.add("box2");
        shouldWin.add("box3");
        shouldWin.add("box1");
        assertTrue(model.winCheck(shouldWin));
    }

    @Test void isValidShouldReturnFalse(){
        model.getAvailableMoves().remove("box1");
        assertFalse(model.isValidMove("box1"));
    }

    @Test void isValidShouldReturnTrue(){
        assertTrue(model.isValidMove("box1"));
    }



}