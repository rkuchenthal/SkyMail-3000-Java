package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PHCollision extends Command {
    private GameWorld g;

    public PHCollision(GameWorld gw){
        super("");
        g = gw;
    }

    public void actionPerformed (ActionEvent e){
        g.PHc();

    }
}
