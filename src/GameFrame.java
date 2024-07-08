import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame(){
//        Image icon = new ImageIcon(this.getClass().getResource("/"));
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}

