package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.BigImage;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.SpriteSheet;

/**
 * A test for big images used as sprites sheets
 *
 * @author kevin
 */
public class BigSpriteSheetTest extends BasicGame {
	/** The original 1024x768 image loaded */
	private Image original;
	/** A sprite sheet made from the big image */
	private SpriteSheet bigSheet;
	/** True if we should use the old method */
	private boolean oldMethod = true;
	
	/**
	 * Create a new image rendering test
	 */
	public BigSpriteSheetTest() {
		super("Big SpriteSheet Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		original = new BigImage("testdata/bigimage.tga", Image.FILTER_NEAREST, 256);
		bigSheet = new SpriteSheet(original, 16, 16);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		if (oldMethod) {
			for (int x=0;x<43;x++) {
				for (int y=0;y<27;y++) {
					bigSheet.getSprite(x, y).draw(10+(x*18),50+(y*18));
				}
			}
		} else {
			bigSheet.startUse();
			for (int x=0;x<43;x++) {
				for (int y=0;y<27;y++) {
					bigSheet.renderInUse(10+(x*18),50+(y*18),x,y);
				}
			}
			bigSheet.endUse();
		}
		
		g.drawString("Press space to toggle rendering method",10,30);
		
		container.getDefaultFont().drawString(10, 100, "TEST");
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new BigSpriteSheetTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
			oldMethod = !oldMethod;
		}
	}
}
