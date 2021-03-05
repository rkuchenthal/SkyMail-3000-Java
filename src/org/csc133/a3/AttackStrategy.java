package org.csc133.a3;

import com.codename1.util.MathUtil;

public class AttackStrategy implements IStrategy {

    private GameWorld gw;

    //strategy to chase down/attack human player
    public void setStrategy(NPH nph, GameWorld targetHeading){

        gw=targetHeading;

        // get player x
        double playerX = gw.player.getXLocation();

        // get player y
        double playerY = gw.player.getYLocation();

        // get nph y
        double nphY = nph.getYLocation();

        // get nph x
        double nphX = nph.getXLocation();

        //get x distance
        double gapX = playerX - nphX;

        //get y distance
        double gapY = playerY - nphY;

        double attackAngle = 90 - Math.toDegrees(MathUtil.atan2(gapY,gapX));

        nph.setHeading((int)attackAngle);

    }





}
