import java.awt.image.BufferedImage;

public class Mario implements Runnable{
    //表示坐标
    private int x,y;
    //表示当前状态
    private String status;
    //表示当前状态对应的图片
    private BufferedImage imageMario = null;
    //定义一个background对象 用于获取障碍物信息
    private Background background = new Background();
    //用来实现马里奥的动作
    private Thread thread = null;
    //马里奥的移动速度
    private int xSpeed,ySpeed;
    //定义一个索引
    private int index;
    //表示马里奥上升的时间
    private int upTime = 0;
    //判断马里奥是否走到城堡门口
    private boolean isFinish = false;

    //判断马里奥是否死亡
    private boolean isDeath = false;
    //添加分数变量(规定消除一个砖块加一分，消除一个敌人加十分)
    private int score = 0;

    public Mario(){

    }

    public Mario(int x,int y){
        this.x = x;
        this.y = y;
        this.imageMario = StaticValue.standRight;
        this.status = "stopRight";
        this.thread = new Thread(this);
        this.thread.start();
    }

    //马里奥死亡
    public void death(){
        isDeath = true;
    }

    //马里奥向左移动
    public void moveLeft(){
        //改变马里奥的移动速度
        xSpeed = -10;

        //判断马里奥是否碰到旗子
        if(background.getIsReach()){
            xSpeed = 0;
        }

        //判断马里奥是否处于空中
        if(status.indexOf("jump") != -1){
            status = "jumpLeft";
        }else{
            status = "moveLeft";
        }
    }

    //马里奥向右移动
    public void moveRight(){
        //改变马里奥的移动速度
        xSpeed = 10;

        //判断马里奥是否碰到旗子
        if(background.getIsReach()){
            xSpeed = 0;
        }

        //判断马里奥是否处于空中
        if(status.indexOf("jump") != -1){
            status = "jumpRight";
        }else{
            status = "moveRight";
        }
    }

    //马里奥向左停止
    public void stopLeft(){
        //改变马里奥的移动速度
        xSpeed = 0;
        //判断马里奥是否处于空中
        if(status.indexOf("jump") != -1){
            status = "jumpLeft";
        }else{
            status = "stopLeft";
        }


    }

    //马里奥向右停止
    public void stopRight(){
        //改变马里奥的移动速度
        xSpeed = 0;
        //判断马里奥是否处于空中
        if(status.indexOf("jump") != -1){
            status = "jumpRight";
        }else{
            status = "stopRight";
        }
    }

    //马里奥跳跃
    public void jump(){

        if(status.indexOf("jump") == -1){
            if(status.indexOf("left") != -1){
                status = "jumpLeft";
            }else{
                status = "jumpRight";
            }
            ySpeed = -10;
            upTime = 7;
        }

        //判断马里奥是否碰到旗子
        if(background.getIsReach()){
            ySpeed = 0;
        }

    }

    //马里奥下落
    public void fall(){
        if(status.indexOf("left") != -1){
            status = "jumpLeft";
        }else{
            status = "jumpRight";
        }
        ySpeed = 10;
    }

