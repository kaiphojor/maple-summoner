package summoner;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import basic.ShopItem;
import basic.Summoner;
import monster.PlayerMonster;

public class PlayerSummoner extends Summoner{
	// 생성자. 이미지, 체력, 생성할 좌표, 가진 코인수가  매개변수이다. 
	public PlayerSummoner(Image image,int health, Point coordinate, int coin) {
		super(image,health, coordinate, coin);
	}
	// 아이템 사기 
	public boolean buyItem(int itemCode,int itemValue,int itemAmount) {
		// 아이템 사기
		if(coin >= itemAmount * itemValue) {
			coin -= itemAmount * itemValue;
			inventory[itemCode] += itemAmount;
			return true;
		}else {
			return false;
		}
	}
	//기지의 남은 체력에 맞는 체력 막대를 가져와서 반환
	public static Image getBaseHealthBar(Toolkit tk, int currentHealth,int totalHealth) {
		// (현재 체력/ 최대체력) = (x / 15 )
		// x = 15 * 현재
		// x = 체력 막대의 갯수, 15 = 최대 체력일 때 막대 갯수
		int currentBarNumber = 15 * currentHealth / totalHealth;
		return tk.getImage("images/base_health_bar/Health_bar"+currentBarNumber + ".png");
	}
	// unit을 소환할만큼의 에너지가 충분하다면 true, 아니면 false 반환
	public boolean summonUnit(PlayerMonster unit){
		if(resource >= unit.cost){
			resource -= unit.cost;
			return true;
		}else {
			return false;			
		}		
	}
}
