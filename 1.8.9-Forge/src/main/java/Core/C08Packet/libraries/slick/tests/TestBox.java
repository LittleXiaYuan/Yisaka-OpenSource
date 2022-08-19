package Core.C08Packet.libraries.slick.tests;

import java.util.ArrayList;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.opengl.SlickCallable;
import Core.C08Packet.libraries.slick.util.Log;

/**
 * A test box containing a bunch of tests that can be used for quickly sanity
 * checking tests.
 *
 * @author kevin
 */
public class TestBox extends BasicGame {
	/** The games that have been added */
	private ArrayList games = new ArrayList();
	/** The current game */
	private BasicGame currentGame;
	/** The index of the current game */
	private int index;
	/** The game container */
	private AppGameContainer container;
	
	/**
	 * Create a new box containing all the tests
	 */
	public TestBox() {
		super("Test Box");
	}
	
	/**
	 * Add a game to the box
	 * 
	 * @param game The game to add to the test box
	 */
	public void addGame(Class game) {
		games.add(game);
	}
	
	/**
	 * Move to the next game
	 */
	private void nextGame() {
		if (index == -1) {
			return;
		}
		
		index++;
		if (index >= games.size()) {
			index=0;
		}
	
		startGame();
	}
	
	/**
	 * Start a particular game
	 */
	private void startGame() {
		try {
			currentGame = (BasicGame) ((Class) games.get(index)).newInstance();
			container.getGraphics().setBackground(Color.black);
			currentGame.init(container);
			currentGame.render(container, container.getGraphics());
		} catch (Exception e) {
			Log.error(e);
		}
		
		container.setTitle(currentGame.getTitle());
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer c) throws SlickException {
		if (games.size() == 0) {
			currentGame = new BasicGame("NULL") {
				public void init(GameContainer container) throws SlickException {
				}

				public void update(GameContainer container, int delta) throws SlickException {
				}

				public void render(GameContainer container, Graphics g) throws SlickException {
				}
			};
			currentGame.init(c);
			index = -1;
		} else {
			index = 0;
			container = (AppGameContainer) c;
			startGame();
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		currentGame.update(container, delta);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		SlickCallable.enterSafeBlock();
		currentGame.render(container, g);
		SlickCallable.leaveSafeBlock();
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerButtonPressed(int, int)
	 */
	public void controllerButtonPressed(int controller, int button) {
		currentGame.controllerButtonPressed(controller, button);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerButtonReleased(int, int)
	 */
	public void controllerButtonReleased(int controller, int button) {
		currentGame.controllerButtonReleased(controller, button);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerDownPressed(int)
	 */
	public void controllerDownPressed(int controller) {
		currentGame.controllerDownPressed(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerDownReleased(int)
	 */
	public void controllerDownReleased(int controller) {
		currentGame.controllerDownReleased(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerLeftPressed(int)
	 */
	public void controllerLeftPressed(int controller) {
		currentGame.controllerLeftPressed(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerLeftReleased(int)
	 */
	public void controllerLeftReleased(int controller) {
		currentGame.controllerLeftReleased(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerRightPressed(int)
	 */
	public void controllerRightPressed(int controller) {
		currentGame.controllerRightPressed(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerRightReleased(int)
	 */
	public void controllerRightReleased(int controller) {
		currentGame.controllerRightReleased(controller);
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerUpPressed(int)
	 */
	public void controllerUpPressed(int controller) {
		currentGame.controllerUpPressed(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#controllerUpReleased(int)
	 */
	public void controllerUpReleased(int controller) {
		currentGame.controllerUpReleased(controller);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		currentGame.keyPressed(key, c);
		
		if (key == Input.KEY_ENTER) {
			nextGame();
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		currentGame.keyReleased(key, c);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		currentGame.mouseMoved(oldx, oldy, newx, newy);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int x, int y) {
		currentGame.mousePressed(button, x, y);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
		currentGame.mouseReleased(button, x, y);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#mouseWheelMoved(int)
	 */
	public void mouseWheelMoved(int change) {
		currentGame.mouseWheelMoved(change);
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			TestBox box = new TestBox();
			box.addGame(AnimationTest.class);
			box.addGame(AntiAliasTest.class);
			box.addGame(BigImageTest.class);
			box.addGame(ClipTest.class);
			box.addGame(DuplicateEmitterTest.class);
			box.addGame(FlashTest.class);
			box.addGame(FontPerformanceTest.class);
			box.addGame(FontTest.class);
			box.addGame(GeomTest.class);
			box.addGame(GradientTest.class);
			box.addGame(GraphicsTest.class);
			box.addGame(ImageBufferTest.class);
			box.addGame(ImageReadTest.class);
			box.addGame(ImageTest.class);
			box.addGame(KeyRepeatTest.class);
			box.addGame(MusicListenerTest.class);
			box.addGame(PackedSheetTest.class);
			box.addGame(PedigreeTest.class);
			box.addGame(PureFontTest.class);
			box.addGame(ShapeTest.class);
			box.addGame(SoundTest.class);
			box.addGame(SpriteSheetFontTest.class);
			box.addGame(TransparentColorTest.class);
			
			AppGameContainer container = new AppGameContainer(box);
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
