package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameClockComponent extends Component {

    //array for digits with dot
    Image[] dotNumbers = new Image[10];
    //array for reg digits
    Image[] digitImages = new Image[10];
    //array for clock display
    Image[] clockDigits = new Image[numDigitsShowing];
    Image colonImage;
    Image dotNumber;
    private long startTimeMillis;
    private boolean dangerTime;
    //save time for pause()
    private float elapsedTime;
    private int ledColor;
    private int tenthsledColor;
    private static int MS_COLON_IDX=2;
    private static final int numDigitsShowing=5;
    private long pauseStartTime;


    private GameWorld gw;

    public GameClockComponent(GameWorld g) {
        try {
            dotNumbers[0] = Image.createImage("/LED_digit_0_with_dot.png");
            dotNumbers[1] = Image.createImage("/LED_digit_1_with_dot.png");
            dotNumbers[2] = Image.createImage("/LED_digit_2_with_dot.png");
            dotNumbers[3] = Image.createImage("/LED_digit_3_with_dot.png");
            dotNumbers[4] = Image.createImage("/LED_digit_4_with_dot.png");
            dotNumbers[5] = Image.createImage("/LED_digit_5_with_dot.png");
            dotNumbers[6] = Image.createImage("/LED_digit_6_with_dot.png");
            dotNumbers[7] = Image.createImage("/LED_digit_7_with_dot.png");
            dotNumbers[8] = Image.createImage("/LED_digit_8_with_dot.png");
            dotNumbers[9] = Image.createImage("/LED_digit_9_with_dot.png");

            colonImage = Image.createImage("/LED_colon.png");

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

        } catch (IOException e) {e.printStackTrace();}

        gw =g;
        //add all digits to clock digit array
        for(int i=0; i<numDigitsShowing; i++)
            clockDigits[i] = digitImages[0];

        //add semi colon to clock array
        clockDigits[MS_COLON_IDX]=colonImage;

        ledColor = ColorUtil.CYAN;
        tenthsledColor = ColorUtil.BLUE;

        //initialize timer for millSec
        startTimeMillis = System.currentTimeMillis();
        dotNumber = dotNumbers[0];
    }

    private void setCurrentTime(){

            int tenths = (int) (TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis() - startTimeMillis) / 100);
            int min = (int) (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - startTimeMillis) % 60);
            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeMillis) % 60);

            //set time to red after 10 min
            if (min == 10) {
                //call flag to tell its over 10 and turn red
                dangerTime = true;

            }

            //insert digits pics into clock []
            clockDigits[0] = digitImages[min / 10];
            clockDigits[1] = digitImages[min % 10];
            clockDigits[3] = digitImages[sec / 10];
            dotNumber = dotNumbers[sec % 10];
            clockDigits[4] = digitImages[tenths % 10];


    }
    public void pauseTime(){
        //record current time at pause
        pauseStartTime = System.currentTimeMillis();

        //unanimate gameclock
        getComponentForm().deregisterAnimated(this);
    }

    public void unpauseTime(){
        startTimeMillis += System.currentTimeMillis() - pauseStartTime;

        //re register as animated
        getComponentForm().registerAnimated(this);
    }


    public void setLedColor(int ledColor){
        this.ledColor = ledColor;
    }

    public void start(){
        getComponentForm().registerAnimated(this);
    }

    public void stop(){
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut(){
        this.start();
    }

    public boolean animate(){
       elapsedTime = System.currentTimeMillis() - startTimeMillis;

        setCurrentTime();
        return true;
    }


    protected Dimension calcPreferredSize() {
        return new Dimension(colonImage.getWidth( )*numDigitsShowing, colonImage.getHeight());
    }


    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = clockDigits[0].getWidth();
        int digitHeight = clockDigits[0].getHeight();
        int clockWidth = numDigitsShowing*digitWidth;

        float scaleFact = (float) 0.6;

        int displayDigitWidth = (int)(scaleFact*digitWidth);
        int displayDigitHeight = (int)(scaleFact*digitHeight);
        int displayClockWidth = (displayDigitWidth*numDigitsShowing);

        int displayX = getX() ;
        int displayY = getY() ;

        //component background
        g.setColor(ColorUtil.BLACK);
        g.fillRect( getX(), getY(), displayClockWidth, displayDigitHeight );

        //check dangerTIme to see if color needs to be changed
        if(dangerTime){
           ledColor = ColorUtil.rgb(255,0,0);
           tenthsledColor = ColorUtil.rgb(140,0,0);
        }
        else{
            ledColor = ColorUtil.CYAN;
            tenthsledColor = ColorUtil.BLUE;
        }
        //digit cyan background
        g.setColor(ledColor);
        g.fillRect(getX(),getY(),displayClockWidth,displayDigitHeight);

        //make tenths place a diff color from rest of digits
        g.setColor(tenthsledColor);
        // extend box draw distance
        g.fillRect(displayX+ (displayDigitWidth*5), displayY, displayDigitWidth , displayDigitHeight);


        //manually draw digits and colon
        g.drawImage(clockDigits[0], displayX + 0 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        g.drawImage(clockDigits[1], displayX + 1 * displayDigitWidth, displayY,displayDigitWidth , displayDigitHeight);
        g.drawImage(clockDigits[2], displayX + 2 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        g.drawImage(clockDigits[3], displayX + 3 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);

        //draw the dot digits
        g.drawImage(dotNumber, displayX + 4 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);

        //draw tenths digit
        g.drawImage(clockDigits[4], displayX + 5 * displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);


    }

    public void resetElapsedTime(){
        // set all digits to zero
        elapsedTime = 0;
        clockDigits[0] = digitImages[0];
        clockDigits[1] = digitImages[0];
        clockDigits[3] = digitImages[0];
        dotNumber =  dotNumbers[0];
        clockDigits[4] = digitImages[0];
    }

    public void startElapsedTime(){
        start();
    }

    public void stopElapsedTime(){
        stop();
    }

    public float getElapsedTime(){
        return elapsedTime;
    }

}
