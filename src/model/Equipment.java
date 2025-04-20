package model;

import java.awt.Image;

/**
 * 
 * @author lsy
 * @date 2016年12月27日 注释:装备类道具
 */
public class Equipment implements Items {
	private int id;
	private String name;
	private Image ima;
	private int amount;
	private int buyPrice;
	private int salePrice;
	private itemType type;
	//////////////// 装备加成属性   
	private int ad; // 攻击力
	private int dad; // 物理防御
	private int ap; // 法术攻击
	private int dap; // 法术防御
	private int phy; // 体力
	private int avd; // 回避率 0-100
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	public Equipment(int id, String name, Image ima, int salePrice, itemType type,int[] attr) {
		this.amount = 0;
		this.id = id;
		this.name = name;
		this.ima = ima;
		this.buyPrice = 0;
		this.salePrice = salePrice;
		this.type = type;
		ad = attr[0];
		dad = attr[1];
		ap = attr[2];
		dap = attr[3];
		phy = attr[4];
		avd = attr[5];
	}

	@Override
	public boolean isOne(int id) {
		if (this.id == id) {
			return true;
		}
		return false;
	}

	@Override
	public void addAmount(int num) {
		this.amount = amount + num;

	}

	@Override
	public int removeAmount(int num) {
		if (num < amount) {
			amount = amount - num;
			return 1;
		} else if (num == amount) {
			amount = 0;
			return 0;
		} else {
			return -1;
		}

	}

	@Override
	public String getName() {
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
		return type;
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return amount;
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

}
