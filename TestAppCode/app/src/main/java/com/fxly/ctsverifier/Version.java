package com.fxly.ctsverifier;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Lambert Liu on 2016-07-26.
 */
public class Version {

    static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get find package information for "
                    + context.getPackageName());
        }
    }
}
