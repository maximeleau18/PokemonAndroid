/**************************************************************************
 * DresseurListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.dresseur;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/**
 * List adapter for Dresseur entity.
 */
public class DresseurListAdapter extends HarmonyCursorAdapter<Dresseur> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public DresseurListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public DresseurListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Dresseur cursorToItem(Cursor cursor) {
        return DresseurContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return DresseurContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Dresseur> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Dresseur> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_dresseur);
        }

        /**
         * Populate row with a {@link Dresseur}.
         *
         * @param model {@link Dresseur} data
         */
        public void populate(final Dresseur model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_dresseur_nom);
                    
            TextView prenomView = (TextView) this.getView().findViewById(
                    R.id.row_dresseur_prenom);
                    
            TextView loginView = (TextView) this.getView().findViewById(
                    R.id.row_dresseur_login);
                    
            TextView motDePasseView = (TextView) this.getView().findViewById(
                    R.id.row_dresseur_motdepasse);
                    
            TextView personnageNonJoueurView = (TextView) this.getView().findViewById(
                    R.id.row_dresseur_personnagenonjoueur);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getPrenom() != null) {
                prenomView.setText(model.getPrenom());
            }
            if (model.getLogin() != null) {
                loginView.setText(model.getLogin());
            }
            if (model.getMotDePasse() != null) {
                motDePasseView.setText(model.getMotDePasse());
            }
            if (model.getPersonnageNonJoueur() != null) {
                personnageNonJoueurView.setText(
                        String.valueOf(model.getPersonnageNonJoueur().getId()));
            }
        }
    }
}
