package CS151;

import java.util.Random;

public class DOvalModel extends DShapeModel {

    // stored as integers to make randomize work appropriately
    // consider changing in the future?
    private int xCenter;
    private int yCenter;
    private int xRadius;
    private int yRadius;
    
    public void randomize(int max) {
	Random rand = new Random();
	xCenter = rand.nextInt(max)+1;
	yCenter = rand.nextInt(max)+1;
	// the following computations restrict the size to fit in the canvas
	int maxWidth = (max - xCenter) < xCenter ? (max - xCenter) : xCenter;
	int maxHeight = (max - yCenter) < yCenter ? (max - yCenter) : yCenter;
	xRadius = rand.nextInt(maxWidth)+1;
	yRadius = rand.nextInt(maxHeight)+1;
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
