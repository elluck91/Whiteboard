package CS151;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.awt.geom.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.awt.Color;
import javafx.scene.shape.Rectangle;

public class Canvas extends Pane
{
	private ArrayList<DShape> shapes;
	private ObservableList<DShapeModel> models;
	private DShape selected;
	private Whiteboard gui;
	private ArrayList<DRect> knobs;
	private Wrapper<Point2D> mouseLocation;

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


	/**
	 * Get the list of DShapeModels.
	 * @return ObservableList<DShapeModel>
	 */
	public ObservableList<DShapeModel> getShapeModels() {
		return models;
	}


	/**
	 * Return the list of DShapes.
	 * @return ArrayList<DShape>
	 */
	public ArrayList<DShape> getShapes()
	{
		return shapes;
	}


	/**
	 * Add a shape to the canvas.
	 * @param DShapeModel model
	 */
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


	/**
	 * Determine the correct DShape to create given
	 * a DShapeModel.
	 * @param DShapeModel model
	 * @return DShape
	 */
	public DShape getDShape(DShapeModel model)
	{
		if (model instanceof DRectModel) 
			return new DRect();	

		if (model instanceof DOvalModel) 
			return new DOval();


		if (model instanceof DLineModel) 
			return new DLine();

		if (model instanceof DTextModel) 
			return new DText();	

		return null; 	
	}


	/**
	 * Paint all the shapes in the canvas.
	 */
	public void paintComponent()
	{	
		for (int i = 0; i < shapes.size(); i++) 
			shapes.get(i).draw();

	}

	/**
	 * Update the color of the selected shape.
	 * @param Color color
	 */
	public void updateColor(Color color)
	{
		if (selected != null) 
			selected.setColor(color);
	}


	/**
	 * Determine if a click in the canvas was on a shape.
	 * Set selected to be the shape the click was on.
	 * @param Point2D location
	 */
	public void makeSelection(Point2D location)
	{
		if(selected != null) {
			if(detectKnobClick(location))
				return;
		}

		DShape newSelection = null;
		for (DShape shape : shapes) {
			if(shape.getBounds().contains(Adapters.awtToFx(location))) 
				newSelection = shape;
		}

		if (newSelection == null) 
			removeSelection();	    
		else
			updateSelection(newSelection);
	}

