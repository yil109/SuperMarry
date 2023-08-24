import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyFrame extends JFrame implements KeyListener,Runnable {

    //用于储存所有的背景
    private List<Background> backgroundList = new ArrayList<>();
    //用于储存当前的背景
    private Background currentBackground = new Background();
    //用于双缓存
    private Image offScreenImage = null;
    //定义一个马里奥对象
    private Mario mario = new Mario();
    //定义一个线程对象来实现马里奥的运动
    private Thread thread = new Thread(this);
    //背景音乐
    private Music music;
//    private JButton btnStart = new JButton("Start/开始");
//    private JButton btnInstruction = new JButton("Instruction/说明");
//    private JButton btnExit = new JButton("Exit/退出");
    private String gameStatus = "UI";
    private int isFirst = 1;
    private JLabel labelInstruction = new JLabel();
    private String instructionText;
    private boolean isEnglish = true;
    private String currentScore = " ";
    private String[] instruction;
    private String title = " ";
    private String messageDeath = " ";
    private String messageSuccess = " ";
    private boolean isMusicPause = true;
    private boolean isMusicFirstPlay = true;
//    private JFrame instructionPage = new JFrame();


    public MyFrame(){
        //设置窗口的大小为800 * 600
        this.setSize(800, 600);
        //设置窗口居中显示
        this.setLocationRelativeTo(null);
        //设置窗口的可见性
        this.setVisible(true);
        //设置点击窗口的关闭键，结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口的大小不可变
        this.setResizable(false);
        //初始化图片
        StaticValue.init();
        this.initUI();
        //翻译
        this.translate();
        //设置窗口名称
        this.setTitle(title);

    }

    public void initUI(){
        if(isFirst  == 1){
            backgroundList.add(new Background(0,false));
        }
        //向窗口对象添加键盘监听器
        this.addKeyListener(this);
//        this.addComponent();
//        this.addAction();
//        this.setButtonVisibility(true);
//        this.requestButtonFocus();
        //将userInterface设为初始场景
        currentBackground = backgroundList.get(0);
        repaint();
//        System.out.println(currentBackground);
    }

//    public void setButtonVisibility(boolean isVisible){
//        this.btnStart.setVisible(isVisible);
//        this.btnInstruction.setVisible(isVisible);
//        this.btnExit.setVisible(isVisible);
//    }

//    public void addComponent(){
//
//        btnStart.setLocation(300,300);
//        btnInstruction.setLocation(300,350);
//        btnExit.setLocation(300,400);
//        btnStart.setSize(100,30);
//        btnInstruction.setSize(100,30);
//        btnExit.setSize(100,30);
//        this.add(btnStart);
//        this.add(btnInstruction);
//        this.add(btnExit);
//    }
//
//    public void addAction(){
//        this.btnStart.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                start();
//            }
//        });
//
//        this.btnInstruction.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                createInstructionPage();
//            }
//        });
//
//        this.btnExit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//    }

    public void start(){
        this.gameStatus = "Start";
//        this.setButtonVisibility(false);

//        this.requestFocusInWindow();

        //初始化马里奥
        mario = new Mario(10,300);

        //创建全部的场景
        for(int i = 1; i <= 3; i++){
            if(i == 3){
                backgroundList.add(new Background(i,true));
            }else{
                backgroundList.add(new Background(i,false));
            }
        }
        //将第一个场景设置为当前场景
        currentBackground = backgroundList.get(currentBackground.getSort() + 1);
        mario.setBackground(currentBackground);
        //绘制图像
        repaint();
        if(isFirst == 1){
            thread.start();
        }
        isFirst++;

    }

    public void playMusic(){
        //播放音乐
        try {
            this.music = new Music(System.getProperty("user.dir") +"/src/Music/陈力 - 晴雯歌.wav");
            this.music.play();
        } catch (Exception exception){
            System.out.println("播放音乐错误");
            System.exit(0);
        }
    }

    //暂停音乐
    public void pauseMusic(){
        this.music.pause();
    }

    //恢复音乐播放
    public void resumeMusic() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.music.resumeAudio();
    }


