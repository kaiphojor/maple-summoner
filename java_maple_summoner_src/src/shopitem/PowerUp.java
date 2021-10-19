package shopitem;

import java.awt.Image;

public class PowerUp extends Enhancement{
	static int powerUpAmount;
	public PowerUp(Image image) {
		super(image);
		powerUpAmount = 5;
		itemCost = 100;
		itemIndex = 3;
		name = "파워 업";
		itemDescription = "몬스터공격력+"+powerUpAmount;
	}
	public static int applyEffect() {
		return powerUpAmount;
	}
}
