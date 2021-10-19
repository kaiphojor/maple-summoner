package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class ForceField extends Thread{
	private Image image = new ImageIcon().getImage();
	private Graphics graphics;
	private int timeMilliSeconds;
	public int isSkillOn;
	public int time;
	
	public void setGraphics(Graphics g) {
		graphics = g;
	}
	public void setTime(int timeSec) {
		timeMilliSeconds = timeSec * 1000;
	}
	public ForceField(Toolkit tk) {
		super();
		tk.getImage("images/skill/force_shield.png");
		isSkillOn = 0;
		time = 0;
	}
	public ForceField(Toolkit tk,int timeSec) {
		super();
		tk.getImage("images/skill/force_shield.png");
		this.isSkillOn = 0;
		timeMilliSeconds = timeSec * 1000;
	}
	public void drawSkill(Graphics g) {
		if(isSkillOn > 0) {
			g.drawImage(image,-10,450,null);			
		}
	}
	public void offSkill() {
//		System.out.println("off isSkillOn : "+this.isSkillOn);
//		System.out.println("off time : "+ this.time);
		if(this.isSkillOn == 1 && this.time>=4980) {
			this.interrupt();
//			this.isSkillOn = 0;
//			System.out.println("off isSkillOn : "+this.isSkillOn);
//			System.out.println("off time : "+ this.time);
		}
	}
	public void run(){
		try {
			isSkillOn = 1;
			while(time < timeMilliSeconds) {
				Thread.sleep(50);
				time += 50;
//				System.out.println("skillThread time : "+time);
			}
			isSkillOn = 0;
//			System.out.println("skillThread off : "+isSkillOn);
//			System.out.println("skillThread on :" + isSkillOn);
//			while(timeMilliSeconds> 0) {
//				Thread.sleep(50);
//				timeMilliSeconds -= 50;
//			}
//			int time = 0;
//			while(time < timeMilliSeconds) {
//				Thread.sleep(timeMilliSeconds);
//				time= timeMilliSeconds+100;
//				System.out.println("time : "+time);
//				System.out.println("shield skill : " +isSkillOn);
//				if(timeMilliSeconds-100 <= time) {
//					System.out.println("unlocked");
//					isSkillOn = 0;
//				}
//			}
//			synchronized(this) {
//				(time < 4989) {
//					Thread.sleep(25);
//					time += 25;
//					if(time > 4980) {
//						Thread.sleep(25);
//						offSkill();
//						isSkillOn=0;	
//						System.out.println("skillThread time : "+time);
//						System.out.println("skillThread off : "+isSkillOn);
//					}
//				}
			
//			}


//			Thread.sleep(timeMilliSeconds);
//			Thread.sleep(4000);
		} catch (Exception e) {
		}finally {
			isSkillOn=0;
		}
		
	}
}
