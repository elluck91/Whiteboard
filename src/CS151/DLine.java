package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;

public class DLine extends DShape {

    private Line line;

    public DLine() {
	model = new DLineModel();
	line = new Line();
    }
    
    public void draw(){
	line.setFill(model.getColor());
	double startX = ( (DLineModel) model).getStart().getX();
	double startY = ( (DLineModel) model).getStart().getY();
	double endX = ( (DLineModel) model).getEnd().getX();
	double endY = ( (DLineModel) model).getEnd().getY();
	line.setStartX(startX);
	line.setStartY(startY);
	line.setEndX(endX);
	line.setEndY(endY);
    }

    public Shape getShape() {
	return line;
    }
}
