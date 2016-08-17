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
import android.support.test.uiautomator.UiSelector;
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
public class Streaming_TestCase {
    private static final int LAUNCH_TIMEOUT = 5000;
    String Streaming_Video_Quality_Verifier[]={"H263 Video, AMR Audio","MPEG4 SP Video, AAC Audio","H264 Base Video, AAC Audio"
    ,"H263 Video, AMR Audio","MPEG4 SP Video, AAC Audio","H264 Base Video, AAC Audio"
    };
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
    public void Streaming_Video_Quality_Verifier() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Streaming Video Quality Verifier");
        Action.NoticeConfirm("Streaming Video Quality Verifier");
        UiObject notice_text=new UiObject(new UiSelector().text("Streaming Video Quality Verifier"));
        while (!notice_text.exists()){
            Action.Sleep(2);
            if(notice_text.exists()){
                new UiObject(new UiSelector().text("OK")).click();
                Action.Sleep(2);
                break;}
            else if(new UiObject(new UiSelector().text("RTSP")).exists()){
                break;

            }
        }

        for(int i=0;i<Streaming_Video_Quality_Verifier.length;i++){
            if(new UiObject(new UiSelector().text(Streaming_Video_Quality_Verifier[i])).exists()){
                new UiObject(new UiSelector().text(Streaming_Video_Quality_Verifier[i])).clickAndWaitForNewWindow();
                Action.Sleep(30);
                if(new UiObject(new UiSelector().text("Test Failed")).exists()){
                    Action.UiTextSelector("Close");
                }else {
                    UiObject passbtn=new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button").description("Pass"));
                    while (!passbtn.isEnabled()){
                        Action.Sleep(2);
                        if(passbtn.isEnabled()){break;}
                        else if(new UiObject(new UiSelector().text("RTSP")).exists()) {
                            break;
                        }
                        else if(new UiObject(new UiSelector().text("Test Failed")).exists()){
                            Action.UiTextSelector("Close");  break;
                        }
                    }
                    passbtn.clickAndWaitForNewWindow();
                    Action.Sleep(2);
                }
            }
        }
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
