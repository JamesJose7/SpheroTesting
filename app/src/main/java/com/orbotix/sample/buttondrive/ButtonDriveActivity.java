package com.orbotix.sample.buttondrive;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.Sphero;
import orbotix.view.connection.SpheroConnectionView;

/** Activity for controlling the Sphero with five control buttons. */
public class ButtonDriveActivity extends Activity {

    private Sphero mRobot;

    private Handler mHandler = new Handler();

    private float mSpeed = 0;

    /** The Sphero Connection View */
    private SpheroConnectionView mSpheroConnectionView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        /** Sphero **/
        mSpheroConnectionView = (SpheroConnectionView) findViewById(R.id.sphero_connection_view);
        mSpheroConnectionView.addConnectionListener(new ConnectionListener() {

            @Override
            public void onConnected(Robot robot) {
                //SpheroConnectionView is made invisible on connect by default
                mRobot = (Sphero) robot;
            }

            @Override
            public void onConnectionFailed(Robot sphero) {
                // let the SpheroConnectionView handle or hide it and do something here...
            }

            @Override
            public void onDisconnected(Robot sphero) {
                mSpheroConnectionView.startDiscovery();
            }
        });
    }


    /** Called when the user comes back to this app */
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list of Spheros
        mSpheroConnectionView.startDiscovery();
    }


    /** Called when the user presses the back or home button */
    @Override
    protected void onPause() {
        super.onPause();
        // Disconnect Robot properly
        RobotProvider.getDefaultProvider().disconnectControlledRobots();
    }

    /**
     * When the user clicks "STOP", stop the Robot.
     *
     * @param v The View that had been clicked
     */
    public void onStopClick(View v) {
        if (mRobot != null) {
            // Stop robot
            mRobot.stop();
            mRobot.setColor(0, 0, 0);
        }
    }

    public void pathTriangle() {

        mHandler.post(new Runnable() {
            float[] angles = {0f, 120f, 240f};

            @Override
            public void run() {
                for (float angle : angles) {
                    changeSpheroColor();
                    mRobot.drive(angle, mSpeed);
                    mRobot.stop();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void pathTest() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                changeSpheroColor();
                mRobot.drive(0f, mSpeed);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mRobot.stop();
            }
        });

    }

    private void pathCircle() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (float i = 0f; i <= 360f; i += 10) {
                    changeSpheroColor();
                    mRobot.drive(i, 1f);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mRobot.stop();
            }
        });

    }


    public void pathSquare() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                float[] angles = {0f, 90f, 180f, 270f};

                for (float angle : angles) {
                    changeSpheroColor();
                    mRobot.drive(angle, mSpeed);
                    mRobot.stop();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void changeSpheroColor() {
        Random randomGenerator = new Random();

        mRobot.setColor(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255));
    }

    /**
     * When the user clicks a control button, roll the Robot in that direction
     *
     * @param v The View that had been clicked
     */
    public void onControlClick(View v) throws InterruptedException {
        // Find the heading, based on which button was clicked
        //final float heading;
        switch (v.getId()) {

            case R.id.squareButton:
                pathSquare();
                break;

            case R.id.circleButton:
                pathCircle();
                break;

            case R.id.pathing:
                pathTest();
                break;

            case R.id.triangle:
                pathTriangle();
                break;

            //D-Pad
            case R.id.stop_button:
                mRobot.stop();
                break;

            case R.id.upButton:
                mRobot.drive(0f, mSpeed);
                break;

            case R.id.rightButton:
                mRobot.drive(90f, mSpeed);
                break;

            case R.id.downButton:
                mRobot.drive(180f, mSpeed);
                break;

            case R.id.leftButton:
                mRobot.drive(270f, mSpeed);
                break;

            default:
                // heading = 0f;
                // mRobot.setColor(240, 15, 219);
                break;
        }
    }

    public void onSpeedClick(View view) {
        switch (view.getId()) {
            case R.id.speed100:
                mSpeed = 100f;
                break;
            case R.id.speed90:
                mSpeed = 90f;
                break;
            case R.id.speed80:
                mSpeed = 80f;
                break;
            case R.id.speed70:
                mSpeed = 70f;
                break;
            case R.id.speed60:
                mSpeed = 60f;
                break;
            case R.id.speed50:
                mSpeed = 50f;
                break;
            case R.id.speed40:
                mSpeed = 40f;
                break;
            case R.id.speed30:
                mSpeed = 30f;
                break;
            case R.id.speed20:
                mSpeed = 20f;
                break;
            case R.id.speed10:
                mSpeed = 10f;
                break;
            default:
                mSpeed = 0;
                break;
        }
    }

}
