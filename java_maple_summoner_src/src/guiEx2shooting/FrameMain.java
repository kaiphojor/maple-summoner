package guiEx2shooting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import basic.Shop;
import gui.DeathStrike;
import gui.EndingThread;
import gui.ForceField;
import gui.HealthBar;
import gui.Music;
import gui.UnitExplosion;
import monster.AdvancedEnemyMonster;
import monster.EKeyMonster;
import monster.EnemyMonster;
import monster.IntermediateEnemyMonster;
import monster.JuniorEnemyMonster;
import monster.PlayerMonster;
import monster.QKeyMonster;
import monster.WKeyMonster;
import shopitem.DeathStrikeSkill;
import shopitem.ForceShield;
import shopitem.Portion;
import shopitem.PowerUp;
import shopitem.SpeedUp;
import shopitem.UnitHealthUp;
import summoner.EnemySummoner;
import summoner.PlayerSummoner;

public class FrameMain extends JFrame implements KeyListener, Runnable{
	public static void main(String[] ar) {
		// 메인 클래스 .나중을 위해 단순 프레임 생성은 다른 클래스에서 만들어 불러 온다
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	JFrame frame = new FrameMain();
				frame.setResizable(false); 
				frame.setVisible(true);
            }
        });
	}
	
	
	
	// 프레임 생성을 위한 JFrame 상속
	// 키보드 이벤트 처리를 위한 KeyListener를 상속
	// 스레드를 돌리기 위한 Runnable 상속

	// 캐릭터의 좌표 변수
	int x, y; 
	int frameWidth; 
	int frameHeight;
	// key flag
	boolean keyUp = false; 
	boolean keyDown = false;
	boolean keyLeft = false;
	boolean keyRight = false;
	boolean keySpace = false;
	boolean keyEnter = false; 
	boolean keyQ = false;
	boolean keyW = false;
	boolean keyE = false;
	boolean keyR = false;
	boolean keyT = false;
	boolean key1 = false;
	// cheat key
	boolean keyVictoryCheat = false;
	boolean keyMoneyCheat = false;

	// 각종 타이밍 조절을 위한 루프 카운터
	int count; 

	// 적 이미지의 크기값을 받을 변수
	int enemyWidth, enemyHeight; 
	// 아군 이미지의 크기값을 받을 변수
	int allyWidth, allyHeight; 

	Thread thread; // 스레드 생성

	// 이미지를 불러오기 위한 툴킷
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	// 각 요소의 이미지 선언
	Image summonerImage; 
	Image backgroundImage; 
	Image enemySummonerImage; 

	Image allyMonsterImage; 
	Image spectorImage; 
	Image juniorEnemyMonsterImage;
	Image intermediateEnemyMonsterImage;
	Image advancedEnemyMonsterImage;
	
	// 승리 / 패배 이미지 
	Image victoryImage; 
	Image defeatImage; 
	// 소환가능한 유닛을 표시하는 키보드 키 이미지 
	// 해당 키보드 키를 누르면 유닛이 소환됨
	Image keyboardKeyImage;
	Image keyboardKeyQImage;
	Image keyboardKeyWImage; 
	Image keyboardKeyEImage;
	Image keyboardKeyRImage;
	Image keyboardKeyTImage;
	Image keyboardKey1Image;
	
	// 키보드 키 이미지 안에 들어갈 유닛, 소모품, 스킬 이미지
	Image keyboard1PortionImage; 
	Image keyboardQUnitImage;
	Image keyboardWUnitImage;
	Image keyboardEUnitImage;
	Image keyboardRSkillImage;
	Image keyboardTSkillImage;

	// 상점 아이템 이미지
	Image healthPortionImage;
	Image forceShieldImage;
	Image powerUpImage;
	Image unitHealthUpImage;
	Image deathStrikeImage;
	Image speedUpImage;

	// 키보드 키 이미지 안에 들어갈 썸네일
	Image enemyThumbnail;
	Image juniorEnemyThumbnail;
	Image intermediateEnemyThumbnail;
	Image advancedEnemyThumbnail;
	Image healthPortionThumbnail;
	Image forceShieldThumbnail;
	Image powerUpThumbnail;
	Image unitHealthUpThumbnail;
	Image deathStrikeThumbnail;
	Image speedUpThumbnail;
	
	// 로고 이미지
	Image playerLogoImage; 
	Image enemyLogoImage; 

	HealthBar healthUI;

	// 다수의 아군 소환물을 관리하기 위한 배열
	ArrayList<PlayerMonster> allyList = new ArrayList<>();
	ArrayList<EnemyMonster> enemyList = new ArrayList<>();
	PlayerMonster ally; 
	EnemyMonster enemyMonster; 
	EnemySummoner enemySummoner;
	PlayerSummoner summoner;

	// 더블 버퍼링용 이미지 변수, 그래픽
	Image bufferImage; 
	Graphics bufferGraphics; 
	
	// 스킬 / 스킬 이펙트 이미지
	Image shieldImage;
	Image explosionImage;
	Image unitExplosionImage;
	// 보스 이미지
	static Image secondStageEnemyImage = new ImageIcon("images/enemy/second_enemy.png").getImage();
	static Image lastStageEnemyImage = new ImageIcon("images/enemy/black_wizard_resized.gif").getImage();
	
	// 유닛 폭발을 제어하는 스레드 리스트
	ArrayList<UnitExplosion> unitExplosionList;
	
	// 키에 쿨타임 효과 적용하는 변수
	int keyQCoolDown = 0;
	int keyWCoolDown = 0;
	int keyECoolDown = 0;
	int keyRCoolDown = 0;
	int keyTCoolDown = 0;
	int key1CoolDown = 0;
	// 게임 중인지 쇼핑중인지 구분 하는 변수
	boolean isGameScreen; 
	boolean isShopScreen;
	boolean isEndingScreen;
	boolean isMainScreen;
	
	// 전투가 끝나거나 누가 이겼는지 여부를 담는 변수
	boolean isBattleEnd;
	boolean isPlayerWin;
	// 스킬, 음악, 상점 객체변수
	ForceField shieldSkill;
	DeathStrike deathStrikeSkill;
	Music musicPlayer;
	Shop shop;
	// 유닛을 표시하기 위해 설정한 바닥 부분의 Y좌표ㄴ
	int mapYBaseLine = 541;
	// stage
	int stage = 1;
	EnemySummoner enemySummoners[];
	
