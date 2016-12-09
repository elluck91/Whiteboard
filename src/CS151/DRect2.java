package CS151;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Rectangle;

public class DRect2 extends DShape implements Observer {


    private Rectangle rect;
    
    public DRect2() {
        model = new DRectModel();
        rect = new Rectangle(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }
    
    public DRect2(double x, double y, int width, int height) {
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

	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println("I am drawing myself on line 25 in DRect");
        rect.setFill(model.getColor());
        rect.setX((double) model.getX());
        rect.setY((double) model.getY());
        rect.setWidth((double) model.getWidth()-2);
        rect.setHeight((double) model.getHeight()-2);
        
	}
	
	

}