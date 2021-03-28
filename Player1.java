import java.awt.*;
import java.awt.event.*;

public class Player1 extends Rectangle
{
    int xVelocity;
    int yVelocity;
    int speed = 10;

    Player1(int x, int y, int PLAYER_WIDTH, int PLAYER_HEIGHT)
    {
        super(x,y,PLAYER_WIDTH,PLAYER_HEIGHT);
    }

    public void keyPressed(KeyEvent e)
    {
        if((e.getKeyCode() == KeyEvent.VK_RIGHT) && (gamePanel.play))
        {
            setxDirection(speed);
            move();
        }

        if((e.getKeyCode() == KeyEvent.VK_LEFT) && (gamePanel.play))
        {
            setxDirection(-speed);
            move();
        }

        if((e.getKeyCode() == KeyEvent.VK_UP) && (gamePanel.play))
        {
            setyDirection(-speed);
            move();
        }

        if((e.getKeyCode() == KeyEvent.VK_DOWN) && (gamePanel.play))
        {
            setyDirection(speed);
            move();
        }

    }

    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            setxDirection(0);
            move();
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            setxDirection(0);
            move();
        }

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            setyDirection(0);
            move();
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            setyDirection(0);
            move();
        }

    }

    public void setxDirection(int xDirection)
    {
        xVelocity = xDirection;
    }

    public void setyDirection(int yDirection)
    {
        yVelocity = yDirection;
    }

    public void move()
    {
        x = x + xVelocity;
        y = y + yVelocity;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
}
