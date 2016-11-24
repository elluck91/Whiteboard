package CS151;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Whiteboard extends Application {

	private WhiteboardPresenter presenter;

	@Override
	public void start(Stage stage) throws Exception {
		presenter = new WhiteboardPresenter();
		presenter.attachView(this);
		presenter.addShape(new DShape());
		
		GridPane gp = new GridPane();
		Canvas rightColumn = presenter.getRightColumn();
		VBox leftColumn = presenter.getLeftColumn();
		
		gp.addColumn(0, leftColumn);
		gp.addColumn(1, rightColumn);
		
		stage.setScene(new Scene(gp, 900, 400));
		stage.setTitle("Whiteboard - Collaborative");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
