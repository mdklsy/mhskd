package tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.Logger;

import com.sun.media.Log;

import model.Monster;
/**
 * 
 * @author L
 * 游戏音效播放器
 */
public class MusicPlayer {
	private static MusicPlayer musicPlayer;
	private Timer timer;
	private Timer timer2;
	private PlayBGM playBgm;
	public Map<String, ArrayList<String>> bgmlist;
	private MusicPlayer() {
		timer = new Timer();
		timer2 = new Timer();
		bgmlist = new HashMap<String, ArrayList<String>>();
		ArrayList<String> bgm = new ArrayList<String>();
		for (int i = 1; i < 12; i++) {
			bgm.add("xsc"+i+".mid");
		}
		bgmlist.put("xsc", bgm);
		bgm = new ArrayList<String>();
		for (int i = 1; i < 6; i++) {
			bgm.add("fight"+i+".au");
		}
		bgmlist.put("fight", bgm);
	}

	public static MusicPlayer getPlayer() {
		if (musicPlayer == null) {
			musicPlayer = new MusicPlayer();
		}
		return musicPlayer;
	}


	public void closeBGM() {
		playBgm.setFlag(false);
		playBgm.getAuline().close();
	}
	

	public void playOther(String path) {
		timer2.schedule(new PlayOtherTask(path), 0);
	}
	
	public void playBGM(String mapName){
		playBgm = new PlayBGM(mapName,bgmlist);
		timer.schedule(playBgm, 0);
	}

	
	
}

class PlayOtherTask extends TimerTask {
	private String path;

	public PlayOtherTask(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
	}

	@Override
	public void run() {
		System.out.println("run");
		SourceDataLine auline = null;
		try {
			File file = new File("config/music/" + path);
			AudioInputStream audio = AudioSystem.getAudioInputStream(file);
			AudioFormat format = audio.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
			auline.start();
			int nBytesRead = 0;
			byte[] abData = new byte[1024];
			nBytesRead = audio.read(abData, 0, abData.length);
			while (nBytesRead > 0) {
				auline.write(abData, 0, nBytesRead);
				nBytesRead = audio.read(abData, 0, abData.length);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			Log.error(e);
		} finally {
			auline.close();
		}
	}

}

class PlayBGM extends TimerTask {
	private static Logger log = Logger.getLogger(PlayBGM.class);
	public Map<String, ArrayList<String>> bgmlist;
	public String mapName;
	private int index;
	private boolean flag;
	private File file;
	private AudioInputStream audio;
	private AudioFormat format;
	private SourceDataLine auline;
	private DataLine.Info info;
	private byte[] abData;

	public PlayBGM(String mapName,Map<String, ArrayList<String>> bgmlist) {
		this.bgmlist = bgmlist;
		this.mapName = mapName;
		abData = new byte[1024];
		try {
			initPlayer();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			log.error(e);
		} 
	}

	private void initPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		index = (int) (Math.random() * bgmlist.get(mapName).size());
		System.out.println(bgmlist.get(mapName).size());
		System.out.println(index);
		flag = true;
		file = new File("config/music/bgm/"+mapName +"/"+ bgmlist.get(mapName).get(index));
		audio = AudioSystem.getAudioInputStream(file);
		format = audio.getFormat();
		info = new DataLine.Info(SourceDataLine.class, format);
		auline = (SourceDataLine) AudioSystem.getLine(info);
		auline.open(format);
		auline.start(); 
	}

	@Override
	public void run() {
		int nBytesRead = 0;
		while (flag) {
				try {
					nBytesRead = audio.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0) {
					auline.write(abData, 0, nBytesRead);
				} else {
					 log.info("当前背景音乐结束，更换下一首");
					try {
						initPlayer();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					} 
				}
			
		}
	}
	
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public SourceDataLine getAuline() {
		return auline;
	}
}
