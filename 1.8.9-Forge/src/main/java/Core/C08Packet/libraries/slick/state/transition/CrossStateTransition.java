package Core.C08Packet.libraries.slick.state.transition;

import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.state.GameState;
import Core.C08Packet.libraries.slick.state.StateBasedGame;

/**
 * A transition that will combine two states into one effect. The first state is
 * the one we're transitioning from. The second state is specified in the constructor. 
 * 
 * By default one state will simply be rendered over the other. Subclass this transition
 * overriding the preRenderFirstState and preRenderSecondState to setup the rendering
 * for each state (alpha or what ever). Note that it's also possible to use the 
 * postRenderSecondState method to clean up your OpenGL setup. 
 * 
 * So these methods are called like so:
 * 
 * preRenderFirstState()
 * = the first state is rendered
 * preRenderSecondState()
 * = the second state is rendered
 * postRenderSecondState()
 *
 * @author kevin
 */
public abstract class CrossStateTransition implements Transition {
	/** The second state to cross with */
	private GameState secondState;
	
	/**
	 * Create a cross state transitions
	 * 
	 * @param secondState The secondary state with combining with the 
	 * source state.
	 */
	public CrossStateTransition(GameState secondState) {
		this.secondState = secondState;
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.state.transition.Transition#isComplete()
	 */
	public abstract boolean isComplete();

	/**
	 * @see Core.C08Packet.libraries.slick.state.transition.Transition#postRender(Core.C08Packet.libraries.slick.state.StateBasedGame, Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
		preRenderSecondState(game, container, g);
		secondState.render(container, game, g);
		postRenderSecondState(game, container, g);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.transition.Transition#preRender(Core.C08Packet.libraries.slick.state.StateBasedGame, Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
		preRenderFirstState(game, container, g);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.transition.Transition#update(Core.C08Packet.libraries.slick.state.StateBasedGame, Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
	}

	/**
	 * Notification that the transition is about to render the first state is the cross
	 * transition.
	 * 
	 * @param game The game being rendered
	 * @param container The container holding the game
	 * @param g The graphic context used to render
	 * @throws SlickException Indicates a failure to setup the rendering state - throw for anything that goes wrong
	 */
	public void preRenderFirstState(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
	}
	
	/**
	 * Notification that the transition is about to render the second state is the cross
	 * transition.
	 * 
	 * @param game The game being rendered
	 * @param container The container holding the game
	 * @param g The graphic context used to render
	 * @throws SlickException Indicates a failure to setup the rendering state - throw for anything that goes wrong
	 */
	public void preRenderSecondState(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
	}

	/**
	 * Notification that the transition is has just rendered the second state is the cross
	 * transition.
	 * 
	 * @param game The game being rendered
	 * @param container The container holding the game
	 * @param g The graphic context used to render
	 * @throws SlickException Indicates a failure to setup the rendering state - throw for anything that goes wrong
	 */	
	public void postRenderSecondState(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
	}
	
}
