package model;

import java.awt.Image;

/**
 * 
 * @author lsy
 * @date 2016年12月26日
 * 注释:药剂类道具
 */
public class Medicament implements Items {
	private int id;
	private String name ;
	private Image ima;
	private int amount;
	private int buyPrice;
	private int salePrice;
	public Medicament(int id, String name,Image ima,int salePrice) {
		this.amount=0;
		this.id = id;
		this.name = name;
		this.ima = ima;
		this.salePrice = salePrice;
	}
	@Override
	public boolean isOne(int id) {
		if(this.id==id){
			return true;
		}
		return false;
	}
	@Override
	public void addAmount(int num) {
		this.amount = amount+num;
		
	}
	@Override
	public int removeAmount(int num) {
		if(num<amount){
			amount = amount-num;
			return 1;
		}else if(num==amount){
			amount = 0;
			return 0;
		}else{
			return  -1;
		}
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public Image getIma() {
		// TODO Auto-generated method stub
		return ima;
	}
	@Override
	public int getSalePrice() {
		// TODO Auto-generated method stub
		return salePrice;
	}
	@Override
	public itemType getItemType() {
		return null;
		// TODO Auto-generated method stub
	}
	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}
	
	
}
