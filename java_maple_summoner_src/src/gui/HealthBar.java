package gui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class HealthBar{
	// health ui에 필요한 배경, 전경, 체력바를 포함한다
	public Image backgroundBar;
	public Image foregroundBar;
	public Image bloodBar;
	public static int healthWidth; // 체력바를 조절하기 위한 너비길이 
	public static int healthHeight; // 체력바의 높이길이 
	public static final double scaleX = 0.3;
	public static final double scaleY = 0.35;
	// 초기화
	public HealthBar(Toolkit toolkit){
		// ,int imageWidth,int imageHeight
		// imageIcon에서 그림의 width, height를 뽑아낸다.
		ImageIcon bloodBarIcon = new ImageIcon("images/enemy_health_bar/blood_cropped.png");
		bloodBar = bloodBarIcon.getImage();
//		bloodBar = toolkit.getImage("images/enemy_health_bar/blood_cropped.png");
		// scale 조정을 한다.
		// 그림을 불러온 다음, 지정해둔 가로 세로 비율에 따라서 축소한다.
		backgroundBar = toolkit.getImage("images/enemy_health_bar/back_cropped.png");		
		foregroundBar = toolkit.getImage("images/enemy_health_bar/background_2_cropped.png");				
		healthWidth = (int)(232 * scaleX);
		healthHeight = (int)(32 * scaleY);	
		
//		healthWidth = imageWidth;
//		healthHeight = imageHeight;
		
//		backgroundBar = backgroundBar.getScaledInstance((int)(232 * scaleX),(int)(32 * scaleY), Image.SCALE_SMOOTH);
//		foregroundBar = foregroundBar.getScaledInstance((int)(232 * scaleX),(int)(32 * scaleY), Image.SCALE_SMOOTH);
		backgroundBar = backgroundBar.getScaledInstance(healthWidth,healthHeight, Image.SCALE_SMOOTH);
		foregroundBar = foregroundBar.getScaledInstance(healthWidth,healthHeight, Image.SCALE_SMOOTH);
//		bloodBar = bloodBar.getScaledInstance((int)(232 * scaleX),(int)(32 * scaleY), Image.SCALE_SMOOTH);		
		bloodBar = bloodBar.getScaledInstance(healthWidth,healthHeight, Image.SCALE_SMOOTH);		
	}
	// 남은 체력에 비례하는 막대의 가로길이를 반환  
	public static int barWidth(int currentHP,int totalHP) {
		return (int)(healthWidth * (currentHP /(double)totalHP));
	}
}
