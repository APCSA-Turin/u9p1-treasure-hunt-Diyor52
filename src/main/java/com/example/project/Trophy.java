package com.example.project;

//subclass of treasure 
public class Trophy extends Treasure {
    public Trophy(int x, int y) {
        super(x, y);//looks at treasure which treasure looks at sprite 
    }
}