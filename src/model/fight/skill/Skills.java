package model.fight.skill;

import java.awt.Image;

import model.Monster;
import model.fight.AttackREG;

/**
 * 
 * @author lsy
 * @date 2017年1月6日
 * 注释:技能接口
 */
public interface Skills {
	int getID(); //获取技能id
	Image getIcon(); //获取技能图标
	AttackREG execute();//技能执行的主方法
	String getName(); //获取技能名字
	void setCloneobj(Object cloneobj);//重新设置技能宿主，克隆对象的时候一定要注意加上此处
}
