package CS151;

public class DOvalModel extends DShapeModel {

    
    /**
     * Calculate and return the x-coordinate of the center.
     * @return double
     */
    public double getXCenter() {
	return (double)( ( getX() + (getX() + getWidth()) ) / 2 );
    }

    
    /**
     * Calculate and return the y-coordinate of the center.
     */
    public double getYCenter() {
	return (double)( ( getY() + (getY() + getHeight()) ) / 2);
    }


    /**
     * Calculate and return the radius in the x-direction
     */
    public double getXRadius() {	
	return (double)( getWidth() / 2 );
    }


    /**
     * Calculate and return the radius in the y-direction
     */
    public double getYRadius() {
	return (double)( getHeight() / 2);
    }

}
