package game;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import model.Bao;
import model.Equipment;
import model.Items.itemType;
import model.MissionMoveTo;
import model.Monster;
import model.Npc;
import model.Person;
import tool.MusicPlayer;
import tool.Utils;

/**
 * 
 * @author LSY
 * @date Dec 7, 2016 游戏主窗体
 */
public class KaiShi extends JFrame {
	private JLayeredPane layeredPane;
	private Welcome welcome;
	private GameMap gameMap;
	private BeiBao beiBao;
	private Npc npc;
	private TalkLable talkLable;
	private PersonEqpt pereqpt;
	private Thread updataGameMap;
	private Thread updataPerson;
	private KeyListener talkt;
	private KeyListener talke;
	private FightFarme ff;

	public KaiShi() {
		layeredPane = new JLayeredPane();
		setLayeredPane(layeredPane);
		setLayout(null);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setWelcome(layeredPane);
		talkLable = new TalkLable(layeredPane);
		talkLable.setLocation(150, 300);
		setVisible(true);
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Utils.getImage("show/鼠标指针.png"), new Point(4, 4), "手"));
	}

	// 显示欢迎界面(输入用户名)
	private void setWelcome(JLayeredPane jlp) {
		welcome = new Welcome(jlp);
		welcome.setLocation(0, 0);
		layeredPane.repaint();
		welcome.getJbt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = welcome.getjTextField().getText().trim();
				if ("".equals(name)) {
					welcome.getJl2().setText("你丫的，没名字啊！");
					return;
				} else {
					welcome.getPro().setProperty("name", name);
					try {
						welcome.getPro().store(new FileOutputStream("config/message/a.properties"), null);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					layeredPane.remove(layeredPane.getIndexOf(welcome));
					showGame();
				}
			}
		});
	}

	// 显示游戏画面
	protected void showGame() {
		gameMap = new GameMap(layeredPane);
		gameMap.setLocation(0, 0);
		addKeyEvent(gameMap.getPerson());
		layeredPane.repaint();
		updataGameMap = new UpdataGameMap(gameMap);
		updataGameMap.start();
		updataPerson = new UpdataPerson(gameMap);
		updataPerson.start();
		pereqpt = new PersonEqpt(gameMap.getPerson().getPersonEqpt(), layeredPane,gameMap.getPerson());
		this.addKeyListener(new EqptC(pereqpt));
		this.addKeyListener(new FightQ(this));
		this.requestFocus();
		MusicPlayer.getPlayer().playBGM(this.getGameMap().getMap().getMapName());
	}

	// 添加键盘监听事件
	private void addKeyEvent(final Person person) {
		talkt = new TalkT(this);
		this.addKeyListener(talkt);
		KaiShi.this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			// 释放
			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case 87:
					person.setUp(false);
					break;
				case 83:
					person.setDown(false);
					break;
				case 65:
					person.setLeft(false);
					break;
				case 68:
					person.setRight(false);
					break;

				default:
					break;
				}

			}

			// 按下
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case 87:
					person.setUp(true);
					break;
				case 83:
					person.setDown(true);
					break;
				case 65:
					person.setLeft(true);
					break;
				case 68:
					person.setRight(true);
					break;
				default:
					break;
				}
			}
		});

	}

	public Point getNpcIJ(Person person) {
		int i = -1;
		int j = -1;
		switch (person.getFx()) {
		case 1:
			i = person.getupJ();
			j = person.getI();
			break;
		case 2:
			i = person.getdownJ();
			j = person.getI();
			break;
		case 3:
			i = person.getJ();
			j = person.getleftI();
			break;
		case 4:
			i = person.getJ();
			j = person.getrightI();
			break;

		default:
			break;
		}
		return new Point(i, j);

	}

	// 监听键盘的T按键 实现和npc的对话功能
	protected void talkWithNpc(Person person) {
		int npci = getNpcIJ(person).x;
		int npcj = getNpcIJ(person).y;

		// 如果人物的面朝方向有npc
		int[][] a = gameMap.getMap().getNpcData();
		if (npci >= 0 && npci < a.length && npcj >= 0 && npcj < a[0].length) {
			if ((a[npci][npcj] >0)) {
				npc = Utils.getNpc(gameMap.getMap(), a[npci][npcj]);
				if(npc==null){
					return;
				}
				KaiShi.this.removeKeyListener(talkt);
				npc.setNpcImage(new ImageIcon("config/images/npc/xscLsy_" + person.getFx() + ".png").getImage());
				ArrayList<String> talk = null;
				if (npc.getMission() == null) {
					talk = npc.getAfmissionTalk();
				} else {
					boolean isStart = npc.getMission().isStart();
					boolean isEnd = npc.getMission().isEnd();
					if ((!isStart) && (!isEnd)) {
						talk = npc.getMission().getBeforeTalk();
					} else if ((isStart) && (!isEnd)) {
						talk = npc.getMission().getInTalk();
					} else {
						talk = npc.getMission().getAfterTalk();
					}
				}
				talkLable.setText(npc.getName(),talk.get(0));
				talkKeyListener lis = new talkKeyListener(talk, KaiShi.this);
				KaiShi.this.addKeyListener(lis);
				talkLable.setVisible(true);
			}
		}

	}

	public TalkLable getTalkLable() {
		return talkLable;
	}

	public void setTalkLable(TalkLable talkLable) {
		this.talkLable = talkLable;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public Npc getNpc() {
		return npc;
	}

	public KeyListener getTalkt() {
		return talkt;
	}

	public KeyListener getTalke() {
		return talke;
	}

	public void setTalke(KeyListener talke) {
		this.talke = talke;
	}

	public BeiBao getBeiBao() {
		return beiBao;
	}

	public void setBeiBao(BeiBao beiBao) {
		this.beiBao = beiBao;
	}

	public PersonEqpt getPereqpt() {
		return pereqpt;
	}

	public void setFf(FightFarme ff) {
		this.ff = ff;
	}
	
	
}

