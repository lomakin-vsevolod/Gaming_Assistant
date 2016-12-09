package com.by.lomakin.gaming_assistant.loaders;

import android.content.Context;
import android.support.v4.content.Loader;

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
        super.onStartLoading();
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
        VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS,"1,2"));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiUser> users = (VKList<VKApiUser>) response.parsedModel;
                deliverResult(users);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

            }

            @Override
            public void onError(VKError error) {

            }
        });
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
