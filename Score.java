import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle
{
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player;

    Score(int GAME_WIDTH, int GAME_HEIGHT)
    {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;

    }

    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN,30));
        g.drawString("Score:",720,40);
        g.drawString(String.valueOf(player),910,40);

    }
}
