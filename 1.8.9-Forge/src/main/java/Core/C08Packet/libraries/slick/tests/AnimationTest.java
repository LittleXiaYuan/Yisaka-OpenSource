package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.Animation;
import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.SpriteSheet;

/**
 * A test for basic animation rendering
 *
 * @author kevin
 */
public class AnimationTest extends BasicGame {
	/** The animation loaded */
	private Animation animation;
	/** The limited animation loaded */
	private Animation limited;
	/** The manual update animation loaded */
	private Animation manual;
	/** The animation loaded */
	private Animation pingPong;
	/** The container */
	private GameContainer container;
	/** Start limited counter */
	private int start = 5000;
	
	/**
	 * Create a new image rendering test
	 */
	public AnimationTest() {
		super("Animation Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
		SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
		animation = new Animation();
		for (int i=0;i<8;i++) {
			animation.addFrame(sheet.getSprite(i,0), 150);
		}
		limited = new Animation();
		for (int i=0;i<8;i++) {
			limited.addFrame(sheet.getSprite(i,0), 150);
		}
		limited.stopAt(7);
		manual = new Animation(false);
		for (int i=0;i<8;i++) {
			manual.addFrame(sheet.getSprite(i,0), 150);
		}
		pingPong = new Animation(sheet, 0,0,7,0,true,150,true);
		pingPong.setPingPong(true);
		container.getGraphics().setBackground(new Color(0.4f,0.6f,0.6f));
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("Space to restart() animation", 100, 50);
		g.drawString("Til Limited animation: "+start, 100, 500);
		g.drawString("Hold 1 to move the manually animated", 100, 70);
		g.drawString("PingPong Frame:"+pingPong.getFrame(), 600, 70);
		
		g.scale(-1,1);
		animation.draw(-100,100);
		animation.draw(-200,100,36*4,65*4);
		if (start < 0) {
			limited.draw(-400,100,36*4,65*4);
		}
		manual.draw(-600,100,36*4,65*4);
		pingPong.draw(-700,100,36*2,65*2);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
		if (container.getInput().isKeyDown(Input.KEY_1)) {
			manual.update(delta);
		}
		if (start >= 0) {
			start -= delta;
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new AnimationTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
		if (key == Input.KEY_SPACE) {
			limited.restart();
		}
	}
}
