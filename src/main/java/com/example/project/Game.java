package com.example.project;
import java.util.Scanner;

public class Game {
    //6 different instance variables 
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size;

    //constructor of the game class
    public Game(int size) {
        this.size =size; 
       initialize();//sets up the game based on the code written in the initialize method
        play();//plays the game out 
    }

    public static void clearScreen() {//clears the screen each time the character moves 
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //play method where everything starts working in order to pla the actual game 
    public void play() {
        Scanner scanner = new Scanner(System.in);//scanner in order to take in the players inputs 
        boolean gameRunning = true;
    

        while(gameRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen();
            
            // Starts off by displaying the grid 
            grid.display();
            //need these 4 on the bottom of the grid in order to represent the players coordiantes, rowcol coordinates, lives and the amount of treasues you need to collect in order to win
            System.out.println("Player: (" + player.getX() + "," + player.getY() + ")");
            
            System.out.println("Player: [" + (size-1-player.getY()) + "][" + player.getX() + "]");
            
            System.out.println("Lives: " + player.getLives());
           
            System.out.println("Treasures: " + player.getTreasureCount());

            if (player.getLives() <= 0) {//checks for the condition of the loss
                grid.placeSprite(new Dot(player.getX(), player.getY()));//makes a dot in the place where the player died and replaces the player with dot
                grid.placeSprite(player);
                //uses for loop to chck the whole grid and replace every trophy, treasure, and dot with a skull except the player with a dot 
                for(int row = 0; row < size; row++) {
                    for(int col = 0; col < size; col++) {//checking through the grid
                        Sprite[][] gridArray = grid.getGrid();
                        Sprite current = gridArray[row][col];
                        if(current instanceof Dot || current instanceof Enemy || current instanceof Trophy || current instanceof Treasure || current instanceof Wall) {
                            gridArray[row][col] = new Skull(col, size-1-row);//calls to a new skull emoji to replace the other emojis
                            //if the grid has any of the instances at that place it replaces it with a skull 
                        }
                    }
                }
                clearScreen();//clears the whole screen to refresh 
                grid.display();//displays the final game screen
                System.out.println("YOU LOSE");
                gameRunning = false;//you lose and it ends 
                //now this is the win condition in the follow up else if condition 
            } else if (player.getWin()) {
                grid.placeSprite(new Dot(player.getX(), player.getY()));//putting the former player position with dot
                player.setX(trophy.getX());//setting the values of player to the values of trophy as the player is on the trophy tile 
                player.setY(trophy.getY());
                grid.placeSprite(player);//places the player on the trophy tile as player moves there 
                for(int row = 0; row < size; row++) {//once again checks all of the grid 
                    for(int col = 0; col < size; col++) {
                        Sprite[][] gridArray = grid.getGrid();
                        Sprite current = gridArray[row][col];
                        if(current instanceof Dot || current instanceof Enemy|| current instanceof Wall) {//if any of dots or enemies 
                            gridArray[row][col]= new Treasure(col, size-1-row);
                            //replaces grid with treasure all around except the former treasure places and player/old trophy place as player colleted trophy and no need to go over player 
                        }
                    }
                }
                clearScreen();//shows the final win screen of the game 
                grid.display();
                System.out.println("YOU WIN");
                gameRunning = false;//player has won and ends game
                //now main section where the player inputs wasd since already did the win or lose above
            } else {
                System.out.print("Enter move (w/a/s/d): ");//asks for the input 
                String direction = scanner.nextLine();//makes direction variable take in the input 
                //if any of these values given 
                if (direction.equals("w") || direction.equals("a") || 
                    direction.equals("s") || direction.equals("d")) {
                    //first checks if the next place it is going to is inside the boundries and not ouside as it has player.isvalid
                    if (player.isValid(size, direction)) {
                        int oldX = player.getX();
                        int oldY = player.getY();
                        int newX = oldX;
                        int newY = oldY;
                        
                        //takes in the new values and moves them 
                        if (direction.equals("w")) {//w means move up
                            newY++;
                        } else if (direction.equals("s")) {//s goes down so y 
                            newY--;
                        }else if (direction.equals("a")) {//a is to the left so minus x
                            newX--;
                        }else if (direction.equals("d")) {//d is right so plus 1 to x
                            newX++;
                        }
                        Sprite[][] gridArray = grid.getGrid();
                        Sprite target = gridArray[size-1-newY][newX];
                        //the position of the sprite in terms of row col to keep track 
                        if (target instanceof Trophy) {//if the sprite is on the trophy position 
                            if (player.getTreasureCount() == treasures.length) {//first checks if all the treasure collected 
                                grid.placeSprite(new Dot(oldX, oldY));//places dot in old value of player 
                                player.setX(newX);//sets the new values to player
                                player.setY(newY);
                                grid.placeSprite(player);//places it in the new values which was the old position of trophy
                                player.interact(size, direction, treasures.length, target);//takes the trophy(interacts with it)
                            } else {
                                //if not all treasure collected you cannot collect trophy and maintains the old position 
                                player.setX(oldX);
                                player.setY(oldY);
                                System.out.println("Collect all treasures first!");//prints this out 
                            }
                        } else if (target instanceof Enemy) {//now if  sprite meets with enemy case 
                            grid.placeSprite(new Dot(oldX, oldY));//place dot at  player old position 
                            grid.placeSprite(new Dot(newX, newY));//place dot after player goes to enemy old position 
                            player.setX(newX);//updates the coordinates once again 
                            player.setY(newY);
                            grid.placeSprite(player);//places player on enemy position 
                            player.interact(size, direction, treasures.length, target);//takes care of the enemy interaction which causes player to lose a life 
                        } else if (target instanceof Treasure) {
                            grid.placeSprite(new Dot(oldX, oldY));//place dot at players old position 
                            grid.placeSprite(new Dot(newX, newY));//place dot in new position of player which was old treasure area 
                            player.setX(newX);//updates the coordinates once again 
                            player.setY(newY);
                            grid.placeSprite(player);//places sprite on enemy position 
                            player.interact(size, direction, treasures.length, target);//interacts and collects it increasing the treasure count by 1 
                        } else if (!(target instanceof Wall)) {//checks to allow movement due to a a wall/maze ec
                            grid.placeSprite(new Dot(player.getX(), player.getY()));//replaces old position with dot 
                            player.move(direction);//gets coordinates of new position
                            grid.placeSprite(player);//places player at new position 
                        } else {
                            System.out.println("Can't walk through walls!");//else if hits wall cannot move 
                        }
                        
                    }
                }
            }
            
        }
        scanner.close();
    }


    //used to make you own game which I did for testing 
    public void initialize() {
        grid = new Grid(size);//makes a grid 
        player = new Player(0, 0);//coordinates of player 
        grid.placeSprite(player);//places player sprite  at those coordinates  

        for(int y=0; y<5; y++) {//vertical barrier I made here with the y values 
            grid.placeSprite(new Wall(2, y)); 
            grid.placeSprite(new Wall(7, y+3)); 
        }
    
        // made these horiztonal type walls with 1 hole exception 
        for(int x=0; x<8; x++) {
            if(x != 4) {
                grid.placeSprite(new Wall(x, 5)); 
            }
        }
        for (int x =0;x<10;x++){
            if(x!=3){
                grid.placeSprite(new Wall(x,8));
            }
        }
        
       
        grid.placeSprite(new Wall(2, 3)); //added a bunch of other walls over the places 
        grid.placeSprite(new Wall(6, 7));  
        grid.placeSprite(new Wall(7, 8));  
        
        grid.placeSprite(new Wall(9, 8));
        grid.placeSprite(new Wall(9, 8));


        treasures = new Treasure[2];//num of treasures 
        treasures[0] = new Treasure(1, 7);//where the treasues are in coordinates 
        treasures[1] = new Treasure(2, 2);
        for (Treasure t : treasures) {//places those treasures where i put the coordinates 
            grid.placeSprite(t);
        }
        
        enemies = new Enemy[2];//same thing with enemy as treasure 
        enemies[0] = new Enemy(2, 7);//coords for enemies 
        enemies[1] = new Enemy(7, 5);
        for (Enemy e : enemies) {
            grid.placeSprite(e);//for loop to go through and place them at the coordinates 
        }
        
        trophy = new Trophy(9, 9);//coordiates of trophy
        grid.placeSprite(trophy);//places at those coordinates 
        

        
    }

    public static void main(String[] args) {
        new Game(10);//grid size of 15 by 15 
          
        
    }
}