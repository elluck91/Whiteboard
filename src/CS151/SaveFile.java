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

public class SaveFile extends InputWindow {

	public SaveFile(Whiteboard gui, String title, String lbl, String btn, String defaultTxt) {
		super(gui, title, lbl, btn, defaultTxt);
	}

	private ObservableList<DShapeModel> models;

	public void save(File file) {
		try {
			XMLEncoder xmlOut = new XMLEncoder(
					new BufferedOutputStream(
							new FileOutputStream(file)));
			DShapeModel[] shapeModels = models.toArray(new DShapeModel[0]);
			xmlOut.writeObject(shapeModels);
			xmlOut.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void performAction(TextField input) {
		if (input.getText().length() == 0) {
			Warning.display();
		}	
		else {
			models = canvas.getShapeModels();
			File f = new File(input.getText());
			save(f);
			stage.close();
		}
	}



}