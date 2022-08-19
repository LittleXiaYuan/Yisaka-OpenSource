package Core.C08Packet.libraries.slick.tests;

import java.io.IOException;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.particles.ConfigurableEmitter;
import Core.C08Packet.libraries.slick.particles.ParticleIO;
import Core.C08Packet.libraries.slick.particles.ParticleSystem;

/**
 * A test for loading editing particle systems
 *
 * @author kevin
 */
public class PedigreeTest extends BasicGame {
	/** The image we're currently displaying */
	private Image image;
	/** The game container */
	private GameContainer container;
	/** The smoke trail particle system */
	private ParticleSystem trail;
	/** The fire particle system */
	private ParticleSystem fire;
	/** The rocket x position */
	private float rx;
	/** The rocket y position */
	private float ry = 900;
	
	/**
	 * Create a new image rendering test
	 */
	public PedigreeTest() {
		super("Pedigree Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
		try {
			fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
			trail = ParticleIO.loadConfiguredSystem("testdata/smoketrail.xml");
			
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
		image = new Image("testdata/rocket.png");
	
		spawnRocket();
	}

	/**
	 * Spawn a test rocket 
	 */
	private void spawnRocket() {
		ry = 700;
		rx = (float) ((Math.random()*600) + 100);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		((ConfigurableEmitter) trail.getEmitter(0)).setPosition(rx+14,ry+35);
		trail.render();
		image.draw((int) rx,(int) ry);
		
		g.translate(400, 300);
		fire.render();
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
		fire.update(delta);
		trail.update(delta);
		
		ry -= delta * 0.25f;
		if (ry < -100) {
			spawnRocket();
		}
	}

	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		for (int i=0;i<fire.getEmitterCount();i++) {
			((ConfigurableEmitter) fire.getEmitter(i)).setPosition(x - 400, y - 300, true);
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new PedigreeTest());
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
	}
}
