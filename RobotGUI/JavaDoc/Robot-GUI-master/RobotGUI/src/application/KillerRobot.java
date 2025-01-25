package application;

/**
 * Represents a Killer Robot that can kill other robots by colliding with them.
 * It extends the BasicRobot class and overrides behavior related to movement and collision.
 */
public class KillerRobot extends BasicRobot {

    /**
     * Constructs a KillerRobot with initial position, radius, angle, and speed.
     * 
     * @param ix The initial x-coordinate.
     * @param iy The initial y-coordinate.
     * @param ir The radius of the robot.
     * @param angle The initial angle of the robot.
     * @param speed The speed of the robot.
     */
    KillerRobot(double ix, double iy, double ir, double angle, double speed) {
        super(ix, iy, ir, angle, speed);
        col = 'b';  // Color the KillerRobot with blue
    }

    /**
     * Determines if this robot is colliding with another robot.
     * 
     * @param x The x-coordinate of the other robot.
     * @param y The y-coordinate of the other robot.
     * @param rad The radius of the other robot.
     * @return True if the two robots are colliding, false otherwise.
     */
    @Override
    public boolean hitting(double x, double y, double rad) {
        double distance = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        return distance < (this.rad + rad);  // Return true if the distance is less than the sum of their radii
    }

    /**
     * Adjusts the KillerRobot's position and angle based on interactions with the arena and other robots.
     * If a collision with another robot is detected, the collided robot is removed from the arena.
     * 
     * @param r The RobotArena instance where the robot is located.
     */
    @Override
    protected void adjustRobot(RobotArena r) {
        // Adjust angle and position if there’s a collision with boundary or robot
        bAngle = r.CheckRobotAngle(x, y, rad, bAngle, robotID);

        // Adjust position based on the new angle after the check
        double radAngle = Math.toRadians(bAngle);
        x += bSpeed * Math.cos(radAngle);
        y += bSpeed * Math.sin(radAngle);

        // Check for collisions with other robots
        for (Robot other : r.getAllRobots()) {
            if (other.getID() != robotID) {  // Ensure we don’t check the collision with itself
                if (this.hitting(other.getX(), other.getY(), other.getRad())) {  // Check if the KillerRobot collides with another robot
                    System.out.println("KillerRobot at (" + x + "," + y + ") collides with Robot ID: " + other.getID());
                    System.out.println("KillerRobot killed Robot ID: " + other.getID());
                    r.removeRobot(other);  // Remove the other robot from the arena
                }
            }
        }
    }
}