//	JButton testButton = new JButton("테스트 버튼");
	private JPanel contentPane;
	// 현재 돈상태 
	JLabel starBalance;
	// 엔딩 장면 연출 UI, thread
	JLabel endingLabel;
	JLabel creditLabel;
	JTextArea creditLine;
	EndingThread endingEffect;
	// 방어막 효과
	Music shieldSound;
	Music deathStrikeSound;
	// 게임 오버 여부
	boolean isGameOver = false;

	FrameMain() { 
		// 프레임에 들어갈 요소 초기화
		init(); 
		// gui thread 처리하는 부분
		start(); 
	}
	// 요소 초기화 메소드
	public void init() { 
		// 캐릭터의 최초 좌표.
		x = 10; 
		y = 470;
		frameWidth = 800; 
		frameHeight = 600; 
		setLayout(null);
		// 프레임 이름
		setTitle("단풍소환사"); 
		
		// 프레임이 윈도우에 표시될때 위치를 세팅하기 위해
		// 현재 모니터의 해상도 값을 받아온다
		// 프레임의 크기를 위에 값에서 가져와 설정
		// 프레임을 모니터 화면 정중앙에 배치시키기 위해 좌표 값을 계산
		setSize(frameWidth, frameHeight); 
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int f_xpos = (int) (screen.getWidth() / 2 - frameWidth / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - frameHeight / 2);
		// 프레임을 화면에 배치
		setLocation(f_xpos, f_ypos);
		// x 누르면 프로그램 종료
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 전투 종료,승패여부 변수의 초기화
		isBattleEnd = false;
		isPlayerWin = false;
		isGameScreen = true;
		
		/*
		 * for test
		 */		
		isPlayerWin = true;
		// 체력 바 UI
		healthUI = new HealthBar(toolkit);
		
		// 적 이미지의 w(넓이)값, h(높이) 값을 계산
		// 해당 메소드는 아래에 이미지 크기값 계산용으로 추가된 메소드
		enemyWidth = ImageWidthValue("images/blue_snail.png");
		enemyHeight = ImageHeightValue("images/blue_snail.png");
		// 아군 소환물 이미지의 w(넓이)값, h(높이) 값
		allyWidth = ImageWidthValue("images/jr_seal.png");
		allyHeight = ImageHeightValue("images/jr_seal.png");

		// 이미지 초기화
		backgroundImage = toolkit.getImage("images/enchanted_forest.png"); 
		backgroundImage = backgroundImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
		summonerImage = toolkit.getImage("images/ally/ally_character.png");
		// 키보드 키, 썸네일 이미지
		keyboardQUnitImage = toolkit.getImage("images/ally/star_pixie_thumbnail.png");
		keyboardQUnitImage = keyboardQUnitImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		keyboardWUnitImage = toolkit.getImage("images/ally/king_slime_thumbnail.png");
		keyboardWUnitImage = keyboardWUnitImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		keyboardEUnitImage = toolkit.getImage("images/ally/dragon_thumbnail.png"); 
		keyboardEUnitImage = keyboardEUnitImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		juniorEnemyThumbnail = toolkit.getImage("images/monster/orange_mushroom_thumbnail_30.png");
		intermediateEnemyThumbnail = toolkit.getImage("images/monster/crimson_barlog_thumbnail_30.png");
		advancedEnemyThumbnail = toolkit.getImage("images/monster/spector_thumbnail_30.png");

		// 적 소환물의 이미지
		spectorImage = toolkit.getImage("images/monster/blue_snail.png");
		spectorImage = toolkit.getImage("images/monster/spector.gif");
		juniorEnemyMonsterImage = toolkit.getImage("images/monster/orange_mushroom_resized.gif");
		spectorImage = toolkit.getImage("images/monster/orange_mushroom_resized.gif");
		intermediateEnemyMonsterImage = toolkit.getImage("images/monster/crimson_balog.gif");
		advancedEnemyMonsterImage = toolkit.getImage("images/monster/spector.gif");
		enemySummonerImage = toolkit.getImage("images/enemy/mermaid_resized.png"); 

		// 로고 이미지 가져오기 & 크기 조절
		playerLogoImage = toolkit.getImage("images/player_logo_transparent.png"); 
		playerLogoImage = playerLogoImage.getScaledInstance(24 * 5, 10 * 5, Image.SCALE_FAST);		
		enemyLogoImage = toolkit.getImage("images/enemy_logo_transparent.png"); 
		enemyLogoImage = enemyLogoImage.getScaledInstance(24 * 5, 10 * 5, Image.SCALE_FAST);
		victoryImage = toolkit.getImage("images/battle_end/victory_transparent.png"); 
		defeatImage = toolkit.getImage("images/battle_end/defeat_transparent.png"); 
		
		// 키보드 키, q,w,e,r,t,1번 키 초기화 및 resize
		keyboardKeyImage = toolkit.getImage("images/key_ui/blank_key.png");
		keyboardKeyImage = keyboardKeyImage.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		keyboardKeyQImage = toolkit.getImage("images/key_ui/key_q.png");
		keyboardKeyQImage = keyboardKeyQImage.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		keyboardKeyWImage = toolkit.getImage("images/key_ui/key_w.png");
		keyboardKeyWImage = keyboardKeyWImage.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		keyboardKeyEImage = toolkit.getImage("images/key_ui/key_e.png");
		keyboardKeyEImage = keyboardKeyEImage.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		keyboardKeyRImage = toolkit.getImage("images/key_ui/key_r.png");
		keyboardKeyRImage = keyboardKeyRImage.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		keyboardKeyTImage = toolkit.getImage("images/key_ui/key_t.png");
		keyboardKeyTImage = keyboardKeyTImage.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		keyboardKey1Image = toolkit.getImage("images/key_ui/key_1.png");
		keyboardKey1Image = keyboardKey1Image.getScaledInstance(50, 53, Image.SCALE_SMOOTH);
		
		// 상점 아이템 이미지
		healthPortionImage = toolkit.getImage("images/shop_item/red_portion.png");
		healthPortionImage = healthPortionImage.getScaledInstance(60,60,Image.SCALE_AREA_AVERAGING);		
		forceShieldImage = new ImageIcon("images/shop_item/force_shield.gif").getImage();
		forceShieldImage = forceShieldImage.getScaledInstance(60,60,Image.SCALE_AREA_AVERAGING);
		powerUpImage = new ImageIcon("images/shop_item/power_up.gif").getImage();
		powerUpImage = powerUpImage.getScaledInstance(60,60,Image.SCALE_AREA_AVERAGING);
		unitHealthUpImage = new ImageIcon("images/shop_item/unit_health_up.gif").getImage();
		unitHealthUpImage = unitHealthUpImage.getScaledInstance(60,60,Image.SCALE_AREA_AVERAGING);
		deathStrikeImage = new ImageIcon("images/shop_item/deathStrikeImage.gif").getImage();
		deathStrikeImage = deathStrikeImage.getScaledInstance(60,60,Image.SCALE_AREA_AVERAGING);
		deathStrikeThumbnail = deathStrikeImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		speedUpImage = new ImageIcon("images/shop_item/speed_up.gif").getImage();
		speedUpImage = speedUpImage.getScaledInstance(60,60,Image.SCALE_AREA_AVERAGING);
		
		// 상점 아이템 thumbnail 불러오기 & 크기 조절  
		enemyThumbnail = spectorImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		healthPortionThumbnail = healthPortionImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		powerUpThumbnail = powerUpImage.getScaledInstance(25,25,Image.SCALE_AREA_AVERAGING);
		unitHealthUpThumbnail = unitHealthUpImage.getScaledInstance(25,25,Image.SCALE_AREA_AVERAGING);
		speedUpThumbnail = speedUpImage.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
		forceShieldThumbnail = forceShieldImage.getScaledInstance(30,30,Image.SCALE_AREA_AVERAGING);
		
		// 적 소환사 목록 및 아군 소환사의 생성
		enemySummoners = new EnemySummoner[] {
			new EnemySummoner(enemySummonerImage, 100, new Point(frameWidth - 80,mapYBaseLine-86), 100),
			new EnemySummoner(secondStageEnemyImage, 100, new Point(frameWidth - 80, mapYBaseLine-99), 100),
			new EnemySummoner(lastStageEnemyImage, 100, new Point(frameWidth - 80, mapYBaseLine-90), 100)
		};
		enemySummoner = new EnemySummoner(enemySummonerImage, 100, new Point(frameWidth - 80, y), 100);
		summoner = new PlayerSummoner(summonerImage, 100, new Point(x, y), 1000);
		
		// 상점 초기화
		shop = new Shop(summonerImage);
		shop.addItem(new Portion(healthPortionImage));
		shop.addItem(new SpeedUp(speedUpImage));
		shop.addItem(new UnitHealthUp(unitHealthUpImage));
		shop.addItem(new PowerUp(powerUpImage));
		shop.addItem(new ForceShield(forceShieldImage));
		shop.addItem(new DeathStrikeSkill(deathStrikeImage));
		
		// 스킬 이미지
		shieldImage = toolkit.getImage("images/skill/force_shield.png");
		shieldImage = shieldImage.getScaledInstance(100,100,Image.SCALE_AREA_AVERAGING);
		explosionImage = new ImageIcon("images/skill/explosion_missile.gif").getImage();
		unitExplosionImage = new ImageIcon("images/skill/unit_explosion.gif").getImage();
		// 스킬 객체 초기화
		shieldSkill = new ForceField(toolkit,5);
		deathStrikeSkill = new DeathStrike(toolkit);		
		
		// 효과, 효과음 내기위한 변수 초기화 
		unitExplosionList = new ArrayList<>();
		endingLabel = new JLabel();
		creditLine = new JTextArea();
		endingEffect = new EndingThread(endingLabel,creditLine);
		musicPlayer = new Music("DragonNest.mp3",true);
		musicPlayer.start();
	}

    public void paintComponent(Graphics g) {
        super.paintComponents(g);       
    }  
	// JFrame에 그림을 그리는 메소드
	@Override
	public void paint(Graphics g) {
		// 배경, 아군, 적군, ui, 전투 종료시 뜨는 ui를 그려준다
		if(isGameScreen) {
			// 더블버퍼링 버퍼 크기를 화면 크기와 같게 설정
			bufferImage = createImage(frameWidth, frameHeight);
			// 버퍼의 그래픽 객체를 얻기
			bufferGraphics = bufferImage.getGraphics(); 
			// 그래픽 버퍼에 요소들 그리기
			drawBackground();
			drawSummoner();
			drawAlly(); 
			drawEnemy(); 
			drawEnemySummoner(); 
			drawKeyboardKeyUI();
			drawEffect();
			drawUnitExplosion();
			// 전투 종료 시 표시할 승리/패배 이미지
			drawEndOfBattle();
			// 요소를 다 그린 그래픽 버퍼를 화면에 출력한다.
			g.drawImage(bufferImage, 0, 0, this);
		}else {
			// 버튼 그려주는 부분
		}		
	}
	//repaint() -> update()를 호출하기 위한 메소드
	@Override 
	public void repaint() {
		super.repaint();
	}
	// update 메소드는 ->paint() 메소드를 호출한다.
	@Override 
	public void update(Graphics g) {
		super.update(g);
	}
	// 스킬 효과 그리는 메소드
	public void drawEffect() {
		// 효과 적용 위한 지연
		try {
			Thread.sleep(1);
		} catch (Exception e) {
		}
		// 보호막 스킬
		if(shieldSkill.isSkillOn == 1) {
			bufferGraphics.drawImage(shieldImage,-10,450,this);
		}
		// 광역기 스킬
		if(deathStrikeSkill.isSkillOn) {
			bufferGraphics.drawImage(explosionImage,deathStrikeSkill.getMissileX(),340,this);
		}
	}
	
	// 배경을 그려주는 메소드
	public void drawBackground() {
		bufferGraphics.drawImage(backgroundImage, 0, 0, this);
		// 로고 이미지
		if(isGameScreen) {
			bufferGraphics.drawImage(playerLogoImage,5,160,this);
			bufferGraphics.drawImage(enemyLogoImage,670,160,this);
		}else {
		}
	}
	
	// 내 캐릭터(소환사)를 그린다.
	public void drawSummoner() { 
		// 캐릭터 체력이 0이 되면 전투 종료
		if (summoner.healthPoint > 0) {
			bufferGraphics.drawImage(summonerImage, x, y, this);
		}else {
			isBattleEnd = true;
			isPlayerWin = false;
		}
		// 소환사의 체력을 그려주는 부분
		// 현재 체력 비율에 맞는 체력 막대 그림을 계산
		Image baseHealthImage = PlayerSummoner.getBaseHealthBar(toolkit, summoner.healthPoint, summoner.totalHealth);
		bufferGraphics.drawImage(baseHealthImage, 0, 10, this);
		// 계속 차오르는 자원 바를 표현한다.
		// 자원바의 전체 길이
		// 자원바를 그려주는 부분(전체 자원 막대, 현재 자원 막대, 문구)
		int regenBarWidth = 380; 
		bufferGraphics.setColor(Color.CYAN);
		bufferGraphics.draw3DRect(10, 120, regenBarWidth, 30, true);
		bufferGraphics.setColor(Color.LIGHT_GRAY);
		bufferGraphics.fill3DRect(10, 120, regenBarWidth, 30, true);
		bufferGraphics.setColor(Color.YELLOW);
		bufferGraphics.fill3DRect(10, 120, regenBarWidth * summoner.resource / summoner.totalResource, 30, true);
		bufferGraphics.setColor(Color.DARK_GRAY);
		bufferGraphics.drawString("소환력 : " + summoner.resource + " / " + summoner.totalResource, 160, 140);
	}

	// 적 소환사의 체력이 남았을 때 적 소환사를 그린다
	public void drawEnemySummoner() {
		if (enemySummoner.healthPoint > 0) {
			bufferGraphics.drawImage(enemySummoner.imageSource, enemySummoner.coordinate.x, enemySummoner.coordinate.y, this);
		// 적 캐릭터 체력이 0이 되면 전투 종료
		}else {
			isBattleEnd = true;
			isPlayerWin = true;
		}
		// 소환사의 체력을 그려주는 부분
		// 현재 체력 비율에 맞는 체력 막대 그림을 가져온다.
		 Image enemyBaseHealthImage = enemySummoner.getBaseHealthBar(toolkit, enemySummoner.healthPoint, enemySummoner.totalHealth);
		// 소환사의 체력을 계산
		bufferGraphics.drawImage(enemyBaseHealthImage, 390, 10, this);
		int regenBarWidth = 380; 
		// 적 자원 막대 x 좌표(좌상단 기준)
		int enemyResourceBarX = 400; 
		// 적 자원 막대 y 좌표(좌상단 기준)
		int enemyResourceBarY = 120;
		// 체력바 그리기
		bufferGraphics.setColor(Color.CYAN);
		bufferGraphics.draw3DRect(enemyResourceBarX, enemyResourceBarY, regenBarWidth, 30, true);
		bufferGraphics.setColor(Color.LIGHT_GRAY);		
		bufferGraphics.fill3DRect(enemyResourceBarX, enemyResourceBarY, regenBarWidth, 30, true);
		bufferGraphics.setColor(Color.ORANGE);
		bufferGraphics.fill3DRect(enemyResourceBarX + regenBarWidth - regenBarWidth * enemySummoner.resource / enemySummoner.totalResource, enemyResourceBarY,regenBarWidth * enemySummoner.resource / enemySummoner.totalResource,30, true);
		bufferGraphics.setColor(Color.DARK_GRAY);
		bufferGraphics.drawString("적 소환력 : " + enemySummoner.resource + " / " + enemySummoner.totalResource,  160 + 390, 140);
	}

	// 아군 그리는 메소드
	public void drawAlly() { 
		for (int i = 0; i < allyList.size(); ++i) {
			// 아군 존재 유무를 확인한다.
			ally = (PlayerMonster) (allyList.get(i));
			// 아군 위치값을 확인
			bufferGraphics.drawImage(ally.imageSource, ally.coordinate.x, ally.coordinate.y, this);
			// 현재 좌표에 아군 소환물 그리기.
			// 이미지 크기를 감안한 아군 소환물 발사 좌표는 수정됨.
			// 그려진 아군 소환물을 정해진 숫자만큼 이동시키기
			// 아군 소환물이 화면 밖으로 나가면 아군 소환물 지우기
			if (ally.coordinate.x > frameWidth) { 
				allyList.remove(i); 
			}
			// 아군의 체력 막대 그리기. 남은 체력/ 전체체력 비율을 현재체력 길이에 적용한다.
			if(ally.keyCode == 'w') {
				bufferGraphics.drawImage(healthUI.backgroundBar, ally.coordinate.x+ally.imageWidth/2-healthUI.healthWidth/2+30, ally.coordinate.y + 70, this);
				bufferGraphics.drawImage(healthUI.foregroundBar, ally.coordinate.x+ally.imageWidth/2-healthUI.healthWidth/2+30, ally.coordinate.y + 70, this);
				bufferGraphics.drawImage(healthUI.bloodBar, ally.coordinate.x+ally.imageWidth/2-healthUI.healthWidth/2+30, ally.coordinate.y+ 70,
						healthUI.barWidth(ally.healthPoint, ally.totalHealth), healthUI.healthHeight, this);				
			// 아군의 체력 막대 그리기. 남은 체력/ 전체체력 비율을 현재체력 길이에 적용한다.
			}else {
				bufferGraphics.drawImage(healthUI.backgroundBar, ally.coordinate.x+ally.imageWidth/2-healthUI.healthWidth/2, ally.coordinate.y -20, this);
				bufferGraphics.drawImage(healthUI.foregroundBar, ally.coordinate.x+ally.imageWidth/2-healthUI.healthWidth/2, ally.coordinate.y -20, this);
				bufferGraphics.drawImage(healthUI.bloodBar, ally.coordinate.x+ally.imageWidth/2-healthUI.healthWidth/2, ally.coordinate.y-20,
						healthUI.barWidth(ally.healthPoint, ally.totalHealth), healthUI.healthHeight, this);
			}
			
		}
	}

	public void drawEnemy(){
		for (int i = 0; i < enemyList.size(); ++i) {
			// 아군 소환물 존재 유무를 확인한다.
			enemyMonster = (EnemyMonster) (enemyList.get(i));
			// 아군 소환물 위치값을 확인
			bufferGraphics.drawImage(enemyMonster.imageSource, enemyMonster.coordinate.x, enemyMonster.coordinate.y, this);
			// 현재 좌표에 아군 소환물 그리기.
			// 이미지 크기를 감안해서 아군 소환물 발사 좌표는 수정됨.
			// 아군 소환물이 화면 밖으로 나가면  지우기
			if (enemyMonster.coordinate.x > frameWidth || enemyMonster.coordinate.x < 0) { 
				enemyList.remove(i); 
			}
			if(enemyMonster.coordinate.getX() < deathStrikeSkill.getMissileX()+710) {
				summoner.coin += enemyMonster.getCoin();
				// 죽은 적의 위치를 폭발위치로 지정
				unitExplosionList.add(new UnitExplosion((int)enemyMonster.coordinate.getX()));
				// 적 몬스터 객체 삭제				
				enemyList.remove(i); 
				// 폭발위치 스레드 시작
				unitExplosionList.get(unitExplosionList.size()-1).start();
			}else {
				// 발록 소환시 체력바 UI 위치 조정
				if(enemyMonster.requirementStage == 2) {
					bufferGraphics.drawImage(healthUI.backgroundBar, enemyMonster.coordinate.x +enemyMonster.imageWidth/2-healthUI.healthWidth/2, enemyMonster.coordinate.y+70, this);
					bufferGraphics.drawImage(healthUI.foregroundBar, enemyMonster.coordinate.x +enemyMonster.imageWidth/2-healthUI.healthWidth/2, enemyMonster.coordinate.y+70, this);
					bufferGraphics.drawImage(healthUI.bloodBar, enemyMonster.coordinate.x +enemyMonster.imageWidth/2-healthUI.healthWidth/2, enemyMonster.coordinate.y +70,
							healthUI.barWidth(enemyMonster.healthPoint, enemyMonster.totalHealth), healthUI.healthHeight, this);
				}else {
					bufferGraphics.drawImage(healthUI.backgroundBar, enemyMonster.coordinate.x +enemyMonster.imageWidth/2-healthUI.healthWidth/2, enemyMonster.coordinate.y-20, this);
					bufferGraphics.drawImage(healthUI.foregroundBar, enemyMonster.coordinate.x +enemyMonster.imageWidth/2-healthUI.healthWidth/2, enemyMonster.coordinate.y-20, this);
					bufferGraphics.drawImage(healthUI.bloodBar, enemyMonster.coordinate.x +enemyMonster.imageWidth/2-healthUI.healthWidth/2, enemyMonster.coordinate.y -20,
							healthUI.barWidth(enemyMonster.healthPoint, enemyMonster.totalHealth), healthUI.healthHeight, this);
				}
			}
		}
	}
	public void drawUnitExplosion() {
		for(UnitExplosion explosion : unitExplosionList ) {
			if(explosion.isEffectOn) {
				bufferGraphics.drawImage(explosion.getExplosionImage(),explosion.getExplosionX(),420, this);
			}else {
			}
		}
	}
	// 뽑을 수 있는 유닛을 표시하는 키보드 키 UI
	public void drawKeyboardKeyUI() {
		// 키보드 키 이미지를 나란히 정렬하기 위한 x,y 좌표 
		int keyboardKeyAxisY= 550;
		int enemyListAxisX = 630;
		int enemyListAxisY = 220;
		// 플레이어가 소환가능한 유닛 목록 표시
		// 키보드 키 그림
		bufferGraphics.drawImage(keyboardKeyQImage,10,keyboardKeyAxisY, this);
		bufferGraphics.drawImage(keyboardKeyWImage,60,keyboardKeyAxisY, this);
		bufferGraphics.drawImage(keyboardKeyEImage,110,keyboardKeyAxisY, this);
		bufferGraphics.drawImage(keyboardKeyRImage,165,keyboardKeyAxisY, this);
		bufferGraphics.drawImage(keyboardKeyTImage,215,keyboardKeyAxisY, this);
		bufferGraphics.drawImage(keyboardKey1Image,270,keyboardKeyAxisY, this);
		// 키보드 키에 표시되는 소환가능 몬스터 그림
		bufferGraphics.drawImage(keyboardQUnitImage,10+10,keyboardKeyAxisY+10, this);
		if(summoner.isSummonable.get("w")) {
			bufferGraphics.drawImage(keyboardWUnitImage,60+10,keyboardKeyAxisY+10, this);
		}
		if(summoner.isSummonable.get("e")) {
			bufferGraphics.drawImage(keyboardEUnitImage,110+10,keyboardKeyAxisY+10, this);
		}
		// 스킬, 포션을 샀을 때 표시하는 이미지
		if(summoner.inventory[0]>0){
			// 10 자리,1의 자리에 따라 달라지는 글자배치
			int decimalWidth = 0;
			if(summoner.inventory[0]<10) {
				decimalWidth = 7;
			}
			
			bufferGraphics.drawImage(healthPortionThumbnail,270+10,keyboardKeyAxisY+10, this);			
			bufferGraphics.setFont(new Font("Monospace",Font.BOLD,15));
			bufferGraphics.setColor(Color.MAGENTA);
			bufferGraphics.drawString(summoner.inventory[0]+"",  272+27 + decimalWidth, keyboardKeyAxisY+13);
		}
		// 강화여부를 화면상에 보여주는 이미지
		if(shop.isSold(1)) {
			bufferGraphics.drawImage(speedUpThumbnail,10,keyboardKeyAxisY-330, this);
		}
		if(shop.isSold(2)) {
			bufferGraphics.drawImage(unitHealthUpThumbnail,35+10,keyboardKeyAxisY-330, this);
		}
		if(shop.isSold(3)) {
			bufferGraphics.drawImage(powerUpThumbnail,70+10,keyboardKeyAxisY-330, this);
		}
		if(shop.isSold(4)) {
			bufferGraphics.drawImage(forceShieldThumbnail,165+10,keyboardKeyAxisY+10, this);
		}
		if(shop.isSold(5)) {
			bufferGraphics.drawImage(deathStrikeThumbnail,215+10,keyboardKeyAxisY+10, this);
		}
		// 적이 소환가능한 유닛 목록 표시
		bufferGraphics.drawImage(keyboardKeyImage,enemyListAxisX,enemyListAxisY, this);
		bufferGraphics.drawImage(keyboardKeyImage,enemyListAxisX+50,enemyListAxisY, this);
		bufferGraphics.drawImage(keyboardKeyImage,enemyListAxisX+100,enemyListAxisY, this);
		bufferGraphics.drawImage(juniorEnemyThumbnail,enemyListAxisX+10,enemyListAxisY+10, this);
		if(stage>=2) {
			bufferGraphics.drawImage(intermediateEnemyThumbnail,enemyListAxisX+50+10,enemyListAxisY+10, this);			
		}
		if(stage>=3) {
			bufferGraphics.drawImage(advancedEnemyThumbnail,enemyListAxisX+100+10,enemyListAxisY+10, this);			
		}
		
	}
	// 전투가 끝났을 때에만 그리는 메소드
	public void drawEndOfBattle() {
		if(isBattleEnd) {
			// 승리 이미지 그리기
			if(isPlayerWin) {
				bufferGraphics.drawImage(victoryImage,frameWidth/2 - 779/2,frameHeight/2-271/2, this);
			// 패배 이미지 그리기 & 패배 음악 재생
			}else {
				bufferGraphics.drawImage(defeatImage,frameWidth/2 - 779/2,frameHeight/2-271/2, this);
				if(!isGameOver) {
					musicPlayer.close();
					musicPlayer = new Music("sad_violin.mp3",false);
					musicPlayer.start();
					isGameOver = true;					
				}
			}
		}
	}

	// 적의 행동을 처리하는 메소드
	public void enemyProcess() {
		/*
		 * 다음을 모두 만족할 때 적 유닛을 생산한다
		 * count가 100의 배수인지
		 * 전투가 아직 끝나지 않았는지
		 * 유닛을 생산할만큼 자원을 가지고 있는지
		 */
		//적몬
		if (count % 100 == 0 && summoner.healthPoint > 0 && enemySummoner.healthPoint > 0) {
			/*
			 *  1 ~ stage 범위 내 랜덤 선택
			 *  선택된 숫자에 해당하는 몬스터 생성
			 */
			Random random = new Random();
			int monsterNumber = random.nextInt(stage)+1; 
			switch(monsterNumber) {
			case 1: 
				//그림, 체력, 위치(x,y),보상, 사거리,이동속도, 공격력, 소모 자원, 공격지연, 적 소환사를 매개변수로 가진다
				enemyMonster = new JuniorEnemyMonster(juniorEnemyMonsterImage, 5, null, 100, 40, 5, 3, 50, 25,enemySummoner);
				break;
			case 2: 
				enemyMonster = new IntermediateEnemyMonster(intermediateEnemyMonsterImage, 5, null, 100, 40, 5, 3, 50, 25,enemySummoner);
				break;
			case 3: 
				enemyMonster = new AdvancedEnemyMonster(advancedEnemyMonsterImage, 5, null, 100, 40, 5, 3, 50, 25,enemySummoner);
				break;
			}
			// 바닥을 고려한 적 좌표 설정
			enemyMonster.setCoordinate(frameWidth,mapYBaseLine - enemyMonster.imageHeight);
			// 유닛을 소환할만한 자원이 있다면 적을 추가한다.									
			if(enemySummoner.summonUnit(enemyMonster)) { 
				enemyList.add(enemyMonster); 
			}
		}
		// 생성된 적의 행동을 설정
		for (int i = 0; i < enemyList.size(); i++) {
			enemyMonster = (EnemyMonster) enemyList.get(i);
			
			// 적 또한 아군을 탐색한다
			enemyMonster.findEnemy(allyList, summoner);
			if (enemyMonster.isEnemyDetected) {
				// 여기서 cnt 등록. 공격 주기가 어디서 시작될지를 정한다
				if (enemyMonster.attackCnt == -1) {
					enemyMonster.attackCnt = count;
				// 쿨타임이 다 차고 아군 유닛 공격할 차례
				} else if ((count - enemyMonster.attackCnt) % enemyMonster.delay == 0) {
					// 보호막 적용시 피해 무시 및 방어 효과음
					if(enemyMonster.target == -2 && shieldSkill.isSkillOn == 1) {
						shieldSound = new Music("dingding.mp3",false);
						shieldSound.start();
					// 보호막 미적용 시 적 공격
					}else {
						enemyMonster.attackEnemy(allyList, summoner,0);									
					}
				}
			} else {
				// 아군 몬스터 미발견시 적 몬스터 이동
				enemyMonster.move();
			}
		}
	}

	// 아군 몬스터 행동 처리 메소드
	public void allyProcess() { 		
		if(summoner.healthPoint > 0 && enemySummoner.healthPoint > 0) {
			// 실드 스킬의 사용
			if(keyR && keyRCoolDown <= 0) {
				if(summoner.inventory[4]> 0) {
					keyR = false;
					shieldSkill = new ForceField(toolkit,5);
					shieldSkill.start();		

				}
			}
			// 데스 스트라이크 스킬의 사용
			if(keyT && keyTCoolDown <= 0) {
				if(summoner.inventory[5]> 0) {
					keyT = false;
					deathStrikeSound = new Music("horns.mp3",false);
					deathStrikeSound.start();
					deathStrikeSkill = new DeathStrike(toolkit);
					deathStrikeSkill.start();
				}
			}			
			// 키입력시 물약 사용
			if(key1 && key1CoolDown <= 0) {
				if(summoner.inventory[0]> 0) {
					Portion.applyEffect(summoner);
					summoner.inventory[0] -= 1;
					key1CoolDown= 30;
				}
			}

			/*
			 * 다음을 다 만족할 때만 유닛을 생산한다
			 * space key 눌렸을 때
			 * 재사용 대기시간이 다 지났을 때
			 * 전투가 끝나지 않았을 때
			 */
			// q 키 몬스터의 생성
			if (keyQ && keyQCoolDown <= 0 ) {
				ally = new QKeyMonster(new ImageIcon("images/ally/star_pixy_revised_flip_90.gif").getImage(),10,null,0,50,5,3,40,30,summoner); // 좌표 체크하여 넘기기
				ally.setCoordinate(x,mapYBaseLine - ally.imageHeight);
				// 유닛을 생성할만한 자원이 되는지를 판별하고 생산   
				if(summoner.summonUnit(ally)) {
					// resource가 제대로 소모되는지 test
//					System.out.println(base.resource);
//					System.out.println(ally.cost);
					allyList.add(ally); 
					keyQCoolDown = 30;				
				}else {
				}				
			}
			// w key 몬스터의 생성
			if (keyW && keyWCoolDown <= 0 && summoner.isSummonable.get("w")) {
				ally = new WKeyMonster(new ImageIcon("images/ally/slime.gif").getImage(),10,null,0,50,5,3,40,30,summoner); // 좌표 체크하여 넘기기
				ally.setCoordinate(x, mapYBaseLine - ally.imageHeight);
				// 유닛을 생성할만한 자원이 되는지를 판별하고 생산     
				if(summoner.summonUnit(ally)) {
					allyList.add(ally); 
					keyWCoolDown = 30;				
				}else {
				}				
			}
			// e key 몬스터 생성
			if (keyE && keyECoolDown <= 0 && summoner.isSummonable.get("e") ) {
				ally = new EKeyMonster(new ImageIcon("images/ally/dragon_flip.gif").getImage(),10,null,0,50,5,3,40,30,summoner); // 좌표 체크하여 넘기기
				ally.setCoordinate(x, mapYBaseLine - ally.imageHeight-10);
				// 유닛을 생성할만한 자원이 되는지를 판별하고 생산     
				if(summoner.summonUnit(ally)) {
					allyList.add(ally); 
					keyECoolDown = 30;				
				}else {
				}				
			}
		}
		// 각 아군 유닛의 행동을 설정
		for (int i = 0; i < allyList.size(); ++i) {
			ally = (PlayerMonster) allyList.get(i);
			// 적을 탐색한다. 사거리에 적이 들어오면 타겟을 설정한다
			ally.findEnemy(enemyList, enemySummoner);
			// 적을 찾았으면 공격한다
			if (ally.isEnemyDetected) {
				// 여기서 cnt등록
				if (ally.attackCnt == -1) {
					ally.attackCnt = count;
				// delay가 다 지나고 아군 유닛이 공격할 차례가 왔다면
				} else if ((count - ally.attackCnt) % ally.delay == 0) {
					summoner.coin += ally.attackEnemy(enemyList, enemySummoner);
				}
			} else {
				// 적을 못찾았으면 이동한다
				ally.move(summoner);
			}
			if (ally.coordinate.x > frameWidth - 20) {
				allyList.remove(i);
			}
		}
		
		// 승리 치트키 입력
		if(keyVictoryCheat) {
			isPlayerWin = true;
			keyEnter = true;
		}

		
		// 플레이어가 이기고, 엔터 키가 입력되면
		if(isPlayerWin && keyEnter) {
			// 전투가 모두 끝났을 때
			isGameScreen = false;
			// 메인화면 전환 - 미구현
			if(stage == 0) {
				musicPlayer.close();
				musicPlayer = new Music("MuruengHill.mp3",true);
				musicPlayer.start();
				goMain();
			// 엔딩화면 전환
			}else if(stage == 3) {
				musicPlayer.close();
				musicPlayer = new Music("MuruengHill.mp3",true);
				musicPlayer.start();
				isEndingScreen = true;
				goEnding();
			
			// 전투 -> 상점 전환
			}else {
				musicPlayer.close();
				musicPlayer = new Music("Aquarium.mp3",true);
				musicPlayer.start();
				goShop();
			}
			// disable enter key trigger
			keyEnter = false;
		}
		
		
	}
	// 키보드가 눌러졌을때 이벤트 처리하는 곳
	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		// 상하좌우 키 
		case KeyEvent.VK_UP:
			keyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = true;
			break;
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		// 기타 키
		case KeyEvent.VK_SPACE: 
			keySpace = true;
			break;
		// 치트 키 입력
		// enter 입력 시
		case KeyEvent.VK_ENTER: 
			keyEnter = true;
			break;
		// 유닛생산 키 입력
		case KeyEvent.VK_Q: 
			keyQ = true;
			break;
		case KeyEvent.VK_W: 
			keyW = true;
			break;
		case KeyEvent.VK_E: 
			keyE = true;
			break;
		// 스킬 키 입력
		case KeyEvent.VK_R: 
			keyR = true;
			break;
		case KeyEvent.VK_T: 
			keyT = true;
			break;
		// 물약 키입력
		case KeyEvent.VK_1: 
			key1 = true;
			break;
		// 치트 키
		case KeyEvent.VK_F1: 
			keyVictoryCheat = true;
			break;
		case KeyEvent.VK_F2: 
			keyMoneyCheat = true;
			stage = 3;
			keyEnter = true;			
			break;

		}
	}

	// 키보드가 눌러졌다가 때어졌을때 이벤트 처리하는 곳
	// 가상 키 이벤트
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = false;
			break;
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		case KeyEvent.VK_SPACE: 
			keySpace = false;
			break;
		case KeyEvent.VK_ENTER: 
			keyEnter = false;
			break;
		// 유닛생산 키 입력
		case KeyEvent.VK_Q: 
			keyQ = false;
			break;
		case KeyEvent.VK_W: 
			keyW = false;
			break;
		// 스킬 키
		case KeyEvent.VK_E: 
			keyE = false;
			break;
		case KeyEvent.VK_T: 
			keyT = false;
			break;
		// 물약 키
		case KeyEvent.VK_1: 
			key1 = false;
			break;
		// 승리 치트
		case KeyEvent.VK_F1: 
			keyVictoryCheat = false;
			break;
		// 돈 치트
		case KeyEvent.VK_F2: 
			keyMoneyCheat = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	// 방향 키 타이핑 될때 이벤트 처리하는 곳
	public void KeyProcess() {
		if (keyUp == true)
			y -= 5;
		if (keyDown == true)
			y += 5;
		if (keyLeft == true)
			x -= 5;
		if (keyRight == true)
			x += 5;
	}
	// 돈 치트 적용
	public void moneyCheatProcess() {
		if(keyMoneyCheat) {
			summoner.coin = 99999;
			starBalance.setText(summoner.getCoin()+"");
			keyMoneyCheat = false;
		}
	}
	// 이미지 가로길이 반환 메소드
	public static int ImageWidthValue(String file) {
		// 파일을 받아들여 그 파일 값을 계산 하도록 한다.
		int x = 0;
		try {
			File f = new File(file); // 파일을 받음
			BufferedImage bi = ImageIO.read(f);
			// 받을 파일을 이미지로 읽어들임
			// 이미지의 넓이 값을 받음
			x = bi.getWidth(); 
		} catch (Exception e) {
		}
		// 받은 넓이 값을 리턴 
		return x; 
	}
	// 이미지 높이 반환 메소드
	// 이미지 높이 크기 값 계산
	public static int ImageHeightValue(String file) { 
		int y = 0;
		try {
			File f = new File(file);
			BufferedImage bi = ImageIO.read(f);
			y = bi.getHeight();
		} catch (Exception e) {
		}
		return y;
	}
	// 게임이 끝났을 때 화면 전환 메소드
	// 이겼으면 상점으로 들어가고 졌으면 메인화면으로 들어간다
	public void battleEnd() {
		// 플레이어가 이기고, 엔터 키가 입력되면 
		if(isPlayerWin && keyEnter && isGameScreen) {
			isGameScreen = false;
			isShopScreen = true;
			keyEnter = false;
		}else if(!isPlayerWin && keyEnter && isGameScreen) {

//			System.exit(0);
		}
	}
	
	
	// 스레드 시작 부분
	public void start() { // 나중을 위한 기본적인 시작 명령 처리 부분입니다.

		addKeyListener(this); // 키보드 이벤트 실행
		thread = new Thread(this); // 스레드 생성
		thread.start(); // 스레드 실행
//		SwingUtilities.invokeLater(thread);
		
//		repaint(); // 갱신된 x,y값으로 이미지 새로 그리기
	}
	//TODO : run 	
	@Override
	public void run() { // 스레드가 무한 루프될 부분
		try { // 예외옵션 설정으로 에러 방지
			boolean drawFlag = true;
			while (true) { // while 문으로 무한 루프 시키기
					
				// 게임 중일때
				if(isGameScreen) {
					// 아군 소환물 처리 메소드 실행 - 왼쪽에서 오른쪽으로 쏨
					allyProcess(); 
					enemyProcess();					
					repaint(); //log
					Thread.sleep(25); // 20 milli sec 로 스레드 돌리기
				}else {
//					Thread.sleep(25); // 20 milli sec 로 스레드 돌리기
//					repaint();	

				}
				// 전투 할 때, 내 소환사와 적 소환사의 자원을 채운다
				if(isBattleEnd == false) {
					summoner.recoverResource();
					enemySummoner.recoverResource();					
					count++;// 무한 루프 카운터
				}else {
					// 전투가 끝나면 카운터를 0으로 초기화한다
					count = 0;
				}
				while(!isGameScreen) {
//					moneyCheatProcess();
					Thread.sleep(33);
					
					if(isEndingScreen) {
						if(endingEffect.isTyped == false) {
							endingEffect.typeWriterEffect();
						}
					}else {
						if(drawFlag) {
							repaint();
							drawFlag = false;
						}
					}
				}
				// keyCoolDown이 0되기 전까지 1씩 감소하도록 처리
				keyQCoolDown = keyQCoolDown > 0 ? keyQCoolDown - 1 : 0;
				key1CoolDown = key1CoolDown > 0 ? key1CoolDown - 1 : 0;
				keyWCoolDown = keyWCoolDown > 0 ? keyWCoolDown - 1 : 0;
				keyECoolDown = keyECoolDown > 0 ? keyECoolDown - 1 : 0;
				keyRCoolDown = keyRCoolDown > 0 ? keyRCoolDown - 1 : 0;
				keyTCoolDown = keyTCoolDown > 0 ? keyTCoolDown - 1 : 0;
			}
		} catch (Exception e) {
		}
	}
	public void goShop() {
		
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 
		ImageIcon originIcon = new ImageIcon("images/halloween_1280_1024.jpg");
		//ImageIcon에서 Image를 추출
		Image originImg = originIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		Image changedImg= originImg.getScaledInstance(800, 600, Image.SCALE_SMOOTH );
		
		ImageIcon hasteIcon = new ImageIcon("C:\\Users\\grinder\\Desktop\\PAST\\JAVA1\\javagame\\TestWindowBuilder\\images\\icon\\haste.gif");
		//ImageIcon에서 Image를 추출
		Image hasteImg = hasteIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		hasteImg= hasteImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH );
		
		ImageIcon starIcon = new ImageIcon("C:\\Users\\grinder\\Desktop\\PAST\\JAVA1\\javagame\\TestWindowBuilder\\images\\icon\\star.png");
		//ImageIcon에서 Image를 추출
		Image starImg = starIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		starImg= starImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH );
		starBalance = new JLabel(summoner.getCoin()+"");
				
				JPanel shopPanel = new JPanel();
				shopPanel.setBounds(0, 0, 800, 600);
				contentPane.add(shopPanel);
				shopPanel.setLayout(null);
				
				JPanel leftPanel = new JPanel();
				leftPanel.setBounds(0, 0, 200, 567);
				shopPanel.add(leftPanel);
				leftPanel.setBackground(Color.LIGHT_GRAY);
				leftPanel.setLayout(null);
								
				JPanel leftTopPanel = new JPanel();
				leftTopPanel.setToolTipText("툴팁퉅팁툴타툴툴타");
				leftTopPanel.setBounds(14, 12, 172, 170);
				leftPanel.add(leftTopPanel);
				leftTopPanel.setLayout(null);
				
				JLabel itemLabel1 = new JLabel();
				itemLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				itemLabel1.setVerticalAlignment(SwingConstants.TOP);
				itemLabel1.setBounds(25, 12, 120, 18);
				itemLabel1.setText(shop.items[0].name);
				leftTopPanel.add(itemLabel1);
				
				JLabel itemThumbnail1 = new JLabel("");
				itemThumbnail1.setIcon(new ImageIcon(shop.items[0].imageSource));
				itemThumbnail1.setHorizontalAlignment(SwingConstants.CENTER);
				itemThumbnail1.setBounds(54, 35, 60, 60);
				leftTopPanel.add(itemThumbnail1);
				
				JLabel itemDescription1 = new JLabel();
				itemDescription1.setText("수량 "+shop.selectedPortionAmount);
				itemDescription1.setHorizontalAlignment(SwingConstants.CENTER);
				itemDescription1.setBounds(25, 104, 120, 18);
				
				
				leftTopPanel.add(itemDescription1);
				
				JButton buyItemButton1 = new JButton();
				buyItemButton1.setFocusPainted(false);
				buyItemButton1.setText(""+shop.items[0].itemCost);
				buyItemButton1.setIcon(new ImageIcon(starImg));
				buyItemButton1.addMouseListener(new MouseAdapter(){
					@Override public void mousePressed(MouseEvent e) {
						if(summoner.buyItem(shop.items[0].itemIndex, shop.items[0].itemCost, shop.selectedPortionAmount)) {
							shop.selectedPortionAmount = 1;
							itemDescription1.setText("수량 "+shop.selectedPortionAmount);
							buyItemButton1.setText(Integer.toString(shop.items[0].itemCost * shop.selectedPortionAmount));
							starBalance.setText(summoner.getCoin()+"");
							// keyListener에 다시 focus를 가져옴
							buyItemButton1.setFocusable(false);
						}
					}
				});
				
				JButton decreaseAmountButton = new JButton("◀");
				decreaseAmountButton.setFocusPainted(false);
				decreaseAmountButton.addMouseListener(new MouseAdapter(){
					@Override public void mousePressed(MouseEvent e) {
						if(shop.selectedPortionAmount != 1) {
							shop.selectedPortionAmount--;
							itemDescription1.setText("수량 "+shop.selectedPortionAmount);
							buyItemButton1.setText(Integer.toString(shop.items[0].itemCost * shop.selectedPortionAmount));
							decreaseAmountButton.setFocusable(false);
//							revalidate();
//							repaint();
						}
					}
				});
				decreaseAmountButton.setHorizontalAlignment(SwingConstants.TRAILING);
				decreaseAmountButton.setBounds(14, 101, 47, 24);
				decreaseAmountButton.setBorderPainted(false);
				decreaseAmountButton.setContentAreaFilled(false);
				leftTopPanel.add(decreaseAmountButton);
				buyItemButton1.setBounds(34, 131, 105, 27);
				leftTopPanel.add(buyItemButton1);
				
				JButton increaseAmountButton = new JButton("▶");
				increaseAmountButton.setHorizontalAlignment(SwingConstants.LEADING);
				increaseAmountButton.setFocusPainted(false);
				increaseAmountButton.setContentAreaFilled(false);
				increaseAmountButton.setBorderPainted(false);
				increaseAmountButton.setBounds(111, 101, 47, 24);
				increaseAmountButton.addMouseListener(new MouseAdapter(){
					@Override public void mousePressed(MouseEvent e) {
						if(shop.selectedPortionAmount != 9) {
							shop.selectedPortionAmount += 1;
							itemDescription1.setText("수량 "+shop.selectedPortionAmount);
							buyItemButton1.setText(Integer.toString(shop.items[0].itemCost * shop.selectedPortionAmount));
							increaseAmountButton.setFocusable(false);
//							revalidate();
//							repaint();
						}
					}
				});
				leftTopPanel.add(increaseAmountButton);
				
				JPanel leftCenterPanel = new JPanel();
				leftCenterPanel.setLayout(null);
				leftCenterPanel.setBounds(14, 194, 172, 170);
				leftPanel.add(leftCenterPanel);
				
				JLabel itemLabel2 = new JLabel();
				itemLabel2.setVerticalAlignment(SwingConstants.TOP);
				itemLabel2.setHorizontalAlignment(SwingConstants.CENTER);
				itemLabel2.setText(shop.items[1].name);
				itemLabel2.setBounds(25, 12, 120, 18);
				leftCenterPanel.add(itemLabel2);
				
				JLabel itemThumbnail2 = new JLabel("");
				itemThumbnail2.setIcon(new ImageIcon(shop.items[1].imageSource));
				itemThumbnail2.setHorizontalAlignment(SwingConstants.CENTER);
				itemThumbnail2.setBounds(54, 35, 60, 60);
				leftCenterPanel.add(itemThumbnail2);
				
				JLabel itemDescription2 = new JLabel();
				itemDescription2.setText(shop.items[1].itemDescription);
				itemDescription2.setBounds(25, 104, 120, 18);
				itemDescription2.setHorizontalAlignment(SwingConstants.CENTER);
				leftCenterPanel.add(itemDescription2);
				
				JButton buyItemButton2 = new JButton(""+shop.items[1].itemCost);
				buyItemButton2.setIcon(new ImageIcon(starImg));
				buyItemButton2.setFocusPainted(false);

				buyItemButton2.addMouseListener(new MouseAdapter(){
					@Override
					public void mousePressed(MouseEvent e) {
						if(summoner.buyItem(shop.items[1].itemIndex, shop.items[1].itemCost,1)){
							// 중복판매되는 것을 막기 위한 flag
							shop.setSold(1);
							// 대화문구
							starBalance.setText(summoner.getCoin()+"");
							buyItemButton2.setText("판매완료");
							buyItemButton2.setEnabled(false);
							buyItemButton2.setIcon(null);
							buyItemButton2.setFocusable(false);
						}else {
							// 구매실패
						}
					}
					@Override
					public void mouseEntered(MouseEvent e) {
//						System.out.println("버튼 진입 확인");
					}
				});
				if(shop.isSold(1)) {
					buyItemButton2.setEnabled(false);
					buyItemButton2.setText("판매완료");
					buyItemButton2.setIcon(null);
				}
				
				
				buyItemButton2.setBounds(35, 131, 105, 27);
				leftCenterPanel.add(buyItemButton2);
				
				JPanel leftBottomPanel = new JPanel();
				leftBottomPanel.setBounds(14, 376, 172, 170);
				leftPanel.add(leftBottomPanel);
				leftBottomPanel.setLayout(null);
				
				JLabel itemLabel3 = new JLabel();
				itemLabel3.setVerticalAlignment(SwingConstants.TOP);
				itemLabel3.setHorizontalAlignment(SwingConstants.CENTER);
				itemLabel3.setText(shop.items[2].name);
				itemLabel3.setBounds(25, 12, 120, 18);
				leftBottomPanel.add(itemLabel3);
				
				JLabel itemThumbnail3 = new JLabel("");
				itemThumbnail3.setIcon(new ImageIcon(shop.items[2].imageSource));
				itemThumbnail3.setBounds(54, 35, 60, 60);
				leftBottomPanel.add(itemThumbnail3);
				
				JLabel itemDescription3 = new JLabel();
				itemDescription3.setText(shop.items[2].itemDescription);
				itemDescription3.setBounds(25, 104, 120, 18);
				itemDescription3.setHorizontalAlignment(SwingConstants.CENTER);
				leftBottomPanel.add(itemDescription3);
				
				JButton buyItemButton3 = new JButton(""+shop.items[2].itemCost);
				buyItemButton3.setIcon(new ImageIcon(starImg));
				buyItemButton3.setFocusPainted(false);
