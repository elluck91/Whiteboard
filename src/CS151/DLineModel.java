package CS151;

import java.awt.geom.Point2D;

public class DLineModel extends DShapeModel implements java.io.Serializable {

	private double startX, startY;
	private double endX, endY;

	public DLineModel() {
		startX = getX();
		startY = getY();
		endX = getX() + getWidth();
		endY = getY() + getHeight();
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
