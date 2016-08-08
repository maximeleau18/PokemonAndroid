package com.maximeleau.harmony.android.pokemon.view.connectedchooseaction;

import android.content.Intent;
import android.os.Bundle;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 08/08/2016.
 */
public class ConnectedChooseActionActivity extends EngagementFragmentActivity {

    private Dresseur dresseurConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.dresseurConnected = (Dresseur) intent.getSerializableExtra("dresseur");

        setContentView(R.layout.activity_connected_choose_action);
    }

    public Dresseur getDresseurConnected(){
        return this.dresseurConnected;
    }
}
