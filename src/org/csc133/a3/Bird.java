package org.csc133.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.Random;

public class Bird extends MovableObjects {

    public Bird(int size, String shape, float xCoord, float yCoord , int r, int g, int b){
        setSize(size);
        setShape(shape);
        setLocation(xCoord, yCoord);
        setObjectColor(r, g, b);
        Random spe = new Random();
        setSpeed(spe.nextInt(10)+1);
        Random ran = new Random();
        setHeading(ran.nextInt(360));
    }

    //TODO: add collisions
    //we dont need to check if birds hit anything
    public boolean collidesWith(GameObject thatObject){return false;}
    public void handleCollision(GameObject thatObject){}

    public void nphCheck(int x){}

    public void changeObjectColor(int r, int g, int b){
        //overriding the inherited method so you cannot change this color.
    }


    public String toString(){
        return "Bird: " + super.toString();
    }


    public void draw( Graphics g, Point containerOrigin ){

        int getBirdX = (int)( containerOrigin.getX() + getXLocation() );
        int getBirdY = (int)( containerOrigin.getY() + getYLocation() );

        //draw the circle outline of the bird with object color,
        //using the initial size given to bird
        g.setColor(getObjectColor());
        g.drawArc(containerOrigin.getX() + (int)getXLocation() -getSize()/2,
                containerOrigin.getY() + (int)getYLocation() -getSize()/2,
                getSize(), getSize(),0, 360 );

        //drawLine
        g.setColor( ColorUtil.BLACK );
        g.drawLine(getBirdX, getBirdY, getBirdX + (int)( Math.cos( Math.toRadians( 90-getHeading() )) * getSize()),
                                       getBirdY + (int)( Math.sin( Math.toRadians( 90-getHeading() )) * getSize()));
    }

}
