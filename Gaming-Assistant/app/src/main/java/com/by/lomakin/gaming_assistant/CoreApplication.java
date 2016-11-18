package com.by.lomakin.gaming_assistant;

import android.app.Application;
import com.vk.sdk.VKSdk;

/**
 * Created by 23699 on 18.11.2016.
 */
public class CoreApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
