package basic;

import java.awt.Image;
import java.awt.Point;
// 캐릭터는 체력이 존재하는 것들이다.
public class Character extends Entity{
	public int healthPoint;// 체력
	public int totalHealth; // 전체 체력
	public Point coordinate; // 캐릭터가 있는 위치 좌표. x,y 좌표를 포함하고 있으며 그림의 좌상단 지점을 기준으로 한다
	public int coin; // 적이 죽었을 때 주는 보상 & 플레이어가 가지고 있는 코인 보유량
		
	// 생성자. 캐릭터의 이미지, 체력, 위치좌표, 가지고 있는 코인을 매개변수로 한다.
	public Character(Image image, int health, Point point, int coin){
		super(image);
		this.healthPoint = health;
		this.totalHealth = health;
		this.coordinate = point;
		this.coin = coin;
	}
	// 체력이 존재하는지 마는지 확인
	public boolean isDead() {
		return this.healthPoint <= 0 ;
	}
	// 체력 소모 메소드. 체력은 0 미만으로 떨어지지 않는다.
	public void loseHealthPoint(int attack) {
		healthPoint -= attack;
		if(healthPoint < 0 ) {
			healthPoint = 0;
		}
	}
	
	public int getHealth() {
		return healthPoint;
	}
	public void setHealth(int health) {
		this.healthPoint = health;
	}
	public Point getLocation() {
		return coordinate;
	}
	public void setLocation(Point coordinate ) {
		this.coordinate = coordinate;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	

}
