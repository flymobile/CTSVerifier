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
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import com.fxly.ctsverifier.Action;
import com.fxly.ctsverifier.TextStrings;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Security_TestCase {
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
    public void KeyChain_Storage_Test() throws UiObjectNotFoundException {
        Action.UiTextScrollable("KeyChain Storage Test");
        Action.NoticeConfirm("KeyChain Storage Test");
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Skip");
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Skip");
        Action.Pass_btn_check();
    }

    @Test
    public void Keyguard_Password_Verification() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Keyguard Password Verification");
        Action.NoticeConfirm("Keyguard Password Verification");
        Action.Pass_btn_check();
    }

    @Test
    public void Lock_Bound_Keys_Test() throws UiObjectNotFoundException {

        mDevice.openQuickSettings();
        Action.Sleep(2);
        if(android.os.Build.VERSION.SDK_INT==23){
            Action.UidescriptionSelector("Settings");
        }else if(android.os.Build.VERSION.SDK_INT==19) {
            Action.UiTextSelector("Settings");
        }
        Action.UiTextScrollable("Security");
        Action.UiTextSelector("Screen lock");
        Action.UiTextSelector("PIN");
        UiObject password_are=new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));
        password_are.setText("1234");
        Action.Sleep(2);
        Action.UiTextSelector("Continue");
        password_are.setText("1234");
        Action.Sleep(2);
        Action.UiTextSelector("OK");
        Action.UiTextSelector("Done");
        mDevice.pressBack();
        Action.Sleep(2);
        mDevice.pressBack();
        Action.Sleep(2);
        Action.UiTextScrollable("Lock Bound Keys Test");
        Action.NoticeConfirm("Lock Bound Keys Test");
        Action.UiTextSelector("Start Test");
        Action.Sleep(5);
        if(new UiObject(new UiSelector().text("").text("")).exists()){

        }else {
            Action.Sleep(5);
        }
        password_are.setText("1234");Action.Sleep(1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_ENTER);
        Action.Sleep(2);
        Action.Pass_btn_check();
        mDevice.openQuickSettings();
        Action.Sleep(2);
        if(android.os.Build.VERSION.SDK_INT==23){
            Action.UidescriptionSelector("Settings");
        }else if(android.os.Build.VERSION.SDK_INT==19) {
            Action.UiTextSelector("Settings");
        }
        Action.UiTextScrollable("Security");
        Action.UiTextSelector("Screen lock");
        password_are.setText("1234");
        mDevice.pressKeyCode(KeyEvent.KEYCODE_ENTER);
        Action.UiTextSelector("Swipe");
        Action.UiTextSelector("Yes, remove");
        mDevice.pressBack();
        Action.Sleep(2);
        mDevice.pressBack();
        Action.Sleep(2);
    }
    @Test
    public void SUID_File_Scanner() throws UiObjectNotFoundException {
        Action.UiTextScrollable("SUID File Scanner");
        Action.NoticeConfirm("SUID File Scanner");
        Action.Sleep(20);
        while (new UiObject(new UiSelector().textContains("Scan")).exists()){
            Action.Sleep(2);
            break;
        }
        Action.UiTextSelector("OK");
        Action.Pass_btn_check();
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
