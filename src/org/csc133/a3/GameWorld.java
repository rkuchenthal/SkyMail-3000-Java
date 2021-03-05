package org.csc133.a3;

import com.codename1.ui.Container;
import com.codename1.ui.Display;

import java.util.ArrayList;
import java.util.Random;


public class GameWorld {
    private final GameObjectCollection gameObjects;
    private MapView mapView;
    private int gameClock = 0;
    private int playerLives = 3;
    private final int numOfBirds = 5;
    private final int numOfSkyScrapers = 9;
    private final int numOfNPHS = 3;
    private final int numOfRefuelBlimps = 3;
    protected Helicopter player;
    private ArrayList<Bird> birds;
    public ArrayList<Skyscraper> skys;
    private ArrayList<RefuelingBlimp> blimps;
    private ArrayList<NPH> nphs;
    private boolean soundToggle = true;
    private BGSound bgSound;
    private Sound heliCrashSound;
    private Sound heliRefuelSound;
    private Sound heliDmgSound;
    private Sound heliDeathSound;
    public boolean paused = false;
    public GameClockComponent clockRef;


    public GameWorld(){
        gameObjects = new GameObjectCollection();

    }

    public void init(MapView map){
    //code here to create the
    //initial game objects/setup
         mapView = map;

         //initialize all arrays
         birds = new ArrayList<Bird>();
         skys = new ArrayList<Skyscraper>();
         blimps = new ArrayList<RefuelingBlimp>();
         nphs = new ArrayList<NPH>();

         playerLives = 3;
         gameClock = 0;

         //create birds
        for( int i=0; i< numOfBirds;i++){
            addBird();
        }
        //create skyscrapers
        for( int i=0; i< numOfSkyScrapers;i++){
            addSky();
        }
        //create blimps
        for( int i=0; i< numOfRefuelBlimps;i++){
            addBlimp();
        }
        //add helicopter last so it shows on top of everything
        gameObjects.add(new Helicopter(75, "circle", skys.get(0).getXLocation(), skys.get(0).getYLocation(), 255, 0, 255, 10, this ));

        // assign helicopter to a variable
        player = (Helicopter) gameObjects.get(gameObjects.size()-1);

        //create nphs
        for( int i=0; i< numOfNPHS;i++){
            addNPH();
        }

        //initially moves all objects
        for (int i=0; i<gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof MovableObjects) {
                    ((MovableObjects) gameObjects.get(i)).move(mapView);
            }
        }

        bgSound = new BGSound("bgMusic.wav");
        //play bg music
        bgSound.play();
        //createSounds();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // additional methods here to
    // manipulate world objects and
    // related game state data


    //these are used to make sure random gen numbers arent overlapping
    private int nphSpeed;


    //makes random x var to use in object creations
    public int getRandomX(){
        Random bx1 = new Random(); // x axis
        int xLoc = bx1.nextInt(mapView.getWidth());
        //make sure its off the boundary
        if(xLoc <= 45){
            return xLoc + 150;
        }
        else if(xLoc >= (mapView.getWidth()-45)){
            return xLoc - 150;
        }
        else{
            return xLoc;
        }
    }

    //makes random y var to use in object creations
    public int getRandomY(){
        Random by1 = new Random(); // y axis
        int yLoc = by1.nextInt(mapView.getHeight());
        //make sure its off the boundary
        if(yLoc <= 45){
            return yLoc + 150;
        }
        else if(yLoc >= (mapView.getHeight()-45)){
            return yLoc - 150;
        }
        else{
            return yLoc;
        }
    }

    //makes random x var for nph to use in  creations
    public int getRandomXNPH(){
        Random bx1 = new Random(); // x axis
        int xLoc = bx1.nextInt(450);
        //make sure its off the boundary
        if(xLoc <= 10){
            return xLoc + 100;
        }
        else if(xLoc >= (mapView.getWidth()-10)){
            return xLoc - 100;
        }
        else{
            return xLoc;
        }
    }

