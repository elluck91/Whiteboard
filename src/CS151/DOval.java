package CS151;

import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class DOval extends DShape implements ModelListener
{

    private Ellipse oval;
    private Rectangle rect;
    private final double handleWidth = 8;
    private final double handleCenter = handleWidth / 2;

    public DOval()
    {
        oval = new Ellipse();

        model = new DOvalModel();

        rect = new Rectangle();

        oval.centerXProperty().bind(rect.xProperty().add(rect.widthProperty().divide(2)));
        oval.centerYProperty().bind(rect.yProperty().add(rect.heightProperty().divide(2)));
        oval.radiusXProperty().bind(rect.widthProperty().divide(2));
        oval.radiusYProperty().bind(rect.heightProperty().divide(2));

        // top left resize handle:
        resizeHandleNW = new Rectangle(handleWidth, handleWidth, Color.BLACK);
        // bind to top left corner of Rectangle:
        resizeHandleNW.xProperty().bind(rect.xProperty().subtract(handleCenter));
        resizeHandleNW.yProperty().bind(rect.yProperty().subtract(handleCenter));

        // top right resize handle:
        resizeHandleNE = new Rectangle(handleWidth, handleWidth, Color.BLACK);
        // bind to top right corner of Rectangle:
        resizeHandleNE.xProperty().bind(rect.xProperty().add(rect.widthProperty().subtract(handleCenter)));
        resizeHandleNE.yProperty().bind(rect.yProperty().subtract(handleCenter));

        // bottom right resize handle:
        resizeHandleSE = new Rectangle(handleWidth, handleWidth, Color.BLACK);
        // bind to bottom right corner of Rectangle:
        resizeHandleSE.xProperty().bind(rect.xProperty().add(rect.widthProperty().subtract(handleCenter)));
        resizeHandleSE.yProperty().bind(rect.yProperty().add(rect.heightProperty().subtract(handleCenter)));

        // bottom left resize handle:
        resizeHandleSW = new Rectangle(handleWidth, handleWidth, Color.BLACK);
        resizeHandleSW.xProperty().bind(rect.xProperty().subtract(handleCenter));
        resizeHandleSW.yProperty().bind(rect.yProperty().add(rect.heightProperty().subtract(handleCenter)));

        // force circles to live in same parent as rectangle:
        rect.parentProperty().addListener((obs, oldParent, newParent) -> {
            for (Shape r : Arrays.asList(resizeHandleNW, resizeHandleSE, resizeHandleNE, resizeHandleSW, oval)) {
                Pane currentParent = (Pane) r.getParent();
                if (currentParent != null) {
                    currentParent.getChildren().remove(r);
                }
                ((Pane) newParent).getChildren().add(r);
            }
            moveToFront();
        });

        Wrapper<Point2D> mouseLocation = new Wrapper<>();

        setUpDragging(resizeHandleNW, mouseLocation);
        setUpDragging(resizeHandleSE, mouseLocation);
        setUpDragging(rect, mouseLocation);
        setUpDragging(resizeHandleNE, mouseLocation);
        setUpDragging(resizeHandleSW, mouseLocation);

        resizeHandleNW.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();
                double newX = rect.getX() + deltaX;
                if (newX >= handleWidth
                        && newX <= rect.getX() + rect.getWidth() - handleWidth) {
                    rect.setX(newX);
                    rect.setWidth(rect.getWidth() - deltaX);
                }
                double newY = rect.getY() + deltaY;
                if (newY >= handleWidth
                        && newY <= rect.getY() + rect.getHeight() - handleWidth) {
                    rect.setY(newY);
                    rect.setHeight(rect.getHeight() - deltaY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });

        resizeHandleNE.setOnMouseDragged(event -> {

            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();
                double newMaxX = rect.getX() + rect.getWidth() + deltaX;

                if (newMaxX >= rect.getX()
                        && newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleWidth) {
                    rect.setWidth(rect.getWidth() + deltaX);
                }

                double newY = rect.getY() + deltaY;

                if (newY >= handleWidth
                        && newY <= rect.getY() + rect.getHeight() - handleWidth) {
                    rect.setY(newY);
                    rect.setHeight(rect.getHeight() - deltaY);
                }

                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
                updateRectModel();
                moveToFront();

            }
        });

        resizeHandleSE.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();
                double newMaxX = rect.getX() + rect.getWidth() + deltaX;
                if (newMaxX >= rect.getX()
                        && newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleWidth) {
                    rect.setWidth(rect.getWidth() + deltaX);
                }
                double newMaxY = rect.getY() + rect.getHeight() + deltaY;
                if (newMaxY >= rect.getY()
                        && newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleWidth) {
                    rect.setHeight(rect.getHeight() + deltaY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
                updateRectModel();
                moveToFront();

            }
        });

        resizeHandleSW.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {

                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();
                double newX = rect.getX() + deltaX;
                if (newX >= handleWidth
                        && newX <= rect.getX() + rect.getWidth() - handleWidth) {
                    rect.setX(newX);
                    rect.setWidth(rect.getWidth() - deltaX);
                }
                double newMaxY = rect.getY() + rect.getHeight() + deltaY;
                if (newMaxY >= rect.getY()
                        && newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleWidth) {
                    rect.setHeight(rect.getHeight() + deltaY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
                updateRectModel();
                moveToFront();

            }

        });

        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                moveToFront();
                DOval.this.drawKnobs();
            }
        });

        rect.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();
                double newX = rect.getX() + deltaX;
                double newMaxX = newX + rect.getWidth();
                if (newX >= handleWidth
                        && newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleWidth) {
                    rect.setX(newX);
                }
                double newY = rect.getY() + deltaY;
                double newMaxY = newY + rect.getHeight();
                if (newY >= handleWidth
                        && newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleWidth) {
                    rect.setY(newY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
                updateRectModel();
                moveToFront();

            }

        });

    }

    private void setUpDragging(Rectangle r, Wrapper<Point2D> mouseLocation)
    {

        r.setOnDragDetected(event -> {
            r.getParent().setCursor(Cursor.CLOSED_HAND);
            mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
        });

        r.setOnMouseReleased(event -> {
            r.getParent().setCursor(Cursor.DEFAULT);
            mouseLocation.value = null;
        });
    }

    private void updateRectModel()
    {
        model.setX(rect.xProperty().intValue());
        model.setY(rect.yProperty().intValue());
        model.setHeight(rect.heightProperty().intValue());
        model.setWidth(rect.widthProperty().intValue());
        model.update(rect.getWidth(), rect.getHeight());
    }

    private void moveToFront()
    {
        oval.toFront();

        rect.toFront();
        resizeHandleNE.toFront();
        resizeHandleNW.toFront();
        resizeHandleSE.toFront();
        resizeHandleSW.toFront();
    }


    @Override
    public void drawKnobs()
    {
        resizeHandleNE.setVisible(true);
        resizeHandleNW.setVisible(true);
        resizeHandleSE.setVisible(true);
        resizeHandleSW.setVisible(true);
    }

    @Override
    public void removeKnobs()
    {
        resizeHandleNE.setVisible(false);
        resizeHandleNW.setVisible(false);
        resizeHandleSE.setVisible(false);
        resizeHandleSW.setVisible(false);    }

    static class Wrapper<T>
    {

        T value;
    }

// Draw the oval
    public void draw()
    {
        oval.setFill(model.getColor());
        // Casts are necessary because model does not know it's 
        // a DOvalModel. It only knows it is a DShapeModel
        rect.setFill(Color.TRANSPARENT);
        rect.setX((double) model.getX());
        rect.setY((double) model.getY());
        rect.setWidth((double) model.getWidth());
        rect.setHeight((double) model.getHeight());
    }

    public Shape getShape()
    {
        return rect;
    }

}
