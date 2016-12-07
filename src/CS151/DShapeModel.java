package CS151;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

public class DShapeModel implements Observer {

	/*
      I changed the variables because the file assignment
      document recommended using Rectangle & Point classes
	 */
	protected Rectangle rectangle;
	protected Point point;
	protected Color color;
	protected ArrayList<DShape> listeners;
	DoubleProperty width;
	DoubleProperty height;
	WhiteboardPresenter presenter;
	
	public DShapeModel() {
		rectangle = new Rectangle(0, 0, 0, 0);
		width = new SimpleDoubleProperty(rectangle.getWidth());
		height = new SimpleDoubleProperty(rectangle.getHeight());
		point = new Point(0, 0);
		color = Color.GRAY;
		listeners = new ArrayList<DShape>();
	}


	public int getX() {
		return rectangle.x;
	}


	public void setX(int x) {
		this.rectangle.x = x;
		notifyListeners();
	}


	public int getY() {
		return rectangle.y;
	}


	public void setY(int y) {
		this.rectangle.y = y;
		notifyListeners();
	}


	public int getWidth() {
		return rectangle.width;
	}


	public void setWidth(int width) {
		this.rectangle.width = width;
		notifyListeners();
	}


	public int getHeight() {
		return rectangle.height;
	}


	public void setHeight(int height) {
		this.rectangle.height = height;
		
		notifyListeners();
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}


	/**
	 * Register a shape as a listener of the model.
	 * @param DShape shape
	 */
	public void addListener(DShape shape) {
		listeners.add(shape);
	}


	/**
	 * Remove a shape as a listener of the model.
	 * @param DShape shape
	 */
	public void removeListener(DShape shape) {
		listeners.remove(shape);
	}


	/**
	 * Notify all DShapes of changes to the model
	 * private seems appropriate for now, I think
	 * this function will only be called from the set
	 * methods of the model
	 */
	private void notifyListeners() {
		for(DShape shape: listeners) {
			shape.modelChanged(this);
		}
	}


	/**
	 * create and return a copy of the rectangle model
	 */
	public Rectangle getBounds() {       
		return new Rectangle(rectangle);
	}


	/**
	 * Get the top left corner point of the shape bounds
	 * @return Point
	 */
	public Point getTopLeft() {
		System.out.println("top left x: " + getX() + " top right y: " + getY());
		return new Point(point);
	}


	/**
	 * Get the top right corner point of the shape bounds
	 * @return Point
	 */
	public Point getTopRight() {
		int topX = getX() + getWidth();
		// testing output
		System.out.println("top right x: " + topX + " top right y: " + getY());
		return new Point(topX, getY());	
	}


	/**
	 * Get the bottom left corner point of the shape bounds
	 * @return Point
	 */
	public Point getBottomLeft() {
		int bottomY = getY() + getHeight();
		System.out.println("bottom left x: " + getX() + "bottom left y: " + bottomY);
		return new Point(getX(), bottomY);
	}


	/**
	 * Get the bottom right corner point of the shape bounds
	 * @return Point
	 */
	public Point getBottomRight() {
		int bottomX = getX() + getWidth();
		int bottomY = getY() + getHeight();
		System.out.println("bottom right x: " + bottomX + " bottom left y: " + bottomY);
		return new Point(bottomX, bottomY);
	}


	/**
	 * generate a random rectangle to serve as the bounds
	 * for a DShape
	 */    
	public void randomize(int max) { 
		Random rand = new Random();
		int maxWidth = 0;
		int maxHeight = 0;
		int newX = 0;
		int newY = 0;
		while(maxWidth == 0 || maxHeight == 0){
			newX = rand.nextInt(max)+1;
			newY = rand.nextInt(max)+1;
			maxWidth = max - newX;
			maxHeight = max - newY;
		}
		setX(newX);
		setY(newY);
		setWidth(rand.nextInt(maxWidth)+1);
		setHeight(rand.nextInt(maxHeight)+1);
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(double wid, double hei) {
		this.width.setValue(wid);
		this.height.setValue(hei);
		presenter.getGui().getTv().refresh();
	}


	public void attachPresenter(WhiteboardPresenter whiteboardPresenter) {
		presenter = whiteboardPresenter;
		
	}

}
