package com.by.lomakin.gaming_assistant.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkAuthConstants;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;



public class LoginActivity extends AppCompatActivity {

    private VkAuthUtils vkAuthUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vkAuthUtils = new VkAuthUtils(this);
        if (vkAuthUtils.checkTokenInSharedPreferences()){
            startMainActivity();
        }

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(LoginActivity.this, VkAuthConstants.SCOPE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vkAuthUtils.saveTokenToSharedPreferences(res.accessToken);
                vkAuthUtils.saveUserIdToSharedPreferences(res.userId);
                startMainActivity();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(LoginActivity.this, R.string.auth_error, Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void startMainActivity (){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
