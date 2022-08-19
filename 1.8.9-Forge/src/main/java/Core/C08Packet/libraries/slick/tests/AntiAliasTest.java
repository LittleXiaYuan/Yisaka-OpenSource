package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.SlickException;

/**
 * Test to view the effects of antialiasing on cirles
 *
 * @author kevin
 */
public class AntiAliasTest extends BasicGame {

	/**
	 * Create the test
	 */
	public AntiAliasTest() {
		super("AntiAlias Test");
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		container.getGraphics().setBackground(Color.green);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setAntiAlias(true);
		g.setColor(Color.red);
		g.drawOval(100,100,100,100);
		g.fillOval(300,100,100,100);
		g.setAntiAlias(false);
		g.setColor(Color.red);
		g.drawOval(100,300,100,100);
		g.fillOval(300,300,100,100);
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments passed to the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new AntiAliasTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
