package org.csc133.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.IOException;
import java.io.InputStream;


public class BGSound implements Runnable{

    private Media m;

    public  BGSound (String fileName){

        while ( m == null){

            try{

                InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);

                m = MediaManager.createMedia( is,"audio/wav", this);

            }

            catch (IOException e) { e.printStackTrace();

            }
        }

    }

    public void pause(){
        m.pause();
    }
    public void play(){
        m.play();
    }

    public void run(){
        m.setTime(0);
        m.play();
    }

}

