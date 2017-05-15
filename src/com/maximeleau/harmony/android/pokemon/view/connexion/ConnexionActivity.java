package com.maximeleau.harmony.android.pokemon.view.connexion;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.maximeleau.harmony.android.pokemon.R;
import com.microsoft.azure.engagement.EngagementAgent;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 01/08/2016.
 */
public class ConnexionActivity extends EngagementFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        EngagementAgent.getInstance(this).getDeviceId(new EngagementAgent.Callback<String>()
        {
            @Override
            public void onResult(String deviceId)
            {
                Log.v("myapp", "Got my device id:" + deviceId);
            }
        });
    }

}
