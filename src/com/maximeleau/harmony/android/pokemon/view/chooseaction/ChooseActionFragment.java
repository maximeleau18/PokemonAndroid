package com.maximeleau.harmony.android.pokemon.view.chooseaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.view.connexion.ConnexionActivity;

/**
 * Created by Maxime Léau on 01/08/2016.
 */
public class ChooseActionFragment extends Fragment {

    private Button btnCreateGame;
    private Button btnContinueGame;
    private ImageView ivChooseAction;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_action, container, false);
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(final View view) {
        this.btnCreateGame = (Button) view.findViewById(R.id.choose_action_create_game_button);
        this.btnContinueGame = (Button) view.findViewById(R.id.choose_action_continue_game_button);
        this.ivChooseAction = (ImageView) view.findViewById(R.id.choose_action_iv);

        int drawableResourceId = this.getActivity().getResources().getIdentifier("menu_menu", "drawable", this.getActivity().getPackageName());
        this.ivChooseAction.setImageResource(drawableResourceId);

        // Binding click
        this.btnCreateGame.setOnClickListener(btnCreateGameOnClick());
        this.btnCreateGame.setVisibility(View.GONE);

        this.btnContinueGame.setOnClickListener(btnContinueOnClick());
    }

    public View.OnClickListener btnContinueOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ChooseActionFragment.this.getActivity(), ConnexionActivity.class);
                ChooseActionFragment.this.getActivity().startActivity(intent);
            }
        };
    }

    public View.OnClickListener btnCreateGameOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }
}
