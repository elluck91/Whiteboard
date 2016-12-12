package CS151;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.control.TextField;

public class OpenFile extends InputWindow{

	public OpenFile(Whiteboard gui, String title, String lbl, String btn, String defaultTxt) {
		super(gui, title, lbl, btn, defaultTxt);
	}

	public void open(File file) throws IOException {
		DShapeModel[] dotArray = null;
		try {
			XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(file))); 
			dotArray = (DShapeModel[]) xmlIn.readObject();
			xmlIn.close();
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

	@Override
	public void performAction(TextField input) {
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

	}



}
