//required import statements

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main extends JPanel {

    // Height and Width for Frame
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    // deltaTime
    private final int DELTA_TIME = 10;

    // Graphics Stuff
    private final BufferedImage image;
    private final Graphics g;
    private Timer timer;

    // Test platform
    Platform testPlatform = new Platform(50, WIDTH, 0, HEIGHT - 50, Color.black);
    Platform testPlatform2 = new Platform(25, WIDTH / 4, 0, HEIGHT - 200, Color.black);

    Platform testPlatform3 = new Platform(25, WIDTH / 4, WIDTH - WIDTH / 4, HEIGHT - 200, Color.black);
    Platform testPlatform4 = new Platform(25, WIDTH / 4, WIDTH / 2 - WIDTH / 8, HEIGHT - 375, Color.black);

    Platform testPlatform5 = new Platform(25, WIDTH / 4, 0, HEIGHT - 550, Color.black);

    Platform testPlatform6 = new Platform(25, WIDTH / 4, WIDTH - WIDTH / 4, HEIGHT - 550, Color.black);

    Platform[] array = new Platform[]{testPlatform, testPlatform2, testPlatform3, testPlatform4, testPlatform5, testPlatform6};

    Player testPlayer = new Player(Color.red, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E, WIDTH, HEIGHT);
    Player player2 = new Player(Color.blue, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_U, KeyEvent.VK_O, WIDTH, HEIGHT);

    boolean gameOver = false;

    public Main() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();


        timer = new Timer(DELTA_TIME, new TimerListener());
        timer.start();

        addKeyListener(new Keyboard()); //new code
        setFocusable(true); //new code

        playMusic();

    }

    //private nested class for the timer
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //move the cloud and redraw the ENTIRE scene
            //(in this case just the background + cloud)

            if (player2.getLives() == 0 || testPlayer.getLives() == 0) {
                gameOver = true;
            }

            if (!gameOver) {
                g.setColor(Color.lightGray);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                for (Platform platform : array) {
                    platform.draw(g);
                }

                testPlayer.draw(g);
                testPlayer.update(DELTA_TIME, WIDTH, player2);
                testPlayer.checkCollisions(array, WIDTH, HEIGHT);

                player2.draw(g);
                player2.update(DELTA_TIME, WIDTH, testPlayer);
                player2.checkCollisions(array, WIDTH, HEIGHT);


                g.setFont(new Font("TimesRoman", Font.PLAIN, 32));

                g.setColor(Color.RED);
                g.drawString("RED: " + testPlayer.getLives(), 50, 50);

                g.setColor(Color.BLUE);
                g.drawString("BLUE: " + player2.getLives(), WIDTH - 200, 50);
            } else {
                String gameMessage;
                if (player2.getLives() == 0) {
                    gameMessage = "RED WINS";
                } else {
                    gameMessage = "BLUE WINS";
                }

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 32));

                g.drawString(gameMessage, WIDTH / 2 - 100, HEIGHT / 2);
                g.drawString("CLICK R TO PLAY AGAIN", WIDTH / 2 - 200, HEIGHT / 2 + 50);

            }

            repaint();
        }
    }

    //paintComponent should look EXACTLY like this when using buffered images
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    //standard graphics main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animation");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Main());
        frame.setVisible(true);
    }

    private class Keyboard implements KeyListener {
        public void keyPressed(KeyEvent e) {
            testPlayer.move(e, DELTA_TIME);
            player2.move(e, DELTA_TIME);

            if (e.getKeyCode() == KeyEvent.VK_R) {
                if (gameOver) {
                    gameOver = false;
                    player2.reset(WIDTH, HEIGHT);
                    testPlayer.reset(WIDTH, HEIGHT);
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            testPlayer.stopMove(e);
            player2.stopMove(e);
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    //sound files are stored and accessed the same as images
    public void playMusic() {
        File soundFile;
        soundFile = new File("src/Hornet.wav");
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY); //several options here
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Audio error");
        }
    }
}