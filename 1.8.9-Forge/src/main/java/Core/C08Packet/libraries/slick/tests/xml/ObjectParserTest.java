package Core.C08Packet.libraries.slick.tests.xml;

import Core.C08Packet.libraries.slick.util.xml.ObjectTreeParser;
import Core.C08Packet.libraries.slick.util.xml.SlickXMLException;

/**
 * A simple test to check that the object parser from XML works. Read the Javadoc of 
 * ObjectParser to work out whats going on here
 * 
 * @author kevin
 */
public class ObjectParserTest {

	/**
	 * Entity point to our test. Simple read some XML which should
	 * generate an object tree.
	 * 
	 * @param argv The arguments passed into the test
	 * @throws SlickXMLException Indicates a failure to parse XML or generate objects
	 */
	public static void main(String[] argv) throws SlickXMLException {
		ObjectTreeParser parser = new ObjectTreeParser("Core.C08Packet.libraries.slick.tests.xml");
		parser.addElementMapping("Bag", ItemContainer.class);
		
		GameData parsedData = (GameData) parser.parse("testdata/objxmltest.xml");
		parsedData.dump("");
	}
}
