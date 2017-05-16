package com.maximeleau.harmony.android.pokemon.view.chooseaction;

import android.os.Bundle;
import com.maximeleau.harmony.android.pokemon.R;
import com.microsoft.azure.engagement.EngagementAgent;
import com.microsoft.azure.engagement.EngagementConfiguration;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 01/08/2016.
 */
public class ChooseActionActivity extends EngagementFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        EngagementConfiguration engagementConfiguration = new EngagementConfiguration();
        engagementConfiguration.setConnectionString("Endpoint=PokemonAPIPush.device.mobileengagement.windows.net;SdkKey=cf62c7530a90a65edfd96ecf9064b879;AppId=nep000127");
        EngagementAgent.getInstance(this).init(engagementConfiguration);
    }
}
