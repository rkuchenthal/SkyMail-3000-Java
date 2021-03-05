package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;

import java.util.ArrayList;

//superclass for all game objects
abstract public class GameObject implements IDrawable, ICollider {

    protected int mycolor = ColorUtil.rgb(0,0,0);
    private int size;
    private String shape;
    private double xLocation, yLocation;
    //protected int objectColor;


    protected ArrayList<GameObject> collidingWith = new ArrayList<GameObject>();

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize(){
        return size;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }


    public void setLocation(double xLocation, double yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public void setXLocation(int xLoc){
        this.xLocation = (double)xLoc;
    }

    public void setYLocation(int yLoc){
        this.yLocation = (double)yLoc;
    }

    public double getXLocation(){
        return xLocation;

    }

    public double getYLocation() {
        return yLocation;

    }

    public void setObjectColor(int r, int g, int b){
        mycolor = ColorUtil.rgb(r, g, b);
        //objectColor = mycolor;

     }
     public void changeObjectColor(int r, int g, int b){
         mycolor = ColorUtil.rgb(r, g, b);
         //objectColor = mycolor;

     }

    public int getObjectColor(){
        return mycolor;
    }

    public String toString(){
        return "loc(x,y)=("+ getXLocation() + "," + getYLocation() + "), color=[" + ColorUtil.red(mycolor)
                                                                                + ", " + ColorUtil.green(mycolor) +
                                                                                    ", "+ ColorUtil.blue(mycolor) + "] " +
                                                                                        ", size="+ getSize() ;
    }

    public abstract void nphCheck(int c);
}