//				buyItemButton3.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent arg0) {
//						if(summoner.buyItem(shop.items[2].itemIndex, shop.items[2].itemCost,1)){
//							starBalance.setText(summoner.getCoin()+"");
//							buyItemButton3.setText("판매완료");
//							buyItemButton3.setEnabled(false);
//							buyItemButton3.setIcon(null);
//							// 대화문구
//						}else {
//							// 구매실패
//						}
//					}
//				});
				buyItemButton3.addMouseListener(new MouseAdapter(){
					@Override public void mousePressed(MouseEvent e) {
						if(summoner.buyItem(shop.items[2].itemIndex, shop.items[2].itemCost,1)){
							shop.setSold(2);
							starBalance.setText(summoner.getCoin()+"");
							buyItemButton3.setText("판매완료");
							buyItemButton3.setEnabled(false);
							buyItemButton3.setIcon(null);
							buyItemButton3.setFocusable(false);
							// 대화문구
						}else {
							// 구매실패
						}
					}
					@Override
					public void mouseEntered(MouseEvent e) {
//						System.out.println("버튼 진입 확인");
					}
				});
				if(shop.isSold(3)) {
					buyItemButton3.setEnabled(false);
					buyItemButton3.setText("판매완료");
					buyItemButton3.setIcon(null);
				}
				buyItemButton3.setBounds(35, 131, 105, 27);
				leftBottomPanel.add(buyItemButton3);
				
				JPanel rightPanel = new JPanel();
				rightPanel.setBounds(595, 0, 200, 567);
				shopPanel.add(rightPanel);
				rightPanel.setBackground(Color.LIGHT_GRAY);
				rightPanel.setLayout(null);
				
				JPanel rightTopPanel = new JPanel();
				rightTopPanel.setLayout(null);
				rightTopPanel.setBounds(14, 12, 172, 170);
				rightPanel.add(rightTopPanel);
				
				JLabel itemLabel4 = new JLabel();
				itemLabel4.setVerticalAlignment(SwingConstants.TOP);
				itemLabel4.setHorizontalAlignment(SwingConstants.CENTER);
				itemLabel4.setText(shop.items[3].name);
				itemLabel4.setBounds(25, 12, 120, 18);
				rightTopPanel.add(itemLabel4);
				
				JLabel itemThumbnail4 = new JLabel("");
				itemThumbnail4.setBounds(54, 35, 60, 60);
				itemThumbnail4.setIcon(new ImageIcon(shop.items[3].imageSource));
				itemThumbnail4.setHorizontalAlignment(SwingConstants.CENTER);
				rightTopPanel.add(itemThumbnail4);
				
				JLabel itemDescription4 = new JLabel();
				itemDescription4.setText(shop.items[3].itemDescription);
				itemDescription4.setBounds(25, 104,120, 18);
				itemDescription4.setHorizontalAlignment(SwingConstants.CENTER);
				rightTopPanel.add(itemDescription4);
				
				JButton buyItemButton4 = new JButton(""+shop.items[3].itemCost);
				buyItemButton4.setFocusPainted(false);
				buyItemButton4.setIcon(new ImageIcon(starImg));
				buyItemButton4.addMouseListener(new MouseAdapter(){
					@Override 
					public void mousePressed(MouseEvent e) {
						if(summoner.buyItem(shop.items[3].itemIndex, shop.items[3].itemCost,1)){
							shop.setSold(3);
							starBalance.setText(summoner.getCoin()+"");
							buyItemButton4.setText("판매완료");
							buyItemButton4.setEnabled(false);
							buyItemButton4.setIcon(null);
							buyItemButton4.setFocusable(false);
							// 대화문구
						}else {
							// 구매실패
						}
					}
				});
				if(shop.isSold(3)) {
					buyItemButton4.setEnabled(false);
					buyItemButton4.setText("판매완료");
					buyItemButton4.setIcon(null);
				}
				buyItemButton4.setBounds(35, 131, 105, 27);
				rightTopPanel.add(buyItemButton4);
				
				JPanel rightCenterPanel = new JPanel();
				rightCenterPanel.setLayout(null);
				rightCenterPanel.setBounds(14, 194, 172, 170);
				rightPanel.add(rightCenterPanel);
				
				JLabel itemLabel5 = new JLabel();
				itemLabel5.setVerticalAlignment(SwingConstants.TOP);
				itemLabel5.setHorizontalAlignment(SwingConstants.CENTER);
				itemLabel5.setText(shop.items[4].name);
				itemLabel5.setBounds(25, 12, 120, 18);
				rightCenterPanel.add(itemLabel5);
				
				JLabel itemThumbnail5 = new JLabel("");
				itemThumbnail5.setBounds(54, 35, 60, 60);
				itemThumbnail5.setHorizontalAlignment(SwingConstants.CENTER);
				itemThumbnail5.setIcon(new ImageIcon(shop.items[4].imageSource));
				rightCenterPanel.add(itemThumbnail5);

				JLabel itemDescription5 = new JLabel();
				itemDescription5.setText(shop.items[4].itemDescription);
				itemDescription5.setBounds(25, 104, 120, 18);
				itemDescription5.setHorizontalAlignment(SwingConstants.CENTER);
				rightCenterPanel.add(itemDescription5);
				
				JButton buyItemButton5 = new JButton(""+shop.items[4].itemCost);
				buyItemButton5.setIcon(new ImageIcon(starImg));
				buyItemButton5.setFocusPainted(false);
				buyItemButton5.addMouseListener(new MouseAdapter(){
					@Override 
					public void mousePressed(MouseEvent e) {
						if(summoner.buyItem(shop.items[4].itemIndex, shop.items[4].itemCost,1)){
							shop.setSold(4);
							starBalance.setText(summoner.getCoin()+"");
							buyItemButton5.setText("판매완료");
							buyItemButton5.setEnabled(false);
							buyItemButton5.setIcon(null);
							buyItemButton5.setFocusable(false);
							// 대화문구							
						}else {
							// 구매실패
						}
					}
				});
				if(shop.isSold(4)) {
					buyItemButton5.setEnabled(false);
					buyItemButton5.setText("판매완료");
					buyItemButton5.setIcon(null);
				}
				buyItemButton5.setBounds(35, 131, 105, 27);
				rightCenterPanel.add(buyItemButton5);
				
				JPanel rightBottomPanel = new JPanel();
				rightBottomPanel.setLayout(null);
				rightBottomPanel.setBounds(14, 376, 172, 170);
				rightPanel.add(rightBottomPanel);
				
				JLabel itemLabel6 = new JLabel();
				itemLabel6.setVerticalAlignment(SwingConstants.TOP);
				itemLabel6.setHorizontalAlignment(SwingConstants.CENTER);
				itemLabel6.setText(shop.items[5].name);
				itemLabel6.setBounds(25, 12, 120, 18);
				rightBottomPanel.add(itemLabel6);
				
				JLabel itemThumbnail6 = new JLabel("");
				itemThumbnail6.setBounds(54, 35, 60, 60);
				itemThumbnail6.setIcon(new ImageIcon(shop.items[5].imageSource));
				itemThumbnail6.setHorizontalAlignment(SwingConstants.CENTER);
				rightBottomPanel.add(itemThumbnail6);
				
				JLabel itemDescription6 = new JLabel();
				itemDescription6.setText(shop.items[5].itemDescription);
				itemDescription6.setBounds(25, 104, 120, 18);
				itemDescription6.setHorizontalAlignment(SwingConstants.CENTER);
				rightBottomPanel.add(itemDescription6);
				
				JButton buyItemButton6 = new JButton(""+shop.items[5].itemCost);
				buyItemButton6.setFocusPainted(false);
				buyItemButton6.setIcon(new ImageIcon(starImg));
				buyItemButton6.addMouseListener(new MouseAdapter(){
					@Override 
					public void mousePressed(MouseEvent e) {
						if(summoner.buyItem(shop.items[5].itemIndex, shop.items[5].itemCost,1)){
							shop.setSold(5);
							starBalance.setText(summoner.getCoin()+"");
							buyItemButton6.setText("판매완료");
							buyItemButton6.setEnabled(false);
							buyItemButton6.setIcon(null);
							buyItemButton6.setFocusable(false);
							// 대화문구
						}else {
							// 구매실패
						}
					}
				});
				if(shop.isSold(5)) {
					buyItemButton6.setEnabled(false);
					buyItemButton6.setText("판매완료");
					buyItemButton6.setIcon(null);
				}
				buyItemButton6.setBounds(35, 131, 105, 27);
				rightBottomPanel.add(buyItemButton6);
		
				
				JPanel background = new JPanel();
				background.setBounds(0, 0, 800, 568);
				shopPanel.add(background);
				background.setLayout(null);
						JLabel backgroundImageLabel = new JLabel("background");
						
						JPanel centerBottomPanel = new JPanel();
						centerBottomPanel.setLayout(null);
						centerBottomPanel.setBounds(256, 488, 283, 48);
						background.add(centerBottomPanel);
						
						
						starBalance.setIcon(new ImageIcon(starImg));
						starBalance.setText(summoner.getCoin()+"");
						starBalance.setHorizontalAlignment(SwingConstants.CENTER);
						starBalance.setBounds(14, 12, 100, 25);
						centerBottomPanel.add(starBalance);
						
						JButton nextGameButton = new JButton("다음게임");
						nextGameButton.setBounds(128, 11, 105, 27);
						nextGameButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								// stage가 올라가면 소환가능한 몬스터를 해금
								stage++;
								if(stage == 2) {
									// w키로 소환 가능한 몬스터
									summoner.isSummonable.put("w",true);
								}else if(stage == 3) {
									// e키로 소환 가능한 몬스터
									summoner.isSummonable.put("e",true);
								}
								
								
								nextGameButton.setFocusable(false);
