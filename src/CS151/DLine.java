package CS151;

import java.awt.Color;
import javafx.scene.shape.Line;
import java.awt.geom.Point2D;

public class DLine extends DShape implements ModelListener
{

	private Line line;


	public DLine() {
		model = new DLineModel();       
		line = new Line(((DLineModel) model).getStartX(), ((DLineModel) model).getStartY(),
				((DLineModel) model).getEndX(), ((DLineModel) model).getEndY());
		line.setStroke(Adapters.awtToFx(Color.GRAY));;
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
	}


	public void moveBy(double dx, double dy) {	
		double startX = ((DLineModel) model).getStartX()+dx;
		double startY = ((DLineModel) model).getStartY()+dy;
		double endX = ((DLineModel) model).getEndX()+dx;
		double endY = ((DLineModel) model).getEndY()+dy;
		if(startX > 0 && startY > 0 && endX > 0 && endY > 0) {
			((DLineModel) model).setStart(new Point2D.Double(startX,startY));
			((DLineModel) model).setEnd(new Point2D.Double(endX, endY));
		}
	}


	public void moveTo(Point2D anchor, Point2D result) {
		double startX = ((DLineModel) model).getStartX();
		double startY = ((DLineModel) model).getStartY();
		double endX = ((DLineModel) model).getEndX();
		double endY = ((DLineModel) model).getEndY();
		if(startX == anchor.getX() && startY == anchor.getY()){
			((DLineModel) model).setEndX(result.getX());
			((DLineModel) model).setEndY(result.getY());	    	    
		}
		else {
			((DLineModel) model).setStartX(result.getX());
			((DLineModel) model).setStartY(result.getY());	   
		}
	}


	public Line getShape()
	{
		return line;
	}

	public Point2D getStart() {
		return ((DLineModel) model).getStart();
	}

	public Point2D getEnd() {
		return ((DLineModel) model).getEnd();
	}


	public void setModel(DShapeModel model)
	{
		this.model = model;	
	}


}
