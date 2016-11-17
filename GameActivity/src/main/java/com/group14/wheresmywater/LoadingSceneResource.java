package com.group14.wheresmywater;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.util.color.Color;

/**
 * The Class LoadingSceneResource.
 */
public class LoadingSceneResource {

	/** The resource manager. */
	private ResourcesManager resourceManager = ResourcesManager.getInstance();

	/** The font. */
	public Font _font;

	/**
	 * Load.
	 */
	public void load() {
		loadGraphic();
	}

	/**
	 * Load Graphic.
	 */
	private void loadGraphic() {
		// TODO Auto-generated method stub
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(resourceManager.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		_font = FontFactory.createStrokeFromAsset(resourceManager.getFontManager(), mainFontTexture, resourceManager.getAssets(), "font.ttf", 50, true, Color.WHITE_ARGB_PACKED_INT, 2, Color.BLACK_ARGB_PACKED_INT);
		_font.load();
	}

	/**
	 * Unload.
	 */
	public void unload() {

	}
}
