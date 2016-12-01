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

    Whiteboard gui;
    Canvas canvas;
    javafx.scene.paint.Color color;

    public void attachView(Whiteboard gui)
    {
        this.gui = gui;
    }

    WhiteboardPresenter()
    {
        canvas = new Canvas();
        color = javafx.scene.paint.Color.GRAY;
    }

}
