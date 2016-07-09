/**************************************************************************
 * ProfessionListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.profession;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;

/**
 * List adapter for Profession entity.
 */
public class ProfessionListAdapter extends HarmonyCursorAdapter<Profession> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ProfessionListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ProfessionListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Profession cursorToItem(Cursor cursor) {
        return ProfessionContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ProfessionContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Profession> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Profession> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_profession);
        }

        /**
         * Populate row with a {@link Profession}.
         *
         * @param model {@link Profession} data
         */
        public void populate(final Profession model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_profession_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
