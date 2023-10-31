package com.example.tictactoev4;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//Todo cara konsekvent i opponent eller computer

public class Model {
    private final int TOTAL_BOXES = 9;
    Images images = new Images();
    Sounds sounds = new Sounds();
    Random random = new Random();
    Service service = new Service();
    FactoryMethods factoryMethods = new FactoryMethods();
    private final BooleanProperty isButtonVisible = new SimpleBooleanProperty(false);
    private List<String> availableMoves;
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> opponentMoves = new ArrayList<>();
    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);
    private int opponentScore = 0;
    private int userScore = 0;
    private final StringProperty printoutScoreForUser = new SimpleStringProperty("");
    private final StringProperty printoutScoreForOpponent = new SimpleStringProperty("");
    private final StringProperty winningMessage = new SimpleStringProperty("No Winner Yet");
    private final ObjectProperty<Image> box1;
    private final ObjectProperty<Image> box2;
    private final ObjectProperty<Image> box3;
    private final ObjectProperty<Image> box4;
    private final ObjectProperty<Image> box5;
    private final ObjectProperty<Image> box6;
    private final ObjectProperty<Image> box7;
    private final ObjectProperty<Image> box8;
    private final ObjectProperty<Image> box9;
    private final ObjectProperty<Image> hufflePuffLogo;
    private final ObjectProperty<Image> slytherinLogo;
    private final ObjectProperty<Image> ravenclawLogo;
    private final ObjectProperty<Image> gryffindorLogo;
    private final ObjectProperty<Image> userHouse;
    private final ObjectProperty<Image> opponentHouse;
    private final List<ObjectProperty<Image>> listOfBoxes = new ArrayList<>();


    public Model() {
        box1 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box2 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box3 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box4 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box5 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box6 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box7 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box8 = new SimpleObjectProperty<>(images.getAvailableSpace());
        box9 = new SimpleObjectProperty<>(images.getAvailableSpace());
        hufflePuffLogo = new SimpleObjectProperty<>(images.getHufflePuffImage());
        slytherinLogo = new SimpleObjectProperty<>(images.getSlytherinImage());
        ravenclawLogo = new SimpleObjectProperty<>(images.getRavenClawImage());
        gryffindorLogo = new SimpleObjectProperty<>(images.getGryffindorImage());
        userHouse = new SimpleObjectProperty<>(images.getGryffindorImage());
        opponentHouse = new SimpleObjectProperty<>(images.getSlytherinImage());
        images.setUserMarker(images.gryffindorImage);
        images.setOpponentMarker(images.getSlytherinImage());
        listOfBoxes.add(box1);
        listOfBoxes.add(box2);
        listOfBoxes.add(box3);
        listOfBoxes.add(box4);
        listOfBoxes.add(box5);
        listOfBoxes.add(box6);
        listOfBoxes.add(box7);
        listOfBoxes.add(box8);
        listOfBoxes.add(box9);
        initializeAvailableMoves();
    }


    // Inputs from controller
    public void userClick(String boxId) {
        if (isValidMove(boxId)) {
            userMove(boxId);
            isGameOver();
            toggleGameRunning();
            sounds.getUserSound().play();
            initializeComputerMove();
        }
    }

    private void initializeAvailableMoves() {
        availableMoves = factoryMethods.getAvailableMoves();
    }

    public void restartGame() {
        resetBoxes();
        resetAvailableMoves();
        setWinningMessage("No Winner Yet");
        setIsButtonVisible(false);
        toggleGameRunning();
    }

    private void resetBoxes() {
        listOfBoxes.forEach(this::markBoxAvailable);
        userMoves.clear();
        opponentMoves.clear();
    }

    private void resetAvailableMoves() {
        initializeAvailableMoves();
    }

    public void resetScore() {
        setUserScore(0);
        setPrintoutScoreForUser("Your wins: " + userScore);
        setOpponentScore(0);
        setPrintoutScoreForOpponent("Opponent wins: " + opponentScore);
    }


    //MOVES
    private void userMove(String userMove) {
        markUserMove(boxSelector(userMove));
        userMoves.add(userMove);
        availableMoves.remove(userMove);
    }

    private void markUserMove(ObjectProperty<Image> boxToPaint) {
        boxToPaint.set(images.getUserMarker());
    }

    private void makeComputerMove(String computerMove) {
        markComputerMove(boxSelector(computerMove));
        opponentMoves.add(computerMove);
        availableMoves.remove(computerMove);
        sounds.getUserSound().play();
        isGameOver();
    }


    private String generateComputerMove() {
        if (availableMoves.isEmpty()) {
            return null; // Or some other appropriate action
        }
        int move = random.nextInt(availableMoves.size());
        return availableMoves.get(move);
    }

    private void initializeComputerMove() {
        String computerMove = generateComputerMove();
        if (isValidMove(computerMove))
            makeComputerMove(computerMove);
    }

    private void markComputerMove(ObjectProperty<Image> boxToMark) {
        boxToMark.set(images.getComputerMarker());
    }

    private void markBoxAvailable(ObjectProperty<Image> boxToMark) {
        boxToMark.set(images.getAvailableSpace());
    }

    private void disableAllMoves() {
        availableMoves.clear();
    }


    // Checks
    public boolean winCheck(List<String> movesToCheck) {
        return factoryMethods.winningCombinations().stream()
                .anyMatch(movesToCheck::containsAll
                );
    }


    public void isGameOver() {
        if (winCheck(opponentMoves)) {
            computerWin();
        } else if (winCheck(userMoves)) {
            userWin();
        } else if (availableMoves.isEmpty()) {
            tie();
        }
    }

    public boolean isValidMove(String move) {
        return availableMoves.contains(move);
    }


    // OUTCOMES
    public void userWin() {
        disableAllMoves();
        setWinningMessage("You won this match!");
        setUserScore(userScore += 1);
        setPrintoutScoreForUser("Your wins: " + userScore);
        setIsButtonVisible(true);
        sounds.getWinningSound().play();
    }

    public void computerWin() {
        disableAllMoves();
        setWinningMessage("The computer won this match!");
        setOpponentScore(opponentScore += 1);
        setPrintoutScoreForOpponent("Computer wins: " + opponentScore);
        setIsButtonVisible(true);
        sounds.getLosingSound().play();
    }

    private void tie() {
        disableAllMoves();
        setWinningMessage("It's a tie!");
        disableAllMoves();
        setIsButtonVisible(true);
    }

    // BoxSelector
    private ObjectProperty<Image> boxSelector(String id) {
        ObjectProperty<Image> box;
        switch (id) {
            case "box1" -> box = box1;
            case "box2" -> box = box2;
            case "box3" -> box = box3;
            case "box4" -> box = box4;
            case "box5" -> box = box5;
            case "box6" -> box = box6;
            case "box7" -> box = box7;
            case "box8" -> box = box8;
            case "box9" -> box = box9;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        }
        return box;
    }

    // Update house
    public void updateOpponentHouse(String house) {
        images.setOpponentMarker(service.houseSelector(house));
        setOpponentHouse(service.houseSelector(house));
    }

    public void updateUserHouse(String house) {
        images.setUserMarker(service.houseSelector(house));
        setUserHouse(service.houseSelector(house));
    }


    //  GETTERS  &  SETTERS
    public Image getBox1() {
        return box1.get();
    }

    public ObjectProperty<Image> box1Property() {
        return box1;
    }

    public void setBox1(Image box1) {
        this.box1.set(box1);
    }

    public Image getBox2() {
        return box2.get();
    }

    public ObjectProperty<Image> box2Property() {
        return box2;
    }

    public void setBox2(Image box2) {
        this.box2.set(box2);
    }

    public Image getBox3() {
        return box3.get();
    }

    public ObjectProperty<Image> box3Property() {
        return box3;
    }

    public void setBox3(Image box3) {
        this.box3.set(box3);
    }

    public Image getBox4() {
        return box4.get();
    }

    public ObjectProperty<Image> box4Property() {
        return box4;
    }

    public void setBox4(Image box4) {
        this.box4.set(box4);
    }

    public Image getBox5() {
        return box5.get();
    }

    public ObjectProperty<Image> box5Property() {
        return box5;
    }

    public void setBox5(Image box5) {
        this.box5.set(box5);
    }

    public Image getBox6() {
        return box6.get();
    }

    public ObjectProperty<Image> box6Property() {
        return box6;
    }

    public void setBox6(Image box6) {
        this.box6.set(box6);
    }

    public Image getBox7() {
        return box7.get();
    }

    public ObjectProperty<Image> box7Property() {
        return box7;
    }

    public void setBox7(Image box7) {
        this.box7.set(box7);
    }

    public Image getBox8() {
        return box8.get();
    }

    public ObjectProperty<Image> box8Property() {
        return box8;
    }

    public void setBox8(Image box8) {
        this.box8.set(box8);
    }

    public Image getBox9() {
        return box9.get();
    }

    public ObjectProperty<Image> box9Property() {
        return box9;
    }

    public void setBox9(Image box9) {
        this.box9.set(box9);
    }



    public String getWinningMessage() {
        return winningMessage.get();
    }

    public StringProperty winningMessageProperty() {
        return winningMessage;
    }

    public void setWinningMessage(String winningMessage) {
        this.winningMessage.set(winningMessage);
    }

    public boolean isIsButtonVisible() {
        return isButtonVisible.get();
    }

    public BooleanProperty isButtonVisibleProperty() {
        return isButtonVisible;
    }

    public void setIsButtonVisible(boolean isButtonVisible) {
        this.isButtonVisible.set(isButtonVisible);
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getPrintoutScoreForUser() {
        return printoutScoreForUser.get();
    }

    public StringProperty printoutScoreForUserProperty() {
        return printoutScoreForUser;
    }

    public void setPrintoutScoreForUser(String printoutScoreForUser) {
        this.printoutScoreForUser.set(printoutScoreForUser);
    }

    public String getPrintoutScoreForOpponent() {
        return printoutScoreForOpponent.get();
    }

    public StringProperty printoutScoreForOpponentProperty() {
        return printoutScoreForOpponent;
    }

    public void setPrintoutScoreForOpponent(String printoutScoreForOpponent) {
        this.printoutScoreForOpponent.set(printoutScoreForOpponent);
    }

    public List<String> getAvailableMoves() {
        return availableMoves;
    }







    public void setUserHouse(Image userHouse) {
        this.userHouse.set(userHouse);
    }

    public void setOpponentHouse(Image opponentHouse) {
        this.opponentHouse.set(opponentHouse);
    }

    public Image getUserHouse() {
        return userHouse.get();
    }

    public ObjectProperty<Image> userHouseProperty() {
        return userHouse;
    }

    public Image getOpponentHouse() {
        return opponentHouse.get();
    }

    public ObjectProperty<Image> opponentHouseProperty() {
        return opponentHouse;
    }


    public boolean isGameRunning() {
        return TOTAL_BOXES > availableMoves.size();
    }

    public void toggleGameRunning() {
        gameRunning.set(isGameRunning());
    }

    public BooleanProperty gameRunningProperty() {
        return gameRunning;
    }

}
