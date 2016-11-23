package CS151;

import java.util.ArrayList;

	public class Canvas {

		/*
		 * The shapes list in the canvas is effectively the "document"
		 * the user is editing; whatever is in that list, it is being
		 * edited. Removing a shape from this list removes it from the
		 * document as well.
		 */
		private ArrayList<DShape> shapes;

		Canvas() {
			this.shapes = new ArrayList<DShape>();
		}
		
		// loops through the list of shapes and draws them
		public void paintComponent() {
			for (DShape eachShape : shapes)
				eachShape.draw();
		}
}
