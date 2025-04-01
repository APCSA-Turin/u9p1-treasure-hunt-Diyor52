package com.example.project;


//Dot only needs a constructor
//extends Sprite as it is subclass of Sprite. 
public class Dot extends Sprite{

    public Dot(int x, int y){
        //I called super which is found in Sprite to get the x and y coordinates
        super(x,y);
    }
}
