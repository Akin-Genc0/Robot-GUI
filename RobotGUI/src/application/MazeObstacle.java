package application;

public class MazeObstacle extends Obstacle {
    
    public MazeObstacle(double x, double y, double radius) {
        super(x, y, radius);  // Inheriting from Obstacle
        // You could customize the appearance or behavior of the maze obstacle
    }
    
    public void draw(MyCanvas mc) {
        double rad = 0;
		mc.showCircle(getX(), getY(), rad, 'b'); // Example: Draw the maze obstacle in black
    }
}
