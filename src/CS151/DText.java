package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;

public class DText extends DShape implements ModelListener {
    
    public DText() {
	model = new DTextModel();
    }
    
    public void draw() {

    }

    public String getText() {
	return ((DTextModel) model).getText();
    }
    
    public Shape getShape() {
	return new Rectangle();
    }

    @Override
    public void drawKnobs()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeKnobs()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
