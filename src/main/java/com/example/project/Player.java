package com.example.project;

//3 instance variables for the player class itself 
public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) {
        super(x, y);//looks at sprite as it is parent class 
        treasureCount = 0;//sets tresure to 0
        numLives = 2;//lives to 2
        win = false;//win is false
    }

    //bunch of getter methods 
    public int getTreasureCount() { return treasureCount; }
    public int getLives() { return numLives; }
    public boolean getWin() { return win; }

    //overrides just like the rest of subclass of sprite 
    @Override
    public String getCoords() {
        return "Player:" + super.getCoords();
    }

    //once again overrides 
    @Override
    public String getRowCol(int size) {
        return "Player:" + super.getRowCol(size);
    }

    @Override
    public void move(String direction) {//move position of the player sprite 
        
        if (direction.equals("w")) {
            setY(getY() + 1); // move up
        }
        if (direction.equals("s")) {
            setY(getY() - 1); // move down
        }
        if (direction.equals("a")) {
            setX(getX() - 1); // move left
        }
        if (direction.equals("d")) {
            setX(getX() + 1); // move right
        }
    }

    //where all the interactions happens 
    public void interact(int size, String direction, int numTreasures, Object obj) {
        if (obj instanceof Treasure && !(obj instanceof Trophy)) {//if it is treasure but not trophy then adds 1 or else it would add trophy as well 
            treasureCount = treasureCount + 1;
        }
        if (obj instanceof Enemy) {//removes 1 life from lives count 
            numLives--;
        }
        if (obj instanceof Trophy) {
            //have to make sure the treasure count is equal to actual number of treasures 
            if (treasureCount == numTreasures) {
                win = true;//then can proceed to win 
            }
        }
    }

    //checks the boundaries and if it is going out of bounds or not 
    public boolean isValid(int size, String direction) {
        int newX = getX();
        int newY = getY();
        
        
        if (direction.equals("w")) {//does the same as the move but uses it to check 
            newY = newY + 1;
        }
        if (direction.equals("s")) {
            newY = newY - 1;
        }
        if (direction.equals("a")) {
            newX = newX - 1;
        }
        if (direction.equals("d")) {
            newX = newX + 1;
        }

        if (newX < 0 || newX >= size || newY < 0 || newY >= size) {//if the x value is less than 0 or greater than or equal then remove since its size-1 or same concept for y values 
            return false;//returns false and is not valid 
        }
        return true;//else it is 
    }
}