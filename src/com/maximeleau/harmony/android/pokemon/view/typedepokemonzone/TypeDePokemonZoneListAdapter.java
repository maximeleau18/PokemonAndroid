/**************************************************************************
 * TypeDePokemonZoneListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonzone;


import com.maximeleau.harmony.android.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/**
 * List adapter for TypeDePokemonZone entity.
 */
public class TypeDePokemonZoneListAdapter extends HarmonyCursorAdapter<TypeDePokemonZone> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonZoneListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeDePokemonZoneListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeDePokemonZone cursorToItem(Cursor cursor) {
        return TypeDePokemonZoneContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeDePokemonZoneContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeDePokemonZone> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeDePokemonZone> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typedepokemonzone);
        }

        /**
         * Populate row with a {@link TypeDePokemonZone}.
         *
         * @param model {@link TypeDePokemonZone} data
         */
        public void populate(final TypeDePokemonZone model) {
            TextView zoneView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemonzone_zone);
                    
            TextView typeDePokemonView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemonzone_typedepokemon);
                    

            if (model.getZone() != null) {
                zoneView.setText(
                        String.valueOf(model.getZone().getId()));
            }
            if (model.getTypeDePokemon() != null) {
                typeDePokemonView.setText(
                        String.valueOf(model.getTypeDePokemon().getId()));
            }
        }
    }
}
