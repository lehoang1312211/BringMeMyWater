package com.group14.wheresmywater;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class GameActivity extends BaseGameActivity implements OnClickListener {

	public static final int DIALOG_PAUSE = 1;

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	/**
	 * Font
	 */
	public Font mFont;

	/**
	 * Camera
	 */
	public SmoothCamera mCamera;

	/**
	 * Scene Current
	 */
	public Scene mCurrentScene;

	/**
	 * Scene Manager
	 */
	public SceneManager mSceneManager;

	/**
	 * INSTANCE of GameActivity
	 */
	private static BaseGameActivity INSTANCE;

	// ---------------------------------------------
	//
	// ---------------------------------------------
	@Override
	protected final synchronized void onCreateGame() {
		super.onCreateGame();
		INSTANCE = this;
	}

	@Override
	public final Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public final EngineOptions onCreateEngineOptions() {
		this.mCamera = new SmoothCamera(0, 0, Global.CAMERA_WIDTH,
				Global.CAMERA_HEIGHT, 10, 400, 1.0f);
		EngineOptions engOps = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_SENSOR, new RatioResolutionPolicy(
						Global.CAMERA_WIDTH, Global.CAMERA_HEIGHT),
				this.mCamera);
		engOps.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engOps.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engOps;
	}

	@Override
	public final void onCreateResources(
			final OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		ResourcesManager.prepareManager(mEngine, this, mCamera,
				getVertexBufferObjectManager());
		SceneManager.prepareManager();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public final void onCreateScene(
			final OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
		pOnCreateSceneCallback.onCreateSceneFinished(mSceneManager
				.getCurrentScene());
	}

	@Override
	public final void onPopulateScene(final Scene pScene,
			final OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						SceneManager.getInstance().createMenuScene();
					}
				}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	protected final void onDestroy() {
		super.onDestroy();
		if (this.isGameLoaded()) {
			System.exit(0);
		}
	}

	/**
	 * Get Context Activity
	 * @return context
	 */
	public static Context getContext() {
		return INSTANCE;
	}

	@Override
	protected final void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * @param keyCode : KeyCode
	 * @param event   : event when KeyDown
	 */
	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}

	/**
	 * Dialog
	 */
	private Dialog dialog;

	@Override
	protected final Dialog onCreateDialog(final int id) {
		switch (id) {
		case DIALOG_PAUSE:
			dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.dialog);
			dialog.setOnKeyListener(new Dialog.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface arg0, int keyCode,
						KeyEvent event) {
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						mEngine.start();
						dialog.dismiss();
					}
					return true;
				}
			});

			Button btnResume = (Button) dialog.findViewById(R.id.btnresume);
			Button btnMenu = (Button) dialog.findViewById(R.id.btnmenu);
			Button btnLevels = (Button) dialog.findViewById(R.id.btnlevels);

			btnResume.setOnClickListener(this);
			btnMenu.setOnClickListener(this);
			btnLevels.setOnClickListener(this);

			return dialog;
		default:
			return null;
		}
	}

	@Override
	public final void onClick(final View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnresume:
			mEngine.start();
			dialog.dismiss();
			break;
		case R.id.btnmenu:
			mEngine.start();
			SceneManager.getInstance().loadMenuScene(mEngine);
			dialog.dismiss();
			break;
		case R.id.btnlevels:
			mEngine.start();
			SceneManager.getInstance().loadSelectLevelScene(mEngine);
			dialog.dismiss();
			break;
		default:
			break;
		}
	}
}
