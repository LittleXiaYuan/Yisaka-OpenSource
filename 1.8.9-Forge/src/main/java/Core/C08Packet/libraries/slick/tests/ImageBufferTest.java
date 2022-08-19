package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.ImageBuffer;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;

/**
 * A test for image buffer maniupulation rendering
 *
 * @author kevin
 */
public class ImageBufferTest extends BasicGame {
	/** The image we're currently displaying */
	private Image image;
	
	/**
	 * Create a new image buffer rendering test
	 */
	public ImageBufferTest() {
		super("Image Buffer Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		ImageBuffer buffer = new ImageBuffer(320,200);
		for (int x=0;x<320;x++) {
			for (int y=0;y<200;y++) {
				if (y == 20) {
					buffer.setRGBA(x, y, 255,255,255,255);
				} else {
					buffer.setRGBA(x, y, x,y,0,255);
				}
			}
		}
		image = buffer.getImage();
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		image.draw(50,50);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			net.minecraftforge.fml.common.FMLCommonHandler.instance().exitJava(0, true);
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new ImageBufferTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
