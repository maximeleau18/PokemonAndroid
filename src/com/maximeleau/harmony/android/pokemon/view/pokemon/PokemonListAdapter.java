/**************************************************************************
 * PokemonListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.pokemon;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/**
 * List adapter for Pokemon entity.
 */
public class PokemonListAdapter extends HarmonyCursorAdapter<Pokemon> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokemonListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokemonListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Pokemon cursorToItem(Cursor cursor) {
        return PokemonContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokemonContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Pokemon> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Pokemon> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokemon);
        }

        /**
         * Populate row with a {@link Pokemon}.
         *
         * @param model {@link Pokemon} data
         */
        public void populate(final Pokemon model) {
            TextView surnomView = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_surnom);
                    
            TextView niveauView = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_niveau);
                    
            TextView captureLeView = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_capturele);
                    
            TextView attaque1View = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_attaque1);
                    
            TextView attaque2View = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_attaque2);
                    
            TextView attaque3View = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_attaque3);
                    
            TextView attaque4View = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_attaque4);
                    
            TextView typeDePokemonView = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_typedepokemon);
                    
            TextView personnageNonJoueurView = (TextView) this.getView().findViewById(
                    R.id.row_pokemon_personnagenonjoueur);
                    

            if (model.getSurnom() != null) {
                surnomView.setText(model.getSurnom());
            }
            niveauView.setText(String.valueOf(model.getNiveau()));
            if (model.getCaptureLe() != null) {
                captureLeView.setText(DateUtils.formatDateTimeToString(model.getCaptureLe()));
            }
            if (model.getAttaque1() != null) {
                attaque1View.setText(
                        String.valueOf(model.getAttaque1().getId()));
            }
            if (model.getAttaque2() != null) {
                attaque2View.setText(
                        String.valueOf(model.getAttaque2().getId()));
            }
            if (model.getAttaque3() != null) {
                attaque3View.setText(
                        String.valueOf(model.getAttaque3().getId()));
            }
            if (model.getAttaque4() != null) {
                attaque4View.setText(
                        String.valueOf(model.getAttaque4().getId()));
            }
            if (model.getTypeDePokemon() != null) {
                typeDePokemonView.setText(
                        String.valueOf(model.getTypeDePokemon().getId()));
            }
            if (model.getPersonnageNonJoueur() != null) {
                personnageNonJoueurView.setText(
                        String.valueOf(model.getPersonnageNonJoueur().getId()));
            }
        }
    }
}
