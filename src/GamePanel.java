import javax.swing.*;
        import java.awt.*;
        import java.awt.image.BufferedImage;
        import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    //Field
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    private Thread thread; // Создаем поток
    private BufferedImage image; // Создание холста
    private Graphics2D g; // Создание кисточки

    public static GameBackground background;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;

    //Constructor
    public GamePanel() {
        super(); // вызываем конструктор наследуемого нами класса

        setPreferredSize(new Dimension(WIDTH, HEIGHT)); //задаем размеры панели
        addKeyListener(new Listeners()); // Добавили Listener кнопок
        setFocusable(true);
        requestFocus();
    }


    //Functions
    public void start(){
        thread = new Thread(this);
        thread.start(); // запускаем поточек
    }

    @Override
    public void run() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        // Дополнительное улучшение визуализации
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        background = new GameBackground();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        wave = new Wave();

        while(true){ // TODO states

            gameUpdate();
            gameRender();
            gameDraw();

            try {
                thread.sleep(33); // TODO FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void gameUpdate(){
        // Background update
        background.update();

        // Player update
        player.update();

        // Bullets update
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).update();
            // удаляет пулю
            if(bullets.get(i).remove()) {
                bullets.remove(i);
                i--;
            }
        }

        // Enemies update
        for(int i = 0; i < enemies.size(); i++)
            enemies.get(i).update();

        //Bullets-enemies collide
        for(int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();

            for(int j = 0; j < bullets.size(); j++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();

                double dx = ex - bx;
                double dy = ey - by;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if(dist <= e.getR() + b.getR()){
                    e.hit();
                    bullets.remove(j);
                    j--;

                    boolean remove = e.remove();
                    if (remove){
                        enemies.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }

        //Player-Enemies collide
        for(int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();

            double px = player.getX();
            double py = player.getY();

            double dx = ex - px;
            double dy = ey - py;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if(dist <= e.getR() + player.getR()){
                e.hit();
                player.hit();

                boolean removeE = e.remove();
                if (removeE){
                    enemies.remove(i);
                    i--;
                }
            }
        }

        //Wave update
        wave.update();
    }

    public void gameRender(){
        // Background draw
        background.draw(g);

        // Player draw
        player.draw(g);

        // Bullets draw
        for(int i = 0; i < bullets.size(); i++)
            bullets.get(i).draw(g);

        // Enemies draw
        for(int i = 0; i < enemies.size(); i++)
            enemies.get(i).draw(g);

        // Wave draw
        if(wave.showWave())
            wave.draw(g);
    }

    private void gameDraw(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose(); // уничтожает g2
    }
}