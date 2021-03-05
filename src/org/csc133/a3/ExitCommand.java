package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;


public class ExitCommand extends Command {

    private GameWorld g;

    public ExitCommand (GameWorld gw){
        super("Exit");
            g = gw;

    }

    public void actionPerformed (ActionEvent e){

        Command cOk = new Command("Ok");
        Command cCancel = new Command("Cancel");

        Command c = Dialog.show("Exit Game", "Are you sure you want to exit the game?", cOk, cCancel);

        if(c== cOk){
            g.exit();
        }
        else{
            return;
        }

    }


}
