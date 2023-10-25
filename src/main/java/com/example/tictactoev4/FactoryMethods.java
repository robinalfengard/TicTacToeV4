package com.example.tictactoev4;

import java.util.ArrayList;
import java.util.List;

public class BoxFactory {

    public List<String> getAvailableMoves() {
        List<String> availableMoves = new ArrayList<>();
        availableMoves.add("box1");
        availableMoves.add("box2");
        availableMoves.add("box3");
        availableMoves.add("box4");
        availableMoves.add("box5");
        availableMoves.add("box6");
        availableMoves.add("box7");
        availableMoves.add("box8");
        availableMoves.add("box9");
        return availableMoves;


    }
}