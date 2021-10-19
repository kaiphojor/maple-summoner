package basic;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import gui.Effect;
import gui.Music;
// 소환사가 소환한 몬스터이다. 적 몬스터와 적 소환사를 공격한다.
public class Monster extends Character{
	protected int range;// 사정거리
	protected int speed; // 이동속도
	protected int attackPoint; // 공격력
	public int cost; // 유닛을 생산하는데 들이는 자원
	
	Effect effect; // 가지고 있는 특수효과(피격효과 아님, 공격효과)
	public int target; 
	public boolean isEnemyDetected;
	// 타격 효과음
	protected Music soundEffect;
	public int delay; // 공격하는데 준비하는 시간. 시간지연
	//공격 타이밍을 thread의 cnt와 맞추기 위한 변수. 이 변수를 기준으로 해서 공격 타이밍이 맞춰진다
	public int attackCnt; 
	// 생성자. 이미지, 체력, 좌표, 처치보상, 사거리, 이동속도, 공격력, 요구 자원, 공격 대기시간을 매개변수로 가진다. 
	public Monster(Image image,
			int health, Point location, int coin, int range, int speed, int attackPoint, int cost,int delay){
		super(image,health, location, coin);
		this.range = range;
		this.speed = speed;
		this.attackPoint = attackPoint;
		this.cost = cost;
		this.delay = delay;
		this.attackCnt = -1;
	}
	
	// 실제 구현은 상속한 클래스에서 함.
	// 적 몬스터나 적 소환사를 찾는 메소드
	public void findEnemy(ArrayList<Monster> enemyHorde,Summoner enemySummoner ){
		
	}
	// 유닛은 적 유닛이나 적기지를 마주치면 공격한다.
	public void attackEnemy(ArrayList<Monster> enemyHorde,Summoner enemySummoner) {
		// 자기 사정거리 안에 적이 있을 감지하면 멈추고 때린다
	}
	public void setCoordinate(int x,int y) {
		coordinate = new Point(x,y);
	}


}
