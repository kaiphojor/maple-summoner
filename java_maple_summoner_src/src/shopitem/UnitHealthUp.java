package shopitem;

import java.awt.Image;

public class UnitHealthUp extends Enhancement{
	static int healthUpAmount;
	public UnitHealthUp(Image image) {
		super(image);
		healthUpAmount = 5;
		itemCost = 100;
		itemIndex = 2;
		name = "보조 방패";
		itemDescription = "몬스터체력+"+healthUpAmount;
	}
	public static int applyEffect() {
		return healthUpAmount;
	}
}
