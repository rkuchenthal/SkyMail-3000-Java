package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.Random;

public class NPH extends Helicopter {

    private GameWorld targetHeading;
    private IStrategy strategy;
    //private int lastSky;

    public NPH(int size, String shape, double xCoord, double yCoord , int r, int g, int b, double maxSpd, GameWorld gw){
        super( size, shape, xCoord, yCoord , r, g, b, maxSpd, gw);
        targetHeading = gw;
        setSpeed(maxSpd);
        strategy = new RaceStrategy();
        Random sH = new Random();
        setHeading(sH.nextInt(360));
        lastSkyscraperReached = 0;
    }

    //TODO: complete collisions
    public boolean collidesWith(GameObject thatObject){

        //instanciate collisiion arrayList
        //collidingWith = new ArrayList<>();

        //check for collision
        if((collisionCheck(thatObject)) && collidingWith.isEmpty()){

            //add thatObject to arrayList
            collidingWith.add(thatObject);

            //TODO: remove
            System.out.println(collidingWith.get(0));

            //returns true since we are colliding with object and no other object
            return true;

        }
        else if(collisionCheck(thatObject) && !collidingWith.isEmpty() ){

            //returns false because we are still colliding with object
            return false;

        }
        else{

            //remove thatObject to arrayList
            collidingWith.remove(thatObject);

            //TODO: remove
            if(collidingWith.isEmpty()){
                System.out.println("collision arraylist empty");
            }

            //return false because we are no longer colliding and remove collision object from arraylist
            return false;

        }

    }


    // determine what we collided with and call appropriate collisions
    public void handleCollision(GameObject thatObject){

        //check if skyscraper
        if(thatObject instanceof Skyscraper){
            targetHeading.nphCheckpoint((Skyscraper)thatObject, this);
        }
        //check if blimp
        if(thatObject instanceof RefuelingBlimp){
            targetHeading.e((RefuelingBlimp) thatObject);
        }


    }

    //collision check that makes the collidesWith() more readable
    private boolean collisionCheck(GameObject thatObject){
        if(this.getYLocation() + getSize()/2 >=thatObject.getYLocation() - thatObject.getSize()/2
                && this.getXLocation() + getSize()/2 >=thatObject.getXLocation() - thatObject.getSize()/2
                && this.getYLocation() - getSize()/2 <=thatObject.getYLocation() + thatObject.getSize()/2
                && this.getXLocation() - getSize()/2 <=thatObject.getXLocation() + thatObject.getSize()/2){

            return true;
        }
        else{
            return false;
        }
    }

    public int lastSkyscraperReached(){
        return lastSkyscraperReached;
    }
    public void setLastSky(int sky){
        lastSkyscraperReached = sky;
    }

    public void accelerate(){

    }
    //once a chopper "collides" with a the correct skyscraper ( in numerical order),
    //its check point is updated to the new skyscraper.
    public void nphCheck(int newSkyscraperNumber){
        if(newSkyscraperNumber == (lastSkyscraperReached() + 1)) {
            setLastSky(newSkyscraperNumber);
        }
    }
    //sets the strategy
    public void setStrat(IStrategy strategy){
        this.strategy = strategy;
    }


    //runs the strategy
    public void startStrat(){
        strategy.setStrategy(this, targetHeading);
    }


    public void draw(Graphics g, Point containerOrigin){

        //set nph fuel to max everytime paint is called
        // to make it so they dont run out of fuel
        fuelLevel = maxFuelLevel;

        //get obj x and y loc in container
        int getNphX = containerOrigin.getX() + (int)getXLocation();
        int getNphY = containerOrigin.getY() + (int)getYLocation();

        //centers for drawing line
        int getX = ( getNphX + getSize()/14 );
        int getY = ( getNphY + getSize()/14 );

        //draw helicopter circle with helicopter color
        g.setColor( getObjectColor() );
        g.fillArc(getNphX -getSize()/2,
                  getNphY -getSize()/2,
                getSize(), getSize(),0, 360 );

        //drawLine
        g.setColor( ColorUtil.BLACK );
        g.drawLine( getX, getY, getX + (int) ( Math.cos( Math.toRadians( 90-getHeading() )) * getSize() ),
                                getY + (int) ( Math.sin( Math.toRadians( 90-getHeading() )) * getSize()) );
    }

}
