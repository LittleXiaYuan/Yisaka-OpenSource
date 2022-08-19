package Core.C08Packet.libraries.slick.tests;

import Core.C08Packet.libraries.slick.*;
import Core.C08Packet.libraries.slick.util.Log;

/**
 * Tests the SpriteSheetFont.
 *
 * @author Onno Scheffers
 */
public class SpriteSheetFontTest extends BasicGame {
   /**
    * The font we're going to use to render
    */
   private Font font;

   /**
    * Create a new test for font rendering
    */
   public SpriteSheetFontTest() {
      super("SpriteSheetFont Test");
   }

   /**
    * @see Core.C08Packet.libraries.slick.Game#init(Core.C08Packet.libraries.slick.GameContainer)
    */
   public void init(GameContainer container) throws SlickException {
      SpriteSheet sheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
      font = new SpriteSheetFont(sheet, ' ');
   }

   /**
    * @see Core.C08Packet.libraries.slick.BasicGame#render(Core.C08Packet.libraries.slick.GameContainer, Core.C08Packet.libraries.slick.Graphics)
    */
   public void render(GameContainer container, Graphics g) {
      g.setBackground(Color.gray);
      font.drawString(80, 5, "A FONT EXAMPLE", Color.red);
      font.drawString(100, 50, "A MORE COMPLETE LINE");
   }

   /**
    * @see Core.C08Packet.libraries.slick.BasicGame#update(Core.C08Packet.libraries.slick.GameContainer,int)
    */
   public void update(GameContainer container, int delta) throws SlickException {
   }

   /**
    * @see Core.C08Packet.libraries.slick.BasicGame#keyPressed(int, char)
    */
   public void keyPressed(int key, char c) {
      if (key == Input.KEY_ESCAPE) {
         net.minecraftforge.fml.common.FMLCommonHandler.instance().exitJava(0, true);
      }
      if (key == Input.KEY_SPACE) {
         try {
            container.setDisplayMode(640, 480, false);
         } catch (SlickException e) {
            Log.error(e);
         }
      }
   }

   /**
    * The container we're using
    */
   private static AppGameContainer container;

   /**
    * Entry point to our test
    *
    * @param argv The arguments passed in the test
    */
   public static void main(String[] argv) {
      try {
         container = new AppGameContainer(new SpriteSheetFontTest());
         container.setDisplayMode(800, 600, false);
         container.start();
      } catch (SlickException e) {
         e.printStackTrace();
      }
   }
}
