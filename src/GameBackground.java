import java.awt.*;

public class GameBackground {

    //Field
    private Color color;


    //Constructor
    public GameBackground(){
        color = Color.BLUE;
    }


    //Functions
    public void update(){

    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}
