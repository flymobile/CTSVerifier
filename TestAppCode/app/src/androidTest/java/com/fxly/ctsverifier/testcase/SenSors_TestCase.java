package com.fxly.ctsverifier.testcase;

/**
 * Created by Lambert Liu on 2016-07-08.
 */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import com.fxly.ctsverifier.Action;
import com.fxly.ctsverifier.TextStrings;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SenSors_TestCase {
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(TextStrings.PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(TextStrings.PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }



    @Test
    public void Accelerometer_Measurement_Test() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Accelerometer Measurement Tests");
  //      Action.NoticeConfirm("Accelerometer Measurement Tests");
        runbefor();
        while (new UiObject(new UiSelector().text("Next")).exists()){
            while (new UiObject(new UiSelector().text("Next")).isEnabled()){
                new UiObject(new UiSelector().text("Next")).click();
                Action.Sleep(5);
                if(new UiObject(new UiSelector().text("Wireless & networks")).exists()){
                    break;
                }
            }
            if(new UiObject(new UiSelector().text("Wireless & networks")).exists()){
                break;
            }
        }

        runafter();
        pass_btn();
    }

    @Test
    public void CTS_Sensor_Batching_Tests() throws RemoteException, UiObjectNotFoundException {
        Action.UiTextScrollable("CTS Sensor Batching Tests");
        runbefor();
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");

        while (!mDevice.isScreenOn()){
            Action.Sleep(5);
            if(mDevice.isScreenOn()){
                break;
            }
        }
        Unlock_Screen();
        Action.UiTextSelector("Next");
        runafter();
        pass_btn();
    }

    @Test
    public void CTS_Sensor_Integration_Tests() throws RemoteException, UiObjectNotFoundException{
        Action.UiTextScrollable("CTS Sensor Integration Tests");
        runbefor();
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");

        while (!mDevice.isScreenOn()){
            Action.Sleep(5);
            if(mDevice.isScreenOn()){
                break;
            }
        }
        Unlock_Screen();
        Action.UiTextSelector("Next");
        runafter();
        pass_btn();
    }

    @Test
    public void CTS_Sensor_Test() throws RemoteException, UiObjectNotFoundException{
        Action.UiTextScrollable("CTS Sensor Test");
        runbefor();
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");
        while (!mDevice.isScreenOn()){
            Action.Sleep(5);
            if(mDevice.isScreenOn()){
                break;
            }
        }
        Unlock_Screen();
        Action.UiTextSelector("Next");
        runafter();
        pass_btn();
    }

    @Test
    public void CTS_Single_Sensor_Test() throws RemoteException, UiObjectNotFoundException{
        Action.UiTextScrollable("CTS Single Sensor Tests");
        runbefor();
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");
        while (!mDevice.isScreenOn()){
            Action.Sleep(5);
            if(mDevice.isScreenOn()){
                break;
            }
        }
        Unlock_Screen();
        Action.UiTextSelector("Next");
        runafter();
        pass_btn();
    }

    @Test
    public void CTS_Suspend_Test() throws RemoteException, UiObjectNotFoundException{
        Action.UiTextScrollable("Device Suspend Tests");
        runbefor();
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");
        while (new UiObject(new UiSelector().text("Next")).exists()){
            while (new UiObject(new UiSelector().text("Next")).isEnabled()){
                new UiObject(new UiSelector().text("Next")).click();
                Action.Sleep(5);
                if(new UiObject(new UiSelector().text("Wireless & networks")).exists()){
                    break;
                }
            }
            if(new UiObject(new UiSelector().text("Wireless & networks")).exists()){
                break;
            }
        }
        runafter();
        pass_btn();
    }


    @Test
    public void Sensor_Batching_Test() throws RemoteException, UiObjectNotFoundException{
        Action.UiTextScrollable("Sensor Batching Tests");
        runbefor();
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");
        while (new UiObject(new UiSelector().text("Next")).exists()){
            while (new UiObject(new UiSelector().text("Next")).isEnabled()){
                new UiObject(new UiSelector().text("Next")).click();
                Action.Sleep(5);
                if(new UiObject(new UiSelector().text("Wireless & networks")).exists()){
                    break;
                }
            }
            if(new UiObject(new UiSelector().text("Wireless & networks")).exists()){
                break;
            }
        }
        runafter();
        pass_btn();
    }

    @Test
    public void Significant_Motion_Tests(){
        Action.UiTextScrollable("Significant Motion Tests");
        runbefor();
        Action.UiTextSelector("Next");
        runafter();
        pass_btn();
    }

   public void runbefor(){
       Action.UiTextSelector("Next");
       Action.UiTextSelector("Airplane mode");
       mDevice.pressBack();
       Action.Sleep(1);
       Action.UiTextSelector("Next");
       Action.UiTextScrollable("Auto-rotate screen");
       mDevice.pressBack();
       Action.Sleep(1);
   }

    public void runafter(){
        Action.UiTextSelector("Airplane mode");
        mDevice.pressBack();
        Action.Sleep(1);
        Action.UiTextSelector("Next");
        Action.UiTextScrollable("Auto-rotate screen");
        mDevice.pressBack();
        Action.Sleep(1);
    }


    public void pass_btn(){
        if(new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button")).exists()){
            try {
                new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button")).click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public  void Unlock_Screen()  throws UiObjectNotFoundException, RemoteException {
        if(mDevice.isScreenOn()){
            if(android.os.Build.VERSION.SDK_INT==23){
                // Unlock
                UiObject screen_is_locked=    new UiObject(new UiSelector().description("Unlock"));
                while (screen_is_locked.exists()){
                    int x= screen_is_locked.getBounds().centerX();
                    int y= screen_is_locked.getBounds().centerY();
                    mDevice.drag(x,y,x,y-300,50);
                    break;
                }
            }else if(android.os.Build.VERSION.SDK_INT==19){
                UiObject locked=new UiObject(new UiSelector().description("Slide area."));
                int x= locked.getBounds().centerX();
                int y= locked.getBounds().centerY();
                while (locked.exists()){
                    mDevice.drag(x,y,x+200,y,50);
                    break;
                }
            }
        }
    }
    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

}
