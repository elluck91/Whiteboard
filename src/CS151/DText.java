package CS151;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class DText extends DShape implements ModelListener {

    private Text text;
    
    public DText() {
	model = new DTextModel();
	text = new Text();
    }
    
    public void draw() {
	Rectangle clip = new Rectangle(model.getX(), model.getY(),
				       model.getWidth(), model.getHeight());
	text.setClip(clip);
	text.setText( ((DTextModel) model).getText());
	double fontSize = computeFontSize();
	Font f = new Font( ((DTextModel) model).getFont(), fontSize);
	text.setFont(f);
	text.setX(model.getX());
	text.setY(model.getY()+model.getHeight());
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

    @Override
    public void drawKnobs()
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeKnobs()
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
