package CS151;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OpenFile {
	Whiteboard gui;
	Canvas canvas;

	public OpenFile(Whiteboard gui) {
		this.gui = gui;
		canvas = gui.getCanvas();
		display();
	}

	private void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(gui.getPrimaryStage());
		stage.setMinWidth(250);
		stage.setTitle("Open File");
		Label instruction = new Label();
		instruction.setText("Enter file name:");

		Button open = new Button("Open");
		Button cancel = new Button("Cancel");
		TextField input = new TextField();

		open.setOnAction(e -> {

			if (input.getText().length() == 0) {
				Warning.display();
			}	
			else {
				File f = new File(input.getText());
				try {
					open(f);
					stage.close();
				} catch (IOException e1) {
					Warning.display();
				}
				
			}

		});

		cancel.setOnAction(e -> {
			stage.close();
		});

		VBox inputLayout = new VBox();
		inputLayout.getChildren().addAll(instruction, input);

		HBox buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(open, cancel);

		VBox layout = new VBox(10);

		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(inputLayout, buttons);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);

		stage.setScene(scene);
		stage.showAndWait();
	}


	public void open(File file) throws IOException {
		DShapeModel[] dotArray = null;
		try {
			// Create an XMLDecoder around the file
			XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(file))); 
			// Read in the whole array of DotModels
			dotArray = (DShapeModel[]) xmlIn.readObject();
			xmlIn.close();
			// Now we have the data, so go ahead and wipe out the old state
			// and put in the new. Goes through the same doAdd() bottleneck
			// used by the UI to add dots.
			// Note that we do this after the operations that might throw.
			clear();
			for(DShapeModel sm:dotArray) {
				doAdd(sm);
			}

		}
		catch (IOException e) {
			throw new IOException();
		}
	}


	public void doAdd(DShapeModel shapeModel) {
		canvas.addShape(shapeModel);
		canvas.removeSelection();
	}

	public void clear() {
		System.out.println("Here");
		canvas.clearCanvas();
	}



}
