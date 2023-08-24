import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {

    //背景
    protected static BufferedImage background1 = null;
    protected static BufferedImage background2 = null;
    //马里奥向左跳跃
    protected static BufferedImage jumpLeft = null;
    //马里奥向右跳跃
    protected static BufferedImage jumpRight = null;
    //马里奥向左站立
    protected static BufferedImage standLeft = null;
    //马里奥向右站立
    protected static BufferedImage standRight = null;
    //城堡
    protected static BufferedImage castle = null;
    //旗杆
    protected static BufferedImage flagpole = null;
    //障碍物
    protected static List<BufferedImage> obstacle = new ArrayList<>();
    //马里奥向左跑
    protected static List<BufferedImage> runLeft = new ArrayList<>();
    //马里奥向右跑
    protected static List<BufferedImage> runRight = new ArrayList<>();
    //蘑菇敌人
    protected static List<BufferedImage> mushroom = new ArrayList<>();
    //食人花敌人
    protected static List<BufferedImage> flower = new ArrayList<>();
    //路径的前缀，方便后续调用
    protected static String path = System.getProperty("user.dir") + "/src/images/";
    public static BufferedImage userInterface = null;
    //初始化方法
    public static void init(){

        try {
            //加载用户界面
            userInterface = resize(ImageIO.read(new File(path + "start.jpg")),800,600);
            //加载背景图片
            background1 = ImageIO.read(new File(path + "firststage.jpg"));
            background2 = ImageIO.read(new File(path + "firststageend.jpg"));
            //马里奥向左站立
            standLeft = ImageIO.read(new File(path + "6.png"));
            standRight = ImageIO.read(new File(path + "1.png"));
            //加载城堡
            castle = ImageIO.read(new File(path + "image2.png"));
            //加载旗杆
            flagpole = ImageIO.read(new File(path + "image1.png"));
            //加载马里奥向左跳跃
            jumpLeft = ImageIO.read(new File(path + "7.png"));
            //加载马里奥向右跳跃
            jumpRight = ImageIO.read(new File(path + "2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载马里奥向左跑
        for(int i = 0; i < 2; i++){
            if(i == 0){
                try {
                    runLeft.add(ImageIO.read(new File(path + "8.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    runLeft.add(ImageIO.read(new File(path + "10.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //加载马里奥向右跑
        for(int i = 0; i < 2; i++){
            if(i == 0){
                try {
                    runRight.add(ImageIO.read(new File(path + "2.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    runRight.add(ImageIO.read(new File(path + "5.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //加载障碍物
        try {
            //普通砖块
            BufferedImage originalBlock = ImageIO.read(new File(path + "ob1.png"));
            BufferedImage updatedBlock = resize(originalBlock,30,30);
            obstacle.add(updatedBlock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载管道
        for(int i = 6; i < 10; i++){
            try {
                BufferedImage originalTube = ImageIO.read(new File(path + "ob" + i + ".png"));
                BufferedImage updatedTube = resize(originalTube,30,30);
                obstacle.add(updatedTube);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //加载不可破坏的砖块
        try {
            BufferedImage originalGoodBlock = ImageIO.read(new File(path + "ob2.png"));
            BufferedImage updatedGoodBlock = resize(originalGoodBlock,30,30);
            obstacle.add(updatedGoodBlock);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载女流图像
        try {
            BufferedImage original66Graph = ImageIO.read(new File(path + "image1.png"));
            BufferedImage updated66Graph = resize(original66Graph, 100, 100);
            obstacle.add(updated66Graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载蘑菇敌人
        for(int i = 1; i <= 3; i++){
            try {
                BufferedImage originalMushroomImage = ImageIO.read(new File(path + "triangle"+ i + ".png"));
                BufferedImage updatedMushroomImage = resize(originalMushroomImage,30,30);
                mushroom.add(updatedMushroomImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //加载食人花敌人
        for(int i = 1; i <= 2; i++){
            try {
                BufferedImage originalFlowerImage = ImageIO.read(new File(path + "flower" + i + ".png"));
                BufferedImage updatedFlowerImage = resize(originalFlowerImage,30,30);
                flower.add(updatedFlowerImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
