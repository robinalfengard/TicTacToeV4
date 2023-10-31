package com.example.tictactoev4;

import javafx.scene.media.AudioClip;

public class Sounds {


    AudioClip userSound = new AudioClip(getClass().getResource("Sounds/strong-hit-36455.mp3").toExternalForm());
    AudioClip opponentSound = new AudioClip(getClass().getResource("Sounds/sword-hit-7160.mp3").toExternalForm());
    AudioClip winningSound = new AudioClip(getClass().getResource("Sounds/tada-fanfare-a-6313.mp3").toExternalForm());
    AudioClip losingSound = new AudioClip(getClass().getResource("Sounds/fail-144746.mp3").toExternalForm());


    public AudioClip getUserSound() {
        return userSound;
    }

    public AudioClip getOpponentSound() {
        return opponentSound;
    }

    public AudioClip getWinningSound() {
        return winningSound;
    }

    public AudioClip getLosingSound() {
        return losingSound;
    }
}
