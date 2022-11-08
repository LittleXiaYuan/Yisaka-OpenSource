package Core.C08Packet.libraries.slick.tests;

import java.io.IOException;

import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.imageout.ImageOut;
import Core.C08Packet.libraries.slick.particles.ParticleIO;
import Core.C08Packet.libraries.slick.particles.ParticleSystem;

/**
 * A test for saving images
 *
 * @author kevin
 */
public class ImageOutTest extends BasicGame {
	/** The game container */
	private GameContainer container;
	/** The fire particle system */
	private ParticleSystem fire;
	/** The graphics context */
	private Graphics g;
	/** The image we're going to use to copy into */
	private Image copy;
	/** The message to display */
	private String message;
	
	/**
	 * Create a new image rendering test
	 */
	public ImageOutTest() {
		super("Image Out Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
		try {
			fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
		} catch (IOException e) {
			throw new SlickException("Failed to load particle systems", e);
		}
		
		copy = new Image(400,300);
		String[] formats = ImageOut.getSupportedFormats();
		message = "Formats supported: ";
		for (int i=0;i<formats.length;i++) {
			message += formats[i];
			if (i < formats.length - 1) {
				message += ",";
			}
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("T - TGA Snapshot", 10,50);
		g.drawString("J - JPG Snapshot", 10,70);
		g.drawString("P - PNG Snapshot", 10,90);

		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(copy, 200, 300);
		g.setDrawMode(Graphics.MODE_NORMAL);
		
		g.drawString(message, 10,400);
		g.drawRect(200,0,400,300);
		g.translate(400, 250);
		fire.render();
		this.g = g;
	}

	/**
	 * Capture and save to the specified file name
	 * 
	 * @param fname The name of the file to write to
	 * @throws SlickException Indicates a failure to capture or write
	 */
	private void writeTo(String fname) throws SlickException {
		g.copyArea(copy, 200,0);
		ImageOut.write(copy, fname);
		message = "Written "+fname;
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		fire.update(delta);
		
		if (container.getInput().isKeyPressed(Input.KEY_P)) {
			writeTo("ImageOutTest.png");
		}
		if (container.getInput().isKeyPressed(Input.KEY_J)) {
			writeTo("ImageOutTest.jpg");
		}
		if (container.getInput().isKeyPressed(Input.KEY_T)) {
			writeTo("ImageOutTest.tga");
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new ImageOutTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
	}
}