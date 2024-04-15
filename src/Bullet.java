import java.awt.*;

public class Bullet {
    private double speed;
    private final int RADIUS = 15;
    private double x;
    private double y;
    private boolean hasCollided = false;

    public Bullet(double speed, double x, double y) {
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval((int) this.x, (int) this.y, RADIUS, RADIUS);
    }

    public void move(double deltaTime)
    {
        this.x += speed * deltaTime / 1000;
    }

    public boolean getCollided()
    {
        return hasCollided;
    }

    public void checkCollision(int width, Player otherPlayer)
    {
        if (this.x + RADIUS > width)
        {
            hasCollided = true;
        }

        if (this.x - RADIUS < 0)
        {
            hasCollided = true;
        }



        double rx = otherPlayer.getX();
        double ry = otherPlayer.getY();
        double s = otherPlayer.getSize();
        double testX = x;
        double testY = y;

        if (x < rx)         testX = rx;      // test left edge
        else if (x > rx+s) testX = rx+s-15;   // right edge
        if (y < ry)         testY = ry;      // top edge
        else if (y > ry+s) testY = ry+s;   // bottom edge

        // get distance from closest edges
        double distX = x-testX;
        double distY = y-testY;
        double distance = Math.sqrt( (distX*distX) + (distY*distY) );

        // if the distance is less than the radius, collision!
        if (distance <= RADIUS) {
            hasCollided = true;
            otherPlayer.looseLife();
            System.out.println("HIT");
        }


    }


}
