package CS151;

import javafx.scene.shape.Shape;

public abstract class DShape {

    protected DShapeModel model;
    
    DShape() {
	model = new DShapeModel();
    }
	
    public DShapeModel getModel() {
	return model;
    }

    public abstract void draw();
    public abstract Shape getShape();


}
