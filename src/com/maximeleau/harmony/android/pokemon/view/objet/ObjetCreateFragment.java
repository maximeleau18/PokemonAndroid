/**************************************************************************
 * ObjetCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.objet;

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
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.ObjetProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeObjetProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;

/**
 * Objet create fragment.
 *
 * This fragment gives you an interface to create a Objet.
 */
public class ObjetCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Objet model = new Objet();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** quantite View. */
    protected EditText quantiteView;
    /** urlImage View. */
    protected EditText urlImageView;
    /** The typeObjet chooser component. */
    protected SingleEntityWidget typeObjetWidget;
    /** The typeObjet Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeObjet> 
                typeObjetAdapter;
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
            (EditText) view.findViewById(R.id.objet_nom);
        this.quantiteView =
            (EditText) view.findViewById(R.id.objet_quantite);
        this.urlImageView =
            (EditText) view.findViewById(R.id.objet_urlimage);
        this.typeObjetAdapter = 
                new SingleEntityWidget.EntityAdapter<TypeObjet>() {
            @Override
            public String entityToString(TypeObjet item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeObjetWidget =
            (SingleEntityWidget) view.findViewById(R.id.objet_typeobjet_button);
        this.typeObjetWidget.setAdapter(this.typeObjetAdapter);
        this.typeObjetWidget.setTitle(R.string.objet_typeobjet_dialog_title);
        this.personnageNonJoueurAdapter = 
                new SingleEntityWidget.EntityAdapter<PersonnageNonJoueur>() {
            @Override
            public String entityToString(PersonnageNonJoueur item) {
                return String.valueOf(item.getId());
            }
        };
        this.personnageNonJoueurWidget =
            (SingleEntityWidget) view.findViewById(R.id.objet_personnagenonjoueur_button);
        this.personnageNonJoueurWidget.setAdapter(this.personnageNonJoueurAdapter);
        this.personnageNonJoueurWidget.setTitle(R.string.objet_personnagenonjoueur_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.quantiteView.setText(String.valueOf(this.model.getQuantite()));
        if (this.model.getUrlImage() != null) {
            this.urlImageView.setText(this.model.getUrlImage());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setQuantite(Integer.parseInt(
                    this.quantiteView.getEditableText().toString()));

        this.model.setUrlImage(this.urlImageView.getEditableText().toString());

        this.model.setTypeObjet(this.typeObjetAdapter.getSelectedItem());

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
            error = R.string.objet_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.quantiteView.getText().toString().trim())) {
            error = R.string.objet_quantite_invalid_field_error;
        }
        if (this.typeObjetAdapter.getSelectedItem() == null) {
            error = R.string.objet_typeobjet_invalid_field_error;
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
                R.layout.fragment_objet_create,
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
        private final Objet entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final ObjetCreateFragment fragment,
                final Objet entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.objet_progress_save_title),
                    this.ctx.getString(
                            R.string.objet_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new ObjetProviderUtils(this.ctx).insert(
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
                                R.string.objet_error_create));
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
        private ObjetCreateFragment fragment;
        /** typeObjet list. */
        private ArrayList<TypeObjet> typeObjetList;
        /** personnageNonJoueur list. */
        private ArrayList<PersonnageNonJoueur> personnageNonJoueurList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ObjetCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.objet_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.objet_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeObjetList = 
                new TypeObjetProviderUtils(this.ctx).queryAll();
            this.personnageNonJoueurList = 
                new PersonnageNonJoueurProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.typeObjetAdapter.loadData(this.typeObjetList);
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
