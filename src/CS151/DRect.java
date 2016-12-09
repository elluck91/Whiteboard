package CS151;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DRect extends DShape implements ModelListener {


    private Rectangle rect;
    
    public DRect() {
        model = new DRectModel();
        rect = new Rectangle(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }
    
    public DRect(double x, double y, int width, int height) {
        model = new DRectModel();
        rect = new Rectangle(x, y, width, height);
    }

    /**
     * Draw the rectangle defined by the model
     */
    public void draw() {
    	//System.out.println("I am drawing myself on line 25 in DRect");
        rect.setFill(model.getColor());
        rect.setX((double) model.getX());
        rect.setY((double) model.getY());
        rect.setWidth((double) model.getWidth());
        rect.setHeight((double) model.getHeight());
    }

    /**
     * Get the shape 
     * @return Rectangle
     */
    public Rectangle getShape() {
        return rect;
    }

}
