package application;

/**
 * A subclass of BasicRobot that is controlled by user input.
 * The robot moves in a specific direction based on user commands and can detect wall collisions.
 */
public class UserControlledRobot extends BasicRobot {

    private Direction currentDirection;

    /**
     * Constructor to initialize a UserControlledRobot with the specified position, radius, angle, and speed.
     * 
     * @param ix The initial x-coordinate of the robot.
     * @param iy The initial y-coordinate of the robot.
     * @param ir The radius of the robot.
     * @param i The initial angle of the robot (in degrees).
     * @param j The initial speed of the robot.
     */
    UserControlledRobot(double ix, double iy, double ir, int i, int j) {
        super(ix, iy, ir, i, j);
        this.col = 'y';  // Set the color of the robot
    }

    /**
     * Enum representing the possible movement directions for the robot.
     */
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Updates the robot's direction based on the user's input.
     * The input is expected to be "W", "A", "S", or "D", representing the four cardinal directions.
     * 
     * @param input The input string that indicates the desired movement direction.
     */
    public void direction(String input) {
        switch(input.toUpperCase()) {
            case "W":
                currentDirection = Direction.UP;
                bAngle = 270; // Face upwards
                break;
            case "A":
                currentDirection = Direction.LEFT;
                bAngle = 180; // Face left
                break;
            case "S":
                currentDirection = Direction.DOWN;
                bAngle = 90; // Face downwards
                break;
            case "D":
                currentDirection = Direction.RIGHT;
                bAngle = 0;  // Face right
                break;    
            default:
                currentDirection = Direction.UP;
                bAngle = 270;   // Default to facing up
        }         
    }

    /**
     * Moves the robot based on its current direction.
     * The robot checks for collisions with the walls and adjusts its direction if necessary.
     * 
     * @param xLimit The maximum x-coordinate limit for the arena.
     * @param yLimit The maximum y-coordinate limit for the arena.
     */
    @Override
    public void move(double xLimit, double yLimit) {
        // Move according to currentDirection
        if (currentDirection == Direction.UP) {
            y -= bSpeed;
        } else if (currentDirection == Direction.DOWN) {
            y += bSpeed;
        } else if (currentDirection == Direction.LEFT) {
            x -= bSpeed;
        } else if (currentDirection == Direction.RIGHT) {
            x += bSpeed;
        }

        // Check for wall collisions
        if(sensorSeesObstacle(xLimit, yLimit)) {
            System.out.println("Wall collision detected, changing direction.");
            bAngle += 100 + (Math.random() * 80);  // Change direction randomly
        }

        // Make sure the robot stays within the bounds of the arena
        clampPosition(xLimit, yLimit);
    }

    /**
     * Simulates a sensor that detects if the robot is about to collide with an obstacle.
     * 
     * @param xLimit The maximum x-coordinate limit for the arena.
     * @param yLimit The maximum y-coordinate limit for the arena.
     * @return True if an obstacle is detected, false otherwise.
     */
    private boolean sensorSeesObstacle(double xLimit, double yLimit) {
        return false;  // Placeholder implementation
    }
}

