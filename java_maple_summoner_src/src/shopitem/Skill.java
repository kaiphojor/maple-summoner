package shopitem;

import java.awt.Image;

import basic.ShopItem;

/*
 * 상점에서 파는 스킬이다. 사용하면 일정시간 동안 효과를 준다
 */
public class Skill extends ShopItem{
	int coolDown; // 스킬의 재사용 대기시간
	int duration; // 스킬의 지속시간, 단발성일수도
	public Skill(Image image) {
		super(image);
	}
	public void applyEffect() {
		// 적용 대상에 따라서 파라미터가 달라질 수 있다.
	}
}