    //makes random y var for nph to use in  creations
    public int getRandomYNPH(){
        Random by1 = new Random(); // y axis
        int yLoc = by1.nextInt(800);
        //make sure its off the boundary
        if(yLoc <= 10){
            return yLoc + 100;
        }
        else if(yLoc >= (mapView.getHeight()-10)){
            return yLoc - 100;
        }
        else{
            return yLoc;
        }
    }

    //makes random blimp size var to use in  creations
    public int getRandomBlimpSize(){
        Random s1 = new Random(); // y axis
        return s1.nextInt(75) + 30;
    }

    //makes random y var for nph to use in  creations
    public int getRandomSpdNPH(){
        Random by1 = new Random();
        int spd = by1.nextInt(4);

        if(spd == 0){
            spd +=1;
        }
        //change speed if it equals previous nph spd
        if(nphSpeed == spd ){
            if(spd-2 <= 0) {
                nphSpeed = spd - 1;
                return nphSpeed;
            }
            else{
                nphSpeed = spd - 2;
                return nphSpeed;
            }
        }
        else{
            return spd;
        }
        ///return by1.nextInt(4);
    }

    //creates birds
    public void addBird(){
        birds.add(new Bird(60, "circle", (float)(getRandomX()), (float)(getRandomY()), 255,0,0 ));
        gameObjects.add(birds.get(birds.size()-1));
    }


    //creates sky scrapers
    public void addSky(){
        int seqNum = skys.size()-1;
        skys.add(new Skyscraper(85, "square",getRandomX(),getRandomY(), seqNum + 1, 0,155,200, this));
        gameObjects.add(skys.get(skys.size()-1));
    }


    //Creates blimps
    public void addBlimp(){
        int x = getRandomX();
        int y = getRandomY();
        boolean offset = false;
        boolean inBounds = false;

        //make sure there is none to minimal overlap
        for(Skyscraper oldObject : skys){
            if((oldObject.getXLocation()+oldObject.getSize()) > (x+100)){
                if((oldObject.getXLocation()+oldObject.getSize()) < (x-100)){
                    if((oldObject.getYLocation()+oldObject.getSize()) < (y+100)){
                        if((oldObject.getYLocation()+oldObject.getSize()) < (y-100)){
                            offset = true;
                        }
                    }
                }
            }
        }

        if (offset){
            blimps.add(new RefuelingBlimp(getRandomBlimpSize(), "oval", (float)x, (float)y,0,255,0));
            gameObjects.add(blimps.get(blimps.size()-1));
        }
        else{

            y = y+85;
            x = x+85;
            //make sure its in the boundarys
            if(y <= 10){
                y= y + 185;
            }
            else if(y >= (mapView.getHeight()-10)){
                y= y - 185;
            }


            blimps.add(new RefuelingBlimp(getRandomBlimpSize(), "oval", (float)x, (float)y,0,255,0));
            gameObjects.add(blimps.get(blimps.size()-1));
        }



        //blimps.add(new RefuelingBlimp(getRandomBlimpSize(), "oval", (float)getRandomX(),(float)getRandomY(),0,255,0));
        //gameObjects.add(blimps.get(blimps.size()-1));
    }


    //creates nphs
    public void addNPH(){
        double spd = getRandomSpdNPH()+1;


        //make sure speeds are diff
        for(NPH oldObject : nphs){
            if(oldObject.getSpeed() == spd){
                spd = spd+1;
            }
        }
        nphs.add(new NPH(75, "circle", skys.get(0).getXLocation() + getRandomXNPH() ,
                                                   skys.get(0).getYLocation() + getRandomYNPH() ,
                                                    0, 0, 255, spd, this));
        gameObjects.add(nphs.get(nphs.size()-1));
    }



    //returns gameObjects arrayList
    public GameObjectCollection getObjectList(){
        return gameObjects;
    }

