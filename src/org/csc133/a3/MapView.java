package org.csc133.a3;

import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class MapView extends Component {

    private GameWorld gw;

    public MapView(GameWorld gw){
        this.gw = gw;
    }



    public void paint(Graphics g){
        super.paint(g);

        //initialize Point to get x and y
        Point p = new Point(this.getX(), this.getY());

        //draw all objects in game
        for(int i =0; i < gw.getObjectList().size(); i++){
            gw.getObjectList().get(i).draw(g,p);
        }

    }
   // @Overide
    public void update(){
        repaint();
    }
}
