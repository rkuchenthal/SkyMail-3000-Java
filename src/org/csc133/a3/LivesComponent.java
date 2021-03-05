package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class LivesComponent extends Component {

    //array for reg digits
    Image[] digitImages = new Image[10];
    //array for  display
    Image[] clockDigits = new Image[numDigitsShowing];

    private int lives;
    private int ledColor;
    private static final int numDigitsShowing = 1;

    private GameWorld gw;
    //scale factor for resizing LED's
    private static final float scaleFact = (float) 0.6;

    public LivesComponent(GameWorld gw) {
        this.gw =gw;

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

        //add all digits images to clockDigit array
        for (int i = 0; i < numDigitsShowing; i++)
            clockDigits[i] = digitImages[0];

        //initialize digit colors
        ledColor = ColorUtil.MAGENTA;
    }

    private void setCurrentLives() {
        //update lives #
        lives = gw.getLives();

        //insert digits pics into clock []
        clockDigits[0] = digitImages[lives %10];

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
        setCurrentLives();
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

        int displayX = getX() ;
        int displayY = getY() ;

        //component background
        g.setColor( ColorUtil.BLACK );
        g.fillRect( getX(),getY(),displayClockWidth,displayDigitHeight );

        //digit green background
        g.setColor( ledColor );
        g.fillRect( displayX, displayY, displayClockWidth, displayDigitHeight );

        //manually draw digits
        g.drawImage( clockDigits[0], displayX + 0 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight );

    }

    public void resetLives() {
        // set all digits to zero
        clockDigits[0] = digitImages[0];

    }

    public void startLives() {
        start();
    }

    public void stopLives() {
        stop();
    }
}
