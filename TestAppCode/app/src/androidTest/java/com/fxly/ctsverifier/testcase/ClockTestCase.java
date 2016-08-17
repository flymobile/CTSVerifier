package com.fxly.ctsverifier.testcase;

/**
 * Created by Lambert Liu on 2016-07-08.
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
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
public class ClockTestCase {
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
     String alarms_list[]={"Show Alarms Test","Set Alarm Test","Start Alarm Test","Full Alarm Test","Set Timer Test","Start Timer Test","Start Timer With UI Test"};
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
    public void Clock_Alams_TestCase() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Alarms and Timers Tests");
        Action.NoticeConfirm("Alarms and Timers Tests");
//        UiObject passbtn=new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button").description("Pass"));
        for(int i=0;i<alarms_list.length;i++){
            new UiObject(new UiSelector().text(alarms_list[i])).clickAndWaitForNewWindow();
            Action.Sleep(2);
                System.out.println("Start test "+alarms_list[i]);

//            if(passbtn.isEnabled()){
//                passbtn.clickAndWaitForNewWindow();
//                Action.Sleep(2);
//            }
            Action.Pass_btn_check();

        }
//        UiDevice.getInstance().pressBack();
//        UiObject passbtn=new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button").description("Pass"));
//        if(passbtn.isEnabled()){
//            passbtn.clickAndWaitForNewWindow();
//            Action.Sleep(2);
//        }
        Action.Pass_btn_check();
    }




//    @Test
//    public void Case3_Clock_item_test() throws UiObjectNotFoundException {
//        Action.UiTextScrollable("Alarms and Timers Tests");
//
//    }


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
