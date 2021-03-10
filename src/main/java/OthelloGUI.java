import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OthelloGUI extends JFrame {
    private OthelloModel model;
    private JButton[][] buttons;

    OthelloGUI(OthelloModel model) {
        this.model = model;
        setTitle("Othello");
        super.setSize(600, 620);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int size = model.gridSize;

        setLayout(new GridLayout(size , size));
        ActionListener al = new ButtonListener();

        buttons = new MyJButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.add(buttons[i][j] = new MyJButton(new Point(i, j)));
                buttons[i][j].addActionListener(al);
                buttons[i][j].setFont(new Font("", Font.BOLD, 40));
                buttons[i][j].setBackground(new Color(0, 140, 0));
            }
        }
        updateBoard();
        setVisible(true);
    }

    void updateBoard() {
        int size = model.gridSize;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pieces c = model.getPiece(i, j);
                buttons[i][j].setText(c.toIcon());
            }
        }
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MyJButton b = (MyJButton) e.getSource();
            Point p = b.p;
            if (model.gamePlay(p.x, p.y) == Pieces.EMPTY) {
                RestartGame a = new RestartGame(model);
                setVisible(false);
                a.setVisible(true);
            }
            updateBoard();
        }
    }

    static class MyJButton extends JButton {
        MyJButton(Point p) {
            this.p = p;
        }

        Point p;
    }

    static class RestartGame extends JFrame {
        public RestartGame(OthelloModel model) {
            this.setTitle("Othello");
            setSize(600, 600);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            Font f = new Font("TimesRoman", Font.BOLD, 20);

            setLayout(new BorderLayout());

            JLabel restartLabel = new JLabel("WINNER: " + model.winner() +
                    "   Black Points: " + model.blackPoints + "   White Points: " + model.whitePoints);
            restartLabel.setForeground(Color.blue);
            restartLabel.setFont(f);
            restartLabel.setHorizontalAlignment(JLabel.CENTER);
            restartLabel.setBackground(new Color(0, 140, 0));
            restartLabel.setOpaque(true);

            add(restartLabel, BorderLayout.CENTER);
        }
    }
}
