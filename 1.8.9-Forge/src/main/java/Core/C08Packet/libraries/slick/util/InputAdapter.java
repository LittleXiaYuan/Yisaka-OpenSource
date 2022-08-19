package Core.C08Packet.libraries.slick.util;

import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.InputListener;

/**
 * An implement implementation of the InputListener interface
 *
 * @author kevin
 */
public class InputAdapter implements InputListener {
	/** A flag to indicate if we're accepting input here */
	private boolean acceptingInput = true;
	
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
	 * @see Core.C08Packet.libraries.slick.InputListener#inputEnded()
	 */
	public void inputEnded() {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#isAcceptingInput()
	 */
	public boolean isAcceptingInput() {
		return acceptingInput;
	}

	/**
	 * Indicate if we should be accepting input of any sort
	 * 
	 * @param acceptingInput True if we should accept input
	 */
	public void setAcceptingInput(boolean acceptingInput) {
		this.acceptingInput = acceptingInput;
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
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseWheelMoved(int)
	 */
	public void mouseWheelMoved(int change) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#setInput(Core.C08Packet.libraries.slick.Input)
	 */
	public void setInput(Input input) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.InputListener#mouseClicked(int, int, int, int)
	 */
	public void mouseClicked(int button, int x, int y, int clickCount) {
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.ControlledInputReciever#inputStarted()
	 */
	public void inputStarted() {
		
	}
}
