package Core.C08Packet.libraries.slick.particles.effects;

import Core.C08Packet.libraries.slick.Image;
import Core.C08Packet.libraries.slick.particles.Particle;
import Core.C08Packet.libraries.slick.particles.ParticleEmitter;
import Core.C08Packet.libraries.slick.particles.ParticleSystem;

/**
 * A stock effect for fire usin the particle system
 *
 * @author kevin
 */
public class FireEmitter implements ParticleEmitter {
	/** The x coordinate of the center of the fire effect */
	private int x;
	/** The y coordinate of the center of the fire effect */
	private int y;
	
	/** The particle emission rate */
	private int interval = 50;
	/** Time til the next particle */
	private int timer;
	/** The size of the initial particles */
	private float size = 40;
	
	/**
	 * Create a default fire effect at 0,0
	 */
	public FireEmitter() {
	}

	/**
	 * Create a default fire effect at x,y
	 * 
	 * @param x The x coordinate of the fire effect
	 * @param y The y coordinate of the fire effect
	 */
	public FireEmitter(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Create a default fire effect at x,y
	 * 
	 * @param x The x coordinate of the fire effect
	 * @param y The y coordinate of the fire effect
	 * @param size The size of the particle being pumped out
	 */
	public FireEmitter(int x, int y, float size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#update(Core.C08Packet.libraries.slick.particles.ParticleSystem, int)
	 */
	public void update(ParticleSystem system, int delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = interval;
			Particle p = system.getNewParticle(this, 1000);
			p.setColor(1, 1, 1, 0.5f);
			p.setPosition(x, y);
			p.setSize(size);
			float vx = (float) (-0.02f + (Math.random() * 0.04f));
			float vy = (float) (-(Math.random() * 0.15f));
			p.setVelocity(vx,vy,1.1f);
		}
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#updateParticle(Core.C08Packet.libraries.slick.particles.Particle, int)
	 */
	public void updateParticle(Particle particle, int delta) {
		if (particle.getLife() > 600) {
			particle.adjustSize(0.07f * delta);
		} else {
			particle.adjustSize(-0.04f * delta * (size / 40.0f));
		}
		float c = 0.002f * delta;
		particle.adjustColor(0,-c/2,-c*2,-c/4);
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#completed()
	 */
	public boolean completed() {
		return false;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#useAdditive()
	 */
	public boolean useAdditive() {
		return false;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#getImage()
	 */
	public Image getImage() {
		return null;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#usePoints(Core.C08Packet.libraries.slick.particles.ParticleSystem)
	 */
	public boolean usePoints(ParticleSystem system) {
		return false;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#isOriented()
	 */
	public boolean isOriented() {
		return false;
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#wrapUp()
	 */
	public void wrapUp() {
	}

	/**
	 * @see Core.C08Packet.libraries.slick.particles.ParticleEmitter#resetState()
	 */
	public void resetState() {
	}
}
