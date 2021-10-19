package basic;

import java.awt.Image;
import java.util.ArrayList;
// 상점 클래스
public class Shop extends Entity{
	// 판매하는 아이템의 리스트
	public ShopItem[] items;
	public boolean[] isSold;// 팔렸는가
	public int selectedPortionAmount;
	// 생성자 
	public Shop(Image image) {
		super(image );
		items = new ShopItem[6];
		isSold = new boolean[6];
		selectedPortionAmount = 1;		
	}
	public void addItem(ShopItem item) {
		items[item.itemIndex] = item; 
	}
	public void setSold(int index) {
		isSold[index] = true;
	}
	public boolean isSold(int index) {
		return isSold[index];
	}
	
}
