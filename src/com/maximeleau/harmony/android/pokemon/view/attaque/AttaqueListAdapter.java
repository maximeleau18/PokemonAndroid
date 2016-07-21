/**************************************************************************
 * AttaqueListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.attaque;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

/**
 * List adapter for Attaque entity.
 */
public class AttaqueListAdapter extends HarmonyCursorAdapter<Attaque> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public AttaqueListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public AttaqueListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Attaque cursorToItem(Cursor cursor) {
        return AttaqueContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return AttaqueContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Attaque> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Attaque> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_attaque);
        }

        /**
         * Populate row with a {@link Attaque}.
         *
         * @param model {@link Attaque} data
         */
        public void populate(final Attaque model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_attaque_nom);
                    
            TextView puissanceView = (TextView) this.getView().findViewById(
                    R.id.row_attaque_puissance);
                    
            TextView degatsView = (TextView) this.getView().findViewById(
                    R.id.row_attaque_degats);
                    
            TextView typeAttaqueView = (TextView) this.getView().findViewById(
                    R.id.row_attaque_typeattaque);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            puissanceView.setText(String.valueOf(model.getPuissance()));
            degatsView.setText(String.valueOf(model.getDegats()));
            if (model.getTypeAttaque() != null) {
                typeAttaqueView.setText(
                        String.valueOf(model.getTypeAttaque().getId()));
            }
        }
    }
}
