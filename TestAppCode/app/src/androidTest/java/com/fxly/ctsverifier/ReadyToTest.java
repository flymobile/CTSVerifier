package com.fxly.ctsverifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Lambert Liu on 2016-07-15.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ReadyToTest {

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
    public void Ready() throws UiObjectNotFoundException {
        if(android.os.Build.VERSION.SDK_INT==24){
            //Android 7.0 平台
        }else if(android.os.Build.VERSION.SDK_INT==23){
            //Android 6.0 平台
            UiObject permission_message=new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/permission_message"));
            if(permission_message.exists()){
            //    new UiObject(new UiSelector().className("android.widget.CheckBox").text("Never ask again")).click();
                Action.Sleep(2);
                while (new UiObject(new UiSelector().text("Allow")).exists()){
                    new UiObject(new UiSelector().text("Allow")).click();
                    Action.Sleep(2);
                    if(!new UiObject(new UiSelector().text("Allow")).exists()){break;}
                }
            }

        }else if(android.os.Build.VERSION.SDK_INT==22||android.os.Build.VERSION.SDK_INT==21){
            //Android 5.0或5.1 平台
        }else if(android.os.Build.VERSION.SDK_INT<=20){
            //Android 4.4 以下平台
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
