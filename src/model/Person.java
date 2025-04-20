package model;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import model.fight.DefenceREG;
import model.fight.MyREC;
import model.fight.skill.PersonSkill;

/**
 * 
 * @author LSY
 * @date Dec 7, 2016 主人公类 主要参数：坐标，步长，人物名
 */
public class Person implements Cloneable {
	
	private static Logger log = Logger.getLogger(Monster.class); 
	// 人物大小
	// 人物在面板中的坐标
	private final int person_x = 400;
	private final int person_y = 300;
	// 人物步长
	private int step_size = 2;
	// 人物出生点顶点在地图中的坐标
	private int person_init_x;
	private int person_init_y;
	// 人物方向 上下左右>>1234
	private int fx = 1;
	// 人物名
	private String name;
	//////////////// 人物游戏属性
	private boolean isLive; // 是否存活
	private int lv; // 等级
	private int exp; // 经验值
	private int hp; // 当前血量
	private int zhp; // 总血量
	private int ad; // 攻击力
	private int dad; // 物理防御
	private int ap; // 法术攻击
	private int dap; // 法术防御
	private int phy; // 体力
	private int zphy; // 总体力
	private int avd; // 回避率 0-100
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	//////////////// 人物pk参数
	private double pk_fx; // 人物移动方向角度的弧度表示
	private double skill_fx; // 鼠标方向（决定技能释放的方向）角度的弧度表示
	private int pk_x;
	private int pk_y;
	private int target_x;
	private int target_y;
	private boolean isPKWin;
	private Vector<PersonSkill> perSkills;
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	private Bao bao;
	private HashMap<String, Items> personEqpt;
	private Image personImage;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean rigth;
	private int[] personImageIndex;

	public Person(String name) {
		person_init_x = 100;
		person_init_y = 500;
		bao = null;
		this.name = name;
		up = false;
		down = false;
		left = false;
		rigth = false;
		personImage = new ImageIcon("config/images/person/girl_up_1.png").getImage();
		personImageIndex = new int[] { 1, 1, 1, 1 };
		eqptInit();// 初始化人物装备
		attrInit();// 初始化人物属性

	}

	private void attrInit() {
		lv = 1;
		exp = 0;
		hp = 50;
		zhp = 50;
		ad = 10;
		ap = 10;
		dad = 0;
		dap = 0;
		phy = 10;
		zphy = 10;
		avd = 0;
		isLive = true;
		isPKWin = false;
		perSkills = new Vector<PersonSkill>();
	}

	private void eqptInit() {
		// helmet头盔--coat上衣--pants裤子--shoes鞋--weapon武器--Addition附加道具
		personEqpt = new HashMap<String, Items>();
		personEqpt.put("helmet", null);
		personEqpt.put("coat", null);
		personEqpt.put("pants", null);
		personEqpt.put("shoes", null);
		personEqpt.put("weapon", null);
		personEqpt.put("Addition1", null);
		personEqpt.put("Addition2", null);
		personEqpt.put("Addition3", null);
	}

	public void upDataAttr() {
		Equipment equ = null;
		ad = 10;
		dad = 0;
		ap = 10;
		dap = 0;
		phy = 10;
		avd = 10;
		for (String name : personEqpt.keySet()) {
			equ = (Equipment) personEqpt.get(name);
			if (equ != null) {
				ad = ad + equ.getAd();
				dad = dad + equ.getDad();
				ap = ap + equ.getAp();
				dap = dap + equ.getDap();
				phy = phy + equ.getPhy();
				avd = avd + equ.getAvd();
			}

		}
	}

	public DefenceREG getDefenceREG() {
		return new DefenceREG(avd, dad, dap, fx, pk_x, pk_y, 50);
	}

	public HashMap<String, Items> getPersonEqpt() {
		return personEqpt;
	}

	public int getI() {
		return (int) ((person_init_x + 24.9) / 50);
	}

	public int getJ() {
		return (int) ((person_init_y + 24.9) / 50);
	}

