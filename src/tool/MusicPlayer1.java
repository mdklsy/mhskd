package tool;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer1 {
	public static int flag = 0;
		
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		File file;  
        AudioInputStream audio;  
        AudioFormat format;  
        SourceDataLine auline = null;  
        DataLine.Info info;  
        Timer timer = new Timer();
        try {  
            System.out.println("Start"); 
            System.out.print("\r\n".getBytes().length);
            file = new File("config/music/attack/p_att3.au");  
            audio = AudioSystem.getAudioInputStream(file);  
            format = audio.getFormat();  
            info = new DataLine.Info(SourceDataLine.class, format);  
            auline = (SourceDataLine) AudioSystem.getLine(info);  
            auline.open(format);  
            auline.start();  
            int nBytesRead = 0;  
            byte[] abData = new byte[1024];  
            
            while (true) {  
            	if(flag==0){
            		System.out.println("flag=0 播放中");
            		nBytesRead = audio.read(abData, 0, abData.length);  
            		if (nBytesRead >= 0) {  
            			auline.write(abData, 0, nBytesRead);  
            		}  
            	}else if(flag==1){
            		System.out.println("flag=1  暂停中");
            	}else if(flag ==3){
            		System.out.println("已关闭");
            	}
            } 
        } catch (IOException e) {  
            // System.out.println(e.getMessage());  
            e.printStackTrace();  
        } catch (UnsupportedAudioFileException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (LineUnavailableException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } finally {  
            auline.close();  
        }  
        
        System.out.println("stop");
	}
	
	
}

class Ss extends TimerTask{
	SourceDataLine auline;
	int i ;
	
	public Ss(SourceDataLine auline) {
		this.auline = auline;
		i  =  0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		if(i==1){
			auline.stop();
			MusicPlayer1.flag = 1;
		}else if(i==2){
			auline.start();
			MusicPlayer1.flag = 0;
		}else if(i==3){
			auline.close();
			MusicPlayer1.flag = 3;
		}
		i++;
	}
	
}
