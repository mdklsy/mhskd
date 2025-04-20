package model.fight;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * 
 * @author lsy
 * @date 2017年1月6日
 * 注释:防御矩形
 */
public class DefenceREG {
	private int avd; //回避率
	private int dad; //物理防御
	private int dap; //魔法防御
	private MyREC rec;  //受攻击范围
	public DefenceREG(int avd,int dad, int dap,double fx,int pk_x,int pk_y,int size) {
		this.dad = dad;
		this.dap = dap;
		this.avd = avd;
		Point2D p1 = new Point2D.Float((float)(pk_x+size/2*1.414*Math.cos(fx+Math.toRadians(45))), (float)(pk_y+size/2*1.414*Math.sin(fx+Math.toRadians(45)))); 
		Point2D p2 = new Point2D.Float((float)(pk_x+size/2*1.414*Math.cos(fx-Math.toRadians(45))), (float)(pk_y+size/2*1.414*Math.sin(fx-Math.toRadians(45)))); 
		Point2D p3 = new Point2D.Float((float)(pk_x+size/2*1.414*Math.cos(fx-Math.toRadians(135))), (float)(pk_y+size/2*1.414*Math.sin(fx-Math.toRadians(135)))); 
		Point2D p4 = new Point2D.Float((float)(pk_x+size/2*1.414*Math.cos(fx-Math.toRadians(225))), (float)(pk_y+size/2*1.414*Math.sin(fx-Math.toRadians(225)))); 
		this.rec = new MyREC(p1, p2, p3, p4);
	}
	public int getAvd() {
		return avd;
	}
	public MyREC getRec() {
		return rec;
	}
	public int getDad() {
		return dad;
	}
	public int getDap() {
		return dap;
	}
	
}