    @Override
    public void run() {
        while(true) {
            //判断是否在障碍物上
            boolean onObstacle = false;
            //判断是否可以往右走
            boolean canRight = true;
            //判断是否可以往左走
            boolean canLeft = true;
            //判断马里奥是否到达女流的位置
            if (background.getFlag() && this.getX() >= 500) {
                this.background.setIsReach(true);

                //判断旗子是否下落完成
                if(this.background.getIsReachGround()){
                    status = "moveRight";
                    if(x < 700){
                        x += 5;
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
                    }else{
                        this.isFinish = true;
                    }
                }else{
                    if(y < 360){
//                        System.out.println(1);

                        xSpeed = 0;

                        this.y += 5;
//                        System.out.println(y);
                        status = "jumpRight";

//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
                    }

                    if(y > 360){
                        this.y = 360;
                        status = "stopRight";
                    }

                }

            } else {
//            System.out.println(status);
                //遍历场景内所有的障碍物
                for (int i = 0; i < background.getObstacleList().size(); i++) {
                    Obstacle obstacle = background.getObstacleList().get(i);
                    //判断马里奥是否在障碍物上
                    if (this.getY() + this.imageMario.getHeight() == obstacle.getY() && (this.getX() + this.imageMario.getWidth() >= obstacle.getX() && this.getX() <= obstacle.getX() + obstacle.getObstacleImage().getWidth())) {
                        onObstacle = true;
                    }
                    //判断是否跳起来顶到砖块
                    if (this.getY() == obstacle.getY() + obstacle.getObstacleImage().getHeight() && (this.getX() + this.getImageMario().getWidth() >= obstacle.getX() && this.getX() <= obstacle.getX() + obstacle.getObstacleImage().getWidth())) {
                        if (obstacle.getType() == 0) {
                            background.getObstacleList().remove(obstacle);
                            this.updateScore(true);
                        }
                        upTime = 0;
                    }

                    //判断是否可以往右走
                    if (this.getX() + this.getImageMario().getWidth() == obstacle.getX() && (this.getY() + this.getImageMario().getHeight() > obstacle.getY() && this.getY() < obstacle.getY() + obstacle.getObstacleImage().getHeight())) {
                        canRight = false;
                    }
                    boolean test1 = this.getX() + this.getImageMario().getWidth() == obstacle.getX();

                    //判断是否可以往左走
                    if (this.getX() == obstacle.getX() + obstacle.getObstacleImage().getWidth() && (this.getY() + this.getImageMario().getHeight() > obstacle.getY() && this.getY() < obstacle.getY() + obstacle.getObstacleImage().getHeight())) {
                        canLeft = false;
                    }

                }

                //判断马里奥是否碰到敌人死亡或者踩死蘑菇敌人
                for(int i = 0;i < this.background.getEnemyList().size();i++){
                    Enemy enemy = background.getEnemyList().get(i);
                    //判断马里奥是否处于敌人正上方
                    if(this.getY() + this.imageMario.getHeight() == enemy.getY() && (this.getX() + this.imageMario.getWidth() >= enemy.getX() && this.getX() <= enemy.getX() + enemy.getEnemyImage().getWidth())){
                        if(enemy.getType() == 1){
                            //敌人死亡
//                            System.out.println(1);
                            enemy.death();
                            upTime = 3;
                            ySpeed = -10;
                            this.updateScore(false);
                        } else if (enemy.getType() == 2) {
                            //马里奥死亡
                            this.death();
                        }
                    }
//                    int test = this.getY() + this.getImageMario().getHeight();
//                    System.out.println(enemy.getY() +  "    " +  test);

                    if((this.getX() + this.getImageMario().getWidth() == enemy.getX() && (this.getY() + this.getImageMario().getHeight() > enemy.getY() && this.getY() < enemy.getY() + enemy.getEnemyImage().getHeight())) || (this.getX() == enemy.getX() + enemy.getEnemyImage().getWidth() && (this.getY() + this.getImageMario().getHeight() > enemy.getY() && this.getY() < enemy.getY() + enemy.getEnemyImage().getHeight()))){
                        //马里奥死亡
                        this.death();
                    }
                }


                //马里奥跳跃
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("Left") != -1) {
                        if (xSpeed != 0) {
                            status = "moveLeft";
                        } else {
                            status = "stopLeft";
                        }
                    } else {
                        if (xSpeed != 0) {
                            status = "moveRight";
                        } else {
                            status = "stopRight";
                        }
                    }
                } else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        fall();
                    }
                    y += ySpeed;
                }
            }


                if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)) {
                    x += xSpeed;
                    //判断马里奥是否运动到了屏幕的最左边
                    if (x < 0) {
                        x = 0;
                    }
                }
                //判断当前是否是移动状态
                if (status.contains("move")) {
                    if (index == 0) {
                        index = 1;
                    } else {
                        index = 0;
                    }
                }
                //判断马里奥是否向左移动
//            System.out.println(status);
                if (status.equals("moveLeft")) {
                    this.imageMario = StaticValue.runLeft.get(index);
                }
                //判断马里奥是否向右移动
                if (status.equals("moveRight")) {
                    this.imageMario = StaticValue.runRight.get(index);
                }

                //判断马里奥是否向左停止
                if (status.equals("stopLeft")) {
                    this.imageMario = StaticValue.standLeft;
                }
                //判断马里奥是否向右停止
                if (status.equals("stopRight")) {
                    this.imageMario = StaticValue.standRight;
                }

                //判断马里奥是否向左跳跃
                if (status.equals("jumpLeft")) {
                    this.imageMario = StaticValue.jumpLeft;
                }

                //判断马里奥是否向右跳跃
                if (status.equals("jumpRight")) {
                    this.imageMario = StaticValue.jumpRight;
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    public void updateScore(boolean isBlock){
        if(isBlock){
            this.score += 1;
        }else{
            this.score += 10;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImageMario() {
        return imageMario;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImageMario(BufferedImage imageMario) {
        this.imageMario = imageMario;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public boolean getIsFinish(){
        return this.isFinish;
    }

    public int getScore() {
        return score;
    }

    public void setIsDeath(boolean isDeath){
        this.isDeath = isDeath;
    }

    public void setIsFinish(boolean isFinish){
        this.isFinish = isFinish;
    }

}
