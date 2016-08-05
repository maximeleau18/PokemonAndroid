package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.data.CombatWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.view.combatmanager.CombatManagerShowActivity;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class ChoosePokemonButtonsFragment extends Fragment {
    private Button btnBack;
    private Button btnValidate;
    protected Combat combat = new Combat();

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
                ChoosePokemonShowAttacksActivity parent = (ChoosePokemonShowAttacksActivity) getActivity();
                // Get data to construct combatManager
                Pokemon pokemonSelected = parent.getPokemonChoisen();
                Dresseur dresseurConnected = parent.getDresseurConnected();

                ChoosePokemonButtonsFragment.this.combat.setDresseur2(dresseurConnected);
                ChoosePokemonButtonsFragment.this.combat.setPokemon2(pokemonSelected);

                new SearchEmptyFightTask(ChoosePokemonButtonsFragment.this, ChoosePokemonButtonsFragment.this.combat, dresseurConnected).execute();
            }
        };
    }

    public static class SearchEmptyFightTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Combat combat;
        /** Entity Dresseur to give to activity **/
        private final Dresseur dresseur;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param combat The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public SearchEmptyFightTask(final ChoosePokemonButtonsFragment fragment,
                             final Combat combat, final Dresseur dresseur) {
            super();
            this.ctx = fragment.getActivity();
            this.combat = combat;
            this.dresseur = dresseur;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.search_fight_inprogress_title),
                    this.ctx.getString(
                            R.string.search_fight_inprogress_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                CombatWebServiceClientAdapter webService = new CombatWebServiceClientAdapter(this.ctx, "10.0.2.2", 8000, "http", "/api/");
                result = webService.searchEmptyFight(this.combat);
            } catch (Exception e) {
                android.util.Log.e("ChoosePokemonButtonsFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result < 0) {

                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.search_fight_error));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
                builder.show();
            } else {
                // Create combat Manager with this fight inside
                CombatManager combatManager = new CombatManager();

                combatManager.setCombat(this.combat);
                combatManager.setPokemonActualTurnId(this.combat.getPokemon1().getId());

                final Intent intent = new Intent(this.ctx, CombatManagerShowActivity.class);
                intent.putExtra("combatManager", (Serializable) combatManager);
                intent.putExtra("dresseur", (Serializable) this.dresseur);
                this.ctx.startActivity(intent);
            }

            this.progress.dismiss();
        }
    }
}
