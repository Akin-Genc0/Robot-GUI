package application;

public class BasicRobot extends Robot {
    // Wheel-based movement
    protected double leftWheelSpeed;
    protected double rightWheelSpeed;
    protected double turningSpeed;

    // Two line sensors
    private Line leftSensorLine;
    private Line rightSensorLine;

    // Flags indicating whether each sensor is triggered
    private boolean leftSensorTriggered = false;
    private boolean rightSensorTriggered = false;

    BasicRobot(double ix, double iy, double ir, double angle, double speed) {
        super(ix, iy, ir);

        // Initial settings
        this.bAngle = 0;    // e.g., face east
        this.bSpeed = 1;
        this.leftWheelSpeed  = 1;
        this.rightWheelSpeed = 1;
        this.turningSpeed    = 0;

        // Create the sensors. For demonstration, 40 px length each
       
        double sensorLength = 40;
        
        // Left sensor angle
        double leftSensorAngle  = Math.toRadians(bAngle - 30);
        double leftX2  = x + sensorLength * Math.cos(leftSensorAngle);
        double leftY2  = y + sensorLength * Math.sin(leftSensorAngle);
        leftSensorLine  = new Line(x, y, leftX2, leftY2);

        // Right sensor angle
        double rightSensorAngle = Math.toRadians(bAngle + 30);
        double rightX2 = x + sensorLength * Math.cos(rightSensorAngle);
        double rightY2 = y + sensorLength * Math.sin(rightSensorAngle);
        rightSensorLine = new Line(x, y, rightX2, rightY2);
    }

    
    
    public boolean contains(double mouseX, double mouseY) {
        double distance = Math.sqrt(Math.pow(this.x - mouseX, 2) + Math.pow(this.y - mouseY, 2));
        return distance <= this.rad;  // If mouse is within the robot's radius
    }

    
    
    @Override
    public void drawRobot(MyCanvas mc) {
        // 1. Draw the main body
        mc.showCircle(x, y, rad, col);

        
        double wheelRadius = rad / 3;      
        double wheelOffset = rad * 1.2;  

        double leftWheelAngleRad  = Math.toRadians(bAngle + 90);
        double rightWheelAngleRad = Math.toRadians(bAngle - 90);

        double leftWheelX  = x + wheelOffset * Math.cos(leftWheelAngleRad);
        double leftWheelY  = y + wheelOffset * Math.sin(leftWheelAngleRad);
        double rightWheelX = x + wheelOffset * Math.cos(rightWheelAngleRad);
        double rightWheelY = y + wheelOffset * Math.sin(rightWheelAngleRad);

        mc.showCircle(leftWheelX,  leftWheelY,  wheelRadius, 'p');
        mc.showCircle(rightWheelX, rightWheelY, wheelRadius, 'p');

        // 3. Draw the two sensor lines
        //    If triggered, color them red; otherwise green
        char leftColor  = leftSensorTriggered  ? 'r' : 'g';
        char rightColor = rightSensorTriggered ? 'r' : 'g';

        // We'll assume you have a getCoords() in Line:
        double[] leftCoords  = leftSensorLine.getCoords();
        double[] rightCoords = rightSensorLine.getCoords();

        // Draw the lines
        mc.showLine(leftCoords[0],  leftCoords[1],  leftCoords[2],  leftCoords[3],  leftColor);
        mc.showLine(rightCoords[0], rightCoords[1], rightCoords[2], rightCoords[3], rightColor);
    }

    @Override
    public void move(double xLimit, double yLimit) {
        // turning
        turningSpeed = (rightWheelSpeed - leftWheelSpeed) * 5;
        bAngle += turningSpeed;

        //Forward speed
        bSpeed = (leftWheelSpeed + rightWheelSpeed) / 2;

        //Move in direction bAngle
        double radAngle = Math.toRadians(bAngle);
        x += bSpeed * Math.cos(radAngle);
        y += bSpeed * Math.sin(radAngle);

        // 4. Update both sensor lines
        updateSensorLines();

        // Check if either sensor sees a wall
        leftSensorTriggered  = sensorSeesWall(leftSensorLine,  xLimit, yLimit);
        rightSensorTriggered = sensorSeesWall(rightSensorLine, xLimit, yLimit);

        if (leftSensorTriggered || rightSensorTriggered) {
            // Turn the robot decisively away from the wall
            // For example, do a big random turn
            bAngle += 100 + (Math.random() * 80); // turn 100..180 degrees
        }

        // 6. Keep the robot within the arena
        clampPosition(xLimit, yLimit);
    }