    //if the player crashes or runs out of fuel this is used by tic()
    // to reset the chopper back to the last checkpoint, reset dmg == 00, , reset fuel, decLives
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //programme exit function
    public void exit(){
        System.out.println("Program Exited by User");
        Display.getInstance().exitApplication();
    }

    //takes keyboard input, to accelerate heli
    public void acc(){
        getHeli().accelerate(2);
        System.out.println("acc increased");
    }

    //takes keyboard input, to brake heli
    public void brak(){
        getHeli().brake();
        System.out.println("brake applied");
    }

    //takes keyboard input, to turn heli joystick left
    public void left(){
        System.out.println("turned left");
        player.changeStickAngle(5);
        player.turn(true);
    }

    //takes keyboard input, to turn heli joystick right
    public void right(){
        System.out.println("turned right");
        player.changeStickAngle(-5);
        player.turn(true);

    }

    //set the toggle switch for sound/no sound button
    public void setSoundToggle(){
        //sound play
        if (!soundToggle){
            soundToggle = true;
            bgSound.play();
        }
        //mute all sound
        else{
            soundToggle = false;
            bgSound.pause();
        }

    }


///////COLLISIONS///////////////////////////////////////////////////////////////////////////////////////////



    //heli vs heli collision
    public void PHc(){
        if(heliCrashSound == null) {
            heliCrashSound = new Sound("heliCrash.wav");
        }
        //check if sound is unmuted
        if(soundToggle){
            heliCrashSound.play();
        }
        //heliCrashSound.play();
        getHeli().heliCollision();
        System.out.println("collided with another layer helicopter");

    }

    //heli vs bird collision
    public void g(){
        if(heliDmgSound == null) {
            heliDmgSound = new Sound("dmgLoss.wav");
        }
         //check if sound is unmuted
        if(soundToggle){
            heliDmgSound.play();
        }

        System.out.println("You have collided with a Bird!! \n Ouch that hurt!!!");
        getHeli().birdCollision();

    }

    //heli vs blimp collision
    public void e(RefuelingBlimp blimp){

        if(blimp.getFuelCapacity() > 0){
            if(heliRefuelSound == null) {
                heliRefuelSound = new Sound("chargeSound.wav");
            }
            //check if sound is unmuted
            if(soundToggle){
                heliRefuelSound.play();
            }

            System.out.println("You have collided with a blimp! \n Congratulations, you now have more fuel");
            // decide which blimp to eliminate, then zero out
            emptyBlimp(blimp);

            //make new blimp
            addBlimp();
        }

      }


    //helicopter Skyscraper collision
    public void checkpoint(Skyscraper skyscraper) {

        //makes it so i can increment through checkpoints
        int c;


        for(int i =0; i<skys.size();i++) {
            if (skyscraper == skys.get(i)) {
                c = skys.get(i).getSequenceNumber();


                    //make sure we havent already reached said skyscraper
                    if (c <= player.getLastSkyscraperReached()) {
                        System.out.println("You have already reached that Skyscraper");
                    }
                    //make sure said skyscraper exists in game
                    else if (c > skys.size()) {
                        System.out.println("Skyscraper does not exist, try another");
                    }
                    //check if its final skyscraper
                    else if (c == (skys.size()-1) && (player.getLastSkyscraperReached() == (skys.size() -1)) ) {
                        System.out.println("Congratulations you win!");
                        exit();
                    }
                    //else call update checkpoint for player helicopter
                    else {
                        System.out.println("You have collided " + c + "! Your checkpoint is now saved.");
                        player.updateCheckPoint(c);
                    }

            }
        }

    }
    //helicopter Skyscraper collision
    public void nphCheckpoint(Skyscraper skyscraper, NPH nph) {
        //check to make sure nph hasn't reached final skyscraper, then move it to next skyscraper
        if (skyscraper.getSequenceNumber() == nph.getLastSkyscraperReached()+1 ) {
            nph.setLastSky(skyscraper.getSequenceNumber());

            //check if its final skyscraper, then cause nph to win
            if (skyscraper.getSequenceNumber() == (numOfSkyScrapers - 1)) {
                System.out.println("Congratulations you Lost to a computer Helicopter!");
                exit();
            }

        }

    }



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //once heli dies or runs out of gas reset all attributes back to normal
    public void heliReset(){

        Skyscraper lastSky = skys.get(player.getLastSkyscraperReached());
        player.setLocation(lastSky.getXLocation(), lastSky.getYLocation());
        player.refuel();
        player.setHeading(0);
        player.setDmg(0);
        player.setMaximumSpeed(9);
        player.setSpeed(2);
        player.setObjectColor(255, 0, 255);
        resetHeliDmg();

        mapView.update();
    }

