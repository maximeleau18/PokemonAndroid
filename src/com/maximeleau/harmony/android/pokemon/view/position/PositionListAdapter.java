/**************************************************************************
 * PositionListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.position;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/**
 * List adapter for Position entity.
 */
public class PositionListAdapter extends HarmonyCursorAdapter<Position> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PositionListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PositionListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Position cursorToItem(Cursor cursor) {
        return PositionContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PositionContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Position> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Position> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_position);
        }

        /**
         * Populate row with a {@link Position}.
         *
         * @param model {@link Position} data
         */
        public void populate(final Position model) {
            TextView xView = (TextView) this.getView().findViewById(
                    R.id.row_position_x);
                    
            TextView yView = (TextView) this.getView().findViewById(
                    R.id.row_position_y);
                    
            TextView zoneView = (TextView) this.getView().findViewById(
                    R.id.row_position_zone);
                    

            xView.setText(String.valueOf(model.getX()));
            yView.setText(String.valueOf(model.getY()));
            if (model.getZone() != null) {
                zoneView.setText(
                        String.valueOf(model.getZone().getId()));
            }
        }
    }
}
