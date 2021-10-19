package basic;

import java.awt.Image;

/*
 * 최상위 클래스
 * 그림으로 표현할 수 있는 하나하나의 낱개
 * 게임의 모든 요소는 그림을 포함한다
 */
public class Entity {
	public Image imageSource;
	public int imageWidth;
	public int imageHeight;
	public String name;
	public Entity(Image image) {
		this.imageSource = image;
		imageWidth = image.getWidth(null);
		imageHeight = image.getHeight(null);
	}
}
