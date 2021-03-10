import java.util.*;

enum Pieces {
    EMPTY, WHITE, BLACK, TIE;

    public String toIcon() {
        if (this == Pieces.WHITE) return "\u25CB";
        if (this == Pieces.BLACK) return "\u2B24";
        return " ";
    }
}

public class OthelloModel implements OthelloModelInterface {
    final int gridSize = 8;
    Pieces[][] grid = new Pieces[gridSize][gridSize];
    Map<Integer, List<Integer>> emptyValues = new HashMap<>();
    Pieces turn = Pieces.BLACK;
    int piecesCounter = 4;
    int blackPoints = 2;
    int whitePoints = 2;

    public OthelloModel() {
        for (int i = 0; i < grid.length; i++) {
            ArrayList<Integer> a = new ArrayList<>();
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = Pieces.EMPTY;
                a.add(j);
                if (j == 7) {
                    emptyValues.put(i, a);
                }
            }
        }
        grid[3][3] = Pieces.WHITE;grid[4][4] = Pieces.WHITE;grid[4][3] = Pieces.BLACK;grid[3][4] = Pieces.BLACK;
    }

    public Pieces getPiece(int x, int y) { return grid[x][y]; }

    public Pieces gamePlay(int x, int y) {
        int checker = 0;
        try {
            if (playerChecker() && turn == Pieces.BLACK) {
                turn = Pieces.WHITE;
                checker++;
            }
            if (turn == Pieces.BLACK) {
                pieceShift(x, y, true);
                turn = Pieces.WHITE;
            }
        } catch (IllegalArgumentException ignored) {}
        if (turn == Pieces.WHITE) {
            checker = checker + AITurn();
            if (checker == 1 && playerChecker()) { checker++; }
        }
        if (checker >= 2 || piecesCounter == 64) {
            return Pieces.EMPTY;
        }
        return Pieces.WHITE;
    }

    public int AITurn() {
        ArrayList<Integer> scoresI = new ArrayList<>();
        ArrayList<Integer> scoresJ = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        for (Integer x : emptyValues.keySet()) {
            for (Integer y : emptyValues.get(x)) {
                scores.add(pieceShift(x, y, false));
                scoresI.add(x);
                scoresJ.add(y);
            }
        }
        try {
            int maxNumber = Collections.max(scores);
            if (maxNumber > 0) {
                int maxIndex = scores.indexOf(maxNumber);
                pieceShift(scoresI.get(maxIndex), scoresJ.get(maxIndex), true);
                turn = Pieces.BLACK;
                return 0;
            }
        } catch (Exception ignored) {}
        turn = Pieces.BLACK;
        return 1;
    }

    public int pieceShift(int x, int y, boolean swapper) {
        int scoreAdded = 0;
        boolean invalidMove = true;
        if (grid[x][y] == Pieces.EMPTY) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    ArrayList<Integer> flipX = new ArrayList<>();
                    ArrayList<Integer> flipY = new ArrayList<>();
                    flipX.add(x);
                    flipY.add(y);
                    int newX = x + i;
                    int newY = y + j;
                    try {
                        if (grid[newX][newY] != turn && grid[newX][newY] != Pieces.EMPTY) {
                            for (int k = 0; k < 7; k++) {
                                flipX.add(newX);
                                flipY.add(newY);
                                newX = newX + i;
                                newY = newY + j;
                                if (grid[newX][newY] == Pieces.EMPTY) {
                                    break;
                                }
                                if (grid[newX][newY] == turn) {
                                    if (swapper) {
                                        invalidMove = false;
                                        pieceSwap(flipX, flipY);
                                    }
                                    else{
                                        scoreAdded = flipX.size();
                                    }
                                    break;
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                }
            }
            if (invalidMove && swapper) { throw new IllegalArgumentException("This move isn't possible! Try Again."); }
        } else if (turn == Pieces.BLACK && swapper) {
            if (grid[x][y] == Pieces.BLACK || grid[x][y] == Pieces.WHITE) {
                throw new IllegalArgumentException("Spot is already occupied! Can't place a piece there! Try Again.");
            }
        }
        return scoreAdded;
    }

    public void pieceSwap(ArrayList<Integer> x, ArrayList<Integer> y) {
        for (int i = 0; i < x.size(); i++) {
            emptyValues.get(x.get(i)).remove(y.get(i));
            pointGrabber(x.get(i), y.get(i));
            grid[x.get(i)][y.get(i)] = turn;
        }
    }

    public void pointGrabber(int x, int y) {
        if (grid[x][y] == Pieces.EMPTY) {
            piecesCounter++;
            if (turn == Pieces.BLACK) {
                blackPoints++;
            }
            if (turn == Pieces.WHITE) {
                whitePoints++;
            }
        } else if (grid[x][y] != turn) {
            if (turn == Pieces.BLACK) {
                blackPoints++;
                whitePoints--;
            }
            if (turn == Pieces.WHITE) {
                whitePoints++;
                blackPoints--;
            }
        }
    }

    public boolean playerChecker() {
        ArrayList<Integer> scores = new ArrayList<>();
        int maxNumber = 0;
        for (Integer x : emptyValues.keySet()) {
            for (Integer y : emptyValues.get(x)) {
                scores.add(pieceShift(x, y, false));
            }
        }
        try { maxNumber = Collections.max(scores);} catch (Exception ignored) {}
        return maxNumber == 0;
    }

    public Pieces winner() {
        if (blackPoints > whitePoints) { return Pieces.BLACK; }
        if (whitePoints > blackPoints) { return Pieces.WHITE; }
        return Pieces.TIE;
    }
}