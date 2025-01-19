package application;

public class ArenaSizeAdjuster {
    private RobotArena arena;  // Reference to the RobotArena instance

    // Constructor that accepts an existing RobotArena instance
    public ArenaSizeAdjuster(RobotArena arena) {
        this.arena = arena;
    }

    // Method to change the arena size
    public void setArenaSize(double newWidth, double newHeight) {
        // Adjust the size of the arena
        arena.setArenaSize(newWidth, newHeight);
        
       
    }
}
