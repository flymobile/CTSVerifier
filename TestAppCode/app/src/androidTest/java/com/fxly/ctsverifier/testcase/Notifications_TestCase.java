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
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.fxly.ctsverifier.Action;
import com.fxly.ctsverifier.TextStrings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Notifications_TestCase {
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
    public void CA_Cert_Notification_Test() throws UiObjectNotFoundException {
        UiScrollable settingItems = new UiScrollable(new UiSelector().scrollable(true));
        UiObject ca = settingItems.getChildByText(
                new UiSelector().text("CA Cert Notification Test"), "CA Cert Notification Test",
                true);

        if (ca.exists()) {
            Action.UiTextScrollable("CA Cert Notification Test");
            Action.NoticeConfirm("CA Cert Notification Test");
            UiObject passbtn = new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button"));
            if (passbtn.isEnabled()) {
                passbtn.clickAndWaitForNewWindow();
                Action.Sleep(2);
            } else {
                new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/fail_button")).click();
                Action.Sleep(2);
            }
        } else {
            Assert.assertTrue("This Device is not support,So Skipp to running", true);
        }
    }

    @Test
    public void CA_Cert_Notification_on_Boot_Test() throws UiObjectNotFoundException {
        UiScrollable settingItems = new UiScrollable(new UiSelector().scrollable(true));
        UiObject ca = settingItems.getChildByText(
                new UiSelector().text("CA Cert Notification on Boot test"), "CA Cert Notification on Boot test",
                true);

        if (ca.exists()) {
            ca.clickAndWaitForNewWindow();
            Action.Sleep(2);
            Action.NoticeConfirm("CA Cert Notification on Boot test");

            UiObject passbtn = new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button"));
            if (passbtn.isEnabled()) {
                passbtn.clickAndWaitForNewWindow();
                Action.Sleep(2);
            } else {
                new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/fail_button")).click();
                Action.Sleep(2);
            }
        } else {
            Assert.assertTrue("This Device is not support,So Skipp to running", true);
        }
    }

    @Test
    public void Notification_Listener_test() throws UiObjectNotFoundException {
//        Notification Listener Test
        UiScrollable settingItems = new UiScrollable(new UiSelector().scrollable(true));
        UiObject ca = settingItems.getChildByText(new UiSelector().text("Notification Listener Test"), "Notification Listener Test", true);

        if (ca.exists()) {
            ca.clickAndWaitForNewWindow();
            Action.Sleep(2);
            Action.NoticeConfirm("Notification Listener Test");

            UiObject setting_btn = new UiObject(new UiSelector().text("Launch Settings"));
            if (setting_btn.isEnabled()) {
                setting_btn.clickAndWaitForNewWindow();
                Action.Sleep(2);
                new UiObject(new UiSelector().text("Notification Listener for CTS Verifier")).click();
                Action.Sleep(2);

                if (android.os.Build.VERSION.SDK_INT >= 21 && android.os.Build.VERSION.SDK_INT <= 23) {
                    if (new UiObject(new UiSelector().textMatches("Allow notification access for Notification Listener for CTS Verifier?")).exists()) {
                        Action.UiTextSelector("Allow");
                        mDevice.pressBack();
                    }
                } else if (android.os.Build.VERSION.SDK_INT >= 18 && android.os.Build.VERSION.SDK_INT <= 20) {
                    if (new UiObject(new UiSelector().textMatches("Enable Notification Listener for CTS Verifier?")).exists()) {
                    }
                    Action.UiTextSelector("OK");
                    mDevice.pressBack();
                }


            } else {
                Assert.assertTrue("Notification Listener has set to eable", true);
            }

            UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            scroll.flingToEnd(2);
            Action.Sleep(60);

            if (setting_btn.isEnabled()) {

                setting_btn.clickAndWaitForNewWindow();
                Action.Sleep(2);
                new UiObject(new UiSelector().text("Notification Listener for CTS Verifier")).click();
                Action.Sleep(2);
                mDevice.pressBack();
                Action.Sleep(2);
            } else {
                Assert.assertTrue("Notification Listener has set to eable", true);
            }

            UiObject passbtn = new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button"));
            int i = 1;
            while (!passbtn.isEnabled()) {
                Action.Sleep(2);
                i++;
                if (passbtn.isEnabled()) {
                    break;
                }
                if (i > 15) {
                    break;
                }
            }
            if (passbtn.isEnabled()) {
                passbtn.clickAndWaitForNewWindow();
                Action.Sleep(2);
            } else {
                new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/fail_button")).click();
                Action.Sleep(2);
            }

        } else {
            Assert.assertTrue("This Device is not support,So Skipp to running", true);
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
