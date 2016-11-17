package com.group14.wheresmywater;

import java.util.ArrayList;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Point;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.group14.wheresmywater.SceneManager.SceneType;

/**
 * The Class Level03.
 */
public class Level03 extends BaseScene implements IOnSceneTouchListener,
		IOnMenuItemClickListener {

	/** The _resource. */
	private Level03Resource _resource;

	/** The sprite rock. */
	private Sprite _spriteRock;

	/** The sprite wall. */
	private Sprite _spriteWall;

	/** The sprite cranky. */
	private AnimatedSprite _spriteCranky;

	/** The list soil. */
	private ArrayList<SoilArea> listSoil;

	/** The number row. */
	private int nRow;

	/** The number col. */
	private int nCol;

	/** The pos. */
	private Point pos;

	/** The width cell. */
	private float wCell;

	/** The height cell. */
	private float hCell;

	/** The physics world. */
	private PhysicsWorld mPhysicsWorld;

	/** The list water. */
	private ArrayList<Sprite> listWater;

	/** The list body. */
	private ArrayList<Body> listBody;

	/** The list ducky empty. */
	private ArrayList<Sprite> listDuckyEmpty;

	/** The list ducky. */
	private ArrayList<Sprite> listDucky;

	/** The rect pipe. */
	private Rectangle rectPipe;

	/** The child scene. */
	private MenuScene childScene;

	/** The restart menu. */
	private final int RESTART_MENU = 0;

	/** The pause menu. */
	private final int PAUSE_MENU = 1;

	/** The n water into room. */
	private int nWaterIntoRoom = 0;

	/** The number ducky have water. */
	public int nDuckyHaveWater = 0;

	/** The time start. */
	private long timeStart;

	/** The is game win. */
	private boolean isGameWin = false;

	/** The is game over. */
	private boolean isGameOver = false;

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#createScene()
	 */
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		_resource = ResourcesManager.getInstance()._level03Resource;

		// get time start Level
		timeStart = System.currentTimeMillis();

		// create PhysicWorld
		mPhysicsWorld = new PhysicsWorld(new Vector2(0,
				SensorManager.GRAVITY_EARTH), false);
		this.registerUpdateHandler(mPhysicsWorld);

		// create Object
		createWall();
		createSoil();
		createRock();
		createWater(390, 100, 80);
		createRectPipe();
		createMenu();
		createDuckyEmpty();
		createCranky();
		playMusic();

		this.setOnSceneTouchListener(this);
	}

	/**
	 * Instantiates a new level03.
	 */
	public Level03() {

	}

	/**
	 * Instantiates a new level03.
	 *
	 * @param unused the unused
	 */
	public Level03(int unused) {
		super(unused);
	}

	/**
	 * Creates the cranky.
	 */
	private void createCranky() {
		// TODO Auto-generated method stub
		addCranky(_resource.crankyWait_Region);
	}

	/**
	 * Adds the cranky.
	 *
	 * @param region the region
	 */
	private void addCranky(TiledTextureRegion region) {
		_spriteCranky = new AnimatedSprite(580, 1010, 200, 200, region, _vbom);
		_spriteCranky.setZIndex(3);
		_spriteCranky.animate(200);
		this.attachChild(_spriteCranky);
	}

	/**
	 * Creates the ducky empty.
	 */
	private void createDuckyEmpty() {
		// TODO Auto-generated method stub
		int nDucky = 3;
		float top = 10;
		float left = 10;
		listDuckyEmpty = new ArrayList<Sprite>();
		listDucky = new ArrayList<Sprite>();
		Vector2[] pos = {new Vector2(100, 550), new Vector2(693, 670),
				new Vector2(45, 995) };
		for (int i = 0; i < nDucky; i++) {
			float topEmpty = pos[i].y;
			float leftEmpty = pos[i].x;
			Sprite duckyEmpty = new Sprite(leftEmpty, topEmpty,
					_resource.listduckywater_region.get(0), _vbom);
			duckyEmpty.setTag(0);
			listDuckyEmpty.add(duckyEmpty);
			duckyEmpty.setZIndex(0);
			touch(leftEmpty + 32, topEmpty + 32);

			Sprite ducky = new Sprite(left + i * 70, top,
					_resource.listduckywater_region.get(0), _vbom);
			listDucky.add(ducky);
			duckyEmpty.setZIndex(4);

			this.attachChild(duckyEmpty);
			this.attachChild(ducky);
		}
	}

	/**
	 * Creates the menu.
	 */
	private void createMenu() {
		// TODO Auto-generated method stub
		childScene = new MenuScene(this._camera);
		childScene.setPosition(0, 0);

		final IMenuItem restartMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(RESTART_MENU, 75, 75,
						_resource.btnRePlay_Region, _vbom), 1.1f, 1);
		final IMenuItem pauseMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(PAUSE_MENU, 75, 75,
						_resource.btnPause_Region, _vbom), 1.1f, 1);

		childScene.addMenuItem(restartMenuItem);
		childScene.addMenuItem(pauseMenuItem);
		childScene.buildAnimations();
		childScene.setBackgroundEnabled(false);
		childScene.setOnMenuItemClickListener(this);

		restartMenuItem.setPosition(720 - 75 - 5, 10);
		pauseMenuItem.setPosition(720, 10);
		setChildScene(childScene);
	}

	/**
	 * Play music.
	 */
	private void playMusic() {
		// TODO Auto-generated method stub
		_resource.music.setLooping(true);
		_resource.music.play();
	}

	/**
	 * Creates the rect pipe.
	 */
	private void createRectPipe() {
		// TODO Auto-generated method stub
		rectPipe = new Rectangle(184, 1200, 43, 1, _vbom);
	}

	/**
	 * Creates the wall.
	 */
	private void createWall() {
		// TODO Auto-generated method stub
		_spriteWall = new Sprite(0, 0, Global.CAMERA_WIDTH,
				Global.CAMERA_HEIGHT, _resource.wall_Region, _vbom);
		_spriteWall.setZIndex(0);
		this.attachChild(_spriteWall);
	}

	/**
	 * Creates the soil.
	 */
	private void createSoil() {
		// TODO Auto-generated method stub
		listSoil = new ArrayList<SoilArea>();
		float WidthSoil = 800.0f;
		float HeightSoil = 1130.0f;
		pos = new Point(0, 150);
		nRow = 10;
		nCol = 10;
		wCell = WidthSoil / nCol;
		hCell = HeightSoil / nRow;

		for (int i = 0; i < nRow; i++) {
			for (int j = 0; j < nCol; j++) {
				SoilArea soil = new SoilArea();
				soil.addPoint(0, 0);
				if (!(i >= 7 && j >= 6)) {
					soil.addPoint(wCell, 0);
					soil.addPoint(wCell, hCell);
					soil.addPoint(0, hCell);
				}

				soil.setTouchable(true);
				soil.setPosition(j * wCell + pos.x, i * hCell + pos.y);
				listSoil.add(soil);
			}
		}

		touch(400, 150);
		touch(400, 170);
		touch(400, 190);
		touch(400, 210);

		touch(300, 150);
		touch(330, 165);
		touch(360, 180);
		touch(390, 195);

		touch(500, 150);
		touch(470, 165);
		touch(440, 180);
		touch(410, 195);

		for (SoilArea soil : listSoil) {
			soil.attachToScene(this, this._engine, mPhysicsWorld, _vbom);
		}
	}

	/**
	 * Creates the rock.
	 */
	private void createRock() {
		// TODO Auto-generated method stub
		_spriteRock = new Sprite(0, 0, 800, 1280, _resource.rock_Region, _vbom);
		_spriteRock.setZIndex(3);
		this.attachChild(_spriteRock);

		createBody();
	}

	/**
	 * Creates the body.
	 */
	private void createBody() {
		// TODO Auto-generated method stub
		FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		BodyProvider.generateBodies("body/body_level03.xml", mPhysicsWorld,
				FIXTURE_DEF);
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#onBackKeyPressed()
	 */
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		if (!isGameWin) {
			_activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					_engine.stop();
					_activity.showDialog(GameActivity.DIALOG_PAUSE);
				}
			});
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#disposeScene()
	 */
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		stopMusic();
		ResourcesManager.getInstance().unloadLevel03Screen();
		System.out.println("Level03 Dispose");
	}

	/**
	 * Stop music.
	 */
	private void stopMusic() {
		if (_resource.music.isPlaying())
			_resource.music.stop();
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#getSceneType()
	 */
	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	/**
	 * Creates the water.
	 *
	 * @param pX the p x
	 * @param pY the p y
	 * @param count the count
	 */
	protected void createWater(float pX, float pY, int count) {
		listWater = new ArrayList<Sprite>();
		listBody = new ArrayList<Body>();
		for (int i = 0; i < count; i++) {
			Sprite drop = new Sprite(pX, pY, 100, 100, _resource.water_Region,
					_vbom);
			drop.setZIndex(10);
			Rectangle rect = new Rectangle(pX + 42.5f, pY + 80, 10, 10, _vbom);

			final FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(1f,
					0f, 0f);
			Body body = PhysicsFactory.createCircleBody(mPhysicsWorld, rect,
					BodyType.DynamicBody, fixtureDef);

			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(drop,
					body));
			this.attachChild(drop);
			listWater.add(drop);
			listBody.add(body);
		}
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.IOnSceneTouchListener#onSceneTouchEvent(org.andengine.entity.scene.Scene, org.andengine.input.touch.TouchEvent)
	 */
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		final float x = pSceneTouchEvent.getX();
		final float y = pSceneTouchEvent.getY();
		touch(x, y);

		return true;
	}

	/**
	 * Touch.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private void touch(final float x, final float y) {
		int col = (int) ((x - pos.x) / wCell);
		int row = (int) ((y - pos.y) / hCell);

		for (int i = -1; i < 2; i++) {
			int newRow = row + i;
			for (int j = -1; j < 2; j++) {
				int newCol = col + j;
				if (newRow < 0 || newRow >= nRow || newCol < 0
						|| newCol >= nCol)
					continue;
				final int index = newRow * nCol + newCol;
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listSoil.get(index).touch(x, y);
					}
				});
				thread.start();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.Scene#onManagedUpdate(float)
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		super.onManagedUpdate(pSecondsElapsed);
		for (SoilArea soil : listSoil) {
			soil.Update();
		}
		sortChildren();
		updateWater();

		if (isGameWin == false && isGameOver == false && listWater.size() <= 0) {
			isGameOver = true;
			gameOver();
		}
	}

	/**
	 * Game over.
	 */
	private void gameOver() {
		stopMusic();
		_resource.soundCrankyCry.play();
		((SmoothCamera) _camera).setMaxVelocityX(200);
		((SmoothCamera) _camera).setCenter(600, 950);
		((SmoothCamera) _camera).setZoomFactor(2.0f);
		Thread threadGameOver = new ThreadGameOver();
		threadGameOver.start();
	}

	/**
	 * The Class ThreadGameOver.
	 */
	class ThreadGameOver extends Thread {

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2500);
				((SmoothCamera) _camera).setCenter(400, 640);
				((SmoothCamera) _camera).setZoomFactor(1.0f);
				Thread.sleep(2000);
				SceneManager.getInstance().loadGameSceneReplay(_engine);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Update water.
	 */
	private void updateWater() {
		for (int i = 0; i < listWater.size(); i++) {
			Sprite sprite = listWater.get(i);
			Rectangle rect1 = new Rectangle(sprite.getX() + 40f,
					sprite.getY() + 40.0f, 10, 10, _vbom);

			for (int j = 0; j < listDuckyEmpty.size(); j++) {
				Sprite ducky = listDuckyEmpty.get(j);
				if (rect1.collidesWith(ducky)) {
					excuteCollidesWithDucky(j);
					this.detachChild(sprite);
					Body b = listBody.get(i);
					mPhysicsWorld.destroyBody(b);
					listBody.remove(i);
					listWater.remove(i);
				}
			}

			if (sprite.getX() < -sprite.getWidth()
					|| sprite.getX() > Global.CAMERA_WIDTH
					|| sprite.getY() > Global.CAMERA_HEIGHT) {
				this.detachChild(sprite);
				Body b = listBody.get(i);
				mPhysicsWorld.destroyBody(b);
				listBody.remove(i);
				listWater.remove(i);
				i--;
			}
		}

		for (int i = 0; i < listWater.size(); i++) {
			Sprite sprite = listWater.get(i);
			if (sprite.collidesWith(rectPipe)) {
				excuteCollidesWithPipe(i);
				i--;
			}
		}
	}

	/**
	 * Excute collides with ducky.
	 *
	 * @param index the index
	 */
	private void excuteCollidesWithDucky(int index) {
		// TODO Auto-generated method stub
		Sprite duckyEmpty = listDuckyEmpty.get(index);
		int count = duckyEmpty.getTag() + 1;
		if (count >= 5) {
			this.detachChild(duckyEmpty);
			listDuckyEmpty.remove(index);
			_resource.soundDucky.play();
			Sprite ducky = listDucky.get(nDuckyHaveWater);
			this.detachChild(ducky);
			ducky = new Sprite(ducky.getX(), ducky.getY(),
					_resource.listduckywater_region.get(5), _vbom);
			this.attachChild(ducky);
			nDuckyHaveWater++;
		} else {
			this.detachChild(duckyEmpty);
			duckyEmpty = new Sprite(duckyEmpty.getX(), duckyEmpty.getY(),
					_resource.listduckywater_region.get(count), _vbom);
			duckyEmpty.setTag(count);
			listDuckyEmpty.remove(index);
			listDuckyEmpty.add(index, duckyEmpty);
			this.attachChild(duckyEmpty);
		}
	}

	/**
	 * Excute collides with pipe.
	 *
	 * @param index the index
	 */
	private void excuteCollidesWithPipe(int index) {
		Sprite sprite = listWater.get(index);

		// Test Win Game
		testWingame();

		// Move water into Room
		mPhysicsWorld.destroyBody(listBody.get(index));
		listWater.remove(index);
		listBody.remove(index);
		sprite.setPosition(530, 1000);
		Rectangle rect = new Rectangle(530 + 42.5f, 1000 + 80, 5, 5, _vbom);

		final FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(1f, 0f,
				5f);
		Body body = PhysicsFactory.createCircleBody(mPhysicsWorld, rect,
				BodyType.DynamicBody, fixtureDef);
		sprite.setZIndex(4);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite,
				body));
		index--;

		if (isGameWin == false)
			_resource.soundWaterDrop.play();
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener#onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene, org.andengine.entity.scene.menu.item.IMenuItem, float, float)
	 */
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		int id = pMenuItem.getID();
		switch (id) {
		case RESTART_MENU:
			if (isGameWin && isGameOver == false)
				return true;
			stopMusic();
			SceneManager.getInstance().loadGameSceneReplay(_engine);
			break;
		case PAUSE_MENU:
			if (!isGameWin) {
				_activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						_engine.stop();
						_activity.showDialog(GameActivity.DIALOG_PAUSE);
					}
				});
			}
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * Test wingame.
	 */
	private void testWingame() {
		if (isGameWin == true)
			return;
		nWaterIntoRoom += 1;
		if (nWaterIntoRoom >= 30) {
			isGameWin = true;
			this.detachChild(_spriteCranky);
			addCranky(_resource.crankyHaveWater_Region);
			Thread threadWin = new ThreadWinGame();
			threadWin.start();
			((SmoothCamera) _camera).setMaxVelocityX(200);
			((SmoothCamera) _camera).setCenter(600, 950);
			((SmoothCamera) _camera).setZoomFactor(2.0f);
		}
	}

	/**
	 * The Class ThreadWinGame.
	 */
	class ThreadWinGame extends Thread {

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Global.TimePlayGame = System.currentTimeMillis() - timeStart;
				Global.IDScene = 3;
				Global.nDuckyHaveWater = nDuckyHaveWater;
				_resource.soundGameWin.play();
				_resource.soundCrankyLaugh.play();
				stopMusic();
				Thread.sleep(4500);
				SceneManager.getInstance().loadScoreScene(_engine);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#clone()
	 */
	@Override
	public BaseScene clone() {
		// TODO Auto-generated method stub
		return new Level03();
	}

	/* (non-Javadoc)
	 * @see com.group14.wheresmywater.BaseScene#load()
	 */
	@Override
	public void load() {
		// TODO Auto-generated method stub
		ResourcesManager.getInstance().loadLevel03Screen();
	}
}