package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.svg.Diagram;
import Core.C08Packet.libraries.slick.svg.InkscapeLoader;
import Core.C08Packet.libraries.slick.svg.SVGMorph;
import Core.C08Packet.libraries.slick.svg.SimpleDiagramRenderer;

/**
 * A test to try shape morphing
 * 
 * @author Kevin Glass
 */
public class MorphSVGTest extends BasicGame {
	/** The morphing SVG */
	private SVGMorph morph;
	/** First shape of the morph */
	private Diagram base;
	/** The time index of the morph being display */
	private float time;
	/** The current x position */
	private float x = -300;
	
	/**
	 * Create a simple test
	 */
	public MorphSVGTest() {
		super("MorphShapeTest");
	}

	/**
	 * @see BasicGame#init(GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		base = InkscapeLoader.load("testdata/svg/walk1.svg");
		morph = new SVGMorph(base);
		morph.addStep(InkscapeLoader.load("testdata/svg/walk2.svg"));
		morph.addStep(InkscapeLoader.load("testdata/svg/walk3.svg"));
		morph.addStep(InkscapeLoader.load("testdata/svg/walk4.svg"));
		
		container.setVSync(true);
	}

	/**
	 * @see BasicGame#update(GameContainer, int)
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {
		morph.updateMorphTime(delta * 0.003f);
		
		x += delta * 0.2f;
		if (x > 550) {
			x = -450;
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#render(GameContainer, Graphics)
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.translate(x, 0);
		SimpleDiagramRenderer.render(g, morph);
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
					new MorphSVGTest());
			container.setDisplayMode(800, 600, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
