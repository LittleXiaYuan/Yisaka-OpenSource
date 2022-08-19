package Core.C08Packet.libraries.slick.tests.states;

import Core.C08Packet.libraries.slick.AngelCodeFont;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.Font;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.state.BasicGameState;
import Core.C08Packet.libraries.slick.state.StateBasedGame;
import Core.C08Packet.libraries.slick.state.transition.FadeInTransition;
import Core.C08Packet.libraries.slick.state.transition.FadeOutTransition;

/**
 * A simple test state to display an image and rotate it
 *
 * @author kevin
 */
public class TestState2 extends BasicGameState {
	/** The ID given to this state */
	public static final int ID = 2;
	/** The font to write the message with */
	private Font font;
	/** The image to be display */
	private Image image;
	/** The angle we'll rotate by */
	private float ang;
	/** The game holding this state */
	private StateBasedGame game;
	
	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#init(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		font = new AngelCodeFont("testdata/demo2.fnt","testdata/demo2_00.tga");
		image = new Image("testdata/logo.tga");
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setFont(font);
		g.setColor(Color.green);
		g.drawString("This is State 2", 200, 50);
		
		g.rotate(400,300,ang);
		g.drawImage(image,400-(image.getWidth()/2),300-(image.getHeight()/2));
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#update(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
		ang += delta * 0.1f;
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_1) {
			game.enterState(TestState1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		if (key == Input.KEY_3) {
			game.enterState(TestState3.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}
}
