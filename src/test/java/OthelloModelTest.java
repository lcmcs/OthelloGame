import org.junit.Test;

import static org.junit.Assert.*;

public class OthelloModelTest {
    OthelloModel a = new OthelloModel();

    @Test
    public void checkScoreandWinner() {
        a.gamePlay(4,5);
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < a.grid.length; i++) {
                for (int j = 0; j < a.grid.length; j++) {
                    a.gamePlay(i, j);
                }
            }
        }
        assertEquals(25, a.blackPoints);
        assertEquals(39, a.whitePoints);
        assertEquals(Pieces.WHITE, a.winner());
    }

    @Test
    public void endGameEarlyTieGame() {
        a.grid[3][3] = Pieces.EMPTY; a.grid[4][4] = Pieces.EMPTY; a.grid[4][3] = Pieces.EMPTY; a.grid[3][4] = Pieces.EMPTY;
        a.blackPoints = a.blackPoints - 2;
        a.whitePoints = a.whitePoints - 2;
        a.gamePlay(0,0);
        assertEquals(0, a.blackPoints);
        assertEquals(0, a.whitePoints);
        assertEquals(Pieces.EMPTY, a.winner());
    }

    @Test
    public void skipPlayerTurn(){
        a.grid[3][3] = Pieces.EMPTY; a.grid[4][4] = Pieces.EMPTY; a.grid[4][3] = Pieces.EMPTY; a.grid[3][4] = Pieces.EMPTY;
        a.grid[0][0] = Pieces.WHITE;
        a.grid[1][1] = Pieces.BLACK;
        a.gamePlay(1,1);
        assertEquals(Pieces.WHITE, a.grid[1][1]);
    }

    @Test
    public void skipAITurn(){
        a.grid[3][3] = Pieces.EMPTY; a.grid[4][4] = Pieces.EMPTY; a.grid[4][3] = Pieces.EMPTY; a.grid[3][4] = Pieces.EMPTY;
        a.grid[0][0] = Pieces.BLACK;
        a.grid[1][1] = Pieces.WHITE;
        a.gamePlay(2,2);
        assertEquals(Pieces.BLACK, a.grid[1][1]);
    }

    @Test
    public void normalGameplay(){
        a.gamePlay(4,5);
        assertEquals(Pieces.BLACK, a.grid[4][5]);
    }
}