package Core.C08Packet.libraries.slick.svg.inkscape;

import Core.C08Packet.libraries.slick.geom.Rectangle;
import Core.C08Packet.libraries.slick.geom.Shape;
import Core.C08Packet.libraries.slick.geom.Transform;
import Core.C08Packet.libraries.slick.svg.Diagram;
import Core.C08Packet.libraries.slick.svg.Figure;
import Core.C08Packet.libraries.slick.svg.Loader;
import Core.C08Packet.libraries.slick.svg.NonGeometricData;
import Core.C08Packet.libraries.slick.svg.ParsingException;
import org.w3c.dom.Element;

/**
 * A processor for the <rect> element.
 *
 * @author kevin
 */
public class RectProcessor implements ElementProcessor {

	/**
	 * @see Core.C08Packet.libraries.slick.svg.inkscape.ElementProcessor#process(Core.C08Packet.libraries.slick.svg.Loader, Element, Core.C08Packet.libraries.slick.svg.Diagram, Core.C08Packet.libraries.slick.geom.Transform)
	 */
	public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
		Transform transform = Util.getTransform(element);
	    transform = new Transform(t, transform); 
		
		float width = Float.parseFloat(element.getAttribute("width"));
		float height = Float.parseFloat(element.getAttribute("height"));
		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		
		Rectangle rect = new Rectangle(x,y,width+1,height+1);
		Shape shape = rect.transform(transform);
		
		NonGeometricData data = Util.getNonGeometricData(element);
		data.addAttribute("width", ""+width);
		data.addAttribute("height", ""+height);
		data.addAttribute("x", ""+x);
		data.addAttribute("y", ""+y);
		
		diagram.addFigure(new Figure(Figure.RECTANGLE, shape, data, transform));
	}

	/**
	 * @see Core.C08Packet.libraries.slick.svg.inkscape.ElementProcessor#handles(Element)
	 */
	public boolean handles(Element element) {
		if (element.getNodeName().equals("rect")) {
			return true;
		}
		
		return false;
	}
}
