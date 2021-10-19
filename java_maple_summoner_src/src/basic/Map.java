package basic;

import java.awt.Image;
import java.util.ArrayList;

import summoner.EnemySummoner;
import summoner.PlayerSummoner;

/*
 * 맵에서 전투를 진행한다.
 */
public class Map extends Entity{
	ArrayList<PlayerSummoner> player;
	ArrayList<EnemySummoner> enemy;
	Monster[] playerUnitList;// 생성된 유닛 리스트
	Monster[] enemyUnitList; // 적 유닛 리스트 
	
	
	// 생성자
	public Map(Image image) {
		super(image);
	}

	

}
