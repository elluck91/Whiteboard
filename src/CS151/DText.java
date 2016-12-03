package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;

public class DText extends DShape {
    
    public DText() {
	model = new DTextModel();
    }
    
    public void draw() {

    }

    public Shape getShape() {
	return new Rectangle();
    }
}
