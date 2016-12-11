package CS151;

public class Adapters {
	public static javafx.geometry.Point2D awtToFx(java.awt.geom.Point2D point) {
		return new javafx.geometry.Point2D(point.getX(), point.getY());
	}
	
	public static java.awt.geom.Point2D.Double fxToAwt(javafx.geometry.Point2D point) {
		return new java.awt.geom.Point2D.Double(point.getX(), point.getY());
	}
}
