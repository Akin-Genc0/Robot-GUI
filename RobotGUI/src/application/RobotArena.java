package application;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Class representing the arena where robots are placed and interact.
 */
public class RobotArena {
    private boolean isMaze;
    double xSize, ySize;             // size of arena
    private ArrayList<Robot> allRobots;   // array list of all robots in arena
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    /**
     * Constructs a default RobotArena with size 500 x 400.
     */
    RobotArena() {
        this(500, 400); // default size
    }

    /**
     * Constructs a RobotArena with the given dimensions.
     * 
     * @param xS The width of the arena.
     * @param yS The height of the arena.
     */
    RobotArena(double xS, double yS) {
        xSize = xS;
        ySize = yS;
        allRobots = new ArrayList<>();
        // Add some example robots for demonstration
        allRobots.add(new BasicRobot(xS / 1, yS / 1, 10, 45, 10));
        allRobots.add(new BasicRobot(xS / 1.5, yS / 1.5, 10, 45, 10));
        allRobots.add(new AdvancedRobot(1, 1, 10, 45, 10));
        this.isMaze = false;
    }

    /**
     * Toggles between maze and regular arena layout.
     * Generates a maze if toggled on, otherwise clears existing obstacles.
     */
    public void toggleArena() {
        isMaze = !isMaze;  // Switch between maze and regular
        if (isMaze) {
            generateMaze(); // Generate maze if toggled on
        } else {
            clearArena(); // Clear arena if toggled off
        }
    }

    /**
     * Generates a random maze by adding obstacles in the arena.
     */
    private void generateMaze() {
        obstacles.clear(); // Clear existing obstacles
        // Maze generation logic (e.g., DFS or random placement of walls)
        for (int i = 0; i < 20; i++) {  // Example: Add some random obstacles
            double x = Math.random() * 400;  // Arena width
            double y = Math.random() * 500;  // Arena height
            obstacles.add(new MazeObstacle(x, y, 10));  // Example obstacle
        }
    }

    /**
     * Clears all obstacles from the arena.
     */
    private void clearArena() {
        obstacles.clear();  // Remove all obstacles
    }

    /**
     * Sets the size of the arena.
     * 
     * @param newWidth The new width of the arena.
     * @param newHeight The new height of the arena.
     */
    public void setArenaSize(double newWidth, double newHeight) {
        this.xSize = newWidth;
        this.ySize = newHeight;
    }

    /**
     * Adds a random obstacle with the specified radius to the arena.
     * 
     * @param obstacleRadius The radius of the new obstacle.
     */
    public void addRandomObstacle(double obstacleRadius) {
        // So we don’t place the circle partly outside the arena,
        // we limit random X between [obstacleRadius, xSize - obstacleRadius]
        double randomX = obstacleRadius + Math.random() * (xSize - 2 * obstacleRadius);
        double randomY = obstacleRadius + Math.random() * (ySize - 2 * obstacleRadius);
        obstacles.add(new Obstacle(randomX, randomY, obstacleRadius));
    }

    /**
     * Gets the width of the arena.
     * 
     * @return The width of the arena.
     */
    public double getXSize() {
        return xSize;
    }

    /**
     * Gets the height of the arena.
     * 
     * @return The height of the arena.
     */
    public double getYSize() {
        return ySize;
    }

    /**
     * Draws the arena, including robots and obstacles.
     * 
     * @param mc The canvas to draw on.
     */
    public void drawArena(MyCanvas mc) {
        mc.drawBorder(xSize, ySize, Color.BLACK, 2.0);

        for (Robot r : allRobots) {
            r.drawRobot(mc);
        }

        for (Obstacle obs : obstacles) {
            obs.drawObstacle(mc);
        }
    }

    /**
     * Provides descriptions of all robots in the arena.
     * 
     * @return A list of strings describing all robots.
     */
    public ArrayList<String> describeAll() {
        ArrayList<String> descriptions = new ArrayList<>();
        for (Robot r : allRobots) {
            descriptions.add(
                "Robot ID: " + r.getID() + 
                " Position: (" + r.getX() + ", " + r.getY() + ")"
            );
        }
        return descriptions;
    }

