import java.util.ArrayList;

public interface OthelloModelInterface {
    /**
     * Plays the game. Start with white and runs the placeShift
     * then moves on to the AI and the AI makes a loop checking through all of the spots on the board
     * then it enters the one with the highest score using pieceShift
     */
    Pieces gamePlay(int x, int y);

    /**
     * AI runs through all pieces to check which spot will give the most points.
     * Then it calls pieceShift which flips those pieces
     */
    int AITurn();

    /**
     * checks all the pieces which need to be switched or added.
     * Runs around the piece which you selected then adds on till it hits another of the same piece.
     */
    int pieceShift(int x, int y, boolean swapper);

    /**
     * Does the switching of pieces.  It uses an arraylist that found the pieces earlier that need to be flipped.
     */
    void pieceSwap(ArrayList<Integer> x, ArrayList<Integer> y);

    /**
     * Checks which pieces have been switched or added and adds the appopriate score to them.
     */
    void pointGrabber(int x, int y);

    /**
     * Checks if the player has any available moves.  If not skips his turn in the gamePlay method.
     */
    boolean playerChecker();

    /**
     * Checks to see who the winner is through the score class.  This is done with if statements to see
     * who leads the game.
     */
    Pieces winner();


}
