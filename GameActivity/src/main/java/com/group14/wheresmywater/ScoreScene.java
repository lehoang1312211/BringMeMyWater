/*
 * File: ScoreScene.java. 
 */
package com.group14.wheresmywater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.os.Environment;

import com.group14.wheresmywater.SceneManager.SceneType;

/**
 * The Class ScoreScene.
 */
public class ScoreScene extends BaseScene implements IOnMenuItemClickListener {

	/** The resource. */
	private ScoreSceneResource _resource;

	/** The child scene. */
	private MenuScene childScene;

	/** The replay menu. */
	private final int REPLAY_MENU = 0;

	/** The level menu. */
	private final int LEVEL_MENU = 1;

	/** The next level menu. */
	private final int NEXT_LEVEL_MENU = 2;

	/** The timebonus. */
	private int timebonus = 0;

	/** The duckybonus. */
	private int duckybonus = 0;

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#createScene()
	 */
	@Override
	public void createScene() {
		_resource = ResourcesManager.getInstance()._scoreSceneResource;

		createBackground();
		createMenu();
		createMusic();
		createScore();
		createLevel();
		save();
		// CreateBackground();
	}

	/**
	 * Save score.
	 */
	private void save() {
		// TODO Auto-generated method stub
		String[] s = new String[Global.nScene];
		for (int i = 0; i < s.length; i++) {
			s[i] = String.valueOf(i + 1) + "," + "0" + "," + "0";
		}
		try {
			File root = Environment.getExternalStorageDirectory();
			FileReader filereader = new FileReader(new File(root, "wmw.txt"));
			BufferedReader reader = new BufferedReader(filereader);

			for (int i = 0; i < Global.nScene; i++) {
				String str = reader.readLine();
				if (str != null) {
					s[i] = str;
				}
			}
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (Integer.parseInt(s[Global.IDScene - 1].split(",")[2]) < Global.nDuckyHaveWater)
			s[Global.IDScene - 1] = String.valueOf(Global.IDScene) + "," + "1"
					+ "," + String.valueOf(Global.nDuckyHaveWater);
		
		try {
			File root = Environment.getExternalStorageDirectory();
			FileWriter filewriter = new FileWriter(new File(root, "wmw.txt"));
			BufferedWriter writer = new BufferedWriter(filewriter);
			for (int i = 0; i < s.length; i++) {
				writer.write(s[i] + '\n');
			}
			writer.close();
			;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the level.
	 */
	private void createLevel() {
		// TODO Auto-generated method stub
		TextureRegion reg = _resource.completeLevel_Region;
		switch (Global.nDuckyHaveWater) {
		case 1:
			reg = _resource.goodJobLevel_Region;
			break;
		case 2:
			reg = _resource.superLevel_Region;
			break;
		case 3:
			reg = _resource.excellentLevel_Region;
			break;
		default:
			break;
		}

		Sprite s = new Sprite(250, 70, 300, 160, reg, _vbom);
		this.attachChild(s);
	}

	/**
	 * Computing bonus.
	 *
	 * @param time the time
	 * @param nducky the nducky
	 */
	private void computingBonus(long time, int nducky) {
		// TODO Auto-generated method stub

		if (time < 5000)
			timebonus = 200;
		else if (time < 10000)
			timebonus = 150;
		else if (time < 15000)
			timebonus = 100;
		else if (time < 20000)
			timebonus = 50;

		duckybonus = nducky * 100;
	}

	/**
	 * Creates the score.
	 */
	private void createScore() {
		// TODO Auto-generated method stub
		computingBonus(Global.TimePlayGame, Global.nDuckyHaveWater);
		float top = 420;
		float left = 130;

		Text mText = new Text(left, top, _resource.mFont1, "Time Bonus", 1000,
				new TextOptions(HorizontalAlign.LEFT), _vbom);
		this.attachChild(mText);
		mText = new Text(left + 400, top, _resource.mFont1,
				String.valueOf(timebonus), 1000, new TextOptions(
						AutoWrap.LETTERS, 200, HorizontalAlign.RIGHT), _vbom);
		this.attachChild(mText);

		mText = new Text(left, top + 70, _resource.mFont1, "Ducky Bonus", 1000,
				new TextOptions(HorizontalAlign.LEFT), _vbom);
		this.attachChild(mText);
		mText = new Text(left + 400, top + 70, _resource.mFont1,
				String.valueOf(duckybonus), 1000, new TextOptions(
						AutoWrap.LETTERS, 200, HorizontalAlign.RIGHT), _vbom);
		this.attachChild(mText);

		Line l = new Line(left, top + 140, left + 600, top + 140, _vbom);
		l.setColor(Color.WHITE);
		l.setLineWidth(3);
		this.attachChild(l);

		mText = new Text(left, top + 160, _resource.mFont, "Score", 1000,
				new TextOptions(HorizontalAlign.LEFT), _vbom);
		this.attachChild(mText);
		mText = new Text(left + 400, top + 160, _resource.mFont,
				String.valueOf(duckybonus + timebonus), 1000, new TextOptions(
						AutoWrap.LETTERS, 200, HorizontalAlign.RIGHT), _vbom);
		this.attachChild(mText);
	}

	/**
	 * Creates the music.
	 */
	private void createMusic() {
		// TODO Auto-generated method stub
		_resource.music.setLooping(true);
		_resource.music.play();
	}

	/**
	 * Creates the background.
	 */
	private void createBackground() {
		// TODO Auto-generated method stub
		Sprite background = new Sprite(0, 0, Global.CAMERA_WIDTH,
				Global.CAMERA_HEIGHT, _resource.bg_Region, _vbom);
		this.setBackground(new SpriteBackground(background));
	}

	/**
	 * Creates the menu.
	 */
	private void createMenu() {
		// TODO Auto-generated method stub
		childScene = new MenuScene(this._camera);
		childScene.setPosition(55, 800);

		final IMenuItem replayMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(REPLAY_MENU, 210, 82,
						_resource.btnReplay_Region, _vbom), 1.1f, 1);
		final IMenuItem levelMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(LEVEL_MENU, 210, 82,
						_resource.btnSelectLevel_Region, _vbom), 1.1f, 1);
		final IMenuItem nextlevelMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(NEXT_LEVEL_MENU, 210, 82,
						_resource.btnNextLevel_Region, _vbom), 1.1f, 1);

		childScene.addMenuItem(replayMenuItem);
		childScene.addMenuItem(levelMenuItem);
		childScene.addMenuItem(nextlevelMenuItem);
		childScene.buildAnimations();
		childScene.setBackgroundEnabled(false);
		childScene.setOnMenuItemClickListener(this);

		replayMenuItem.setPosition(0, 0);
		levelMenuItem.setPosition(240, 0);
		nextlevelMenuItem.setPosition(480, 0);

		setChildScene(childScene);
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#onBackKeyPressed()
	 */
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#disposeScene()
	 */
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#getSceneType()
	 */
	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SCORE;
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener#onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene, org.andengine.entity.scene.menu.item.IMenuItem, float, float)
	 */
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		int id = pMenuItem.getID();
		boolean isDirect = false;
		switch (id) {
		case REPLAY_MENU:
			SceneManager.getInstance().loadGameSceneReplay(_engine);
			isDirect = true;
			break;
		case LEVEL_MENU:
			SceneManager.getInstance().loadSelectLevelScene(_engine);
			isDirect = true;
			break;
		case NEXT_LEVEL_MENU:
			if (Global.IDScene < Global.nScene) {
				SceneManager.getInstance().loadGameScene(Global.IDScene + 1,
						_engine);
				isDirect = true;
			}
			break;
		default:
			break;
		}
		if (_resource.music.isPlaying() && isDirect)
			_resource.music.stop();
		return false;
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#clone()
	 */
	@Override
	public BaseScene clone() {
		// TODO Auto-generated method stub
		return new ScoreScene();
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#load()
	 */
	@Override
	public void load() {
		// TODO Auto-generated method stub
		ResourcesManager.getInstance().loadScoreScreen();
	}
}
