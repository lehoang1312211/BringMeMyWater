/**
 * @author HHS
 */
package com.group14.wheresmywater;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import java.util.Vector;

import org.andengine.engine.Engine;
import org.andengine.engine.Engine.EngineLock;
import org.andengine.entity.primitive.Polygon;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;


/**
 * Create SoilArea on screen.
 * @author HHS, TPho, HNam
 */
public class SoilArea {
	
	/** The base poly. */
	private Poly mBasePoly;
	
	/** The position x. */
	private float mPositionX;
	
	/** The position y. */
	private float mPositionY;

	/** The fill color. */
	private Color mFillColor;
	
	/** The hole poly color. */
	private Color mHolePolyColor;
	
	/** The line color. */
	private Color mLineColor;
	
	/** The line width. */
	private float mLineWidth;

	/** The touchable. */
	private boolean mTouchable;

	/** The touch radius. */
	private int mTouchRadius;

	/** The current polygons. */
	private Vector<Polygon> mCurrPolygons;

	/** The curr polygon bodies. */
	private Vector<Body> mCurrPolygonBodies;

	/** The scene. */
	private Scene mScene;

	/** The vertex buffer object manager. */
	private VertexBufferObjectManager mVertexBuffObjManager;

	/** The engine. */
	private Engine mEngine;

	/** The PhysicsWorld object. */
	private PhysicsWorld mPhysicsWorld;

	/** The fixture def of soilArea. */
	private final FixtureDef FIXTURE_DEF = 
			PhysicsFactory.createFixtureDef(0f, 0f, 0.0f);

	/**
	 * Gets the positionX.
	 *
	 * @return positionX of this soilArea
	 */
	public float getX() {
		return mPositionX;
	}

	/**
	 * Get the positionY.
	 * @return positionY
	 */
	public float getY() {
		return mPositionY;
	}

	/**
	 * Set position of soilArea on screen.
	 *
	 * @param pX : positionX
	 * @param pY : positionY
	 */
	public void setPosition(final float pX, final float pY) {
		mPositionX = pX;
		mPositionY = pY;
	}

	/**
	 * Touchable SoilArea.
	 *
	 * @return true, if successful
	 */
	public boolean touchable() {
		return mTouchable;
	}

	/**
	 * Allow user touch on soilArea.
	 *
	 * @param touchable the new touchable
	 */
	public void setTouchable(boolean touchable) {
		mTouchable = touchable;
	}

	/**
	 * Gets the fill color.
	 *
	 * @return The background color
	 */
	public Color getFillColor() {
		return mFillColor;
	}

	/**
	 * Set the background color.
	 *
	 * @param fillColor The background color
	 */
	public void setFillColor(Color fillColor) {
		mFillColor = fillColor;
	}

	/**
	 * Gets the line color.
	 *
	 * @return The color of line
	 */
	public Color getLineColor() {
		return mLineColor;
	}

	/**
	 * Set the line's color.
	 *
	 * @param lineColor the new line color
	 */
	public void setLineColor(Color lineColor) {
		mLineColor = lineColor;
	}

	/**
	 * Gets the line width.
	 *
	 * @return the line's width
	 */
	public float getLineWidth() {
		return mLineWidth;
	}

	/**
	 * Sets the line width.
	 *
	 * @param lineWidth the new line width
	 */
	public void setLineWidth(float lineWidth) {
		mLineWidth = lineWidth;
	}

	/**
	 * Sets the touch radius.
	 *
	 * @param touchRadius the new touch radius
	 */
	public void setTouchRadius(int touchRadius) {
		if (touchRadius <= 0) {
			touchRadius = 0;
		}

		mTouchRadius = touchRadius;
	}

	/**
	 * Gets the touch radius.
	 * @return the radius
	 */
	public int getTouchRadius() {
		return mTouchRadius;
	}

	// ================================================
	// Constructor
	// ================================================

	/**
	 * Constructor.
	 */
	public SoilArea() {
		mBasePoly = new PolyDefault();

		mFillColor = new Color(208 / 255f, 105 / 255f, 21 / 255f);
		mHolePolyColor = Color.WHITE;
		mLineColor = new Color(114 / 255f, 60 / 255f, 16 / 255f);

		mTouchable = true;

		mTouchRadius = 40;
		mLineWidth = 3.5f;

		mCurrPolygons = new Vector<Polygon>();
		mCurrPolygonBodies = new Vector<Body>();
	}

