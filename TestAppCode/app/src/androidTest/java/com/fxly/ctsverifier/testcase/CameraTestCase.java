package com.fxly.ctsverifier.testcase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.fxly.ctsverifier.Action;
import com.fxly.ctsverifier.TextStrings;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Lambert Liu on 2016-07-08.
 */
public class CameraTestCase {

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    String CAMERA_FORMATS[]={"Camera 0","Camera 1"};
    String CAMERA_FORMATS_resolution[] = {"176 x 144", "240 x 320", "320 x 240", "352 x 288", "480 x 320", "480 x 368", "480 x 640", "624 x 352", "640 x 480", "720 x 480", "800 x 480", "800 x 600", "864 x 480",
            "960 x 540", "1024 x 768", "1152 x 864", "1280 x 720", "1280 x 736", "1280 x 768", "1280 x 960", "1440 x 1080", "1600 x 1200", "1670 x 1252", "1920 x 1080", "1920 x 1088", "2048 x 1536",
            "2560 x 1440","2560 x 1920"};
    String CAMERA_FORMATS_resolution_1[] = {
            "176 x 144","320 x 240","352 x 288","480 x 320","480 x 368","640 x 480","720 x 480","800 x 480",
            "800 x 600","864 x 480","960 x 540","1024 x 768","1280 x 720","1280 x 736","1280 x 768","1280 x 960","1440 x 1080",
            "1600 x 1200", "1680 x 1248", "1920 x 1080", "1920 x 1088"};
    String CAMERA_FORMATS_TYPE[]={"NV21","YV12"};
    String CAMERA_VIDEO_TYPE[]={"LOW","HIGH","QCIF","QVGA","CIF","480P","720P","1080P"};
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        //      assertThat(launcherPackage, notNullValue());
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
    public void Camera_Fov_Calibration() {
        Action.UiTextScrollable("Camera FOV Calibration");
        Action.NoticeConfirm("Camera FOV Calibration");
        Action.Sleep(2);
        while (new UiObject(new UiSelector().text("Tap to calibrate")).exists()){
            mDevice.click(mDevice.getDisplayWidth()/2,mDevice.getDisplayHeight()/2);
            Action.Sleep(5);
            Action.UiTextSelector("Done");
            Action.Sleep(2);
//            break;
            if (!new UiObject(new UiSelector().text("Tap to calibrate")).exists()) {
                break;
            }
        }


    }

    @Test
    public void Camera_Flashlight() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Camera Flashlight");
        Action.NoticeConfirm("Camera Flashlight");
        Action.Sleep(2);
        Action.UiTextSelector("Start");
        Action.UiTextSelector("On");
        Action.UiTextSelector("Next");
        Action.UiTextSelector("Off");
        Action.UiTextSelector("Next");
        if(new UiObject(new UiSelector().text("Done")).exists()){
            new UiObject(new UiSelector().text("Done")).click();
            Action.Sleep(2);
        }else {
            Action.UiTextSelector("On");
            Action.UiTextSelector("Next");
            Action.UiTextSelector("Off");
            Action.UiTextSelector("Next");
            if(new UiObject(new UiSelector().text("Done")).exists()){
                new UiObject(new UiSelector().text("Done")).click();
                Action.Sleep(2);
            }
        }
    }
    @Test
    public void Camera_Formats() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Camera Formats");
        Action.NoticeConfirm("Camera Formats");
        //开启默认后置相机测试
        for(int i=0;i<CAMERA_FORMATS_resolution.length;i++){
            new UiObject(new UiSelector().className("android.widget.Spinner").resourceId("com.android.cts.verifier:id/resolution_selection")).click();
            Action.Sleep(1);
           // Action.UiTextScrollable(CAMERA_FORMATS_resolution[i]);
            if(new UiObject(new UiSelector().text(CAMERA_FORMATS_resolution[i])).exists()){
                new UiObject(new UiSelector().text(CAMERA_FORMATS_resolution[i])).click();
                Action.Sleep(1);
                for(int j=0;j<CAMERA_FORMATS_TYPE.length;j++){
                    new UiObject(new UiSelector().className("android.widget.Spinner").resourceId("com.android.cts.verifier:id/format_selection")).click();
                    Action.Sleep(1);
//                Action.UiTextScrollable(CAMERA_FORMATS_TYPE[j]);
                    new UiObject(new UiSelector().text(CAMERA_FORMATS_TYPE[j])).click();
                    Action.Sleep(1);
                }
            }else {mDevice.pressBack();Action.Sleep(1);}

        }

        //切换前置相机测试
        new UiObject(new UiSelector().className("android.widget.Spinner").resourceId("com.android.cts.verifier:id/cameras_selection")).click();
        Action.Sleep(1);
        if(new UiObject(new UiSelector().text("Camera 1")).exists()){
            new UiObject(new UiSelector().text("Camera 1")).click();
            Action.Sleep(1);
            //开始前置相机测试
            for(int m=0;m<CAMERA_FORMATS_resolution_1.length;m++){
                new UiObject(new UiSelector().className("android.widget.Spinner").resourceId("com.android.cts.verifier:id/resolution_selection")).click();
                Action.Sleep(1);
                if(new UiObject(new UiSelector().text(CAMERA_FORMATS_resolution_1[m])).exists()){
                    new UiObject(new UiSelector().text(CAMERA_FORMATS_resolution_1[m])).click();
                    Action.Sleep(1);
                    for(int n=0;n<CAMERA_FORMATS_TYPE.length;n++){
                        new UiObject(new UiSelector().className("android.widget.Spinner").resourceId("com.android.cts.verifier:id/format_selection")).click();
                        Action.Sleep(1);
                        new UiObject(new UiSelector().text(CAMERA_FORMATS_TYPE[n])).click();
                        Action.Sleep(1);
                    }
                }else {mDevice.pressBack();Action.Sleep(1);}
            }
        }
