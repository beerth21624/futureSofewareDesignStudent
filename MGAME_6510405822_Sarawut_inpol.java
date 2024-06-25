//6510405822 Sarawut inpol

public class Main {
    public static void main(String[] args) {
        MGame game = new MGame(7);
        game.playGame();
    }
}

class Die{
    private int faceValue = 1;

    public void roll() {
        faceValue = (int) (Math.random() * 6) + 1;
    }
    public int getFaceValue() {
        return faceValue;
    }
}


class MGame {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 8;
    private int roundCount = 1;
    private Die[] dice ;
    private Board board = new Board();
    private Player[] players ;

    public MGame(int playerCount){
        if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS){
            throw new IllegalArgumentException("ผู้เล่นต้องอยู่ระหว่าง 2-8 คน");
        }
        createDice();
        createPlayers(playerCount);
    }



    private void createDice(){
        dice = new Die[2];
        for (int i = 0; i < 2; i++){
            dice[i] = new Die();
        }
    }

    private void createPlayers(int playerCount){
        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++){
            players[i] = new Player("Player" + i, dice, board);
        }
    }

    public Player[] getPlayers() {
        return players;
    }
    public void playGame(){
        for (int i = 0; i < roundCount; i++){
            playRound();
        }
    }
    private void playRound(){
        for (Player player : players){
            player.takeTurn();
        }
    }
}

class Board {
    private static final int SQUARE_SIZE = 40;
    private Square[] squares = new Square[SQUARE_SIZE];

    public Board(){
        for (int i = 0; i < SQUARE_SIZE; i++){
            squares[i] = new Square("Square" + i);
        }
    }

    public Square getSquare(int index){
        return squares[index];
    }

    public Square[] getSquares(){
        return squares;
    }
}

class Square {
    private String name;

    public Square(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}

class Player {
    private String name;
    private Die[] dice;
    private Board board;
    private Piece piece;

    public Player(String name, Die[] dice, Board board){
        this.name = name;
        this.dice = dice;
        this.board = board;
        piece = new Piece(board.getSquare(0));

    }

    public void takeTurn(){
        int fv = 0;
        for (Die die : dice){
            die.roll();
            fv += die.getFaceValue();
        }
        Square oldLoc = piece.getLocation();
        int oldLocIndex = 0;
        for (int i = 0; i < board.getSquares().length; i++){
            if (board.getSquares()[i] == oldLoc){
                oldLocIndex = i;
                break;
            }
        }
        int newLocIndex = (oldLocIndex + fv) % board.getSquares().length;
        Square newLoc = board.getSquare(newLocIndex);

        piece.setLocation(newLoc);
    }


}

class Piece{
    private Square location;

    public Piece(Square location){
        this.location = location;
    }

    public Square getLocation(){
        return location;
    }

    public void setLocation(Square location){
        this.location = location;
    }
}
