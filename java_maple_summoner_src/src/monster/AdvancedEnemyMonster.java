package monster;


import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import basic.Monster;
import gui.ForceField;
import guiEx2shooting.FrameMain;
import summoner.EnemySummoner;
import summoner.PlayerSummoner;

// 적 소환사가 소환해서 보내는 몬스터들이다. 직접적인 공격은 몬스터가 담당한다
public class AdvancedEnemyMonster extends EnemyMonster{
	int[] enemyWidth;
	int detectedEnemyWidth;
	// 생성자
	// 이미지, 체력, 위치좌표, 처치시 보상, 사거리, 어느 stage에 나오는지 구분하는 변수, 속도, 공격력, 소모하는 자원, 공격하는 간격을 매개변수로 삼는다 	
	public AdvancedEnemyMonster(Image image, int health, Point location, int coin, int range, int speed,
			int attackPoint, int cost, int delay,EnemySummoner summoner) {
		// faction이 0이란 의미는 아군이란 뜻이다
		super(image, health, location, coin, range, speed, attackPoint, cost,delay,summoner);
		enemyWidth = new int[]{90,128,232};
		requirementStage = 3;
		imageHeight = 242;
	}	
}