//								System.out.println("버튼 눌림 확인");
//								contentPane.setVisible(false);
								
								background.setVisible(false);
								leftPanel.setVisible(false);
								rightPanel.setVisible(false);
								
								
								
//								nextGameButton.setVisible(false);
//								starBalance.setVisible(false);
//								centerBottomPanel.setVisible(false);
								
								allyList = new ArrayList<>();
								enemyList = new ArrayList<>();
								// 적 소환사와 아군 소환사의 생성
								enemySummoner = enemySummoners[stage-1]; 
//										new EnemySummoner(enemySummonerImage, 100, new Point(frameWidth - 80, y), 100);
//								summoner = new PlayerSummoner(summonerImage, 100, new Point(x, y),99999);
								// 전투 종료,승패여부 변수의 초기화
								isBattleEnd = false;
								isPlayerWin = false;
								isGameScreen = true;
								isShopScreen = false;
								
								shop.selectedPortionAmount = 1;
								musicPlayer.close();
								musicPlayer = new Music("DragonNest.mp3",true);
								musicPlayer.start();
//								System.out.println(isGameScreen);
//								getContentPane().removeAll();
//								validate();//버튼이 추가된 것을 컨테이너에 알려서 갱신한다
//								revalidate();
								repaint();
								
							}
							@Override
							public void mouseEntered(MouseEvent e) {
//								System.out.println("버튼 진입 확인");
//								System.out.println(getContentPane().toString());
							}
						});
						
						centerBottomPanel.add(nextGameButton);
						backgroundImageLabel.setIcon(new ImageIcon(changedImg));
						backgroundImageLabel.setBounds(0, 0, 800, 568);
						background.add(backgroundImageLabel);
						
		this.setVisible(true);		
	}
	public void goEnding() {
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 
		ImageIcon originIcon = new ImageIcon("images/background/ending_background.jpg");
		//ImageIcon에서 Image를 추출
		Image originImg = originIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		Image changedImg= originImg.getScaledInstance(800, 600, Image.SCALE_SMOOTH );
	

		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		ImageIcon hasteIcon = new ImageIcon("C:\\Users\\grinder\\Desktop\\PAST\\JAVA1\\javagame\\TestWindowBuilder\\images\\icon\\haste.gif");
		//ImageIcon에서 Image를 추출
		Image hasteImg = hasteIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		hasteImg= hasteImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH );
		
		ImageIcon starIcon = new ImageIcon("C:\\Users\\grinder\\Desktop\\PAST\\JAVA1\\javagame\\TestWindowBuilder\\images\\icon\\star.png");
		//ImageIcon에서 Image를 추출
		Image starImg = starIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		starImg= starImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH );
		starBalance = new JLabel(summoner.getCoin()+"");
				
				JPanel shopPanel = new JPanel();
				shopPanel.setBounds(0, 0, 800, 600);
				contentPane.add(shopPanel);
				shopPanel.setLayout(null);
								
				JPanel background = new JPanel();
