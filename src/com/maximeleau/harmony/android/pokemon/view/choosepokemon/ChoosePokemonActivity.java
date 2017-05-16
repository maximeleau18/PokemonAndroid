package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.view.pokemon.PokemonListFragment;
import com.microsoft.azure.engagement.EngagementAgent;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonActivity extends EngagementFragmentActivity {
    private Dresseur dresseur;
    private String choisenAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.dresseur = (Dresseur) intent.getSerializableExtra("dresseur");
        this.choisenAction = intent.getStringExtra("choisenAction");

        EngagementAgent.getInstance(this).getDeviceId(new EngagementAgent.Callback<String>()
        {
            @Override
            public void onResult(String deviceId)
            {
                Log.v("PokemonAndroidML", "Got my device id:" + deviceId);
            }
        });

        setContentView(R.layout.activity_choose_pokemon);
    }

    public Dresseur getDresseur(){
        return this.dresseur;
    }

    public int getChoisenAction(){
        if(this.choisenAction.equals("create_fight")) {
            return 1;
        }else if(this.choisenAction.equals("join_fight")){
            return 2;
        }
        return 0;
    }
}
