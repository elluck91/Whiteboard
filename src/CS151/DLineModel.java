package CS151;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class DLineModel extends DShapeModel {

    private Point2D start;
    private Point2D end;
    
    public DLineModel() {
	start = new Point2D(getX(), getY());
	end = new Point2D(getX() + getWidth(), getY() + getHeight());
    }
    
    public Point2D getStart() {
	return new Point2D(start.getX(), start.getY());
    }
    
    public Point2D getEnd() {
	return new Point2D(end.getX(), end.getY());
    }
    
    public void setStart(Point2D p1) {
	start = new Point2D(p1.getX(), p1.getY());
	updateBounds();
    }

    
    public void setEnd(Point2D p2) {
	end = new Point2D(p2.getX(), p2.getY());
	updateBounds();
    }
    
    public void updateBounds() {
	if(start.getY() > end.getY()) {
	    if(start.getX() > end.getX()) {
		updateBoundsCase1();
	    } else {
		updateBoundsCase2();
	    }
	} else {
	    if(start.getX() > end.getX()) {
		updateBoundsCase3();
	    } else {
		updateBoundsCase4();
	    }
	}
	System.out.println("x: " + getX() +
			   " y: " + getY() +
			   " width: " + getWidth() +
			   " height: " + getHeight());
    }
    
    private void updateBoundsCase1() {
	setX(end.getX());
	setY(end.getY());
	setWidth(start.getX() - end.getX());
	setHeight(start.getY() - end.getY());
    }
    
    private void updateBoundsCase2() {
	setX(start.getX());
	setY(end.getY());
	setWidth(end.getX() - start.getX());
	setHeight(start.getY() - end.getY());
    }

    private void updateBoundsCase3() {
	    setX(end.getX());
	    setY(start.getY());
	    setWidth(start.getX() - end.getX());
	    setHeight(end.getY() - start.getY());
    }
    
    private void updateBoundsCase4() {
	setX(start.getX());
	setY(start.getY());
	setWidth(end.getX() - start.getX());
	setHeight(end.getY() - start.getY());
    }
}
