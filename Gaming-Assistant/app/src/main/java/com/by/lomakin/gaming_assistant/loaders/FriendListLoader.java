package com.by.lomakin.gaming_assistant.loaders;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.by.lomakin.gaming_assistant.api.VkApiConstants;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

/**
 * Created by 23699 on 09.12.2016.
 */

public class FriendListLoader extends Loader<VKList<VKApiUser>> {

    public FriendListLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
        VKRequest vkRequest = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, VkApiConstants.FIRST_NAME +","+VkApiConstants.LAST_NAME+","+VkApiConstants.PHOTO_100,VkApiConstants.ORDER,VkApiConstants.NAME));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiUser> friends = (VKList<VKApiUser>) response.parsedModel;
                deliverResult(friends);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

            }

            @Override
            public void onError(VKError error) {
                deliverResult(null);
            }
        });

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }


}
