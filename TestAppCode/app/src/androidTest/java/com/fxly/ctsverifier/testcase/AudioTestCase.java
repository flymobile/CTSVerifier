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
import com.fxly.ctsverifier.R;
import com.fxly.ctsverifier.TextStrings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AudioTestCase {
    private static final int LAUNCH_TIMEOUT = 5000;
    String Audio_testitem[]={"Audio Input Devices Notifications Test","Audio Input Routing Notifications Test","Audio Output Devices Notifications Test","Audio Output Routing Notifications Test"};
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
    public void Audio_Frequency_line_test() throws UiObjectNotFoundException {

        check_headset();
       if(new UiObject(new UiSelector().text("pass")).exists()){
           onView(withId(R.id.is_inset_earset)).check(matches(withText("pass")));
           enteragain("Audio Frequency Line Test");
           Action.UiTextSelector("Yes");
           Action.UiTextSelector("Loopback Plug Ready");
           Action.UiTextSelector("Test");
           Action.Sleep(5);
           int x=mDevice.getDisplayWidth();
           int y=mDevice.getDisplayHeight();
           mDevice.swipe(x/2,5*y/6,x/2,y/6,200);
           Action.Sleep(1);
           Action.Pass_btn_check();


       }else if(new UiObject(new UiSelector().text("fail")).exists()){
           Assert.assertFalse("headset no found,pls inset and to try!",true);
       }


    }
    @Test
    public void Audio_Input_Devices_Notifications_test() throws UiObjectNotFoundException {

        for(int i=0;i<Audio_testitem.length;i++){
            Assert.assertTrue(Audio_testitem[i]+" Start test",true);
            Action.UiTextScrollable(Audio_testitem[i]);
            Action.NoticeConfirm(Audio_testitem[i]);
            Action.Pass_btn_check();
        }




    }


    //Hifi Ultrasound Speaker Test
    @Test
    public void Hifi_Ultrasound_Speaker_Test() throws UiObjectNotFoundException {
        Action.UiTextSelector("Hifi Ultrasound Speaker Test");
        Action.NoticeConfirm("Hifi Ultrasound Speaker Test");
        Action.UiTextSelector("PLAY");
        Action.Sleep(2);
        Action.UiTextSelector("OK");
        Action.Sleep(2);
        if (new UiObject(new UiSelector().text("Hifi Ultrasound Speaker Test")).exists()) {
            mDevice.pressBack();
            Action.Sleep(2);
        }
        Action.Sleep(2);
        Action.Pass_btn_check();
    }


    public void check_headset() throws UiObjectNotFoundException{
        mDevice.pressHome();
        UiObject handleView = new UiObject(new UiSelector().descriptionContains("Apps"));
        handleView.clickAndWaitForNewWindow();
        Action.Sleep(2);
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();
        appViews.scrollForward();
        //通过类名和Text找到联系人应用图标，Text获取通过uiautomatorviewer
        UiObject findsettings = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), "CtsVerifier Tools");
        //点击并等待打开短信应用
        findsettings.clickAndWaitForNewWindow();
        Action.Sleep(2);

    }

    public void  enteragain(String itemtext)throws UiObjectNotFoundException{
        mDevice.pressHome();
        UiObject handleView = new UiObject(new UiSelector().descriptionContains("Apps"));
        handleView.clickAndWaitForNewWindow();
        Action.Sleep(2);
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();
        appViews.scrollForward();
        //通过类名和Text找到联系人应用图标，Text获取通过uiautomatorviewer
        UiObject findsettings = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), "CTS Verifier");
        //点击并等待打开短信应用
        findsettings.clickAndWaitForNewWindow();
        Action.Sleep(2);
        Action.UiTextScrollable(itemtext);
        Action.NoticeConfirm(itemtext);
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
