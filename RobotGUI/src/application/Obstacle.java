package application;

public class Obstacle {
    private double x, y, radius;
    
    public double getX()      { return x; }
    public double getY()      { return y; }
    public double getRadius() { return radius; }

    /**
     * Constructor for a circular obstacle at position (x, y) with given radius
     */
    public Obstacle(double x, double y, double radius) {
        this.x      = x;
        this.y      = y;
        this.radius = radius;
    }

    /**
     * Draw the obstacle on the canvas as a circle.
     */
    public void drawObstacle(MyCanvas mc) {
    	
    	
        // If MyCanvas has showCircle(x, y, rad, col), just use that:
        mc.showCircle(x, y, radius, 'c');  
        
    }

    
 
    /**
     * Circle–circle collision:
     * Robot center: (rx, ry), radius = r
     * Obstacle center: (x, y), radius = this.radius
     */
    public boolean isColliding(double rx, double ry, double r) {
    	double dx = rx - x;
    	double dy = ry - y;
    	double distSquared = dx*dx + dy*dy;
    	double sumRadii = r + this.radius;
    	return distSquared < sumRadii * sumRadii;

    }

    public String getStrType() {
        return String.format("Obstacle at (%.1f, %.1f), Radius: %.1f", x, y, radius);
    }

    public String getInfo() {
        return " (" + x + ", " + y + ")";  
    }
   


}

