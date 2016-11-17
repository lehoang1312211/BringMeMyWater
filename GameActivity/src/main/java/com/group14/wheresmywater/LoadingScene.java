package com.group14.wheresmywater;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.group14.wheresmywater.SceneManager.SceneType;

/**
 * The Class LoadingScene.
 */
public class LoadingScene extends BaseScene {
	/** The _resource. */
	private LoadingSceneResource _resource;

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#createScene()
	 */
	@Override
	public final void createScene() {
		_resource = ResourcesManager.getInstance()._loadingSceneResource;
		 setBackground(new Background(Color.BLACK));  
		 attachChild(new Text(280, 1000, _resource._font, "Loading...", _vbom));
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#onBackKeyPressed()
	 */
	@Override
	public final void onBackKeyPressed() {
		return;
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#disposeScene()
	 */
	@Override
	public void disposeScene() {
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#getSceneType()
	 */
	@Override
	public final SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#clone()
	 */
	@Override
	public final BaseScene clone() {
		// TODO Auto-generated method stub
		return new LoadingScene();
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#load()
	 */
	@Override
	public final void load() {
		ResourcesManager.getInstance().loadLoadingScreen();
	}
}
