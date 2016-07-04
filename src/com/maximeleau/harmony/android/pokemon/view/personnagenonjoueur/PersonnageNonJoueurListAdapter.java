/**************************************************************************
 * PersonnageNonJoueurListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueur;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/**
 * List adapter for PersonnageNonJoueur entity.
 */
public class PersonnageNonJoueurListAdapter extends HarmonyCursorAdapter<PersonnageNonJoueur> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PersonnageNonJoueurListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PersonnageNonJoueurListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PersonnageNonJoueur cursorToItem(Cursor cursor) {
        return PersonnageNonJoueurContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PersonnageNonJoueurContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PersonnageNonJoueur> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PersonnageNonJoueur> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_personnagenonjoueur);
        }

        /**
         * Populate row with a {@link PersonnageNonJoueur}.
         *
         * @param model {@link PersonnageNonJoueur} data
         */
        public void populate(final PersonnageNonJoueur model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_personnagenonjoueur_nom);
                    
            TextView descriptionView = (TextView) this.getView().findViewById(
                    R.id.row_personnagenonjoueur_description);
                    
            TextView professionView = (TextView) this.getView().findViewById(
                    R.id.row_personnagenonjoueur_profession);
                    
            TextView dresseurView = (TextView) this.getView().findViewById(
                    R.id.row_personnagenonjoueur_dresseur);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getDescription() != null) {
                descriptionView.setText(model.getDescription());
            }
            if (model.getProfession() != null) {
                professionView.setText(
                        String.valueOf(model.getProfession().getId()));
            }
            if (model.getDresseur() != null) {
                dresseurView.setText(
                        String.valueOf(model.getDresseur().getId()));
            }
        }
    }
}