	/**
	 * Determine if a click in the canvas was on a knob.
	 * @param Point2D location
	 * @return boolean
	 */
	public boolean detectKnobClick(Point2D location) {
		for(DRect knob: knobs) {
			if(knob.getShape().contains(Adapters.awtToFx(location))) { 
				return true;
			}
		}
		return false;
	}
	/**
	 * Update the selected shape and setup moving/dragging.
	 * @param DShape selection
	 */
	public void updateSelection(DShape selection)
	{
		mouseLocation =  new Wrapper<>();
		selected = selection;
		shapes.remove(selection);
		shapes.add(selection);
		models.remove(selection.getModel());
		models.add(selection.getModel());
		gui.updateTable();
		moveToFront();

		if (selected instanceof DLine && selected != null) {
			
			DLine selectedLine = (DLine) selected;
			DLineModel lineModel = (DLineModel) selectedLine.getModel();
			setUpDragging(selectedLine, mouseLocation);
			selectedLine.getShape().setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					System.out.println("HERE!!!!!!!!!!!!!!!!!!!!!");
					double deltaX = event.getSceneX() - mouseLocation.value.getX();
					double deltaY = event.getSceneY() - mouseLocation.value.getY();
					double newX = lineModel.getX() + deltaX;
					double newY = lineModel.getY() + deltaY;
					double newMaxY = newY + lineModel.getHeight();
					if (newX >= 9
							&& 9 <= selected.getShape().getParent().getBoundsInParent().getWidth() - 9
							&& newY >= 9 && newMaxY <= selected.getShape().getParent().getBoundsInLocal().getHeight() - 9)  {
						lineModel.setStartX(lineModel.getStartX() + deltaX);
						lineModel.setStartY(lineModel.getStartY() + deltaY);
						
						lineModel.setEndX(lineModel.getEndX() + deltaX);
						lineModel.setEndY(lineModel.getEndY() + deltaY);

						knobs.get(0).getShape().setX(knobs.get(0).getShape().getX() + deltaX);
						knobs.get(0).getShape().setY(knobs.get(0).getShape().getY() + deltaY);
						knobs.get(1).getShape().setX(knobs.get(1).getShape().getX() + deltaX);
						knobs.get(1).getShape().setY(knobs.get(1).getShape().getY() + deltaY);
					}

					mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
					paintComponent();
					gui.updateTable();
				}

			});
		}
		else {
			setUpDragging(selection, mouseLocation);
			selected.getShape().setOnMouseDragged(event -> {
				if (mouseLocation.value != null && selected != null) {
					double deltaX = event.getSceneX() - mouseLocation.value.getX();
					double deltaY = event.getSceneY() - mouseLocation.value.getY();
					double newX = selected.getModel().getX() + deltaX;

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
					mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());

					paintComponent();
					gui.updateTable();
				}
			});
		}

		// output for testing
		System.out.print("x: " + selection.getModel().getX());
		System.out.print(" y: " + selection.getModel().getY() + '\n');
	}


	/**
	 * Clear the selection.
	 */
	public void removeSelection()
	{
		if (selected != null) {
			selected = null;
			removeKnobs();
		}
	}


	/**
	 * Delete the selected shape from the canvas.
	 */
	public void deleteSelected()
	{
		if (selected != null) {
			shapes.remove(selected);
			models.remove(selected.getModel());
			selected.getModel().removeListener(selected);
			this.getChildren().remove(selected.getShape());
			removeKnobs();
			selected = null;
		}
	}


	/**
	 * Wrapper class for the moving/resizing
	 */
	class Wrapper<T> {
		T value;
	}


	/**
	 * Create knobs and add them to the list of knobs.
	 * @param Rectangle area
	 */
	void addKnobs(Rectangle area) {

		// Knobs for DLine and the rest are differed, use instance of
				if (selected instanceof DLine) {
					DLine selectedLine = (DLine)selected;
					DLineModel lineModel = (DLineModel)selectedLine.getModel();
					DRect topLeft = new DRect(lineModel.getStartX()-4.5, lineModel.getStartY()-4.5, 9, 9);
					setUpDragging(topLeft, mouseLocation);
					topLeft.getShape().setOnMouseDragged(event -> {
						if (mouseLocation.value != null) {

							double deltaX = event.getSceneX() - mouseLocation.value.getX();
							double deltaY = event.getSceneY() - mouseLocation.value.getY();
							double newMaxX = lineModel.getStartX() + deltaX;
							double newMaxY = lineModel.getStartY() + deltaY;

							if (selectedLine.getShape().getParent().getBoundsInLocal().getMaxX() - 9 >= newMaxX
									&& selectedLine.getShape().getParent().getBoundsInLocal().getMaxY() -  9 >= newMaxY
									&& selectedLine.getShape().getParent().getBoundsInLocal().getMinY() +  9 <= newMaxY
									&& selectedLine.getShape().getParent().getBoundsInLocal().getMinX() +  9 <= newMaxX) {
								
								lineModel.setStartX(lineModel.getStartX() + deltaX);
								lineModel.setStartY(lineModel.getStartY() + deltaY);
								
								knobs.get(0).getShape().setX(knobs.get(0).getShape().getX() + deltaX);
								knobs.get(0).getShape().setY(knobs.get(0).getShape().getY() + deltaY);



							}
							mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
							paintComponent();
							gui.updateTable();

						}
					});

					DRect bottomRight = new DRect(lineModel.getEndX()-4.5, lineModel.getEndY()-4.5, 9, 9);
					setUpDragging(bottomRight, mouseLocation);
					bottomRight.getShape().setOnMouseDragged(event -> {

						if (mouseLocation.value != null) {


							double deltaX = event.getSceneX() - mouseLocation.value.getX();
							double deltaY = event.getSceneY() - mouseLocation.value.getY();
							double newMaxX = lineModel.getEndX() + deltaX;
							double newMaxY = lineModel.getEndY() + deltaY;

							if (selectedLine.getShape().getParent().getBoundsInLocal().getMaxX() - 9 >= newMaxX
									&& selectedLine.getShape().getParent().getBoundsInLocal().getMaxY() -  9 >= newMaxY
									&& selectedLine.getShape().getParent().getBoundsInLocal().getMinY() +  9 <= newMaxY
									&& selectedLine.getShape().getParent().getBoundsInLocal().getMinX() +  9 <= newMaxX) {
								
								
								lineModel.setEndX(lineModel.getEndX() + deltaX);
								lineModel.setEndY(lineModel.getEndY() + deltaY);
								knobs.get(1).getShape().setX(knobs.get(1).getShape().getX() + deltaX);
								knobs.get(1).getShape().setY(knobs.get(1).getShape().getY() + deltaY);


							}
							mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
							paintComponent();
							gui.updateTable();

						}
					});

					knobs.add(topLeft);
					knobs.add(bottomRight);

				}
		else {
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

					selected.draw();
					gui.updateTable();
					mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
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

					mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
					selected.draw();
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

					mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
					selected.draw();
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

					mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
					selected.draw();
					gui.updateTable();

				}
			});

			knobs.add(topLeft);
			knobs.add(topRight);
			knobs.add(bottomLeft);
			knobs.add(bottomRight);

			

		}
				for (int i = 0; i < knobs.size(); i++) 
					this.getChildren().add(knobs.get(i).getShape());
	}

	/**
	 * Set up dragging for a shape.
	 * @param DShape r, Wrapper<Point2D> mouseLocation
	 */
	private void setUpDragging(DShape r, Wrapper<Point2D> mouseLocation)
	{

		r.getShape().setOnDragDetected(event -> {
			r.getShape().getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
		});

		r.getShape().setOnMouseReleased(event -> {
			r.getShape().getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}


	/**
	 * Remove the knobs from the pane.
	 */
	public void removeKnobs() {
		for (int i = 0; i < knobs.size(); i++) 
			this.getChildren().remove(knobs.get(i).getShape());       
		knobs.clear();
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


	public DShape getSelected() {
		return selected;
	}
	
	public void clearCanvas() {
		shapes.clear();
		models.clear();
		this.getChildren().clear();
		removeKnobs();
		selected = null;
	}


}
