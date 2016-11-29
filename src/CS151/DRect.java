package CS151;

import javafx.scene.shape.Rectangle;

public class DRect extends DShape {

    private Rectangle rectangle;
    
    public DRect() {
	super();
	rectangle = new Rectangle();
    }

    public void draw(){
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
