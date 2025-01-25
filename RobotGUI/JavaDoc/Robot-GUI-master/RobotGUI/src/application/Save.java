package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class responsible for saving and loading the arena, robots, and obstacles to/from a file.
 */
public class Save {
    private RobotArena arena;

    /**
     * Constructor to initialize the Save object with a RobotArena instance.
     * 
     * @param arena The RobotArena instance to be saved or loaded.
     */
    public Save(RobotArena arena) {
        this.arena = arena;
    }

    /**
     * Saves the current state of the arena, including all robots and obstacles, to a text file.
     */
    public void SaveArena() {
        TextFile tf = new TextFile("Text files", "txt");  // Create a TextFile object to handle saving as a txt file

        // Check if the file was created or chosen successfully
        if (tf.createFile()) {
            
            // Create a StringBuilder to accumulate all the data to be written
            StringBuilder allData = new StringBuilder();

            // Write the arena data to the StringBuilder
            System.out.println("Writing arena data: " + arena.toString());
            allData.append(arena.toString()).append("\n");

            // Write all robots to the StringBuilder
            System.out.println("Number of robots: " + arena.getAllRobots().size());
            for (Robot robot : arena.getAllRobots()) {
                System.out.println("Saving robot: " + robot.toString());
                allData.append(robot.toString()).append("\n");  // Append each robot's details
            }

            // Write all obstacles to the StringBuilder
            System.out.println("Number of obstacles: " + arena.getObstacles().size());
            for (Obstacle obstacle : arena.getObstacles()) {
                System.out.println("Saving obstacle: " + obstacle.getStrType());
                allData.append(obstacle.getStrType()).append("\n");  // Append each obstacle's details
            }

            // Write all accumulated data to the file
            tf.writeAllFile(allData.toString());  // Write everything in one go
            System.out.println("Arena saved successfully to " + tf.usedFileName());
        } else {
            System.out.println("Failed to create or select a file.");
        }

        // Now close the file after writing everything
        tf.closeWriteFile();  // Close the file after writing everything
    }
     
    /**
     * Loads the arena, robots, and obstacles from a saved file and updates the current arena state.
     */
    public void loadArena() {
        TextFile tf = new TextFile("Text files", "txt"); // Your file chooser/creator

        // If user selects or creates a file to load
        if (tf.createFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(tf.usedFileName()))) {

                String line;

                line = br.readLine();
                if (line != null && line.startsWith("Arena Dimensions:")) {
                    // Remove the "Arena Dimensions: " part
                    String dimensionPart = line.substring("Arena Dimensions:".length()).trim();
                    // dimensionPart should look like "500.000000 x 400.000000"
                    String[] dims = dimensionPart.split("x");
                    if (dims.length == 2) {
                        double width = Double.parseDouble(dims[0].trim());
                        double height = Double.parseDouble(dims[1].trim());

                        // Since RobotArena has no dedicated "setDimensions" method,
                        // we can directly modify xSize, ySize (they are package-private).
                        arena.xSize = width;
                        arena.ySize = height;
                    }
                }

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    
                    // --- Parse Robot ---
                    if (line.startsWith("Robot ID:")) {
                        String[] parts = line.split(",");

                        // Extract the ID, X, Y from the first part
                        String firstPart = parts[0].trim(); // "Robot ID: 0 at (105.8, 222.6)"
                        firstPart = firstPart.replace("Robot ID:", "").trim();  // "0 at (105.8, 222.6)"
                        
                        String[] idAndCoords = firstPart.split("at");
                        String idString = idAndCoords[0].trim();    // "0"
                        String coords = idAndCoords[1].trim();      // "(105.8, 222.6)"

                        int robotID = Integer.parseInt(idString);
                        
                        // coords = "(105.8, 222.6)" -> remove parentheses
                        coords = coords.replace("(", "").replace(")", "").trim();
                        String[] xyParts = coords.split(",");
                        double rx = Double.parseDouble(xyParts[0].trim());
                        double ry = Double.parseDouble(xyParts[1].trim());

                        // "Radius: 10.0"
                        double radius = Double.parseDouble(parts[1].replace("Radius:", "").trim());
                        // "Speed: 1.0"
                        double speed = Double.parseDouble(parts[2].replace("Speed:", "").trim());
                        // "Angle: 1045.8"
                        double angle = Double.parseDouble(parts[3].replace("Angle:", "").trim());

                        // We can create a BasicRobot (or whichever type you want).
                        Robot newRobot = new BasicRobot(rx, ry, radius, angle, speed);

                        // If you want to preserve the ID from file, you can manually set it,
                        // but your Robot class automatically increments ID in its constructor.
                        arena.addRobott(newRobot);
                    }
                    
                    // --- Parse Obstacle ---
                    else if (line.startsWith("Obstacle at")) {
                        // Example:
                        // "Obstacle at (105.8, 222.6), Radius: 10.0"
                        
                        // Isolate coordinates first: everything inside "(...)"
                        int openParen = line.indexOf("(");
                        int closeParen = line.indexOf(")");
                        
                        if (openParen > 0 && closeParen > openParen) {
                            String coordStr = line.substring(openParen + 1, closeParen); // "105.8, 222.6"
                            String[] xyParts = coordStr.split(",");
                            double ox = Double.parseDouble(xyParts[0].trim());
                            double oy = Double.parseDouble(xyParts[1].trim());
                            
                            // Next, find the radius substring
                            int radiusIndex = line.indexOf("Radius:");
                            if (radiusIndex > 0) {
                                String radiusStr = line.substring(radiusIndex + 7).trim(); // "10.0"
                                double oradius = Double.parseDouble(radiusStr);
                                
                                // Create the obstacle
                                Obstacle newObs = new Obstacle(ox, oy, oradius);
                                arena.addObstacle(newObs);
                            }
                        }
                    }
                }

                System.out.println("Arena loaded successfully from: " + tf.usedFileName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to load the file.");
        }
    }
}




    
    
    
    
    
 




