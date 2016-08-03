package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.view.pokemon.PokemonListAdapter;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonListFragment extends Fragment {
    /** The adapter which handles list population. */
    private ChoosePokemonListAdapter mAdapter;
    private TextView textViewEmptyList;
    private ListView listViewPokemons;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_choose_pokemon_list,
                        null);

        // Get data from activity
        ChoosePokemonActivity activity = (ChoosePokemonActivity) this.getActivity();
        Dresseur dresseur = activity.getData();
        // Get ui elements
        this.textViewEmptyList = (TextView) view.findViewById(R.id.choose_pokemon_list_empty);
        this.listViewPokemons = (ListView) view.findViewById(R.id.choose_pokemon_list_view);

        if (dresseur.getPersonnageNonJoueur().getPokemons().size() > 0){
            // Create the adapter that will receive pokemons
            this.mAdapter = new ChoosePokemonListAdapter(this.getActivity(), R.layout.row_choose_pokemon, dresseur.getPersonnageNonJoueur().getPokemons());
            this.listViewPokemons.setAdapter(this.mAdapter);
            // Set on click item list event
            this.listViewPokemons.setOnItemClickListener(OnPokemonItemClick());
            this.listViewPokemons.setVisibility(View.VISIBLE);
            this.textViewEmptyList.setVisibility(View.GONE);
        }else{
            this.listViewPokemons.setVisibility(View.GONE);
            this.textViewEmptyList.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public AdapterView.OnItemClickListener OnPokemonItemClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pokemon pokemonClicked = (Pokemon)parent.getItemAtPosition(position);

                final Intent intent = new Intent(view.getContext(), ChoosePokemonShowAttacksActivity.class);
                intent.putExtra("pokemon", (Serializable) pokemonClicked);
                view.getContext().startActivity(intent);
            }
        };
    }

}
