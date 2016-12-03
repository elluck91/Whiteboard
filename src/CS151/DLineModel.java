package CS151;

import java.awt.Point;
import java.util.Random;

public class DLineModel extends DShapeModel {

    private Point start;
    private Point end;

    public DLineModel() {
	start = new Point(getX(), getY());
	end = new Point(getX() + getWidth(), getY() + getHeight());
    }
    
    public Point getStart() {
	return start;
    }

    public Point getEnd() {
	return end;
    }

    public void setStart(Point start) {
	this.start = new Point(start);
    }

    public void setEnd(Point end) {
	this.end = new Point(end);
    }
}
