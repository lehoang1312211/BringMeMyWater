package com.group14.wheresmywater;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * The Class Radio.
 */
public class Radio extends Sprite {

	/** The second time tolal. */
	float secondTimeTolal = 0;
	
	/** The second last rotate. */
	float secondLastRotate = 0;
	
	/** The delta. */
	float delta = 0.6f;
	
	/** The angle. */
	float angle = 0;

	/**
	 * Instantiates a new radio.
	 *
	 * @param pX the p x
	 * @param pY the p y
	 * @param pWidth the width
	 * @param pHeight the height
	 * @param pTextureRegion the texture region
	 * @param pVertexBufferObjectManager the vertex buffer object manager
	 */
	public Radio(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager); 
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	protected final void onManagedUpdate(final float pSecondsElapsed) {
		secondTimeTolal += pSecondsElapsed;
		int t = (int) ((secondTimeTolal - secondLastRotate) / delta);
		if (t > 0) {
			secondLastRotate = secondTimeTolal;
			angle = (angle == 0) ? 7 : 0;
		}
		this.setRotation(angle);
		super.onManagedUpdate(pSecondsElapsed);
	}
}
