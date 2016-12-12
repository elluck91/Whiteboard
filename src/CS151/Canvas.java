package CS151;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Canvas extends Pane
{
	private ArrayList<DShape> shapes;
	private ObservableList<DShapeModel> models;
	private DShape selected;
	private Whiteboard gui;
	private ArrayList<Rectangle> knobs;
	private Wrapper<Point2D> mouseLocation;
	private Rectangle topLeft;
	private Rectangle topRight;
	private Rectangle bottomLeft;
	private Rectangle bottomRight;
	private Rectangle startKnob;
	private Rectangle endKnob;

	private final int knobSize = 9;

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
		knobs = new ArrayList<Rectangle>();
		topLeft = new Rectangle();
		topRight = new Rectangle();
		bottomLeft = new Rectangle();
		bottomRight = new Rectangle();
		startKnob = new Rectangle();
		endKnob = new Rectangle();
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
		if (gui.getStatus().equals("server")) {
			System.out.println("Berfore change: " + model.getId());
			model.setId(UUID.randomUUID().toString());
		}
			
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
			if(shape.getShape().contains(Adapters.awtToFx(location)))
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
		for(Rectangle knob: knobs) {
			if(knob.contains(Adapters.awtToFx(location))) {
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
		if (gui.getStatus().equals("server") ||
		    gui.getStatus().equals("normal")) {
		    handleShapeDrag();
		}
	}


	public void handleShapeDrag() {
		enableShapeDragging(selected.getShape());		
		selected.getShape().setOnMouseDragged(event -> {
			if (mouseLocation.value != null && selected != null) {
				double deltaX = event.getSceneX()-mouseLocation.value.getX();
				double deltaY = event.getSceneY()-mouseLocation.value.getY();
				if(selected instanceof DLine) {
					((DLine) selected).moveBy(deltaX, deltaY);
					gui.doSend("change", selected.getModel());
					setupLineKnobs();
				} else {
					selected.moveBy(deltaX, deltaY);
					gui.doSend("change", selected.getModel());
					setupShapeKnobs();
				}
				mouseLocation.value = new Point2D.Double(event.getSceneX(), event.getSceneY());
				selected.draw();
				gui.updateTable();
			}
		});	
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
			if (gui.getStatus().equals("server"))
				gui.doSend("delete", selected.getModel());
			shapes.remove(selected);
			models.remove(selected.getModel());
			selected.getModel().removeListener(selected);
			this.getChildren().remove(selected.getShape());
			removeKnobs();
			selected = null;
		}
	}


	/**
	 * Wrapper class for the moving
	 */
	class Wrapper<T> {
		T value;
	}


	/**
	 * Create knobs and set up the dragging feature.
	 *
	 */
	public void addKnobs() {
	    System.out.println("Adding knobs");
		if (selected instanceof DLine) {
			DLine line = (DLine) selected;
			setupLineKnobs();
			setupKnobDrag(startKnob, line.getStart());
			setupKnobDrag(endKnob, line.getEnd());
			addLineKnobs();
			addToPane();
		}
		else {		
			setupShapeKnobs();
			setupKnobDrag(topLeft, selected.getBottomRight());
			setupKnobDrag(topRight, selected.getBottomLeft());
			setupKnobDrag(bottomLeft, selected.getTopRight());
			setupKnobDrag(bottomRight, selected.getTopLeft());
			addShapeKnobs();
			addToPane();	
		}

	}

	private void setupLineKnobs() {
		DLine line = (DLine) selected;
		createLineKnob(startKnob, line.getEnd());
		enableKnobDragging(startKnob);	
		createLineKnob(endKnob, line.getStart());
		enableKnobDragging(endKnob);
	}

	private void createLineKnob(Rectangle knob, Point2D point) {
		knob.setFill(javafx.scene.paint.Color.BLACK);
		knob.setX(point.getX()-(knobSize/2.0));
		knob.setY(point.getY()-(knobSize/2.0));
		knob.setWidth(knobSize);
		knob.setHeight(knobSize);
	}


	private void dragKnob(Point2D anchor, Point2D result) {
		if(selected instanceof DLine) {
			moveLine(anchor, result);
			setupLineKnobs();
		} else {
			move(anchor, result);
			setupShapeKnobs();
		}
		gui.updateTable();
	}


	public void moveLine(Point2D anchor, Point2D result) {
		if (!gui.getStatus().equals("server")) {
			((DLine)selected).moveTo(anchor, result);
			((DLine)selected).draw();
		}
		else {
			((DLine)selected).moveTo(anchor, result);
			((DLine)selected).draw();
			gui.doSend("change", selected.getModel());
		}
		
	}

	private void move(Point2D anchor, Point2D result) {
		double x = result.getX() < anchor.getX()?result.getX():anchor.getX();
		double y = result.getY() < anchor.getY()?result.getY():anchor.getY();
		double width = Math.abs(anchor.getX()-result.getX());
		double height = Math.abs(anchor.getY()-result.getY());
		selected.moveTo(x, y, width, height);
		gui.doSend("change", selected.getModel());
		selected.draw();
	}


	private void setupKnobDrag(Rectangle knob, Point2D anchor) {				  	 
		knob.setOnMouseDragged(event -> {
			javafx.geometry.Point2D temp = knob.sceneToLocal(event.getSceneX(),
					event.getSceneY());
			Point2D.Double result = new Point2D.Double(temp.getX(), temp.getY());
			if(result.getX() > 0 && result.getY() > 0) {
				dragKnob(anchor, result);
			}
		});
	}


	private void setupShapeKnobs() {
		setupTopLeft(selected.getBounds());
		enableKnobDragging(topLeft);
		setupTopRight(selected.getBounds());
		enableKnobDragging(topRight);	
		setupBottomLeft(selected.getBounds());
		enableKnobDragging(bottomLeft);	
		setupBottomRight(selected.getBounds());
		enableKnobDragging(bottomRight);
	}

	private void setupTopLeft(Rectangle bounds) {
		topLeft.setFill(javafx.scene.paint.Color.BLACK);
		topLeft.setX(bounds.getX()-(knobSize/2.0));
		topLeft.setY(bounds.getY()-(knobSize/2.0));
		topLeft.setWidth(knobSize);
		topLeft.setHeight(knobSize);
	}

	private void setupTopRight(Rectangle bounds) {
		topRight.setFill(javafx.scene.paint.Color.BLACK);
		topRight.setX(bounds.getX()+bounds.getWidth()-(knobSize/2.0));
		topRight.setY(bounds.getY()-(knobSize/2.0));
		topRight.setWidth(knobSize);
		topRight.setHeight(knobSize);
	}

	private void setupBottomLeft(Rectangle bounds) {
		bottomLeft.setFill(javafx.scene.paint.Color.BLACK);
		bottomLeft.setX(bounds.getX()-(knobSize/2.0));
		bottomLeft.setY(bounds.getY()+bounds.getHeight()-(knobSize/2.0));
		bottomLeft.setWidth(knobSize);
		bottomLeft.setHeight(knobSize);
	}

	private void setupBottomRight(Rectangle bounds) {
		bottomRight.setFill(javafx.scene.paint.Color.BLACK);
		bottomRight.setX(bounds.getX()+bounds.getWidth()-(knobSize/2.0));
		bottomRight.setY(bounds.getY()+bounds.getHeight()-(knobSize/2.0));
		bottomRight.setWidth(knobSize);
		bottomRight.setHeight(knobSize);
	}


	private void enableShapeDragging(Shape shape) {
		shape.setOnDragDetected(event -> {
			shape.getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D.Double(event.getSceneX(),
					event.getSceneY());
		});

		shape.setOnMouseReleased(event -> {
			shape.getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});	   
	}


	/**
	 * Set up dragging for a knob.
	 * @param DShape knob
	 */
	private void enableKnobDragging(Shape knob)
	{	
		knob.setOnDragDetected(event -> {
			knob.getParent().setCursor(Cursor.CLOSED_HAND);
		});

		knob.setOnMouseReleased(event -> {
			knob.getParent().setCursor(Cursor.DEFAULT);
			addKnobs();
		});
	}


	private void addToPane() {
	    if(gui.getStatus().equals("server") ||
	       gui.getStatus().equals("normal") ){
		for(int i = 0; i < knobs.size(); i++) 
		    this.getChildren().add(knobs.get(i));
	    }
	}


	private void addShapeKnobs() {
		removeKnobs();		
		knobs.add(topLeft);
		knobs.add(topRight);
		knobs.add(bottomLeft);
		knobs.add(bottomRight);
	}

	private void addLineKnobs() {
		removeKnobs();
		knobs.add(startKnob);
		knobs.add(endKnob);	
	}

	/**
	 * Remove the knobs from the pane.
	 */
	public void removeKnobs() {
		for (int i = 0; i < knobs.size(); i++) 
			this.getChildren().remove(knobs.get(i));       
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
		addKnobs();
		gui.updateTable();
	}
	
	public void moveToFront(DShape shape) {
		shapes.remove(shape);
		models.remove(shape.getModel());
		shapes.add(shape);
		models.add(shape.getModel());
		this.getChildren().remove(shape.getShape());
		this.getChildren().add(shape.getShape());
		removeKnobs();
		addKnobs();
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
		addKnobs();
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


	public DShapeModel findById(DShapeModel model) {
		System.out.println("Passed ID: " + model.getModel().getId());
		DShapeModel someModel = null;
		
		for (DShapeModel x : models) {
			System.out.println("Array ID: " + x.getModel().getId());
			if (model.getId().equals(x.getId()))
				someModel = x;
		}
		
		return someModel;
	}
	
	public DShape findShapeByID(DShapeModel model) {
		DShape current = null;
		
		for (DShape shape : shapes) {
			
			if (shape.getModel().getId().equals(model.getId()))
				current = shape;
		}
		System.out.println("Finding shape by id. Found? " + (current != null));
		return current;
	}


	public void removeShape(DShapeModel model) {
	    //		selected = getDShape(findById(model));
		deleteSelected();
	}
	
	public void setSelected(DShape selected) {
		this.selected = selected;
	}
	
	public void removeSelected() {
		deleteSelected();
	}

}
