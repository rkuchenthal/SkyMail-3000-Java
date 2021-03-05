package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.table.TableLayout;


public class GlassCockpit extends Container {
    private GameClockComponent clock;

    public GlassCockpit(GameWorld gw) {

        this.setLayout(new TableLayout(2,6));

        //create array for labels
        Label[] labels = new Label[6];

        //add labels to array
        labels[0]= new Label("Time");
        labels[1]= new Label("Fuel");
        labels[2]= new Label("Dmg");
        labels[3]= new Label("Lives");
        labels[4]= new Label("Last");
        labels[5]= new Label("Head");

        // set bg color and transparency
        this.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        this.getAllStyles().setBgTransparency(255);

        //set sizes for text
        for(int i = 0; i < labels.length; i++){
            //allow things to change sizes
            labels[i].setAutoSizeMode(true);
            //change sizes
            labels[i].setMaxAutoSize((float)1.3);
            labels[i].setMinAutoSize((float)1.3);

        }

        //add labels to component
        this.add(labels[0])
                .add(labels[1])
                .add(labels[2])
                .add(labels[3])
                .add(labels[4])
                .add(labels[5]);

        clock = new GameClockComponent(gw);
        //add all displays
        this.add(clock )
            .add(new FuelComponent(gw))
            .add(new DamageComponent(gw))
            .add(new LivesComponent(gw))
            .add(new LastComponent(gw))
            .add(new HeadingComponent(gw));

    }

    public void update(){
        repaint();

    }

    public GameClockComponent getClock(){
        return clock;
    }
}







