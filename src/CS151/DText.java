package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;

public class DText extends DShape implements ModelListener {
    
    public DText() {
	model = new DTextModel();
    }
    
    public void draw() {

    }

    public Shape getShape() {
	return new Rectangle();
    }
}
