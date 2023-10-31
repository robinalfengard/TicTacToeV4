package com.example.tictactoev4;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.property.ObjectProperty;

public class Model {
    private final int TOTAL_BOXES = 9;
    Images images = new Images();
    Sounds sounds = new Sounds();
    Random random = new Random();
    Service service = new Service();
    GameLogic gameLogic = new GameLogic();
    private final BooleanProperty isButtonVisible = new SimpleBooleanProperty(false);
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> opponentMoves = new ArrayList<>();
    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);
    private int opponentScore = 0;
    private int userScore = 0;
    private final StringProperty printoutScoreForUser = new SimpleStringProperty("");
    private final StringProperty printoutScoreForOpponent = new SimpleStringProperty("");
    private final StringProperty winningMessage = new SimpleStringProperty("No Winner Yet");
    private final ObjectProperty<Image> box1 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box2 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box3 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box4 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box5 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box6 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box7 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box8 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final ObjectProperty<Image> box9 = new SimpleObjectProperty<>(images.getAvailableSpace());
    private final List<ObjectProperty<Image>> listOfBoxes = List.of(
            box1, box2, box3, box4, box5, box6, box7, box8, box9);

    private final ObjectProperty<Image> hufflePuffLogo = new SimpleObjectProperty<>(images.getHufflePuffImage());
    private final ObjectProperty<Image> slytherinLogo = new SimpleObjectProperty<>(images.getSlytherinImage());
    private final ObjectProperty<Image> ravenclawLogo = new SimpleObjectProperty<>(images.getRavenClawImage());
    private final ObjectProperty<Image> gryffindorLogo = new SimpleObjectProperty<>(images.getGryffindorImage());
    private final ObjectProperty<Image> userHouse = new SimpleObjectProperty<>(images.getGryffindorImage());
    private final ObjectProperty<Image> opponentHouse = new SimpleObjectProperty<>(images.getSlytherinImage());

    public Model() {
        images.setUserMarker(images.getGryffindorImage());
        images.setOpponentMarker(images.getSlytherinImage());
        gameLogic.initializeAvailableMoves();
    }

    public void userClick(String boxId) {
        if (gameLogic.isValidMove(boxId)) {
            userMove(boxId);
            isGameOver();
            toggleGameRunning();
            sounds.getUserSound().play();
            initializeComputerMove();
        }
    }


    private void userMove(String userMove) {
        markUserMove(boxSelector(userMove));
        userMoves.add(userMove);
        gameLogic.getAvailableMoves().remove(userMove);
    }

    public void markUserMove(ObjectProperty<Image> boxToPaint) {
        boxToPaint.set(images.getUserMarker());
    }

    private void initializeComputerMove() {
        String computerMove = generateComputerMove();
        if (gameLogic.isValidMove(computerMove))
            makeComputerMove(computerMove);
    }

    private String generateComputerMove() {
        if (gameLogic.getAvailableMoves().isEmpty()) {
            return null;
        }
        int move = random.nextInt(gameLogic.getAvailableMoves().size());
        return gameLogic.getAvailableMoves().get(move);
    }

    private void makeComputerMove(String computerMove) {
        markComputerMove(boxSelector(computerMove));
        opponentMoves.add(computerMove);
        gameLogic.getAvailableMoves().remove(computerMove);
        sounds.getUserSound().play();
        isGameOver();
    }

    public void markComputerMove(ObjectProperty<Image> boxToMark) {
        boxToMark.set(images.getComputerMarker());
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
        gameLogic.getAvailableMoves().clear();
        gameLogic.initializeAvailableMoves();
    }

    public void resetScore() {
        setUserScore(0);
        setPrintoutScoreForUser("Your wins: " + userScore);
        setOpponentScore(0);
        setPrintoutScoreForOpponent("Opponent wins: " + opponentScore);
    }

    private void markBoxAvailable(ObjectProperty<Image> boxToMark) {
        boxToMark.set(images.getAvailableSpace());
    }

    private void disableAllMoves() {
        gameLogic.getAvailableMoves().clear();
    }

    public void isGameOver() {
        if (gameLogic.winCheck(opponentMoves)) {
            computerWin();
        } else if (gameLogic.winCheck(userMoves)) {
            userWin();
        } else if (gameLogic.getAvailableMoves().isEmpty()) {
            tie();
        }
    }



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
        return TOTAL_BOXES > gameLogic.getAvailableMoves().size();
    }

    public void toggleGameRunning() {
        gameRunning.set(isGameRunning());
    }

    public BooleanProperty gameRunningProperty() {
        return gameRunning;
    }

}
