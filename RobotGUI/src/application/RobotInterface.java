
package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author shsmchlr
 * Example with balls, paddles and targets in arena
 */
public class RobotInterface extends Application {
	
	private MyCanvas mc;
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private RobotArena arena;
	private UserControlledRobot  userControlledRobot ;
	private KillerRobot   killerRobot;
	private ArenaSizeAdjuster sizeAdjuster;


	/**
	 * function to show in a box ABout the programme
	 */
	
	 
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("About");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("Akin's JavaFX Demonstrator");			// give text
	    alert.showAndWait();										// show box and wait for user to close
	}
	
	private void showInfo() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("About");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("There are two types of robots in this simulation. The first type, "
	    		+ "Basic Robots, has simple movement behavior: they move around and change direction when they "
	    		+ "hit a wall or another robot. You can add them by clicking the \"Add Basic Robot\" button.");			// give text
	    
	    alert.showAndWait();										// show box and wait for user to close
	}

	
	
	
	
		/**
	 * set up the menu of commands for the GUI
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();						// create main menu
	
		Menu mFile = new Menu("File");							// add File main menu
		MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mFile.getItems().addAll(mExit);							// add exit to File menu
		
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");
		MenuItem minfo = new MenuItem("Instructions");// add About sub men item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// and its action to print about
            }	
		});
		
		minfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showInfo();									// and its action to print about
            }	
		});
		mHelp.getItems().addAll(mAbout, minfo);						// add About to Help main item
		 
		menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
		return menuBar;											// return the menu
	}

	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * @return
	 */
 
	private VBox setButtons() {
	    Button btnStart = new Button("Start");					// create button for starting
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	// now define event when it is pressed
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// its action is to start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// now button for stop
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });

	    
	    Button btnAddOps = new Button("Add_Obstical");					// now button for stop
	    btnAddOps.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	arena.addRandomObstacle(20); // example coordinates
	        	drawWorld();									// and its action to stop the timer
	       }
	    });

	    
	    
	    Button btnAdd = new Button("Add BasicRobot");				// now button for stop
	    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
		        public void handle(ActionEvent event) {
		           	arena.addAdRobot();								// and its action to stop the timer
		           	drawWorld();
	       }
	    });
	    
	    Button btnAddR = new Button("Add AdvRobot");				// now button for stop
	    btnAddR.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
	           	arena.addRobot();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    
	    

	    Button btnSave = new Button("Save");				// now button for stop
	    btnSave.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	   Save s = new Save(arena);
	        	    s.SaveArena();
	        	    System.out.println("Simulation state saved!");
	 	       
	       }
	    });
	    
	    
	    
	    Button btnLoad = new Button("Load");
	    btnLoad.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            // 1) Clear out the default robots and obstacles in the arena
	            arena.getAllRobots().clear();
	            arena.getObstacles().clear();

	            // 2) Load from file
	            Save s = new Save(arena);
	            s.loadArena();  

	            // 3) Print to console for verification
	            System.out.println("=== After Loading ===");
	            System.out.println("Robots:");
	            for (Robot r : arena.getAllRobots()) {
	                System.out.println(r);
	            }
	            System.out.println("Obstacles:");
	            for (Obstacle o : arena.getObstacles()) {
	                System.out.println(o.getStrType());
	            }

	            // 4) Redraw the world so you can visually see the loaded objects
	            drawWorld(); 
	            System.out.println("Arena loaded!");
	            
	        }
	    });


	    
	    Button btnUserRobot = new Button("UserControledRobot");				// now button for stop
	    btnUserRobot.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	 userControlledRobot = new UserControlledRobot(100, 100, 10, 1, 1);
	        	
	            
	            // Add the robot to the arena
	        	 arena.addRobott(userControlledRobot);
	             drawWorld();  // Redraw to show the new  
	       }
	    });
	  
	    Button btnToggleMaze = new Button("Toggle Obstacles Terrain");
	    btnToggleMaze.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            arena.toggleArena();  // Toggle between arena and maze
	            drawWorld();  // Redraw the world with the new layout
	        }
	    });
	    
	    Button btnKillerRobot = new Button("Killer Robot");
	    btnKillerRobot.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	killerRobot = new KillerRobot(100, 106, 12, 10, 1);
	        	 arena.addRobott(killerRobot);
	             drawWorld();  // Redraw to show the new 
	        }
	    });
	    
	    
	    
	    Button btnAddTeleportingRobot = new Button("Add Teleporting Robot");
	    btnAddTeleportingRobot.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            // Create a new TeleportingRobot instance and add it to the arena
	            TeleportingRobot teleportingRobot = new TeleportingRobot(100, 100, 10, 0, 1); // Adjust coordinates, radius, angle, and speed as needed
	            arena.addRobott(teleportingRobot);  // Add the teleporting robot to the arena
	            drawWorld();  // Redraw the world to show the new robot
	        }
	    });

	    
	    
	    TextField widthField = new TextField();
	    widthField.setPromptText("Enter new width");

	    TextField heightField = new TextField();
	    heightField.setPromptText("Enter new height");
	    Button btnResizeArena = new Button("Resize Arena");
	    
	    
	    btnResizeArena.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            try {
	                // Parse the new width and height from the TextFields
	                double newWidth = Double.parseDouble(widthField.getText());
	                double newHeight = Double.parseDouble(heightField.getText());

	                // Adjust the arena size using the setArenaSize method
	                arena.setArenaSize(newWidth, newHeight);

	                // Redraw the world after resizing the arena
	                drawWorld();  // Make sure to redraw the world after resizing
	                System.out.println("Arena resized to: " + newWidth + " x " + newHeight);
	            } catch (NumberFormatException e) {
	                System.out.println("Please enter valid numerical values for width and height.");
	            }
	        }
	    });	
	    
	    // Add the resize controls at the bottom of the BorderPane
	    HBox resizeControls = new HBox(5, widthField, heightField, btnResizeArena);
	  
	    HBox rowRun = new HBox(5, 
		        new Label("Run: "), 
		        btnStart, 
		        btnStop,
		        btnToggleMaze,
		        btnAddTeleportingRobot
		         
		       
		    );
	    HBox rowArena = new HBox(5, 
		        new Label("Change: "), 
		        btnResizeArena,
		        resizeControls
		      
		    );
		    // Middle row: add robots/obstacles
		    HBox rowAdd = new HBox(5, 
		        new Label("Add: "), 
		        btnAdd, 
		        btnAddR, 
		        btnAddOps,
		        btnSave, 
		        btnLoad, 
		        btnKillerRobot,
		        btnUserRobot
		       
		        
		    );
		    
		    
		    
		  

		    // Put everything in a VBox
		    VBox vbox = new VBox(10, rowArena, rowRun, rowAdd);
		    vbox.setAlignment(Pos.CENTER_LEFT);

		    return vbox;
	}
	




	/**
	 * Show the score .. by writing it at position x,y
	 * @param x
	 * @param y
	 * @param score
	 */
	public void showScore (double x, double y, int score) {
		mc.showText(x, y, Integer.toString(score));
	}
	/** 
	 * draw the world with ball in it
	 */
	public void drawWorld () {
	 	mc.clearCanvas();						// set beige colour
	 	arena.drawArena(mc);
	}
	
	/**
	 * show where ball is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		ArrayList<String> allBs = arena.describeAll();
		for (String s : allBs) {
			Label l = new Label(s); 		// turn description into a label
			rtPane.getChildren().add(l);	// add label	
		}	
	}


	
 
	@Override
	public void start(Stage primaryStage) throws Exception {
	    // TODO Auto-generated method stub
	    primaryStage.setTitle("Akin Robot GUIl");
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));
	    bp.setBottom(setButtons());
	    bp.setTop(setMenu());  // put menu at the top

	    Group root = new Group();  // create group with canvas
	    Canvas canvas = new Canvas(400, 500);
	    root.getChildren().add(canvas);
	    bp.setLeft(root);  // load canvas to left area

 
	    
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), 600, 500);

	    timer = new AnimationTimer() {  // set up timer
	        public void handle(long currentNanoTime) {  // and its action when on
	            arena.adjustAllRobots();  // move all balls
	            drawWorld();  // redraw the world
	            drawStatus();  // indicate where balls are
	        }
	    };  // set up mouse events
	    timer.start();
	    arena = new RobotArena(400, 500);  // set up arena
	    drawWorld();

	    rtPane = new VBox();  // set vBox on right to list items
	    rtPane.setAlignment(Pos.TOP_LEFT);  // set alignment
	    rtPane.setPadding(new Insets(5, 75, 75, 5));  // padding
	    bp.setRight(rtPane);  // add rtPane to borderpane right

	    // Create the Scene
	    Scene scene = new Scene(bp, 700, 600);  // set overall scene
	    bp.prefHeightProperty().bind(scene.heightProperty());
	    bp.prefWidthProperty().bind(scene.widthProperty());

	    // Handle key presses
	    scene.setOnKeyPressed(event -> {
	        if (userControlledRobot != null) {
	            // Pass the key press to the user-controlled robot for movement
	            userControlledRobot.direction(event.getText());
	        }
	    });

	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Application.launch(args);			// launch the GUI

	}

}