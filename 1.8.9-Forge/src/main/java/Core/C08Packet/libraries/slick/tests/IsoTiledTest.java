package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.BasicGame;
import Core.C08Packet.libraries.slick.GameContainer;
import Core.C08Packet.libraries.slick.Graphics;
import Core.C08Packet.libraries.slick.SlickException;
import Core.C08Packet.libraries.slick.tiled.TiledMap;
import Core.C08Packet.libraries.slick.util.Bootstrap;

/**
 * Simple test for isometric map rendering
 * 
 * @author kevin
 */
public class IsoTiledTest extends BasicGame {
	/** The tilemap we're going to render */
	private TiledMap tilemap;
	
	/**
	 * Create a new test
	 */
	public IsoTiledTest() {
		super("Isometric Tiled Map Test");
	}

	/*
	 * (non-Javadoc)
	 * @see me.kiras.andrew.libraries.slick.BasicGame#init(me.kiras.andrew.libraries.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		tilemap = new TiledMap("testdata/isoexample.tmx", "testdata/");
	}

	/*
	 * (non-Javadoc)
	 * @see me.kiras.andrew.libraries.slick.BasicGame#update(me.kiras.andrew.libraries.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * @see me.kiras.andrew.libraries.slick.Game#render(me.kiras.andrew.libraries.slick.GameContainer, me.kiras.andrew.libraries.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		tilemap.render(350,150);
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments passed in from the command line
	 */
	public static void main(String[] argv) {
		Bootstrap.runAsApplication(new IsoTiledTest(), 800,600,false);
	}
}