	/**
	 * Add a point to definite the soilArea.
	 *
	 * @param pX Tọa độ x của điểm
	 * @param pY Tọa độ y của điểm
	 */
	public void addPoint(float pX, float pY) {
		mBasePoly.add(pX, pY);
		attachAllPolygonsToScene(mBasePoly);
	}

	/**
	 * Xử lý khi người chơi chạm vào vùng đất.
	 *
	 * @param touchPositionX Tọa độ x của vị trí chạm
	 * @param touchPositionY Tọa độ y của vị trí chạm
	 */
	public synchronized void touch(float touchPositionX,
			float touchPositionY) {
		if (mTouchable) {
			Poly touchPoly = createTouchPoly(
					touchPositionX - mPositionX,
					touchPositionY - mPositionY,
					mTouchRadius);

			difference(touchPoly);
		}
	}

	/**
	 * Difference with a poly.
	 *
	 * @param poly the poly
	 */
	public void difference(Poly poly) {
		if (mBasePoly.isEmpty() == false) {
			mBasePoly = mBasePoly.difference(poly);
			bChange = true;
		}
	}

	/** The flag mark the change of soilArea. */
	private Boolean bChange = false;

	/**
	 * Update.
	 */
	public void Update() {
		if (bChange) {
			attachAllPolygonsToScene(mBasePoly);
			bChange = false;
		}
	}

	/**
	 * Attach to scene.
	 *
	 * @param scene the scene
	 * @param engine the engine
	 * @param physicsWorld the physics world
	 * @param vertexBuffObjMngr the vertex buff obj mngr
	 */
	public void attachToScene(Scene scene, Engine engine,
			PhysicsWorld physicsWorld,
			VertexBufferObjectManager vertexBuffObjMngr) {
		mScene = scene;
		mEngine = engine;
		mPhysicsWorld = physicsWorld;
		mVertexBuffObjManager = vertexBuffObjMngr;


		int numPoints = mBasePoly.getNumPoints();

		if (numPoints < 3) {
			return;
		}

		float[] pVertexX = new float[numPoints];
		float[] pVertexY = new float[numPoints];

		for (int i = 0; i < numPoints; i++) {
			pVertexX[i] = (float) mBasePoly.getX(i);
			pVertexY[i] = (float) mBasePoly.getY(i);
		}

		attachAllPolygonsToScene(mBasePoly);
	}

	/**
	 * Attach all polygons to scene.
	 *
	 * @param poly the poly
	 */
	private void attachAllPolygonsToScene(Poly poly) {
		if ((mScene == null) || (mVertexBuffObjManager == null)
				|| (mEngine == null)) {
			return;
		}

		detachAllPolygonsInScene();

		if ((poly == null) || (poly.isEmpty())) {
			return;
		}

		int numInnerPoly = poly.getNumInnerPoly();

		for (int i = 0; i < numInnerPoly; i++) {
			attachPolygonToScene(poly.getInnerPoly(i));
		}
	}

	/**
	 * Attach polygon to scene.
	 *
	 * @param polyToAttach the poly to attach
	 */
	private void attachPolygonToScene(Poly polyToAttach) {
		if ((polyToAttach == null) || (mScene == null)) {
			return;
		}

		// LÃ¡ÂºÂ¥y tÃ¡Â»ï¿½a Ã„â€˜Ã¡Â»â„¢ cÃƒÂ¡c Ã„â€˜Ã¡Â»â€°nh
		int numPoints = polyToAttach.getNumPoints();

		if (numPoints < 3) {
			return;
		}

		float[] pVertexX = new float[numPoints];
		float[] pVertexY = new float[numPoints];

		for (int i = 0; i < numPoints; i++) {
			pVertexX[i] = (float) polyToAttach.getX(i);
			pVertexY[i] = (float) polyToAttach.getY(i);
		}

		// TÃ¡ÂºÂ¡o polygon Ã„â€˜Ã¡Â»Æ’ thÃƒÂªm vÃƒÂ o mScene
		Polygon polygonToAttach = new Polygon(mPositionX, mPositionY, pVertexX,
				pVertexY, mVertexBuffObjManager);

		if (polyToAttach.isHole())
			polygonToAttach.setColor(mHolePolyColor);
		else
			polygonToAttach.setColor(mFillColor);

		polygonToAttach.setZIndex(1);
		mCurrPolygons.add(polygonToAttach); // lÃ†Â°u lÃ¡ÂºÂ¡i Ã„â€˜Ã¡Â»Æ’ sau
											// nÃƒÂ y detach
		mScene.attachChild(polygonToAttach);
		// TÃ¡ÂºÂ¡o body
		if (polyToAttach.isHole() == false) {
			generateBodies(numPoints, pVertexX, pVertexY);
			// generateLines(numPoints, pVertexX, pVertexY);
		}
	}