//				background.setBounds(0, 0, 800, 568);
//				background.setBounds(0, 0, 800, 600);
				background.setBounds(0, 0, 800, 575);
				
				shopPanel.add(background);
				background.setLayout(null);
						JLabel backgroundImageLabel = new JLabel("background");
						
//						JLabel endingLabel = new JLabel("Thank you for playing!");
						endingLabel.setForeground(new Color(148, 0, 211));
						endingLabel.setFont(new Font("Kristen ITC", Font.BOLD, 42));
						endingLabel.setBounds(12, 10, 650, 87);
						background.add(endingLabel);
						
						creditLine.setOpaque(false);
						creditLine.setBackground(new Color(0,0,0,0));
						creditLine.setForeground(new Color(148, 0, 211));
						creditLine.setBounds(12, 117, 500, 461);
						creditLine.setFont(new Font("휴먼편지체", Font.BOLD, 40));
						creditLine.setFocusable(false);
//						creditLine.setEnabled(false);
						creditLine.setEditable(false);
						background.add(creditLine);
//						creditLabel.setVerticalAlignment(SwingConstants.TOP);
						
//						creditLabel.setFont(new Font("Kristen ITC", Font.BOLD, 20));
//						creditLabel.setBounds(12, 117, 458, 361);
//						background.add(creditLabel);
						
						backgroundImageLabel.setIcon(new ImageIcon(changedImg));
