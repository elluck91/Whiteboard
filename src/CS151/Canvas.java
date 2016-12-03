package CS151;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.awt.Point;
import java.awt.Rectangle;

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
    public void addShape(DShapeModel shape) {
	/*
	shapes.add(shape);
	updateSelection(shape);
	shape.draw();
	this.getChildren().add(shape.getShape());			
	*/
	DShape s = getDShape(shape);
	s.setModel(shape);
	shapes.add(s);
	s.draw();
	updateSelection(s);
	this.getChildren().add(s.getShape());
    }


    public DShape getDShape(DShapeModel shape) {
	if(shape instanceof DRectModel)
	    return new DRect();
	if(shape instanceof DOvalModel)
	    return new DOval();
	if(shape instanceof DLineModel)
	    return new DLine();
	if(shape instanceof DTextModel)
	    return new DText();
	return null; // this is necessary to compile
	// maybe there is another solution, but this
	// was a quick fix
    }
    
    /**
     * Update the color of the currently selected shape
     */
    public void updateColor(Color c) {
	if(selected != null) {
	    selected.setColor(c);
	}
    }

    
    /** 
     * Given a point, determine which shape should be selected
     * if no shapes are selected, should we set selection to null?
     */
    public void makeSelection(Point location) {
	DShape newSelection = null;
	for(DShape shape: shapes) {
	    Rectangle area = shape.getBounds();
	    if(area.contains(location)){
		newSelection = shape;
	    }
	}
	
	if(newSelection == null) {
	    removeSelection();
	} else {
	    updateSelection(newSelection);
	}
    }

    
    /**
     * Update the currently selected shape to
     * @param DShape selection
     */
    public void updateSelection(DShape selection) {
	removeSelection();
	selected = selection;
	selected.drawKnobs();
	// output for testing
	System.out.print("x: " +selection.getModel().getX());
	System.out.print(" y: " + selection.getModel().getY() + '\n');
    }

    
    /**
     * Remove the currently selected shape
     */
    public void removeSelection() {
	if(selected != null) {
	    selected.removeKnobs();
	    selected = null;
	}
    }

    
    @Override
    public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
    }
		
}
