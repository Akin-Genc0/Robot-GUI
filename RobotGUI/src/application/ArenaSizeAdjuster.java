package application;

/**
 * A class responsible for adjusting the size of the robot arena.
 * It provides functionality to change the dimensions of the arena.
 */
public class ArenaSizeAdjuster {
    private RobotArena arena;  // Reference to the RobotArena instance

    /**
     * Constructor that accepts an existing RobotArena instance.
     * 
     * @param arena The RobotArena instance whose size will be adjusted.
     */
    public ArenaSizeAdjuster(RobotArena arena) {
        this.arena = arena;
    }

    /**
     * Sets the new dimensions of the arena.
     * This method calls the setArenaSize method from the RobotArena class 
     * to adjust the width and height of the arena.
     * 
     * @param newWidth  The new width of the arena.
     * @param newHeight The new height of the arena.
     */
    public void setArenaSize(double newWidth, double newHeight) {
        // Adjust the size of the arena
        arena.setArenaSize(newWidth, newHeight);
    }
}
