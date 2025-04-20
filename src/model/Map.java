package model;
/***
 * 
 * @author LSY
 * @date Dec 7, 2016
 * 地图的数据模型对象
 */
public class Map {
	//二维数组小于零的元素表示人物不能通过
	private int[][] mapData;
	private int[][] npcData;
	private String mapName;
	
	public Map(int[][] mapData,String mapName){
		this.mapData = mapData;
		this.mapName = mapName;
	}

	public int[][] getMapData() {
		return mapData;
	}

	public void setMapData(int[][] mapData) {
		this.mapData = mapData;
	}

	public int[][] getNpcData() {
		return npcData;
	}

	public void setNpcData(int[][] npcData) {
		this.npcData = npcData;
	}

	public String getMapName() {
		return mapName;
	}
	
	
}
