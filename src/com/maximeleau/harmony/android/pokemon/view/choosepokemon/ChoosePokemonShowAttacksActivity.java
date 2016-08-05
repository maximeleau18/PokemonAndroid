package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonShowAttacksActivity extends EngagementFragmentActivity {
    private Pokemon pokemon;
    private Dresseur dresseur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pokemon_attacks_show);

        Intent intent = getIntent();
        this.pokemon = (Pokemon) intent.getSerializableExtra("pokemon");
        this.dresseur = (Dresseur) intent.getSerializableExtra("dresseur");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ChoosePokemonAttackFragment fragmentAttack1 = new ChoosePokemonAttackFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("attaque", this.pokemon.getAttaque1());
        fragmentAttack1.setArguments(bundle1);
        fragmentTransaction.add(R.id.fragment_attack1, fragmentAttack1);

        ChoosePokemonAttackFragment fragmentAttack2 = new ChoosePokemonAttackFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("attaque", this.pokemon.getAttaque2());
        fragmentAttack2.setArguments(bundle2);
        fragmentTransaction.add(R.id.fragment_attack2, fragmentAttack2);

        ChoosePokemonAttackFragment fragmentAttack3 = new ChoosePokemonAttackFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("attaque", this.pokemon.getAttaque3());
        fragmentAttack3.setArguments(bundle3);
        fragmentTransaction.add(R.id.fragment_attack3, fragmentAttack3);

        ChoosePokemonAttackFragment fragmentAttack4 = new ChoosePokemonAttackFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putSerializable("attaque", this.pokemon.getAttaque4());
        fragmentAttack4.setArguments(bundle4);
        fragmentTransaction.add(R.id.fragment_attack4, fragmentAttack4);

        // Commit changes
        fragmentTransaction.commit();

    }

    public Pokemon getPokemonChoisen(){
        return this.pokemon;
    }

    public Dresseur getDresseurConnected(){
        return this.dresseur;
    }
}
