package com.example.project;

//Enemy only need constructor and getCoords() getRowCol()
//extends Sprite once again as Enemy is a subclass of the parent class Sprite 
public class Enemy extends Sprite {
    
    public Enemy(int x, int y) {
         //I called super which is found in Sprite to get the x and y coordinates
        super(x, y);
    }
    
    //Player must move away from enemy in order not to lose a life 
    //Overrides the parent class getCoords() method since it does a different action.
    @Override
    public String getCoords() {
        //gets the x and y value but with enemy: behind it 
        return "Enemy:" + super.getCoords();
    }

    //Same concept with the override which makes it different from the parent class getRowCol()
    @Override
    public String getRowCol(int size) {
        return "Enemy:" + super.getRowCol(size);//gets the row col position of the enemy which is different than x y since row col is reversed since y is row and x is col
    }

    
}