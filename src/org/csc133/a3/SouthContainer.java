package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.GridLayout;

import java.io.IOException;


public class SouthContainer extends Container {

    public SouthContainer(GameWorld gw){
        //this.setLayout(new BorderLayout());

        try {
            //import arrow images
            Image left = Image.createImage("/left-arrow-100.png");
            Image right = Image.createImage("/right-arrow-100.png");
            Image acc = Image.createImage("/up-arrow-100.png");
            Image brake = Image.createImage("/down-arrow-100.png");

            //adding all buttons to bottomContainer
            Button bleft = new Button(left);
            Button bright= new Button(right);
            Button bacc = new Button(acc);
            Button bbrake = new Button(brake);

            this.add(bleft)
                    .add(bright)
                    .add(bacc)
                    .add(bbrake);

           //format buttons
            this.getAllStyles().setBgTransparency(255);
            this.getAllStyles().setBgColor(ColorUtil.BLACK);
            this.setLayout(new GridLayout(1, 4));

            //adding padding to buttons
            this.getAllStyles().setPadding(0, 0, 5, 5);

            //initialize all commands for buttons and keys
            AccCommand accCom = new AccCommand(gw);
            BrakeCommand brakeCom = new BrakeCommand(gw);
            LeftCommand leftCom = new LeftCommand(gw);
            RightCommand rightCom = new RightCommand(gw);

            //add commads to key listeners
            bacc.setCommand( accCom );
            bbrake.setCommand( brakeCom );
            bleft.setCommand( leftCom );
            bright.setCommand( rightCom );


        } catch (IOException e) {e.printStackTrace();}






    }
}
