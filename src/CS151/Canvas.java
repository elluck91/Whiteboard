package CS151;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

	public class Canvas extends Pane implements Observer{

		/*
		 * The shapes list in the canvas is effectively the "document"
		 * the user is editing; whatever is in that list, it is being
		 * edited. Removing a shape from this list removes it from the
		 * document as well.
		 */
		ObservableList<DShape> shapes;

		Canvas() {
			super();
			this.setStyle("-fx-background-color: white;");
			this.setPrefSize(400, 400);
			shapes = FXCollections.observableArrayList();
		}
		
		// loops through the list of shapes and draws them
		public void paintComponent() {
			for (int i = 0; i < shapes.size(); i++)
				shapes.get(i).draw();
		}

		public ObservableList<DShape> getShapes() {
			return shapes;
		}
		
		public ObservableList<DShapeModel> getShapeModels() {
			ObservableList<DShapeModel> models = FXCollections.observableArrayList();
			
			for (DShape each : shapes)
				models.add(each.getModel());
			
			return models;
			
		}

		public void addShapes(DShape shape) {
			this.shapes.add(shape);
			paintComponent();
			this.getChildren().add(shape.getShape());			
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}
		
}
