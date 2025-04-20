package tool;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

import game.KaiShi;
import model.*;
import model.fight.FSMMachine;
import model.fight.fsmstates.Die;
import model.fight.fsmstates.FSMState;
import model.fight.fsmstates.Live1;
import model.fight.fsmstates.Stop;
import model.fight.skill.MonsterSkill;

/**
 * 
 * @author LSY
 * @date Dec 7, 2016 工具类
 */
public class Utils {
	private static Image ima1 = new ImageIcon("config/images/map/草.png").getImage();
	private static Image ima2 = new ImageIcon("config/images/map/瓷砖.png").getImage();
	private static Image ima3 = new ImageIcon("config/images/map/银杏树桩.png").getImage();

	private static Image ima101 = new ImageIcon("config/images/map/树.png").getImage();
	private static Image ima102 = new ImageIcon("config/images/map/银杏树.png").getImage();
	private static ArrayList<Npcs> npcsList = new ArrayList<Npcs>();

	public static Map getMap(String mapName, Person person) {
		if ("新手村".equals(mapName)) {
			Npc xscLsy = getNpc("廖老头", person.getName());
			Monster shadan = getMonster(1,"傻蛋",3,10,person);
			npcsList.add(xscLsy);
			npcsList.add(shadan);
			int[][] npcData = new int[20][40];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 40; j++) {
					for (Npcs npcs : npcsList) {
						if (npcs.getI() == i && npcs.getJ() == j) {
							npcData[i][j] = npcsList.indexOf(npcs)+1;
						} 
					}
				}
			}
			// 1-99为本来可以通过的素材 101-199 为本来就不能通过的素材
			// 201---以后表示在原来的素材上添加了元素而不能通过（怪物，道具，npc等）
			int[][] mapData = {
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2,
							2, 2, 2, 2, 2, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1,
							2, 1, 1, 2, 1, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2,
							2, 2, 2, 2, 2, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 2, 102, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 2, 101 },
					{ 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2,
							2, 2, 2, 2, 2, 2, 101 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 101, 101, 101, 101, 101,
							101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101 } };
			Map map = new Map(mapData,"xsc");
			map.setNpcData(npcData);
			return map;
		}
		return null;
	}

	private static Monster getMonster(int id,String name ,int i, int j,Person person) {
		ArrayList<FSMState> states = new ArrayList<FSMState>();
		Monster mon = new Monster(id, name, i, j,person);
		states.add(new Live1( mon));
		states.add(new Die( mon));
		states.add(new Stop());
		mon.setFsmmachine(new FSMMachine(states, 0));
		Image ima = new ImageIcon("config/images/monster/mon_"+id+".png").getImage();
		mon.setMonsterImage(ima);
		mon.getMonSkills().add(new MonsterSkill(1, mon, null, "普咬",1000,60,50));
		mon.setHp(50);
		mon.setZhp(50);
		mon.setAd(10);
		return mon;
	}

	private static Npc getNpc(String npcName, String personName) {
		if ("廖老头".equals(npcName)) {
			ArrayList<String> beforetalk = new ArrayList<String>();
			ArrayList<String> intalk = new ArrayList<String>();
			ArrayList<String> aftertalk = new ArrayList<String>();
			ArrayList<String> afmissionTalk = new ArrayList<String>();
			beforetalk.add("小姑娘，你好呀！我是捡破烂的廖老头，曾经我也是一名厉害的程序猿，可惜不务正业，上班时间写游戏，如今落得这幅光景，唉！！");
			beforetalk.add("真可怜啊！（内心想：活该！让你不好好工作）。我的名字叫" + personName + ",有什么可以帮助你的吗？");
			beforetalk.add(personName + ",真是个好姑娘啊！老汉我刚好最近吃肉卡着牙缝了，你能去把西南方向那棵千年银杏树砍回来给我吗？老汉我做根牙签用。");
			beforetalk.add("好的！没问题。（内心疯狂吐槽：你大爷啊！捡破烂还吃肉啊！千年，千年银杏树啊！做你妹的牙签啊！待会不给我好东西打死你这糟老头啊！）");
			intalk.add(personName + ",银杏树呢！没牙签的话，老汉我这牙可疼的紧啊！");
			intalk.add("我…………");
			aftertalk.add(personName + ",老汉我还以为你会遭遇不测呢，银杏树给我吧。");
			aftertalk.add("嘿嘿！给吧。（内心：你才不测呢，你全家都不测）");
			aftertalk.add("嗯嗯！好好好！");
			aftertalk.add("嘿嘿！老爷爷，我帮了你这么大忙，有没有什么东西给我呢？");
			aftertalk.add("嗯嗯！老汉我上次捡破烂时捡了一个包，里面还有点破烂东西,不嫌弃的话你就拿去用吧");
			aftertalk.add("谢谢大爷！");
			afmissionTalk.add("小姑娘，老汉我准备睡觉了，一边玩去。");
			afmissionTalk.add("我…………");
			MissionMoveTo MissionXscLsy1 = new MissionMoveTo(1,null, new Point(1, 18), beforetalk, intalk, aftertalk,
					null);
			Npc xscLsy = new Npc("廖老头");
			xscLsy.setNpcImage(new ImageIcon("config/images/npc/xscLsy_1.png").getImage());
			xscLsy.setNpc_i(3);
			xscLsy.setNpc_j(3);
			xscLsy.setMission(MissionXscLsy1);
			xscLsy.setAfmissionTalk(afmissionTalk);
			return xscLsy;
		}
		return null;
	}

	public static Image getMapImage(Map map, int i, int j) {
		int[][] a = map.getMapData();
		switch (a[i][j] % 200) {
		case 1:
			return ima1;
		case 2:
			return ima2;
		case 3:
			return ima3;
		case 101:
			return ima101;
		case 102:
			return ima102;
		}
		return null;
	}

	public static Image getNpcImage(Map map, int i, int j) {
		int[][] a = map.getNpcData();
		int[][] b = map.getMapData();
		if (a[i][j] > 0) {
			b[i][j] = b[i][j]+200;
			return npcsList.get(a[i][j]-1).getImage();
		} else {
			return null;
		}
	}
	public static void killMonster(Map map,Monster mon){
		int[][] a = map.getNpcData();
		int[][] b = map.getMapData();
		int i = mon.getI();
		int j = mon.getJ();
		npcsList.remove(mon);
		a[i][j] = 0;
		b[i][j] = b[i][j] - 200;
	}
	public static Npc getNpc(Map map,int i ){
		if(npcsList.get(i-1).getClass()==Npc.class){
			return (Npc)npcsList.get(i-1);
		}
		return null;
	}

	public static Monster getMonster(Map map, int i) {
		if(npcsList.get(i-1).getClass()==Monster.class){
			return (Monster)npcsList.get(i-1);
		}
		return null;
	}
	
	public static Image getImage(String path){
		return new ImageIcon("config/images/"+path).getImage();
		
	}
}


