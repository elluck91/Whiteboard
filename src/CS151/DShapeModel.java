package CS151;

import javafx.scene.paint.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;

public class DShapeModel {

    /*
      I changed the variables because the file assignment
      document recommended using Rectangle & Point classes
     */
    protected Rectangle rectangle;
    protected Point point;
    protected Color color;
    protected ArrayList<DShape> listeners;

    
    public DShapeModel() {
	rectangle = new Rectangle(0, 0, 0, 0);
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
    
}
