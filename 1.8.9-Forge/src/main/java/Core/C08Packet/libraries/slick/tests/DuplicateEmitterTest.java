package Core.C08Packet.libraries.slick.tests;

import java.io.IOException;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.particles.ConfigurableEmitter;
import Core.C08Packet.libraries.slick.particles.ParticleIO;
import Core.C08Packet.libraries.slick.particles.ParticleSystem;

/**
 * A test for duplicating a ConfigurableEmitter several times
 * @author Tommy
 *
 */
public class DuplicateEmitterTest extends BasicGame {

	/** the game container */
	private GameContainer container;
	/** the particle system which contains an explosion emitter which we want to duplicate */
	private ParticleSystem explosionSystem;
	/** The original emitter we've duplicated */
	private ConfigurableEmitter explosionEmitter;
	
	/**
	 * Create a new DuplicateEmitterTest
	 */
	public DuplicateEmitterTest() {
		super("DuplicateEmitterTest");
	}
	
	/**
	 * load ressources (the particle system) and create our duplicate emitters
	 * and place them nicely on the screen
	 * @param container The surrounding game container
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
		try {
			// load the particle system containing our explosion emitter
			explosionSystem = ParticleIO.loadConfiguredSystem("testdata/endlessexplosion.xml");
			// get the emitter, it's the first (and only one) in this particle system
			explosionEmitter = (ConfigurableEmitter) explosionSystem.getEmitter(0);
			// set the original emitter in the middle of the screen at the top
			explosionEmitter.setPosition(400,100);
			// create 5 duplicate emitters
			for (int i = 0; i < 5; i++) {
				// a single duplicate of the first emitter is created here
				ConfigurableEmitter newOne = explosionEmitter.duplicate();
				// we might get null as a result - protect against that
				if (newOne == null)
					throw new SlickException("Failed to duplicate explosionEmitter");
				// give the new emitter a new unique name
				newOne.name = newOne.name + "_" + i;
				// place it somewhere on a row below the original emitter
				newOne.setPosition((i+1)* (800/6), 400);
				// and add it to the original particle system to get the new emitter updated and rendered
				explosionSystem.addEmitter(newOne);
			}
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		explosionSystem.update(delta);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		explosionSystem.render();
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
		if (key == Input.KEY_K) {
			explosionEmitter.wrapUp();
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test, not used here
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new DuplicateEmitterTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
