package Core.C08Packet.libraries.slick.state;

import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;

/**
 * A simple state used an adapter so we don't have to implement all the event methods
 * every time.
 *
 * @author kevin
 */
public abstract class BasicGameState implements GameState {
	/**
	 * @see Core.C08Packet.libraries.slick.ControlledInputReciever#inputStarted()
	 */
	public void inputStarted() {
		
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#isAcceptingInput()
	 */
	public boolean isAcceptingInput() {
		return true;
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#setInput(Core.C08Packet.libraries.slick.Input)
	 */
	public void setInput(Input input) {
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#inputEnded()
	 */
	public void inputEnded() {
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.state.GameState#getID()
	 */
	public abstract int getID();

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerButtonPressed(int, int)
	 */
	public void controllerButtonPressed(int controller, int button) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerButtonReleased(int, int)
	 */
	public void controllerButtonReleased(int controller, int button) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerDownPressed(int)
	 */
	public void controllerDownPressed(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerDownReleased(int)
	 */
	public void controllerDownReleased(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerLeftPressed(int)
	 */
	public void controllerLeftPressed(int controller) {
		
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerLeftReleased(int)
	 */
	public void controllerLeftReleased(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerRightPressed(int)
	 */
	public void controllerRightPressed(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerRightReleased(int)
	 */
	public void controllerRightReleased(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerUpPressed(int)
	 */
	public void controllerUpPressed(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#controllerUpReleased(int)
	 */
	public void controllerUpReleased(int controller) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseDragged(int, int, int, int)
	 */
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseClicked(int, int, int, int)
	 */
	public void mouseClicked(int button, int x, int y, int clickCount) {
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int x, int y) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.GameState#enter(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame)
	 */
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.GameState#leave(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame)
	 */
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseWheelMoved(int)
	 */
	public void mouseWheelMoved(int newValue) {
	}

}
