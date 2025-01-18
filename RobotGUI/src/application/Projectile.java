package application;


public class Projectile {
    private double x, y, speed, angle;  // Position, speed, and angle of the projectile
    private final double radius = 5;  // Size of the projectile

    public Projectile(double x, double y, double angle, double speed) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = speed;
    }

    public void move() {
        // Move the projectile in the direction of the angle
        x += speed * Math.cos(Math.toRadians(angle));
        y += speed * Math.sin(Math.toRadians(angle));
    }

    public boolean checkCollision(Robot target) {
        // Simple collision detection (could be extended with radius and hitbox)
        return (Math.abs(target.getX() - x) < radius + target.getRad()) && 
               (Math.abs(target.getY() - y) < radius + target.getRad());
    }

    public void drawProjectile(MyCanvas mc) {
        mc.showCircle(x, y, radius, 'r');  // Display the projectile (red for example)
    }

    // Getters for projectile position
    public double getX() { return x; }
    public double getY() { return y; }
}
