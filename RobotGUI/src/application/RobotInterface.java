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
 * The main GUI application for simulating robots in an arena.
 * It provides various controls and displays the arena with robots and obstacles.
 */
public class RobotInterface extends Application {
	
	private MyCanvas mc;
	private AnimationTimer timer; // Timer used for animation
	private VBox rtPane; // Vertical box for putting info
	private RobotArena arena;
	private UserControlledRobot userControlledRobot;
	private KillerRobot killerRobot;
	private ArenaSizeAdjuster sizeAdjuster;
	private Robot selectedRobot = null;

	/**
	 * Displays information about the program in an About dialog.
	 */
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("About");
	    alert.setHeaderText("How to Use the Robot Simulation");

	    // Shortened instructions text
	    alert.setContentText(
	        "This simulation allows you to interact with robots in an arena. Here's how to use it:\n\n"
	        + "1. **Start/Stop Simulation**: Click 'Start' to begin, 'Pause' to stop.\n"
	        + "2. **Add Robots**: Use the buttons to add different types of robots: Basic, Advanced, User-Controlled, Killer, or Teleporting.\n"
	        + "3. **Interact with the Arena**: Add obstacles, toggle terrain, or resize the arena.\n"
	        + "4. **Save/Load**: Save the current state of the simulation and load it later.\n\n"
	        + "Experiment with different settings and watch how robots react!"
	    );
	    
	    // Increase window size
	    alert.getDialogPane().setPrefSize(600, 400);
	    
