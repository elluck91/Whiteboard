package CS151;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

public class DShapeModel implements Observer {

	/**
	 * I changed the variables because the file assignment
	 * document recommended using Rectangle & Point classes
	 */
	protected Rectangle rectangle;
	protected Point2D point;
	protected Color color;
	protected ArrayList<ModelListener> listeners;
	DoubleProperty width;
	DoubleProperty height;
	WhiteboardPresenter presenter;

	public DShapeModel() {
		rectangle = new Rectangle(0, 0, 0, 0);
		width = new SimpleDoubleProperty(rectangle.getWidth());
		height = new SimpleDoubleProperty(rectangle.getHeight());
		point = new Point2D(0, 0);
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


	/**
	 * Get the top left corner point of the shape bounds
	 * @return Point2D
	 */
	public Point2D getTopLeft() {
		System.out.println("top left x: " + getX() + " top right y: " + getY());
		return new Point2D(point.getX(), point.getY());
	}


	/**
	 * Get the top right corner point of the shape bounds
	 * @return Point2D
	 */
	public Point2D getTopRight() {
		double topX = getX() + getWidth();
		// testing output
		System.out.println("top right x: " + topX + " top right y: " + getY());
		return new Point2D(topX, getY());	
	}


	/**
	 * Get the bottom left corner point of the shape bounds
	 * @return Point2D
	 */
	public Point2D getBottomLeft() {
		double bottomY = getY() + getHeight();
		System.out.println("bottom left x: " + getX() + "bottom left y: " + bottomY);
		return new Point2D(getX(), bottomY);
	}


	/**
	 * Get the bottom right corner point of the shape bounds
	 * @return Point2D
	 */
	public Point2D getBottomRight() {
		double bottomX = getX() + getWidth();
		double bottomY = getY() + getHeight();
		System.out.println("bottom right x: " + bottomX + " bottom left y: " + bottomY);
		return new Point2D(bottomX, bottomY);
	}


	/**
	 * generate a random rectangle to serve as the bounds
	 * for a DShape
	 */
	/*
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
	 */

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
