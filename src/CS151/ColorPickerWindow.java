package CS151;

import java.awt.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColorPickerWindow {

	static Color finalColor = Color.GRAY;
	static java.awt.Color startColor;
	static Whiteboard gui;

	public ColorPickerWindow(Whiteboard gui, java.awt.Color gray) {
		startColor = gray;
		this.gui = gui;
	}

	public static java.awt.Color display() {		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(gui.getPrimaryStage());
		stage.setTitle("Color Picker");
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setValue(Adapters.awtToFx(startColor));         
		final Circle circle = new Circle(50);
		circle.setFill(colorPicker.getValue());

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Color color = Adapters.fxToAwt(colorPicker.getValue());
				circle.setFill(colorPicker.getValue());
				finalColor = color;
			}
		});

		Button saveButton = new Button("Save Color");
		saveButton.setOnAction(e -> {
			stage.close();
		});

		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10, 10, 10, 10));

		root.getChildren().addAll(circle, colorPicker, saveButton);

		Scene scene = new Scene(root, 200, 200); 
		stage.setScene(scene);
		stage.showAndWait();
		System.out.println(finalColor.toString());
		return finalColor;
	}
}
