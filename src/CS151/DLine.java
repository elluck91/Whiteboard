package CS151;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DLine extends DShape implements ModelListener
{

    private Rectangle rect = new Rectangle();
    private Line line;

    public DLine() {
        model = new DLineModel();

        line = new Line(((DLineModel) model).getStart().getX(), ((DLineModel) model).getStart().getY(),
			((DLineModel) model).getEnd().getX(), ((DLineModel) model).getEnd().getY());
        line.setStroke(Color.BLACK);
        //line.setStrokeWidth(5);
    }

    @Override
    public void draw()
    {
	System.out.println(model.getColor().toString());
        line.setStroke(model.getColor());
        double startX = ((DLineModel) model).getStart().getX();
        double startY = ((DLineModel) model).getStart().getY();
        double endX = ((DLineModel) model).getEnd().getX();
        double endY = ((DLineModel) model).getEnd().getY();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        updateModel();
    }

    private void updateModel()
    {

        Point2D start = new Point2D(line.getStartX(), line.getStartY());
        Point2D end = new Point2D(line.getEndX(), line.getEndY());

        ((DLineModel) model).setStart(start);
        ((DLineModel) model).setEnd(end);

    }

    public void setModel(DShapeModel model)
    {
        this.model = model;
        Point2D start = new Point2D(model.getX(), model.getY());
        Point2D end = new Point2D(model.getX() + model.getWidth(), model.getY() + model.getWidth());
        ((DLineModel) model).setStart(start);
        ((DLineModel) model).setEnd(end);
    }

    public Shape getShape()
    {
        return line;
    }

}
