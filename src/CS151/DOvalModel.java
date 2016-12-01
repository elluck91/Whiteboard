package CS151;

import java.util.Random;

public class DOvalModel extends DShapeModel {

    // stored as integers to make randomize work appropriately
    // consider changing in the future? Javafx shapes use doubles
    // to define size
    private int xCenter;
    private int yCenter;
    private int xRadius;
    private int yRadius;

    /** 
     * set the center and radius of the ellipse using the defined
     * model
     */
    public void defineEllipse() {
	xCenter = ( getX() + getWidth() ) / 2;
	yCenter = ( getY() + getHeight() ) / 2;
	xRadius = getWidth()/2;
	yRadius = getHeight()/2;
    }

    public double getXCenter() {
	return (double) xCenter;
    }

    public double getYCenter() {
	return (double) yCenter;
    }

    public double getXRadius() {
	return (double) xRadius;	
    }

    public double getYRadius() {
	return (double) yRadius;
    }
}
