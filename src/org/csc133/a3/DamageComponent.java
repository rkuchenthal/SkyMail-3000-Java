package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class DamageComponent extends Component {
    //array for reg digits
    Image[] digitImages = new Image[10];
    //array for clock display
    Image[] clockDigits = new Image[numDigitsShowing];


    private int percentage;
    private int ledColor;
    private boolean greenFlag;
    private boolean yellowFlag;
    private boolean redFlag;
    private static final int numDigitsShowing = 2;

    private GameWorld gw;
    //scale factor for resizing LED's
    private static final float scaleFact = (float) 0.6;

    public DamageComponent(GameWorld gw) {
        try {

            digitImages[0] = Image.createImage("/LED_digit_0.png");
            digitImages[1] = Image.createImage("/LED_digit_1.png");
            digitImages[2] = Image.createImage("/LED_digit_2.png");
            digitImages[3] = Image.createImage("/LED_digit_3.png");
            digitImages[4] = Image.createImage("/LED_digit_4.png");
            digitImages[5] = Image.createImage("/LED_digit_5.png");
            digitImages[6] = Image.createImage("/LED_digit_6.png");
            digitImages[7] = Image.createImage("/LED_digit_7.png");
            digitImages[8] = Image.createImage("/LED_digit_8.png");
            digitImages[9] = Image.createImage("/LED_digit_9.png");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //load digit.imgs into clock digit array
        for (int i = 0; i < numDigitsShowing; i++)
            clockDigits[i] = digitImages[0];

        this.gw= gw;
        ledColor = ColorUtil.GREEN;

    }

    private void setCurrentDmg() {

       percentage= gw.getHeliDmgPerc();

        //set color flags for different dmg %
        if(percentage > 50 && percentage <= 85){
            greenFlag = false;
            yellowFlag = true;
            redFlag = false;
        }
        else if(percentage > 85){
            greenFlag = false;
            yellowFlag = false;
            redFlag = true;
        }
        else if(percentage >= 99){
            gw.heliReset();
        }
        else{
            greenFlag = true;
            yellowFlag = false;
            redFlag = false;
        }

        //check to see if percentage >= 100%
        if(percentage >= 100){
            //reset copter position, gas, dmg, - live
            //gw.heliReset();
            percentage = 0;
        }

        //insert digits pics into clock []
        clockDigits[0] = digitImages[percentage / 10];
        clockDigits[1] = digitImages[percentage % 10];


    }

    public void setLedColor(int ledColor) {
        this.ledColor = ledColor;
    }

    public void start() {
        getComponentForm().registerAnimated(this);
    }

    public void stop() {
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut() {
        this.start();
    }

    public boolean animate() {

        setCurrentDmg();
        return true;
    }

    protected Dimension calcPreferredSize() {
        return new Dimension(digitImages[0].getWidth() * numDigitsShowing, digitImages[0].getHeight());
    }


    public void paint(Graphics g) {
        super.paint(g);

        int digitWidth = clockDigits[0].getWidth();
        int digitHeight = clockDigits[0].getHeight();
        int clockWidth = numDigitsShowing * digitWidth;


        int displayDigitWidth = (int) (scaleFact * digitWidth);
        int displayDigitHeight = (int) (scaleFact * digitHeight);
        int displayClockWidth = (displayDigitWidth * numDigitsShowing)  ;

        int displayX = getX();
        int displayY = getY();

        //component background
        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(),getY(),displayClockWidth,displayDigitHeight);

        //check which flag is set to determine dmg digit colors
        if(yellowFlag){
            ledColor = ColorUtil.YELLOW;
        }
        else if(redFlag){
            ledColor = ColorUtil.rgb(255,0,0);
        }
        else{
            ledColor = ColorUtil.GREEN;
        }

        //digit green background
        g.setColor(ledColor);
        g.fillRect(displayX, displayY, displayClockWidth, displayDigitHeight);

        //manually draw digits
        g.drawImage(clockDigits[0], displayX + 0 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        g.drawImage(clockDigits[1], displayX + 1 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);

    }

    public void resetDmg() {
        // set all digits to zero

        clockDigits[0] = digitImages[0];
        clockDigits[1] = digitImages[0];

    }

    public void startDmg() {
        start();
    }

    public void stopDmg() {
        stop();
    }
}
