package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Skyscraper extends GameObject {

    private int sequenceNumber;
    private GameWorld gw;
    //skyscraper constructor
    public Skyscraper(int size, String shape, int xCoord, int yCoord, int seqNum , int r, int g, int b, GameWorld gw){
        setSize(size);
        setShape(shape);
        setLocation(xCoord, yCoord);
        setSequenceNumber(seqNum);
        setObjectColor(r, g, b);
        this.gw = gw;
    }

    //TODO: add collisions
    public boolean collidesWith(GameObject thatObject){  //instanciate collisiion arrayList
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
        else if(collisionCheck(thatObject) && !collidingWith.contains(thatObject) && !gw.deathCheck(gw.player)){

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

    public void handleCollision(GameObject thatObject){
        if((thatObject == gw.player) && ((Helicopter)thatObject).getLastSkyscraperReached() == this.getSequenceNumber()){
            changeObjectColor(0,0,0);
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

    //creates the checkpoint number for skyscraper
    public void setSequenceNumber(int sequenceNum){
        this.sequenceNumber = sequenceNum;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }


    public String toString(){
        return "Skyscraper"+ getSequenceNumber() + ": " + super.toString() + " seqNum = " + getSequenceNumber();

    }


    public void draw(Graphics g, Point containerOrigin){

        //get obj x and y loc in container
        int getSkyX = containerOrigin.getX() + (int) getXLocation();
        int getSkyY = containerOrigin.getY() + (int) getYLocation();

        //draw skyscraper square with obj color,
        //using given obj size
        g.setColor( getObjectColor() );
        g.fillRect(getSkyX - getSize() /2,
                   getSkyY - getSize() /2,
                      getSize(), getSize() );

        //add skyscraper number
        g.setColor( ColorUtil.WHITE );
        g.drawString( Integer.toString(getSequenceNumber()),getSkyX - getSize() /20,
                                                                 getSkyY - getSize() /2);

        

    }

    public void nphCheck(int x){}
}
