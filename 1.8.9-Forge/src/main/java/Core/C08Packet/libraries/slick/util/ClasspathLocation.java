package Core.C08Packet.libraries.slick.util;

import java.io.InputStream;
import java.net.URL;

/**
 * A resource location that searches the classpath
 * 
 * @author kevin
 */
public class ClasspathLocation implements ResourceLocation {
	/**
	 * @see Core.C08Packet.libraries.slick.util.ResourceLocation#getResource(String)
	 */
	public URL getResource(String ref) {
		String cpRef = ref.replace('\\', '/');
		return ResourceLoader.class.getClassLoader().getResource(cpRef);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.util.ResourceLocation#getResourceAsStream(String)
	 */
	public InputStream getResourceAsStream(String ref) {
		String cpRef = ref.replace('\\', '/');
		return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);	
	}

}