    //get helicopter object from array list to be able to modify it
    public Helicopter getHeli(){
        //Helicopter hObj = (Helicopter)gameObjects.get(0);
        return player;
    }

    //had to use an external incrementer to make sure we are not emptying he
    // the same blimp each time there is a collision
    public void emptyBlimp(GameObject blimp){

        for(int i =0; i<blimps.size();i++){
            if(blimp == blimps.get(i)){

                player.addFuelFromBlimp(blimps.get(i).getFuelCapacity());
                //change blimp fuel level and color after collison
                blimps.get(i).setFuelLevel(0);
                blimps.get(i).changeObjectColor(0, 75,0);

                //add fuel to heli
                player.addFuelFromBlimp(blimps.get(i).getFuelCapacity());

                //TODO: make it so zeroed blimps dont cause emptyBlimp() to be called
                blimps.remove(i);
            }

        }

    }

    //returns heli dmg lvl
    public int getHeliDmgPerc(){
        return(getHeli().getDmgPercent());
    }

    public ArrayList getSkyArray(){
        return skys;
    }

    //reset player heli dmg to 0
    public void resetHeliDmg(){
        getHeli().resetDmg();
    }

    //decriment player lives
    public void decLives(){
        playerLives = playerLives -1;
        if(playerLives <= 0){
            System.out.println("You have run out of lives...\n GAME OVER!");
            exit();
        }
    }

    public void addClock(GameClockComponent clock){
        clockRef = clock;
    }

    //returns # of lives left
    public int getLives(){
        return(playerLives);
    }

    public void pauseGame(){
        paused = true;

        clockRef.pauseTime();
    }

    public void unPause(){
        paused = false;
        clockRef.unpauseTime();
    }

    //returns last sky reached
    public int getLast(){
        return player.getLastSkyscraperReached();
    }

    //returns nph last sky reached
    public int getNPHLastSky(NPH nph){
        return nph.lastSkyscraperReached();
    }

    //returns heli heading
    public int getHead(){
        return(getHeli().getHeading());
    }

    //iterates through all nphs and sets their strategies to the new one
    public void setNPHStrategy(int strat){
        if(strat == 1){
            for(int i = 0 ; i <nphs.size(); i++){
               nphs.get(i).setStrat(new AttackStrategy());
            }
        }
        else if(strat == 2){
            for(int i = 0 ; i <nphs.size(); i++){
                nphs.get(i).setStrat(new RaceStrategy());
            }
        }
    }

    //get skyscraper x loc
    public double getSkyX(int skyNum){
       return skys.get(skyNum).getXLocation();
    }


    //get skyscraper x loc
    public double getSkyY(int skyNum){
       return skys.get(skyNum).getYLocation();
    }

    //return number of sky's in game
    public int getNumSkys(){
        return numOfSkyScrapers;
    }


