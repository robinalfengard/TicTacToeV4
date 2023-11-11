package com.example.tictactoev4;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    GameLogic gameLogic = new GameLogic();


    @Test void winCheckShouldReturnFalse(){
        List<String> shouldNotWin = new ArrayList<>();
        shouldNotWin.add("box2");
        shouldNotWin.add("box3");
        shouldNotWin.add("box4");
        assertFalse(gameLogic.winCheck(shouldNotWin));
    }

    @Test void winCheckShouldReturnTrue(){
        List<String> shouldWin = new ArrayList<>();
        shouldWin.add("box1");
        shouldWin.add("box2");
        shouldWin.add("box3");
        assertTrue(gameLogic.winCheck(shouldWin));
    }

    @Test void winCheckWithBoxesInNonOrderShouldReturnTrue(){
        List<String> shouldWin = new ArrayList<>();
        shouldWin.add("box3");
        shouldWin.add("box1");
        shouldWin.add("box2");
        assertTrue(gameLogic.winCheck(shouldWin));
    }

    @Test void isValidMoveShouldReturnFalse(){
        gameLogic.initializeAvailableMoves();
        gameLogic.getAvailableMoves().remove("box1");
        assertFalse(gameLogic.isValidMove("box1"));
    }

    @Test void isValidMoveShouldReturnTrue(){
        gameLogic.initializeAvailableMoves();
        assertTrue(gameLogic.isValidMove("box1"));
    }



}