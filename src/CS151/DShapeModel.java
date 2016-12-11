package CS151;

import java.util.ArrayList;
import java.util.UUID;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.awt.geom.Point2D;
import java.awt.Color;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class DShapeModel {


	/**
	 * I changed the variables because the file assignment
	 * document recommended using Rectangle & Point classes
	 */
	protected Rectangle rectangle;
	protected Color color;
	protected ArrayList<ModelListener> listeners;
	DoubleProperty width;
	DoubleProperty height;
	DoubleProperty x;
	DoubleProperty y;
	private String id;

	public String getId() {
		return id;
	}


	public DShapeModel() {
		id = UUID.randomUUID().toString();
		rectangle = new Rectangle(10, 10, 20, 20);
		width = new SimpleDoubleProperty(rectangle.getWidth());
		height = new SimpleDoubleProperty(rectangle.getHeight());
		x = new SimpleDoubleProperty(rectangle.getX());
		y = new SimpleDoubleProperty(rectangle.getY());
		color = Color.GRAY;
		listeners = new ArrayList<ModelListener>();
	}


	public double getX() {
		return rectangle.getX();
	}


	public void setX(double x) {
		rectangle.setX(x);
		notifyListeners();
	}


	public double getY() {
		return rectangle.getY();
	}


	public void setY(double y) {
		rectangle.setY(y);
		notifyListeners();
	}


	public double getWidth() {
		return rectangle.getWidth();
	}


	public void setWidth(double width) {
		rectangle.setWidth(width);
		notifyListeners();
	}


	public double getHeight() {
		return rectangle.getHeight();
	}


	public void setHeight(double height) {
		rectangle.setHeight(height);
		notifyListeners();
	}


	public Color getColor() {
		return color;
	}
	


	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}


	public void setBounds(Rectangle rect) {
		// do something
		notifyListeners();
	}


	public void moveBy(double dx, double dy) {

	}


	/**
	 * Register a shape as a listener of the model.
	 * @param DShape shape
	 */
	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}


	/**
	 * Remove a shape as a listener of the model.
	 * @param DShape shape
	 */
	public void removeListener(ModelListener listener) {
		listeners.remove(listener);
	}


	/**
	 * Notify all DShapes of changes to the model
	 * private seems appropriate for now, I think
	 * this function will only be called from the set
	 * methods of the model
	 */
	protected void notifyListeners() {
		for(ModelListener listener: listeners) {
			listener.modelChanged(this);
		}
	}


	/**
	 * create and return a copy of the rectangle model
	 */
	public Rectangle getBounds() {       
		return new Rectangle(rectangle.getX(), rectangle.getY(),
				rectangle.getWidth(), rectangle.getHeight());
	}

	public void update(double wid, double hei) {
		System.out.println("UPDATING");
		this.width.setValue(wid);
		this.height.setValue(hei);
		this.x.setValue(getX());
		this.y.setValue(getY());
	}


	public Paint translateColor(Color awtColor) {
		javafx.scene.paint.Color fxColor 
		= javafx.scene.paint.Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha()/255.0);
		return fxColor;
	}
	
	


}
