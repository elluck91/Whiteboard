package CS151;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class DRect extends DShape {

    private Rectangle rectangle;

    
    public DRect() {
	model = new DRectModel();
	rectangle = new Rectangle();
    }

    
    /**
     * Draw the rectangle defined by the model
     */
    public void draw(){
	rectangle.setFill(model.getColor());
	rectangle.setX((double) model.getX());
	rectangle.setY((double) model.getY());
	rectangle.setWidth((double) model.getWidth());
	rectangle.setHeight((double) model.getHeight());	
    }

    
    public Shape getShape() {
	return rectangle;
    }
    
}
