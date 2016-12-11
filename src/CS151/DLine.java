package CS151;

import java.awt.geom.Point2D;
import java.awt.Color;
import javafx.scene.shape.Line;

public class DLine extends DShape implements ModelListener
{


	private Line line;

	public DLine() {
		model = new DLineModel();

		line = new Line(((DLineModel) model).getStartX(), ((DLineModel) model).getStartY(),
				((DLineModel) model).getEndX(), ((DLineModel) model).getEndY());
		line.setStroke(model.translateColor(Color.GRAY));
		line.setStrokeWidth(5);
	}

	@Override
	public void draw()
	{
		line.setStroke(model.translateColor(model.getColor()));
		double startX = ((DLineModel) model).getStartX();
		double startY = ((DLineModel) model).getStartY();
		double endX = ((DLineModel) model).getEndX();
		double endY = ((DLineModel) model).getEndY();
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
		updateModel();
	}

	private void updateModel()
	{

		Point2D.Double start = new Point2D.Double(line.getStartX(), line.getStartY());
		Point2D.Double end = new Point2D.Double(line.getEndX(), line.getEndY());

		((DLineModel) model).setStart(start);
		((DLineModel) model).setEnd(end);

	}

	public void setModel(DShapeModel model)
	{
		this.model = model;
		Point2D.Double start = new Point2D.Double(model.getX(), model.getY());
		Point2D.Double end = new Point2D.Double(model.getX() + model.getWidth(), model.getY() + model.getWidth());
		((DLineModel) model).setStart(start);
		((DLineModel) model).setEnd(end);
	}

	public Line getShape()
	{
		return line;
	}

}
