/* Austin Paul and Aaron Mehrings
 * Final Project
 * SE350
 */


import javafx.stage.Stage;

import java.util.Observer;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OceanExplorer extends Application{
	Random randIsland = new Random();
	int randIslands = randIsland.nextInt(20);
	final int dimensions = 20; 
	final int scale = 50;
	final int islandCount = randIslands;
	
	OceanMap oceanMap; 
	boolean[][] islandMap;
	Scene scene;
	Pane root;
	
	ImageView ChristopherColumbusImageView;
	ImageView pirateOneImageView;
	ImageView pirateTwoImageView;
	ImageView islandImageView;
	
	ChristopherColumbus ChristopherColumbus;
	PirateShip pirate1;
	PirateShip pirate2;
	
	@Override
	public void start(Stage oceanStage) throws Exception {
		oceanMap = OceanMap.getInstance();
		islandMap = oceanMap.getMap();
		
		root = new AnchorPane();
		drawMap();
		loadIslandImages();
		
		ChristopherColumbus = new ChristopherColumbus(oceanMap);
		loadChristopherColumbusImage();
		
		pirate1 = new PirateShip(oceanMap, 1);
		pirate2 = new PirateShip(oceanMap, 2);
		loadPirateImages();
		
		ChristopherColumbus.addObserver(pirate1);
		ChristopherColumbus.addObserver(pirate2);
		
		scene = new Scene(root, dimensions*scale, dimensions*scale);
		oceanStage.setScene(scene);
		oceanStage.setTitle("Ocean");
		oceanStage.show();
		startSailing();
	}
	
	public void drawMap() {
		islandMap = oceanMap.getMap();
		for(int x = 0; x < dimensions; x++){
		   for(int y = 0; y < dimensions; y++){
		       Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale); 
		       rect.setStroke(Color.BLACK);   // We want the black outline
		       rect.setFill(Color.PALETURQUOISE);  // I like this color better than BLUE
		       root.getChildren().add(rect);
		       // Add to the node tree in the pane
		   } 
		} 
	}
	
	private void loadChristopherColumbusImage() {
		Image ChristopherColumbusImage = new Image("images\\ship.png", 50, 50 , true, true);
		ChristopherColumbusImageView = new ImageView(ChristopherColumbusImage);
		ChristopherColumbusImageView.setX(oceanMap.getChristopherColumbusLocation().x*scale);
		ChristopherColumbusImageView.setY(oceanMap.getChristopherColumbusLocation().y*scale);
		root.getChildren().add(ChristopherColumbusImageView);
	}
	
	private void loadIslandImages() {
		for(int x = 0; x < dimensions; x++){
			   for(int y = 0; y < dimensions; y++){
				   if (islandMap[x][y] == true) {
					    Image islandImage = new Image("images\\island.jpg", 50, 50 , true, true);
						islandImageView = new ImageView(islandImage);
						islandImageView.setX(x*scale);
						islandImageView.setY(y*scale);
						root.getChildren().add(islandImageView);
				   }
			   }
		}
	}
	
	private void loadPirateImages() {
		Image pirateChristopherColumbusImage = new Image("images\\pirateShip.png", 50, 50, true, true);
		pirateOneImageView = new ImageView(pirateChristopherColumbusImage);
		pirateOneImageView.setX(oceanMap.getPirateLocation(1).x*scale);
		pirateOneImageView.setY(oceanMap.getPirateLocation(1).y*scale);
		root.getChildren().add(pirateOneImageView);
		
		pirateTwoImageView = new ImageView(pirateChristopherColumbusImage);
		pirateTwoImageView.setX(oceanMap.getPirateLocation(2).x*scale);
		pirateTwoImageView.setY(oceanMap.getPirateLocation(2).y*scale);
		root.getChildren().add(pirateTwoImageView);
	}
	
	
	
	private void startSailing() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()) {
					case RIGHT:
						ChristopherColumbus.goEast();
						break;
					case LEFT:
						ChristopherColumbus.goWest();
						break;
					case UP:
						ChristopherColumbus.goNorth();
						break;
					case DOWN:
						ChristopherColumbus.goSouth();
						break;
				default:
					break;
				}
				ChristopherColumbusImageView.setX(oceanMap.getChristopherColumbusLocation().x*scale);
				ChristopherColumbusImageView.setY(oceanMap.getChristopherColumbusLocation().y*scale);
				
				pirateOneImageView.setX(oceanMap.getPirateLocation(1).x*scale);
				pirateOneImageView.setY(oceanMap.getPirateLocation(1).y*scale);
				
				pirateTwoImageView.setX(oceanMap.getPirateLocation(2).x*scale);
				pirateTwoImageView.setY(oceanMap.getPirateLocation(2).y*scale);
			}
		});
	}
	
	
	public static void main(String[] args) {
        launch(args);
    }

}
