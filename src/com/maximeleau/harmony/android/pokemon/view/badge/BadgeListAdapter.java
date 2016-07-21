/**************************************************************************
 * BadgeListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.badge;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/**
 * List adapter for Badge entity.
 */
public class BadgeListAdapter extends HarmonyCursorAdapter<Badge> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public BadgeListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public BadgeListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Badge cursorToItem(Cursor cursor) {
        return BadgeContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return BadgeContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Badge> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Badge> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_badge);
        }

        /**
         * Populate row with a {@link Badge}.
         *
         * @param model {@link Badge} data
         */
        public void populate(final Badge model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_badge_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
