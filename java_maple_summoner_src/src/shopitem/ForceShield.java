package shopitem;

import java.awt.Image;

// 무적스킬 : 일시적으로 피해를 입지 않는다
public class ForceShield extends Skill{
	
	public ForceShield(Image image) {
		super(image);
		itemCost = 100;
		itemIndex = 4;
		name = "A.T. 필드";
		coolDown = 10; // 스킬의 재사용 대기시간
		duration = 5; // 스킬의 지속시간, 단발성일수도

		itemDescription = duration+"초 동안 소환사 무적";
	}
}
