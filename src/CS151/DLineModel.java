package CS151;

import javafx.geometry.Point2D;
import java.util.Random;

public class DLineModel extends DShapeModel {

    private Point2D start;
    private Point2D end;

    public DLineModel() {
	start = new Point2D(getX(), getY());
	end = new Point2D(getX() + getWidth(), getY() + getHeight());
    }
    
    public Point2D getStart() {
	return start;
    }

    public Point2D getEnd() {
	return end;
    }

    public void setStart(Point2D start) {
	this.start = new Point2D(start.getX(), start.getY());
    }

    public void setEnd(Point2D end) {
	this.end = new Point2D(end.getX(), end.getY());
    }
}
