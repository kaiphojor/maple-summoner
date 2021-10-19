package basic;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

import shopitem.Skill;

/*
 * 기지. 기지에서 유닛을 생산한다. 
 */
public class Summoner extends Character{
	ArrayList<Skill> skillList;// 스킬 목록
	public int resource;// 현재 자원 
	public int totalResource;//최대 자원 
	public int resourceRecoverySpeed; // 자원 회복 속도
	ArrayList<Monster> unitList;// 기지가 생산할 수 있는 유닛리스트/ 기지가 생산한 유닛 리스트
//	public boolean[] isSummonable;
	public Hashtable<String, Boolean> isSummonable;
	int skillCoolDown; 			//스킬 쿨타임
	public int[] inventory; 
	
	// 생성자. 이미지, 체력, 좌표, 동전을 매개변수로 한다.
	// 자원(소환력)은 0부터 자원차는 속도만큼 차기 시작한다.
	public Summoner(Image image,int health, Point coordinate, int coin) {
		super(image,health, coordinate, coin);
		this.inventory = new int[] {0,0,0,0,0,0};// 초기값 0
		this.totalResource = 100;
		this.resource = 0;
		this.resourceRecoverySpeed = 1;
//		isSummonable = new boolean[] {true,false,false};
		isSummonable = new Hashtable<String, Boolean>();
		isSummonable.put("q", true);
		isSummonable.put("w", false);
		isSummonable.put("e", false);
	}
	// 스킬 사용
	public void useSkill(int skillCode) {
		//스킬 사용
	}
	// 유닛 생산
	public void makeUnit(int unitCode) {		
	}
	// 체력소모가 있을 때 메소드
	public void loseHealthPoint(int attack) {
		healthPoint -= attack;
		if(healthPoint < 0 ) {
			healthPoint = 0;
		}
	}
	// 자원(소환력)을 회복시킨다. 다만 최대 소환력을 넘지 못하게 막는다
	public void recoverResource() {
		resource += resourceRecoverySpeed; 
		resource = resource > totalResource ? totalResource:resource;
	}


}
