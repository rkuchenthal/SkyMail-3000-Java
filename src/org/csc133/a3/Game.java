package org.csc133.a3;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;

// sets up entire game Form, and buttons
public class Game extends Form implements Runnable{

    private GameWorld gw;
    private MapView map;
    private SouthContainer bottom;


    public Game() {
        gw = new GameWorld();
        map = new MapView(gw);

        GlassCockpit cockpit = new GlassCockpit(gw);
        gw.addClock(cockpit.getClock());

        //instantiate south buttons, mapView, and Glass Cockpit
        // then add them to appropriate Container
        this.setLayout(new BorderLayout());
        bottom = new SouthContainer(gw);
        this.add(BorderLayout.SOUTH, bottom);
        this.add(BorderLayout.NORTH, cockpit);
        this.add(BorderLayout.CENTER, map);

        //create toolbar
        Toolbar toolbar = new Toolbar();
        setToolbar(toolbar);
        Toolbar.setOnTopSideMenu(true);

        //add the title
        this.setTitle("SkyMail 3000");

        //create a buffer zone in side menu
        toolbar.addComponentToSideMenu(new Label(" "));

        //create commands for side menu buttons
        ToggleSound toggleSound = new ToggleSound(gw);
        ChangeStrat changeStratCom = new ChangeStrat(gw);
        Help helpCom = new Help(gw);
        AboutInfo aboutInfoCom = new AboutInfo(gw);
        ExitCommand exitComm = new ExitCommand(gw);

        //create a buffer zone in side menu
        toolbar.addComponentToSideMenu(new Label(" "));

        //add buttons to side menu
        toolbar.addCommandToLeftSideMenu(toggleSound);
        toolbar.addCommandToLeftSideMenu(changeStratCom);
        toolbar.addCommandToLeftSideMenu(helpCom);
        toolbar.addCommandToLeftSideMenu(aboutInfoCom);
        toolbar.addCommandToLeftSideMenu(exitComm);


        //create listeners for commands
        AccCommand accCom = new AccCommand(gw);
        BrakeCommand brakeCom = new BrakeCommand(gw);
        LeftCommand leftCom = new LeftCommand(gw);
        RightCommand rightCom = new RightCommand(gw);
        ExitCommand exitCom = new ExitCommand(gw);
        PHCollision phCom = new PHCollision(gw);
        SkyCollisionCommand skyCom = new SkyCollisionCommand(gw);
        BlimpCollision blimpCom = new BlimpCollision(gw);
        BirdCollision birdCom = new BirdCollision(gw);

        //add keys to all listeners
        addKeyListener('a',accCom);
        addKeyListener('b',brakeCom);
        addKeyListener('l',leftCom);
        addKeyListener('r',rightCom);
        addKeyListener('x', exitCom);
        addKeyListener('n', phCom);
        addKeyListener('s', skyCom);
        addKeyListener('e', blimpCom);
        addKeyListener('g', birdCom);

        //show the form
        show();
        //create UI Timer to move objects
        UITimer mtimer = new UITimer(this);
        mtimer.schedule(30,true,this);

        //bgSound = new BGSound("bgMusic.wav");
        //play bg music
        //bgSound.play();

        //call init with passing in mapView
        gw.init(map);


    }

    public void run(){
        // tick every time the UiTimer hits set number
        gw.tick(map);
    }



}