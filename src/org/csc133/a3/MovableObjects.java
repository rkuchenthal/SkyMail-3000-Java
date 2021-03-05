package org.csc133.a3;


import java.util.Random;


abstract public class MovableObjects extends GameObject {

    protected int heading;
    private double speed;

    public MovableObjects(){
        //constructor
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHeading(int heading){
        this.heading = heading;
    }

    public void changeSpeed(double speed){
        this.speed = speed;
    }

    public int getHeading(){
        return heading;
    }

    //once out of bounds is signaled this makes sure to wrap
    // the new heading if it is less than 0 or more than 360
    public void changeHeading(int h){
        heading = h;
        if( heading>=360 ){
            heading = heading -360;
        }
        //wrapper for +360 case
        if( heading<0 ){
            heading = heading +360;
        }
    }



    public double getSpeed() {
        return speed;
    }

    //called by birds for their random movement
    public void changeDir() {

        Random rDegree = new Random();
        int random = rDegree.nextInt(2);
        if (( random % 2 ) == 0) {
            this.setHeading( getHeading() + 5 );
        } else {
            this.setHeading( getHeading() - 5 );
        }
    }


   //move() for everything but Bird
     public void move(MapView mp){

        double deltaX, deltaY;
        double e = Math.toRadians(90 - heading);

        //deltaX = cos(θ)*speed
        deltaX = Math.cos(e)*speed;
        //deltaY = sin(θ)*speed
        deltaY = Math.sin(e)*speed;

        if(getXLocation() >= mp.getWidth()){
            changeHeading(270);

        }
        else if(getYLocation() >= mp.getHeight()){
            changeHeading(180);

        }
        else if(getXLocation() <= 0 ){
            changeHeading(90);
         }
        else if(getYLocation() <= 0){
            changeHeading(360);
         }

        this.setLocation(getXLocation() + deltaX,
                         getYLocation() + deltaY);

    }


    public String toString(){
        return super.toString() + ", heading=" + getHeading() + ", speed=" + getSpeed();
    }

}
