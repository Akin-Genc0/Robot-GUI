package application;

public class KillerRobot extends BasicRobot {

    KillerRobot(double ix, double iy, double ir, double angle, double speed) {
        super(ix, iy, ir, angle, speed);
       
        col = 'b';
    }

    // Adjust the hitting logic to return true only when actually colliding
    @Override
    public boolean hitting(double x, double y, double rad) {
        double distance = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        return distance < (this.rad + rad);  
    }

    @Override
    protected void adjustRobot(RobotArena r) {
        // Adjust angle and position if there’s a collision with boundary or robot
        bAngle = r.CheckRobotAngle(x, y, rad, bAngle, robotID);

        // Adjust position based on the new angle after the check
        double radAngle = Math.toRadians(bAngle);
        x += bSpeed * Math.cos(radAngle);
        y += bSpeed * Math.sin(radAngle);

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
