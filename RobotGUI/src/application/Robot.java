package application;

import javafx.scene.image.Image;

public abstract class Robot {
    protected double x, y, rad, size; // Position and size
    protected char col;        
    protected int robotID;     // Unique identifier
    static int robotCounter = 0;
    protected double bAngle;      // Movement angle in degrees
    protected double bSpeed;      // Movement speed
    
	Robot (double ix, double iy, double ir) {
		x = ix;
		y = iy;
		rad = ir;
		robotID = robotCounter++;			// set the identifier and increment class static
		col = 'r';
	}
 
	
 
	public double getX() { return x; }
	/**
	 * return y position
	 * @return
	 */
	public double getY() { return y; }
	/**
	 * return radius of ball
	 * @return
	 */
	public double getRad() { return rad; }
	/** 
	 * set the ball at position nx,ny
	 * @param nx
	 * @param ny
	 */
	public void setXY(double nx, double ny) {
		x = nx;
		y = ny;
	}
	
	
	public int getID() {return robotID; }
	/**
	 * draw a ball into the interface bi
	 * @param bi
	 */
	public void drawRobot(MyCanvas mc) {
		mc.showCircle(x, y, rad, col);
	
	}
	
	
	
	@Override
	public String toString() {
	    return String.format("Robot ID: %d at (%.1f, %.1f), Radius: %.1f, Speed: %.1f, Angle: %.1f",
	                         robotID, x, y, rad, bSpeed, bAngle);
	}

	



	protected abstract String getStrType();



	public abstract void move(double xLimit, double yLimit);
	
	
	public boolean hitting(double ox, double oy, double or) {
		return (ox-x)*(ox-x) + (oy-y)*(oy-y) < (or+rad)*(or+rad);
	}		// hitting if dist between ball and ox,oy < ist rad + or
	
	 
	public boolean hitting (Robot oRobot) {
		return hitting(oRobot.getX(), oRobot.getY(), oRobot.getRad());
	}



	protected void adjustRobot(RobotArena arena) {
	    // Convert angle to radians
	    double radAngle = Math.toRadians(bAngle);

	    // Update position
	    x += bSpeed * Math.cos(radAngle);
	    y += bSpeed * Math.sin(radAngle);

	    // Ensure the robot changes direction on collisions
	    bAngle = arena.CheckRobotAngle(x, y, rad, bAngle, robotID); // Use the arena instance
	}



	// in Robot.java or BasicRobot.java
	public double getAngle() {
	    return bAngle;
	}
	public void setAngle(double angle) {
	    bAngle = angle;
	}


}


