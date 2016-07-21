/**************************************************************************
 * TypeDePokemonEvolutionListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/**
 * List adapter for TypeDePokemonEvolution entity.
 */
public class TypeDePokemonEvolutionListAdapter extends HarmonyCursorAdapter<TypeDePokemonEvolution> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonEvolutionListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeDePokemonEvolutionListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeDePokemonEvolution cursorToItem(Cursor cursor) {
        return TypeDePokemonEvolutionContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeDePokemonEvolutionContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeDePokemonEvolution> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeDePokemonEvolution> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typedepokemonevolution);
        }

        /**
         * Populate row with a {@link TypeDePokemonEvolution}.
         *
         * @param model {@link TypeDePokemonEvolution} data
         */
        public void populate(final TypeDePokemonEvolution model) {
            TextView evolueEnView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemonevolution_evolueen);
                    
            TextView estEvolueEnView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemonevolution_estevolueen);
                    

            if (model.getEvolueEn() != null) {
                evolueEnView.setText(
                        String.valueOf(model.getEvolueEn().getId()));
            }
            if (model.getEstEvolueEn() != null) {
                estEvolueEnView.setText(
                        String.valueOf(model.getEstEvolueEn().getId()));
            }
        }
    }
}
