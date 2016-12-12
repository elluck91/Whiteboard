package CS151;

import java.awt.Color;

public class DTextModel extends DShapeModel {

	private String text;
	private String font;

	public DTextModel() {
		text = "Hello";
		font = "Dialog";
	}

	public String getText() {
		return text;
	}

	public String getFont() {
		return font;
	}

	public void setText(String text) {
		this.text = text;
		notifyListeners();
	}

	public void setFont(String font) {
		this.font = font;
		notifyListeners();
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		System.out.println("In set COlor");
		this.color = color;
		notifyListeners();
	}
	
	public DTextModel getModel() {
		return this;
	}

	public void mimic(DShapeModel other) {
		System.out.println("Inside mimic in DTextModel");
		setText(((DTextModel) other).getText());
		setFont(((DTextModel) other).getFont());
		setColor(((DTextModel) other).getColor());
		setX(other.getX());
		setY(other.getY());
		setWidth(other.getWidth());
		setHeight(other.getHeight());
	}
}
