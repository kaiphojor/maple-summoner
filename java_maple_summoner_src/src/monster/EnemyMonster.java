package monster;


import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import basic.Monster;
import gui.Music;
import summoner.EnemySummoner;
import summoner.PlayerSummoner;

// 적 소환사가 소환해서 보내는 몬스터들이다. 직접적인 공격은 몬스터가 담당한다
public class EnemyMonster extends Monster{
	int[] enemyWidth;
	int detectedEnemyWidth;
	EnemySummoner summoner;
	// 생산하기 위해서 필요한 스테이지조건
	public int requirementStage;
	// 때리는 효과음
	Music soundEffect;
	// 생성자
	// 이미지, 체력, 위치좌표, 처치시 보상, 사거리, 어느 stage에 나오는지 구분하는 변수, 속도, 공격력, 소모하는 자원, 공격하는 간격을 매개변수로 삼는다 	
	public EnemyMonster(Image image, int health, Point location, int coin, int range, int speed,
			int attackPoint, int cost, int delay,EnemySummoner enemySummoner) {
		// faction이 0이란 의미는 아군이란 뜻이다
		super(image, health, location, coin, range, speed, attackPoint, cost,delay);
		enemyWidth = new int[]{90,128,232};
		summoner = enemySummoner;
	}
	
	/*
	 * 주의할 점.
	 * 적 입장에서 적은 아군이다. 따라서 아군은 enemyHorde로 표기 된다. 
	 */
	// 사거리 안에 있는 가장 가까운 적을 찾는다. 소환사가 사거리에 있으면 소환사를 목표물로 삼는다
	public void findEnemy(ArrayList<PlayerMonster> enemyHorde,PlayerSummoner summoner ){
		// 최단거리, 그리고 최단거리에 해당하는 인덱스를 담는 변수
		int nearestEnemyDistance= -1;
		int nearestEnemyIndex = -1;
		// 적의 가로 길이. 사거리를 잴때 필요하다.
//		int enemyHorizontalSize = FrameMain.ImageWidthValue("images/jr_seal.png");
		// 먼저 소환사 찾기 - 소환사 먼저 공격하게 해서 한번 기세가 밀리면 바로 지는 상황을 덜 나오게 한다 
		if(!summoner.isDead() && coordinate.x - range <= 10) { // 소환사의 체력이 1 이상일 때만 공격한다
			target = -2; // -1은 목표물이 정해지지 않았을 때, -2는 소환사
			isEnemyDetected = true;
		}else{
			// 사정거리에 소환사가 없다면
			// 반복문을 통해 가장 가까운 적 찾기
			for(int i=0; i<enemyHorde.size(); i++){
				PlayerMonster enemy = enemyHorde.get(i);
				switch(enemy.keyCode) {
				case 'q':
					detectedEnemyWidth = enemyWidth[0];
					break;
				case 'w':
					detectedEnemyWidth = enemyWidth[1];
					break;
				case 'e':
					detectedEnemyWidth = enemyWidth[2];
					break;
				}
				int enemyXcoordinate = enemy.coordinate.x;
//				char keyCode = enemy.keyCode;
				// 사정거리 내에 적이 있다면. 적이 왼쪽에 있고 아군이 오른쪽에 있을 때를 가정한다
				// 아군의 좌상단 위치 - 왼쪽 방향 사거리 >= 적의 좌상단 위치 + 적의 너비
				if(coordinate.x - range <= enemyXcoordinate + detectedEnemyWidth) {
					isEnemyDetected = true;
					// -1 일때는 사거리에 적이 처음으로 들어왔을때.
					if(nearestEnemyIndex == -1) {
						nearestEnemyIndex = i;
						nearestEnemyDistance = enemyXcoordinate;
					// 거리를 비교해서 더 거리가 가까운 쪽을 목표물으로 잡는다
					}else if(nearestEnemyDistance > enemyXcoordinate) {
						nearestEnemyIndex = i;
						nearestEnemyDistance = enemyXcoordinate;
					}
				}
			}
			// 사거리 내 가장 가까운 적을 목표물으로 정한다. 
			target = nearestEnemyIndex;	
		}		
	}
	// 적을 공격한다. 소환사가 있으면 소환사를 우선 공격한다.
	public void attackEnemy(ArrayList<PlayerMonster> enemyHorde,PlayerSummoner base,int isSkillOn) {
		// 만약 지정한 목표물이 없으면 목표물 초기화
		// 목표물이 있으면 카운트 기점으로 때린다.대기시간이 있고, 때리고, 그 다음타격의 대기시간을 반복한다.
		// 목표물이 죽었으면 목표를 다시 정한다. 
		if(target == -1) {
			// 목표물이 없으면 적을 찾았다는 flag를 해제한다
			this.isEnemyDetected = false;
		}else if(target == -2){
				// 소환사를 공격한다면
			if(isSkillOn == 1) {
//				System.out.println("guarded");

			}else {
				base.loseHealthPoint(this.attackPoint);
				soundEffect = new Music("derp.mp3",false);
				soundEffect.start();
			}
			
		}else if(target >= 0){
			soundEffect = new Music("derp.mp3",false);
			soundEffect.start();
			PlayerMonster targetEnemy = enemyHorde.get(target);
			// 공격력 만큼 적을 공격한다
			targetEnemy.healthPointLoss(attackPoint);
			// 공격해서 hp가 0이 되면 목표에서 해제한다
			if(targetEnemy.getHealthPoint() <= 0){
				enemyHorde.remove(target);
				this.target = -1;
				this.isEnemyDetected = false;				
			}

		}
	}
	

	// 체력을 줄어들게 하는 메소드. 0 미만으로 떨어지지 않게 한다.
	public void healthPointLoss(int attack) {
		this.healthPoint -= attack; 
		this.healthPoint = this.healthPoint < 0 ? 0 : this.healthPoint;
	}
	// 적 몬스터의 이동을 위한 메소드
	public void move() { 
		coordinate.x -= 5; // x 좌표에 10만큼 몬스터 이동
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
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int rewardCoin) {
		this.coin = rewardCoin;
	}
}
