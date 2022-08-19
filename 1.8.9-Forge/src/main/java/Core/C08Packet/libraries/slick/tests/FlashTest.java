package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;

/**
 * A test for image flashes
 *
 * @author kevin
 */
public class FlashTest extends BasicGame {
	/** The TGA image loaded */
	private Image image;
	/** True if the image is rendered flashed */
	private boolean flash;
	/** The container for the test */
	private GameContainer container;
	
	/**
	 * Create a new image rendering test
	 */
	public FlashTest() {
		super("Flash Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
		image = new Image("testdata/logo.tga");
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("Press space to toggle",10,50);
		if (flash) {
			image.draw(100,100);
		} else {
			image.drawFlash(100,100,image.getWidth(), image.getHeight(), new Color(1,0,1f,1f));
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new FlashTest());
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
		if (key == Input.KEY_SPACE) {
			flash = !flash;
		}
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
	}
}
