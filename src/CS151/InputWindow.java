package CS151;

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

public abstract class InputWindow {
	Whiteboard gui;
	Canvas canvas;
	String input;
	Stage stage;

	public InputWindow(Whiteboard gui, String title, String lbl, String btn, String defaultTxt) {
		this.gui = gui;
		canvas = gui.getCanvas();
		input = defaultTxt;
		display(title, lbl, btn, defaultTxt);
	}

	private void display(String title, String lbl, String btn, String defaultTxt) {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(gui.getPrimaryStage());
		stage.setMinWidth(250);
		stage.setTitle(title);
		Label instruction = new Label();
		instruction.setText(lbl);

		Button main = new Button(btn);
		Button cancel = new Button("Cancel");
		TextField input = new TextField(defaultTxt);

		main.setOnAction(e -> {
			performAction(input);
		});

		cancel.setOnAction(e -> {
			stage.close();
		});

		VBox inputLayout = new VBox();
		inputLayout.getChildren().addAll(instruction, input);

		HBox buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(main, cancel);

		VBox layout = new VBox(10);

		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(inputLayout, buttons);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);

		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public abstract void performAction(TextField input2);

}
