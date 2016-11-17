package com.group14.wheresmywater;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

import com.group14.wheresmywater.SceneManager.SceneType;

/**
 * @author Thanh Quang
 *
 */
public abstract class BaseScene extends Scene {

	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------

	/**
	 * Engine.
	 */
	protected Engine _engine;
	/**
	 * Activity.
	 */
	protected Activity _activity;
	/**
	 * Resource Manager.
	 */
	protected ResourcesManager _resourcesManager;
	/**
	 * VertexBufferObjectManager.
	 */
	protected VertexBufferObjectManager _vbom;
	/**
	 * Camera.
	 */
	protected Camera _camera;

	// ---------------------------------------
	// CONTRUCTOR
	// ---------------------------------------

	/**
	 * Constructor.
	 */
	public BaseScene() {
		this._resourcesManager = ResourcesManager.getInstance();
        this._engine   = _resourcesManager._engine;
        this._activity = _resourcesManager._activity;
        this._vbom     = _resourcesManager._vbom;
        this._camera   = _resourcesManager._camera;
        createScene();
	}

	/**
	 *
	 * @param unused
	 * Constructor
	 */
	public BaseScene(final int unused) {
	}

	// ---------------------------------------
	// METHOD
	// ---------------------------------------
	/**
	 * Create Scene.
	 */
	public abstract void createScene();

	/**
	 * Excute KeyPressed.
	 */
	public abstract void onBackKeyPressed();

	/**
	 * Dispose Scene.
	 */
	public abstract void disposeScene();

	/**
	 * @return SceneType
	 */
	public abstract SceneType getSceneType();

	/**
	 * Clone Object
	 * @return BaseScene
	 */
	public abstract BaseScene clone();

	/**
	 * Load resource for Scene.
	 */
	public abstract void load();
}
