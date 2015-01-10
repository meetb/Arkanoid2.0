//This is the main file of this project.

package gamedevelopment;

// Importing all the classes needed.

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createLineBorder;

public class GameDevelopment extends JPanel implements Runnable { // This class extends JPanel to add to a JFrame and implements type Runnable to run threads simultaneously with similar object instances

    //declaring global variables while keeping them private/final accordingly.
    private ArrayList<GameObject> objects = new ArrayList<GameObject>(); // This arraylist grows as needed, and it stores all the objects of type GameObject.
    final Color[] colours = {Color.GREEN, new Color(76, 0, 153), Color.BLUE, Color.YELLOW, Color.RED, Color.GRAY};
    private boolean run = false;
    private boolean first = true;
    private boolean pressed = false;
    private JButton start = new JButton("Start");
    private int score = 0;
    final Random randG = new Random();
    final int x = 22;
    final int y = 60;
    
    //This is a keylistener which allows the program to detect when to move the player board when the arrow keys are pressed, released, or typed.
    private KeyListener keys = new KeyAdapter() {
        public void keyTyped(KeyEvent ke) {
        }

        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                setPlayer(-8);
                if (!pressed) {
                    setBall(-8, 0);
                }
                repaint();
            }
            if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                setPlayer(8);
                if (!pressed) {
                    setBall(8, 0);
                }
                repaint();
            }
            if (first) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    setBall(4, 4);
                    first = false;
                    pressed = true;
                }
            }
        }

        public void keyReleased(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                setPlayer(0);
                if (!pressed) {
                    setBall(0, 0);
                }
                repaint();
            }
        }
    };

    // This is a mouselistener to add for mouse support and it lets go of the ball at the starting of the game when the left mouse button is clicked.
    private MouseListener mouseClick = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            if (first) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    setBall(4, 4);
                    first = false;
                    pressed = true;
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }
    };
    
    // This adds for mouse movement control and moves the player board when playing the game.
    private MouseMotionListener mouseMovement = new MouseAdapter() {
        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            int leftX = ((Player) objects.get(checkInstance(3) - 1)).getX();
            int rightX = leftX + ((Player) objects.get(checkInstance(3) - 1)).getWidth();
            if (e.getX() < leftX) {
                setPlayer(-8);
                if (!pressed) {
                    setBall(-8, 0);
                }
                repaint();
            } else if (e.getX() > rightX) {
                setPlayer(8);
                if (!pressed) {
                    setBall(8, 0);
                }
                repaint();
            } else {
                setPlayer(0);
                if (!pressed) {
                    setBall(0, 0);
                }
                repaint();
            }
        }
    };

    public void paintComponent(Graphics g) {
        ImageIcon bg = new ImageIcon("backgroundOne.png");
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
        if (run == true) {
            bg = new ImageIcon("background.jpg");
            g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            Graphics2D g2 = (Graphics2D) g;
            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).paintComponent(g2);
            }
            g2.setFont(new Font("Papyrus", Font.ITALIC, 30));
            g2.setColor(Color.WHITE);
            g2.drawString("Score", 10, getHeight() - 30);
            g2.drawString(score + "", 100, getHeight() - 30);
        }
    }

    public GameDevelopment() {
        // This creates the JFrame onto which the GameDevelopment panel will fit onto and all the gameobjects.
        JFrame frame = new JFrame();
        start.setPreferredSize(new Dimension(100, 50));
        start.addActionListener(new start());

        this.setBorder(createCompoundBorder(createEmptyBorder(10, 10, 70, 10), createLineBorder(Color.WHITE, 5, true)));
        this.add(start);
        frame.setContentPane(this);
        addKeyListener(keys);
        addMouseListener(mouseClick);
        addMouseMotionListener(mouseMovement);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setPreferredSize(new Dimension(700, 600));
        setFocusable(true);
        frame.pack();
        setup(randG, x, y);
    }
    
    // Sets up the game in the starting by adding the ball, player board, and blocks.
    public void setup(Random randG, int x, int y) {
        objects.add(new Ball(this.getWidth() / 2, 469, 11, Color.RED, 0, 0));
        objects.add(new Player(this.getWidth() - 32, getHeight() - 92, 15, 10, 2, 2, Color.BLACK, 0, 3, -1));
        objects.add(new Player(this.getWidth() - 35, getHeight() - 95, 15, 10, 2, 2, Color.GRAY, 0, 3, -1));

        //2nd life
        objects.add(new Player(this.getWidth() - 62, getHeight() - 92, 15, 10, 2, 2, Color.BLACK, 0, 3, -1));
        objects.add(new Player(this.getWidth() - 65, getHeight() - 95, 15, 10, 2, 2, Color.GRAY, 0, 3, -1));

        //3rd life
        objects.add(new Player(this.getWidth() / 2 - 39, 482, 75, 12, 4, 4, Color.BLACK, 0, 3, -1));
        objects.add(new Player(this.getWidth() / 2 - 37, 480, 75, 12, 4, 4, Color.GRAY, 0, 3, -1));
        
        while (objects.size() < 147) {
            int random = randG.nextInt(6);
            objects.add(new Blocks(x, y, 30, 13, colours[random], random + 1));
            if (x + 33 >= getWidth() - 30) {
                x = 22;
                y += 16;
            } else {
                x += 33;
            }
        }

        // This for loop accounts for the powerups that have already been used up to make sure there are only a set amount in the game and randomly places it in one of the blocks.
        int[] alreadyUsed = new int[20];
        int place;
        //11
        for (int i = 0; i < 20; i++) {
            if (checkInstance(4) != -1) {
                place = checkInstance(3) + randG.nextInt(checkInstance(4) - checkInstance(3));
            } else {
                place = checkInstance(3) + randG.nextInt(objects.size() - checkInstance(3));
            }
            for (int j = 0; j < i; j++) {
                if (alreadyUsed[j] == place) {
                    j -= 1;
                    break;
                } else if (i - 1 == j) {
                    objects.add(new PowerUp(objects.get(place).getX(), objects.get(place).getY(), 30, 13, 0, place, randG.nextInt(4), ((Blocks) objects.get(place)).getColour()));
                }
            }
        }
    }

    // Since the GameObject arraylist composes of different child objects and classes, this method differentiates between them by returning the ith element at which a certain type of GameObject ends or starts in the arrayList named "objects".
    // 1 = ball, 2 = player, 3 = blocks, 4 = powerups
    public int checkInstance(int j) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof Ball && j == 1) {
                return i;
            }
            if (objects.get(i) instanceof Player && j == 2) {
                return i;
            }
            if (objects.get(i) instanceof Blocks && j == 3) {
                return i;
            }
            if (objects.get(i) instanceof PowerUp && j == 4) {
                return i;
            }
        }
        return -1;
    }
    
    // This function takes care of the immediate consequences of the player getting a powerUp
    // type: 0 = Enlarge, 1 = slow, 2 = speed, 3 = multi, 4 = life;
    public void powerUp(int type) {
        System.out.println(type);
        score += 100;
        if (((Player) objects.get(checkInstance(3) - 1)).getPUp() != -1) {
            removePowerUp(((Player) objects.get(checkInstance(3) - 1)).getPUp());
        }
        if (type == 0) {
            objects.get(checkInstance(3) - 2).setWidth(objects.get(checkInstance(3) - 2).getWidth() + 10);
            objects.get(checkInstance(3) - 1).setWidth(objects.get(checkInstance(3) - 1).getWidth() + 10);
        } else if (type == 1) {
            ((Ball) objects.get(0)).setXSpeed(((Ball) objects.get(0)).getXSpeed() - 4);
        } else if (type == 2) {
            ((Ball) objects.get(0)).setXSpeed(((Ball) objects.get(0)).getXSpeed() + 2);
        } else if (type == 3) {
            objects.add(1, new Ball(objects.get(0).getX() + 20, objects.get(0).getY(), 10, Color.RED, ((Ball) objects.get(0)).getXSpeed() * -1, ((Ball) objects.get(0)).getYSpeed()));
            objects.add(2, new Ball(objects.get(0).getX() - 20, objects.get(0).getY(), 10, Color.RED, ((Ball) objects.get(0)).getXSpeed(), ((Ball) objects.get(0)).getYSpeed() + 2));
        } else if (type == 4) {
            objects.add(checkInstance(3) - 2, new Player(objects.get(checkInstance(3) - 4).getX() - 30, this.getHeight() - 12, 15, 10, 2, 2, Color.BLACK, 0, 3, -1));
            objects.add(checkInstance(3) - 2, new Player(objects.get(checkInstance(3) - 4).getX() - 30, this.getHeight() - 15, 15, 10, 2, 2, Color.GRAY, 0, 3, -1));
        }
        ((Player) objects.get(checkInstance(3) - 1)).setPUp(type);
    }

    // type: 0 = Enlarge, 1 = slow, 2 = speed, 3 = multi, 4 = life;
    public void removePowerUp(int type) {
        if (type == 0) {
            objects.get(checkInstance(3) - 2).setWidth(objects.get(checkInstance(3) - 2).getWidth() - 10);
            objects.get(checkInstance(3) - 1).setWidth(objects.get(checkInstance(3) - 1).getWidth() - 10);
        } else if (type == 1) {
            for (int j = checkInstance(2) - 1; j >= 0; j--) {
                ((Ball) objects.get(j)).setXSpeed(((Ball) objects.get(j)).getXSpeed() + 2);
            }
        } else if (type == 2) {
            for (int j = checkInstance(2) - 1; j >= 0; j--) {
                ((Ball) objects.get(j)).setXSpeed(((Ball) objects.get(j)).getXSpeed() - 2);
            }
        } else if (type == 3) {
            objects.remove(1);
            objects.remove(1);
        }
    }

    public void run() {
        while (run) {
            // Check for collision, draw objects and sleep 
            for (int j = checkInstance(2) - 1; j >= 0; j--) {
                if (((Ball) objects.get(j)).checkCollisionPlayer(((Player) objects.get(checkInstance(3) - 1)))) {
                    playSound(0);
                    ((Ball) objects.get(j)).reverseYSpeed();
                    ((Ball) objects.get(j)).collisions(objects.get(checkInstance(3) - 1), 10);
                }
            }
            // Takes care of the number of hits a certain block has taken and removes if appropriate.
            for (int i = checkInstance(3); i < objects.size() - (objects.size() - checkInstance(4)); i++) {
                for (int j = checkInstance(2) - 1; j >= 0; j--) {
                    if (((Ball) objects.get(j)).checkCollisionBlock((Blocks) objects.get(i))) {
                        score += 50;
                        ((Ball) objects.get(j)).reverseYSpeed();
                        ((Ball) objects.get(j)).collisions(objects.get(i), 1);
                        ((Blocks) objects.get(i)).setTolerance(((Blocks) objects.get(i)).getTolerance() - 1);
                    }
                }
                if (((Blocks) objects.get(i)).getTolerance() == 0) {
                    objects.remove(i);
                    if (checkInstance(3) == -1) {
                        removeKeyListener(keys);
                        removeMouseListener(mouseClick);
                        removeMouseMotionListener(mouseMovement);
                        JOptionPane.showMessageDialog(this, "Congrats, you won!");
                        run = false;
                        break;
                    }
                } else {
                    ((Blocks) objects.get(i)).setColor(colours[((Blocks) objects.get(i)).getTolerance() - 1]);
                }
                if (!run) {
                    break;
                }
            }
            // This checks if a player got a powerUp or not and removes it from the panel if the player got it.
            for (int j = checkInstance(4); j < objects.size(); j++) {
                if (!run) {
                    break;
                }
                for (int i = checkInstance(2) - 1; i >= 0; i--) {
                    if (((Ball) objects.get(i)).checkCollisionPowerUp((PowerUp) objects.get(j))) {
                        ((PowerUp) objects.get(j)).setColour(colours[((PowerUp) objects.get(j)).getType()]);
                        ((PowerUp) objects.get(j)).setCollision();
                        ((PowerUp) objects.get(j)).setSpeed(2);
                    }
                }
                if (((Player) objects.get(checkInstance(3) - 1)).checkCollisionPowerUp((PowerUp) objects.get(j))) {
                    playSound(1);
                    int type = ((PowerUp) objects.get(j)).getType();
                    objects.remove(j);
                    powerUp(type);
                    break;
                }
            }
            // Adds a new ball if a life is lost.
            if (((Ball) objects.get(0)).getDeath()) {
                objects.remove(checkInstance(2));
                objects.remove(checkInstance(2));
                objects.remove(0);
                objects.add(0, new Ball(this.getWidth() / 2, 469, 11, Color.RED, 0, 0));
                if (checkInstance(2) != -1) {
                    ((Player) objects.get(checkInstance(3) - 1)).setX(this.getWidth() / 2);
                    ((Player) objects.get(checkInstance(3) - 2)).setX(this.getWidth() / 2);
                }
                first = true;
                pressed = false;
            }
            // Checks if you lost all of your lives.
            if (checkInstance(2) == -1) {
                removeKeyListener(keys);
                removeMouseListener(mouseClick);
                removeMouseMotionListener(mouseMovement);
                JOptionPane.showMessageDialog(this, "Better luck next time, you lost.");
                run = false;
                break;
            }
            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).update(this);
            }
            repaint();
            try {
                Thread.sleep(17);
            } catch (Exception e) {
            }
        }
        this.addKeyListener(keys);
        this.addMouseListener(mouseClick);
        this.addMouseMotionListener(mouseMovement);
        objects = new ArrayList<GameObject>();
        score = 0;
        first = true;
        pressed = false;
        setup(randG, x, y);
        start.setLabel("Restart");
        this.add(start);

    }

    public void start() {
        this.remove(start);
        Thread thread = new Thread(this);
        run = true;
        thread.start();
    }

    public void playSound(int sound) {
        String file = "";

        switch (sound) {
            case 0:
                file = "HitBlock";
                break;
            case 1:
                file = "ObtainPowerUp";
                break;
            case 2:
                file = "Death";
                break;
        }
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(file + ".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
        }
    }

    public void stop() {
        run = false;
    }

    public static void main(String[] args) {
        GameDevelopment control = new GameDevelopment();
    }

    public class start implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            start();
        }
    }

    public void setPlayer(int xSpeed) {
        ((Player) objects.get(checkInstance(3) - 1)).setXSpeed(xSpeed);
        ((Player) objects.get(checkInstance(3) - 2)).setXSpeed(xSpeed);
    }

    public void setBall(int xSpeed, int ySpeed) {
        ((Ball) objects.get(checkInstance(1))).setYSpeed(ySpeed);
        ((Ball) objects.get(checkInstance(1))).setXSpeed(xSpeed);
    }
}
