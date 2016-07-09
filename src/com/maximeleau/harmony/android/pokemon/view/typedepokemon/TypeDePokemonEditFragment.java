/**************************************************************************
 * TypeDePokemonEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemon;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.widget.MultiEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/** TypeDePokemon create fragment.
 *
 * This fragment gives you an interface to edit a TypeDePokemon.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected TypeDePokemon model = new TypeDePokemon();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** attaque View. */
    protected EditText attaqueView;
    /** defense View. */
    protected EditText defenseView;
    /** pv View. */
    protected EditText pvView;
    /** numPokedex View. */
    protected EditText numPokedexView;
    /** The pokemons chooser component. */
    protected MultiEntityWidget pokemonsWidget;
    /** The pokemons Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokemon>
            pokemonsAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.typedepokemon_nom);
        this.attaqueView = (EditText) view.findViewById(
                R.id.typedepokemon_attaque);
        this.defenseView = (EditText) view.findViewById(
                R.id.typedepokemon_defense);
        this.pvView = (EditText) view.findViewById(
                R.id.typedepokemon_pv);
        this.numPokedexView = (EditText) view.findViewById(
                R.id.typedepokemon_numpokedex);
        this.pokemonsAdapter =
                new MultiEntityWidget.EntityAdapter<Pokemon>() {
            @Override
            public String entityToString(Pokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.typedepokemon_pokemons_button);
        this.pokemonsWidget.setAdapter(this.pokemonsAdapter);
        this.pokemonsWidget.setTitle(R.string.typedepokemon_pokemons_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.attaqueView.setText(String.valueOf(this.model.getAttaque()));
        this.defenseView.setText(String.valueOf(this.model.getDefense()));
        this.pvView.setText(String.valueOf(this.model.getPv()));
        this.numPokedexView.setText(String.valueOf(this.model.getNumPokedex()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setAttaque(Integer.parseInt(
                    this.attaqueView.getEditableText().toString()));

        this.model.setDefense(Integer.parseInt(
                    this.defenseView.getEditableText().toString()));

        this.model.setPv(Integer.parseInt(
                    this.pvView.getEditableText().toString()));

        this.model.setNumPokedex(Integer.parseInt(
                    this.numPokedexView.getEditableText().toString()));

        this.model.setPokemons(this.pokemonsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.typedepokemon_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaqueView.getText().toString().trim())) {
            error = R.string.typedepokemon_attaque_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defenseView.getText().toString().trim())) {
            error = R.string.typedepokemon_defense_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.pvView.getText().toString().trim())) {
            error = R.string.typedepokemon_pv_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.numPokedexView.getText().toString().trim())) {
            error = R.string.typedepokemon_numpokedex_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }
    @Override
    public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(R.layout.fragment_typedepokemon_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (TypeDePokemon) intent.getParcelableExtra(
                TypeDePokemonContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final TypeDePokemon entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypeDePokemonEditFragment fragment,
                    final TypeDePokemon entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.typedepokemon_progress_save_title),
                    this.ctx.getString(
                            R.string.typedepokemon_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypeDePokemonProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypeDePokemonEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.typedepokemon_error_edit));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                                int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }


    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private TypeDePokemonEditFragment fragment;
        /** pokemons list. */
        private ArrayList<Pokemon> pokemonsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypeDePokemonEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.typedepokemon_progress_load_relations_title),
                this.ctx.getString(
                    R.string.typedepokemon_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.pokemonsList = 
                new PokemonProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onPokemonsLoaded(this.pokemonsList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when pokemons have been loaded.
     * @param items The loaded items
     */
    protected void onPokemonsLoaded(ArrayList<Pokemon> items) {
        this.pokemonsAdapter.loadData(items);
        ArrayList<Pokemon> modelItems = new ArrayList<Pokemon>();
        for (Pokemon item : items) {
            if (item.getTypeDePokemon() != null && item.getTypeDePokemon().getId() == this.model.getId()) {
                modelItems.add(item);
                this.pokemonsAdapter.checkItem(item, true);
            }
        }
        this.model.setPokemons(modelItems);
    }
}