	    // Show alert
	    alert.showAndWait();
	
	}
	
	 
	

	private void handleMouseClick(MouseEvent event) {
	    double mouseX = event.getX();
	    double mouseY = event.getY();

	    System.out.println("Mouse clicked at: (" + mouseX + ", " + mouseY + ")");

	    boolean robotFound = false;

	    for (Robot robot : arena.getAllRobots()) { 
	        double robotX = robot.getX();
	        double robotY = robot.getY();
	        double robotRadius = robot.getRad();

	        double distance = Math.sqrt(Math.pow(robotX - mouseX, 2) + Math.pow(robotY - mouseY, 2));

	        if (distance <= robotRadius) {
	            selectedRobot = robot;
	            robotFound = true;
	            System.out.println("Robot selected at: (" + robotX + ", " + robotY + ")");
	            break;
	        }
	    }

	    if (!robotFound) {
	        selectedRobot = null;
	        System.out.println("No robot selected.");
	    }

	    drawWorld();  // Refresh canvas to highlight selected robot
	}

	
	



	/**
	 * Sets up the menu bar for the GUI.
	 * @return The menu bar with options for File and Help.
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();
	
		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
	        	timer.stop();
		        System.exit(0); 
		    }
		});
		mFile.getItems().addAll(mExit);
		
		Menu mHelp = new Menu("Help");
		MenuItem mAbout = new MenuItem("About");
		 
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();
            }	
		});
		
	 
		mHelp.getItems().addAll(mAbout);
		 
		menuBar.getMenus().addAll(mFile, mHelp);
		return menuBar;
	}

	/**
	 * Sets up the buttons and controls at the bottom of the GUI.
	 * @return A VBox containing buttons for controlling the simulation.
	 */
	private VBox setButtons() {
	    Button btnStart = new Button("Start");
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();
	       }
	    });

	    Button btnStop = new Button("Pause");
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();
	       }
	    });

	    Button btnAddOps = new Button("Add_Obstical");
	    btnAddOps.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	arena.addRandomObstacle(20); // Example coordinates
	        	drawWorld();
	       }
	    });

	    Button btnAdd = new Button("Add BasicRobot");
	    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
		        public void handle(ActionEvent event) {
		           	arena.addAdRobot();
		           	drawWorld();
	       }
	    });
	    
	    Button btnAddR = new Button("Add AdvRobot");
	    btnAddR.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
	           	arena.addRobot();
	           	drawWorld();
	       }
	    });

	    Button btnSave = new Button("Save");
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
	            arena.getAllRobots().clear();
	            arena.getObstacles().clear();

	            Save s = new Save(arena);
	            s.loadArena();  

	            System.out.println("=== After Loading ===");
	            System.out.println("Robots:");
	            for (Robot r : arena.getAllRobots()) {
	                System.out.println(r);
	            }
	            System.out.println("Obstacles:");
	            for (Obstacle o : arena.getObstacles()) {
	                System.out.println(o.getStrType());
	            }

	            drawWorld(); 
	            System.out.println("Arena loaded!");
	        }
	    });

	    Button btnUserRobot = new Button("UserControledRobot");
	    btnUserRobot.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	 userControlledRobot = new UserControlledRobot(100, 100, 10, 1, 1);
	        	 arena.addRobott(userControlledRobot);
	             drawWorld();
	       }
	    });
	  
	    Button btnToggleMaze = new Button("Toggle Obstacles Terrain");
	    btnToggleMaze.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            arena.toggleArena();
	            drawWorld();
	        }
	    });
	    
	    Button btnKillerRobot = new Button("Killer Robot");
	    btnKillerRobot.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	killerRobot = new KillerRobot(100, 106, 12, 10, 1);
	        	 arena.addRobott(killerRobot);
	             drawWorld();
	        }
	    });
	    
	    Button btnAddTeleportingRobot = new Button("Add Teleporting Robot");
	    btnAddTeleportingRobot.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            TeleportingRobot teleportingRobot = new TeleportingRobot(100, 100, 10, 0, 1);
	            arena.addRobott(teleportingRobot);
	            drawWorld();
	        }
	    });
	    
	    
	    
        Button btnDelete = new Button("Delete Selected Robot");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectedRobot != null) {
                    arena.removeRobot(selectedRobot);
                    System.out.println("Robot deleted.");
                    selectedRobot = null;  // Clear selection after deletion
                    drawWorld();
                } else {
                    System.out.println("No robot selected.");
                }
            }
        });

        TextField moveXField = new TextField();
        moveXField.setPromptText("X position");

        TextField moveYField = new TextField();
        moveYField.setPromptText("Y position");

        Button btnMove = new Button("Move Selected Robot");
        btnMove.setOnAction(event -> {
            if (selectedRobot != null) {
                try {
                    double newX = Double.parseDouble(moveXField.getText());
                    double newY = Double.parseDouble(moveYField.getText());

                    selectedRobot.setXY(newX, newY);
                    

                    System.out.println("Moved robot to: (" + newX + ", " + newY + ")");
                    drawWorld();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric values.");
                }
            } else {
                System.out.println("No robot selected to move.");
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
	                double newWidth = Double.parseDouble(widthField.getText());
	                double newHeight = Double.parseDouble(heightField.getText());

	                arena.setArenaSize(newWidth, newHeight);
	                drawWorld();
	                System.out.println("Arena resized to: " + newWidth + " x " + newHeight);
	            } catch (NumberFormatException e) {
	                System.out.println("Please enter valid numerical values for width and height.");
	            }
	        }
	    });	

	    HBox resizeControls = new HBox(5, widthField, heightField, btnResizeArena);
	  
	    HBox rowRun = new HBox(10,  // Increased spacing between elements
	            new Label("Run: "), 
	            btnStart, 
	            btnStop,
	            btnToggleMaze,
	            btnAddTeleportingRobot,
	            btnDelete
	           
	    );

	    

	    HBox rowArena = new HBox(10, 
	            new Label("Change: "), 
	            btnResizeArena,
	            resizeControls,
	            btnMove,
	            moveXField,
	            moveYField
	    );

	    HBox rowAdd = new HBox(10, 
	            new Label("Add: "), 
	            btnAdd, 
	            btnAddR, 
	            btnAddOps,
	            btnSave, 
	            btnLoad, 
	            btnKillerRobot,
	            btnUserRobot
	    );

	    // Add more padding and spacing for better structure
	    VBox vbox = new VBox(20, rowArena, rowRun, rowAdd);
	    vbox.setPadding(new Insets(20));
	    vbox.setAlignment(Pos.CENTER_LEFT);

	    return vbox;
	}
	

	/**
	 * Displays the current score at a specified position.
	 * @param x The x-coordinate of the position.
	 * @param y The y-coordinate of the position.
	 * @param score The current score.
	 */
	public void showScore (double x, double y, int score) {
		mc.showText(x, y, Integer.toString(score));
	}

	/** 
	 * Redraws the world on the canvas.
	 */
	public void drawWorld () {
	 	mc.clearCanvas();
	 	arena.drawArena(mc);
	}
	
	/**
	 * Displays the status of the robots and obstacles in the arena.
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();
		ArrayList<String> allBs = arena.describeAll();
		for (String s : allBs) {
			Label l = new Label(s);
			rtPane.getChildren().add(l);
		}	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	    primaryStage.setTitle("Akin Robot GUI");

	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));
	    bp.setBottom(setButtons());
	    bp.setTop(setMenu());

	    Group root = new Group();
	    Canvas canvas = new Canvas(800, 600);  // Increase canvas size
	    root.getChildren().add(canvas);

	    canvas.setOnMouseClicked(this::handleMouseClick);
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), 800, 600);

	    timer = new AnimationTimer() {
	        public void handle(long currentNanoTime) {
	            arena.adjustAllRobots();
	            drawWorld();
	            drawStatus();
	        }
	    };
	    timer.start();
	    arena = new RobotArena(800, 600);
	    drawWorld();

	    rtPane = new VBox();
	    rtPane.setAlignment(Pos.TOP_LEFT);
	    rtPane.setPadding(new Insets(20, 50, 20, 50));
	    bp.setRight(rtPane);

	    Scene scene = new Scene(bp, 1200, 800);  // Increase window size for better view
	    bp.prefHeightProperty().bind(scene.heightProperty());
	    bp.prefWidthProperty().bind(scene.widthProperty());
	    bp.setCenter(root);  // Move the arena canvas to the center

	    primaryStage.setScene(scene);
	    primaryStage.setMinWidth(1000);
	    primaryStage.setMinHeight(750);
	    primaryStage.show();

	    // Fix keyboard controls for selected robot movement
	    scene.setOnKeyPressed(event -> {
	        if (selectedRobot != null) {
	            switch (event.getCode()) {
	                case UP:
	                    selectedRobot.setXY(selectedRobot.getX(), selectedRobot.getY() - 10);
	                    break;
	                case DOWN:
	                    selectedRobot.setXY(selectedRobot.getX(), selectedRobot.getY() + 10);
	                    break;
	                case LEFT:
	                    selectedRobot.setXY(selectedRobot.getX() - 10, selectedRobot.getY());
	                    break;
	                case RIGHT:
	                    selectedRobot.setXY(selectedRobot.getX() + 10, selectedRobot.getY());
	                    break;
	                default:
	                    break;
	            }
	            drawWorld();  // Update the canvas to reflect movement
	        } else {
	            System.out.println("No robot selected to move.");
	        }
	    });
	}


	
	
	
	
	public static void main(String[] args) {
	    Application.launch(args);
	}
}
