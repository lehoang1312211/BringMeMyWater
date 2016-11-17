package com.group14.wheresmywater;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

/**
 * The Class SceneManager.
 */
public class SceneManager {

	// ---------------------------------------------
	// LIST SCENES
	// ---------------------------------------------
	/** The list game scene. */
	private ArrayList<BaseScene> listGameScene = null;

	// ---------------------------------------------
	// SCENES
	// ---------------------------------------------
	/** The splash scene. */
	private BaseScene _splashScene;

	/** The menu scene. */
	private BaseScene _menuScene;

	/** The load scene. */
	private BaseScene _loadScene;

	/** The game scene. */
	private BaseScene _gameScene;

	/** The score scene. */
	private BaseScene _scoreScene;

	// ---------------------------------------------
	// TYPE SCENES
	// ---------------------------------------------
	/**
	 * The Enum SceneType.
	 */
	public enum SceneType {
		/** The scene splash. */
		SCENE_SPLASH,
		/** The scene menu. */
		SCENE_MENU,
		/** The scene game. */
		SCENE_GAME,
		/** The scene loading. */
		SCENE_LOADING,
		/** The scene score. */
		SCENE_SCORE,
	}

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	/** The Constant INSTANCE. */
	private static final SceneManager INSTANCE = new SceneManager();

	/** The current scene. */
	private BaseScene _currentScene;

	/** The engine. */
	private Engine _engine = ResourcesManager.getInstance()._engine;

	/**
	 * Sets the scene.
	 * 
	 * @param scene
	 *            the new scene
	 */
	public final void setScene(final BaseScene scene) {
		_engine.setScene(scene);
		_currentScene = scene;
		// addScene(scene);
	}

	/**
	 * Adds the scene.
	 * 
	 * @param scene
	 *            the scene
	 */
	public final void addScene(final BaseScene scene) {
		switch (scene.getSceneType()) {
		case SCENE_SPLASH:
			_splashScene = scene;
			break;
		case SCENE_GAME:
			_gameScene = scene;
			break;
		case SCENE_LOADING:
			_loadScene = scene;
			break;
		case SCENE_MENU:
			_menuScene = scene;
			break;
		default:
			_splashScene = scene;
			break;
		}
	}

	/**
	 * Sets the scene.
	 * 
	 * @param type
	 *            the new scene
	 */
	public final void setScene(final SceneType type) {
		switch (type) {
		case SCENE_SPLASH:
			_currentScene = _splashScene;
			break;
		case SCENE_GAME:
			_currentScene = _gameScene;
			break;
		case SCENE_LOADING:
			_currentScene = _loadScene;
			break;
		case SCENE_MENU:
			_currentScene = _menuScene;
			break;
		default:
			break;
		}
		_engine.setScene(_currentScene);
	}

	/**
	 * Gets the current scene type.
	 * 
	 * @return the current scene type
	 */
	public final SceneType getCurrentSceneType() {
		return _currentScene.getSceneType();
	}

	/**
	 * Gets the current scene.
	 * 
	 * @return the current scene
	 */
	public final BaseScene getCurrentScene() {
		return _currentScene;
	}

	/**
	 * Sets the next scene.
	 * 
	 * @param idCurrent
	 *            the id current
	 * @return the base scene
	 */
	public final BaseScene setNextScene(final int idCurrent) {
		BaseScene scene = getScene(idCurrent + 1);
		this.setScene(scene);
		return scene;
	}

	/**
	 * Gets the scene.
	 * 
	 * @param id
	 *            the id
	 * @return the scene
	 */
	public final BaseScene getScene(final int id) {
		BaseScene scene = null;
		switch (id) {
		case 1:
			// scene = new Level01(this);
			this.setScene(scene);
			break;
		case 2:
			// scene = new Level02(this);
			this.setScene(scene);
			break;
		default:
			break;
		}

		return scene;
	}

	/**
	 * Gets the single instance of SceneManager.
	 * 
	 * @return single instance of SceneManager
	 */
	public static SceneManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Prepare manager.
	 */
	public static void prepareManager() {
		getInstance().listGameScene = new ArrayList<BaseScene>();
		getInstance().listGameScene.add(new Level01(1));
		getInstance().listGameScene.add(new Level02(1));
		getInstance().listGameScene.add(new Level03(1));
		getInstance().listGameScene.add(new Level04(1));
	}

	/**
	 * Creates the splash scene.
	 * 
	 * @param pOnCreateSceneCallback
	 *            the on create scene callback
	 */
	public final void createSplashScene(
			OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadSplashScreen();
		_splashScene = new SplashScene();
		_currentScene = _splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(_splashScene);
	}

	/**
	 * Dispose splash scene.
	 */
	private void disposeSplashScene() {
		ResourcesManager.getInstance().unloadSplashScreen();
		_splashScene.disposeScene();
		_splashScene = null;
	}

	/**
	 * Creates the menu scene.
	 */
	public final void createMenuScene() {
		ResourcesManager.getInstance().loadMainMenuScreen();
		ResourcesManager.getInstance().loadLoadingScreen();
		_menuScene = new MainMenuScene();
		_loadScene = new LoadingScene();
		setScene(_menuScene);
		disposeSplashScene();
	}

	/**
	 * Load level01 scene.
	 *
	 * @param mEngine
	 *            the m engine
	 */
	public final void loadLevel01Scene(final Engine mEngine) {
		setScene(_loadScene);
		ResourcesManager.getInstance().unloadMainMenuScreen();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadLevel01Screen();
						_gameScene = new Level01();
						setScene(_gameScene);
					}
				}));
	}

	/**
	 * Load score scene.
	 *
	 * @param mEngine
	 *            the m engine
	 */
	public final void loadScoreScene(final Engine mEngine) {
		// setScene(_loadScene);
		final BaseScene scene = _currentScene;
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadScoreScreen();
						_scoreScene = new ScoreScene();
						setScene(_scoreScene);
						((SmoothCamera) ResourcesManager.getInstance()._camera)
								.setCenter(400, 640);
						((SmoothCamera) ResourcesManager.getInstance()._camera)
								.setZoomFactor(1.0f);
						scene.disposeScene();
					}
				}));
	}

	/**
	 * Load game scene replay.
	 *
	 * @param mEngine
	 *            the m engine
	 */
	public final void loadGameSceneReplay(final Engine mEngine) {
		_currentScene.disposeScene();
		setScene(_loadScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						_gameScene.load();
						_gameScene = _gameScene.clone();
						setScene(_gameScene);
					}
				}));
	}

	/**
	 * Load game scene.
	 *
	 * @param id
	 *            the id
	 * @param mEngine
	 *            the m engine
	 */
	public final void loadGameScene(final int id, final Engine mEngine) {
		final int index = id - 1;
		_currentScene.disposeScene();
		setScene(_loadScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						listGameScene.get(index).load();
						_gameScene = listGameScene.get(index).clone();
						setScene(_gameScene);
					}
				}));
	}

	/**
	 * Load select level scene.
	 *
	 * @param mEngine
	 *            the m engine
	 */
	public final void loadSelectLevelScene(final Engine mEngine) {
		_currentScene.disposeScene();
		setScene(_loadScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadSelectLevelScreen();
						setScene(new SelectLevelScene());
					}
				}));
	}

	/**
	 * Load menu scene.
	 *
	 * @param mEngine
	 *            the m engine
	 */
	public final void loadMenuScene(final Engine mEngine) {
		_currentScene.disposeScene();
		setScene(_loadScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadMainMenuScreen();
						_menuScene = new MainMenuScene();
						setScene(_menuScene);
					}
				}));
	}
}
