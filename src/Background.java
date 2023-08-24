import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Background {

    //当前场景要显示的图片
    private BufferedImage backgroundImage = null;

    //记录当前是第几个场景
    private int sort;

    //判断是否是最后一个场景
    private boolean flag;
    //用于存放障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();

    //用于存放敌人
    private List<Enemy> enemyList = new ArrayList<>();

    //显示旗杆
    private BufferedImage doNotDecide1 = null;

    //显示城堡
    private BufferedImage doNotDecide2 = null;
    //判断马里奥是否到达女流的位置
    private boolean isReach = false;
    //判断女流是否落地
    private boolean isReachGround = false;


    public Background(){

    }

    public Background(int sort, boolean flag){
        this.sort = sort;
        this.flag = flag;

        if(this.flag){
            this.backgroundImage = StaticValue.background2;
        }else{
            if(sort == 0){
                this.backgroundImage = StaticValue.userInterface;
            }else {
                this.backgroundImage = StaticValue.background1;
            }
        }

        //判断是否为第一关
        if(sort == 1){
            //绘制第一关场景的地面，上地面type = 1,下地面type= 2;
            for(int i = 0; i < 27; i++){
                obstacleList.add(new Obstacle(i*30,420,0,this));
            }

            for(int i = 0; i<=120 ;i+=30){
                for(int j = 0;j <27;j++){
                    obstacleList.add(new Obstacle(j*30, 570 - i, 0, this));
                }
            }

            //绘制砖块
            for(int i = 120; i <= 150 ;i = i + 30){
                obstacleList.add(new Obstacle(i, 300, 5, this));
            }
            for(int i = 300;i <= 570 ;i= i + 30){
                if(i == 360 || i == 390 || i == 480 || i == 510 || i == 540){
                    obstacleList.add(new Obstacle(i, 300, 0, this));
                }else{
                    obstacleList.add(new Obstacle(i, 300, 0,this));
                }
            }
            for(int i = 420; i <= 450;i = i + 30){
                obstacleList.add(new Obstacle(i,240,0,this));
            }

            //绘制水管
            for(int i = 360;i<= 600;i = i + 25){
                if(i == 360){
                    obstacleList.add(new Obstacle(620, i,1 ,this));
                    obstacleList.add(new Obstacle(645,i,2,this));
                }else{
                    obstacleList.add(new Obstacle(620,i, 3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }
            }

            //绘制第一关的蘑菇敌人
            enemyList.add(new Enemy(580,390,1,true,this));
            //绘制第一关食人花敌人
            enemyList.add(new Enemy(635,420,2,true,this,328,428));

        }

        if(sort == 2){
            //绘制第二关场景的地面，上地面type = 1,下地面type= 2;
            for(int i = 0; i < 27; i++){
                obstacleList.add(new Obstacle(i*30,420,0,this));
            }

            for(int i = 0; i<=120 ;i+=30){
                for(int j = 0;j <27;j++){
                    obstacleList.add(new Obstacle(j*30, 570 - i, 0, this));
                }
            }

            //绘制第一根水管
            for(int i = 360;i<= 600;i = i + 25){
                if(i == 360){
                    obstacleList.add(new Obstacle(60, i,1 ,this));
                    obstacleList.add(new Obstacle(85,i,2,this));
                }else{
                    obstacleList.add(new Obstacle(60,i, 3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                }
            }

            //绘制第二根水管
            for(int i = 330;i<= 600;i = i + 25){
                if(i == 330){
                    obstacleList.add(new Obstacle(620, i,1 ,this));
                    obstacleList.add(new Obstacle(645,i,2,this));
                }else{
                    obstacleList.add(new Obstacle(620,i, 3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }
            }

            //绘制砖块
            obstacleList.add(new Obstacle(300, 330, 0,this));
            for(int i = 270;i <= 330;i+=30){
                if(i == 270 || i == 330){
                    obstacleList.add(new Obstacle(i, 360,0,this));
                }else{
                    obstacleList.add(new Obstacle(i, 360,5,this));
                }
            }

            for(int i = 240; i <= 360; i +=30){
                if(i == 240 || i == 360){
                    obstacleList.add(new Obstacle(i, 390, 0, this));
                }else{
                    obstacleList.add(new Obstacle(i,390,5,this));
                }
            }
            obstacleList.add(new Obstacle(240,300,0,this));
            for(int i = 360; i <=540; i +=60){
                obstacleList.add(new Obstacle(i, 270,5,this));
            }

            //绘制第二关第一个食人花敌人
            enemyList.add(new Enemy(75,420,2,true,this,328,418));
            //绘制第二关第二个食人花敌人
            enemyList.add(new Enemy(635,420,2,true,this,298,388));
            //绘制第二关第一个蘑菇敌人
            enemyList.add(new Enemy(200,390,1,true,this));
            //绘制第二关第二个蘑菇敌人
            enemyList.add(new Enemy(500,390,1,true,this));

        }

        //判断是否是第三关
        if(sort == 3){
            //绘制第三关场景的地面，上地面type = 1,下地面type= 2;
            for(int i = 0; i < 27; i++){
                obstacleList.add(new Obstacle(i*30,420,0,this));
            }

            for(int i = 0; i<=120 ;i+=30){
                for(int j = 0;j <27;j++){
                    obstacleList.add(new Obstacle(j*30, 570 - i, 0, this));
                }
            }

            //绘制砖块
            int temp = 290;
            for(int i = 390;i >= 270;i -=30){
                for(int j = temp ; j <= 410 ;j+=30){
                    obstacleList.add(new Obstacle(j,i ,5,this));
                }
                temp += 30;
            }

            temp = 60;
            for(int i = 390;i >= 360;i-=30){
                for(int j = temp;j <=90;j+=30){
                    obstacleList.add(new Obstacle(j,i, 5,this));
                }
                temp+=30;
            }

            //给旗杆赋值
            obstacleList.add(new Obstacle(500, 66, 6, this));

            //绘制城堡
            doNotDecide2 = StaticValue.resize(StaticValue.castle,100,100);

            //绘制第三关第一个蘑菇敌人
            enemyList.add(new Enemy(150,390,1,true,this));
        }


    }

    public BufferedImage getBackgroundImage() {
        return this.backgroundImage;
    }

    public int getSort() {
        return this.sort;
    }

    public boolean getFlag(){
        return this.flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getDoNotDecide1() {
        return doNotDecide1;
    }

    public BufferedImage getDoNotDecide2() {
        return doNotDecide2;
    }
    public boolean getIsReach(){
        return this.isReach;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setIsReach(boolean isReach){
        this.isReach = isReach;
    }

    public boolean getIsReachGround(){
        return this.isReachGround;
    }

    public void setIsReachGround(boolean isReachGround){
        this.isReachGround = isReachGround;
    }

}
