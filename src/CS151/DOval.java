package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;

public class DOval extends DShape{

    private Ellipse oval;
    
    public DOval() {
	oval = new Ellipse();
    }
    
    public void draw() {

    }

    public Shape getShape() {
	return new Ellipse();
    }
}
