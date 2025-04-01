package com.example.project;

//2 instance variables 
public class Sprite {
    private int x;
    private int y;

    //constructor 
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //bunch of getter methods 
    public int getX() { 
        return x; 
    }
    
    public int getY() { 
        return y; 
    }

    //setter methods so have to be void and sets to new values 
    public void setX(int newX) {
        x= newX ; 
    }
    
    public void setY(int newY) { 
        y= newY ;
    }

    //gets the coodinates in coodinate form
    public String getCoords() {
        return "(" + x + "," + y + ")";
    }

    //gets the rowcol of the coordinates and flips it as row col is inversed with brackets 
    public String getRowCol(int size) {
        return "[" + (size - 1 - y) + "][" + x + "]";
    }


    public void move(String direction) { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }

    public void interact() { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }



}
