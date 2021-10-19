package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class DeathStrike extends Thread{
//	private Image image = new ImageIcon("images/skill/explosion_missile.png").getImage();
	private Graphics graphics;
	private int missileXCoordinate;

	public boolean isSkillOn;
	public void setGraphics(Graphics g) {
		graphics = g;
	}
	// 미사일의 현재 x좌표 반환
	public int getMissileX() {
		return missileXCoordinate;
	}
	public DeathStrike(Toolkit tk) {
		super();
//		tk.getImage("images/skill/force_shield.png");
		isSkillOn = false;
		missileXCoordinate = -700;
	}
	
	public void screenDraw(Graphics g) {
		if(isSkillOn) {
//			g.drawImage(image,missileXCoordinate,340,null);
		}
	}
	public void run(){
		try {
			isSkillOn = true;
			while(missileXCoordinate<750) {
				Thread.sleep(25);
				missileXCoordinate += 30;
			}
			isSkillOn = false;
			// 오류 방지를 위한 재 초기화
			missileXCoordinate = -700;
		} catch (Exception e) {
		}
		
	}
}
