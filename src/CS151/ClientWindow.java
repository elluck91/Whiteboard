package CS151;

import javafx.scene.control.TextField;

public class ClientWindow extends InputWindow {

	public ClientWindow(Whiteboard gui, String title, String lbl, String btn, String defaultTxt) {
		super(gui, title, lbl, btn, defaultTxt);
	}

	@Override
	public void performAction(TextField input2) {
		String addr = input2.getText();
		String[] ipPort = addr.split(":");
		
		gui.startClient(ipPort[0], Integer.parseInt(ipPort[1]));
		stage.close();
	}

}
