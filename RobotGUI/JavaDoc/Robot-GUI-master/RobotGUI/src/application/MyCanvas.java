package application;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.ArcType;

public class MyCanvas {
    private int xCanvasSize = 600;
    private int yCanvasSize = 500;
    private GraphicsContext gc;

    public MyCanvas(GraphicsContext gc, int xcs, int ycs) {
        this.gc = gc;
        this.xCanvasSize = xcs;
        this.yCanvasSize = ycs;
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, xCanvasSize, yCanvasSize); // Clear the canvas
    }

    public void showCircle(double x, double y, double rad, char col) {
        gc.setFill(colFromChar(col));
        gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND);
    }

    public void showText(double x, double y, String s) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Color.WHITE);
        gc.fillText(s, x, y);
    }

    public void showLine(double x1, double y1, double x2, double y2, char col) {
        gc.setStroke(colFromChar(col));
        gc.setLineWidth(2);
        gc.strokeLine(x1, y1, x2, y2);
    }

    private Color colFromChar(char c) {
        switch (c) {
            case 'r':
                return Color.RED;
            case 'g':
                return Color.GREEN;
            case 'b':
                return Color.BLUE;
            case 'y':
                return Color.YELLOW;
            case 'w':
                return Color.WHITE;
            case 'o':
                return Color.PURPLE;
            default:
                return Color.BLACK;
        }
    }

    /**
     * Draws a border around the arena with the specified color and thickness.
     * @param width The width of the arena.
     * @param height The height of the arena.
     * @param black The color of the border.
     * @param thickness The thickness of the border lines.
     */
    public void drawBorder(double width, double height, java.awt.Color black, double thickness) {
        
        gc.setLineWidth(thickness);
        gc.strokeRect(0, 0, width, height);
    }


}

