package application;

import java.util.ArrayList;

public class ShooterRobot extends BasicRobot {
    private ArrayList<Projectile> projectiles;  // List of projectiles fired by the robot

    ShooterRobot(double ix, double iy, double ir, double angle, double speed) {
        super(ix, iy, ir, angle, speed);
        projectiles = new ArrayList<>();
    }

    public void fire() {
        // Create a new projectile when the robot fires, using the robot's position and angle
        Projectile p = new Projectile(x, y, bAngle, 5);  // 5 is the speed of the projectile
        projectiles.add(p);
    }

    @Override
    public void move(double xLimit, double yLimit) {
        super.move(xLimit, yLimit);  // Regular movement (inherits from BasicRobot)

        // Move all projectiles
        for (Projectile p : projectiles) {
            p.move();
        }
    }

    public void checkCollisions(RobotArena arena) {
        for (Projectile p : projectiles) {
            // Check for collisions with robots in the arena
            for (Robot target : arena.getAllRobots()) {
                if (target != this && p.checkCollision(target)) {
                    // If the projectile hits a robot, remove it
                    System.out.println("Projectile hit Robot ID: " + target.getID());
                    arena.removeRobot(target);  // Optionally remove or damage the robot
                }
            }
        }
    }

    @Override
    public void drawRobot(MyCanvas mc) {
        super.drawRobot(mc);  // Draw the robot
        // Draw all projectiles
        for (Projectile p : projectiles) {
            p.drawProjectile(mc);
        }
    }
}
