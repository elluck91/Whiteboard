package CS151;

import java.awt.Color;
import javafx.scene.shape.Line;

public class DLine extends DShape implements ModelListener
{


	private Line line;

	public DLine() {
		model = new DLineModel();

		line = new Line(((DLineModel) model).getStartX(), ((DLineModel) model).getStartY(),
				((DLineModel) model).getEndX(), ((DLineModel) model).getEndY());
		line.setStroke(Adapters.awtToFx(Color.GRAY));
		line.setStrokeWidth(5);
	}

	@Override
	public void draw()
	{
		line.setStroke(Adapters.awtToFx(model.getColor()));
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

		double startX = model.getX();
		double startY = model.getY();
		
		double endX = model.getX() + model.getWidth();
		double endY = model.getY() + model.getHeight();
		
		((DLineModel) model).setStartX(startX);
		((DLineModel) model).setStartY(startY);
		
		((DLineModel) model).setEndX(endX);
		((DLineModel) model).setEndY(endY);

	}

	public void setModel(DShapeModel model)
	{
		this.model = model;
		double startX = model.getX();
		double startY = model.getY();
		
		double endX = model.getX() + model.getWidth();
		double endY = model.getY() + model.getHeight();
		
		((DLineModel) model).setStartX(startX);
		((DLineModel) model).setStartY(startY);
		
		((DLineModel) model).setEndX(endX);
		((DLineModel) model).setEndY(endY);
	}

	public Line getShape()
	{
		return line;
	}

}
