import java.awt.image.BufferedImage;

public class Obstacle implements Runnable {
    //用于表示坐标
    private int x;
    private int y;
    //用于记录障碍物类型
    private int type;
    //用于显示图像
    private BufferedImage obstacleImage = null;
    //定义当前的场景对象
    private Background background = null;
    //定义一个线程对象
    private Thread thread = new Thread(this);

    public Obstacle(int x, int y, int type, Background background){
        this.x = x;
        this.y = y;
        this.type = type;
        this.background = background;
        this.obstacleImage = StaticValue.obstacle.get(type);
        //如果是旗子的话启动线程
        if(type == 6){
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Background getBackground() {
        return background;
    }

    public BufferedImage getObstacleImage() {
        return obstacleImage;
    }

    public int getType() {
        return type;
    }

    @Override
    public void run() {
        while (true){
            if(this.background.getIsReach()){
                if(this.getY() < 320){
                    this.y +=5;
                }else{
                    this.background.setIsReachGround(true);
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
