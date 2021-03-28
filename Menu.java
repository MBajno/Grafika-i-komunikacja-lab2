import java.awt.*;

public class Menu
{
    public Rectangle playButton = new Rectangle(1000/2 -50,250,100,50);
    public Rectangle lvlButton = new Rectangle(1000/2 -210,350,420,50);
    public Rectangle playerButton = new Rectangle(1000/2 -150,450,320,50);
    public Rectangle quitButton = new Rectangle(1000/2 -50,550,100,50);


    public void draw(Graphics g, int choose, boolean players)
    {
        Graphics2D graphics2D = (Graphics2D) g;

        g.setFont(new Font("arial",Font.BOLD,50));
        g.setColor(Color.WHITE);
        g.drawString("AVOID THEM ALL", 1000/3 - 40,200);

        graphics2D.draw(playButton);
        graphics2D.draw(lvlButton);
        graphics2D.draw(playerButton);
        graphics2D.draw(quitButton);

        g.setFont(new Font("arial", Font.BOLD,30));
        g.drawString("Play", playButton.x + 20, playButton.y + 35);
        g.drawString("Difficulty level:", lvlButton.x + 20, lvlButton.y + 35);
        g.drawString("Choose: ", playerButton.x + 20, playerButton.y + 35);
        g.drawString("Exit", quitButton.x + 20, quitButton.y + 35);

        if(players == true)
        {
            g.setFont(new Font("arial",Font.BOLD,30));
            g.drawString("1 player", playerButton.x + 175, playerButton.y + 35);
        }
        else if(players == false)
        {
            g.setFont(new Font("arial",Font.BOLD,30));
            g.drawString("2 players", playerButton.x + 175, playerButton.y + 35);
        }

        if(choose == 1)
        {
            g.setFont(new Font("arial",Font.BOLD,30));
            g.setColor(Color.GREEN);
            g.drawString("Easy", lvlButton.x + 300, lvlButton.y + 35);
        }
        else if(choose == 2)
        {
            g.setFont(new Font("arial",Font.BOLD,30));
            g.setColor(Color.ORANGE);
            g.drawString("Medium", lvlButton.x + 280, lvlButton.y + 35);
        }
        else if(choose == 3)
        {
            g.setFont(new Font("arial",Font.BOLD,30));
            g.setColor(Color.RED);
            g.drawString("Hard", lvlButton.x + 300, lvlButton.y + 35);
        }
    }
}
