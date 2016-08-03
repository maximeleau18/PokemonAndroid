package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.view.chooseaction.ChooseActionActivity;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class ChoosePokemonButtonsFragment extends Fragment {
    private Button btnBack;
    private Button btnValidate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_pokemon_buttons, container, false);
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(View view){
        this.btnBack = (Button) view.findViewById(R.id.choose_pokemon_button_back);
        this.btnValidate = (Button) view.findViewById(R.id.choose_pokemon_button_validate);

        // Bind click
        this.btnBack.setOnClickListener(onClickButtonBack());
        this.btnValidate.setOnClickListener(onClickButtonValidate());
    }

    private View.OnClickListener onClickButtonBack(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        };
    }

    private View.OnClickListener onClickButtonValidate(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }
}
