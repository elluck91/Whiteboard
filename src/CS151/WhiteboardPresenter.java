package CS151;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

// Presenter creates the elements to be displayed in the GUI

public class WhiteboardPresenter
{

    private Whiteboard gui;
    private final int defaultX = 10;
    private final int defaultY = 10;
    private final int defaultWidth = 20;
    private final int defaultHeight = 20;
    
    public void attachView(Whiteboard gui)
    {
        this.gui = gui;
    }

    WhiteboardPresenter()
    {
	// nothing to instantiate?
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
    }
    
}
