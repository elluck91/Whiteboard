package CS151;

import javafx.scene.control.TextField;

public class ServerWindow extends InputWindow {

	public ServerWindow(Whiteboard gui, String title, String lbl, String btn, String defaultTxt) {
		super(gui, title, lbl, btn, defaultTxt);
		
	}

	@Override
	public void performAction(TextField input) {
		gui.startServer(Integer.parseInt(input.getText()));
		stage.close();

	}

}
