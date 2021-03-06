/**************************************************************************
 * ObjetEditFragment.java, pokemon Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;

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
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/** Objet create fragment.
 *
 * This fragment gives you an interface to edit a Objet.
 *
 * @see android.app.Fragment
 */
public class ObjetEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Objet model = new Objet();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.objet_nom);
        this.quantiteView = (EditText) view.findViewById(
                R.id.objet_quantite);
        this.urlImageView = (EditText) view.findViewById(
                R.id.objet_urlimage);
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

    /** Load data from model to curr.fields view. */
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

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_objet_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Objet) intent.getParcelableExtra(
                ObjetContract.PARCEL);

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
        private final Objet entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final ObjetEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new ObjetProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("ObjetEditFragment", e.getMessage());
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
                        R.string.objet_error_edit));
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
        private ObjetEditFragment fragment;
        /** typeObjet list. */
        private ArrayList<TypeObjet> typeObjetList;
        /** personnageNonJoueur list. */
        private ArrayList<PersonnageNonJoueur> personnageNonJoueurList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ObjetEditFragment fragment) {
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
            this.fragment.onTypeObjetLoaded(this.typeObjetList);
            this.fragment.onPersonnageNonJoueurLoaded(this.personnageNonJoueurList);

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
     * Called when typeObjet have been loaded.
     * @param items The loaded items
     */
    protected void onTypeObjetLoaded(ArrayList<TypeObjet> items) {
        this.typeObjetAdapter.loadData(items);
        
        if (this.model.getTypeObjet() != null) {
            for (TypeObjet item : items) {
                if (item.getId() == this.model.getTypeObjet().getId()) {
                    this.typeObjetAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when personnageNonJoueur have been loaded.
     * @param items The loaded items
     */
    protected void onPersonnageNonJoueurLoaded(ArrayList<PersonnageNonJoueur> items) {
        this.personnageNonJoueurAdapter.loadData(items);
        
        if (this.model.getPersonnageNonJoueur() != null) {
            for (PersonnageNonJoueur item : items) {
                if (item.getId() == this.model.getPersonnageNonJoueur().getId()) {
                    this.personnageNonJoueurAdapter.selectItem(item);
                }
            }
        }
    }
}