	/**
	 * Generate bodies.
	 *
	 * @param numPoints the num points
	 * @param pVertexX the vertex x
	 * @param pVertexY the vertex y
	 */
	private void generateBodies(int numPoints, float[] pVertexX,
			float[] pVertexY) {
		Vector2[] vertices = new Vector2[numPoints];

		for (int i = 0; i < numPoints; i++) {
			vertices[i] = new Vector2(
					(pVertexX[i] + mPositionX)
					/ PIXEL_TO_METER_RATIO_DEFAULT,
					(pVertexY[i] + mPositionY - 20) 
					/ PIXEL_TO_METER_RATIO_DEFAULT);
		}

		Body body = PhysicsFactory.createChainBody(
				mPhysicsWorld, vertices,
				BodyType.StaticBody,
				FIXTURE_DEF, PIXEL_TO_METER_RATIO_DEFAULT);

		mCurrPolygonBodies.add(body);

		if (numPoints > 1) {
			Body lineBody = PhysicsFactory.createEdgeBody(
					mPhysicsWorld,
					vertices[0],
					vertices[numPoints - 1],
					BodyType.StaticBody,
					FIXTURE_DEF,
					PIXEL_TO_METER_RATIO_DEFAULT);

			mCurrPolygonBodies.add(lineBody);
		}
	}

	/**
	 * Detach all polygons in scene.
	 */
	public void detachAllPolygonsInScene() {
		if ((mEngine == null) || (mScene == null)) {
			return;
		}

		final EngineLock engineLock = mEngine.getEngineLock();
		engineLock.lock();

		// xÃƒÂ³a body
		for (Body polygonBody : mCurrPolygonBodies) {
			polygonBody.setActive(false); // safe remove
			mPhysicsWorld.destroyBody(polygonBody);
		}
		mCurrPolygonBodies.clear();

		// xÃƒÂ³a polygon
		for (Polygon attachedPoly : mCurrPolygons) {
			mScene.detachChild(attachedPoly);
		}
		mCurrPolygons.clear();

		engineLock.unlock();
	}

	/**
	 * Create polygon.
	 * @param touchPositionX the touch position x
	 * @param touchPositionY the touch position y
	 * @param touchRadius the touch radius
	 * @return the poly
	 */
	public static Poly createTouchPoly(float touchPositionX,
			float touchPositionY, float touchRadius) {
		Poly touchPoly = new PolyDefault(true); // isHole = true

		float x = touchPositionX;
		float y = touchPositionY;
		float r = touchRadius;

		touchPoly.add(x - r, y);
		touchPoly.add(x - r * 0.866, y - r * 0.5);
		touchPoly.add(x - r * 0.5, y - r * 0.866);
		touchPoly.add(x, y - r);
		touchPoly.add(x + r * 0.5, y - r * 0.866);
		touchPoly.add(x + r * 0.866, y - r * 0.5);
		touchPoly.add(x + r, y);
		touchPoly.add(x + r * 0.866, y + r * 0.5);
		touchPoly.add(x + r * 0.5, y + r * 0.866);
		touchPoly.add(x, y + r);
		touchPoly.add(x - r * 0.5, y + r * 0.866);
		touchPoly.add(x - r * 0.866, y + r * 0.5);

		return touchPoly;
	}
}