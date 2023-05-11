package BE;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Region;

import java.util.function.Consumer;

public class CanvasPane extends Region {
    private Canvas canvas;
    private Consumer<Canvas> repaint;


    public CanvasPane()
    {
        this.canvas = new Canvas();
        getChildren().add(canvas);
        repaint = c ->{};
    }

    public Consumer<Canvas> getRepaint()
    {
        return repaint;
    }

    public void setRepaint(Consumer<Canvas> repaint) {
        this.repaint = repaint ;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    @Override
    protected void layoutChildren()
    {
        super.layoutChildren();
        double width = getWidth();
        canvas.setWidth(width);
        double height = getHeight();
        canvas.setHeight(height);
        repaint.accept(canvas);
    }
}
