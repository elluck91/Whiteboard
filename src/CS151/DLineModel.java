package CS151;

import java.awt.Point;
import java.util.Random;

public class DLineModel extends DShapeModel {

    private Point start;
    private Point end;

    
    public void randomize(int max) {
	Random rand = new Random();
	start = new Point(rand.nextInt(max)+1, rand.nextInt(max)+1);
	end = new Point(rand.nextInt(max)+1, rand.nextInt(max)+1);	
    }
    
    public Point getStart() {
	return start;
    }

    public Point getEnd() {
	return end;
    }
}
