import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;


public class gamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 750;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int ENEMY_DIAMETER = 30;
    static final int PLAYER_HEIGHT = 25;
    static final int PLAYER_WIDTH = 25;
    static boolean play = false;
    static boolean players = true;
    static int dif = 1;
    static int difficulty = 1;
    static boolean hit = false;
    static int bounce = 0;
    static int numBall = 1;

    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    Thread gameThread;
    Image image;
    Graphics graphics;
    Player1 player1;
    Player2 player2;
    Clip clip;
    Enemy enemy;
    Score score;

    enum STATE {
        MENU,
        GAME
    }

    static STATE state = STATE.MENU;
    static Menu menu;


    gamePanel() {
        menu = new Menu();
        play = true;
        newPlayer();
        newEnemy();

        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.addMouseListener(new ML());
        this.setPreferredSize(SCREEN_SIZE);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        gameThread = new Thread(this);
        gameThread.start();


    }


    public void checkDif() {
        if (dif == 1) {
            difficulty = 20;
        } else if (dif == 2) {
            difficulty = 30;
        } else if (dif == 3) {
            difficulty = 40;
        }
    }

    public void newEnemy() {
        enemy = new Enemy(((GAME_WIDTH / 2) - (ENEMY_DIAMETER / 2)), ((GAME_HEIGHT / 2) - (ENEMY_DIAMETER / 2)) + 40, ENEMY_DIAMETER, ENEMY_DIAMETER);
        enemies.add(enemy);
    }


    public void newPlayer() {
        player1 = new Player1((GAME_WIDTH / 2) - (PLAYER_WIDTH / 2), 725, PLAYER_WIDTH, PLAYER_HEIGHT);
        player2 = new Player2((GAME_WIDTH / 2) - (PLAYER_WIDTH / 2), 0, PLAYER_WIDTH, PLAYER_HEIGHT);

    }

    public void paint(Graphics g) {
        try {
            image = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);

        if (hit && state == STATE.GAME) {
            play = false;

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over", (GAME_WIDTH / 2) - 70, 300);
            g.drawString("Score: " + score.player, (GAME_WIDTH / 2) - 65, 350);
            g.drawString("Press R to restart", (GAME_WIDTH / 2) - 115, 400);
        }
    }

    public void draw(Graphics g) {
        if (state == STATE.GAME) {
            player1.draw(g);

            if (players == false) {
                player2.draw(g);
            }


            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }

            score.draw(g);
        } else if (state == STATE.MENU) {
            menu.draw(g, dif, players);
        }
    }

    public void move() {
        if (state == STATE.GAME) {
            enemy.move();
        }
    }

    public void click() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        File file = new File("click.wav");
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(sound);
        clip.start();
    }


    public void checkCollision() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        if (enemy.y <= 0 || enemy.y >= GAME_HEIGHT - ENEMY_DIAMETER || enemy.x <= 0 || enemy.x >= GAME_WIDTH - ENEMY_DIAMETER) {
            bounce++;

            if (!hit) {
                score.player += numBall % 3;
            }

            if ((bounce == 0 || bounce % (4 * (numBall + 2)) == 0) && enemies.size() < difficulty) {
                numBall++;
                newEnemy();
            }

            if (dif == 2 && (bounce < 3)) {
                if (enemy.xVelocity > 0) {
                    enemy.xVelocity++;
                } else {
                    enemy.xVelocity--;
                }

                if (enemy.yVelocity > 0) {
                    enemy.yVelocity++;
                } else {
                    enemy.yVelocity--;
                }
                enemy.setXDirection(enemy.xVelocity);
                enemy.setYDirection(enemy.yVelocity);

            } else if (dif == 3 && (bounce < 6)) {
                if (enemy.xVelocity > 0) {
                    enemy.xVelocity++;
                } else {
                    enemy.xVelocity--;
                }

                if (enemy.yVelocity > 0) {
                    enemy.yVelocity++;
                } else {
                    enemy.yVelocity--;
                }
                enemy.setXDirection(enemy.xVelocity);
                enemy.setYDirection(enemy.yVelocity);
            }
        }

        //ball hit player
        if (enemy.intersects(player1) || enemy.intersects(player2)) {
            hit = true;
            enemy.stop();
        }


        //bounce the ball of all edges
        if (enemy.y <= 0) {
            enemy.setYDirection(-enemy.yVelocity);
        }

        if (enemy.y >= GAME_HEIGHT - ENEMY_DIAMETER) {
            enemy.setYDirection(-enemy.yVelocity);
        }

        if (enemy.x <= 0) {
            enemy.setXDirection(-enemy.xVelocity);
        }

        if (enemy.x >= GAME_WIDTH - ENEMY_DIAMETER) {
            enemy.setXDirection(-enemy.xVelocity);
        }


        //stops player at edges
        if (player1.x <= 0) {
            player1.x = 0;
        }

        if (player1.y <= 0) {
            player1.y = 0;
        }

        if (player1.x >= (GAME_WIDTH - PLAYER_WIDTH)) {
            player1.x = GAME_WIDTH - PLAYER_WIDTH;
        }

        if (player1.y >= (GAME_HEIGHT - PLAYER_HEIGHT)) {
            player1.y = GAME_HEIGHT - PLAYER_HEIGHT;
        }


        if (player2.x <= 0) {
            player2.x = 0;
        }

        if (player2.y <= 0) {
            player2.y = 0;
        }

        if (player2.x >= (GAME_WIDTH - PLAYER_WIDTH)) {
            player2.x = GAME_WIDTH - PLAYER_WIDTH;
        }

        if (player2.y >= (GAME_HEIGHT - PLAYER_HEIGHT)) {
            player2.y = GAME_HEIGHT - PLAYER_HEIGHT;
        }




    }

    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amoutOfTicks = 60.0;
        double ns = 1000000000 / amoutOfTicks;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            checkDif();

            if (delta >= 1) {
                player1.move();
                player2.move();
                for (int i = 0; i < numBall; i++) {
                    enemy = enemies.get(i);
                    move();

                    try {
                        checkCollision();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
                delta--;
            }

        }
    }


    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            player1.keyPressed(e);
            player2.keyPressed(e);

            if (e.getKeyCode() == KeyEvent.VK_R) {
                if (!play) {
                    play = true;

                    for (int i = enemies.size() - 1; i >= 0; i--) {
                        enemies.remove(i);
                    }

                    hit = false;
                    newEnemy();
                    newPlayer();
                    score.player = 0;
                    numBall = 1;
                    bounce = 0;

                    repaint();
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (state == STATE.GAME) {
                    state = STATE.MENU;
                    play = false;
                    hit = false;

                    for (int i = enemies.size() - 1; i >= 0; i--) {
                        enemies.remove(i);
                    }

                    newEnemy();
                    newPlayer();
                    score.player = 0;
                    numBall = 1;
                    bounce = 0;

                    repaint();
                }
            }

        }

        public void keyReleased(KeyEvent e) {
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }

    public class ML implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int menuX = e.getX();
            int menuY = e.getY();

            //play
            if (menuX >= (GAME_WIDTH / 2) - 10 && menuX <= (GAME_WIDTH / 2) + 50) {
                if (menuY >= 250 && menuY <= 300) {
                    try {
                        click();
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                        ioException.printStackTrace();
                    }


                    state = STATE.GAME;
                    play = true;
                    bounce = 0;
                }
            }

            //difficulty
            if (menuX >= (GAME_WIDTH / 2) - 210 && menuX <= (GAME_WIDTH / 2) + 210) {
                if (menuY >= 350 && menuY <= 400) {
                    if (dif == 1) {
                        try {
                            click();
                        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ioException) {
                            ioException.printStackTrace();
                        }
                        dif = 2;
                    } else if (dif == 2) {
                        try {
                            click();
                        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ioException) {
                            ioException.printStackTrace();
                        }
                        dif = 3;
                    } else if (dif == 3) {
                        try {
                            click();
                        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ioException) {
                            ioException.printStackTrace();
                        }
                        dif = 1;
                    }
                }
            }

            //players
            if (menuX >= (GAME_WIDTH / 2) - 160 && menuX <= (GAME_WIDTH / 2) + 200) {
                if (menuY >= 450 && menuY <= 500) {
                    if (players == true) {
                        try {
                            click();
                        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ioException) {
                            ioException.printStackTrace();
                        }
                        players = false;
                    } else if (players == false) {
                        try {
                            click();
                        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ioException) {
                            ioException.printStackTrace();
                        }
                        players = true;
                    }
                }
            }


            //exit
            if (menuX >= (GAME_WIDTH / 2) - 50 && menuX <= (GAME_WIDTH / 2) + 50) {
                if (menuY >= 550 && menuY <= 600) {
                    try {
                        click();
                        System.exit(1);


                    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ioException) {
                        ioException.printStackTrace();

                    }
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

}
