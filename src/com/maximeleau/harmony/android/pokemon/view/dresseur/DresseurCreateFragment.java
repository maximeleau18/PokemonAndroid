/**************************************************************************
 * DresseurCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.dresseur;

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
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.DresseurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;

/**
 * Dresseur create fragment.
 *
 * This fragment gives you an interface to create a Dresseur.
 */
public class DresseurCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Dresseur model = new Dresseur();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** prenom View. */
    protected EditText prenomView;
    /** login View. */
    protected EditText loginView;
    /** motDePasse View. */
    protected EditText motDePasseView;
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
        this.nomView =
            (EditText) view.findViewById(R.id.dresseur_nom);
        this.prenomView =
            (EditText) view.findViewById(R.id.dresseur_prenom);
        this.loginView =
            (EditText) view.findViewById(R.id.dresseur_login);
        this.motDePasseView =
            (EditText) view.findViewById(R.id.dresseur_motdepasse);
        this.personnageNonJoueurAdapter = 
                new SingleEntityWidget.EntityAdapter<PersonnageNonJoueur>() {
            @Override
            public String entityToString(PersonnageNonJoueur item) {
                return String.valueOf(item.getId());
            }
        };
        this.personnageNonJoueurWidget =
            (SingleEntityWidget) view.findViewById(R.id.dresseur_personnagenonjoueur_button);
        this.personnageNonJoueurWidget.setAdapter(this.personnageNonJoueurAdapter);
        this.personnageNonJoueurWidget.setTitle(R.string.dresseur_personnagenonjoueur_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getPrenom() != null) {
            this.prenomView.setText(this.model.getPrenom());
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getMotDePasse() != null) {
            this.motDePasseView.setText(this.model.getMotDePasse());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setPrenom(this.prenomView.getEditableText().toString());

        this.model.setLogin(this.loginView.getEditableText().toString());

        this.model.setMotDePasse(this.motDePasseView.getEditableText().toString());

        this.model.setPersonnageNonJoueur(this.personnageNonJoueurAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.dresseur_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.prenomView.getText().toString().trim())) {
            error = R.string.dresseur_prenom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.loginView.getText().toString().trim())) {
            error = R.string.dresseur_login_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.motDePasseView.getText().toString().trim())) {
            error = R.string.dresseur_motdepasse_invalid_field_error;
        }
        if (this.personnageNonJoueurAdapter.getSelectedItem() == null) {
            error = R.string.dresseur_personnagenonjoueur_invalid_field_error;
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
                R.layout.fragment_dresseur_create,
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
        private final Dresseur entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final DresseurCreateFragment fragment,
                final Dresseur entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.dresseur_progress_save_title),
                    this.ctx.getString(
                            R.string.dresseur_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new DresseurProviderUtils(this.ctx).insert(
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
                                R.string.dresseur_error_create));
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
        private DresseurCreateFragment fragment;
        /** personnageNonJoueur list. */
        private ArrayList<PersonnageNonJoueur> personnageNonJoueurList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final DresseurCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.dresseur_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.dresseur_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.personnageNonJoueurList = 
                new PersonnageNonJoueurProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
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
