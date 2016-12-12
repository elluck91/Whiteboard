package CS151;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class DOval extends DShape implements ModelListener {


    private Ellipse oval;


    public DOval() {
        oval = new Ellipse();
        model = new DOvalModel();
    }

    // Draw the oval
    public void draw() {
        oval.setFill(Adapters.awtToFx(model.getColor()));
        // Casts are necessary because model does not know it's 
        // a DOvalModel. It only knows it is a DShapeModel
	double x = ( (DOvalModel) model).getXCenter();
	double y = ( (DOvalModel) model).getYCenter();
	double xRadius = ( (DOvalModel) model).getXRadius();
	double yRadius = ( (DOvalModel) model).getYRadius();
	oval.setCenterX(x);
	oval.setCenterY(y);
	oval.setRadiusX(xRadius);
	oval.setRadiusY(yRadius);				   
    }

    public Shape getShape() {
        return oval;
    }
    
}
