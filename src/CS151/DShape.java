package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class DShape implements ModelListener
{
    protected Rectangle resizeHandleNW;
    protected Rectangle resizeHandleNE;
    protected Rectangle resizeHandleSE;
    protected Rectangle resizeHandleSW;
    protected Rectangle resizeHandleLeft;
    protected Rectangle resizeHandleRight;

    protected DShapeModel model;

    DShape()
    {
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

    public java.awt.Rectangle getBounds()
    {
        return model.getBounds();
    }

    public void randomize(int max)
    {
        model.randomize(max);
    }

    public void setColor(Color c)
    {
        model.setColor(c);
    }
    
    

    /**
     * Calculate a list of points representing the four corners of the model
     *
     * @return ArrayList<Point>
     */
    public ArrayList<Point> getKnobs()
    {
        ArrayList<Point> knob = new ArrayList<Point>();
        knob.add(model.getTopLeft());
        knob.add(model.getTopRight());
        knob.add(model.getBottomLeft());
        knob.add(model.getBottomRight());
        return knob;
    }

    
    public abstract void drawKnobs();
    public abstract void removeKnobs();
    

    



    public void modelChanged(DShapeModel model)
    {
        setModel(model); // set the new changes maybe unnecessary
        draw(); // redraw
    }

    public abstract void draw();
    public abstract Shape getShape();


}
