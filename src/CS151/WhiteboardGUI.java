package CS151;

import javafx.stage.Stage;

// Displays components and handles user interaction
public class WhiteboardGUI extends Stage {
	WhiteboardPresenter presenter;

	
	public WhiteboardGUI(WhiteboardPresenter whiteboardPresenter) {
		this.presenter = whiteboardPresenter;
		presenter.attachView(this);
		showGUI();
	}

	// displays the GUI
	private void showGUI() {
		// TODO Auto-generated method stub
		
	}

}
