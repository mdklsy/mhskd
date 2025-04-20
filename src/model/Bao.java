package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Vector;

/**
 * 
 * @author lsy
 * @date 2016年12月26日 注释:背包系统数据模型
 */
public class Bao {
	// 行索引
	private int i_index;
	// 列索引
	private int j_index;
	private Vector<Items> items;
	private int baoSize;

	public Bao(int i, int j) {
		baoSize = i*j;
		items = new Vector<Items>();
	}
	//获取背包道具的图像
	public ArrayList<Image> upDataImages(){
		ArrayList<Image> data = new ArrayList<Image>();
		for (Items item : items) {
			data.add(item.getIma());
		}
		return data;
		
	}
	//删除道具   道具的位置
	public void removeItem(int index){
		if(items.get(index)!=null){
			items.remove(index);
		}
	}
	//添加道具     道具及其数量
	public boolean addItem(Items item, int num) {
		for (int i = 0; i < items.size(); i++) {
				if (items.get(i).isOne(item.getId())) {
					items.get(i).addAmount(num+item.getAmount());
					return true;
				}
		}
		if (items.size() >= baoSize) {
			return false;
		}else{
			item.addAmount(num);
			items.add(item);
			return true;
		}
	}
	
	public Items getItem(int index){
		return items.get(index);
	}
	public int getBaoSize() {
		return baoSize;
	}
	public int getItemSum() {
		return items.size();
	}
	
}


