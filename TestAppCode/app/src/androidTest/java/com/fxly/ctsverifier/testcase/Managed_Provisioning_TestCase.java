package com.fxly.ctsverifier.testcase;

/**
 * Created by Lambert Liu on 2016-07-08.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.fxly.ctsverifier.Action;
import com.fxly.ctsverifier.TextStrings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Managed_Provisioning_TestCase {
    private static final int LAUNCH_TIMEOUT = 5000;
    String Device_Owner_list[] = {"Device administrator settings", "Disallow configuring WiFi", "Disallow configuring VPN", "Disallow configuring Bluetooth", "Disallow USB file transfer"
            , "Disable status bar", "Disable keyguard", "Remove device owner"
    };
    String WIFI_Configuration_lockdown[] = {"Unlocked config is modifiable in Settings", "Locked config is not modifiable in Settings", "Locked config can be connected to", "Unlocked config can be forgotten in Settings"};
    String Device_Own_Check[] = {"Check device owner", "Permissions lockdown"};
    private UiDevice mDevice;

    //    Permissions lockdown  Check device owner
//    "WiFi configuration lockdown",
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
    public void BYOD_Managed_Provisioning(){
        Action.UiTextScrollable("BYOD Managed Provisioning");
        Action.NoticeConfirm("BYOD Managed Provisioning");


    }
    @Test
    public void Device_Owner_Provisioning() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Device Owner Provisioning");
        Action.NoticeConfirm("Device Owner Provisioning");
        Action.UiTextSelector("Device owner negative test");
        Action.Pass_btn_check();
        Action.Sleep(5);
        Action.Pass_btn_check();
    }

    @Test
    public void Device_Owner_Tests() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Device Owner Tests");
        Action.NoticeConfirm("Device Owner Tests");

        //点击部分
        for (int a = 0; a < Device_Own_Check.length; a++) {
//            Action.UiTextScrollable(Device_Own_Check[a]);
            Action.UiTextScrollableListView(Device_Own_Check[a]);
            Action.Sleep(2);
        }
        //子项目部分
        Action.UiTextScrollableListView("WiFi configuration lockdown");
        Action.NoticeConfirm("WiFi configuration lockdown");
        Action.Sleep(2);
        mDevice.pressBack();
        Action.Sleep(2);
        for (int b = 0; b < WIFI_Configuration_lockdown.length; b++) {
            Action.UiTextScrollableListView(WIFI_Configuration_lockdown[b]);
            Action.NoticeConfirm(WIFI_Configuration_lockdown[b]);
            Action.Pass_btn_check();
        }

    }

//    UiScrollable noteList = new UiScrollable( new UiSelector().className("android.widget.ListView"));
//    UiObject note = null;
//    note = noteList.getChildByText(new UiSelector().className("android.widget.TextView"), "Note1", true);
//    assertThat(note,notNullValue());
//    note.longClick();
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
