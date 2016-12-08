package CS151;

import javafx.scene.control.TableView;

// Presenter creates the elements to be displayed in the GUI

public class WhiteboardPresenter
{

	private Whiteboard gui;
	private final int defaultX = 10;
	private final int defaultY = 10;
	private final int defaultWidth = 20;
	private final int defaultHeight = 20;

	public void attachView(Whiteboard gui) {
		this.gui = gui;
	}


	/**
	 * Add a new DShape to the view
	 */
	public void addDShape(DShapeModel shape) {
		shape.setX(defaultX);
		shape.setY(defaultY);
		shape.setWidth(defaultWidth);
		shape.setHeight(defaultHeight);
		gui.updateView(shape);
		shape.attachPresenter(this);
	}

	public void updateShape() {

	}
	
	public Whiteboard getGui() {
		return gui;
	}
	
	public TableView<DShapeModel> getTable() {
		return gui.getTv();
	}

}
