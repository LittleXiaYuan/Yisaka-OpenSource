package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.state.BasicGameState;
import Core.C08Packet.libraries.slick.state.StateBasedGame;
import Core.C08Packet.libraries.slick.state.transition.BlobbyTransition;
import Core.C08Packet.libraries.slick.state.transition.FadeInTransition;
import Core.C08Packet.libraries.slick.state.transition.FadeOutTransition;
import Core.C08Packet.libraries.slick.state.transition.HorizontalSplitTransition;
import Core.C08Packet.libraries.slick.state.transition.RotateTransition;
import Core.C08Packet.libraries.slick.state.transition.SelectTransition;
import Core.C08Packet.libraries.slick.state.transition.Transition;
import Core.C08Packet.libraries.slick.state.transition.VerticalSplitTransition;
import Core.C08Packet.libraries.slick.util.Log;

/**
 * A test to view the different transitions that are currently implemented
 * 
 * @author kevin
 */
public class TransitionTest extends StateBasedGame {
	/** The transitions under test */
	private Class[][] transitions = new Class[][] {
			{null, VerticalSplitTransition.class},
			{FadeOutTransition.class, FadeInTransition.class},
			{null, RotateTransition.class},
			{null, HorizontalSplitTransition.class},
			{null, BlobbyTransition.class},
			{null, SelectTransition.class},
	};
	/** The index of the next transition to use */
	private int index;
	
	/**
	 * Test the transitions implemented
	 */
	public TransitionTest() {
		super("Transition Test - Hit Space To Transition");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.state.StateBasedGame#initStatesList(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ImageState(0, "testdata/wallpaper/paper1.png", 1));
		addState(new ImageState(1, "testdata/wallpaper/paper2.png", 2));
		addState(new ImageState(2, "testdata/bigimage.tga", 0));
	}

	/**
	 * Get the next transition pair that we'lluse
	 * 
	 * @return The pair of transitions used to enter and leave the next state
	 */
	public Transition[] getNextTransitionPair() {
		Transition[] pair = new Transition[2];
		
		try {
			if (transitions[index][0] != null) {
				pair[0] = (Transition) transitions[index][0].newInstance();
			}
			if (transitions[index][1] != null) {
				pair[1] = (Transition) transitions[index][1].newInstance();
			}
		} catch (Throwable e) {
			Log.error(e);
		}
		
		index++;
		if (index >= transitions.length) {
			index = 0;
		}
		
		return pair;
	}
	
	/**
	 * A test state that just displayed one image full scren
	 * 
	 * @author kevin
	 */
	private class ImageState extends BasicGameState {
		/** The id of this state */
		private int id;
		/** The next state we'll move to */
		private int next;
		/** The reference to the image to be displayed */
		private String ref;
		/** The loaded image */
		private Image image;
		
		/**
		 * Create a new image state
		 * 
		 * @param id The id of the this state
		 * @param ref The reference to the image to display
		 * @param next The next state we'll mvoe to
		 */
		public ImageState(int id, String ref, int next) {
			this.ref = ref;
			this.id = id;
			this.next = next;
		}

		/**
		 * @see Core.C08Packet.libraries.slick.state.BasicGameState#getID()
		 */
		public int getID() {
			return id;
		}

		/**
		 * @see Core.C08Packet.libraries.slick.state.GameState#init(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame)
		 */
		public void init(GameContainer container, StateBasedGame game) throws SlickException {
			image = new Image(ref);
		}

		/**
		 * @see Core.C08Packet.libraries.slick.state.GameState#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame, Core.C08Packet.libraries.slick.Graphics)
		 */
		public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
			image.draw(0,0,800,600);
			g.setColor(Color.red);
			g.fillRect(-50,200,50,50);
		}

		/**
		 * @see Core.C08Packet.libraries.slick.state.GameState#update(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame, int)
		 */
		public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
			if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
				Transition[] pair = getNextTransitionPair();
				game.enterState(next, pair[0], pair[1]);
			}
		}
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
					new TransitionTest());
			container.setDisplayMode(800, 600, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
