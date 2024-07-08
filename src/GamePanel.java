import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = (screenHeight*screenWidth)/unitSize;
    static final int delay = 75;
    final int x[] = new int[gameUnits];
    final int y[] = new int[gameUnits];
    int bodyPart = 6;
    int applesEaten;
    int applesX ;
    int applesY ;
    char direction = 'R';
    boolean running = false;
    Timer timer;

    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize( new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApples();
        running= true;
        timer = new Timer(delay,this);
        timer.start();
        timer.setDelay(100);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
//            for (int i = 0; i < screenHeight / unitSize; i++) {
//                g.drawLine(i * unitSize, 0, i * unitSize, screenHeight);
//                g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
//            }
            g.setColor(Color.red);
            g.fillOval(applesX, applesY, unitSize, unitSize);

            for (int i = 0; i < bodyPart; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,45));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(screenWidth - fontMetrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApples(){
        applesX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
        applesY = random.nextInt((int)(screenHeight/unitSize))*unitSize;
    }
    public void move(){
        for(int i =bodyPart;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0] - unitSize;
                break;
            case 'D':
                y[0] = y[0]+unitSize;
                break;
            case 'L':
                x[0] = x[0]-unitSize;
                break;
            case 'R':
                x[0] = x[0]+unitSize;
                break;
        }
    }
    public void apple(){
        if((x[0] == applesX) && (y[0]== applesY)){
            bodyPart++;
            applesEaten++;
            newApples();
        }
    }
    public void collision(){
        for(int i = bodyPart; i>0;i--){
            if((x[0] == x[i]) && (y[0]==y[i])){
                running = false;
            }
            if(x[0]<0){
                running =false;
            }
            if(x[0]>screenWidth){
                running =false;
            }
            if(y[0]<0){
                running =false;
            }
            if(y[0]>screenHeight){
                running = false;
            }
            if(!running){
                timer.stop();
            }
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Instagram Sans",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(("Game Over"), (screenWidth - metrics.stringWidth("Game Over"))/2,(screenHeight)/2);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,45));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(screenWidth - fontMetrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            apple();
            collision();
        }
        repaint();
    }
    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }

    }
}