//        else {Assert.assertFalse("前置相机有异常，请更换设备重试！",true);}
        Action.Sleep(10);
        UiObject passbtn=new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button").description("Pass"));
//        passbtn.clickAndWaitForNewWindow();
        if(passbtn.isEnabled()){
            passbtn.clickAndWaitForNewWindow();
            Action.Sleep(2);
        } else {
            new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/fail_button").description("Fail")).click();
            Action.Sleep(1);
        }

    }
    //Camera ITS Test
    @Test
    public void Camera_ITS_Test() {
        Action.UiTextScrollable("Camera ITS Test");
        Action.Sleep(2);
        //android.widget.Spinner
    }
    @Test
    public void Camera_intents() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Camera Intents");
        Action.NoticeConfirm("Camera Intents");
        Action.Sleep(2);
        Action.UiTextSelector("Start Test");
        mDevice.pressHome();
        Action.Sleep(2);

        if (Build.MODEL.equals("Wileyfox*")) {


        } else {
            if (new UiObject(new UiSelector().description("Camera")).exists()) {
                new UiObject(new UiSelector().description("Camera")).clickAndWaitForNewWindow();
            } else {
                new UiObject(new UiSelector().description("Apps")).clickAndWaitForNewWindow();
                Action.Sleep(2);
                UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
                appViews.setAsHorizontalList();
                appViews.scrollForward();
                //通过类名和Text找到联系人应用图标，Text获取通过uiautomatorviewer
                UiObject findcamera = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), "Camera");
                //点击并等待打开短信应用
                findcamera.clickAndWaitForNewWindow();
                Action.Sleep(2);
            }
            Action.Sleep(2);
            new UiObject(new UiSelector().description("Shutter button").resourceId("com.mediatek.camera:id/shutter_button_photo")).click();

            Action.Sleep(2);
            mDevice.pressBack();
            Action.Sleep(2);

            UiObject handleView = new UiObject(new UiSelector().descriptionContains("Apps"));
            //	UiObject appList = new UiObject(new UiSelector().text(applist[j]));
            handleView.clickAndWaitForNewWindow();
            Action.Sleep(2);

            UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
            appViews.setAsHorizontalList();
            appViews.scrollForward();

            //通过类名和Text找到Calendar应用图标，Text获取通过uiautomatorviewer
            UiObject Verifierapp = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), "CTS Verifier");
            //点击并等待打开Calendar应用
            Verifierapp.clickAndWaitForNewWindow();
            Action.Sleep(2);
            Action.Pass_btn_check();

            Action.UiTextSelector("Start Test");
            mDevice.pressHome();
            Action.Sleep(2);
            new UiObject(new UiSelector().description("Camera")).clickAndWaitForNewWindow();
            Action.Sleep(2);
            UiObject record_btn = new UiObject(new UiSelector().description("Video shutter button").resourceId("com.mediatek.camera:id/shutter_button_video"));
//        mDevice.drag(record_btn.getBounds().centerX(),record_btn.getBounds().centerY(),record_btn.getBounds().centerX(),record_btn.getBounds().centerY(),100);
            record_btn.click();
            Action.Sleep(5);
            record_btn.click();
            Action.Sleep(5);
            mDevice.pressBack();
            mDevice.pressHome();


            //	UiObject appList = new UiObject(new UiSelector().text(applist[j]));
            handleView.clickAndWaitForNewWindow();
            Action.Sleep(2);


            appViews.setAsHorizontalList();
            appViews.scrollForward();

            //通过类名和Text找到Calendar应用图标，Text获取通过uiautomatorviewer
            Verifierapp.clickAndWaitForNewWindow();
            Action.Sleep(2);
            Action.Pass_btn_check();
            Action.UiTextSelector("Start Test");
            new UiObject(new UiSelector().description("Shutter button").resourceId("com.mediatek.camera:id/shutter_button_photo")).click();
            Action.Sleep(2);
            mDevice.pressBack();
            Action.Sleep(2);
            Action.Pass_btn_check();
            Action.UiTextSelector("Start Test");
            record_btn.click();
            Action.Sleep(5);
            record_btn.click();
            Action.Sleep(5);
            mDevice.pressBack();
            Action.Sleep(2);
            Action.Pass_btn_check();
        }




    }
    @Test
    public void Camera_Orientation() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Camera Orientation");
        Action.NoticeConfirm("Camera Orientation");
        Action.Sleep(2);
        for(int y=0;y<8;y++){
            Action.UiTextSelector("Take Photo");
            Action.Sleep(5);
            Action.Pass_btn_check();
        }

    }
    @Test
    public void Camera_Video() throws UiObjectNotFoundException {
        Action.UiTextScrollable("Camera Video");
        Action.NoticeConfirm("Camera Video");
        Action.Sleep(2);
        //切换前置相机测试
        for (int x=0;x<CAMERA_FORMATS.length;x++){
            new UiObject(new UiSelector().className("android.widget.Spinner").resourceId("com.android.cts.verifier:id/cameras_selection")).click();
            Action.Sleep(1);
            new UiObject(new UiSelector().text(CAMERA_FORMATS[x])).click();
            Action.Sleep(1);

//            设置视频质量类型

            for(int y=0;y<CAMERA_VIDEO_TYPE.length;y++){
                new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/resolution_selection")).click();

                UiObject camera_type=new UiObject(new UiSelector().text(CAMERA_VIDEO_TYPE[y]));
                if(camera_type.exists()){
                    camera_type.click();
                    Action.UiTextSelector("Test");
                    Action.Sleep(10);

                }else {
                    mDevice.pressBack();
                    Action.Sleep(1);
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