//						backgroundImageLabel.setBounds(0, 0, 800, 568);						
						backgroundImageLabel.setBounds(0, 0, 800, 575);
//						backgroundImageLabel.setBounds(0, 0, 800, 600);
						background.add(backgroundImageLabel);
						
		this.setVisible(true);	
	}
	public void goMain() {
		isGameScreen = false;
		isShopScreen = true;
		contentPane = new JPanel();
		
		// 
//		backgroundImage = toolkit.getImage("images/background/background.jpg"); // 배경이미지를 불러온다
		ImageIcon originIcon = new ImageIcon("images/background/background.jpg");
		//ImageIcon에서 Image를 추출
		Image originImg = originIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		Image changedImg= originImg.getScaledInstance(800, 600, Image.SCALE_SMOOTH );
	

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
				JPanel shopPanel = new JPanel();
				shopPanel.setBounds(0, 0, 800, 600);
				contentPane.add(shopPanel);
				shopPanel.setLayout(null);
								
				JPanel background = new JPanel();
//				background.setBounds(0, 0, 800, 568);
//				background.setBounds(0, 0, 800, 600);
				background.setBounds(0, 0, 800, 575);
				
				shopPanel.add(background);
				background.setLayout(null);
						JLabel backgroundImageLabel = new JLabel("background");
						
//						JLabel endingLabel = new JLabel("Thank you for playing!");
						JLabel titleLabel = new JLabel("검은마법사 부수기");
						titleLabel.setForeground(new Color(148, 0, 211));
						titleLabel.setFont(new Font("휴먼편지체", Font.BOLD, 70));
						titleLabel.setBounds(12, 10, 650, 87);
						background.add(titleLabel);

						JLabel startLabel = new JLabel("시작하기");
						startLabel.setForeground(new Color(148, 0, 211));
						startLabel.setFont(new Font("휴먼편지체", Font.BOLD, 40));
						startLabel.setBounds(12, 90, 650, 87);
						startLabel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								startLabel.setFocusable(false);
								stage++;

//								init(); // 나중을 위한 프레임에 들어갈 컴포넌트 세팅 메소드
//								start(); // 나중을 위한 기본적인 시작 명령 처리 부분

								
//								nextGameButton.setFocusable(false);
//								System.out.println("버튼 눌림 확인");
//								contentPane.setVisible(false);
								
								background.setVisible(false);
//								leftPanel.setVisible(false);
//								rightPanel.setVisible(false);
								
								
								
//								nextGameButton.setVisible(false);
//								starBalance.setVisible(false);
//								centerBottomPanel.setVisible(false);
								
								allyList = new ArrayList<>();
								enemyList = new ArrayList<>();
								// 적 소환사와 아군 소환사의 생성
//								enemySummoner = enemySummoners[stage-1]; 
//										new EnemySummoner(enemySummonerImage, 100, new Point(frameWidth - 80, y), 100);
//								summoner = new PlayerSummoner(summonerImage, 100, new Point(x, y),99999);
								// 전투 종료,승패여부 변수의 초기화
								isBattleEnd = false;
								isPlayerWin = false;
								isGameScreen = true;
								isShopScreen = false;
								
//								shop.selectedPortionAmount = 1;
								musicPlayer.close();
								musicPlayer = new Music("DragonNest.mp3",true);
								musicPlayer.start();
//								System.out.println(isGameScreen);
//								getContentPane().removeAll();
//								validate();//버튼이 추가된 것을 컨테이너에 알려서 갱신한다
//								revalidate();
								repaint();
							}
							@Override
							public void mouseEntered(MouseEvent e) {
//								System.out.println("버튼 진입 확인");
//								System.out.println(getContentPane().toString());
							}
						});
						background.add(startLabel);
						
						backgroundImageLabel.setIcon(new ImageIcon(changedImg));
//						backgroundImageLabel.setBounds(0, 0, 800, 568);						
						backgroundImageLabel.setBounds(0, 0, 800, 575);
//						backgroundImageLabel.setBounds(0, 0, 800, 600);
						background.add(backgroundImageLabel);						
		this.setVisible(true);	
//		repaint();
//		revalidate();

	}
}