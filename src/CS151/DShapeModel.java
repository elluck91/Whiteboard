package CS151;

import javafx.scene.paint.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.Random;

public class DShapeModel {

    /*
      I changed the variables because the file assignment
      document recommended using Rectangle & Point classes
     */
    protected Rectangle rectangle;
    protected Point point;
    protected Color color;
    
    public DShapeModel() {
	rectangle = new Rectangle(0, 0, 0, 0);
	point = new Point(0, 0);
	color = Color.GRAY;
    }
    
    public int getX() {
	return rectangle.x;
    }

    public void setX(int x) {
	this.rectangle.x = x;
    }

    public int getY() {
	return rectangle.y;
    }

    public void setY(int y) {
	this.rectangle.y = y;
    }

    public int getWidth() {
	return rectangle.width;
    }

    public void setWidth(int width) {
	this.rectangle.width = width;
    }

    public int getHeight() {
	return rectangle.height;
    }

    public void setHeight(int height) {
	this.rectangle.height = height;
    }

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
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
