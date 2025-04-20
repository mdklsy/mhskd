package model;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.log4j.Logger;

import model.fight.AttackREG;
import model.fight.DefenceREG;
import model.fight.FSMMachine;
import model.fight.MyREC;
import model.fight.fsmstates.FSMState;
import model.fight.skill.MonsterSkill;

/**
 * 
 * @author L 怪物模型类
 */

public class Monster implements Npcs,Cloneable{
	
	private static Logger log = Logger.getLogger(Monster.class); 
	
	private Person person;
	private int id;
	private String name;
	private Image monsterImage;
	private int monster_i;// 怪物在地图中的位置 i为行j为列
	private int monster_j;
	private int size;
	private double pk_fx;
	private int pk_x;
	private int pk_y;
	private int ad;
	private int dad;
	private int ap;
	private int dap;
	private int zhp;
	private int hp;
	private int dvd;
	private int step_size; // 移动速度
	private boolean isLive; // 是否存活
	private boolean isPK_ing; // 是否处于释放技能状态，如果正在释放技能则不能移动
	private Vector<MonsterSkill> monSkills; // 技能列表
	private FSMMachine fsmmachine; // 状态机
	private Timer timer;

	public Monster(int id, String name, int i, int j, Person person) {
		this.id = id;
		this.name = name;
		this.monster_i = i;
		this.monster_j = j;
		this.size = 50;
		isLive = true;
		isPK_ing = false;
		step_size = 1;
		monSkills = new Vector<MonsterSkill>();
		timer = new Timer();
		this.person = person;
	}