	public int getleftI() {
		return (int) ((person_init_x - 0.1) / 50);
	}

	public int getrightI() {
		return (int) ((person_init_x + 49.9) / 50);
	}

	public int getupJ() {
		return (int) ((person_init_y - 0.1) / 50);
	}

	public int getdownJ() {
		return (int) ((person_init_y + 49.9) / 50);
	}

	public void move(Map map) {
		int mapData[][] = map.getMapData();
		if (up) {
			// 检测碰撞
			if (!isCanMoveUp(mapData)) {
				return;
			}
			// 移动
			fx = 1;
			person_init_y = person_init_y - step_size;
			personImageIndex[0] = personImageIndex[0] == 4 ? 1 : personImageIndex[0] + 1;
			personImage = new ImageIcon("config/images/person/girl_up_" + personImageIndex[0] + ".png").getImage();
		} else if (down) {
			if (!isCanMoveDown(mapData)) {
				return;
			}
			fx = 2;
			person_init_y = person_init_y + step_size;
			personImageIndex[1] = personImageIndex[1] == 4 ? 1 : personImageIndex[1] + 1;
			personImage = new ImageIcon("config/images/person/girl_down_" + personImageIndex[1] + ".png").getImage();
		} else if (left) {
			if (!isCanMoveLeft(mapData)) {
				return;
			}
			fx = 3;
			person_init_x = person_init_x - step_size;
			personImageIndex[2] = personImageIndex[2] == 4 ? 1 : personImageIndex[2] + 1;
			personImage = new ImageIcon("config/images/person/girl_left_" + personImageIndex[2] + ".png").getImage();
		} else if (rigth) {
			if (!isCanMoveRight(mapData)) {
				return;
			}
			/*
			 * if (getI()<mapData[0].length-1) { if
			 * ((mapData[getJ()][getI()+1]<2)&&(Math.abs(person_init_x)%50)>24)
			 * { return; } }
			 */
			fx = 4;
			person_init_x = person_init_x + step_size;
			personImageIndex[3] = personImageIndex[3] == 4 ? 1 : personImageIndex[3] + 1;
			personImage = new ImageIcon("config/images/person/girl_rigth_" + personImageIndex[3] + ".png").getImage();
			/*
			 * if (Math.abs(pyl_x)>=50) { pyl_x=pyl_x%50; }
			 */
		}
	}

