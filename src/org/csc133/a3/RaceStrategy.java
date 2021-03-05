package org.csc133.a3;

import com.codename1.util.MathUtil;

public class RaceStrategy implements IStrategy {

    private GameWorld gw;

    //strategy to race player to next checkpoint
    public void setStrategy( NPH nph,GameWorld targetHeading ) {

        double nextSkyX;
        double nextSkyY;
        gw = targetHeading;


        if (nph.getLastSkyscraperReached() < gw.skys.size()){

            //nph target being set to next skyscraper
            nextSkyY = gw.skys.get( nph.getLastSkyscraperReached() +1).getYLocation();

            nextSkyX = gw.skys.get( nph.getLastSkyscraperReached() +1 ).getXLocation();


        }
        else{
            //nph has reached the last sky scraper
            nextSkyY = gw.skys.get( nph.getLastSkyscraperReached() ).getYLocation();

            nextSkyX = gw.skys.get( nph.getLastSkyscraperReached() ).getXLocation();

        }

        // get nextSkyX
        //nextSkyX = gw.getSkyX(gw.getNPHLastSky(nph));

        // get nextSkyY
        //nextSkyY = gw.getSkyY( gw.player.getLastSkyscraperReached() );
       // nextSkyY = gw.getSkyY(gw.getSkyArray().get(nph.getLastSkyscraperReached()));
          //nextSkyY = gw.getSkyArray().get(nph.getLastSkyscraperReached());

        // get nph y loc
        double nphY = nph.getYLocation();

        // get nph x loc
        double nphX = nph.getXLocation();

        //get x distance between sky and nph
        double gapX = nextSkyX - nphX;

        //get y distance between sky and nph
        double gapY = nextSkyY - nphY;

        double nextSkyAngle = 90 - Math.toDegrees( MathUtil.atan2( gapY, gapX ));

        nph.setHeading( ( int ) nextSkyAngle );

       /* //check to make sure we have not hit last skyscraper
        if( (nph.getLastSkyscraperReached() != gw.getSkyArray().size()-1) ) {

            nph.setLastSky(nph.getLastSkyscraperReached()+1);

        }
        else{

            System.out.println("The Non-player Helicopters have beat you to the finish");
            gw.exit();

        }*/
    }
}
