package CS151;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Warning {
	public static void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(250);
		stage.setTitle("Incorrect File Name");
		Label instruction = new Label();
		instruction.setText("Enter correct file name.");

		Button ok = new Button("OK");

		ok.setOnAction(e -> {
			stage.close();
		});

		VBox text = new VBox(10);
		text.getChildren().addAll(instruction, ok);

		text.setPadding(new Insets(10, 10, 10, 10));
		text.setAlignment(Pos.CENTER);

		Scene scene = new Scene(text);

		stage.setScene(scene);
		stage.showAndWait();

	}
}