package summoner;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import basic.Summoner;
import monster.EnemyMonster;
// 적 소환사. 몬스터를 불러내어 아군을 공격한다.
public class EnemySummoner extends Summoner{
	// 생성자. 이미지, 체력, 생성할 좌표, 처치보상이 매개변수이다. 
	public EnemySummoner(Image image,int health, Point coordinate, int coin) {
		super(image,health, coordinate, coin);
	}
	// 체력이 적의 공격에 따라 줄게 되는 메소드. 0 미만으로 체력이 떨어지지 않게 한다. 
	public void loseHealthPoint(int attack) {
		healthPoint -= attack;
		if(healthPoint < 0 ) {
			healthPoint = 0;
		}
	}
	
	//기지의 남은 체력에 맞는 체력 막대를 가져와서 반환
	public static Image getBaseHealthBar(Toolkit tk, int currentHealth,int totalHealth) {
		// (현재 체력/ 최대체력) = (x / 15 )
		// x = 15 * 현재
		// x = 체력 막대의 갯수, 15 = 최대 체력일 때 막대 갯수
		int currentBarNumber = 15 * currentHealth / totalHealth;
		return tk.getImage("images/enemy_base_health_bar/Health_bar"+currentBarNumber + ".png");
	}
	// unit을 소환할만큼의 에너지가 충분하다면 true, 아니면 false 반환
	public boolean summonUnit(EnemyMonster unit){
		if(resource >= unit.cost){
			resource -= unit.cost;
			return true;
		}else {
			return false;			
		}		
	}
}
