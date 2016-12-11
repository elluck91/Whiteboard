package CS151;

import java.awt.Color;

import javafx.scene.paint.Paint;

public class Adapters {
	public static javafx.geometry.Point2D awtToFx(java.awt.geom.Point2D point) {
		return new javafx.geometry.Point2D(point.getX(), point.getY());
	}
	
	public static java.awt.geom.Point2D.Double fxToAwt(javafx.geometry.Point2D point) {
		return new java.awt.geom.Point2D.Double(point.getX(), point.getY());
	}
	
	public static javafx.scene.paint.Color awtToFx(java.awt.Color startColor2) {
		javafx.scene.paint.Color fxColor 
		= javafx.scene.paint.Color.rgb(startColor2.getRed(), startColor2.getGreen(), startColor2.getBlue(), startColor2.getAlpha()/255.0);
		return fxColor;
	}

	public static java.awt.Color fxToAwt(javafx.scene.paint.Color fxColor) {
		return new java.awt.Color((float) fxColor.getRed(),
				(float) fxColor.getGreen(),
				(float) fxColor.getBlue(),
				(float) fxColor.getOpacity());
	}
	
	public Paint fwToAwt(Color awtColor) {
		javafx.scene.paint.Color fxColor 
		= javafx.scene.paint.Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha()/255.0);
		return fxColor;
	}
}
