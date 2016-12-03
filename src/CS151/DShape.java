package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.awt.Rectangle;

public abstract class DShape {

    protected DShapeModel model;
    
    DShape() {
	model = new DShapeModel();
    }
	
    public DShapeModel getModel() {
	return model;
    }

    public void setModel(DShapeModel model) {
	this.model = model;
    }
    
    public Rectangle getBounds() {
	return model.getBounds();
    }
    
    public void randomize(int max) {
	model.randomize(max);
    }

    public void setColor(Color c) {
	model.setColor(c);
    }

    public void drawKnobs() {

    }
    
    public void removeKnobs() {

    }
    
    public abstract void draw();
    public abstract Shape getShape();
    

}
