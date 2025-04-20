package model.fight.skill;

import java.awt.Image;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import model.Monster;
import model.Person;
import model.fight.AttackREG;
import model.fight.MyREC;
/**
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:怪物技能类
 */
public class MonsterSkill implements Skills{
	private int id;
	private Monster monster;
	private Image icon;
	private String name;
	private int skillms; //技能释放时间  毫秒为单位
	private int Length;   //技能的长度(从怪物中心坐标开始算起)
	private int width;	  //技能的宽度 
	
	public MonsterSkill(int id,Monster monster,String iconName,String name,int skillms,int Length,int width) {
		this.id = id;
		this.monster = monster;
		icon = new ImageIcon("config/images/skill/"+iconName).getImage();
		this.name = name;
		this.skillms = skillms;
		this.Length = Length;
		this.width = width;
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public Image getIcon() {
		// TODO Auto-generated method stub
		return icon;
	}
	
	public int getSkillms() {
		return skillms;
	}

	@Override
	public AttackREG execute() {
		double DJlen = Math.sqrt((width/2) * (width/2) + Length*Length);
		double fxx = Math.asin((width/2)/ DJlen);
		Point2D p1 = new Point2D.Float((float)(monster.getPk_x()+DJlen*Math.cos(monster.getPk_fx()+fxx)), (float)(monster.getPk_y()+DJlen*Math.sin(monster.getPk_fx()+fxx))); 
		Point2D p2 = new Point2D.Float((float)(monster.getPk_x()+DJlen*Math.cos(monster.getPk_fx()-fxx)), (float)(monster.getPk_y()+DJlen*Math.sin(monster.getPk_fx()-fxx))); 
		Point2D p3 = new Point2D.Float((float)(monster.getPk_x()+width/2*Math.cos(monster.getPk_fx()-Math.PI/2)), (float)(monster.getPk_y()+width/2*Math.sin(monster.getPk_fx()-Math.PI/2))); 
		Point2D p4 = new Point2D.Float((float)(monster.getPk_x()+width/2*Math.cos(monster.getPk_fx()+Math.PI/2)), (float)(monster.getPk_y()+width/2*Math.sin(monster.getPk_fx()+Math.PI/2))); 
		return new AttackREG(0, monster.getAd(), new MyREC(p1, p2, p3, p4));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setCloneobj(Object cloneobj) {
		monster = (Monster)cloneobj;
	}

}
