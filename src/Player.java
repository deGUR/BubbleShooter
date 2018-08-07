import java.awt.*;

public class Player {

    //Fields
    private double x;
    private double y;
    private int r;

    private double dx; // Коэффицент смешения
    private double dy; // вся эта штука нужна чтобы по диагонали норм двигался

    private int speed;
    private int health;

    private Color color1;
    private Color color2;

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;

    public static boolean isFiring;


    //Constructor
    public Player(){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;

        r = 5;
        speed = 5;
        health = 10;

        dx = 0;
        dy = 0;

        color1 = Color.WHITE;

        up = false;
        down = false;
        left = false;
        right = false;

        isFiring = false;
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
        // Здесь реализована система управления
        if(up && y > r) dy = -speed;
        if(down && y < GamePanel.HEIGHT - r) dy = speed;
        if(left && x > r) dx = -speed;
        if(right && x < GamePanel.WIDTH - r) dx = speed;

        if(up && left || up && right || down && left || down && right){
            dy = dy * Math.sin(Math.PI/4); // используются радианы
            dx = dx * Math.cos(Math.PI/4); // поэтому нельзя писать 45 градусов
        }
        // Обновляем координаты
        y += dy;
        x += dx;
        // Обнуляем смещение
        dy = 0;
        dx = 0;

        if(isFiring) GamePanel.bullets.add(new Bullet());
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3)); // увеличиваем толщину линий
        g.setColor(color1.darker()); // Делаем цвет потемнее
        g.drawOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }

    public void hit(){
        health--;
    }

    public boolean remove(){
        return health <= 0 ? true : false;
    }
}
