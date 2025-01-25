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

	/**
	 * Displays information about the program in an About dialog.
	 */
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("About");
	    alert.setHeaderText(null);
	    alert.setContentText("Akin's JavaFX Demonstrator");
	    alert.showAndWait();
	}
	
	/**
	 * Displays instructions for using the program in an Information dialog.
	 */
	private void showInfo() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("About");
	    alert.setHeaderText(null);
	    alert.setContentText("There are two types of robots in this simulation. The first type, "
	    		+ "Basic Robots, has simple movement behavior: they move around and change direction when they "
	    		+ "hit a wall or another robot. You can add them by clicking the \"Add Basic Robot\" button.");
	    alert.showAndWait();
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
		MenuItem minfo = new MenuItem("Instructions");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();
            }	
		});
		
		minfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showInfo();
            }	
		});
		mHelp.getItems().addAll(mAbout, minfo);
		 
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
		    
		    VBox vbox = new VBox(10, rowArena, rowRun, rowAdd);
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
	    Canvas canvas = new Canvas(400, 500);
	    root.getChildren().add(canvas);
	    bp.setLeft(root);

	    mc = new MyCanvas(canvas.getGraphicsContext2D(), 600, 500);

	    timer = new AnimationTimer() {
	        public void handle(long currentNanoTime) {
	            arena.adjustAllRobots();
	            drawWorld();
	            drawStatus();
	        }
	    };
	    timer.start();
	    arena = new RobotArena(400, 500);
	    drawWorld();

	    rtPane = new VBox();
	    rtPane.setAlignment(Pos.TOP_LEFT);
	    rtPane.setPadding(new Insets(5, 75, 75, 5));
	    bp.setRight(rtPane);

	    Scene scene = new Scene(bp, 700, 600);
	    bp.prefHeightProperty().bind(scene.heightProperty());
	    bp.prefWidthProperty().bind(scene.widthProperty());

	    scene.setOnKeyPressed(event -> {
	        if (userControlledRobot != null) {
	            userControlledRobot.direction(event.getText());
	        }
	    });

	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	public static void main(String[] args) {
	    Application.launch(args);
	}
}
