package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.geom.Polygon;

/**
 * A test for polygon collision
 *
 * @author kevin
 */
public class PolygonTest extends BasicGame {
	/** The polygon we're going to test against */
	private Polygon poly;
	/** True if the mouse is in the polygon */
	private boolean in;
	/** The y offset */
	private float y;
	
	/**
	 * Create the test
	 */
	public PolygonTest() {
		super("Polygon Test");
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		poly = new Polygon();
		poly.addPoint(300, 100);
		poly.addPoint(320, 200);
		poly.addPoint(350, 210);
		poly.addPoint(280, 250);
		poly.addPoint(300, 200);
		poly.addPoint(240, 150);
		
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		in = poly.contains(container.getInput().getMouseX(), container.getInput().getMouseY());
		
		poly.setCenterY(0);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		if (in) {
			g.setColor(Color.red);
			g.fill(poly);
		}
		g.setColor(Color.yellow);
		g.fillOval(poly.getCenterX()-3, poly.getCenterY()-3, 6, 6);
		g.setColor(Color.white);
		g.draw(poly);
	}

	/**
	 * Entry point into our test
	 * 
	 * @param argv The arguments passed on the command line
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new PolygonTest(), 640, 480, false);
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
