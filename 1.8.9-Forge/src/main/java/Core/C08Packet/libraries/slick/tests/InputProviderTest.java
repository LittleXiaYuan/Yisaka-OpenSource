package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.command.BasicCommand;
import Core.C08Packet.libraries.slick.command.Command;
import Core.C08Packet.libraries.slick.command.ControllerButtonControl;
import Core.C08Packet.libraries.slick.command.ControllerDirectionControl;
import Core.C08Packet.libraries.slick.command.InputProvider;
import Core.C08Packet.libraries.slick.command.InputProviderListener;
import Core.C08Packet.libraries.slick.command.KeyControl;
import Core.C08Packet.libraries.slick.command.MouseButtonControl;

/**
 * A test for abstract input via InputProvider
 *
 * @author kevin
 */
public class InputProviderTest extends BasicGame implements InputProviderListener {
	/** The command for attack */
	private Command attack = new BasicCommand("attack");
	/** The command for jump */
	private Command jump = new BasicCommand("jump");
	/** The command for jump */
	private Command run = new BasicCommand("run");
	/** The input provider abstracting input */
	private InputProvider provider;
	/** The message to be displayed */
	private String message = "";
	
	/**
	 * Create a new image rendering test
	 */
	public InputProviderTest() {
		super("InputProvider Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		provider = new InputProvider(container.getInput());
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), run);
		provider.bindCommand(new KeyControl(Input.KEY_A), run);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), run);
		provider.bindCommand(new KeyControl(Input.KEY_UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_W), jump);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), attack);
		provider.bindCommand(new MouseButtonControl(0), attack);
		provider.bindCommand(new ControllerButtonControl(0, 1), attack);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("Press A, W, Left, Up, space, mouse button 1,and gamepad controls",10,50);
		g.drawString(message,100,150);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.command.InputProviderListener#controlPressed(Core.C08Packet.libraries.slick.command.Command)
	 */
	public void controlPressed(Command command) {
		message = "Pressed: "+command;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.command.InputProviderListener#controlReleased(Core.C08Packet.libraries.slick.command.Command)
	 */
	public void controlReleased(Command command) {
		message = "Released: "+command;
	}
	
	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new InputProviderTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
