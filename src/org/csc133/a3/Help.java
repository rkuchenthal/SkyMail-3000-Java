package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class Help extends Command {
    private GameWorld g;

    public Help(GameWorld gw){
        super("Help");
        g = gw;
    }

    public void actionPerformed (ActionEvent e){
            g.pauseGame();


            Command cCancel = new Command("Cancel");

            Command c = Dialog.show("Help Menu for Keybindings", "Accelerate: a\n Brake: b\n Turn Left: l\n " +
                                                                         "Turn Right: r ll" , cCancel);

            if(c== cCancel){
                g.unPause();
                return;
            }


    }
}
