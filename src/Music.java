import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {



    //储存当前位置
    private Long currentFrame;

    private Clip clip;

    //Clip的状态
    private String status;
    AudioInputStream audioInputStream;
    private String filePath;

    public Music(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filePath = filePath;
        //创建播放器
        this.audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        //创建clip reference
        this.clip = AudioSystem.getClip();
        //打开播放器
        this.clip.open(this.audioInputStream);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play(){
        clip.start();

        status = "play";
    }

    public void pause(){
        if(status.equals("paused")){
            System.out.println("audio is already paused");
            return;
        }
        this.setCurrentFrame(this.getClip().getMicrosecondPosition());
        this.getClip().stop();
        status = "paused";
    }

    //恢复音乐播放
    public void resumeAudio() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(status.equals("play")){
            System.out.println("Audio is already being played");
            return;
        }
        this.getClip().close();
        resetAudioStream();
        this.getClip().setMicrosecondPosition(this.currentFrame);
        this.play();
    }

    //重新播放
    public void restart() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    //停止音乐
    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException{
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    //到指定位置
    public void jump(long c) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    //重置音频流
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public Long getCurrentFrame() {
        return currentFrame;
    }

    public Clip getClip() {
        return clip;
    }

    public String getStatus() {
        return status;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setCurrentFrame(Long currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAudioInputStream(AudioInputStream audioInputStream) {
        this.audioInputStream = audioInputStream;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
