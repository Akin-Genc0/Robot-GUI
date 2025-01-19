package application;

public class TeleportingRobot extends BasicRobot {

    TeleportingRobot(double ix, double iy, double ir, double angle, double speed) {
        super(ix, iy, ir, angle, speed);
        
        col = 'o';
    }

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

    private void teleport(double xLimit, double yLimit) {
        // Teleport the robot to a new random position
        double randomX = Math.random() * xLimit;
        double randomY = Math.random() * yLimit;

        this.x = randomX;
        this.y = randomY;

 
    }

}
