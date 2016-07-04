/**************************************************************************
 * TypeAttaqueListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typeattaque;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

/**
 * List adapter for TypeAttaque entity.
 */
public class TypeAttaqueListAdapter extends HarmonyCursorAdapter<TypeAttaque> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeAttaqueListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeAttaqueListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeAttaque cursorToItem(Cursor cursor) {
        return TypeAttaqueContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeAttaqueContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeAttaque> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeAttaque> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typeattaque);
        }

        /**
         * Populate row with a {@link TypeAttaque}.
         *
         * @param model {@link TypeAttaque} data
         */
        public void populate(final TypeAttaque model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_typeattaque_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
