/*
 * File: ScoreSceneResource.java.
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
import org.andengine.util.color.Color;

import android.graphics.Typeface;

/**
 * The Class ScoreSceneResource.
 */
public class ScoreSceneResource {

	/** The resource manager. */
	private ResourcesManager _resourceManager =
			ResourcesManager.getInstance();

	/** The TextureRegion of background. */
	public TextureRegion bg_Region;

	/** The BitmapTextureAtlas of background. */
	private BitmapTextureAtlas bgTextureAtlas;

	/** The TextureRegion of replay button. */
	public TextureRegion btnReplay_Region;

	/** The BitmapTextureAtlas of replay button. */
	private BitmapTextureAtlas btnReplayTextureAtlas;

	/** The btn next level_ region. */
	public TextureRegion btnNextLevel_Region;
	
	/** The btn next level texture atlas. */
	private BitmapTextureAtlas btnNextLevelTextureAtlas;

	/** The btn select level_ region. */
	public TextureRegion btnSelectLevel_Region;
	
	/** The btn select level texture atlas. */
	private BitmapTextureAtlas btnSelectLevelTextureAtlas;

	/** The complete level_ region. */
	public TextureRegion completeLevel_Region;
	
	/** The complete level texture atlas. */
	private BitmapTextureAtlas completeLevelTextureAtlas;

	/** The super level_ region. */
	public TextureRegion superLevel_Region;
	
	/** The super level texture atlas. */
	private BitmapTextureAtlas superLevelTextureAtlas;

	/** The good job level_ region. */
	public TextureRegion goodJobLevel_Region;
	
	/** The good job level texture atlas. */
	private BitmapTextureAtlas goodJobLevelTextureAtlas;

	/** The excellent level_ region. */
	public TextureRegion excellentLevel_Region;
	
	/** The excellent level texture atlas. */
	private BitmapTextureAtlas excellentLevelTextureAtlas;

	/** The font. */
	public Font mFont;
	
	/** The font1. */
	public Font mFont1;

	/** The music. */
	public Music music;

	/**
	 * Load Graphic and Audio.
	 */
	public void load() {
		loadGraphic();
		loadAudio();
	}

	/**
	 * Load graphic.
	 */
	private void loadGraphic() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		bgTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 302, 304,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bg_Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				bgTextureAtlas, _resourceManager.getAssets(), "bg_score.png",
				0, 0);
		bgTextureAtlas.load();

		btnReplayTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		btnReplay_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btnReplayTextureAtlas,
						_resourceManager.getAssets(),
						"btnreplay.png", 0, 0);

		btnReplayTextureAtlas.load();

		btnSelectLevelTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btnSelectLevel_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btnSelectLevelTextureAtlas,
						_resourceManager.getAssets(),
						"btnlevel.png", 0, 0);
		btnSelectLevelTextureAtlas.load();

		btnNextLevelTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btnNextLevel_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btnNextLevelTextureAtlas,
						_resourceManager.getAssets(),
						"btnnextlevel.png", 0, 0);

		btnNextLevelTextureAtlas.load();

		completeLevelTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		completeLevel_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(completeLevelTextureAtlas,
						_resourceManager.getAssets(),
						"levelcomplete.png", 0, 0);
		
		completeLevelTextureAtlas.load();

		superLevelTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		superLevel_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(superLevelTextureAtlas,
						_resourceManager.getAssets(),
						"super.png", 0, 0);

		superLevelTextureAtlas.load();

		goodJobLevelTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		goodJobLevel_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(goodJobLevelTextureAtlas,
						_resourceManager.getAssets(),
						"goodjob.png", 0, 0);

		goodJobLevelTextureAtlas.load();

		excellentLevelTextureAtlas = new BitmapTextureAtlas(
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		excellentLevel_Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(excellentLevelTextureAtlas,
						_resourceManager.getAssets(),
						"excellent.png", 0, 0);
		
		excellentLevelTextureAtlas.load();

		mFont = FontFactory.create(_resourceManager.getFontManager(),
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 60,
				Color.WHITE_ABGR_PACKED_INT);
		mFont.load();

		mFont1 = FontFactory.create(_resourceManager.getFontManager(),
				_resourceManager.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 40,
				Color.WHITE_ABGR_PACKED_INT);
		mFont1.load();
	}

	/**
	 * Load audio.
	 */
	private void loadAudio() {
		MusicFactory.setAssetBasePath("sfx/");
		try {
			music = MusicFactory
					.createMusicFromAsset(_resourceManager.getMusicManager(),
							_resourceManager.getApplicationContext(),
							"score_music.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unload.
	 */
	public void unload() {
		bgTextureAtlas.unload();
		bg_Region = null;

		btnReplayTextureAtlas.unload();
		btnReplay_Region = null;

		btnNextLevelTextureAtlas.unload();
		btnNextLevel_Region = null;

		btnSelectLevelTextureAtlas.unload();
		btnSelectLevel_Region = null;

		completeLevelTextureAtlas.unload();
		completeLevel_Region = null;

		superLevelTextureAtlas.unload();
		superLevel_Region = null;

		goodJobLevelTextureAtlas.unload();
		goodJobLevel_Region = null;

		excellentLevelTextureAtlas.unload();
		excellentLevel_Region = null;
	}
}
