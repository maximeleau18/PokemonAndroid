/**************************************************************************
 * PersonnageNonJoueurBadgeListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;

/**
 * List adapter for PersonnageNonJoueurBadge entity.
 */
public class PersonnageNonJoueurBadgeListAdapter extends HarmonyCursorAdapter<PersonnageNonJoueurBadge> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PersonnageNonJoueurBadgeListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PersonnageNonJoueurBadgeListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PersonnageNonJoueurBadge cursorToItem(Cursor cursor) {
        return PersonnageNonJoueurBadgeContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PersonnageNonJoueurBadgeContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PersonnageNonJoueurBadge> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PersonnageNonJoueurBadge> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_personnagenonjoueurbadge);
        }

        /**
         * Populate row with a {@link PersonnageNonJoueurBadge}.
         *
         * @param model {@link PersonnageNonJoueurBadge} data
         */
        public void populate(final PersonnageNonJoueurBadge model) {
            TextView personnageNonJoueurView = (TextView) this.getView().findViewById(
                    R.id.row_personnagenonjoueurbadge_personnagenonjoueur);
                    
            TextView badgeView = (TextView) this.getView().findViewById(
                    R.id.row_personnagenonjoueurbadge_badge);
                    

            if (model.getPersonnageNonJoueur() != null) {
                personnageNonJoueurView.setText(
                        String.valueOf(model.getPersonnageNonJoueur().getId()));
            }
            if (model.getBadge() != null) {
                badgeView.setText(
                        String.valueOf(model.getBadge().getId()));
            }
        }
    }
}
