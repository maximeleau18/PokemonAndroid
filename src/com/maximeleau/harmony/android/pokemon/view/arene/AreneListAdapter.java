/**************************************************************************
 * AreneListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.arene;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/**
 * List adapter for Arene entity.
 */
public class AreneListAdapter extends HarmonyCursorAdapter<Arene> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public AreneListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public AreneListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Arene cursorToItem(Cursor cursor) {
        return AreneContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return AreneContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Arene> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Arene> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_arene);
        }

        /**
         * Populate row with a {@link Arene}.
         *
         * @param model {@link Arene} data
         */
        public void populate(final Arene model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_arene_nom);
                    
            TextView maitreView = (TextView) this.getView().findViewById(
                    R.id.row_arene_maitre);
                    
            TextView badgeView = (TextView) this.getView().findViewById(
                    R.id.row_arene_badge);
                    
            TextView positionView = (TextView) this.getView().findViewById(
                    R.id.row_arene_position);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getMaitre() != null) {
                maitreView.setText(
                        String.valueOf(model.getMaitre().getId()));
            }
            if (model.getBadge() != null) {
                badgeView.setText(
                        String.valueOf(model.getBadge().getId()));
            }
            if (model.getPosition() != null) {
                positionView.setText(
                        String.valueOf(model.getPosition().getId()));
            }
        }
    }
}
