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
import android.os.Build;
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
public class Location_TestCase {
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
    public void Location_mode_test() throws UiObjectNotFoundException {
          Action.UiTextScrollable("Battery Saving Mode Test");
        Action.NoticeConfirm("Battery Saving Mode Test");
        setlocation("Battery saving");

        Action.UiTextScrollable("Device Only Mode Test");
        Action.NoticeConfirm("Device Only Mode Test");
        setlocation("Device only");

        Action.UiTextScrollable("High Accuracy Mode Test");
        Action.NoticeConfirm("High Accuracy Mode Test");
        setlocation("High accuracy");

        Action.UiTextScrollable("Location Mode Off Test");
        Action.NoticeConfirm("Location Mode Off Test");
        mDevice.openQuickSettings();
        Action.Sleep(2);
        Action.UiTextSelector("Location");
        mDevice.pressBack();
        if(new UiObject(new UiSelector().resourceId("com.android.systemui:id/multi_user_avatar").packageName("com.android.systemui")).exists()){mDevice.pressBack();Action.Sleep(1);}
        Action.Sleep(1);
        Action.Pass_btn_check();
    }

    public  void setlocation(String text) throws UiObjectNotFoundException {
        mDevice.openQuickSettings();
        Action.Sleep(2);
        if(android.os.Build.VERSION.SDK_INT==23){
            Action.UidescriptionSelector("Settings");
        }else if(android.os.Build.VERSION.SDK_INT==19) {
            Action.UiTextSelector("Settings");
        }

        Action.UiTextScrollable("Location");
        Action.UiTextSelector("Mode");
        new UiObject(new UiSelector().textMatches(text)).click();
        Action.Sleep(1);
        UiObject location_consent=new UiObject(new UiSelector().text("Location consent"));
        if(location_consent.exists()){
            Action.UiTextSelector("Agree");
        }
        mDevice.pressBack();
        Action.Sleep(1);
        mDevice.pressBack();
        Action.Sleep(1);
        mDevice.pressBack();
        Action.Sleep(1);
        try {
            Action.Pass_btn_check();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
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
