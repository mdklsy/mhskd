package model.fight;

import java.awt.Rectangle;

/**
 * 
 * @author lsy
 * @date 2017年1月6日
 * 注释:攻击矩形
 */
public class AttackREG {
	private int attackType;  //攻击类型 0物理攻击     1魔法攻击
	private float attackPower; //攻击伤害
	private MyREC rec;  //攻击范围
	
	public AttackREG(int attackType,float attackPower,MyREC rec) {
		this.attackType = attackType;
		this.attackPower = attackPower;
		this.rec = rec;
	}

	public int getAttackType() {
		return attackType;
	}

	public float getAttackPower() {
		return attackPower;
	}

	public MyREC getRec() {
		return rec;
	}
	
	
}