	private boolean isCanMoveUp(int[][] mdata) {
		if (getJ() < 1) {
			if (person_init_y > 5) {
				return true;
			} else {
				return false;
			}
		} else {
			if (mdata[getupJ()][getI()] > 100) {
				return false;
			} else {
				if (mdata[getupJ()][getleftI()] > 100) {
					if (person_init_x % 50 == 0) {
						return true;
					} else if (person_init_x % 50 > 40) {
						person_init_x = person_init_x + 50 - person_init_x % 50;
						return true;
					} else {
						return false;
					}
				} else if (mdata[getupJ()][getrightI()] > 100) {
					if (person_init_x % 50 < 10) {
						person_init_x = person_init_x - person_init_x % 50;
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}

		}
	}

	private boolean isCanMoveDown(int[][] mdata) {
		if (getJ() == mdata.length - 1) {
			System.out.println("属于最下面");
			if (person_init_y % 50 < 45) {
				return true;
			} else {
				return false;
			}
		} else {
			if (mdata[getdownJ()][getI()] > 100) {
				return false;
			} else {
				if (mdata[getdownJ()][getleftI()] > 100) {
					if (person_init_x % 50 == 0) {
						return true;
					} else if (person_init_x % 50 > 40) {
						person_init_x = person_init_x + 50 - person_init_x % 50;
						return true;
					} else {
						return false;
					}
				} else if (mdata[getdownJ()][getrightI()] > 100) {
					if (person_init_x % 50 < 10) {
						person_init_x = person_init_x - person_init_x % 50;
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}

		}
	}

	private boolean isCanMoveLeft(int[][] mdata) {
		if (getI() < 1) {
			if (person_init_x % 50 > 5) {
				return true;
			} else {
				return false;
			}
		} else {
			if (mdata[getJ()][getleftI()] > 100) {
				return false;
			} else {
				if (mdata[getupJ()][getleftI()] > 100) {
					if (person_init_y % 50 == 0) {
						return true;
					} else if (person_init_y % 50 > 40) {
						person_init_y = person_init_y + 50 - person_init_y % 50;
						return true;
					} else {
						return false;
					}
				} else if (mdata[getdownJ()][getleftI()] > 100) {
					if (person_init_y % 50 < 10) {
						person_init_y = person_init_y - person_init_y % 50;
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}

		}
	}

	private boolean isCanMoveRight(int[][] mdata) {
		if (getI() > mdata[0].length - 2) {
			if (person_init_x % 50 < 5) {
				return true;
			} else {
				return false;
			}
		} else {
			if (mdata[getJ()][getrightI()] > 100) {
				return false;
			} else {
				if (mdata[getupJ()][getrightI()] > 100) {
					if (person_init_y % 50 == 0) {
						return true;
					} else if (person_init_y % 50 > 40) {
						person_init_y = person_init_y + 50 - person_init_y % 50;
						return true;
					} else {
						return false;
					}
				} else if (mdata[getdownJ()][getrightI()] > 100) {
					if (person_init_y % 50 < 10) {
						person_init_y = person_init_y - person_init_y % 50;
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}

		}
	}

	public Image getPersonImage() {
		return personImage;
	}

	public int getPerson_init_x() {
		return person_init_x;
	}

	public void setPerson_init_x(int person_init_x) {
		this.person_init_x = person_init_x;
	}

	public int getPerson_init_y() {
		return person_init_y;
	}

	public void setPerson_init_y(int person_init_y) {
		this.person_init_y = person_init_y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public   int getPersonX() {
		return person_x;
	}

	public  int getPersonY() {
		return person_y;
	}

	public  int getStepSize() {
		return step_size;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return rigth;
	}

	public void setRight(boolean right) {
		this.rigth = right;
	}

	public int getFx() {
		return fx;
	}

	public void setFx(int fx) {
		this.fx = fx;
	}

	public Bao getBao() {
		return bao;
	}

	public void setBao(Bao bao) {
		this.bao = bao;
	}

	public int getLv() {
		return lv;
	}

	public int getExp() {
		return exp;
	}

	public int getHp() {
		return hp;
	}

	public int getAd() {
		return ad;
	}

	public int getDad() {
		return dad;
	}

	public int getAp() {
		return ap;
	}

	public int getDap() {
		return dap;
	}

	public int getPhy() {
		return phy;
	}

	public int getAvd() {
		return avd;
	}

	public double getPk_fx() {
		return pk_fx;
	}

	public int getPk_x() {
		return pk_x;
	}

	public int getPk_y() {
		return pk_y;
	}

	public Vector<PersonSkill> getPerSkills() {
		return perSkills;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public boolean isPKWin() {
		return isPKWin;
	}

	public void setPKWin(boolean isPKWin) {
		this.isPKWin = isPKWin;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setPk_x(int pk_x) {
		this.pk_x = pk_x;
	}

	public void setPk_y(int pk_y) {
		this.pk_y = pk_y;
	}

	public void setTarget_x(int target_x) {
		this.target_x = target_x;
	}

	public void setTarget_y(int target_y) {
		this.target_y = target_y;
	}

	public int getZhp() {
		return zhp;
	}

	public int getZphy() {
		return zphy;
	}

	public double getSkill_fx() {
		return skill_fx;
	}
	
	public int getStep_size() {
		return step_size;
	}

	public void setStep_size(int step_size) {
		this.step_size = step_size;
	}

	public int getTarget_x() {
		return target_x;
	}

	public int getTarget_y() {
		return target_y;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setZhp(int zhp) {
		this.zhp = zhp;
	}

	public void setAd(int ad) {
		this.ad = ad;
	}

	public void setDad(int dad) {
		this.dad = dad;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public void setDap(int dap) {
		this.dap = dap;
	}

	public void setPhy(int phy) {
		this.phy = phy;
	}

	public void setZphy(int zphy) {
		this.zphy = zphy;
	}

	public void setAvd(int avd) {
		this.avd = avd;
	}

	public void setPk_fx(double pk_fx) {
		this.pk_fx = pk_fx;
	}

	public void setSkill_fx(double skill_fx) {
		this.skill_fx = skill_fx;
	}

	public void setPerSkills(Vector<PersonSkill> perSkills) {
		this.perSkills = perSkills;
	}
	
	public static void setLog(Logger log) {
		Person.log = log;
	}

	public void setPersonImage(Image personImage) {
		this.personImage = personImage;
	}

	public void updataFX(Point p1) {
		target_x = p1.x;
		target_y = p1.y;
		double fx1 = Math.asin((target_y - pk_y)
				/ Math.sqrt((target_x - pk_x) * (target_x - pk_x) + (target_y - pk_y) * (target_y - pk_y)));
		if (pk_x > target_x) {
			if (fx1 > 0) {
				pk_fx = Math.PI - fx1;
			} else {
				pk_fx = 0 - Math.PI - fx1;
			}
		} else {
			pk_fx = fx1;
		}
	}

	public void updataSkillFX(Point p) {
		int x = p.x;
		int y = p.y;
		double fx1 = Math.asin((y - pk_y) / Math.sqrt((x - pk_x) * (x - pk_x) + (y - pk_y) * (y - pk_y)));
		if (pk_x > x) {
			if (fx1 > 0) {
				skill_fx = Math.PI - fx1;
			} else {
				skill_fx = 0 - Math.PI - fx1;
			}
		} else {
			skill_fx = fx1;
		}

	}

	public void pkMove(Monster mon) {
		if (isCanPKMove(mon)) {
			pk_x = (int) (pk_x + step_size * Math.cos(pk_fx) + 0.5);
			pk_y = (int) (pk_y + step_size * Math.sin(pk_fx) + 0.5);
		}
	}

	private boolean isCanPKMove(Monster mon) {
		float x = (float) (pk_x + step_size * 2 * Math.cos(pk_fx));
		float y = (float) (pk_y + step_size * 2 * Math.sin(pk_fx));
		double len = Math.sqrt((x - target_x) * (x - target_x) + (y - target_y) * (y - target_y));
		double perMonLen  = Math.sqrt((x - mon.getPk_x()) * (x - mon.getPk_x()) + (y - mon.getPk_y()) * (y - mon.getPk_y()));
		boolean b1 = perMonLen <25 + mon.getSize()/2;
		boolean b2 = MyREC.isIntersect(new Point2D.Float(x, y), pk_fx, 50, 800, 600);
		if (len > 1 && !b2&&!b1) {
			return true;
		} else {
			return false;
		}
	}

	public void resetFX() {
		up = false;
		left = false;
		rigth = false;
		down = false;
	}
	
	@Override
	public Person clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Person p = new Person("战斗替身");
		p.setStep_size(step_size);
		p.setLive(isLive);
		p.setLv(lv);
		p.setExp(exp);
		p.setHp(hp);
		p.setZhp(zhp);
		p.setAd(ad);
		p.setDad(dad);
		p.setAp(ap);
		p.setDap(dap);
		p.setPhy(phy);
		p.setZhp(zhp);
		p.setAvd(avd);
		p.setPk_fx(pk_fx);
		p.setSkill_fx(skill_fx);
		p.setPk_x(pk_x);
		p.setPk_y(pk_y);
		p.setTarget_x(target_x);
		p.setTarget_y(target_y);
		p.setPKWin(isPKWin);
		Vector<PersonSkill> psks = new Vector<PersonSkill>();
		psks.addAll(perSkills);
		for (PersonSkill psk : psks) {
			psk.setCloneobj(p);
		}
		p.setPerSkills(perSkills);
		p.setLog(log);
		return p;
	}
}