	public void start() {
		fsmmachine.execute();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMonster_i() {
		return monster_i;
	}

	public void setMonster_i(int monster_i) {
		this.monster_i = monster_i;
	}

	public int getMonster_j() {
		return monster_j;
	}

	public void setMonster_j(int monster_j) {
		this.monster_j = monster_j;
	}

	@Override
	public int getI() {
		// TODO Auto-generated method stub
		return getMonster_i();
	}

	@Override
	public int getJ() {
		// TODO Auto-generated method stub
		return getMonster_j();
	}

	public Image getMonsterImage() {
		return monsterImage;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return getMonsterImage();
	}

	public void setMonsterImage(Image monsterImage) {
		this.monsterImage = monsterImage;
	}

	public double getPk_fx() {
		return pk_fx;
	}

	public void setPk_fx(double pk_fx) {
		this.pk_fx = pk_fx;
	}

	public int getPk_x() {
		return pk_x;
	}

	public void setPk_x(int pk_x) {
		this.pk_x = pk_x;
	}

	public int getPk_y() {
		return pk_y;
	}

	public void setPk_y(int pk_y) {
		this.pk_y = pk_y;
	}

	public int getAd() {
		return ad;
	}

	public void setAd(int ad) {
		this.ad = ad;
	}

	public int getAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public int getDad() {
		return dad;
	}

	public void setDad(int dad) {
		this.dad = dad;
	}

	public int getDap() {
		return dap;
	}

	public void setDap(int dap) {
		this.dap = dap;
	}

	public Vector<MonsterSkill> getMonSkills() {
		return monSkills;
	}

	public int getZhp() {
		return zhp;
	}

	public void setZhp(int zhp) {
		this.zhp = zhp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public FSMMachine getFsmmachine() {
		return fsmmachine;
	}

	public void setMonSkills(Vector<MonsterSkill> monSkills) {
		this.monSkills = monSkills;
	}

	public void setFsmmachine(FSMMachine fsmmachine) {
		this.fsmmachine = fsmmachine;
	}

	public Person getPerson() {
		return person;
	}

	public void setPK_ing(boolean isPK_ing) {
		this.isPK_ing = isPK_ing;
	}

	public int getSize() {
		return size;
	}
	
	public static void setLog(Logger log) {
		Monster.log = log;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setDvd(int dvd) {
		this.dvd = dvd;
	}

	public void setStep_size(int step_size) {
		this.step_size = step_size;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	

	public void move() {
		if (!isPK_ing) {
			upDataFx();
			if (isCanMove()) {
				pk_x = (int) (pk_x + step_size * Math.cos(pk_fx) + 0.5);
				pk_y = (int) (pk_y + step_size * Math.sin(pk_fx) + 0.5);
			}
		}
	}

	public void useSkill(int index) {
		if(isPK_ing){
			return;
		}
		if (index < monSkills.size() && monSkills.get(index) != null) {
			isPK_ing = true;
			UpdataPKflag updatapkflag = new UpdataPKflag(this,monSkills.get(index));
			timer.schedule(updatapkflag,monSkills.get(index).getSkillms());
		}
	}

	public boolean isNearPerson(int lenght) {
		double len = Math.sqrt((person.getPk_x() - pk_x) * (person.getPk_x() - pk_x)
				+ (person.getPk_y() - pk_y) * (person.getPk_y() - pk_y));
		return len < lenght;
	}

	public void attack(MonsterSkill skill) {
		log.info("mmmmmm****小怪释放了技能" + skill.getName());
		AttackREG attreg = skill.execute();
		DefenceREG dereg = person.getDefenceREG();
		boolean b = attreg.getRec().isIntersect(dereg.getRec()); // 技能是否命中
		log.info(pk_x+"!!!!"+pk_y);
		if (b) {
			log.info("mmmmmm****小怪释放技能" + skill.getName() + "成功命中");
			int avd = dereg.getAvd();
			double ran = Math.random() * 100;
			if (ran > avd) { // 没有被回避
				int num = 0;
				if (attreg.getAttackType() == 0) { // 物理伤害
					num = (int) (attreg.getAttackPower() - dereg.getDad() + 0.5);
				} else { // 魔法伤害
					num = (int) (attreg.getAttackPower() - dereg.getDap() + 0.5);
				}
				num = num>0?num:0;
				log.info("mmmmmm****小怪释放技能" + skill.getName() + "的伤害类型是" + attreg.getAttackType() + "原本伤害"
						+ attreg.getAttackPower() + "防御后实际伤害" + num);
				log.info("mmmmmmmm****猪脚被攻击,原来血量" + person.getHp());
				person.setHp(person.getHp() - num);
				if (person.getHp() <= 0) {
					person.setHp(0);
					person.setLive(false);
					fsmmachine.updataStates(2);
				}
				log.info("mmmmmmmm****猪脚被攻击后血量" + person.getHp());
			}
		}else{
			log.info("mmmmmmmm****小怪技能释放完毕");
		}
	}

	private boolean isCanMove() {
		float x = (float) (pk_x + step_size* 2 * Math.cos(pk_fx));
		float y = (float) (pk_y + step_size * 2 * Math.sin(pk_fx));
		boolean b2 = MyREC.isIntersect(new Point2D.Float(x, y), pk_fx, size, 800, 600);
		double len = Math.sqrt((x - person.getPk_x()) * (x - person.getPk_x())
				+ (y - person.getPk_y()) * (x - person.getPk_y()));
		if (len >= size / 2 + 25 && !b2) {
			return true;
		} else {
			return false;
		}
	}

	private void upDataFx() {
		double fx1 = Math
				.asin((person.getPk_y() - pk_y) / Math.sqrt((person.getPk_x() - pk_x) * (person.getPk_x() - pk_x)
						+ (person.getPk_y() - pk_y) * (person.getPk_y() - pk_y)));
		if (fx1 > 7 / 18 * Math.PI) {
			fx1 = Math.PI / 2;
		} else if (fx1 < -7 / 18 * Math.PI) {
			fx1 = Math.PI / -2;
		} else if (Math.abs(fx1) < Math.PI / 9) {
			fx1 = 0;
		}
		if (person.getPk_x() < pk_x) {
			if (fx1 > 0) {
				pk_fx = Math.PI - fx1;
			} else {
				pk_fx = 0 - Math.PI - fx1;
			}
		} else {
			pk_fx = fx1;
		}
	}

	public DefenceREG getDefenceREG() {
		return new DefenceREG(dvd, dad, dap, pk_fx, pk_x, pk_y, size);
	}
	
	@Override
	public Monster clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Monster m = new Monster(id, "怪物替身", monster_i, monster_j, person);
		m.setMonsterImage(monsterImage);
		m.setSize(size);
		m.setPk_fx(pk_fx);
		m.setPk_x(pk_x);
		m.setPk_y(pk_y);
		m.setAd(ad);
		m.setAp(ap);
		m.setDad(dad);
		m.setDap(dap);
		m.setHp(hp);
		m.setZhp(zhp);
		m.setDvd(dvd);
		m.setStep_size(step_size);
		m.setLive(isLive);
		m.setPK_ing(isPK_ing);
		Vector<MonsterSkill> sks = new Vector<MonsterSkill>();
		sks.addAll(monSkills);
		for (MonsterSkill ms : sks) {
			ms.setCloneobj(m);
		}
		m.setMonSkills(sks);
		m.setLog(log);
		ArrayList<FSMState> s = new ArrayList<FSMState>();
		s.addAll(fsmmachine.getStates());	
		for (FSMState fsms : s) {
			fsms.updataCloneObj(m);
		}
		FSMMachine fsmma = new FSMMachine(s, fsmmachine.getDefaultIndex());
		m.setFsmmachine(fsmma);
		m.setTimer(new Timer());
		return m;
	}
}

class UpdataPKflag extends TimerTask {
	private Monster mon;
	private MonsterSkill skill;

	public UpdataPKflag(Monster mon,MonsterSkill skill) {
		this.mon = mon;
		this.skill = skill;
	}

	@Override
	public void run() {
		mon.attack(skill);
		mon.setPK_ing(false);
	}

}
