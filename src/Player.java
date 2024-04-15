import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    private final int SIZE = 30;
    private double x;
    private double y;
    private Color color;
    private final double yAccel = 800;
    private double yVel;
    private int lives = 3;
    private boolean onGround;

    // Make sure in order UP, LEFT, RIGHT,  LEFT SHOOT, RIGHT SHOOT
    private int[] controls = new int[5];

    private final double JUMP_SPEED =  (yAccel * .6);
    private final double MOVE_SPEED = 600;
    private double xVel;

    private Bullet[] activeBullets;
    public Player(Color color, int jumpKey, int leftKey, int rightKey, int leftShootKey, int rightShootKey, int WIDTH, int HEIGHT) {
        this.x = Math.random() * (WIDTH * .75);
        this.y = Math.random() * (HEIGHT * .75);
        this.color = color;
        this.yVel = 0;
        this.controls[0] = jumpKey;
        this.controls[1] = leftKey;
        this.controls[2] = rightKey;
        this.controls[3] = leftShootKey;
        this.controls[4] = rightShootKey;
        this.onGround = false;
        this.xVel = 0;
        this.activeBullets = new Bullet[2];
    }

    public void draw(Graphics g)
    {
        g.setColor(this.color);
        g.fillRect((int) this.x, (int) this.y, this.SIZE, this.SIZE);

        for (Bullet b : activeBullets)
        {
            if (b != null)
            {
                b.draw(g);
            }
        }
    }

    public void update(int deltaTime, int width, Player otherPlayer)
    {
        double realDeltaTime = deltaTime / 1000.0;
        this.yVel += this.yAccel * realDeltaTime;
        this.y += this.yVel * realDeltaTime;

        this.x += this.xVel * realDeltaTime;

        for (int i = 0; i < activeBullets.length; i++)
        {
            if (activeBullets[i] != null)
            {
                activeBullets[i].move(deltaTime);
                activeBullets[i].checkCollision(width, otherPlayer);
                if (activeBullets[i].getCollided())
                {
                    activeBullets[i] = null;
                }
            }
        }




    }

    public void jump()
    {
        this.yVel -= JUMP_SPEED;
    }
    public void moveLeft(int deltaTime)
    {
        this.xVel = -MOVE_SPEED;
    }

    public void moveRight(int deltaTime)
    {
        this.xVel = MOVE_SPEED;
    }

    public int getJumpKey()
    {
        return controls[0];
    }

    public int getLeftKey()
    {
        return controls[1];
    }

    public int getRightKey()
    {
        return controls[2];
    }

    public int getLeftShootKey(){return controls[3];}

    public int getRightShootKey(){return controls[4];}

    public double getYVel()
    {
        return this.yVel;
    }

    public void checkCollisions(Platform[] platforms, int width, int height)
    {

        double topY = this.y;
        double bottomY = this.y + SIZE;
        double leftX = this.x;
        double rightX = this.x + SIZE;

        for (Platform platform : platforms)
        {
            if (bottomY > platform.getTop() && topY < platform.getBottom() && leftX < platform.getRight() && rightX > platform.getLeft()) {
                this.onGround = true;
                this.y = platform.getTop() - SIZE + 1;
                this.yVel = 0;
                break;
            } else
            {
                this.onGround = false;
            }

        }

        if (rightX > width)
        {
            xVel = 0;
            this.x = width - SIZE;
        }

        if (leftX < 0)
        {
            xVel = 0;
            this.x = 0;
        }
    }

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getSize()
    {
        return this.SIZE;
    }

    public void setXVel(double xVel)
    {
        this.xVel = xVel;
    }

    public void shoot(boolean dir)
    {
        int d = 1;
        if (!dir)
        {
            d = -1;
        }
        Bullet bullet = new Bullet(800 * d, this.x, this.y);

        for (int i = 0; i < activeBullets.length; i++)
        {
            if (activeBullets[i] == null)
            {
                activeBullets[i] = bullet;
                break;
            }
        }
    }
    
    public void move(KeyEvent e, int DELTA_TIME)
    {
        if (e.getKeyCode() == this.getJumpKey() && this.isOnGround())
        {
            System.out.println(this.isOnGround());
            this.jump();
        }

        if (e.getKeyCode() == this.getLeftKey())
        {
            this.moveLeft(DELTA_TIME);
        }

        if (e.getKeyCode() == this.getRightKey())
        {
            this.moveRight(DELTA_TIME);
        }

        if (e.getKeyCode() == this.getLeftShootKey())
        {
            this.shoot(false);
        }

        if (e.getKeyCode() == this.getRightShootKey())
        {
            this.shoot(true);
        }
    }
    
    public void stopMove(KeyEvent e)
    {
        if (e.getKeyCode() == this.getRightKey())
        {
            this.setXVel(0);
        }

        if (e.getKeyCode() == this.getLeftKey())
        {
            this.setXVel(0);
        }
    }

    public void looseLife()
    {
        if (this.lives > 0)
        {
            this.lives -= 1;
        }

    }

    public int getLives()
    {
       return this.lives;
    }

    public void reset(int WIDTH, int HEIGHT)
    {
        this.lives = 3;
        this.x =  Math.random() * (WIDTH * .75);
        this.y = Math.random() * (HEIGHT * .75);
        this.activeBullets = new Bullet[2];


    }
}
