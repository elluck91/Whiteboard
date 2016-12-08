package CS151;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Canvas extends Pane implements Observer
{

	/*
	 * The shapes list in the canvas is effectively the "document"
	 * the user is editing; whatever is in that list, it is being
	 * edited. Removing a shape from this list removes it from the
	 * document as well.
	 */
    private ObservableList<DShape> shapes;

	// the shape currently selected
	private DShape selected;

	Canvas(VBox main)
	{
	       super();
	        this.setStyle("-fx-background-color: white;");
	        this.setMinSize(400, 400);
	        this.prefHeightProperty().bind(main.heightProperty());
	        this.prefWidthProperty().bind(main.widthProperty());
	        shapes = FXCollections.observableArrayList();
	        selected = null;
	}

	/**
	 * Loops through the list of shapes and draws them. This code might be worth
	 * thinking about again. The call to clear the canvas is necessary to get
	 * shapes that have been modified to reappear in the canvas with the
	 * modifications made. This was a hack and not necessarily good design.
	 */
	public void paintComponent()
	{
		// this.getChildren().clear();
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw();
			//this.getChildren().add(shapes.get(i).getShape());
		}
	}

	public ObservableList<DShape> getShapes()
	{
		return shapes;
	}

	public ObservableList<DShapeModel> getShapeModels()
	{
		ObservableList<DShapeModel> models = FXCollections.observableArrayList();

		for (DShape each : shapes) {
			models.add(each.getModel());
		}

		return models;

	}

	/**
	 * Add the shape to the list of shapes and draw it on the canvas
	 *
	 * @param DShapeModel model
	 */
	public void addShape(DShapeModel model)
	{
		DShape shape = getDShape(model);
		shape.setModel(model);
		shapes.add(shape);
		shape.draw();
		updateSelection(shape);
		this.getChildren().add(shape.getShape());
	}

	/**
	 * Create a new DShape given a DShapeModel
	 *
	 * @param DShapeModel shape
	 * @return DShape
	 */
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

	/**
	 * Update the color of the currently selected shape
	 *
	 * @param Color color
	 */
	public void updateColor(Color color)
	{
		if (selected != null) {
			selected.setColor(color);
		}
	}

	/**
	 * Given a point, determine which shape should be selected if no shapes are
	 * selected, should we set selection to null?
	 */
	public void makeSelection(Point2D location)
	{
		DShape newSelection = null;
		for (DShape shape : shapes) {
			javafx.scene.shape.Rectangle area = shape.getBounds();
			if (area.contains(location)) {
				newSelection = shape;

			}
		}

		if (newSelection == null) {
			removeSelection();
		} else {
			updateSelection(newSelection);
		}
	}

	/**
	 * Update the currently selected shape to
	 *
	 * @param DShape selection
	 */
	public void updateSelection(DShape selection)
	{
		removeSelection();
		selected = selection;
		selected.drawKnobs();
		// output for testing
		System.out.print("x: " + selection.getModel().getX());
		System.out.print(" y: " + selection.getModel().getY() + '\n');
	}

	/**
	 * Set the selected shape to null.
	 */
	public void removeSelection()
	{
		if (selected != null) {
			selected.removeKnobs();
			selected = null;
		}
	}

	/**
	 * Delete the currently selected shape and return the model that was deleted
	 * to the GUI. The GUI will then know to update the table of coordinates. If
	 * there is no shape selected, do nothing.
	 */
	public DShape deleteSelected()
	{
		if (selected != null) {
			DShape delete = selected;
			shapes.removeAll(delete);
			selected.getModel().removeListener(delete);

			if (selected instanceof DRect) {
				// delete rectangle stuff
				delete.getShape().parentProperty().removeListener(((DRect) delete).getListener());
				for (javafx.scene.shape.Rectangle knob : ((DRect) delete).getKnob()) {
					this.getChildren().remove(knob);
					this.getChildren().remove(delete.getShape());

				}
				selected = null;

			}

			//
			if (selected instanceof DOval) {
				// delete ellipse stuff
				delete.getShape().parentProperty().removeListener(((DOval) delete).getListener());
				for (javafx.scene.shape.Rectangle knob : ((DOval) delete).getKnob()) {
					this.getChildren().remove(knob);
					this.getChildren().remove(delete.getShape());
					this.getChildren().remove(((DOval) delete).getOvalShape());

				}
				selected = null;

			}
			//

			return delete;
		} else {
			return null;
		}
	}

	/**
	 * Get the selected shape
	 *
	 * @return DShape
	 */
	public DShape getSelected()
	{
		return selected;
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub

	}

}
