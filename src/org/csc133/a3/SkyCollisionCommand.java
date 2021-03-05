package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SkyCollisionCommand extends Command {
    private GameWorld g;

    public SkyCollisionCommand(GameWorld gw){
        super("");
        g = gw;
    }

    public void actionPerformed (ActionEvent e){

        //g.checkpoint();

    }
}
