package application;

/**
 * Represents an obstacle within the robot arena.
 * The obstacle is circular in shape, with a specified position and radius.
 */
public class Obstacle {
    private double x, y, radius;

    /**
     * Gets the x-coordinate of the obstacle.
     * 
     * @return The x-coordinate of the obstacle.
     */
    public double getX() { return x; }

    /**
     * Gets the y-coordinate of the obstacle.
     * 
     * @return The y-coordinate of the obstacle.
     */
    public double getY() { return y; }

    /**
     * Gets the radius of the obstacle.
     * 
     * @return The radius of the obstacle.
     */
    public double getRadius() { return radius; }

    /**
     * Constructs an obstacle at the specified position with the given radius.
     * 
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     * @param radius The radius of the obstacle.
     */
    public Obstacle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Draws the obstacle on the canvas as a circle.
     * 
     * @param mc The MyCanvas instance used for drawing the obstacle.
     */
    public void drawObstacle(MyCanvas mc) {
        // Draw the obstacle as a circle using the showCircle method
        mc.showCircle(x, y, radius, 'c');  
    }

    /**
     * Checks if this obstacle is colliding with a robot based on their positions and radii.
     * 
     * @param rx The x-coordinate of the robot.
     * @param ry The y-coordinate of the robot.
     * @param r The radius of the robot.
     * @return True if the obstacle is colliding with the robot, false otherwise.
     */
    public boolean isColliding(double rx, double ry, double r) {
        double dx = rx - x;
        double dy = ry - y;
        double distSquared = dx * dx + dy * dy;
        double sumRadii = r + this.radius;
        return distSquared < sumRadii * sumRadii;
    }

    /**
     * Returns a string representation of the obstacle's type and position.
     * 
     * @return A string describing the obstacle's position and radius.
     */
    public String getStrType() {
        return String.format("Obstacle at (%.1f, %.1f), Radius: %.1f", x, y, radius);
    }

    /**
     * Returns a brief description of the obstacle's position.
     * 
     * @return A string containing the obstacle's position in the format "(x, y)".
     */
    public String getInfo() {
        return " (" + x + ", " + y + ")";  
    }

    /**
     * Checks if a given point is inside this obstacle.
     * 
     * @param x2 The x-coordinate of the point.
     * @param y2 The y-coordinate of the point.
     * @return True if the point is inside the obstacle, false otherwise.
     */
    public boolean contains(double x2, double y2) {
        // TODO: Implement collision detection for the point inside the obstacle
        return false;
    }
}


