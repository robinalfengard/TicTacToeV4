package com.example.tictactoev4;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// metod som kan avgöra om man får klicka + test isValid
// om man har vunnit
public class Model {
    Random random = new Random();
    FactoryMethods factoryMethods = new FactoryMethods();

    public Image availableSpace;
    public Image userMarker;
    public Image computerMarker;

    private List<String> availableMoves;
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> computerMoves = new ArrayList<>();

    private int computerScore = 0;
    private int userScore = 0;

    private final StringProperty winningMessage = new SimpleStringProperty("No Winner Yet");
    private final BooleanProperty isButtonVisible = new SimpleBooleanProperty(false);

    private final StringProperty printoutScoreForUser = new SimpleStringProperty("");
    private final StringProperty printoutScoreForComputer = new SimpleStringProperty("");

    private final ObjectProperty<Image> box1;
    private final ObjectProperty<Image> box2;
    private final ObjectProperty<Image> box3;
    private final ObjectProperty<Image> box4;
    private final ObjectProperty<Image> box5;
    private final ObjectProperty<Image> box6;
    private final ObjectProperty<Image> box7;
    private final ObjectProperty<Image> box8;
    private final ObjectProperty<Image> box9;
    private final List<ObjectProperty<Image>> listOfBoxes = new ArrayList<>();



    public Model(){
        availableSpace = new Image(getClass().getResource("Images/vit.png").toExternalForm());
        userMarker = new Image(getClass().getResource("Images/user.png").toExternalForm());
        computerMarker = new Image(getClass().getResource("Images/computer.jpeg").toExternalForm());
        box1 = new SimpleObjectProperty<>(availableSpace);
        box2 = new SimpleObjectProperty<>(availableSpace);
        box3 = new SimpleObjectProperty<>(availableSpace);
        box4 = new SimpleObjectProperty<>(availableSpace);
        box5 = new SimpleObjectProperty<>(availableSpace);
        box6 = new SimpleObjectProperty<>(availableSpace);
        box7 = new SimpleObjectProperty<>(availableSpace);
        box8 = new SimpleObjectProperty<>(availableSpace);
        box9 = new SimpleObjectProperty<>(availableSpace);
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
        if(isValidMove(boxId)){
            userMove(boxId);
            isGameOver();
            initializeComputerMove();
        }
    }

    private void initializeAvailableMoves(){
        availableMoves = factoryMethods.getAvailableMoves();
    }

    public void restartGame() {
        resetBoxes();
        resetAvailableMoves();
        setIsButtonVisible(false);
    }

    private void resetBoxes(){
        listOfBoxes.forEach(this::markBoxAvailable);
        userMoves.clear();
        computerMoves.clear();
    }

    private void resetAvailableMoves() {
        initializeAvailableMoves();
    }

    public void resetScore() {
        setUserScore(0);
        setPrintoutScoreForUser("Your wins: " + userScore);
        setComputerScore(0);
        setPrintoutScoreForComputer("Computer wins: " +computerScore);
    }



    //MOVES
    private void userMove(String userMove) {
            markUserMove(boxSelector(userMove));
            userMoves.add(userMove);
            System.out.println(availableMoves);
             System.out.println(userMove);
            availableMoves.remove(userMove);
    }

    private void markUserMove(ObjectProperty<Image> boxToPaint) {
        boxToPaint.set(userMarker);
    }

    private void makeComputerMove(String computerMove) {
            markComputerMove(boxSelector(computerMove));
            computerMoves.add(computerMove);
            availableMoves.remove(computerMove);
            isGameOver();
    }


    private String generateComputerMove() {
        if (availableMoves.isEmpty()) {
            return null; // Or some other appropriate action
        }
        int move = random.nextInt(availableMoves.size());
        return availableMoves.get(move);
    }


    private void initializeComputerMove(){
        String computerMove = generateComputerMove();
        if (isValidMove(computerMove))
            makeComputerMove(computerMove);
    }

    private void markComputerMove(ObjectProperty<Image> boxToMark) {
        boxToMark.set(computerMarker);
    }

    private void markBoxAvailable(ObjectProperty<Image> boxToMark) {
        boxToMark.set(availableSpace);
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
        if (winCheck(computerMoves)) {
            computerWin();
        } else if (winCheck(userMoves)) {
            userWin();
        } else if (availableMoves.isEmpty()) {
            tie();
        }
    }

    private boolean isValidMove(String move){
        return availableMoves.contains(move);
    }



    // OUTCOMES
    public void userWin(){
        disableAllMoves();
        setWinningMessage("You won this match!");
        setUserScore(userScore+=1);
        setPrintoutScoreForUser("Your wins: " + userScore);
        setIsButtonVisible(true);
    }

    public void computerWin(){
        disableAllMoves();
        setWinningMessage("The computer won this match!");
        setComputerScore(computerScore+=1);
        setPrintoutScoreForComputer("Computer wins: " + computerScore);
        setIsButtonVisible(true);
    }

    private void tie() {
        disableAllMoves();
        setWinningMessage("It's a tie!");
        disableAllMoves();
        setIsButtonVisible(true);
    }

    // BoxSelector
    private ObjectProperty<Image> boxSelector(String id){
        ObjectProperty<Image>  box;
        switch(id){
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























//  GETTERS  &  SETTERS










    public Image getBox1() {
        return box1.get();
    }


    public Image getBox2() {
        return box2.get();
    }



    public Image getBox3() {
        return box3.get();
    }



    public Image getBox4() {
        return box4.get();
    }


    public Image getBox5() {
        return box5.get();
    }



    public Image getBox6() {
        return box6.get();
    }



    public Image getBox7() {
        return box7.get();
    }



    public Image getBox8() {
        return box8.get();
    }



    public Image getBox9() {
        return box9.get();
    }


    public String getWinningMessage() {
        return winningMessage.get();
    }



    public void setWinningMessage(String winningMessage) {
        this.winningMessage.set(winningMessage);
    }

    public boolean isIsButtonVisible() {
        return isButtonVisible.get();
    }


    public void setIsButtonVisible(boolean isButtonVisible) {
        this.isButtonVisible.set(isButtonVisible);
    }



    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }


    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getPrintoutScoreForUser() {
        return printoutScoreForUser.get();
    }


    public void setPrintoutScoreForUser(String printoutScoreForUser) {
        this.printoutScoreForUser.set(printoutScoreForUser);
    }

    public String getPrintoutScoreForComputer() {
        return printoutScoreForComputer.get();
    }


    public void setPrintoutScoreForComputer(String printoutScoreForComputer) {
        this.printoutScoreForComputer.set(printoutScoreForComputer);
    }



}
