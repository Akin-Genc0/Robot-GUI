package application;

import java.awt.Color;
import java.util.ArrayList;



public class RobotArena {
	private boolean isMaze;
    double xSize, ySize;             // size of arena
    private ArrayList<Robot> allRobots;   // array list of all robots in arena
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    RobotArena() {
    	
        this(500, 400);             // default size
        
    }

    RobotArena(double xS, double yS) {
        xSize = xS;
        ySize = yS;
        allRobots = new ArrayList<>();
        // Add one BasicRobot for demonstration
        allRobots.add(new BasicRobot(xS/1, yS/1, 10, 45, 10));
        allRobots.add(new BasicRobot(xS/1.5, yS/1.5, 10, 45, 10));
        allRobots.add(new AdvancedRobot(1, 1, 10, 45, 10));
              
        this.isMaze = false;
        
    }
   
    
    public void toggleArena() {
        isMaze = !isMaze;  // Switch between maze and regular
        if (isMaze) {
            generateMaze(); // Generate maze if toggled on
        } else {
            clearArena(); // Clear arena if toggled off
        }
    }

    
    
    	private void generateMaze() {
            obstacles.clear(); // Clear existing obstacles
            // Maze generation logic (e.g., DFS or random placement of walls)
            for (int i = 0; i < 20; i++) {  // Example: Add some random obstacles
                double x = Math.random() * 400;  // Arena width
                double y = Math.random() * 500;  // Arena height
                obstacles.add(new MazeObstacle(x, y, 10));  // Example obstacle
            }
        }

    	
    	private void clearArena() {
            obstacles.clear();  // Remove all obstacles
        }
    	
	public void setArenaSize(double newWidth, double newHeight) {
        this.xSize = newWidth;
        this.ySize = newHeight;
    }

    
    public void addRandomObstacle(double obstacleRadius) {
        // So we don’t place the circle partly outside the arena,
        // we limit random X between [obstacleRadius, xSize - obstacleRadius]
        double randomX = obstacleRadius + Math.random() * (xSize - 2 * obstacleRadius);
        double randomY = obstacleRadius + Math.random() * (ySize - 2 * obstacleRadius);

        obstacles.add(new Obstacle(randomX, randomY, obstacleRadius));
    }


    public double getXSize() { return xSize; }
    public double getYSize() { return ySize; }

    public void drawArena(MyCanvas mc) {
    	
    	mc.drawBorder(xSize, ySize, Color.BLACK, 2.0);
    	
    	
    	
        for (Robot r : allRobots) {
            r.drawRobot(mc);
        }
        
        for (Obstacle obs : obstacles) {
            obs.drawObstacle(mc);
        }
        
        
    }

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



    public void addRobot() {
        // Add another BasicRobot roughly at the center
        allRobots.add(new BasicRobot(xSize/2, ySize/2, 10, 60, 5));
    }
    
    
    public void addAdRobot() {
        // Add another BasicRobot roughly at the center
        
        allRobots.add(new AdvancedRobot(xSize/0.5, ySize/0.5, 10, 60, 5));

    }
  

    public void addRobott(Robot robot) {
        allRobots.add(robot);
    }
    
    public void addObstacle(Obstacle obstacle) {
    	System.out.println(obstacle.getInfo());
    	 
        obstacles.add(obstacle);
    }
    
    
	/**
     * CheckRobotAngle:
     *  - If hitting boundary, flip angle
     *  - If hitting another robot, set angle to opposite direction of collision
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

    public boolean checkHit(Robot target) {
        boolean ans = false;
        for (Robot b : allRobots) {
            if (b instanceof BasicRobot && b.hitting(target)) ans = true;
        }
        return ans;
    }

    /**
     * Provide read-only access to the list if needed
     */
    public ArrayList<Robot> getAllRobots() {
        return allRobots;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    
    public String toString() {
	    return String.format("Arena Dimensions: %f x %f%n", xSize, ySize);
	         
	}

    public void removeRobot(Robot robot) {
        allRobots.remove(robot);
        System.out.println("Robot " + robot.getID() + " has been removed from the arena.");
    }

    
    
}
