package CS151;


import javafx.scene.shape.Rectangle;
import java.awt.geom.Point2D;
import java.awt.Color;


public class DLineModel extends DShapeModel implements java.io.Serializable {


    private double startX, startY;
    private double endX, endY;
    

    public DLineModel() {
	super();
	startX = getX();
	startY = getY();
	endX = getX()+getWidth();
	endY = getY()+getHeight();
    }
	
    public Point2D getStart() {
	return new Point2D.Double(startX, startY);
    }

    public Point2D getEnd() {
	return new Point2D.Double(endX, endY);	
    }
    
    public double getStartX() {
	return startX;	
    }
	
    public double getStartY() {
	return startY;	
    }


    public double getEndX() {
	return endX;
    }
    
    public double getEndY() {
	return endY;
    }
	
    public Color getColor() {
	return color;
    }

    
    public void setColor(Color color) {
	this.color = color;	
	notifyListeners();
    }

    
    public void setStart(Point2D start) {
	startX = start.getX();
	startY = start.getY();	    
	updateBounds();
    }
    
    public void setEnd(Point2D end) {
	endX = end.getX();
	endY = end.getY();
	updateBounds();
    }

    public void setStartX(double startX) {
	this.startX = startX;
	updateBounds();
    }
    
    public void setStartY(double startY) {
	this.startY = startY;
	updateBounds();
    }
    
    
    public void setEndX(double endX) {
	this.endX = endX;
	updateBounds();
    }
    
    public void setEndY(double endY) {
	this.endY = endY;
	updateBounds();
    }
    	
               
    public void updateBounds() {
	
	if(startY > endY) {
	    if(startX > endX) {
		updateBoundsCase1();
	    } else {
		updateBoundsCase2();
	    }
	} else {
	    if(startX > endX) {
		updateBoundsCase3();
	    } else {
		updateBoundsCase4();
	    }
	}
	
    }


    private void updateBoundsCase1() {
	setX(endX);
	setY(endY);
	setWidth(startX - endX);
	setHeight(startY - endY);
    }
    

    private void updateBoundsCase2() {
	setX(startX);
	setY(endY);
	setWidth(endX - startX);
	setHeight(startY - endY);
    }

    
    private void updateBoundsCase3() {
	setX(endX);
	setY(startY);
	setWidth(startX - endX);
	setHeight(endY - startY);
    }

    
    private void updateBoundsCase4() {
	setX(startX);
	setY(startY);
	setWidth(endX - startX);
	setHeight(endY - startY);	
    }
    
 }
