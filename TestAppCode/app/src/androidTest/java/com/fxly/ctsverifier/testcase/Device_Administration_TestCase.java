package com.fxly.ctsverifier.testcase;

/**
 * Created by Lambert Liu on 2016-07-08.
 */
import org.junit.Assert;
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
public class Device_Administration_TestCase {
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
    public void Policy_Serialization_test_before() {
        Action.UiTextScrollable("Policy Serialization Test");
        Action.NoticeConfirm("Policy Serialization Test");
        Action.UiTextSelector("Generate Policy");
        Action.UiTextSelector("Apply Policy");
        Action.UiTextSelector("OK");
        mDevice.pressBack();
        Action.Sleep(2);

    }

    @Test
    public void Policy_Serialization_test() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Policy Serialization Test");
        UiObject passbtn=new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button").description("Pass"));
        if(passbtn.isEnabled()){
            passbtn.clickAndWaitForNewWindow();
            Action.Sleep(2);
        }else {
            Assert.fail();
        }
        Action.Sleep(2);

    }


    @Test
    public  void ScreenLockTest() throws UiObjectNotFoundException, RemoteException {
        Action.UiTextScrollable("Screen Lock Test");
        Action.NoticeConfirm("Screen Lock Test");
        Action.UiTextSelector("Force Lock");
        if(android.os.Build.VERSION.SDK_INT==23){
             if(new UiObject(new UiSelector().text("Activate device administrator?")).exists()){
                 new UiObject(new UiSelector().text("Activate")).click();
                 Action.Sleep(2);
             }
        }
        UnlockScreen();
        Action.UiTextSelector("OK");
        Action.Pass_btn_check();
    }

    public void UnlockScreen()  throws UiObjectNotFoundException, RemoteException {
            if(!mDevice.isScreenOn()){
                //UiDevice.getInstance().sleep();
                mDevice.wakeUp();
                if(android.os.Build.VERSION.SDK_INT==23){
                   // Unlock
                UiObject screen_is_locked=    new UiObject(new UiSelector().description("Unlock"));
                    if(screen_is_locked.exists()){
                        int x= screen_is_locked.getBounds().centerX();
                        int y= screen_is_locked.getBounds().centerY();
                        mDevice.drag(x,y,x,y-300,10);
                    }
                }else if(android.os.Build.VERSION.SDK_INT==19){
                    UiObject locked=new UiObject(new UiSelector().description("Slide area."));
                    int x= locked.getBounds().centerX();
                    int y= locked.getBounds().centerY();
                    while (locked.exists()){
                        mDevice.drag(x,y,x+200,y,10);
                        break;
                    }
                }
                else {
                    Assert.fail();}
            }else {

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
