package com.group14.wheresmywater;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * @author Thanh Quang
 * Provider function load, create body
 */
public class BodyProvider {

	public static void generateBodies(String fileName, PhysicsWorld physicWorld, FixtureDef fixtureDef) {
		try {
			InputStream is = ResourcesManager.getInstance()._activity.getAssets().open(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;

			db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			Element docEle = doc.getDocumentElement();

			NodeList bodies = docEle.getElementsByTagName("Body");

			for (int i = 0; i < bodies.getLength(); i++) {
				ArrayList<Vector2> list = new ArrayList<Vector2>();
				list = getListVertex((Element)bodies.item(i));
				int count = list.size();
				Vector2[] vertices = new Vector2[count];
				for (int j = 0; j < count; j++) {
					Vector2 point = list.get(j);
					vertices[j] = new Vector2((int)(point.x)
							/ PIXEL_TO_METER_RATIO_DEFAULT, (int)(point.y)
							/ PIXEL_TO_METER_RATIO_DEFAULT);
				}
				PhysicsFactory.createChainBody(physicWorld, vertices,
						BodyType.StaticBody, fixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author Thanh Quang
	 * @param item : element
	 * @return listVectex
	 */
	private static ArrayList<Vector2> getListVertex(final Element item) {
		// TODO Auto-generated method stub
		ArrayList<Vector2> list = new ArrayList<Vector2>();
		NodeList nodes = item.getElementsByTagName("Vertex");
		for (int i = 0; i < nodes.getLength(); i++) {
			Vector2 vertex = new Vector2();
			vertex.x = Float.parseFloat(nodes.item(i).getAttributes()
					.getNamedItem("x").getNodeValue());
			vertex.y = Float.parseFloat(nodes.item(i).getAttributes()
					.getNamedItem("y").getNodeValue());

			list.add(vertex);
		}
		return list;
	}
}
