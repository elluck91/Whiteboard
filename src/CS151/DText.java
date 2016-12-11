package CS151;

import java.awt.Color;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class DText extends DShape implements ModelListener {

	private Text text;


	public DText() {
		model = new DTextModel();
		text = new Text();
		text.setFill(ColorPickerWindow.translateColor(Color.GRAY));
	}

	public void draw() {
		Rectangle clip  = new Rectangle(model.getX(), model.getY(),
				model.getWidth(), model.getHeight());
		text.setClip(clip);
		text.setText( ((DTextModel) model).getText());
		
		//	double fontSize = computeFontSize();
		//	System.out.println(fontSize);
		Font f = new Font( ((DTextModel) model).getFont(), (model.getHeight()+model.getHeight()*.5)/2);
		text.setFont(f);
		
		System.out.println("height: " +
				text.getBoundsInLocal().getHeight());
		text.setX(model.getX());
		text.setY(model.getY()+model.getHeight()*.77);

	}

	public double computeFontSize() {
		double size = 1.0;
		double prev = size;
		boolean control = true;
		while(control) {
			prev = size;
			size = (size*1.10)+1;
			if(size > model.getHeight()){
				control = false;
			}
		}
		return size;
	}


	public String getText() {
		return ((DTextModel) model).getText();
	}

	public String getFont() {
		return ((DTextModel) model).getFont();
	}

	public void setText(String text) {
		((DTextModel) model).setText(text);
	}

	public void setFont(String font) {
		((DTextModel) model).setFont(font);
	}

	public Text getShape() {
		return text;
	}
	
	public void setColor(Color color) {
		text.setFill(ColorPickerWindow.translateColor(color));
	}
	
	
}
