package Core.C08Packet.libraries.slick.svg.inkscape;

import Core.C08Packet.libraries.slick.geom.Ellipse;
import Core.C08Packet.libraries.slick.geom.Shape;
import Core.C08Packet.libraries.slick.geom.Transform;
import Core.C08Packet.libraries.slick.svg.Diagram;
import Core.C08Packet.libraries.slick.svg.Figure;
import Core.C08Packet.libraries.slick.svg.Loader;
import Core.C08Packet.libraries.slick.svg.NonGeometricData;
import Core.C08Packet.libraries.slick.svg.ParsingException;
import org.w3c.dom.Element;

/**
 * Processor for <ellipse> and <path> nodes marked as arcs
 *
 * @author kevin
 */
public class EllipseProcessor implements ElementProcessor {
	
	/**
	 * @see Core.C08Packet.libraries.slick.svg.inkscape.ElementProcessor#process(Core.C08Packet.libraries.slick.svg.Loader, Element, Core.C08Packet.libraries.slick.svg.Diagram, Core.C08Packet.libraries.slick.geom.Transform)
	 */
	public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
		Transform transform = Util.getTransform(element);
		transform = new Transform(t, transform);
		
		float x = Util.getFloatAttribute(element,"cx");
		float y = Util.getFloatAttribute(element,"cy");
		float rx = Util.getFloatAttribute(element,"rx");
		float ry = Util.getFloatAttribute(element,"ry");
		
		Ellipse ellipse = new Ellipse(x,y,rx,ry);
		Shape shape = ellipse.transform(transform);

		NonGeometricData data = Util.getNonGeometricData(element);
		data.addAttribute("cx", ""+x);
		data.addAttribute("cy", ""+y);
		data.addAttribute("rx", ""+rx);
		data.addAttribute("ry", ""+ry);
		
		diagram.addFigure(new Figure(Figure.ELLIPSE, shape, data, transform));
	}

	/**
	 * @see Core.C08Packet.libraries.slick.svg.inkscape.ElementProcessor#handles(Element)
	 */
	public boolean handles(Element element) {
		if (element.getNodeName().equals("ellipse")) {
			return true;
		}
		if (element.getNodeName().equals("path")) {
			if ("arc".equals(element.getAttributeNS(Util.SODIPODI, "type"))) {
				return true;
			}
		}
		
		return false;
	}

}
