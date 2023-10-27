package com.example.tictactoev4;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


//Todo cara konsekvent i opponent eller computer, binda bilden vid score?

public class Model {
    Random random = new Random();
    FactoryMethods factoryMethods = new FactoryMethods();


    public Image hufflePuffImage = new Image(Objects.requireNonNull(getClass().getResource("Images/huff.jpeg")).toExternalForm());
    public Image slytherinImage = new Image(Objects.requireNonNull(getClass().getResource("Images/slytherin.jpeg")).toExternalForm());
    public Image ravenClawImage = new Image(Objects.requireNonNull(getClass().getResource("Images/ravenclaw.jpg")).toExternalForm());
    public Image gryffindorImage = new Image(Objects.requireNonNull(getClass().getResource("Images/gryffindor.jpg")).toExternalForm());

    public Image userMarker;
    public Image computerMarker;
    public Image availableSpace = new Image(Objects.requireNonNull(getClass().getResource("Images/available.jpeg")).toExternalForm());


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
    private final ObjectProperty<Image> hufflePuffLogo;
    private final ObjectProperty<Image> slytherinLogo;
    private final ObjectProperty<Image> ravenclawLogo;
    private final ObjectProperty<Image> gryffindorLogo;
    private final ObjectProperty<Image> userHouse;
    private final ObjectProperty<Image> opponentHouse;
    private final List<ObjectProperty<Image>> listOfBoxes = new ArrayList<>();



    public Model(){
        box1 = new SimpleObjectProperty<>(availableSpace);
        box2 = new SimpleObjectProperty<>(availableSpace);
        box3 = new SimpleObjectProperty<>(availableSpace);
        box4 = new SimpleObjectProperty<>(availableSpace);
        box5 = new SimpleObjectProperty<>(availableSpace);
        box6 = new SimpleObjectProperty<>(availableSpace);
        box7 = new SimpleObjectProperty<>(availableSpace);
        box8 = new SimpleObjectProperty<>(availableSpace);
        box9 = new SimpleObjectProperty<>(availableSpace);
        hufflePuffLogo = new SimpleObjectProperty<>(hufflePuffImage);
        slytherinLogo = new SimpleObjectProperty<>(slytherinImage);
        ravenclawLogo = new SimpleObjectProperty<>(ravenClawImage);
        gryffindorLogo = new SimpleObjectProperty<>(gryffindorImage);
        userHouse = new SimpleObjectProperty<>(gryffindorImage);
        opponentHouse = new SimpleObjectProperty<>(slytherinImage);
        setUserMarker(gryffindorImage);
        setComputerMarker(slytherinImage);

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
        setWinningMessage("No Winner Yet");
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

    public boolean isValidMove(String move){
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

    // Update house
    public void updateOpponentHouse(String house) {
        //todo bryta ut metod
        setComputerMarker(houseSelector(house));
        setOpponentHouse(houseSelector(house));

    }

    public void updateUserHouse(String house) {
        setUserMarker(houseSelector(house));
        setUserHouse(houseSelector(house));

    }

    public Image houseSelector(String input){
        Image house;
        switch (input){
            case "Ravenclaw" -> house = ravenClawImage;
            case "Gryffindor" -> house = gryffindorImage;
            case "Hufflepuff" -> house = hufflePuffImage;
            case "Slytherin" -> house = slytherinImage;
            default -> throw new IllegalStateException("Unexpected value: " + input);
        }
        return house;
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



    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
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

    public String getPrintoutScoreForComputer() {
        return printoutScoreForComputer.get();
    }

    public StringProperty printoutScoreForComputerProperty() {
        return printoutScoreForComputer;
    }

    public void setPrintoutScoreForComputer(String printoutScoreForComputer) {
        this.printoutScoreForComputer.set(printoutScoreForComputer);
    }

    public List<String> getAvailableMoves() {
        return availableMoves;
    }


    public Image getUserMarker() {
        return userMarker;
    }

    public void setUserMarker(Image userMarker) {
        this.userMarker = userMarker;
    }

    public Image getComputerMarker() {
        return computerMarker;
    }

    public void setComputerMarker(Image computerMarker) {
        this.computerMarker = computerMarker;
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
}
