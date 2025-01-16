package application;


public class UserControlledRobot extends BasicRobot {

	private Direction currentDirection;
	
	
	UserControlledRobot(double ix, double iy, double ir, int i, int j) {
		super(ix, iy, ir, i, j);
		// TODO Auto-generated constructor stub
		
		this.col = 'y';
	}
	
	
		public enum Direction {
		    UP,
		    DOWN,
		    LEFT,
		    RIGHT
		  } 
			
		
		public void direction(String input) {
		    switch(input.toUpperCase()) {
		        case "W":
		            currentDirection = Direction.UP;
		            bAngle = 270; // Face upwards
		            break;
		        case "A":
		            currentDirection = Direction.LEFT;
		            bAngle = 180; // Face left
		            break;
		        case "S":
		            currentDirection = Direction.DOWN;
		            bAngle = 90; // Face downwards
		            break;
		        case "D":
		            currentDirection = Direction.RIGHT;
		            bAngle = 0;  // Face right
		            break;    
		        default:
		            currentDirection = Direction.UP;
		            bAngle = 0;   // Default to facing up
		    }         
		}
	
		
	
		@Override
		public void move(double xLimit, double yLimit) {
		    // Move according to currentDirection
		    if (currentDirection == Direction.UP) {
		        y -= bSpeed;
		    } else if (currentDirection == Direction.DOWN) {
		        y += bSpeed;
		    } else if (currentDirection == Direction.LEFT) {
		        x -= bSpeed;
		    } else if (currentDirection == Direction.RIGHT) {
		        x += bSpeed;
		    }

		    // Check for wall collisions
		    if(sensorSeesObstacle(xLimit, yLimit)) {
		        System.out.println("Wall collision detected, changing direction.");
		        bAngle += 100 + (Math.random() * 80);  // Change direction randomly
		    }

		    // Make sure the robot stays within the bounds of the arena
		    clampPosition(xLimit, yLimit);
		}



		private boolean sensorSeesObstacle(double xLimit, double yLimit) {
			// TODO Auto-generated method stub
			return false;
		}

			
		 
	 

			
}
