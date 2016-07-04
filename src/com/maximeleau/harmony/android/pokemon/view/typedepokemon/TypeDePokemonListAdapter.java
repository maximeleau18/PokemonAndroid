/**************************************************************************
 * TypeDePokemonListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemon;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/**
 * List adapter for TypeDePokemon entity.
 */
public class TypeDePokemonListAdapter extends HarmonyCursorAdapter<TypeDePokemon> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeDePokemonListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeDePokemon cursorToItem(Cursor cursor) {
        return TypeDePokemonContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeDePokemonContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeDePokemon> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeDePokemon> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typedepokemon);
        }

        /**
         * Populate row with a {@link TypeDePokemon}.
         *
         * @param model {@link TypeDePokemon} data
         */
        public void populate(final TypeDePokemon model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemon_nom);
                    
            TextView attaqueView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemon_attaque);
                    
            TextView defenseView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemon_defense);
                    
            TextView pvView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemon_pv);
                    
            TextView numPokedexView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemon_numpokedex);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            attaqueView.setText(String.valueOf(model.getAttaque()));
            defenseView.setText(String.valueOf(model.getDefense()));
            pvView.setText(String.valueOf(model.getPv()));
            numPokedexView.setText(String.valueOf(model.getNumPokedex()));
        }
    }
}
