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

public class FilePath {
	public static String display(String title, String instr, String btnTxt) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(250);
		stage.setTitle(title);
		Label instruction = new Label();
		instruction.setText(instr);

		Button save = new Button(btnTxt);
		Button cancel = new Button("Cancel");
		TextField input = new TextField();

		save.setOnAction(e -> {
			if (input.getText().length() == 0) {
				Warning.display();
			}	
			else
				stage.close();
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

		return null;


	}


}
