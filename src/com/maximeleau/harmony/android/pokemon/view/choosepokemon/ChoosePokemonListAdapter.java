package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyCursorAdapter;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyViewHolder;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonListAdapter extends ArrayAdapter<Pokemon> {
    Context mContext;
    int layoutResourceId;
    ArrayList<Pokemon> data = null;

    public ChoosePokemonListAdapter(Context mContext, int layoutResourceId, ArrayList<Pokemon> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // pokemon item based on the position
        Pokemon pokemonItem = data.get(position);

        // get textview and set text with data
        TextView textViewTypeName = (TextView) convertView.findViewById(R.id.row_choose_pokemon_type_de_pokemon_nom);
        textViewTypeName.setText(pokemonItem.getTypeDePokemon().getNom());

        TextView textViewLevel = (TextView) convertView.findViewById(R.id.row_choose_pokemon_level_value);
        textViewLevel.setText(String.format(Locale.FRANCE, "%d", pokemonItem.getNiveau()));

        TextView textViewActualHP = (TextView) convertView.findViewById(R.id.row_choose_pokemon_actual_pv_value);
        textViewActualHP.setText(String.format(Locale.FRANCE, "%d", pokemonItem.getTypeDePokemon().getPv()));

        TextView textViewMaxHP = (TextView) convertView.findViewById(R.id.row_choose_pokemon_max_pv_value);
        textViewMaxHP.setText(String.format(Locale.FRANCE, "%d", pokemonItem.getTypeDePokemon().getPv()));

        return convertView;
    }
}
