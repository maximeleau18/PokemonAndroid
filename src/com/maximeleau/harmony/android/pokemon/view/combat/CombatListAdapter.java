/**************************************************************************
 * CombatListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.combat;


import com.maximeleau.harmony.android.pokemon.R;
import android.widget.CheckBox;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

/**
 * List adapter for Combat entity.
 */
public class CombatListAdapter extends HarmonyCursorAdapter<Combat> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public CombatListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public CombatListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Combat cursorToItem(Cursor cursor) {
        return CombatContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return CombatContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Combat> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Combat> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_combat);
        }

        /**
         * Populate row with a {@link Combat}.
         *
         * @param model {@link Combat} data
         */
        public void populate(final Combat model) {
            TextView lanceLeView = (TextView) this.getView().findViewById(
                    R.id.row_combat_lancele);
                    
            TextView dureeView = (TextView) this.getView().findViewById(
                    R.id.row_combat_duree);
                    
            TextView pokemon1View = (TextView) this.getView().findViewById(
                    R.id.row_combat_pokemon1);
                    
            TextView pokemon2View = (TextView) this.getView().findViewById(
                    R.id.row_combat_pokemon2);
                    
            TextView dresseur1View = (TextView) this.getView().findViewById(
                    R.id.row_combat_dresseur1);
                    
            TextView dresseur2View = (TextView) this.getView().findViewById(
                    R.id.row_combat_dresseur2);
                    
            CheckBox dresseur1VainqueurView = (CheckBox) this.getView().findViewById(
                    R.id.row_combat_dresseur1vainqueur);
            dresseur1VainqueurView.setEnabled(false);
            
            CheckBox dresseur2VainqueurView = (CheckBox) this.getView().findViewById(
                    R.id.row_combat_dresseur2vainqueur);
            dresseur2VainqueurView.setEnabled(false);
            
            CheckBox pokemon1VainqueurView = (CheckBox) this.getView().findViewById(
                    R.id.row_combat_pokemon1vainqueur);
            pokemon1VainqueurView.setEnabled(false);
            
            CheckBox pokemon2VainqueurView = (CheckBox) this.getView().findViewById(
                    R.id.row_combat_pokemon2vainqueur);
            pokemon2VainqueurView.setEnabled(false);
            

            if (model.getLanceLe() != null) {
                lanceLeView.setText(DateUtils.formatDateTimeToString(model.getLanceLe()));
            }
            dureeView.setText(String.valueOf(model.getDuree()));
            if (model.getPokemon1() != null) {
                pokemon1View.setText(
                        String.valueOf(model.getPokemon1().getId()));
            }
            if (model.getPokemon2() != null) {
                pokemon2View.setText(
                        String.valueOf(model.getPokemon2().getId()));
            }
            if (model.getDresseur1() != null) {
                dresseur1View.setText(
                        String.valueOf(model.getDresseur1().getId()));
            }
            if (model.getDresseur2() != null) {
                dresseur2View.setText(
                        String.valueOf(model.getDresseur2().getId()));
            }
            dresseur1VainqueurView.setChecked(model.isDresseur1Vainqueur());
            dresseur2VainqueurView.setChecked(model.isDresseur2Vainqueur());
            pokemon1VainqueurView.setChecked(model.isPokemon1Vainqueur());
            pokemon2VainqueurView.setChecked(model.isPokemon2Vainqueur());
        }
    }
}
