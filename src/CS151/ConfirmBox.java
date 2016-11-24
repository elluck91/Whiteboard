package CS151;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	static boolean answer;
	
	public static boolean display(String title, String message) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(250);
		stage.setTitle(title);
		
		Label label = new Label();
		label.setText(message);
		
		Button yesButton = new Button("Yes");
		yesButton.setOnAction(e -> {
			answer = true;
			stage.close();
		});
		
		Button noButton = new Button("No");
		noButton.setOnAction(e -> {
			answer = false;
			stage.close();
		});
		
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		
		stage.setScene(scene);
		stage.showAndWait();
		
		return answer;
	}
}
