package CS151;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ColorPickerWindow {
	static Color finalColor = Color.GRAY;
	public static Color display() {
		
		
		Stage stage = new Stage();
        stage.setTitle("Color Picker");
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.RED);
        
 
        final Circle circle = new Circle(50);
        circle.setFill(colorPicker.getValue());
 
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Color color = colorPicker.getValue();
                circle.setFill(color);
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
        return finalColor;
	}
}
