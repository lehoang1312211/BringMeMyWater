/**
 * File: SplashSceneResouce.java.
 */
package com.group14.wheresmywater;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * The Class SplashSceneResource.
 */
public class SplashSceneResource {

	/** The resource manager. */
	private ResourcesManager resourceManager = ResourcesManager.getInstance();

	/** The splash_region. */
	public ITextureRegion splash_region;

	/** The splash texture atlas. */
	private BitmapTextureAtlas splashTextureAtlas;

	/**
	 * Load graphics.
	 */
	public void load() {
		loadGraphic();
	}

	/**
	 * Load graphic.
	 */
	private void loadGraphic() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(
				resourceManager.getTextureManager(), 188, 188,
				TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTextureAtlas, resourceManager._activity, "splash1.png",
				0, 0);
		splashTextureAtlas.load();
	}

	/**
	 * Unload.
	 */
	public void unload() {
		splashTextureAtlas.unload();
		splash_region = null;
	}
}
