import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
    //储存当前坐标
    private int x,y;
    //储存敌人类型
    private int type;
    //判断敌人运动的方向
    private boolean isLeftUp = true;
    //用于显示敌人的当前图像
    private BufferedImage enemyImage;
    //定义一个背景对象
    private Background background;
    //敌人运动的极限范围
    private int max = 0;
    private int min = 0;
    //定义一个线程对象
    private Thread thread = new Thread(this);
    //定义当前的图片状态
    private int imageStatus = 0;


    //蘑菇敌人的构造函数
    public Enemy(int x, int y, int type, boolean faceTo,Background background){
        this.x = x;
        this.y = y;
        this.type = type;
        this.isLeftUp = faceTo;
        this.background = background;
        this.enemyImage = StaticValue.mushroom.get(0);
        this.thread.start();
    }

    //食人花敌人的构造函数
    public Enemy(int x, int y, int type, boolean faceTo, Background background, int max, int min){
        this.x = x;
        this.y = y;
        this.type = type;
        this.isLeftUp = faceTo;
        this.background = background;
        this.max = max;
        this.min = min;
        this.enemyImage = StaticValue.flower.get(0);
        this.thread.start();
    }


    //蘑菇敌人的死亡方法
    public void death(){
        this.enemyImage = StaticValue.mushroom.get(2);
        this.background.getEnemyList().remove(this);
    }




    @Override
    public void run() {
        while (true){
            //判断是否是蘑菇敌人
            if(type == 1){

                if(isLeftUp){
                    this.x -= 2;
                }else {
                    this.x += 2;
                }

                if(imageStatus == 1){
                    imageStatus = 0;
                }else{
                    imageStatus = 1;
                }

                this.enemyImage = StaticValue.mushroom.get(imageStatus);


            }

            //定义两个boolean变量(判断蘑菇敌人是否可以向左或向右移动)
            boolean canLeft = true;
            boolean canRight = true;

            for(int i = 0;i < this.background.getObstacleList().size();i++){
                Obstacle obstacle = this.background.getObstacleList().get(i);


                //判断是否可以向右走
                if(this.getX() + this.getEnemyImage().getWidth() == obstacle.getX() && (this.getY() + this.getEnemyImage().getHeight() > obstacle.getY() && this.getY() < obstacle.getY() + obstacle.getObstacleImage().getHeight())){
                    canRight = false;
                }


                //判断是否可以向左走
                if(this.getX() == obstacle.getX() + obstacle.getObstacleImage().getWidth() && (this.getY() + this.getEnemyImage().getHeight() > obstacle.getY() && this.getY() < obstacle.getY() + obstacle.getObstacleImage().getHeight())){
                    canLeft = false;
                }

            }
//            System.out.println(x);
            if((this.isLeftUp && !canLeft) || this.x <= 0){
                isLeftUp = false;
            }

            if((!this.isLeftUp && !canRight) || this.x >= 800){
                isLeftUp = true;
            }



            //判断是否是食人花敌人
            if(this.type == 2){
                if(isLeftUp){
                    this.y -= 2;
                }else{
                    this.y += 2;
                }

                if(imageStatus == 1){
                    imageStatus = 0;
                }else{
                    imageStatus = 1;
                }

                //判断食人花敌人是否移动到了极限位置
                if(isLeftUp && this.y == max){
                    isLeftUp = false;
                }

                if(!isLeftUp && this.y == min){
                    isLeftUp = true;
                }

                this.enemyImage = StaticValue.flower.get(imageStatus);

            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public boolean isLeftUp() {
        return isLeftUp;
    }

    public BufferedImage getEnemyImage() {
        return enemyImage;
    }

    public Background getBackground() {
        return background;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public Thread getThread() {
        return thread;
    }

    public int getImageStatus() {
        return imageStatus;
    }


}
