package shopitem;

import java.awt.Image;

import basic.ShopItem;
import basic.Summoner;
// 소모해서 체력을 채우는 물약클래스
public class Portion extends ShopItem{
	// 체력 회복량
	static int healthPointUp; 
	public Portion(Image image ) {
		super(image);
		healthPointUp = 30;
		itemCost = 100;
		itemIndex = 0;
		name = "빨강포션(HP"+healthPointUp+")";
	}
	


	// 효과 적용시 체력을 회복
	public static void applyEffect(Summoner summoner) {
		summoner.healthPoint += healthPointUp;
		// 다만 최대 체력을 초과해서 회복하지 않음
		if(summoner.healthPoint > summoner.totalHealth) {
			summoner.healthPoint = summoner.totalHealth;
		}
	}
}
