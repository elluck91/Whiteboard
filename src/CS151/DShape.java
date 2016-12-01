package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public abstract class DShape {

    protected DShapeModel model;
    
    DShape() {
	model = new DShapeModel();
    }
	
    public DShapeModel getModel() {
	return model;
    }

    public void randomize(int max) {
	model.randomize(max);
    }

    public void setColor(Color c) {
	model.setColor(c);
    }
    
    public abstract void draw();
    public abstract Shape getShape();
    


}
