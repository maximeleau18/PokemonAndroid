package com.maximeleau.harmony.android.pokemon.view.connectedchooseaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.view.choosepokemon.ChoosePokemonActivity;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 08/08/2016.
 */
public class ConnectedChooseActionFragment extends Fragment {
    private Button btnCreateFight;
    private Button btnJoinFight;
    private Dresseur dresseurConnected;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connected_choose_action, container, false);
        // Get dresseur connected from parent activity
        ConnectedChooseActionActivity parent = (ConnectedChooseActionActivity) getActivity();
        this.dresseurConnected = parent.getDresseurConnected();
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(final View view) {
        this.btnCreateFight = (Button) view.findViewById(R.id.connected_choose_action_launch_fight);
        this.btnJoinFight = (Button) view.findViewById(R.id.connected_choose_action_search_fight);

        // Binding click
        this.btnCreateFight.setOnClickListener(createFightOnClick());

        this.btnJoinFight.setOnClickListener(joinFightOnClick());
    }

    public View.OnClickListener createFightOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ConnectedChooseActionFragment.this.getActivity(), ChoosePokemonActivity.class);
                intent.putExtra("dresseur", (Serializable) ConnectedChooseActionFragment.this.dresseurConnected);
                intent.putExtra("choisenAction", "create_fight");
                ConnectedChooseActionFragment.this.getActivity().startActivity(intent);
            }
        };
    }

    public View.OnClickListener joinFightOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ConnectedChooseActionFragment.this.getActivity(), ChoosePokemonActivity.class);
                intent.putExtra("dresseur", (Serializable) ConnectedChooseActionFragment.this.dresseurConnected);
                intent.putExtra("choisenAction", "join_fight");
                ConnectedChooseActionFragment.this.getActivity().startActivity(intent);
            }
        };
    }
}
