/**************************************************************************
 * ObjetListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.objet;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/**
 * List adapter for Objet entity.
 */
public class ObjetListAdapter extends HarmonyCursorAdapter<Objet> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ObjetListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ObjetListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Objet cursorToItem(Cursor cursor) {
        return ObjetContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ObjetContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Objet> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Objet> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_objet);
        }

        /**
         * Populate row with a {@link Objet}.
         *
         * @param model {@link Objet} data
         */
        public void populate(final Objet model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_objet_nom);
                    
            TextView quantiteView = (TextView) this.getView().findViewById(
                    R.id.row_objet_quantite);
                    
            TextView typeObjetView = (TextView) this.getView().findViewById(
                    R.id.row_objet_typeobjet);
                    
            TextView personnageNonJoueurView = (TextView) this.getView().findViewById(
                    R.id.row_objet_personnagenonjoueur);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            quantiteView.setText(String.valueOf(model.getQuantite()));
            if (model.getTypeObjet() != null) {
                typeObjetView.setText(
                        String.valueOf(model.getTypeObjet().getId()));
            }
            if (model.getPersonnageNonJoueur() != null) {
                personnageNonJoueurView.setText(
                        String.valueOf(model.getPersonnageNonJoueur().getId()));
            }
        }
    }
}
