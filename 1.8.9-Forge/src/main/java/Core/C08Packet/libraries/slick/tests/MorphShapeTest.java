package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.geom.MorphShape;
import Core.C08Packet.libraries.slick.geom.Rectangle;
import Core.C08Packet.libraries.slick.geom.Shape;
import Core.C08Packet.libraries.slick.geom.Transform;

/**
 * A test to try shape morphing
 * 
 * @author Kevin Glass
 */
public class MorphShapeTest extends BasicGame {
	/** First shape of the morph */
	private Shape a;
	/** Second shape of the morph */
	private Shape b;
	/** Third shape of the morph */
	private Shape c;
	/** The morphing shape */
	private MorphShape morph;
	/** The current morph time */
	private float time;
	
	/**
	 * Create a simple test
	 */
	public MorphShapeTest() {
		super("MorphShapeTest");
	}

	/**
	 * @see BasicGame#init(GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		a = new Rectangle(100,100,50,200);
		a = a.transform(Transform.createRotateTransform(0.1f,100,100));
		b = new Rectangle(200,100,50,200);
		b = b.transform(Transform.createRotateTransform(-0.6f,100,100));
		c = new Rectangle(300,100,50,200);
		c = c.transform(Transform.createRotateTransform(-0.2f,100,100));
		
		morph = new MorphShape(a);
		morph.addShape(b);
		morph.addShape(c);
		
		container.setVSync(true);
	}

	/**
	 * @see BasicGame#update(GameContainer, int)
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {
		time += delta * 0.001f;
		morph.setMorphTime(time);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#render(GameContainer, Graphics)
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.green);
		g.draw(a);
		g.setColor(Color.red);
		g.draw(b);
		g.setColor(Color.blue);
		g.draw(c);
		g.setColor(Color.white);
		g.draw(morph);
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv
	 *            The arguments passed to the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(
					new MorphShapeTest());
			container.setDisplayMode(800, 600, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