//    public static void main(String[] args){
//        MyFrame myFrame = new MyFrame();
//    }

    @Override
    public void paint(Graphics g) {

        if (this.gameStatus.equals("Pause")) {
            super.paint(g);

        } else {

            super.paint(g);
            if (offScreenImage == null) {
                offScreenImage = createImage(800, 600);
            }

            Graphics graphics = this.offScreenImage.getGraphics();
            graphics.fillRect(0, 0, 800, 600);


            if (this.gameStatus.equals("UI")) {
//                System.out.println(currentBackground);

                graphics.drawImage(currentBackground.getBackgroundImage(), 0, 0, this);

                Color color = graphics.getColor();
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("黑体", Font.BOLD, 25));
                int tempx = 50;
                int tempy = 450;
                translate();
                for(String s : instruction){
                    graphics.drawString(s, tempx, tempy);
                    tempy += 30;
                }
                graphics.setColor(color);


            } else {
                //绘制背景
                graphics.drawImage(currentBackground.getBackgroundImage(), 0, 0, this);

                //绘制敌人
                for (Enemy enemy : this.currentBackground.getEnemyList()) {
                    graphics.drawImage(enemy.getEnemyImage(), enemy.getX(), enemy.getY(), this);
                }

                //绘制障碍物
//                for (Obstacle obstacle : currentBackground.getObstacleList()) {
//                    graphics.drawImage(obstacle.getObstacleImage(), obstacle.getX(), obstacle.getY(), this);
//                }

                for(int i = 0;i < currentBackground.getObstacleList().size();i++){
                    Obstacle obstacle = currentBackground.getObstacleList().get(i);
                    graphics.drawImage(obstacle.getObstacleImage(), obstacle.getX(), obstacle.getY(), this);
                }

                //绘制旗杆
//        graphics.drawImage(currentBackground.getDoNotDecide1(),500,66,this);

                //绘制城堡
                graphics.drawImage(currentBackground.getDoNotDecide2(), 700, 320, this);


                //绘制马里奥
                graphics.drawImage(mario.getImageMario(), mario.getX(), mario.getY(), this);

                //绘制分数
                Color color = graphics.getColor();
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("黑体", Font.BOLD, 25));
                graphics.drawString(currentScore + mario.getScore(), 330, 75);
                graphics.setColor(color);
            }

            //将缓冲区图片绘制到窗口
            g.drawImage(offScreenImage, 0, 0, this);

        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //向右移动
        if(e.getKeyCode() == 39){
//            System.out.println(gameStatus);
            if(gameStatus.equals("Start")){
                mario.moveRight();
            }
        }

        //向左移动
        if(e.getKeyCode() == 37){
            if(gameStatus.equals("Start")){
                mario.moveLeft();
            }
        }

        //跳跃
        if(e.getKeyCode() == 38){
            if(gameStatus.equals("Start")){
                mario.jump();
            }
        }


        //暂停
        if(e.getKeyCode() == 27){
            if(!gameStatus.equals("UI")){
                if(this.gameStatus.equals("Start")){
                    this.gameStatus = "Pause";
                    repaint();
                }else if(this.gameStatus.equals("Pause")){
                    this.gameStatus = "Start";
                }
            }
//            this.requestFocusInWindow();
//            System.out.println(pause);
        }

        if(e.getKeyCode() == 32){
            if(gameStatus.equals("UI")){
                start();
            }
        }

        if(e.getKeyCode() == 84){
            isEnglish = !isEnglish;
            translate();
            repaint();
        }

        if(e.getKeyCode() == 77){
            if(isMusicPause){
                if(isMusicFirstPlay){
                    playMusic();
                    isMusicFirstPlay = false;
                }else {
                    try {
                        resumeMusic();
                    } catch (UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    } catch (LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                isMusicPause = false;
            }else{
                pauseMusic();
                isMusicPause = true;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //向左停止
        if(e.getKeyCode() == 37){
            if(gameStatus.equals("Start")){
                mario.stopLeft();
            }
        }
        //向右停止
        if(e.getKeyCode() == 39){
            if(gameStatus.equals("Start")){
                mario.stopRight();
            }
        }
    }

    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(50);

                if(mario.getX() + mario.getImageMario().getWidth() >= 800){

                    currentBackground = backgroundList.get(currentBackground.getSort() + 1);
                    mario.setBackground(currentBackground);
                    mario.setX(10);
                    mario.setY(300);
                }
//                System.out.println(this.gameStatus);
                if(this.gameStatus.equals("Pause")){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                //判断马里奥是否死亡
                if(mario.isDeath()){
                    JOptionPane.showMessageDialog(this,messageDeath);
                    System.exit(0);

                }

                //判断游戏是否结束了
                if(mario.getIsFinish()){
                    JOptionPane.showMessageDialog(this,messageSuccess);
                    System.exit(0);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }

//    public void requestButtonFocus(){
//        this.btnStart.requestFocusInWindow();
//        this.btnInstruction.requestFocusInWindow();
//        this.btnExit.requestFocusInWindow();
//    }

//    public void createInstructionPage(){
////        this.instructionPage.setLayout(null);
//        //设置窗口的大小为800 * 600
//        this.instructionPage.setSize(400, 300);
//        //设置窗口居中显示
//        this.instructionPage.setLocationRelativeTo(null);
//        //设置窗口的可见性
//        this.instructionPage.setVisible(true);
//        //设置点击窗口的关闭键，结束程序
////        this.instructionPage.setDefaultCloseOperation();
//        //设置窗口的大小不可变
//        this.instructionPage.setResizable(false);
//        //设置窗口名称
//        this.instructionPage.setTitle("Instruction/说明");
//
//        this.instructionText = "This is the instruction of the game";
//        this.labelInstruction = new JLabel();
//        this.labelInstruction.setText(this.instructionText);
//        this.labelInstruction.setLocation(50,50);
//        this.labelInstruction.setSize(50,);
////        this.labelInstruction.setBounds(400,300,30,30);
////        this.labelInstruction.setVisible(true);
////        this.instructionPage.add(this.labelInstruction);
//
//        System.out.println(this.labelInstruction.getX());
//    }



//    public void removeButton(){
//        this.remove(this.btnStart);
//        this.remove(this.btnPlayMusic);
//        this.remove(this.btnPlayMusic);
//    }

    public void translate(){
        if(isEnglish){
            currentScore = "Current Score: ";
            String temp = "Start Game: Press space,Pause/Resume Game: Esc,Control: Up/Down/Left/Right,Translate: T,Play/Pause Music: M";
            instruction = temp.split(",");
            title = "Super Mario";
            messageDeath = "Game Over!";
            messageSuccess = "Congratulations!";
        }else {
            currentScore = "当前分数: ";
            String temp = "开始游戏: 按空格键,暂停/恢复 游戏: Esc,控制: 上/下/左/右,翻译: T,播放/暂停 音乐: M";
            instruction = temp.split(",");
            title = "超级马里奥";
            messageDeath = "游戏结束!";
            messageSuccess = "恭喜你!";
        }
        this.setTitle(title);
//        for (String s : instruction){
//            System.out.println(s);
//        }
//        repaint();
    }

}
