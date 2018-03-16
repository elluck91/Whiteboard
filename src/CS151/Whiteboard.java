package CS151;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Whiteboard extends Application {
	// The are thread inner classes to handle
	// the networking.
	private ClientHandler clientHandler;
	private ServerAccepter serverAccepter;
	// List of object streams to which we send data
	private java.util.List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	private Canvas canvas;
	private ArrayList<Button> buttons;
	private Button rect, oval, line, text, colorPicker, toFront, toBack, remove, fontButton;
	private TextField textInput;
	private ComboBox<String> fonts;
	private TableView<DShapeModel> tv;
	private MenuItem save, open, savePng, close, startServ, stopServ, startCli, stopCli;
	private Color color;
	private Stage primaryStage;
	private String status;

	public String getStatus() {
		return status;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox main = new VBox();
		canvas = new Canvas(main, this);
		buttons = new ArrayList<Button>();
		status = "normal";
		primaryStage = stage;

		main.setPrefSize(950, 400);
		VBox menu = getMenu();
		GridPane gp = new GridPane();

		VBox leftColumn = getLeftColumn(main);
		setFontBox();

		// gather the location in the canvas where a user clicked
		// use this information to select the correct shape in the
		// view 
		canvas.setOnMouseClicked(e -> {
			System.out.println(status);
			System.out.println("Is status clinet: " + status.equals("client"));
			if (!status.equals("client"))
				handleClick(new Point2D.Double(e.getX(), e.getY()));
		});

		rect.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					DRectModel model = new DRectModel();
					System.out.println("When creating: " + model.getId());
					canvas.addShape(model);
					doSend("add", model);
					disableTextControls();
				}
			}
		});

		oval.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					DOvalModel model = new DOvalModel();
					canvas.addShape(model);
					doSend("add", model);
					disableTextControls();
				}
			}
		});

		line.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					DLineModel model = new DLineModel();
					canvas.addShape(model);
					doSend("add", model);
					disableTextControls();
				}
			}
		});

		text.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					DTextModel model = new DTextModel();
					canvas.addShape(model);
					doSend("add", model);
					enableTextControls();
					DShape text = canvas.getSelected();
					setTextInput( ((DText) text).getText());
					setFontText( ((DText) text).getFont());
					
				}
			}
		});

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!status.equals("client")) {
					ColorPickerWindow colorPick;
					if (canvas.getSelected() != null)
						colorPick = new ColorPickerWindow(getGui(), canvas.getSelected().getModel().getColor());
					else
						colorPick = new ColorPickerWindow(getGui(), Color.GRAY);
					color = colorPick.display();
					canvas.updateColor(color);
					doSend("change", canvas.getSelected().getModel());
					canvas.paintComponent();
				}

			}
		});


		fontButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					displayFonts(stage);
				}
			}
		});

		toFront.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					DShape selected = canvas.getSelected();
					if(selected != null) {
						doSend("front", selected.getModel());
						canvas.moveToFront();
					}
				}

			}
		});

		toBack.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					DShape selected = canvas.getSelected();
					if(selected != null) {
						doSend("back", selected.getModel());
						canvas.moveToBack();
					}
				}

			}
		});

		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					doSend("remove", canvas.getSelected().getModel());
					canvas.deleteSelected();
					disableTextControls();
				}
			}
		});

		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				new SaveFile(getGui(), "Save file", "Enter file file:", "Save", "");
			}
		});

		savePng.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				new savePngFile(getGui(), "Save file as PNG", "Enter file name:", "Save", "");
			}
		});

		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					new OpenFile(getGui(), "Open file", "Enter file name:", "Open", "");
				}
			}
		});

		startServ.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("server")) {
					status = "server";
					new ServerWindow(getGui(), "Launch Server", "Enter port number:", "Start", "8008");
				}
			}
		});

		stopServ.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				status = "normal";

			}
		});

		startCli.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!status.equals("client")) {
					status = "client";
					new ClientWindow(getGui(), "Start Client", "Enter IP and port number:", "Start", "127.0.0.1:8008");
					for (Button btn : buttons)
						btn.setDisable(true);
				}
			}
		});

		stopCli.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				status = "normal";
				for (Button btn : buttons)
					btn.setDisable(false);
			}
		});

		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (ConfirmBox.display("ConfirmBox", "Are you sure that you want to close this window?")) 
					stage.close();
				else	
					return;
			}
		});

		textInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (!status.equals("client")) {
					DShape selected = canvas.getSelected();
					if(selected != null) {
						if(selected instanceof DText) {
							((DText)selected).setText(textInput.getText());
							doSend("change", selected.getModel());
							selected.draw();
						}
					}
				}
			}
		});

		gp.addColumn(0, leftColumn);
		gp.addColumn(1, canvas);


		main.getChildren().addAll(menu, gp);

		stage.setOnCloseRequest(e -> {
			e.consume();
			if (ConfirmBox.display("ConfirmBox", "Are you sure?")) 
				stage.close();
			else	
				return;
		});

		stage.setScene(new Scene(main, 900, 450));
		stage.setTitle("Whiteboard - Collaborative");
		stage.show();

	}

	/**
	 * Return the current color set in the GUI
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * Create the top left components and return them to 
	 * the GUI.
	 * @return VBox
	 */
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

		textInput = new TextField("");
		fontButton = new Button("Font Type");
		fontButton.setMinWidth(100);
		fontButton.setMaxWidth(100);	
		disableTextControls();

		HBox line3 = new HBox(BOX_SIZE);
		line3.getChildren().addAll(textInput, fontButton);

		toFront = new Button("Move to Front");
		toBack = new Button("Move to Back");
		remove = new Button("Remove Shape");

		HBox line4 = new HBox(BOX_SIZE);
		line4.getChildren().addAll(toFront, toBack, remove);

		topLeft.setAlignment(Pos.TOP_CENTER);
		topLeft.setSpacing(15);
		topLeft.setPadding(new Insets(20, 10, 10, 20));

		topLeft.getChildren().addAll(line1, line2, line3, line4);
		buttons.add(rect);
		buttons.add(oval);
		buttons.add(line);
		buttons.add(text);
		buttons.add(colorPicker);
		buttons.add(fontButton);
		buttons.add(toFront);
		buttons.add(toBack);
		buttons.add(remove);
		return topLeft;	
	}


	/**
	 * Create the bottom left components and return them
	 * to the GUI.
	 * @param Vbox main
	 * @return VBox
	 */
	public VBox getBottomLeft(VBox main) {
		tv = new TableView();


		TableColumn<DShapeModel, Integer> xColumn = new TableColumn("x");
		xColumn.setMinWidth(100);
		xColumn.setCellValueFactory(
				new PropertyValueFactory<DShapeModel, Integer>("x"));

		TableColumn<DShapeModel, Integer> yColumn = new TableColumn("y");
		yColumn.setMinWidth(100);
		yColumn.setCellValueFactory(
				new PropertyValueFactory<DShapeModel, Integer>("y"));

		TableColumn<DShapeModel, Integer> widthColumn = new TableColumn("width");
		widthColumn.setMinWidth(100);
		widthColumn.setCellValueFactory(
				new PropertyValueFactory<DShapeModel, Integer>("width"));

		TableColumn<DShapeModel, Integer> heightColumn = new TableColumn("height");
		heightColumn.setMinWidth(100);
		heightColumn.setCellValueFactory(
				new PropertyValueFactory<DShapeModel, Integer>("height"));

		tv.getColumns().addAll(xColumn, yColumn, widthColumn, heightColumn);

		tv.setItems(canvas.getShapeModels());
		tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		tv.prefHeightProperty().bind(main.heightProperty());

		VBox table = new VBox();
		table.setMinWidth(400);
		//table.prefHeightProperty().bind(main.heightProperty());
		table.getChildren().add(tv);
		return table;	
	}


	/**
	 * Create the left column and return it to the GUI.
	 * @param VBox main
	 * @return VBox
	 */
	public VBox getLeftColumn(VBox main) {
		VBox left = new VBox();
		left.getChildren().addAll(getTopLeft(), getBottomLeft(main));
		return left;
	}


	/**
	 * Create the menu components and return them
	 * to the GUI.
	 * @return VBox
	 */
	public VBox getMenu() {
		VBox menu = new VBox();
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu serverMenu = new Menu("Server");

		save = new MenuItem("Save");
		open = new MenuItem("Open");
		savePng = new MenuItem("Save to PNG");
		close = new MenuItem("Close");

		startServ = new MenuItem("Start server");
		stopServ = new MenuItem("Stop server");
		startCli = new MenuItem("Start client");
		stopCli = new MenuItem("Stop client");

		menuFile.getItems().addAll(save, open, savePng, close);
		serverMenu.getItems().addAll(startServ, stopServ, startCli, stopCli);

		menuBar.getMenus().addAll(menuFile, serverMenu);
		menu.getChildren().add(menuBar);
		return menu;
	}


	/**
	 * Handle a click on the canvas and update the 
	 * GUI appropriately.
	 * @param Point2D location
	 */
	public void handleClick(Point2D location) {
		if (!status.equals("client")) {
			canvas.makeSelection(location);
			doSend("select", canvas.getSelected().getModel());
			if(canvas.getSelected() instanceof DText)
				enableTextControls();
			else
				disableTextControls();
		}
	}



	/**
	 * Modify the visibility of the text controls.     
	 * @param boolean set
	 */
	private void changeTextControls(boolean set) {
		fontButton.setDisable(set);
		textInput.setDisable(set);

	}

	/**
	 * Enable the text controls in the GUI.
	 */
	public void enableTextControls() {
		changeTextControls(false);
		DShape text = canvas.getSelected();
		setTextInput( ((DText) text).getText());
		setFontText( ((DText) text).getFont());
	}


	/**
	 * Disable the text controls in the GUI.
	 */
	public void disableTextControls() {
		changeTextControls(true);
		setFontText("Font Type");
		setTextInput("");
	}


	/**
	 * Set the text of the textfield
	 * @param String text
	 */
	public void setTextInput(String text) {
		textInput.setText(text);
	}


	/**
	 * Set the text of the font button
	 * @param String font
	 */
	public void setFontText(String font) {
		fontButton.setText(font);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public Whiteboard getGui() {
		return this;
	}


	/**
	 * Initialize the combo box used to display fonts
	 */
	public void setFontBox() {
		fonts = new ComboBox<String>();
		List<String> systemFonts = Font.getFamilies();
		ObservableList<String> fontModel = FXCollections.observableArrayList();
		for(int i = 0; i < systemFonts.size(); i++) {
			fontModel.add(systemFonts.get(i));
		}
		fonts.setItems(fontModel);
		fonts.setPrefHeight(20);
		fonts.setPrefWidth(200);
		fonts.setMaxWidth(Control.USE_PREF_SIZE);	
	}


	/**
	 * Set the font of the selected shape if it's
	 * a DText shape. 
	 * @param String font
	 */
	public void setFont(String font) {
		DShape selected = canvas.getSelected();
		if(selected != null) {
			if(selected instanceof DText) {
				( (DText) selected).setFont(font);
				doSend("change", selected.getModel());
				canvas.paintComponent();
			}

		}
	}

	public void updateTable() {
		tv.setItems(canvas.getShapeModels());
		tv.refresh();
	}


	/**
	 * Create a popup window that displays the available
	 * font types to the user
	 * @param Stage primaryStage
	 */
	public void displayFonts(Stage primaryStage) {
		DShape selected = canvas.getSelected();	
		fonts.setValue( ((DText)selected).getFont());
		Stage fontSelection = new Stage();
		fontSelection.initModality(Modality.APPLICATION_MODAL);
		fontSelection.initOwner(primaryStage);
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		Button confirm = new Button("Set Font");
		confirm.setOnMouseClicked(e -> {
			String selectedFont = fonts.getValue();
			if(selectedFont != null) {
				setFontText(selectedFont);
				setFont(selectedFont);
				fontSelection.close();
			}
		});
		box.getChildren().addAll(fonts, confirm);
		Scene selection = new Scene(box, 300, 100);
		fontSelection.setScene(selection);
		fontSelection.setTitle("Fonts");
		fontSelection.show();
	}


	public static void main(String[] args) {
		launch(args);
	}


	/*
	 * ***********************************************************************************************************	
	 */
	private class ClientHandler extends Thread {
		private String name;
		private int port;

		ClientHandler(String name, int port) {
			this.name = name;
			this.port = port;
		}

		// Connect to the server, loop getting messages
		public void run() {
			try {
				System.out.println("My status is: " + status);
				// make connection to the server name/port
				Socket toServer = new Socket(name, port);
				// get input stream to read from server and wrap in object input stream
				ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
				System.out.println("client: connected!");
				// we could do this if we wanted to write to server in addition
				// to reading
				// out = new ObjectOutputStream(toServer.getOutputStream());
				while (true) {
					// Get the xml string, decode to a Message object.
					// Blocks in readObject(), waiting for server to send something.
					String xmlString = (String) in.readObject();
					XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
					String instruction = (String) decoder.readObject();
					DShapeModel message = (DShapeModel) decoder.readObject();
					System.out.println("WHen cline receives: " + message.getId());

					invokeToGUI(instruction, message);
				}
			}
			catch (Exception ex) { // IOException and ClassNotFoundException
				ex.printStackTrace();
			}
		}

		private void invokeToGUI(String message, DShapeModel model) {
		    System.out.println("Inside the invokeToGUI " + model.getId());
		    DShapeModel current = canvas.findById(model);
		    if (message.equals("add")) {
			Platform.runLater( new Runnable() {
				public void run() {
				    System.out.println("add");
				    canvas.addShape(model);
				}
			    });
		    }
		    else if (message.equals("remove")) {
			Platform.runLater( new Runnable() {
				public void run() {
				    System.out.println("remove");
				    canvas.removeShape(current);
				}
			    });
		    }
		    else if (message.equals("front")) {
			Platform.runLater( new Runnable() {
				public void run() {
				    canvas.moveToFront();
				}
			    });
		    }
		    else if (message.equals("back")) {
			Platform.runLater( new Runnable() {
				public void run() {
				    canvas.moveToBack();
				}
			    });
		    }
		    else if (message.equals("change")) {
			Platform.runLater( new Runnable() {
				public void run() {
					canvas.removeShape(model);
					current.mimic(model.getModel());
					canvas.addShape(current);
				}
			    });
		    }
		    else if (message.equals("select")) {
			Platform.runLater( new Runnable() {
				public void run() {
				    DShape shape = canvas.findShapeByID(current);
				    canvas.setSelected(shape);
				}
			    });
		    }
		}
	}

	// Runs a client handler to connect to a server.
	// Wired to Client button.
	public void startClient(String ip, int port) {
		clientHandler = new ClientHandler(ip, port);
		clientHandler.start();
	}

	/**************************************************SERVER PART******START***********************************/

	// (this and sendToOutputs() are synchronzied to avoid conflicts)
	public synchronized void addOutput(ObjectOutputStream out) {
		outputs.add(out);
	}

	public void doSend(String instruction, DShapeModel model) {
		System.out.println("ID in doSend: " + model.getId());
		sendRemote(instruction, model);
	}

	// Sends a message to all of the outgoing streams.
	// Writing rarely blocks, so doing this on the swing thread is ok,
	// although could fork off a worker to do it.
	public synchronized void sendRemote(String instruction, DShapeModel model) {
		// Convert the message object into an xml string.
		OutputStream memStream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(memStream);
		encoder.writeObject(instruction);
		System.out.println("Before writing out: " + model.getId());
		encoder.writeObject(model);
		encoder.close();
		String xmlString = memStream.toString();
		// Now write that xml string to all the clients.
		Iterator<ObjectOutputStream> it = outputs.iterator();
		System.out.println("My status is: " + status);
		while (it.hasNext()) {
			ObjectOutputStream out = it.next();
			try {
				out.writeObject(xmlString);
				out.flush();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				it.remove();
			}
		}
	}


	public void startServer(int port) {
		serverAccepter = new ServerAccepter(port);
		serverAccepter.start();
	}

	// Server thread accepts incoming client connections
	class ServerAccepter extends Thread {
		private int port;
		ServerAccepter(int port) {
			this.port = port;
		}
		public void run() {
			try {
				System.out.println("Server Started");
				ServerSocket serverSocket = new ServerSocket(port);
				while (true) {
					Socket toClient = null;
					// this blocks, waiting for a Socket to the client
					toClient = serverSocket.accept();
					System.out.println("server: got client");
					// Get an output stream to the client, and add it to
					// the list of outputs
					// (our server only uses the output stream of the connection)
					addOutput(new ObjectOutputStream(toClient.getOutputStream()));
					for(int i = 0; i < canvas.getShapeModels().size(); i++) {
					    doSend("add", canvas.getShapeModels().get(i));
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace(); 
			}
		}
	}
	/**************************************************SERVER PART******END***********************************/
}


