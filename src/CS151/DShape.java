package CS151;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class DShape implements ModelListener
{

    protected DShapeModel model;

    DShape() {
        model = new DShapeModel();                
    }

    public DShapeModel getModel()
    {
        return model;
    }

    public void setModel(DShapeModel model)
    {
        this.model = model;
    }

    public Rectangle getBounds()
    {
        return model.getBounds();
    }

    /*
    public void randomize(int max)
    {
        model.randomize(max);
    }
    */
    
    public void setColor(Color c)
    {
        model.setColor(c);
    }
    
    public void modelChanged(DShapeModel model)
    {
        setModel(model); // set the new changes maybe unnecessary
        draw(); // redraw
    }

    public abstract void draw();
    public abstract Shape getShape();


}
