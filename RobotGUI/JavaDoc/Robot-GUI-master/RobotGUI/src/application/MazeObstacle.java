package application;

/**
 * Represents a maze obstacle within the robot arena.
 * Inherits from the Obstacle class and provides custom behavior for maze obstacles.
 */
public class MazeObstacle extends Obstacle {

    /**
     * Constructs a MazeObstacle with the specified position and radius.
     * 
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     * @param radius The radius of the obstacle.
     */
    public MazeObstacle(double x, double y, double radius) {
        super(x, y, radius);  // Inheriting from Obstacle
       
    }

    /**
     * Draws the maze obstacle on the canvas.
     * 
     * @param mc The MyCanvas instance used for drawing the obstacle.
     */
    public void draw(MyCanvas mc) {
        double rad = 0;  // No radius for the obstacle; using 0 to represent the obstacle
        mc.showCircle(getX(), getY(), rad, 'b'); 
    }
}
