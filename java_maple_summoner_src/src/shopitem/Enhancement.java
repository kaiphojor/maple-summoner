package shopitem;

import java.awt.Image;

import basic.ShopItem;
import basic.Summoner;

/*
 * 구매하면 유닛,기지의 능력치를 영구적으로 향상시킨다
 */
public class Enhancement extends ShopItem{
	int applyStatCode; 
	// 적용할 스탯이 무엇인지 번호로 정해준다
	// 번호별로 대응되는 스탯은 다음과 같다
	int enhancePoint; // 강화되는 능력치를 정한다 

	public Enhancement(Image image) {
		super(image);
	}
	
	// 영구적으로 강화효과를 적용한다.
	public void applyEffect(Summoner summoner){
		switch(applyStatCode) {
		
		}
	}
	

}
