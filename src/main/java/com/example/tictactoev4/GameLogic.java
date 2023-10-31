package com.example.tictactoev4;

import java.util.List;

public class GameLogic {
    FactoryMethods factoryMethods = new FactoryMethods();
    private List<String> availableMoves;

    public void initializeAvailableMoves() {
        availableMoves = factoryMethods.getAvailableMoves();
    }

    public List<String> getAvailableMoves() {
        return availableMoves;
    }

    public boolean winCheck(List<String> movesToCheck) {
        return factoryMethods.winningCombinations().stream()
                .anyMatch(movesToCheck::containsAll
                );
    }


}
