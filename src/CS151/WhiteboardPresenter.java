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

// Presenter creates the elements to be displayed in the GUI
public class WhiteboardPresenter {

	Whiteboard gui;
	Canvas canvas;
	javafx.scene.paint.Color color;


	public void attachView(Whiteboard gui) {
		this.gui = gui;
	}

	WhiteboardPresenter() {
		canvas = new Canvas();
		color = javafx.scene.paint.Color.GRAY;
	}

	public VBox getTopLeft() {
		VBox topLeft = new VBox();
		topLeft.setPrefSize(500, 200);
		topLeft.setStyle("-fx-background-color: #bbfff8;");

		Label add = new Label("Add: ");

		Button rect = new Button("Rect");
		rect.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		Button oval = new Button("Oval");

		oval.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		Button line = new Button("Line");
		line.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		Button text = new Button("Text");
		text.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		HBox line1 = new HBox(10);

		line1.getChildren().addAll(add, rect, oval, line, text);

		// Color picker
		Button colorButton = new Button("Select Color");

		colorButton.setOnAction(new EventHandler() {
			public void handle(Event t) {
				ColorPickerWindow colorPick = new ColorPickerWindow();
				color = colorPick.display();
				System.out.println(color.toString());
			}
		});


		HBox line2 = new HBox(10);
		line2.getChildren().add(colorButton);



		TextField input = new TextField("Enter text...");
		Button edScript = new Button("Edwardian Script");
		edScript.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		HBox line3 = new HBox(10);
		line3.getChildren().addAll(input, edScript);


		Button toFront = new Button("Move To Front");
		toFront.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		Button toBack = new Button("Move To Back");

		toBack.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		Button shape = new Button("Remove Shape");
		shape.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to take
			}
		});

		HBox line4 = new HBox(10);
		line4.getChildren().addAll(toFront, toBack, shape);


		topLeft.setAlignment(Pos.TOP_CENTER);
		topLeft.setSpacing(15);
		topLeft.setPadding(new Insets(20, 10, 10, 20));

		topLeft.getChildren().addAll(line1, line2, line3, line4);
		return topLeft;
	}

	public VBox getBottomLeft() {
		TableView<DShapeModel> tv = new TableView();
		tv.setMaxHeight(200);

		TableColumn xColumn = new TableColumn("x");
		xColumn.setMinWidth(100);
		xColumn.setCellValueFactory(
				new PropertyValueFactory<DShapeModel, String>("y"));

		// Second column
		TableColumn yColumn = new TableColumn("y");
		yColumn.setMinWidth(100);
		yColumn.setCellValueFactory(
				new PropertyValueFactory<DShapeModel, String>("width"));

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
		VBox leftOne = getTopLeft();
		VBox leftTwo = getBottomLeft();

		VBox left = new VBox();
		left.getChildren().addAll(leftOne, leftTwo);
		return left;
	}

	public Canvas getRightColumn() {
		Canvas canvas = new Canvas();
		return canvas;
	}

	public void addShape(DShape shape) {
		canvas.addShapes(shape);

	}

	public VBox getMenu() {
		VBox menu = new VBox();
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");

		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

			}
		});    

		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

			}
		});
		
		MenuItem close = new MenuItem("Close");
		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

			}
		});

		menuFile.getItems().addAll(save, open, close);
		menuBar.getMenus().add(menuFile);
		menu.getChildren().add(menuBar);

		return menu;
	}

}
