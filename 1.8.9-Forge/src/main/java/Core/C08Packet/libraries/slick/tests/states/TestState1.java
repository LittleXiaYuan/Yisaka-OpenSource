package Core.C08Packet.libraries.slick.tests.states;

import Core.C08Packet.libraries.slick.AngelCodeFont;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.Font;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.state.BasicGameState;
import Core.C08Packet.libraries.slick.state.GameState;
import Core.C08Packet.libraries.slick.state.StateBasedGame;
import Core.C08Packet.libraries.slick.state.transition.CrossStateTransition;
import Core.C08Packet.libraries.slick.state.transition.EmptyTransition;
import Core.C08Packet.libraries.slick.state.transition.FadeInTransition;
import Core.C08Packet.libraries.slick.state.transition.FadeOutTransition;

/**
 * A simple test state to display a message describing the test 
 *
 * @author kevin
 */
public class TestState1 extends BasicGameState {
	/** The ID given to this state */
	public static final int ID = 1;
	/** The font to write the message with */
	private Font font;
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
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("State Based Game Test", 100, 100);
		g.drawString("Numbers 1-3 will switch between states.", 150, 300);
		g.setColor(Color.red);
		g.drawString("This is State 1", 200, 50);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#update(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.state.BasicGameState#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		
		if (key == Input.KEY_2) {
			GameState target = game.getState(TestState2.ID);
			
			final long start = System.currentTimeMillis();
			CrossStateTransition t = new CrossStateTransition(target) {				
				public boolean isComplete() {
					return (System.currentTimeMillis() - start) > 2000;
				}

				public void init(GameState firstState, GameState secondState) {
				}
			};
			
			game.enterState(TestState2.ID, t, new EmptyTransition());
		}
		if (key == Input.KEY_3) {
			game.enterState(TestState3.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}
}
