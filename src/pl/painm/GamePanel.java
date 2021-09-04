package pl.painm;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int SCREEN_WIDTH=900;
    private final int SCREEN_HEIGHT=500;
    private final int UNIT_SIZE=20;
    private int paddle1;
    private int paddle2;
    private int paddleLength=5*UNIT_SIZE;
    int x=1,y=1;
    int ballx;
    int bally;
    int player1=0,player2=0;
    Timer timer;
    Random random;

    GamePanel(){
        random= new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        paddle();
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        timer= new Timer(70,this);
        newBall();
        timer.start();


    }




    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(0,paddle1,UNIT_SIZE,paddleLength);

        g.setColor(Color.BLUE);
        g.fillRect(SCREEN_WIDTH-UNIT_SIZE,paddle2,UNIT_SIZE,paddleLength);

        g.setColor(Color.YELLOW);
        g.fillOval(ballx,bally,UNIT_SIZE,UNIT_SIZE);

        g.setColor(Color.RED);
        g.setFont(new Font("MV Boil",Font.BOLD,30));
        g.drawString(player1+":"+player2,SCREEN_WIDTH/2,getFont().getSize()+10);

    }

    public void paddle(){
        paddle1=SCREEN_HEIGHT/2-paddleLength/2;
        paddle2=SCREEN_HEIGHT/2-paddleLength/2;
    }

    public void newBall(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ballx=SCREEN_WIDTH/2-UNIT_SIZE/2;
        bally=random.nextInt(SCREEN_HEIGHT/UNIT_SIZE-1)*UNIT_SIZE;
        if(random.nextBoolean())
            x=1;
        else
            x=-1;

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    if (bally>=SCREEN_HEIGHT-UNIT_SIZE)
        y=-1;
    if  (bally<=0) y=1;

        if((ballx==UNIT_SIZE)&&
        ((bally==paddle1)||(bally==paddle1+20)||(bally==paddle1+40)||(bally==paddle1+60)||(bally==paddle1+80)||(bally==paddle1+100)))
            x=-1;
        if((ballx==SCREEN_WIDTH-2*UNIT_SIZE)&&
                ((bally==paddle2)||(bally==paddle2+20)||(bally==paddle2+40)||(bally==paddle2+60)||(bally==paddle2+80)||(bally==paddle2+100)))
            x=1;
        System.out.println(ballx);
        if ( ballx<0){
            player2 +=1;
            newBall();
        }
        if (ballx>=SCREEN_WIDTH) {
            player1 += 1;
            newBall();
        }

        ballx+=-x*UNIT_SIZE;
        bally+=y*UNIT_SIZE;



        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {

            switch(e.getKeyCode()) {

                case KeyEvent.VK_W:
                    if(paddle1>0) {
                        paddle1 -= UNIT_SIZE;
                        repaint();
                    }
                    break;

                case KeyEvent.VK_S:
                    if(paddle1+100<SCREEN_HEIGHT) {
                        paddle1 += UNIT_SIZE;
                        repaint();
                    }
                    break;

                case KeyEvent.VK_UP:
                    if(paddle2>0) {
                        paddle2 -= UNIT_SIZE;
                        repaint();
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if(paddle2+100<SCREEN_HEIGHT) {
                        paddle2 += UNIT_SIZE;
                        repaint();
                    }
                    break;
            }
        }
    }
}

