package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.SlickException;

/**
 * A test to demonstrate world clipping as opposed to screen clipping
 *
 * @author kevin
 */
public class CopyAreaAlphaTest extends BasicGame {
	/** The texture to apply over the top */
	private Image textureMap;
	/** The copied image */
	private Image copy;
	
	/**
	 * Create a new tester for the clip plane based clipping
	 */
	public CopyAreaAlphaTest() {
		super("CopyArea Alpha Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		textureMap = new Image("testdata/grass.png");
		container.getGraphics().setBackground(Color.black);
		copy = new Image(100,100);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.clearAlphaMap();
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.setColor(Color.white);
		g.fillOval(100,100,150,150);
		textureMap.draw(10,50);
		
		g.copyArea(copy, 100,100);
		g.setColor(Color.red);
		g.fillRect(300,100,200,200);
		copy.draw(350,150);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
	}
	
	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new CopyAreaAlphaTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
