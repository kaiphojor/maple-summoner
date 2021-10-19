package gui;

import javax.swing.JLabel;
import javax.swing.JTextArea;
// 엔딩화면 출력 스레드
public class EndingThread {
	// 감사의 말 label
	JLabel endingLabel;
	// credit
	JTextArea creditLine;
	public boolean isTyped;
	
	public EndingThread(JLabel endingLabel,JTextArea creditLine) {
		super();
		this.endingLabel = endingLabel;
		this.creditLine = creditLine;
		this.isTyped = false;
	}

	// 타자기 효과 스레드
	public void typeWriterEffect() {
		this.isTyped = true;
		// 엔딩 문구
		String storyString = "Thank you for playing!";
		String creditString = "제작  -  박지후\r\n제작지원  -  TEAM NOVA\r\n누네띠네  -  TEAM NOVA\r\n이미지  -  메이플스토리\r\n음악  -  메이플스토리\r\n     ...... and you";
		char[] storyLine = storyString.toCharArray();
		String bufferString = "";

		try {
			for(int i=0; i< storyString.length(); i++) {
				Thread.sleep(200);
				bufferString = storyString.substring(0,i+1);
				endingLabel.setText(bufferString);				
			}
			for(int i=0; i< creditString.length(); i++) {
				Thread.sleep(100);
				bufferString = creditString.substring(0,i+1);
				if(bufferString.charAt(bufferString.length()-1) == '\n') {
					Thread.sleep(1000);
				}
				creditLine.setText(bufferString);				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
