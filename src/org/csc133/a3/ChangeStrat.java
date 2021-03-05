package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

import static java.lang.Integer.parseInt;

public class ChangeStrat extends Command {

    private GameWorld g;

    public ChangeStrat(GameWorld gw){
        super("Change Strategies");
        g = gw;
    }

    public void actionPerformed (ActionEvent e){

        g.pauseGame();
        TextField inputStrat = new TextField();
        Command cCancel = new Command("Cancel");
        Command cOk = new Command("Ok");

        Dialog.show("Strategy Options", "1 = Agressive Strat\n2 = Competitve Strat", "Ok", null);
        Command c = Dialog.show("Enter Strategy:", inputStrat, cOk,cCancel);

        int stratNum = parseInt(inputStrat.getText());

       try{
           if(c== cOk && (stratNum ==1 || stratNum ==2)){
               Dialog.show(" ", "Strategy Accepted", "Ok", null);

               g.setNPHStrategy(stratNum);
               g.unPause();

           }
       }catch (IndexOutOfBoundsException | NumberFormatException ex){ Dialog.show("Error", "Insert a Positive Number", "Ok", null);}

    }

}
