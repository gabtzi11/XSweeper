import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main
{
    public static final int WIDTH = 15;
    public static final int	HEIGHT = 10;
    public static final int NUM_MINES = 15;

    public Board brd = new Board();

    public static void main(String[] args)
    {
        new Main();
    }

    public Main()
    {
        JFrame frame = new JFrame();
        frame.setTitle("Minesweeper Game");
        frame.getContentPane().setLayout(new GridLayout(HEIGHT, WIDTH));

        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                brd.storeButton(i, j, new BoardSquareButton());
                frame.add(brd.getButton(i, j));
                brd.getButton(i, j).addMouseListener(new MyMouseHandler());
            }
        }
        brd.createMines(NUM_MINES);
        frame.pack();
        frame.setVisible(true);
    }

    public class MyMouseHandler extends MouseAdapter
    {
        public void Zeros(int a, int b)
        {
            for (int k = a-1; k < a+2; k++)
            {
                for (int l = b-1; l < b+2; l++)
                {
                    try
                    {
                        brd.getButton(k, l);
                    }

                    catch (IndexOutOfBoundsException e)
                    {
                        continue;
                    }

                    if ((brd.getButton(k, l) != brd.getButton(a, b)) && (!brd.getButton(k, l).investigated))
                    {
                        brd.getButton(k, l).setInvestigated();
                        if (brd.countSurrounding(k, l) == 0) Zeros(k, l);
                        brd.getButton(k, l).setText(Integer.toString(brd.countSurrounding(k, l)));
                    }
                }
            }
        }

        public void mouseClicked(MouseEvent e)
        {
            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    if (brd.getButton(i, j) == e.getSource())
                    {
                        BoardSquareButton btn = brd.getButton(i, j);
                        if (SwingUtilities.isRightMouseButton(e) && !btn.investigated)
                        {
                            if (btn.possibleMine)
                            {
                                btn.setBackground(Color.gray);
                                btn.setText("?");
                                btn.possibleMine = false;
                            }

                            else btn.setPossibleMine();
                        }

                        else if (!btn.isMine)
                        {
                            btn.setInvestigated();
                            if (brd.countSurrounding(i, j) == 0)
                                Zeros(i, j);
                            btn.setText(Integer.toString(brd.countSurrounding(i, j)));
                        }

                        else
                        {
                            brd.finished();
                            JOptionPane.showMessageDialog(null, "Press OK to restart.", "Boom!", JOptionPane.ERROR_MESSAGE);
                            brd.initialiseAll();
                            brd.createMines(NUM_MINES);
                        }

                        if (brd.hasWon())
                        {
                            JOptionPane.showMessageDialog(null, "Press OK to continue.", "Congratulations!", JOptionPane.ERROR_MESSAGE);
                            brd.initialiseAll();
                            brd.createMines(NUM_MINES);
                        }
                    }
                }
            }
        }
    }
}