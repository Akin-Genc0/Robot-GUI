package application;

import java.util.List;

/**
 * Represents an advanced robot that inherits from BasicRobot.
 * This robot has enhanced movement capabilities and displays wheels based on its movement.
 */
public class AdvancedRobot extends BasicRobot {

    // Tracks whether the robot is selected by the user
    private boolean isSelected = false;

    /**
     * Constructor for the AdvancedRobot.
     * 
     * @param ix Initial x-coordinate.
     * @param iy Initial y-coordinate.
     * @param ir Initial radius of the robot.
     * @param i Initial angle of the robot.
     * @param j Initial speed of the robot.
     */
   

    public AdvancedRobot(double x, double y, double rad, int angle, int speed) {
        super(x, y, rad, angle, speed);
        this.col = 'g'; 
        System.out.println("AdvancedRobot created at: (" + x + ", " + y + ")");
    }

    /**
     * Draws the robot on the canvas, including its main body and wheels.
     * 
     * @param mc The MyCanvas object used to draw the robot.
     */
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

    /**
     * Moves the robot based on the movement of its left and right wheels.
     * If the robot detects an obstacle, it will turn to avoid it.
     * 
     * @param xLimit The maximum x-coordinate boundary.
     * @param yLimit The maximum y-coordinate boundary.
     */
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

    /**
     * Selects the robot, allowing for movement and deletion.
     */
    public void select() {
        isSelected = true;
    }

    /**
     * Deselects the robot.
     */
    public void deselect() {
        isSelected = false;
    }

    /**
     * Checks if the robot is selected.
     * 
     * @return true if the robot is selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Moves the robot to a specified position if it is selected.
     * 
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
    public void moveTo(double newX, double newY) {
        if (isSelected) {
            this.x = newX;
            this.y = newY;
        }
    }

    /**
     * Deletes the robot from the list.
     * 
     * @param robotList The list of robots in the simulation.
     */
    public void deleteRobot(List<AdvancedRobot> robotList) {
        robotList.remove(this);
    }

    /**
     * Ensures that the robot stays within the boundaries of the arena.
     * 
     * @param xLimit The maximum x-coordinate boundary.
     * @param yLimit The maximum y-coordinate boundary.
     */
    protected void clampPosition(double xLimit, double yLimit) {
        if (x - rad < 0)       x = rad;
        if (x + rad > xLimit)  x = xLimit - rad;
        if (y - rad < 0)       y = rad;
        if (y + rad > yLimit)  y = yLimit - rad;
    }

    /**
     * Helper method to check if the robot is about to bump into something (simple bump sensors).
     * 
     * @param xLimit The maximum x-coordinate boundary.
     * @param yLimit The maximum y-coordinate boundary.
     * @return true if the robot detects a collision, false otherwise.
     */
    public boolean sensorSeesObstacle(double xLimit, double yLimit) {
        return x - rad < 0 || x + rad > xLimit || y - rad < 0 || y + rad > yLimit;
    }

    /**
     * Adjusts the robot's angle and position if there’s a collision with the boundary or another robot.
     * 
     * @param r The RobotArena object that contains all the robots and obstacles.
     */
    @Override
    protected void adjustRobot(RobotArena r) {
        bAngle = r.CheckRobotAngle(x, y, rad, bAngle, robotID);
        double radAngle = Math.toRadians(bAngle);
        x += bSpeed * Math.cos(radAngle);
        y += bSpeed * Math.sin(radAngle);
    }

    /**
     * Returns the string representation of the robot type.
     * 
     * @return A string indicating this is an "AdvancedRobot".
     */
    @Override
    protected String getStrType() {
        return "AdvancedRobot"; 
    }
    
    
}