    /**
     * Adjusts all robots by moving them, handling collisions with boundaries,
     * other robots, and obstacles.
     */
    public void adjustAllRobots() {
        for (Robot r : allRobots) {
            // 1. Move the robot
            r.move(xSize, ySize);

            // 2. Handle boundary + robot-robot collisions
            r.adjustRobot(this);

            // 3. NOW check obstacle collisions
            for (Obstacle obs : obstacles) {
                if (obs.isColliding(r.getX(), r.getY(), r.getRad())) {
                    // EXAMPLE REACTION: turn the robot away 120 degrees
                    // Or pick any logic you want.
                    double newAngle = r.getAngle() + 120;   
                    r.setAngle(newAngle);
                }
            }
        }
    }

    /**
     * Adds a new basic robot to the arena at the center.
     */
    public void addRobot() {
        // Add another BasicRobot roughly at the center
        allRobots.add(new BasicRobot(xSize / 2, ySize / 2, 10, 60, 5));
    }

    /**
     * Adds a new advanced robot to the arena at a specific position.
     */
    public void addAdRobot() {
        // Add another AdvancedRobot roughly at the center
        allRobots.add(new AdvancedRobot(xSize / 0.5, ySize / 0.5, 10, 60, 5));
    }

    /**
     * Adds a custom robot to the arena.
     * 
     * @param robot The robot to add.
     */
    public void addRobott(Robot robot) {
        allRobots.add(robot);
    }

    /**
     * Adds a custom obstacle to the arena.
     * 
     * @param obstacle The obstacle to add.
     */
    public void addObstacle(Obstacle obstacle) {
        System.out.println(obstacle.getInfo());
        obstacles.add(obstacle);
    }

    /**
     * Checks if a robot's angle should be adjusted based on collisions with the arena's boundaries or other robots.
     * 
     * @param x The x-coordinate of the robot.
     * @param y The y-coordinate of the robot.
     * @param rad The radius of the robot.
     * @param ang The current angle of the robot.
     * @param robotID The ID of the robot.
     * @return The adjusted angle.
     */
    public double CheckRobotAngle(double x, double y, double rad, double ang, int robotID) {
        double newAngle = ang;

        // Check collision with arena boundaries
        if (x - rad < 0 || x + rad > xSize) {
            newAngle = 180 - newAngle;  // bounce horizontally
        }
        if (y - rad < 0 || y + rad > ySize) {
            newAngle = -newAngle;      // bounce vertically
        }

        // Check collision with other robots
        for (Robot r : allRobots) {
            if (r.getID() != robotID && r.hitting(x, y, rad)) {
                // Bounce angle points away from other robot’s center
                newAngle = Math.toDegrees(
                              Math.atan2(y - r.getY(), x - r.getX())
                          ) + 180;
            }
        }

        return newAngle;
    }

    /**
     * Checks if a specific target robot is being hit by any basic robot.
     * 
     * @param target The robot to check for a hit.
     * @return True if a basic robot hits the target, false otherwise.
     */
    public boolean checkHit(Robot target) {
        boolean ans = false;
        for (Robot b : allRobots) {
            if (b instanceof BasicRobot && b.hitting(target)) ans = true;
        }
        return ans;
    }

    /**
     * Provides read-only access to the list of all robots in the arena.
     * 
     * @return The list of all robots.
     */
    public ArrayList<Robot> getAllRobots() {
        return allRobots;
    }

    /**
     * Provides read-only access to the list of all obstacles in the arena.
     * 
     * @return The list of all obstacles.
     */
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Returns a string representation of the arena's dimensions.
     * 
     * @return A string with the arena dimensions.
     */
    public String toString() {
        return String.format("Arena Dimensions: %f x %f%n", xSize, ySize);
    }

    /**
     * Removes a robot from the arena.
     * 
     * @param robot The robot to remove.
     */
    public void removeRobot(Robot robot) {
        allRobots.remove(robot);
        System.out.println("Robot " + robot.getID() + " has been removed from the arena.");
    }

    /**
     * Removes a selected obstacle from the arena.
     * 
     * @param selectedObstacle The obstacle to remove.
     */
    public void removeObstacle(Obstacle selectedObstacle) {
        // TODO Auto-generated method stub
    }
}

