package com.fxly.ctsverifier;


import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Lambert Liu on 2016-07-11.
 */
public class UnLocked {
    private UiDevice mDevice;


    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();


    }


    @Test
    public void unlocked_screen() throws UiObjectNotFoundException, RemoteException {
        if(!mDevice.isScreenOn()){
            //UiDevice.getInstance().sleep();
            mDevice.wakeUp();
            if(android.os.Build.VERSION.SDK_INT>=21||android.os.Build.VERSION.SDK_INT<=23){
                // Unlock
                UiObject screen_is_locked=    new UiObject(new UiSelector().description("Unlock"));
                while (screen_is_locked.exists()){
                    int x= screen_is_locked.getBounds().centerX();
                    int y= screen_is_locked.getBounds().centerY();
                    mDevice.drag(x,y,x,y-400,50);
                    if(!screen_is_locked.exists()){break;}
                }
            }else if(android.os.Build.VERSION.SDK_INT<=20){
                UiObject locked=new UiObject(new UiSelector().description("Slide area."));
                int x= locked.getBounds().centerX();
                int y= locked.getBounds().centerY();
                while (locked.exists()){
                    mDevice.drag(x,y,x+200,y,50);
                    if(!locked.exists()){break;}
                }
            }
            else {
                Assert.fail("You launcher is not support");}
        }else {

        }
    }




}

