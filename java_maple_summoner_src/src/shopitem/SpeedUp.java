package shopitem;

import java.awt.Image;

public class SpeedUp extends Enhancement{
	static int speedUpAmount;
	public SpeedUp(Image image){
		super(image);
		speedUpAmount = 2;
		itemCost = 100;
		itemIndex = 1;
		name = "신속의 장화";
		itemDescription = "이동속도+"+speedUpAmount;
	}
	public static int applyEffect() {
		return speedUpAmount;
	}
	
}
