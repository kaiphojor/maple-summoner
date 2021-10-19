package shopitem;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//광전사 스킬 : 체력을 희생해서 능력치를 모두 상승시킨다.
public class DeathStrikeSkill extends Skill{
	public DeathStrikeSkill(Image image){
		super(image);
		itemCost = 900;
		itemIndex = 5;
		name = "데스스트라이크";
		coolDown = 10; // 스킬의 재사용 대기시간
		duration = 5; // 스킬의 지속시간, 단발성일수도

		itemDescription = "죽음의 탄환 소환";
	}

}