// 人物移动线程
class UpdataPerson extends Thread {

	private GameMap gameMap;

	public UpdataPerson(GameMap gameMap) {
		this.gameMap = gameMap;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this.gameMap) {
				gameMap.getPerson().move(gameMap.getMap());
				try {
					sleep(10);
					this.gameMap.notify();
					this.gameMap.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

// 刷新画面线程
class UpdataGameMap extends Thread {
	private GameMap gameMap;

	public UpdataGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this.gameMap) {
				gameMap.repaint();
				try {
					sleep(10);
					this.gameMap.notify();
					this.gameMap.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
//空格键对话监听器
class talkKeyListener implements KeyListener {
	ArrayList<String> talk;
	TalkLable lable;
	KaiShi ks;
	int index = 1;

	public talkKeyListener(ArrayList<String> talk, KaiShi ks) {
		this.ks = ks;
		this.talk = talk;
		lable = ks.getTalkLable();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 32) {
			if (index == talk.size()) {
				lable.setVisible(false);
				ks.removeKeyListener(this);
				if (ks.getNpc().getMission() != null) {
					if (ks.getNpc().getMission().getBeforeTalk() != null) {
						ks.getNpc().getMission().setStart(true);
						ks.getNpc().getMission().setBeforeTalk(null);
						ks.addKeyListener(ks.getTalkt());
						ks.setTalke(new TalkE(ks));
						ks.addKeyListener(ks.getTalke());
					} else if (ks.getNpc().getMission().getInTalk() == null) {
						Person  person = ks.getGameMap().getPerson();
						Bao bao = new Bao(3, 3);
						person.setBao(bao);
						ks.getPereqpt().setBao(bao);
						ks.setBeiBao(new BeiBao(ks.getPereqpt(),person, ks.getLayeredPane()));                                                          //攻击 物防 法术  法防 体力 回避
						ks.getBeiBao().addItem(new Equipment(1, "倚天", new ImageIcon("config/images/items/宝剑.jpg").getImage(),998,itemType.weapon,new int[]{100,0,0,0,1,0}), 1);
						ks.getBeiBao().addItem(new Equipment(2, "屠龙", new ImageIcon("config/images/items/刀.png").getImage(),222,itemType.weapon,new int[]{0,0,100,0,2,0}), 2);
						ks.getBeiBao().addItem(new Equipment(3, "帽子", new ImageIcon("config/images/items/帽子.png").getImage(),333,itemType.helmet,new int[]{0,0,0,30,3,3}), 1);
						ks.getBeiBao().addItem(new Equipment(4, "衣服", new ImageIcon("config/images/items/上衣.png").getImage(),444,itemType.coat,new int[]{0,40,0,0,0,4}), 1);
						ks.getBeiBao().addItem(new Equipment(5, "裤子", new ImageIcon("config/images/items/裤子.png").getImage(),555,itemType.pants,new int[]{0,0,50,0,0,5}), 1);
						ks.getBeiBao().addItem(new Equipment(6, "鞋", new ImageIcon("config/images/items/鞋.png").getImage(),666,itemType.shoes,new int[]{0,0,0,0,0,6}), 1);
						ks.addKeyListener(new BeiBaoI(ks));
						ks.getNpc().setMission(null);
						ks.addKeyListener(ks.getTalkt());
						ks.setTalke(new TalkE(ks));
					} else {

					}

				}
				ks.addKeyListener(ks.getTalkt());
				return;
			}
			String s1 =null;
			if(index%2==0){
				s1 = ks.getNpc().getName();
			}else{
				s1 = ks.getGameMap().getPerson().getName();
			}
			lable.setText(s1,talk.get(index));
			index++;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
//T键与NPC对话监听器
class TalkT implements KeyListener {
	Person person;
	KaiShi ks;

	public TalkT(KaiShi ks) {
		this.ks = ks;
		this.person = ks.getGameMap().getPerson();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_T)
			ks.talkWithNpc(person);
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
//E键动作监听器
class TalkE implements KeyListener {
	Person person;
	KaiShi ks;
	MissionMoveTo missi;

	public TalkE(KaiShi ks) {
		this.ks = ks;
		this.person = ks.getGameMap().getPerson();
		missi = ks.getNpc().getMission();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_E) {
			int i = ks.getNpcIJ(person).x;
			int j = ks.getNpcIJ(person).y;
			int[][] a = ks.getGameMap().getMap().getMapData();
			if (i == 17 && j == 2 && a[17][2] == 102) {
				a[17][2] = 3;
				missi.setEnd(true);
				missi.setInTalk(null);
				ks.removeKeyListener(this);
			}
		}

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
//V键打开背包的监听器
class BeiBaoI implements KeyListener{
	KaiShi ks;
	
	public BeiBaoI(KaiShi ks) {
		this.ks = ks;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==86){
			BeiBao bb = ks.getBeiBao();
			if(bb.isVisible()){
				bb.setVisible(false);
			}else{
				bb.setVisible(true);
			}
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

//C键打开人物装备面板
class EqptC implements KeyListener{
	PersonEqpt eqpt;
	
	public EqptC(PersonEqpt eqpt) {
		this.eqpt = eqpt;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==67){
			if(eqpt.isVisible()){
				eqpt.setVisible(false);
			}else{
				eqpt.setVisible(true);
			}
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

//Q键战斗
class FightQ implements KeyListener{
	private KaiShi ks;
	private Person person;
	
	public FightQ(KaiShi ks) {
		this.ks = ks;
		person = ks.getGameMap().getPerson();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==81){
			int npci = ks.getNpcIJ(person).x;
			int npcj = ks.getNpcIJ(person).y;

			// 如果人物的面朝方向有怪
			int[][] a = ks.getGameMap().getMap().getNpcData();
			if (npci >= 0 && npci < a.length && npcj >= 0 && npcj < a[0].length) {
				if ((a[npci][npcj] >0)) {
					Monster mon= Utils.getMonster(ks.getGameMap().getMap(), a[npci][npcj]);
					if(mon==null){
						return;
					}
					ks.dispose();
					MusicPlayer.getPlayer().closeBGM();
					MusicPlayer.getPlayer().playBGM("fight");
					try {
						Person personn = person.clone();
						Monster monn = mon.clone();
						monn.setPerson(personn);
						ks.setFf(new FightFarme(ks, personn,person, monn,mon));
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
	
}


