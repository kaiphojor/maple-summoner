package monster;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import basic.Summoner;
import guiEx2shooting.FrameMain;
import shopitem.PowerUp;
import shopitem.SpeedUp;
import shopitem.UnitHealthUp;
import summoner.EnemySummoner;

// q를 누르면 생성되는 몬스터 
public class WKeyMonster extends PlayerMonster{
	// 생성자. 
	// 이미지, 체력, 좌표, 가지고 있는 코인, 사거리, 이동속도, 
	// 공격력, 요구 자원, 공격 대기시간을 매개변수로 가진다. 
	public WKeyMonster(Image image, int health, Point location, int coin, int range, int speed,
			int attackPoint, int cost, int delay,Summoner summoner) {
		// faction이 0이란 의미는 아군이란 뜻이다		
		super(image, health, location, coin, range,speed, attackPoint, cost,delay,summoner);
		// 강화버프 적용
		if(summoner.inventory[1]>0){
			this.speed += SpeedUp.applyEffect();
		}
		if(summoner.inventory[2]>0){
			this.healthPoint += UnitHealthUp.applyEffect();
			this.totalHealth += UnitHealthUp.applyEffect();
		}
		if(summoner.inventory[3]>0){
			this.attackPoint += PowerUp.applyEffect();
		}		
		keyCode = 'w';
		imageHeight = 254;
//		System.out.println("width : " + imageWidth);
//		System.out.println("height : " + imageHeight);
	}
	// 사거리 안에 있는 가장 가까운 적을 찾는다. 소환사가 사거리에 있으면 소환사를 목표물로 삼는다
	public void findEnemy(ArrayList<EnemyMonster> enemyHorde, EnemySummoner enemyBase){
		// 최단거리, 그리고 최단거리에 해당하는 인덱스를 담는 변수
		int nearestEnemyDistance= -1;
		int nearestEnemyIndex = -1;
		// 아군의 가로길이. 사거리를 잴때 필요하다.
		// 각각의 이미지 필요 
//		int allyHorizontalSize = FrameMain.ImageWidthValue("images/jr_seal.png");
		// 먼저 기지 찾기 - 기지 먼저 공격하게 해서 한번 기세가 밀리면 바로 지는 상황을 덜 나오게 한다
		// 적 기지의 체력이 다 닳지 않은 상태에서만 공격 대상으로 삼는다
		if(enemyBase.healthPoint > 0 && (coordinate.x+imageWidth + range >= 800 - 80)) {
			target = -2; // -1은 값 미지정시, -2는 기지
			isEnemyDetected = true;
		}else{
			// 사정거리에 기지가 없다면
			// 반복문 순회하며 가장 가까운 적 찾기
			for(int i=0; i<enemyHorde.size(); i++){
				// 사정거리 내에 적이 있다면
				// 아군의 좌상단 위치 + 아군의 가로 길이 + 사거리 >= 적의 좌상단 위치
				if(coordinate.x + imageWidth +range >= enemyHorde.get(i).coordinate.x ) {
					isEnemyDetected = true;
					// -1 일때는 사거리에 적이 처음으로 들어왔을때이다
					if(nearestEnemyIndex == -1) {
						nearestEnemyIndex = i;
						nearestEnemyDistance = enemyHorde.get(i).coordinate.x;
					// 거리를 비교해서 더 거리가 가까운 쪽을 목표로 잡는다
					}else if(nearestEnemyDistance > enemyHorde.get(i).coordinate.x) {
						nearestEnemyIndex = i;
						nearestEnemyDistance = enemyHorde.get(i).coordinate.x;
					}
				}
			}
			// 사거리 내 가장 가까운 적을 타겟으로 정한다. 
			target = nearestEnemyIndex;	
		}
		
		
		
		// 만약 적을 찾는다면 isEnemy
	}
	public int getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(int healthPoint) {
		this.healthPoint = healthPoint;
	}

	public int getAttackPoint() {
		return attackPoint;
	}

	public void setAttackPoint(int attackPoint) {
		this.attackPoint = attackPoint;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

//	public void attackEnemy(ArrayList<EnemyMonster> enemyHorde,EnemySummoner enemyBase) {
//		// 만약 지정한 타겟이 없으면 타겟 초기화
//		// 타겟이 있으면 카운트 기점으로 때린다.선딜이 있고, 때리고, 후딜(다음타격의 선딜)
//		// 죽였으면 타겟 초기화 적 hp 기점 
//		if(target == -1) {
//			// 타겟이 없으면 적을 찾았다는 flag를 해제한다
//			this.isEnemyDetected = false;
//		}else if(target == -2){
//			// 기지를 공격한다면
//			enemyBase.loseHealthPoint(this.attackPoint);
//		}else if(target >= 0){
//			EnemyMonster targetEnemy = enemyHorde.get(target);
//			// 공격력 만큼 적을 공격한다
//			targetEnemy.healthPointLoss(attackPoint);
//			// 조져서 hp가 0이 되면  부수고 타게팅을 초기화 한다
//			if(targetEnemy.getHealthPoint() <= 0){
//				//TODO: 코인을 얻는데 나중에 추가해야한다.
//				targetEnemy.getCoin();
//				enemyHorde.remove(target);
//				this.target = -1;
//				this.isEnemyDetected = false;
//				
//			}
//
//			// 몬스터를 공격한다면
//			// TODO: 임시조치 초기화
//		}
//	}
	public void healthPointLoss(int attack) {
		this.healthPoint -= attack;
	}
}

