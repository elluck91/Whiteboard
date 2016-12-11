package CS151;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
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

public class SaveFile {
	Whiteboard gui;
	Canvas canvas;
	private ObservableList<DShapeModel> models;

	public SaveFile(Whiteboard gui) {
		this.gui = gui;
		canvas = gui.getCanvas();
		display();
	}

	private void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(gui.getPrimaryStage());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(250);
		stage.setTitle("Open File");
		Label instruction = new Label();
		instruction.setText("Enter file name:");

		Button save = new Button("Save");
		Button cancel = new Button("Cancel");
		TextField input = new TextField();

		save.setOnAction(e -> {

			if (input.getText().length() == 0) {
				Warning.display();
			}	
			else {
				models = canvas.getShapeModels();
				File f = new File(input.getText());
				save(f);
				stage.close();
			}
		});

		cancel.setOnAction(e -> {
			stage.close();
		});

		VBox inputLayout = new VBox();
		inputLayout.getChildren().addAll(instruction, input);

		HBox buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(save, cancel);

		VBox layout = new VBox(10);

		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(inputLayout, buttons);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);

		stage.setScene(scene);
		stage.showAndWait();
	}


	public void save(File file) {
		try {
			XMLEncoder xmlOut = new XMLEncoder(
					new BufferedOutputStream(
							new FileOutputStream(file)));

			DShapeModel[] shapeModels = models.toArray(new DShapeModel[0]);
			// Dump that whole array
			xmlOut.writeObject(shapeModels);
			// And we're done!
			xmlOut.close();
			
			System.out.println("DONE");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}



}