package application;

import javafx.scene.image.Image;

/**
 * Abstract class representing a Robot.
 */
public abstract class Robot {
    protected double x, y, rad, size; // Position and size
    protected char col;        
    protected int robotID;     // Unique identifier
    static int robotCounter = 0;
    protected double bAngle;      // Movement angle in degrees
    protected double bSpeed;      // Movement speed
    
    /**
     * Constructs a Robot with specified position and radius.
     * 
     * @param ix Initial x-coordinate of the robot.
     * @param iy Initial y-coordinate of the robot.
     * @param ir Initial radius of the robot.
     */
    Robot (double ix, double iy, double ir) {
        x = ix;
        y = iy;
        rad = ir;
        robotID = robotCounter++; // set the identifier and increment class static
        col = 'r';
    }

    /**
     * Gets the x-coordinate of the robot.
     * 
     * @return The x-coordinate of the robot.
     */
    public double getX() { 
        return x; 
    }

    /**
     * Gets the y-coordinate of the robot.
     * 
     * @return The y-coordinate of the robot.
     */
    public double getY() { 
        return y; 
    }

    /**
     * Gets the radius of the robot.
     * 
     * @return The radius of the robot.
     */
    public double getRad() { 
        return rad; 
    }

    /**
     * Sets the position of the robot.
     * 
     * @param nx The new x-coordinate.
     * @param ny The new y-coordinate.
     */
    public void setXY(double nx, double ny) {
        x = nx;
        y = ny;
    }

    /**
     * Gets the unique ID of the robot.
     * 
     * @return The unique robot ID.
     */
    public int getID() {
        return robotID; 
    }

    /**
     * Draws the robot on the given canvas.
     * 
     * @param mc The canvas where the robot will be drawn.
     */
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, col);
    }

    /**
     * Returns a string representation of the robot.
     * 
     * @return A string with robot details including ID, position, radius, speed, and angle.
     */
    @Override
    public String toString() {
        return String.format("Robot ID: %d at (%.1f, %.1f), Radius: %.1f, Speed: %.1f, Angle: %.1f",
                             robotID, x, y, rad, bSpeed, bAngle);
    }

    /**
     * Checks if the given point (mouse click) is within the robot's radius.
     * 
     * @param mouseX The x-coordinate of the mouse click.
     * @param mouseY The y-coordinate of the mouse click.
     * @return True if the point is within the robot's radius, false otherwise.
     */
    public boolean contains(double mouseX, double mouseY) {
        double distance = Math.sqrt(Math.pow(this.x - mouseX, 2) + Math.pow(this.y - mouseY, 2));
        return distance <= this.rad;  // Check if mouse click is within the robot's radius
    }

    /**
     * Abstract method to get the type of the robot.
     * 
     * @return A string representing the type of the robot.
     */
    protected abstract String getStrType();

    /**
     * Abstract method to move the robot based on limits.
     * 
     * @param xLimit The maximum x-coordinate the robot can move.
     * @param yLimit The maximum y-coordinate the robot can move.
     */
    public abstract void move(double xLimit, double yLimit);

    /**
     * Checks if the robot is colliding with another object based on its position and radius.
     * 
     * @param ox The x-coordinate of the other object.
     * @param oy The y-coordinate of the other object.
     * @param or The radius of the other object.
     * @return True if the robot is colliding with the other object, false otherwise.
     */
    public boolean hitting(double ox, double oy, double or) {
        return (ox - x) * (ox - x) + (oy - y) * (oy - y) < (or + rad) * (or + rad);
    }

    /**
     * Checks if the robot is colliding with another robot.
     * 
     * @param oRobot The other robot to check for collision.
     * @return True if the robot is colliding with the other robot, false otherwise.
     */
    public boolean hitting(Robot oRobot) {
        return hitting(oRobot.getX(), oRobot.getY(), oRobot.getRad());
    }

    /**
     * Adjusts the robot's position based on its movement and ensures it doesn't leave the arena.
     * 
     * @param arena The arena to check for collisions and update the robot's movement.
     */
    protected void adjustRobot(RobotArena arena) {
        // Convert angle to radians
        double radAngle = Math.toRadians(bAngle);

        // Update position
        x += bSpeed * Math.cos(radAngle);
        y += bSpeed * Math.sin(radAngle);

        // Ensure the robot changes direction on collisions
        bAngle = arena.CheckRobotAngle(x, y, rad, bAngle, robotID); // Use the arena instance
    }

    /**
     * Gets the current angle of the robot.
     * 
     * @return The current angle of the robot in degrees.
     */
    public double getAngle() {
        return bAngle;
    }

    /**
     * Sets the angle of the robot.
     * 
     * @param angle The angle to set for the robot.
     */
    public void setAngle(double angle) {
        bAngle = angle;
    }
}



