import java.awt.*;

public class Enemy {
    //Fields
    private double x;
    private double y;
    private int r;

    private double speed;
    private double dx;
    private double dy;

    private double health;

    private int type; // тип врага
    private int rank;

    private Color color;

    //Constructor
    public Enemy(int type, int rank){
        this.type = type;
        this.rank = rank;

        switch (type){
            case 1:
                switch (rank){
                    case 1:
                        color = Color.GREEN;
                        x = Math.random() *  GamePanel.WIDTH;
                        y = 0;

                        r = 7;

                        speed = 2;

                        health = 2;

                        double angle = Math.toRadians(Math.random()*360);
                        dx = Math.sin(angle) * speed;
                        dy = Math.cos(angle) * speed;
                }
        }
    }


    //Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }


    //Functions
    public void update(){
        x += dx;
        y += dy;

        if(x < 0 && dx < 0) dx = -dx;
        if(x > GamePanel.WIDTH && dx > 0) dx = -dx;

        if(y < 0 && dy < 0) dy = -dy;
        if(y > GamePanel.HEIGHT && dy > 0) dy = -dy;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);
    }

    public void hit(){
        health--;
    }

    public boolean remove(){
        return health <= 0 ? true : false;
    }
}
