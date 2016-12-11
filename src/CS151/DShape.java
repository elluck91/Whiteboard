package CS151;

import java.awt.Color;
import java.awt.geom.Point2D;
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

    
    public void setColor(Color c)
    {
        model.setColor(c);
    }


    public Point2D getTopLeft() {
	return new Point2D.Double(model.getX(), model.getY());
    }

    public Point2D getTopRight() {
	return new Point2D.Double(model.getX()+model.getWidth(),
			   model.getY());
    }

    public Point2D getBottomLeft() {
	return new Point2D.Double(model.getX(),
			   model.getY()+model.getHeight());
    }

    public Point2D getBottomRight() {
	return new Point2D.Double(model.getX()+model.getWidth(),
			   model.getY()+model.getHeight());
    }

    public void moveBy(double dx, double dy) {
	double x = dx + model.getX();
	double y = dy + model.getY();
	if(x < 0) x = 0;	
	if(y < 0) y = 0;
	model.setBounds(new Rectangle(x, y,
				      model.getWidth(),
				      model.getHeight()));
    }
    
    public void moveTo(double x, double y,
		       double width, double height) {
	model.setBounds(new Rectangle(x, y, width, height));
    }
    
    public void modelChanged(DShapeModel model)
    {
        setModel(model); // set the new changes maybe unnecessary
        draw(); // redraw
    }

    public abstract void draw();
    public abstract Shape getShape();


}
