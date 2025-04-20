package model.fight.skill;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import model.Person;
import model.fight.AttackREG;
import model.fight.MyREC;
import tool.Utils;
/**
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:人物技能类
 */
public class PersonSkill implements Skills{
	private int id;
	private Person person;
	private Image icon;
	private String name;
	private int phy;   //技能消耗的体力
	private int cdms;   //技能冷却时间
	private int afterusems;   //技能冷却时间
	private int waitms;  //技能动作时间（在此时间段内人物不能移动，小于20不起作用）
	private boolean isCanUse;  //技能是否可以释放
	private int Length;   //技能的长度(从人物坐标开始算起)
	private int width;	  //技能的宽度
	private Timer timer;
	
	public PersonSkill(int id,Person person,String iconpath,String name,int phy,int cdms,int waitms,int length,int width) {
		this.id = id;
		this.person = person;
		this.icon =Utils.getImage(iconpath);
		this.name = name;
		this.cdms = cdms;
		this.afterusems = 0;
		this.phy = phy;
		this.waitms = waitms;
		this.width = width;
		this.Length = length;
		
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public Image getIcon() {
		return icon;
	}

	@Override
	public AttackREG execute() {
		setCD();
		double DJlen = Math.sqrt((width/2) * (width/2) + Length*Length);
		double fxx = Math.asin((width/2)/ DJlen);
		Point2D p1 = new Point2D.Float((float)(person.getPk_x()+DJlen*Math.cos(person.getSkill_fx()+fxx)), (float)(person.getPk_y()+DJlen*Math.sin(person.getSkill_fx()+fxx))); 
		Point2D p2 = new Point2D.Float((float)(person.getPk_x()+DJlen*Math.cos(person.getSkill_fx()-fxx)), (float)(person.getPk_y()+DJlen*Math.sin(person.getSkill_fx()-fxx))); 
		Point2D p3 = new Point2D.Float((float)(person.getPk_x()+width/2*Math.cos(person.getSkill_fx()-Math.PI/2)), (float)(person.getPk_y()+width/2*Math.sin(person.getSkill_fx()-Math.PI/2))); 
		Point2D p4 = new Point2D.Float((float)(person.getPk_x()+width/2*Math.cos(person.getSkill_fx()+Math.PI/2)), (float)(person.getPk_y()+width/2*Math.sin(person.getSkill_fx()+Math.PI/2))); 
		return new AttackREG(0, person.getAd(), new MyREC(p1, p2, p3, p4));
	}

	private void setCD(){
		isCanUse  = false;
		afterusems = 0;
		timer.schedule(new TimerTask() {
			public void run() {
				afterusems = afterusems+100;
				if(!(afterusems<cdms)){
					afterusems = 0;
					isCanUse = true;
					this.cancel();
				}
			}
		}, 0, 100);
	}
	

	@Override
	public String getName() {
		return name;
	}

	public boolean isCanUse() {
		return isCanUse;
	}

	public int getCdms() {
		return cdms;
	}

	public int getAfterusems() {
		return afterusems;
	}

	@Override
	public void setCloneobj(Object cloneobj) {
		person = (Person)cloneobj;
		isCanUse = true;
		timer = new Timer();
	}
}
