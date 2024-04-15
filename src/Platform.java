import java.awt.*;

public class Platform {
    private int height;
    private int width;
    private int x;
    private int y;
    Color color;

    public Platform(int height, int width, int x, int y, Color color) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void draw(Graphics g)
    {
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

    public int getTop()
    {
        return this.y;
    }

    public int getBottom()
    {
        return this.y + this.height;
    }

    public int getRight()
    {
        return this.x + this.width;
    }

    public int getLeft()
    {
        return this.x;
    }


}
