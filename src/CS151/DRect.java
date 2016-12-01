package CS151;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class DRect extends DShape {

    private Rectangle rectangle;
    
    public DRect() {
	model = new DRectModel();
	rectangle = new Rectangle();
    }

    public void draw(){
	//	System.out.println(model.getColor().toString());
	rectangle.setFill(model.getColor());
	rectangle.setX((double) model.getX());
	rectangle.setY((double) model.getY());
	rectangle.setWidth((double) model.getWidth());
	rectangle.setHeight((double) model.getHeight());	
    }

    public void randomize(int max) {
	model.randomize(max);
    }
    
    @Override
    public Rectangle getShape() {
	return rectangle;
    }
}
