package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;


public class AboutInfo extends Command {
    private GameWorld g;

    public AboutInfo(GameWorld gw){
        super("About Game");
        g = gw;
    }

    public void actionPerformed (ActionEvent e){

        g.pauseGame();

        Command cCancel = new Command("Cancel");

        Command c = Dialog.show("Information", "Ryan Kuchenthal \n CSC 133 \n Version: 2.0", cCancel);

        if(c== cCancel){
            g.unPause();
            return;
        }

    }
}
