package Core.C08Packet.libraries.slick.tests;

import java.awt.Font;
import java.util.ArrayList;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.TrueTypeFont;

/**
 * A test of the font rendering capabilities
 * 
 * @author James Chambers (Jimmy)
 * @author Kevin Glass (kevglass)
 */
public class TrueTypeFontPerformanceTest extends BasicGame {
	/** The java.awt font we're going to test */
	private Font awtFont;
	/** The True Type font we're going to use to render */
	private TrueTypeFont font;

	/** The test text */
	private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
	/** The text broken into lines */
	private ArrayList lines = new ArrayList();
	/** True if the text is visible */
	private boolean visible = true;

	/**
	 * Create a new test for font rendering
	 */
	public TrueTypeFontPerformanceTest() {
		super("Font Performance Test");
	}

	/**
	 * @see Core.C08Packet.libraries.slick.Game#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		awtFont = new Font("Verdana", Font.PLAIN, 16);
		font = new TrueTypeFont(awtFont, false);

		for (int j = 0; j < 2; j++) {
			int lineLen = 90;
			for (int i = 0; i < text.length(); i += lineLen) {
				if (i + lineLen > text.length()) {
					lineLen = text.length() - i;
				}

				lines.add(text.substring(i, i + lineLen));
			}
			lines.add("");
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer,
	 *      Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.setFont(font);

		if (visible) {
			for (int i = 0; i < lines.size(); i++) {
				font.drawString(10, 50 + (i * 20), (String) lines.get(i),
						i > 10 ? Color.red : Color.green);
			}
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer,
	 *      int)
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			net.minecraftforge.fml.common.FMLCommonHandler.instance().exitJava(0, true);
		}
		if (key == Input.KEY_SPACE) {
			visible = !visible;
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv
	 *            The arguments passed in the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(
					new TrueTypeFontPerformanceTest());
			container.setDisplayMode(800, 600, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
