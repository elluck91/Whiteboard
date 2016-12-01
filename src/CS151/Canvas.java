package CS151;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import java.awt.Point;

public class Canvas extends Pane implements Observer{

    /*
     * The shapes list in the canvas is effectively the "document"
     * the user is editing; whatever is in that list, it is being
     * edited. Removing a shape from this list removes it from the
     * document as well.
     */
    private ObservableList<DShape> shapes;
    
    // the shape currently selected
    private DShape selected;
    
    Canvas() {
	super();
	this.setStyle("-fx-background-color: white;");
	this.setPrefSize(400, 400);
	shapes = FXCollections.observableArrayList();
	selected = null;
    }
		
    // loops through the list of shapes and draws them
    public void paintComponent() {
	for (int i = 0; i < shapes.size(); i++)
	    shapes.get(i).draw();
    }
    
    public ObservableList<DShape> getShapes() {
	return shapes;
    }
		
    public ObservableList<DShapeModel> getShapeModels() {
	ObservableList<DShapeModel> models = FXCollections.observableArrayList();
	
	for (DShape each : shapes)
	    models.add(each.getModel());
	
	return models;
	
    }
    
    /**
     * Add the shape to the list of shapes and
     * draw it on the canvas
     */
    public void addShape(DShape shape) {
	shapes.add(shape);
	shape.draw();
	this.getChildren().add(shape.getShape());			
    }

    /** 
     * Given a point determine which shape should be selected
     * if no shapes are selected, set selected to null
     */
    public void makeSelection(Point location) {
	
    }

	    
    @Override
    public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
    }
		
}
