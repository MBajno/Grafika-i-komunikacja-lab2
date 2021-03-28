import java.awt.*;
import java.awt.event.*;

public class Player2 extends Rectangle {
    int xVelocity;
    int yVelocity;
    int speed = 10;

    Player2(int x, int y, int PLAYER_WIDTH, int PLAYER_HEIGHT) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void keyPressed(KeyEvent e) {

        if ((e.getKeyCode() == KeyEvent.VK_D) && (gamePanel.play)) {
            setxDirection(speed);
            move();
        }

        if ((e.getKeyCode() == KeyEvent.VK_A) && (gamePanel.play)) {
            setxDirection(-speed);
            move();
        }

        if ((e.getKeyCode() == KeyEvent.VK_W) && (gamePanel.play)) {
            setyDirection(-speed);
            move();
        }

        if ((e.getKeyCode() == KeyEvent.VK_S) && (gamePanel.play)) {
            setyDirection(speed);
            move();
        }

    }

    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_D) {
            setxDirection(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            setxDirection(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            setyDirection(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            setyDirection(0);
            move();
        }

    }

    public void setxDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void setyDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        x = x + xVelocity;
        y = y + yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}
