package com.fxly.ctsverifier;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import junit.framework.Assert;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Lambert Liu on 2016-07-08.
 */
public class Action {



    public static void Sleep(int i) {
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * 针对 Text属性进行搜索
     * @param text
     */
    public static void UiTextSelector(String text) {
        UiObject textstring=new UiObject(new UiSelector().text(text));
        if(textstring.exists()==true){
            try {
                textstring.click();Sleep(2);

            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
                Assert.fail("TestCase error");
            }

        }
    }

    /***
     * 针对 Text属性进行搜索
     * @param text
     */
    public static void UiTextContainsSelector(String text) {
        UiObject textstring=new UiObject(new UiSelector().textContains(text));
        if(textstring.exists()==true){
            try {
                textstring.click();Sleep(2);

            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
                Assert.fail("TestCase error");
            }

        }
    }


    /***
     *
     * @param text  文本属性
     * @param x     第几个
     */
    public static void UiTextSelector_index(String classname,String text ,int x) {
        UiObject textstring=new UiObject(new UiSelector().className(classname).text(text).index(x));
        if(textstring.exists()==true){
            try {
                textstring.click();Sleep(2);

            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
                Assert.fail("TestCase error");
            }

        }
    }

    public static void NoticeConfirm(String notice){
        UiObject notice_text=new UiObject(new UiSelector().text(notice));
        if(notice_text.exists()){
            try {
                new UiObject(new UiSelector().text("OK")).click();
                Sleep(2);
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }

        }else {

        }
    }

    public static void UidescriptionSelector(String text){
        UiObject textstring=new UiObject(new UiSelector().description(text));
        if(textstring.exists()==true){
            try {
                textstring.click();Sleep(2);
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            Assert.fail("Test fail");
//            Assert.assertTrue("Test fail",false);
        }
    }

    public static void UiTextScrollable(String text){
        UiScrollable settingItems = new UiScrollable(
                new UiSelector().scrollable(true));
        try {
            UiObject uitextscollable = settingItems.getChildByText(
                    new UiSelector().text(text), text,
                    true);

            if(uitextscollable.exists()){
                uitextscollable.clickAndWaitForNewWindow();
                Sleep(2);
            }
//            else {
//                Assert.fail("Test fail--------Object Text no found");
//            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    public final static void UiTextScrollableListView(String text) throws UiObjectNotFoundException {
        UiScrollable testList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        UiObject listitem = null;
        listitem = testList.getChildByText(new UiSelector().className("android.widget.TextView"), text, true);
        assertThat(listitem, notNullValue());
        listitem.clickAndWaitForNewWindow();
        Sleep(2);
    }

    public final static void Pass_btn_check() throws UiObjectNotFoundException {
        UiObject passbtn=new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/pass_button").description("Pass"));
        if(passbtn.isEnabled()){
            passbtn.clickAndWaitForNewWindow();
            Action.Sleep(2);
        } else {
            new UiObject(new UiSelector().resourceId("com.android.cts.verifier:id/fail_button").description("Fail")).click();
            Sleep(2);
        }
    }



}
