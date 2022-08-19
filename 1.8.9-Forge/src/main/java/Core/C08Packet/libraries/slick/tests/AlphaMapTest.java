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
public class AlphaMapTest extends BasicGame {
	/** The alpha map being applied */
	private Image alphaMap;
	/** The texture to apply over the top */
	private Image textureMap;
	
	/**
	 * Create a new tester for the clip plane based clipping
	 */
	public AlphaMapTest() {
		super("AlphaMap Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		alphaMap = new Image("testdata/alphamap.png");
		textureMap = new Image("testdata/grass.png");
		container.getGraphics().setBackground(Color.black);
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
		textureMap.draw(10,50);
		g.setColor(Color.red);
		g.fillRect(290,40,200,200);
		g.setColor(Color.white);
		// write only alpha
		g.setDrawMode(Graphics.MODE_ALPHA_MAP);
		alphaMap.draw(300,50);
		g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
		textureMap.draw(300,50);
		g.setDrawMode(Graphics.MODE_NORMAL);
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
			AppGameContainer container = new AppGameContainer(new AlphaMapTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
