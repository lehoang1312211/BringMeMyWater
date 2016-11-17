/*
 * File: SelectLevelScene.java.
 */
package com.group14.wheresmywater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;

import android.os.Environment;
import android.util.Log;

import com.group14.wheresmywater.SceneManager.SceneType;

/**
 * The Class SelectLevelScene.
 */
public class SelectLevelScene extends BaseScene implements
		IOnMenuItemClickListener {

	/** The resource. */
	private SelectLevelSceneResource _resource;

	/** The imenu list. */
	ArrayList<IMenuItem> imenuList;

	/** The child scene. */
	private MenuScene childScene;

	/** The indexs. */
	private int[] indexs;

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#createScene()
	 */
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		_resource = ResourcesManager.getInstance()._selectLevelResource;

		imenuList = new ArrayList<IMenuItem>();
		createMenuItem();
		createBtnBack();
		createBackground();
		createMusic();
	}

	/**
	 * Creates the music.
	 */
	private void createMusic() {
		_resource.music.setLooping(true);
		_resource.music.play();
	}

	/**
	 * Creates the menu item.
	 */
	private void createMenuItem() {
		String[] s = getInfoGame();
		if (s == null) {
			return;
		}

		childScene = new MenuScene(this._camera);
		childScene.setPosition(0, 0);

		int col = 3;
		indexs = new int[Global.nScene];
		int size = 200;
		boolean played = false;
		
		for (int i = 0; i < Global.nScene; i++) {
			Log.i("Flag 3 check string",s[i]);
			String[] info = s[i].split(",");
			indexs[i] = getIndexMenuItem(info);
			if ((indexs[i] == 4) && ((i == 0) || (played == true))) {
				indexs[i] = 0;
			}
			
			IMenuItem menuitem = new ScaleMenuItemDecorator(
					new SpriteMenuItem(
					i + 1, size, size,
					_resource.menu_Region.getTextureRegion(indexs[i]), _vbom),
					1.1f, 1);
			
			this.imenuList.add(menuitem);
			childScene.addMenuItem(menuitem);
			played = (Integer.parseInt(info[1]) != 0);
		}

		childScene.buildAnimations();
		childScene.setBackgroundEnabled(false);
		childScene.setOnMenuItemClickListener(this);

		for (int i = 0; i < Global.nScene; i++) {
			int r = i / col;
			int c = i % col;
			
			IMenuItem item = this.imenuList.get(i);
			item.setPosition(50 + (size + 50) * c, 150 + (size + 30) * r);
			
			if (i < indexs.length && indexs[i] != 4) {
				Text mText = new Text(size / 2 - 15, 45, _resource.mFont,
						s[i].split(",")[0], 1000, new TextOptions(
								HorizontalAlign.LEFT), _vbom);
				item.attachChild(mText);
			}
		}

		setChildScene(childScene);
	}

	/**
	 * Gets the index menu item.
	 *
	 * @param info the info
	 * @return the index menu item
	 */
	private int getIndexMenuItem(String[] info) {
		if (Integer.parseInt(info[1]) == 0) {
			return 4;
		}
		
		int nDuck = Integer.parseInt(info[2]);
		
		return nDuck;
	}

	/**
	 * Gets the info game.
	 *
	 * @return the info game
	 */
	private String[] getInfoGame() {
		// TODO Auto-generated method stub
		String[] s = new String[Global.nScene];
		Log.i("Reading GameInfo","Reading....");
		try {
			File root = Environment.getExternalStorageDirectory();
			FileReader filereader = new FileReader(new File(root, "wmw.txt"));
			BufferedReader reader = new BufferedReader(filereader);
			int lvlCount=0;
			for (int i = 0; i < Global.nScene; i++) {
				s[i] = reader.readLine();
				Log.d("Print String",s[i]);
				if(s[i].length()>4) {
					lvlCount=lvlCount+1;
				}
			}

			if(lvlCount < Global.nScene)
			{
				updateLevels(lvlCount);
				for(int i=0;i<Global.nScene-lvlCount;i++)
				{
					s[lvlCount+i]=String.valueOf(i+1+lvlCount) + "," + "0" + "," + "0";
				}
			}
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}

	/**
	 * Creates the back button
	 */
	private void createBtnBack() {
		IMenuItem btnBack = new ScaleMenuItemDecorator(
				new SpriteMenuItem(0,
				100, 100, 
				_resource.btnBack_Region, _vbom), 1.1f, 1);
		childScene.addMenuItem(btnBack);
		btnBack.setPosition(10, 10);
	}

	/**
	 * Creates the background.
	 */
	private void createBackground() {
		Sprite background = new Sprite(
				0, 0,
				Global.CAMERA_WIDTH,
				Global.CAMERA_HEIGHT,
				_resource.bg_Region, _vbom);

		this.setBackground(new SpriteBackground(background));
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
		ResourcesManager.getInstance().unloadSelectLevelScreen();
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#getSceneType()
	 */
	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener#onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene, org.andengine.entity.scene.menu.item.IMenuItem, float, float)
	 */
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		int id = pMenuItem.getID();

		if (id == 0) {
			SceneManager.getInstance().loadMenuScene(_engine);
			stopMusic();
		} else {
			if (indexs[id - 1] != 4) {
				SceneManager.getInstance().loadGameScene(id, _engine);
				stopMusic();
			}
		}

		return true;
	}

	/**
	 * Stop music.
	 */
	private void stopMusic() {
		if (_resource.music.isPlaying()) {
			_resource.music.stop();
		}
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#clone()
	 */
	@Override
	public BaseScene clone() {
		return new SelectLevelScene();
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#load()
	 */
	@Override
	public void load() {
		ResourcesManager.getInstance();
	}

	private void updateLevels(int lvlCount)
	{
		try {
			File root = Environment.getExternalStorageDirectory();
			FileWriter filewriter = new FileWriter(new File(root, "wmw.txt"),true);
			BufferedWriter writer = new BufferedWriter(filewriter);
			for(int i=0;i<Global.nScene-lvlCount;i++)
			{
				writer.newLine();
				String s = String.valueOf(i+1+lvlCount) + "," + "0" + "," + "0";
				writer.write(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
