package model.fight;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
/**
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:自定义斜角矩形类
 */
public class MyREC {
	private Point2D p1;
	private Point2D p2;
	private Point2D p3;
	private Point2D p4;
	
	public MyREC(Point2D p1,Point2D p2,Point2D p3,Point2D p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}

	public Point2D getP1() {
		return p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public Point2D getP3() {
		return p3;
	}

	public Point2D getP4() {
		return p4;
	}
	
	public boolean isIntersect(MyREC o){
		boolean b = false;
		Line2D [] l1 = {new Line2D.Float(p1, p2),new Line2D.Float(p2, p3),new Line2D.Float(p3, p4),new Line2D.Float(p1, p4)}; 
		Line2D [] l2 = {new Line2D.Float(o.getP1(), o.getP2()),new Line2D.Float(o.getP2(), o.getP3()),new Line2D.Float(o.getP3(), o.getP4()),new Line2D.Float(o.getP1(), o.getP4())}; 
		for (int i = 0; i < l1.length; i++) {
			for (int j = 0; j < l2.length; j++) {
				if(l1[i].intersectsLine(l2[j])){
					b = true;
				}
			}
		}
		return b;
		
	}
	//判断两个矩形是否相交
	public static boolean isIntersect(Point2D.Float p1,double fx1,int size1,Point2D.Float p2,double fx2,int size2){
		boolean b = false;
		Point2D pp1 = new Point2D.Float((float)(p1.getX()+size1/2*1.414*Math.cos(fx1+Math.toRadians(45))), (float)(p1.getY()+size1/2*1.414*Math.sin(fx1+Math.toRadians(45)))); 
		Point2D pp2 = new Point2D.Float((float)(p1.getX()+size1/2*1.414*Math.cos(fx1-Math.toRadians(45))), (float)(p1.getY()+size1/2*1.414*Math.sin(fx1-Math.toRadians(45)))); 
		Point2D pp3 = new Point2D.Float((float)(p1.getX()+size1/2*1.414*Math.cos(fx1-Math.toRadians(135))), (float)(p1.getY()+size1/2*1.414*Math.sin(fx1-Math.toRadians(135)))); 
		Point2D pp4 = new Point2D.Float((float)(p1.getX()+size1/2*1.414*Math.cos(fx1-Math.toRadians(225))), (float)(p1.getY()+size1/2*1.414*Math.sin(fx1-Math.toRadians(225)))); 
		Point2D ppp1 = new Point2D.Float((float)(p2.getX()+size2/2*1.414*Math.cos(fx2+Math.toRadians(45))), (float)(p2.getY()+size2/2*1.414*Math.sin(fx2+Math.toRadians(45)))); 
		Point2D ppp2 = new Point2D.Float((float)(p2.getX()+size2/2*1.414*Math.cos(fx2-Math.toRadians(45))), (float)(p2.getY()+size2/2*1.414*Math.sin(fx2-Math.toRadians(45)))); 
		Point2D ppp3 = new Point2D.Float((float)(p2.getX()+size2/2*1.414*Math.cos(fx2-Math.toRadians(135))), (float)(p2.getY()+size2/2*1.414*Math.sin(fx2-Math.toRadians(135)))); 
		Point2D ppp4 = new Point2D.Float((float)(p2.getX()+size2/2*1.414*Math.cos(fx2-Math.toRadians(225))), (float)(p2.getY()+size2/2*1.414*Math.sin(fx2-Math.toRadians(225)))); 
		Line2D [] l1 = {new Line2D.Float(pp1, pp2),new Line2D.Float(pp2, pp3),new Line2D.Float(pp3, pp4),new Line2D.Float(pp1, pp4)}; 
		Line2D [] l2 = {new Line2D.Float(ppp1, ppp2),new Line2D.Float(ppp2, ppp3),new Line2D.Float(ppp3, ppp4),new Line2D.Float(ppp1, ppp4)}; 
		for (int i = 0; i < l1.length; i++) {
			for (int j = 0; j < l2.length; j++) {
				if(l1[i].intersectsLine(l2[j])){
					b = true;
				}
			}
		}
		return b;
	}
	//判断矩形和边界是否相交
	public static boolean isIntersect(Point2D.Float p,double fx,int size,int width,int height){
		boolean b = false;
		Point2D pp1 = new Point2D.Float((float)(p.getX()+size/2*1.414*Math.cos(fx+Math.toRadians(45))), (float)(p.getY()+size/2*1.414*Math.sin(fx+Math.toRadians(45)))); 
		Point2D pp2 = new Point2D.Float((float)(p.getX()+size/2*1.414*Math.cos(fx-Math.toRadians(45))), (float)(p.getY()+size/2*1.414*Math.sin(fx-Math.toRadians(45)))); 
		Point2D pp3 = new Point2D.Float((float)(p.getX()+size/2*1.414*Math.cos(fx-Math.toRadians(135))), (float)(p.getY()+size/2*1.414*Math.sin(fx-Math.toRadians(135)))); 
		Point2D pp4 = new Point2D.Float((float)(p.getX()+size/2*1.414*Math.cos(fx-Math.toRadians(225))), (float)(p.getY()+size/2*1.414*Math.sin(fx-Math.toRadians(225)))); 
		Point2D ppp1 = new Point2D.Float(0,0);
		Point2D ppp2 = new Point2D.Float(width,0);
		Point2D ppp3 = new Point2D.Float(width,height);
		Point2D ppp4 = new Point2D.Float(0,height);
		Line2D [] l1 = {new Line2D.Float(pp1, pp2),new Line2D.Float(pp2, pp3),new Line2D.Float(pp3, pp4),new Line2D.Float(pp1, pp4)}; 
		Line2D [] l2 = {new Line2D.Float(ppp1, ppp2),new Line2D.Float(ppp2, ppp3),new Line2D.Float(ppp3, ppp4),new Line2D.Float(ppp1, ppp4)}; 
		for (int i = 0; i < l1.length; i++) {
			for (int j = 0; j < l2.length; j++) {
				if(l1[i].intersectsLine(l2[j])){
					b = true;
				}
			}
		}
		return b;
	}
	
	
	@Override
	public String toString() {
		return p1.toString()+p2.toString()+p3.toString()+p4.toString();
	}
}
