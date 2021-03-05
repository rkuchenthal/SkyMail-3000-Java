package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ToggleSound extends Command {
    private GameWorld g;

    public ToggleSound(GameWorld gw){
        super("Toggle Sound");
        g=gw;

    }

    public void actionPerformed(ActionEvent e){

        g.setSoundToggle();


    }
}
