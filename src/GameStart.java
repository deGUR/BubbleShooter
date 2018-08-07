import javax.swing.*; // библиотека для графического интерфейса

public class GameStart {

    public static void main(String[] args) {
        GamePanel panel = new GamePanel();
        JFrame startFrame = new JFrame("Bubble Game");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрытие по нажатию крестика

        startFrame.setContentPane(panel); //привязываем к фрейму нашу GamePanel
        startFrame.pack(); // frame меняет размер в зависимости от компонентов
        startFrame.setLocationRelativeTo(null); // устанавливаем позицию по центру
        startFrame.setVisible(true);
        panel.start();
    }

}