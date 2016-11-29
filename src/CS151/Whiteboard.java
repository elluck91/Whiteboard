package CS151;

import javafx.application.Application;
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
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Whiteboard extends Application {

    private WhiteboardPresenter presenter;
    private Canvas canvas;
    private Button rect;
    private Button oval;
    private Button line;
    private Button text;
    private Button colorPicker;
    private Button toFront;
    private Button toBack;
    private Button remove;
    private TextField textInput;
    private Button edScript;
    private TableView<DShapeModel> tv;
    private MenuItem save;
    private MenuItem open;
    private MenuItem close;
    private Color color;
    
    @Override
    public void start(Stage stage) throws Exception {
	presenter = new WhiteboardPresenter();
	presenter.attachView(this);
	canvas = new Canvas();
	
	VBox main = new VBox();
	VBox menu = getMenu();
	GridPane gp = new GridPane();
	VBox leftColumn = getLeftColumn();

	rect.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		    DRect rect = new DRect();
		    rect.randomize(400);
		    canvas.addShapes(rect);
		    // change this line to update tv for the newly added
		    // shape instead of updating the entire tableview
		    tv.setItems(canvas.getShapeModels());				    
		}
	    });

	oval.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		    DOval oval = new DOval();
		    //oval.randomize(400);
		    canvas.addShapes(oval);
		    // change function call below
		    tv.setItems(canvas.getShapeModels());
		}
	    });

	line.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	text.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	colorPicker.setOnAction(new EventHandler() {
		public void handle(Event t) {
		    ColorPickerWindow colorPick = new ColorPickerWindow();
		    color = colorPick.display();
		    System.out.println(color.toString());
		}
	    });
	
	edScript.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	toFront.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	toBack.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	remove.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	save.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	open.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });

	close.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {

		}
	    });
	
	gp.addColumn(0, leftColumn);
	gp.addColumn(1, canvas);
	
	main.getChildren().addAll(menu, gp);
		
	stage.setOnCloseRequest(e -> {
		e.consume();
		if (ConfirmBox.display("ConfirmBox", "Are you sure that you want to close this window?")) 
		    stage.close();
		else	
		    return;
	    });
	
	stage.setScene(new Scene(main, 900, 420));
	stage.setTitle("Whiteboard - Collaborative");
	stage.show();
	
    }
    
    public VBox getTopLeft() {
	final int BOX_SIZE = 10;
	
	VBox topLeft = new VBox();
	topLeft.setPrefSize(500, 200);
	topLeft.setStyle("-fx-background-color: #bbfff8;");

	Label add = new Label("Add:");

	rect = new Button("Rect");
	oval = new Button("Oval");
	line = new Button("Line");
	text = new Button("Text");

	HBox line1 = new HBox(BOX_SIZE);
	line1.getChildren().addAll(add, rect, oval, line, text);

	colorPicker = new Button("Select Color");

	HBox line2 = new HBox(BOX_SIZE);
	line2.getChildren().add(colorPicker);

	textInput = new TextField("Enter text...");
	edScript = new Button("Edwardian Script");

	HBox line3 = new HBox(BOX_SIZE);
	line3.getChildren().addAll(textInput, edScript);

	toFront = new Button("Move to Front");
	toBack = new Button("Move to Back");
	remove = new Button("Remove Shape");

	HBox line4 = new HBox(BOX_SIZE);
	line4.getChildren().addAll(toFront, toBack, remove);

	topLeft.setAlignment(Pos.TOP_CENTER);
	topLeft.setSpacing(15);
	topLeft.setPadding(new Insets(20, 10, 10, 20));

	topLeft.getChildren().addAll(line1, line2, line3, line4);
	return topLeft;	
    }

    public VBox getBottomLeft() {
	tv = new TableView();
	tv.setMaxHeight(200);

	TableColumn xColumn = new TableColumn("x");
	xColumn.setMinWidth(100);
	xColumn.setCellValueFactory(
		        new PropertyValueFactory<DShapeModel, String>("x"));

	TableColumn yColumn = new TableColumn("y");
	yColumn.setMinWidth(100);
	yColumn.setCellValueFactory(
			     new PropertyValueFactory<DShapeModel, String>("y"));

	TableColumn widthColumn = new TableColumn("width");
	widthColumn.setMinWidth(100);
	widthColumn.setCellValueFactory(
			    new PropertyValueFactory<DShapeModel, String>("width"));

	TableColumn heightColumn = new TableColumn("height");
	heightColumn.setMinWidth(100);
	heightColumn.setCellValueFactory(
			     new PropertyValueFactory<DShapeModel, String>("height"));

	tv.getColumns().addAll(xColumn, yColumn, widthColumn, heightColumn);

	tv.setItems(canvas.getShapeModels());
	tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	VBox table = new VBox();
	table.getChildren().add(tv);
	return table;	
    }

    public VBox getLeftColumn() {
	VBox left = new VBox();
	left.getChildren().addAll(getTopLeft(), getBottomLeft());
	return left;
    }

    public VBox getMenu() {
	VBox menu = new VBox();
	MenuBar menuBar = new MenuBar();
	Menu menuFile = new Menu("File");

	save = new MenuItem("Save");
	open = new MenuItem("Open");
	close = new MenuItem("Close");

	menuFile.getItems().addAll(save, open, close);
	menuBar.getMenus().add(menuFile);
	menu.getChildren().add(menuBar);
	return menu;
    }

    public static void main(String[] args) {
	launch(args);
    }

}
