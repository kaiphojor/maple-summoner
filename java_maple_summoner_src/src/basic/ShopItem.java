package basic;

import java.awt.Image;

/*
 * 상점에서 파는 아이템.
 * 스킬, 강화가 있다
 */
public class ShopItem extends Entity{
	
	public int itemCost; // item의 가격
	boolean isSold; // 아이템이 판매 되었는지 아닌지 여부 - 강화, 스킬에만 해당
	public int itemIndex;
	public String itemDescription;
	public ShopItem(Image image) {
		super(image );
		isSold = false;
	}
}