package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class UnitExplosion extends Thread{
	// image 26 frame
	private Graphics graphics;
	private int explosionXCoordinate;
	public int timeMilliSeconds;
	public boolean isEffectOn;
	public Image explosionImage;
	public void setGraphics(Graphics g) {
		graphics = g;
	}
	// 미사일의 현재 x좌표 반환
	public int getExplosionX() {
		return explosionXCoordinate;
	}
	// 폭발 이미지 반환
	public Image getExplosionImage() {
		return explosionImage;
	}
	public UnitExplosion(int deadUnitXCoordinate) {
		super();
		explosionImage = new ImageIcon("images/skill/unit_explosion.gif").getImage();
		// 죽은유닛의 위치 = 폭발지점
		explosionXCoordinate = deadUnitXCoordinate;
		// 2초간 실행
		timeMilliSeconds = 450;
		isEffectOn = false;
	}
	
	public void screenDraw(Graphics g) {
		if(isEffectOn) {
//			g.drawImage(image,missileXCoordinate,340,null);
		}
	}
	public void run(){
		try {
			isEffectOn = true;
			Thread.sleep(timeMilliSeconds);
			isEffectOn = false;
			// 오류 방지를 위한 재 초기화
			explosionXCoordinate = -1;
		} catch (Exception e) {
		}
		
	}
}
