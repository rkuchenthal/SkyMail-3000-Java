package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.ArrayList;

public class Helicopter extends MovableObjects implements Isteerable {

    protected float fuelLevel,  maxFuelLevel = 5000;
    protected int lastSkyscraperReached = 0;
    private float  fuelLossRate, damageLevel = 0,  maxDamageLevel = 100;
    private int    stickAngle, percentage;
    private double maximumSpeed , minSpeed = 0;

    private GameWorld gw;
    //private ArrayList<GameObject> collidingWith;


    //helicopter constructor
    protected Helicopter(int size, String shape, double xCoord, double yCoord , int r, int g, int b, double maxSpd, GameWorld gw){
        setSize(size);
        setShape(shape);
        setLocation(xCoord, yCoord);
        setObjectColor(r, g, b);
        fuelLevel = maxFuelLevel;
        fuelLossRate = 5;
        stickAngle = 0;
        maximumSpeed = maxSpd;
        setSpeed(2);
        setHeading(0);
        this.gw = gw;

        //collidingWith = new ArrayList<GameObject>();
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: complete collisions
    public boolean collidesWith(GameObject thatObject){

        //instanciate collisiion arrayList
        //collidingWith = new ArrayList<>();

        //check for collision
        if((collisionCheck(thatObject)) && collidingWith.isEmpty() && !gw.deathCheck(gw.player)){

            //add thatObject to arrayList
            collidingWith.add(thatObject);

            //TODO: remove
            System.out.println(collidingWith.get(0));

            //returns true since we are colliding with object and no other object
            return true;

        }
        else if(collisionCheck(thatObject) && !collidingWith.isEmpty() && !gw.deathCheck(gw.player)){

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
            gw.checkpoint((Skyscraper) thatObject);
        }
        //check if nph
        if(thatObject instanceof NPH){
            gw.PHc();
        }
        //check if bird
        if( thatObject instanceof Bird){
            gw.g();
        }
        //check if blimp
        if(thatObject instanceof RefuelingBlimp){
            gw.e((RefuelingBlimp) thatObject);
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////

    //cause helicopter to speed up, it caps out at maxSpeed,
    // and is throttled proportionatly if there is any damage or if there isn't maxFuel
    public void accelerate(int newSpeed){
        if((getSpeed()+ (float)newSpeed) <= maximumSpeed){
            setSpeed(getSpeed()+ (float)newSpeed);
        }
        else{
            setSpeed(getMaximumSpeed());
        }
    }

    //slowly decrements the speed of the helicopter
    public void brake(){
        if(getSpeed() > minSpeed){
            if((getSpeed() - 1) <=0 ){
                changeSpeed(0);
            }
            else {
                changeSpeed(getSpeed() - 1);
            }
        }
    }

    //heli to heli collision
    public void heliCollision() {

        if (maximumSpeed > 0) {
            setMaximumSpeed(getMaximumSpeed()- 1);
            if((getSpeed()-1) >= getMinSpeed()){
                setSpeed(getSpeed() - 1);
            }
        }

        //heli to heli collision
        if ((getDamageLevel() + 10) > getMaxDamageLevel()) {
            setDmg(getMaxDamageLevel());

        }
        else{
            addDamage(10);
            changeObjectColor(175, 0, 100);
        }
    }

    //heli to bird collision
    public void birdCollision(){
        if (maximumSpeed > 0) {
            setMaximumSpeed(getMaximumSpeed()- 1);
            if((getSpeed()-1) >= getMinSpeed()){
                setSpeed(getSpeed() - 1);
            }

        }

        if((getDamageLevel() + 5) > getMaxDamageLevel()){
            setDmg(getMaxDamageLevel());
        }
        else{
            addDamage(5);
            changeObjectColor(200,0,10);
        }

    }

   public void nphCheck(int x){}
    //converts current dmg level to a percentage
    public int getDmgPercent(){
        float dmg = getDamageLevel();
        float maxDmg = getMaxDamageLevel();

        // convert the vars to a percent
        percentage = Math.round((dmg*100)/maxDmg);

        return percentage;
    }


    public void setMaximumSpeed(double f){
        maximumSpeed = f;
    }

    public void resetDmg(){
        damageLevel = 0;
    }


    public double getMinSpeed(){
        return minSpeed;
    }


    public void setDmg(float x){
        damageLevel = x;
    }


    //add the blimp fuel amount to the current heli fuel
    public void addFuelFromBlimp(int blimpFuel){
        if((fuelLevel+(float)blimpFuel)> maxFuelLevel){
            fuelLevel= maxFuelLevel;
        }
        else{
            fuelLevel +=(float) blimpFuel;
        }

    }

    public double getMaximumSpeed(){
        return maximumSpeed;
    }

    public float getFuelLossRate(){
        return fuelLossRate;
    }

    public void refuel(){
        fuelLevel = maxFuelLevel;
    }

    public void changeFuelLevel(float mObjs){
        fuelLevel = mObjs;
    }

    public float getFuelLevel(){
        return fuelLevel;
    }

    public void addDamage(float newDamage){
        damageLevel = getDamageLevel() + newDamage;
    }

    public float getDamageLevel(){
        return damageLevel;
    }

    public int getStickAngle(){
        return stickAngle;
    }

    public float getMaxDamageLevel(){
        return maxDamageLevel;
    }

    public int getLastSkyscraperReached(){
        return lastSkyscraperReached;
    }

    //once a chopper "collides" with a the correct skyscraper ( in numerical order),
    //its check point is updated to the new skyscraper.
    public void updateCheckPoint(int newSkyscraperNumber){
        if(newSkyscraperNumber == (lastSkyscraperReached + 1)) {
            lastSkyscraperReached = newSkyscraperNumber;
        }
    }


//////////////////////////////////////////////////////////////////////////////
    // ALL TURNING METHODS///////


    public void changeStickAngle(int stickTurn){
        //int stickTurn
        if((stickAngle + stickTurn) <= 40 && (stickAngle + stickTurn) >= -40){
            stickAngle = stickAngle + stickTurn;
        }
        else if(stickTurn < -40){
            stickAngle = -40;
        }
        else if(stickTurn > 40){
            stickAngle = 40;
        }

    }

    //takes the stick angle and turns
    // the helicopter accordingly
    public void turn(boolean tick){
        //change the actual heading
        if(stickAngle>=5){
            heading = getHeading();
            heading = heading +5;

            //wrapper for +360 case
            if(heading>=360){
                heading = heading -360;
            }
            //reduce stick angle
            stickAngle = stickAngle -5;

            //change heading in movabeObjects
            setHeading(heading);
        }
        else if(stickAngle<=-5){
            heading = getHeading();
            heading = heading -5;

            //wrapper for +360 case
            if(heading<0){
                heading = heading +360;
            }
            //reduce stick angle
            stickAngle = stickAngle +5;

            //change heading in movabeObjects
            setHeading(heading);
        }

    }
//////////////////////////////////////////////////////////////////////////////////////

    public void draw(Graphics g, Point containerOrigin){

        int getHeliX = ( containerOrigin.getX() + (int) getXLocation() + getSize()/14 );
        int getHeliY = ( containerOrigin.getY() + (int) getYLocation() + getSize()/14 );

        //draw helicopter circle with helicopter color,
        //based off the initial size of the helicopter
        g.setColor( getObjectColor() );
        g.fillArc(containerOrigin.getX() + (int) getXLocation() -getSize()/2,
                  containerOrigin.getY() + (int) getYLocation() -getSize()/2,
                  getSize(), getSize(),0, 360 );

        //drawLine with color
        g.setColor( ColorUtil.BLACK );
        g.drawLine( getHeliX, getHeliY, getHeliX + (int)( Math.cos( Math.toRadians( 90-getHeading() )) * getSize() ),
                                        getHeliY + (int)( Math.sin( Math.toRadians( 90-getHeading() )) * getSize() ));
    }
///////////////////////////////////////////////////////////////////////////////////////////////
    ////TO STRINGS/////////////////

    public String toString(){
        return "Helicopter: " + super.toString() + ", maxSpeed=" + getMaximumSpeed() + ", stickAngle=" + getStickAngle()
                + ", fuelLevel=" + getFuelLevel() + ", damageLevel=" + getDamageLevel();
    }
    // toString info for the display method
    public String playerToString(){
        return  ", Highest Skyscraper reached:" + getLastSkyscraperReached() + ", fuelLevel=" + getFuelLevel()
                + ", damageLevel=" + getDamageLevel();
    }

}
