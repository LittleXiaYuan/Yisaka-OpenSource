package Core.C08Packet.libraries.slick.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

/**
 * An image data implementation which represents an empty texture
 * 
 * @author kevin
 */
public class EmptyImageData implements ImageData {
	/** The width of the data */
	private int width;
	/** The height of the data */
	private int height;
	
	/**
	 * Create an empty image data source
	 * 
	 * @param width The width of the source
	 * @param height The height of the source
	 */
	public EmptyImageData(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.opengl.ImageData#getDepth()
	 */
	public int getDepth() {
		return 32;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.opengl.ImageData#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.opengl.ImageData#getImageBufferData()
	 */
	public ByteBuffer getImageBufferData() {
		return BufferUtils.createByteBuffer(getTexWidth() * getTexHeight() * 4);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.opengl.ImageData#getTexHeight()
	 */
	public int getTexHeight() {
		return InternalTextureLoader.get2Fold(height);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.opengl.ImageData#getTexWidth()
	 */
	public int getTexWidth() {
		return InternalTextureLoader.get2Fold(width);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.opengl.ImageData#getWidth()
	 */
	public int getWidth() {
		return width;
	}

}
