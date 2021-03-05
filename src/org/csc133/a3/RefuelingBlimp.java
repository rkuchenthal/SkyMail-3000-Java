package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class RefuelingBlimp extends GameObject {

    private int fuelCapacity;

    public RefuelingBlimp(int size, String shape, float xCoord, float yCoord , int r, int g, int b){
        setSize(size);
        setShape(shape);
        setLocation(xCoord, yCoord);
        setObjectColor(r, g, b);
        setFuelLevel(15*size);
    }

    //TODO: add collisions
    public boolean collidesWith(GameObject thatObject){return true;}
    public void handleCollision(GameObject thatObject){}

    public int getFuelCapacity(){
        return fuelCapacity;
    }


    public void setFuelLevel(int level){
        fuelCapacity = level;
    }

    public int getFuelLevel(){
        return fuelCapacity;
    }


    public String toString(){
        return "RefuelingBlimp: " + super.toString() + " capacity=" + getFuelCapacity();
    }

    public void draw(Graphics g, Point containerOrigin){

        //get obj x and y loc in container
        int getBlimpX = containerOrigin.getX() + (int) getXLocation();
        int getBlimpY = containerOrigin.getY() + (int) getYLocation();

        //draw helicopter circle with helicopter color
        g.setColor( getObjectColor() );
        g.fillArc(getBlimpX -getSize()/2,
                  getBlimpY -getSize()/2,
                getSize()+ 25, getSize(),0, 360 );

        //draw fuel capacity of blimps on the blimp itself in black color
        g.setColor(ColorUtil.BLACK);
        g.drawString( Integer.toString( getFuelCapacity()),getBlimpX -getSize()/20,getBlimpY - getSize()/2);


    }
    public void nphCheck(int x){}
}
