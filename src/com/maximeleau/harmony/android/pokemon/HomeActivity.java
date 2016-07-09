/**************************************************************************
 * HomeActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.objet.ObjetListActivity;
import com.maximeleau.harmony.android.pokemon.view.arene.AreneListActivity;
import com.maximeleau.harmony.android.pokemon.view.position.PositionListActivity;
import com.maximeleau.harmony.android.pokemon.view.zone.ZoneListActivity;
import com.maximeleau.harmony.android.pokemon.view.typeattaque.TypeAttaqueListActivity;
import com.maximeleau.harmony.android.pokemon.view.typedepokemon.TypeDePokemonListActivity;
import com.maximeleau.harmony.android.pokemon.view.badge.BadgeListActivity;
import com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution.TypeDePokemonEvolutionListActivity;
import com.maximeleau.harmony.android.pokemon.view.dresseur.DresseurListActivity;
import com.maximeleau.harmony.android.pokemon.view.typeobjet.TypeObjetListActivity;
import com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge.PersonnageNonJoueurBadgeListActivity;
import com.maximeleau.harmony.android.pokemon.view.profession.ProfessionListActivity;
import com.maximeleau.harmony.android.pokemon.view.attaque.AttaqueListActivity;
import com.maximeleau.harmony.android.pokemon.view.typedepokemonzone.TypeDePokemonZoneListActivity;
import com.maximeleau.harmony.android.pokemon.view.personnagenonjoueur.PersonnageNonJoueurListActivity;
import com.maximeleau.harmony.android.pokemon.view.pokemon.PokemonListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
        implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.initButtons();
    }
    
    @Override
    protected int getContentView() {
        return R.layout.main;
    }

    /**
     * Initialize the buttons click listeners.
     */
    private void initButtons() {
        this.findViewById(R.id.objet_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.arene_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.position_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.zone_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typeattaque_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typedepokemon_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.badge_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typedepokemonevolution_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.dresseur_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typeobjet_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.personnagenonjoueurbadge_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.profession_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.attaque_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typedepokemonzone_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.personnagenonjoueur_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokemon_list_button)
                        .setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.objet_list_button:
                intent = new Intent(this,
                        ObjetListActivity.class);
                break;

            case R.id.arene_list_button:
                intent = new Intent(this,
                        AreneListActivity.class);
                break;

            case R.id.position_list_button:
                intent = new Intent(this,
                        PositionListActivity.class);
                break;

            case R.id.zone_list_button:
                intent = new Intent(this,
                        ZoneListActivity.class);
                break;

            case R.id.typeattaque_list_button:
                intent = new Intent(this,
                        TypeAttaqueListActivity.class);
                break;

            case R.id.typedepokemon_list_button:
                intent = new Intent(this,
                        TypeDePokemonListActivity.class);
                break;

            case R.id.badge_list_button:
                intent = new Intent(this,
                        BadgeListActivity.class);
                break;

            case R.id.typedepokemonevolution_list_button:
                intent = new Intent(this,
                        TypeDePokemonEvolutionListActivity.class);
                break;

            case R.id.dresseur_list_button:
                intent = new Intent(this,
                        DresseurListActivity.class);
                break;

            case R.id.typeobjet_list_button:
                intent = new Intent(this,
                        TypeObjetListActivity.class);
                break;

            case R.id.personnagenonjoueurbadge_list_button:
                intent = new Intent(this,
                        PersonnageNonJoueurBadgeListActivity.class);
                break;

            case R.id.profession_list_button:
                intent = new Intent(this,
                        ProfessionListActivity.class);
                break;

            case R.id.attaque_list_button:
                intent = new Intent(this,
                        AttaqueListActivity.class);
                break;

            case R.id.typedepokemonzone_list_button:
                intent = new Intent(this,
                        TypeDePokemonZoneListActivity.class);
                break;

            case R.id.personnagenonjoueur_list_button:
                intent = new Intent(this,
                        PersonnageNonJoueurListActivity.class);
                break;

            case R.id.pokemon_list_button:
                intent = new Intent(this,
                        PokemonListActivity.class);
                break;

            default:
                intent = null;
                break;
        }

        if (intent != null) {
            this.startActivity(intent);
        }
    }
}
