package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;

/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_pokemon, container, false);
        return view;
    }
}
