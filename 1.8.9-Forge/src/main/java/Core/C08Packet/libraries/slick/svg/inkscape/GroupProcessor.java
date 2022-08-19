package Core.C08Packet.libraries.slick.svg.inkscape;

import Core.C08Packet.libraries.slick.geom.Transform;
import Core.C08Packet.libraries.slick.svg.Diagram;
import Core.C08Packet.libraries.slick.svg.Loader;
import Core.C08Packet.libraries.slick.svg.ParsingException;
import org.w3c.dom.Element;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class GroupProcessor implements ElementProcessor {

	/**
	 * @see Core.C08Packet.libraries.slick.svg.inkscape.ElementProcessor#handles(Element)
	 */
	public boolean handles(Element element) {
		if (element.getNodeName().equals("g")) {
			return true;
		}
		return false;
	}

	/**O
	 * @see Core.C08Packet.libraries.slick.svg.inkscape.ElementProcessor#process(Core.C08Packet.libraries.slick.svg.Loader, Element, Core.C08Packet.libraries.slick.svg.Diagram, Core.C08Packet.libraries.slick.geom.Transform)
	 */
	public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
		Transform transform = Util.getTransform(element);
		transform = new Transform(t, transform);
		
		loader.loadChildren(element, transform);
	}

}