   //increments the gameclock, and updates all movable objects on the map with move(),
   // also makes sure all crashes are handled, keeps track of player lives
    // also double iterates over gameobjects to check for collisions
    public void tick(MapView map) {
           if(!paused) {
               //increment world clock
               gameClock += 1;

               //iterates through nphs array and assigns the current strategy and moves each NPH
               for (int j = 0; j < nphs.size(); j++) {
                   nphs.get(j).startStrat();
                   nphs.get(j).move(map);
               }
               //Outer loop iterates over game objects
               for (int i = 0; i < gameObjects.size(); i++) {
                   GameObject mObjs = gameObjects.get(i);

                   if (mObjs instanceof MovableObjects) {

                       //if mObj is helicopter check crash cases, then move heli
                       if (mObjs instanceof Helicopter) {

                           //decrement fuel every tick
                           ((Helicopter) mObjs).changeFuelLevel(((Helicopter) mObjs).getFuelLevel() - (((Helicopter) mObjs).getFuelLossRate()));

                           //check to make sure the helicopter has not crashed
                           deathCheck(player);

                       }

                       //if mObj is a bird call the changeDir() and move()
                       if (mObjs instanceof Bird) {
                           birds.get(i).changeDir();
                           //birds.get(i).move(map);
                       }
                       //move all objects if no death is found
                       ((MovableObjects) mObjs).move(map);

                   }


                   //Inner loop iterates over game objects
                   for (int k = 0; k < gameObjects.size(); k++) {

                       // As long as thisObject isn’t thatObject
                       if (gameObjects.get(i) != gameObjects.get(k)) {

                           //if(thisObject.collidesWith(thatObject)
                           if (gameObjects.get(i).collidesWith(gameObjects.get(k))) {

                               //thisObject.handleCollision(thatObject)
                               gameObjects.get(i).handleCollision(gameObjects.get(k));
                           }

                       }

                   }


               }

               //after all moves are performed update the mapview
               mapView.update();
           }

    }



    //Checks if heli object has crashed
    public boolean deathCheck(Object mObjs){

        if(heliDeathSound == null) {
            heliDeathSound = new Sound("death sound.wav");
        }
        //crash from overwhelming damage
        if (((Helicopter) mObjs).getDamageLevel() == ((Helicopter) mObjs).getMaxDamageLevel() && playerLives >= 0) {

            System.out.println("You have crashed due to overwhelming damage, " +
                    "\n and have " + playerLives + " more lives, " +
                    "\n Restart from last Skyscraper reached.");

            //check if sound is unmuted
            if(soundToggle){
                heliDeathSound.play();
            }
            decLives();
            heliReset();
            return true;
        }
        //check if fuel = 0 , if so ,decrement playerLives and check to make sure they have more to play with, if not game over
        else if (((Helicopter) mObjs).getFuelLevel() == 0 && playerLives >= 0) {

            System.out.println("You have crashed due to lack of fuel, " +
                    "\n and have " + playerLives + " more lives,  " +
                    "\n Restart from last Skyscraper reached.");
            if(soundToggle){
                heliDeathSound.play();
            }
            decLives();
            heliReset();
            return true;
        }
        //check if fuel = 0 , if so ,decrement playerLives and check to make sure they have more to play with, if not game over
        else if ((/*(Helicopter) mObjs).getMinSpeed() ==*/ (Helicopter) mObjs).getMaximumSpeed() == 0 && playerLives >= 0) {

            System.out.println("You have can no longer mover due to overwhelming damage, " +
                    "\n and have " + playerLives + " more lives,  " +
                    "\n Restart from last Skyscraper reached.");
            if(soundToggle){
                heliDeathSound.play();
            }
            decLives();
            heliReset();
            return true;
        }
        else{
            return false;
        }
    }


    //displays in println heli and game stats
    public void display(){
        System.out.println("Lives remaining:" + playerLives + ", Tics elapsed:" + gameClock + getHeli().playerToString());
    }

    //displays all objects in the game.
    public void map(){
        for (GameObject gameObject : gameObjects) {
            System.out.println(gameObject.toString());
        }
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////\n");
    }

    // tells user what to do if they enter invalid commands.
    public void invalidInput(){
        System.out.println("Please Enter a Valid Key.");
    }



}



