package CS151;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class DText extends DShape implements ModelListener {

	private Text text;


	public DText() {
		model = new DTextModel();
		text = new Text();
	}

	public void draw() {
		Rectangle clip  = new Rectangle(model.getX(), model.getY(),
				model.getWidth(), model.getHeight());

		text.setClip(clip);
		text.setText( ((DTextModel) model).getText());
		Font f = new Font( ((DTextModel) model).getFont(), (model.getHeight()+model.getHeight()*.5)/2);
		text.setFont(f);
		text.setX(model.getX());
		text.setY(model.getY()+model.getHeight()*.77);

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
}
