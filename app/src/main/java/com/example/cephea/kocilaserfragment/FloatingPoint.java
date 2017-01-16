package com.example.cephea.kocilaserfragment;


public class FloatingPoint{
    private float x;
    private float y;

    public FloatingPoint(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    @Override
    public String toString(){
        return "["+x+", "+y+"]";
    }
}
