package application;

public class AdvancedRobot extends BasicRobot {

    AdvancedRobot(double ix, double iy, double ir, int i, int j) {
        super(ix, iy, ir, i, j);  // Call the constructor of BasicRobot

        // Change the color of the robot to indicate it's an advanced version
        this.col = 'g';  // You can choose any color here (e.g., 'g' for green)
    }

    @Override
    public void drawRobot(MyCanvas mc) {
        // Draw the main body of the robot
        mc.showCircle(x, y, rad, col);

        // Optional wheel controls, drawing wheels in a fixed position
        double wheelRadius = rad / 4;     // Wheels are smaller than the main body
        double wheelOffset = rad + wheelRadius; // Position wheels outside the main body

        // Left and Right Wheel Angles based on robot's angle (bAngle)
        double leftWheelAngleRad  = Math.toRadians(bAngle + 90); // Left wheel rotates based on the robot's angle
        double rightWheelAngleRad = Math.toRadians(bAngle - 90); // Right wheel rotates in opposite direction

        // Draw the wheels based on the robot's angle
        double leftWheelX  = x + wheelOffset * Math.cos(leftWheelAngleRad);
        double leftWheelY  = y + wheelOffset * Math.sin(leftWheelAngleRad);
        double rightWheelX = x + wheelOffset * Math.cos(rightWheelAngleRad);
        double rightWheelY = y + wheelOffset * Math.sin(rightWheelAngleRad);

        // Show the wheels at their respective positions
        mc.showCircle(leftWheelX,  leftWheelY,  wheelRadius, 'b'); // Left wheel
        mc.showCircle(rightWheelX, rightWheelY, wheelRadius, 'b'); // Right wheel
    }

    @Override
    public void move(double xLimit, double yLimit) {
        // Move according to left and right wheel speeds
        double leftRadAngle = Math.toRadians(bAngle + 90); // Left wheel angle
        double rightRadAngle = Math.toRadians(bAngle - 90); // Right wheel angle

        // Move in the direction of each wheel’s angle
        double leftSpeedX = leftWheelSpeed * Math.cos(leftRadAngle);
        double leftSpeedY = leftWheelSpeed * Math.sin(leftRadAngle);
        double rightSpeedX = rightWheelSpeed * Math.cos(rightRadAngle);
        double rightSpeedY = rightWheelSpeed * Math.sin(rightRadAngle);

        // The robot's total speed is the average of both wheels' speeds
        x += (leftSpeedX + rightSpeedX) / 2;
        y += (leftSpeedY + rightSpeedY) / 2;

        // Check if the robot hits the boundary or an obstacle
        if (sensorSeesObstacle(xLimit, yLimit)) {
            // Change direction based on collision (turn away from obstacle)
            turningSpeed = 100 + (Math.random() * 80);  // Turn 100-180 degrees
            bAngle += turningSpeed;
        }

        // Ensure the robot stays within the arena
        clampPosition(xLimit, yLimit);
    }

    // Helper to check if the robot is about to bump into something (simple bump sensors)
    public boolean sensorSeesObstacle(double xLimit, double yLimit) {
        // Simple bump sensor: checks if the robot hits the arena boundaries
        if (x - rad < 0 || x + rad > xLimit || y - rad < 0 || y + rad > yLimit) {
            return true; // It hit a wall
        }
        return false;
    }

    @Override
    protected void adjustRobot(RobotArena r) {
        // Adjust angle and position if there’s a collision with boundary or robot
        bAngle = r.CheckRobotAngle(x, y, rad, bAngle, robotID);

        // Adjust position based on the new angle after the check
        double radAngle = Math.toRadians(bAngle);
        x += bSpeed * Math.cos(radAngle);
        y += bSpeed * Math.sin(radAngle);
    }

    // Use "AdvancedRobot" to differentiate from BasicRobot
    protected String getStrType() {
        return "AdvancedRobot"; 
    }

    // Helper to keep the robot in the arena
    protected void clampPosition(double xLimit, double yLimit) {
        if (x - rad < 0)       x = rad;
        if (x + rad > xLimit)  x = xLimit - rad;
        if (y - rad < 0)       y = rad;
        if (y + rad > yLimit)  y = yLimit - rad;
    }
}



