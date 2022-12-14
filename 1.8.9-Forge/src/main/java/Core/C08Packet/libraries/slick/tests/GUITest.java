package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.AngelCodeFont;
import Core.C08Packet.libraries.slick.AppGameContainer;
import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.Color;
import Core.C08Packet.libraries.slick.Font;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.Input;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.gui.AbstractComponent;
import Core.C08Packet.libraries.slick.gui.ComponentListener;
import Core.C08Packet.libraries.slick.gui.MouseOverArea;
import Core.C08Packet.libraries.slick.gui.TextField;
import Core.C08Packet.libraries.slick.util.Log;

/**
 * A test for the GUI components available in Slick. Very simple stuff
 *
 * @author kevin
 */
public class GUITest extends BasicGame implements ComponentListener {
	/** The image being rendered */
	private Image image;
	/** The areas defined */
	private MouseOverArea[] areas = new MouseOverArea[4];
	/** The game container */
	private GameContainer container;
	/** The message to display */
	private String message = "Demo Menu System with stock images";
	/** The text field */
	private TextField field;
	/** The text field */
	private TextField field2;
	/** The background image */
	private Image background;
	/** The font used to render */
	private Font font;
	/** The container */
	private AppGameContainer app;
	
	/**
	 * Create a new test of GUI  rendering
	 */
	public GUITest() {
		super("GUI Test");
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#init(Core.C08Packet.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		if (container instanceof AppGameContainer) {
			app = (AppGameContainer) container;
			app.setIcon("testdata/icon.tga");
		}
		
		font = new AngelCodeFont("testdata/demo2.fnt","testdata/demo2_00.tga");
		field = new TextField(container, font, 150,20,500,35, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				message = "Entered1: "+field.getText();
				field2.setFocus(true);
			}
		});
		field2 = new TextField(container, font, 150,70,500,35,new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				message = "Entered2: "+field2.getText();
				field.setFocus(true);
			}
		});
		field2.setBorderColor(Color.red);
		
		this.container = container;
		
		image = new Image("testdata/logo.tga");
		background = new Image("testdata/dungeontiles.gif");
		container.setMouseCursor("testdata/cursor.tga", 0, 0);
		
		for (int i=0;i<4;i++) {
			areas[i] = new MouseOverArea(container, image, 300, 100 + (i*100), 200, 90, this);
			areas[i].setNormalColor(new Color(1,1,1,0.8f));
			areas[i].setMouseOverColor(new Color(1,1,1,0.9f));
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		background.draw(0, 0, 800, 500);
		
		for (int i=0;i<4;i++) {
			areas[i].render(container, g);
		}
		field.render(container, g);
		field2.render(container, g);
		
		g.setFont(font);
		g.drawString(message, 200, 550);
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
		if (key == Input.KEY_F2) {
			app.setDefaultMouseCursor();
		}
		if (key == Input.KEY_F1) {
			if (app != null) {
				try {
					app.setDisplayMode(640,480,false);		
				} catch (SlickException e) {
					Log.error(e);
				}
			}
		}
	}
	
	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments passed to the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new GUITest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.gui.ComponentListener#componentActivated(Core.C08Packet.libraries.slick.gui.AbstractComponent)
	 */
	public void componentActivated(AbstractComponent source) {
		System.out.println("ACTIVL : "+source);
		for (int i=0;i<4;i++) {
			if (source == areas[i]) {
				message = "Option "+(i+1)+" pressed!";
			}
		}
		if (source == field2) {
		}
	}
}
