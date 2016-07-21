/**************************************************************************
 * PokemonCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.pokemon;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
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
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.widget.DateTimeWidget;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.AttaqueProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;

/**
 * Pokemon create fragment.
 *
 * This fragment gives you an interface to create a Pokemon.
 */
public class PokemonCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Pokemon model = new Pokemon();

    /** Fields View. */
    /** surnom View. */
    protected EditText surnomView;
    /** niveau View. */
    protected EditText niveauView;
    /** captureLe DateTime View. */
    protected DateTimeWidget captureLeView;
    /** The attaque1 chooser component. */
    protected SingleEntityWidget attaque1Widget;
    /** The attaque1 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaque> 
                attaque1Adapter;
    /** The attaque2 chooser component. */
    protected SingleEntityWidget attaque2Widget;
    /** The attaque2 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaque> 
                attaque2Adapter;
    /** The attaque3 chooser component. */
    protected SingleEntityWidget attaque3Widget;
    /** The attaque3 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaque> 
                attaque3Adapter;
    /** The attaque4 chooser component. */
    protected SingleEntityWidget attaque4Widget;
    /** The attaque4 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaque> 
                attaque4Adapter;
    /** The typeDePokemon chooser component. */
    protected SingleEntityWidget typeDePokemonWidget;
    /** The typeDePokemon Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeDePokemon> 
                typeDePokemonAdapter;
    /** The personnageNonJoueur chooser component. */
    protected SingleEntityWidget personnageNonJoueurWidget;
    /** The personnageNonJoueur Adapter. */
    protected SingleEntityWidget.EntityAdapter<PersonnageNonJoueur> 
                personnageNonJoueurAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.surnomView =
            (EditText) view.findViewById(R.id.pokemon_surnom);
        this.niveauView =
            (EditText) view.findViewById(R.id.pokemon_niveau);
        this.captureLeView =
                (DateTimeWidget) view.findViewById(R.id.pokemon_capturele);
        this.attaque1Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaque>() {
            @Override
            public String entityToString(Attaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque1Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemon_attaque1_button);
        this.attaque1Widget.setAdapter(this.attaque1Adapter);
        this.attaque1Widget.setTitle(R.string.pokemon_attaque1_dialog_title);
        this.attaque2Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaque>() {
            @Override
            public String entityToString(Attaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque2Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemon_attaque2_button);
        this.attaque2Widget.setAdapter(this.attaque2Adapter);
        this.attaque2Widget.setTitle(R.string.pokemon_attaque2_dialog_title);
        this.attaque3Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaque>() {
            @Override
            public String entityToString(Attaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque3Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemon_attaque3_button);
        this.attaque3Widget.setAdapter(this.attaque3Adapter);
        this.attaque3Widget.setTitle(R.string.pokemon_attaque3_dialog_title);
        this.attaque4Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaque>() {
            @Override
            public String entityToString(Attaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque4Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemon_attaque4_button);
        this.attaque4Widget.setAdapter(this.attaque4Adapter);
        this.attaque4Widget.setTitle(R.string.pokemon_attaque4_dialog_title);
        this.typeDePokemonAdapter = 
                new SingleEntityWidget.EntityAdapter<TypeDePokemon>() {
            @Override
            public String entityToString(TypeDePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeDePokemonWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokemon_typedepokemon_button);
        this.typeDePokemonWidget.setAdapter(this.typeDePokemonAdapter);
        this.typeDePokemonWidget.setTitle(R.string.pokemon_typedepokemon_dialog_title);
        this.personnageNonJoueurAdapter = 
                new SingleEntityWidget.EntityAdapter<PersonnageNonJoueur>() {
            @Override
            public String entityToString(PersonnageNonJoueur item) {
                return String.valueOf(item.getId());
            }
        };
        this.personnageNonJoueurWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokemon_personnagenonjoueur_button);
        this.personnageNonJoueurWidget.setAdapter(this.personnageNonJoueurAdapter);
        this.personnageNonJoueurWidget.setTitle(R.string.pokemon_personnagenonjoueur_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getSurnom() != null) {
            this.surnomView.setText(this.model.getSurnom());
        }
        this.niveauView.setText(String.valueOf(this.model.getNiveau()));
        if (this.model.getCaptureLe() != null) {
            this.captureLeView.setDateTime(this.model.getCaptureLe());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setSurnom(this.surnomView.getEditableText().toString());

        this.model.setNiveau(Integer.parseInt(
                    this.niveauView.getEditableText().toString()));

        this.model.setCaptureLe(this.captureLeView.getDateTime());

        this.model.setAttaque1(this.attaque1Adapter.getSelectedItem());

        this.model.setAttaque2(this.attaque2Adapter.getSelectedItem());

        this.model.setAttaque3(this.attaque3Adapter.getSelectedItem());

        this.model.setAttaque4(this.attaque4Adapter.getSelectedItem());

        this.model.setTypeDePokemon(this.typeDePokemonAdapter.getSelectedItem());

        this.model.setPersonnageNonJoueur(this.personnageNonJoueurAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.surnomView.getText().toString().trim())) {
            error = R.string.pokemon_surnom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.niveauView.getText().toString().trim())) {
            error = R.string.pokemon_niveau_invalid_field_error;
        }
        if (this.captureLeView.getDateTime() == null) {
            error = R.string.pokemon_capturele_invalid_field_error;
        }
        if (this.typeDePokemonAdapter.getSelectedItem() == null) {
            error = R.string.pokemon_typedepokemon_invalid_field_error;
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
        final View view = inflater.inflate(
                R.layout.fragment_pokemon_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Pokemon entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokemonCreateFragment fragment,
                final Pokemon entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokemon_progress_save_title),
                    this.ctx.getString(
                            R.string.pokemon_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokemonProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.pokemon_error_create));
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
        private PokemonCreateFragment fragment;
        /** attaque1 list. */
        private ArrayList<Attaque> attaque1List;
        /** attaque2 list. */
        private ArrayList<Attaque> attaque2List;
        /** attaque3 list. */
        private ArrayList<Attaque> attaque3List;
        /** attaque4 list. */
        private ArrayList<Attaque> attaque4List;
        /** typeDePokemon list. */
        private ArrayList<TypeDePokemon> typeDePokemonList;
        /** personnageNonJoueur list. */
        private ArrayList<PersonnageNonJoueur> personnageNonJoueurList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokemonCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokemon_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.pokemon_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.attaque1List = 
                new AttaqueProviderUtils(this.ctx).queryAll();
            this.attaque2List = 
                new AttaqueProviderUtils(this.ctx).queryAll();
            this.attaque3List = 
                new AttaqueProviderUtils(this.ctx).queryAll();
            this.attaque4List = 
                new AttaqueProviderUtils(this.ctx).queryAll();
            this.typeDePokemonList = 
                new TypeDePokemonProviderUtils(this.ctx).queryAll();
            this.personnageNonJoueurList = 
                new PersonnageNonJoueurProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.attaque1Adapter.loadData(this.attaque1List);
            this.fragment.attaque2Adapter.loadData(this.attaque2List);
            this.fragment.attaque3Adapter.loadData(this.attaque3List);
            this.fragment.attaque4Adapter.loadData(this.attaque4List);
            this.fragment.typeDePokemonAdapter.loadData(this.typeDePokemonList);
            this.fragment.personnageNonJoueurAdapter.loadData(this.personnageNonJoueurList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
