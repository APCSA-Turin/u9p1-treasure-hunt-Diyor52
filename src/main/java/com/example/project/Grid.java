
package com.example.project;

public class Grid {
    //only has 2 instance variables here 
    private Sprite[][] grid;
    private int size;

    //once again constrcutor of grid class 
    public Grid(int size) {//takes the size as parameter 
        this.size = size;
        grid = new Sprite[size][size];//makes a grid for the size given 
        for (int i = 0; i < size; i++) {//goes through the grid and places a dot everywhere 
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Dot(j, size - 1 - i);//j is x coordinate which is column(left to right) and i is y coordinate  which is row(bottom to top)as it is flipped due to row and colums 
            }
        }
    }

    public Sprite[][] getGrid() { 
        return grid; //getter method for grid 
    }

    public void placeSprite(Sprite s) {//how the sprite is placed on the new position 
        int row = size - 1 - s.getY();//gets the row index by using y coordinate 
        int col = s.getX();//gets the column index by using x coordinate since once again it is inverted and flipped 
        grid[row][col] = s;//sprite at that position in row col order now 
    }

    public void placeSprite(Sprite s, String direction) {//placing the sprite at the new position 
        if(direction.equals("w")){
            grid[size -s.getY() -1][s.getX()]=s;//gets new position of player -1 used to go up 
            grid[size-s.getY()-1+1][s.getX()]= new Dot(s.getX(),s.getY()); //replaces old position with dot +1 to cancel out the -1 and make it 0
        }else if(direction.equals("a")){
            grid[size -s.getY() -1][s.getX()]=s;//once again new position to left
            grid[size-s.getY()-1][s.getX()+1]= new Dot(s.getX(),s.getY());//add 1 to replace right position where it used to be
        }else if(direction.equals("s")){
            grid[size -s.getY() -1][s.getX()]=s;//new position to go down 
            grid[size-s.getY()-1-1][s.getX()]= new Dot(s.getX(),s.getY());//old position is -1-1 to make it appear as it went down to new position 
        }else if(direction.equals("d")){
            grid[size -s.getY() -1][s.getX()]=s;//new position 
            grid[size-s.getY()-1][s.getX()-1]= new Dot(s.getX(),s.getY());//old position which was the left becomes dot as it goes right 
        }
    }

    public void display() {//displays the emojis 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {//goes through the grid 
                if (grid[i][j] instanceof Player) {//player is knight emoji 
                    System.out.print("💂");
                } else if (grid[i][j] instanceof Enemy) {//enemy is alien 
                    System.out.print("👾");
                } else if (grid[i][j] instanceof Trophy) {//trophy is trophy
                    System.out.print("🏆");
                } else if (grid[i][j] instanceof Treasure) {// treasure is a bag of gold 
                    System.out.print("💰");
                } else if(grid[i][j] instanceof Skull){//used for the lose game 
                    System.out.print("💀");
                }else if(grid[i][j] instanceof Wall) {
                    System.out.print("🧱");  // used for extra credit wall
                } else{
                    System.out.print("⬛");//everything else is a black dot 
                }
            }
            System.out.println();//each row is new line 
        }
    }
    
    public void gameover() {
        System.out.println("Game Over! You ran out of lives.");//losing game 
    }

    public void win() {
        System.out.println("Congratulations! You found all treasures and reached the trophy!");//winning game 
    }
}
