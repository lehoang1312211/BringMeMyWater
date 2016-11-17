/*
 * File SplashScene.java.
 */
package com.group14.wheresmywater;

import org.andengine.entity.sprite.Sprite;

import com.group14.wheresmywater.SceneManager.SceneType;

/**
 * The Class SplashScene.
 */
public class SplashScene extends BaseScene {

	/** The _resource. */
	private static SplashSceneResource _resource = ResourcesManager
			.getInstance()._splashSceneResource;

	/** The splash. */
	private Sprite splash;

	/*
	 * (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#createScene()
	 */
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		splash = new Sprite(0, 0, _resource.splash_region, _vbom);

		splash.setScale(1.5f);
		splash.setPosition(300, 540);
		attachChild(splash);
	}

	/*
	 * (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#onBackKeyPressed()
	 */
	@Override
	public void onBackKeyPressed() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#disposeScene()
	 */
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

	/*
	 * (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#getSceneType()
	 */
	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	/*
	 * (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#clone()
	 */
	@Override
	public BaseScene clone() {
		return new SplashScene();
	}

	/*
	 * (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#load()
	 */
	@Override
	public void load() {
		ResourcesManager.getInstance().loadSplashScreen();
	}

}
