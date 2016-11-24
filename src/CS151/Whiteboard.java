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
		
		VBox main = new VBox();
		VBox menu = presenter.getMenu();
		GridPane gp = new GridPane();
		Canvas rightColumn = presenter.getRightColumn();
		VBox leftColumn = presenter.getLeftColumn();
		
		gp.addColumn(0, leftColumn);
		gp.addColumn(1, rightColumn);
		
		main.getChildren().addAll(menu, gp);
		
		stage.setOnCloseRequest(e -> {
			e.consume();
			if (ConfirmBox.display("ConfirmBox", "Are you sure that you want to close this window?")) 
				 stage.close();
			 else	
				 return;
		});
		
		stage.setScene(new Scene(main, 900, 420));
		stage.setTitle("Whiteboard - Collaborative");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
