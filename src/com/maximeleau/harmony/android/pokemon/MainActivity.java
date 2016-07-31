package com.maximeleau.harmony.android.pokemon;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.microsoft.azure.engagement.EngagementAgent;
import com.microsoft.azure.engagement.EngagementConfiguration;
import com.microsoft.azure.engagement.activity.EngagementActivity;

public class MainActivity extends EngagementActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Add microsoft engagement
        EngagementConfiguration engagementConfiguration = new EngagementConfiguration();
        engagementConfiguration.setConnectionString("Endpoint=PokemonAPIPush.device.mobileengagement.windows.net;SdkKey=0e430a57ffd2d7ff4cd75382f707f085;AppId=nep000127");
        EngagementAgent.getInstance(this).init(engagementConfiguration);

        EngagementAgent.getInstance(this).getDeviceId(new EngagementAgent.Callback<String>()
        {
            @Override
            public void onResult(String deviceId)
            {
                Log.v("PokemonAndroidML", "Device id:" + deviceId);
            }
        });
    }
}
