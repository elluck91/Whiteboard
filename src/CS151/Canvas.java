package CS151;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Canvas extends Pane
{
    private ArrayList<DShape> shapes;
    private ObservableList<DShapeModel> models;
    private DShape selected;
    Whiteboard gui;
    ArrayList<DRect> knobs;
    double orgSceneX;
    double orgSceneY;
    double orgTranslateX;
    double orgTranslateY;
    Wrapper<Point2D> mouseLocation;

	Canvas(VBox main, Whiteboard gui)
	{
		this.setStyle("-fx-background-color: white;");
		this.setMinSize(400, 400);
		this.prefHeightProperty().bind(main.heightProperty());
		this.prefWidthProperty().bind(main.widthProperty());
		shapes = new ArrayList<DShape>();
		models = FXCollections.observableArrayList();
		this.gui = gui;
		selected = null;
		mouseLocation =  new Wrapper<>();
		knobs = new ArrayList<DRect>();
	}

	public ObservableList<DShapeModel> getShapeModels() {
		return models;
	}

	public ArrayList<DShape> getShapes()
	{
		return shapes;
	}

	public void addShape(DShapeModel model)
	{
		DShape shape = getDShape(model);
		shape.setModel(model);
		shapes.add(shape);
		models.add(model);
		shape.draw();
		this.getChildren().add(shape.getShape());
		updateSelection(shape);

	}

	public DShape getDShape(DShapeModel shape)
	{
		if (shape instanceof DRectModel) {
			return new DRect();
		}

		if (shape instanceof DOvalModel) {
			return new DOval();
		}

		if (shape instanceof DLineModel) {
			return new DLine();
		}

		if (shape instanceof DTextModel) {
			return new DText();
		}

		return null; // this is necessary to compile
		// maybe there is another solution, but this
		// was a quick fix
	}

	public void paintComponent()
	{

		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw();
		}
	}

	public void updateColor(Color color)
	{
		if (selected != null) {
			selected.setColor(color);
		}
	}


    
	public void makeSelection(Point2D location)
	{
	    if(selected != null) {
		if(detectKnobClick(location))
		    return;
	    }
	    
	    DShape newSelection = null;
	    for (DShape shape : shapes) {
		if(shape.getBounds().contains(location)) {
		    newSelection = shape;
		}
	    }
	    
	    if (newSelection == null) 
		removeSelection();	    
	    else
		updateSelection(newSelection);
	}

    public boolean detectKnobClick(Point2D location) {
	for(DRect knob: knobs) {
	    if(knob.getShape().contains(location)){
		return true;
	    }
	}
	return false;
    }
	public void updateSelection(DShape selection)
	{
		
		mouseLocation =  new Wrapper<>();
		selected = selection;
		shapes.remove(selection);
		shapes.add(selection);
		models.remove(selection.getModel());
		models.add(selection.getModel());
		gui.updateTable();
		setUpDragging(selection, mouseLocation);

		moveToFront();


		selected.getShape().setOnMouseDragged(event -> {
			if (mouseLocation.value != null && selected != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newX = selected.getModel().getX() + deltaX;
				System.out.println(newX);
				System.out.println(selected.getShape().getParent().getBoundsInParent().getWidth());
				if (newX >= 9
						&& 9 <= selected.getShape().getParent().getBoundsInParent().getWidth() - 9)  {
					selected.getModel().setX(newX);
					knobs.get(0).getShape().setX(knobs.get(0).getShape().getX() + deltaX);
					knobs.get(1).getShape().setX(knobs.get(1).getShape().getX() + deltaX);
					knobs.get(2).getShape().setX(knobs.get(2).getShape().getX() + deltaX);
					knobs.get(3).getShape().setX(knobs.get(3).getShape().getX() + deltaX);
				}
				double newY = selected.getModel().getY() + deltaY;
				double newMaxY = newY + selected.getModel().getHeight();
				if (newY >= 9
						&& newMaxY <= selected.getShape().getParent().getBoundsInLocal().getHeight() - 9) {
					selected.getModel().setY(newY);
					knobs.get(0).getShape().setY(knobs.get(0).getShape().getY() + deltaY);
					knobs.get(1).getShape().setY(knobs.get(1).getShape().getY() + deltaY);
					knobs.get(2).getShape().setY(knobs.get(2).getShape().getY() + deltaY);
					knobs.get(3).getShape().setY(knobs.get(3).getShape().getY() + deltaY);
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());

				paintComponent();
				gui.updateTable();
			}
		});

		// output for testing
		System.out.print("x: " + selection.getModel().getX());
		System.out.print(" y: " + selection.getModel().getY() + '\n');
	}

	public void removeSelection()
	{
		if (selected != null) {
			selected = null;
			removeKnobs();
		}
	}

	public void deleteSelected()
	{
	    if (selected != null) {
		shapes.remove(selected);
		models.remove(selected.getModel());
		selected.getModel().removeListener(selected);
		this.getChildren().remove(selected.getShape());
		removeKnobs();
	    }
	}



	class Wrapper<T> {
		T value;
	}

	private void addKnobs(Rectangle area) {
		DRect topLeft = new DRect(area.getX()-4.5, area.getY()-4.5, 9, 9);

		DRect topRight = new DRect(area.getX()-4.5+area.getWidth(), area.getY()-4.5, 9, 9);

		DRect bottomLeft = new DRect(area.getX()-4.5, area.getY()-4.5+area.getHeight(), 9, 9);

		DRect bottomRight = new DRect(area.getX()+area.getWidth()-4.5, area.getY()+area.getHeight()-4.5, 9, 9);

		setUpDragging(topLeft, mouseLocation);
		topLeft.getShape().setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newX = Math.abs(selected.getModel().getX() + deltaX);
				//if (newX >= 9
				    //	&& newX <= selected.getModel().getX() + selected.getModel().getWidth() - 9) {
					selected.getModel().setX(newX);
					selected.getModel().setWidth(Math.abs(selected.getModel().getWidth()) - deltaX);
					knobs.get(0).getShape().setX(knobs.get(0).getShape().getX() + deltaX);
					knobs.get(2).getShape().setX(knobs.get(2).getShape().getX() + deltaX);
					//	}

				double newY = selected.getModel().getY() + deltaY;
				//	if (newY >= 9
				//			&& newY <= selected.getModel().getY() + selected.getModel().getHeight() - 9) {
					selected.getModel().setY(newY);
					selected.getModel().setHeight(Math.abs(selected.getModel().getHeight()) - deltaY);
					knobs.get(0).getShape().setY(knobs.get(0).getShape().getY() + deltaY);
					knobs.get(1).getShape().setY(knobs.get(1).getShape().getY() + deltaY);
					//}

				paintComponent();
				gui.updateTable();
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		setUpDragging(topRight, mouseLocation);
		topRight.getShape().setOnMouseDragged(event -> {

			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newMaxX = selected.getModel().getX() + selected.getModel().getWidth() + deltaX;
				
				//if (newMaxX >= selected.getModel().getX()
				//		&& newMaxX <= selected.getShape().getParent().getBoundsInLocal().getWidth() - 9) {
					selected.getModel().setWidth(selected.getModel().getWidth() + deltaX);
					knobs.get(1).getShape().setX(knobs.get(1).getShape().getX() + deltaX);
					knobs.get(3).getShape().setX(knobs.get(3).getShape().getX() + deltaX);
					//}
				double newY = selected.getModel().getY() + deltaY;
				//if (newY >= 9
				//		&& newY <= selected.getModel().getY() + selected.getModel().getHeight() - 9) {
					selected.getModel().setY(newY);
					selected.getModel().setHeight(selected.getModel().getHeight() - deltaY);
					knobs.get(0).getShape().setY(knobs.get(0).getShape().getY() + deltaY);
					knobs.get(1).getShape().setY(knobs.get(1).getShape().getY() + deltaY);
					//				}
				
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				paintComponent();
				gui.updateTable();

			}
		});

		setUpDragging(bottomLeft, mouseLocation);
		bottomLeft.getShape().setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {

				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newX = selected.getModel().getX() + deltaX;
				
				//if (newX >= 9
				//		&& newX <= selected.getModel().getX() + selected.getModel().getWidth() - 9) {
					selected.getModel().setX(newX);
					selected.getModel().setWidth(selected.getModel().getWidth() - deltaX);
					knobs.get(0).getShape().setX(knobs.get(0).getShape().getX() + deltaX);
					knobs.get(2).getShape().setX(knobs.get(2).getShape().getX() + deltaX);
					//}
				double newMaxY = selected.getModel().getY() + selected.getModel().getHeight() + deltaY;
				//if (newMaxY >= selected.getModel().getY()
				//		&& newMaxY <= selected.getShape().getParent().getBoundsInLocal().getHeight() - 9) {
					selected.getModel().setHeight(selected.getModel().getHeight() + deltaY);
					knobs.get(2).getShape().setY(knobs.get(2).getShape().getY() + deltaY);
					knobs.get(3).getShape().setY(knobs.get(3).getShape().getY() + deltaY);
					//}
				
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				paintComponent();
				gui.updateTable();

			}

		});

		setUpDragging(bottomRight, mouseLocation);
		bottomRight.getShape().setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newMaxX = selected.getModel().getX() + selected.getModel().getWidth() + deltaX;
				
				//if (newMaxX >= selected.getModel().getX()
				//		&& newMaxX <= selected.getShape().getParent().getBoundsInLocal().getWidth() - 9) {
					selected.getModel().setWidth(selected.getModel().getWidth() + deltaX);
					knobs.get(1).getShape().setX(knobs.get(1).getShape().getX() + deltaX);
					knobs.get(3).getShape().setX(knobs.get(3).getShape().getX() + deltaX);
					//}
				
				double newMaxY = selected.getModel().getY() + selected.getModel().getHeight() + deltaY;
				
				//if (newMaxY >= selected.getModel().getY()
				//		&& newMaxY <= selected.getShape().getParent().getBoundsInLocal().getHeight() - 9) {
					selected.getModel().setHeight(selected.getModel().getHeight() + deltaY);
					knobs.get(2).getShape().setY(knobs.get(2).getShape().getY() + deltaY);
					knobs.get(3).getShape().setY(knobs.get(3).getShape().getY() + deltaY);
					//}
				
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				paintComponent();
				gui.updateTable();

			}
		});

		knobs.add(topLeft);
		knobs.add(topRight);
		knobs.add(bottomLeft);
		knobs.add(bottomRight);

		for (int i = 0; i < knobs.size(); i++) {
			this.getChildren().add(knobs.get(i).getShape());
		}
	}

	private void setUpDragging(DShape r, Wrapper<Point2D> mouseLocation)
	{

		r.getShape().setOnDragDetected(event -> {
			r.getShape().getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
		});

		r.getShape().setOnMouseReleased(event -> {
			r.getShape().getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}



	private void removeKnobs() {
		for (int i = 0; i < knobs.size(); i++) {
			this.getChildren().remove(knobs.get(i).getShape());
		}
		knobs.clear();
	}

	public DShape getSelected()
	{
		return selected;
	}

	public void moveToFront() {
		shapes.remove(selected);
		models.remove(selected.getModel());
		shapes.add(selected);
		models.add(selected.getModel());
		this.getChildren().remove(selected.getShape());
		this.getChildren().add(selected.getShape());
		removeKnobs();
		addKnobs(selected.getBounds());
		gui.updateTable();
		
	}
	
	public void moveToBack() {
		shapes.remove(selected);
		models.remove(selected.getModel());
		shapes.add(0, selected);
		models.add(0, selected.getModel());
		this.getChildren().remove(selected.getShape());
		this.getChildren().add(0, selected.getShape());
		removeKnobs();
		addKnobs(selected.getBounds());
		gui.updateTable();
		
	}


}
