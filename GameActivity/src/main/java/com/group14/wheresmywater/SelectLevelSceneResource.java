/*
 * File SelectLevelSceneResource.java.
 */
package com.group14.wheresmywater;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;

import android.graphics.Typeface;

/**
 * The Class SelectLevelSceneResource.
 */
public class SelectLevelSceneResource {

	/** The resource manager. */
	private ResourcesManager _resourceManager =
			ResourcesManager.getInstance();

	/** The TextureRegion of back button. */
	public TextureRegion btnBack_Region;

	/** The BitmapTextureAtlas of back button. */
	private BitmapTextureAtlas btnBackTextureAtlas;

	/** The TextureRegion of background. */
	public TextureRegion bg_Region;

	/** The BitmapTextureAtlas of background. */
	private BitmapTextureAtlas bgTextureAtlas;

	/** The TiledTextureRegion of menu. */
	public TiledTextureRegion menu_Region;

	/** The menu texture atlas. */
	private BitmapTextureAtlas menuTextureAtlas;

	/** The font. */
	public Font mFont;

	/** The music of level. */
	public Music music;

	/**
	 * Load Graphic and Audio.
	 */
	public void load() {
		loadGraphic();
		loadAudio();
	}

	/**
	 * Loa graphic.
	 */
	private void loadGraphic() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		btnBackTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		btnBack_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btnBackTextureAtlas,
						_resourceManager.getAssets(),
						"btnback.png", 0, 0);

		btnBackTextureAtlas.load();

		menuTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 320, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		menu_Region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuTextureAtlas,
						_resourceManager.getAssets(),
						"spriteimenu.png", 0, 0, 5, 1);
		menuTextureAtlas.load();

		bgTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 640, 812,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		bg_Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				bgTextureAtlas, _resourceManager.getAssets(),
				"bg_seleclevel.jpg", 0, 0);
		
		bgTextureAtlas.load();

		mFont = FontFactory.create(_resourceManager.getFontManager(),
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 60,
				Color.WHITE_ABGR_PACKED_INT);
		mFont.load();
	}

	/**
	 * Load audio.
	 */
	private void loadAudio() {
		// TODO Auto-generated method stub
		MusicFactory.setAssetBasePath("sfx/");
		try {
			music = MusicFactory.createMusicFromAsset(
					_resourceManager.getMusicManager(),
					_resourceManager.getApplicationContext(),
					"select_level_music.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unload.
	 */
	public void unload() {
		btnBackTextureAtlas.unload();
		btnBack_Region = null;

		bgTextureAtlas.unload();
		bg_Region = null;

		menuTextureAtlas.unload();
		menu_Region = null;
	}
}