    @Override
    protected void adjustRobot(RobotArena r) {
        // Update lines again so they're fresh
        updateSensorLines();

        // By default, not triggered
        leftSensorTriggered  = false;
        rightSensorTriggered = false;

        // 1. For each sensor, check if it sees another robot
        for (Robot other : r.getAllRobots()) {
            if (other.getID() != robotID) {
                double distLeft  = leftSensorLine.distanceFrom(other.getX(), other.getY());
                double distRight = rightSensorLine.distanceFrom(other.getX(), other.getY());

                // If the sensor line is near the other robot’s center, treat as triggered
                if (distLeft < other.getRad()) {
                    leftSensorTriggered = true;
                }
                if (distRight < other.getRad()) {
                    rightSensorTriggered = true;
                }
            }
        }

        // 2. For each sensor, check if it sees an obstacle
      
        for (Obstacle obs : r.getObstacles()) {
            // If your Line class has a intersectsCircle(cx, cy, r) method, call it:
            if (leftSensorLine.intersectsCircle(obs.getX(), obs.getY(), obs.getRadius())) {
                leftSensorTriggered = true;
            }
            if (rightSensorLine.intersectsCircle(obs.getX(), obs.getY(), obs.getRadius())) {
                rightSensorTriggered = true;
            }
        }

        // 3. If either sensor triggered (from robot or obstacle), turn away
        if (leftSensorTriggered || rightSensorTriggered) {
            bAngle += 100 + (Math.random() * 80); 
        }
    }


    // Helper to keep the robot in the arena
    protected void clampPosition(double xLimit, double yLimit) {
        if (x - rad < 0)       x = rad;
        if (x + rad > xLimit)  x = xLimit - rad;
        if (y - rad < 0)       y = rad;
        if (y + rad > yLimit)  y = yLimit - rad;
    }

    // Recalculates both sensor lines based on bAngle
    private void updateSensorLines() {
        double sensorLength = 40;
        // Left sensor at bAngle - 30
        double leftAngleRad = Math.toRadians(bAngle - 30);
        double lx2 = x + sensorLength * Math.cos(leftAngleRad);
        double ly2 = y + sensorLength * Math.sin(leftAngleRad);
        leftSensorLine = new Line(x, y, lx2, ly2);

        // Right sensor at bAngle + 30
        double rightAngleRad = Math.toRadians(bAngle + 30);
        double rx2 = x + sensorLength * Math.cos(rightAngleRad);
        double ry2 = y + sensorLength * Math.sin(rightAngleRad);
        rightSensorLine = new Line(x, y, rx2, ry2);
    }

    /**
     * Check if a given sensor line intersects with any arena boundary.
     */
    private boolean sensorSeesWall(Line sensor, double xLimit, double yLimit) {
        Line topWall    = new Line(0,       0,       xLimit, 0);
        Line bottomWall = new Line(0,       yLimit,  xLimit, yLimit);
        Line leftWall   = new Line(0,       0,       0,      yLimit);
        Line rightWall  = new Line(xLimit,  0,       xLimit, yLimit);

        if (sensor.findintersection(topWall))    return true;
        if (sensor.findintersection(bottomWall)) return true;
        if (sensor.findintersection(leftWall))   return true;
        if (sensor.findintersection(rightWall))  return true;

        return false;
    }
    
 

    // Optional wheel controls
    public void turnLeft()  { leftWheelSpeed  = 0.5; rightWheelSpeed = 1.5; }
    public void turnRight() { leftWheelSpeed  = 1.5; rightWheelSpeed = 0.5; }
    public void goStraight(){ leftWheelSpeed  = 1.0; rightWheelSpeed = 1.0; }

    protected String getStrType() { 
        return "BasicRobot"; 
    }
}



