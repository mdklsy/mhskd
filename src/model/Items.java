package model;

import java.awt.Image;
/**
 * 
 * @author lsy
 * @date 2016年12月27日
 * 注释:道具类总接口
 */
public interface Items {
	//helmet头盔--coat上衣--pants裤子--shoes鞋--weapon武器--Addition附加道具
	public enum  itemType {helmet,coat,pants,shoes,weapon,Addition}
	//是否是同一种道具
	boolean isOne(int id);
	//添加道具的数量
	void addAmount (int num);
	//减少道具的数量---大于0操作成功;等于零操作成功，道具数量使用完毕，需要删除;小于零，操作失败。
	int removeAmount (int num);
	// 获取道具的名称
	String getName();
	//获取道具数量
	int getAmount();
	// 获取道具id
	int getId();
	// 获取道具图标
	Image getIma();
	// 获取道具出售价格
	int getSalePrice();
	// 获取道具类型
	itemType getItemType();
}
