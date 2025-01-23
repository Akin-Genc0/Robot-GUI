package application;

/**
 * A subclass of BasicRobot that introduces teleportation behavior.
 * The robot moves in the direction of its current angle and has a chance to teleport to a random position.
 */
public class TeleportingRobot extends BasicRobot {

    /**
     * Constructor to initialise a TeleportingRobot with the specified position, radius, angle, and speed.
     * 
     * @param ix The initial x-coordinate of the robot.
     * @param iy The initial y-coordinate of the robot.
     * @param ir The radius of the robot.
     * @param angle The initial angle of the robot.
     * @param speed The speed of the robot's movement.
     */
    TeleportingRobot(double ix, double iy, double ir, double angle, double speed) {
        super(ix, iy, ir, angle, speed);
        
        col = 'o';  // Set the color of the robot
    }

    /**
     * Moves the robot a small distance in its current direction and has a chance to teleport.
     * 
     * @param xLimit The maximum x-coordinate limit for the arena.
     * @param yLimit The maximum y-coordinate limit for the arena.
     */
    @Override
    public void move(double xLimit, double yLimit) {
        // Move the robot a small distance in its current direction
        double radAngle = Math.toRadians(bAngle);
        this.x += bSpeed * Math.cos(radAngle);  // Move in the direction of the current angle
        this.y += bSpeed * Math.sin(radAngle);

        // Check if the robot should teleport
        if (Math.random() < 0.1) {  // 10% chance to teleport every time move is called
            teleport(xLimit, yLimit);
        }

        // Ensure the robot stays within the arena bounds
        clampPosition(xLimit, yLimit);
    }

    /**
     * Teleports the robot to a new random position within the arena limits.
     * 
     * @param xLimit The maximum x-coordinate limit for the arena.
     * @param yLimit The maximum y-coordinate limit for the arena.
     */
    private void teleport(double xLimit, double yLimit) {
        // Teleport the robot to a new random position
        double randomX = Math.random() * xLimit;
        double randomY = Math.random() * yLimit;

        this.x = randomX;
        this.y = randomY;
    }
}
