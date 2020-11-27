import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class BoardSquareButton extends JButton
{
    boolean isMine;
    boolean investigated;
    boolean possibleMine;

    public BoardSquareButton()
    {
        setPreferredSize(new Dimension(60, 60));
        initialise();
    }

    public void initialise()
    {
        setBackground(Color.gray);
        setFont(new Font(null, Font.BOLD, 30));
        setText("?");
        investigated = false;
        possibleMine = false;
        isMine = false;
    }

    public void setMine()
    {
        isMine = true;
    }

    public void setInvestigated()
    {
        investigated = true;
        setBackground(Color.green);
        setFont(new Font(null, Font.BOLD, 30));
    }

    public void setPossibleMine()
    {
        possibleMine = true;
        setText("!");
        setBackground(Color.orange);
        setFont(new Font(null, Font.BOLD, 30));
    }
}
