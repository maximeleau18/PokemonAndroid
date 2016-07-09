/**************************************************************************
 * AttaqueEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.attaque;

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
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;

import com.maximeleau.harmony.android.pokemon.provider.utils.AttaqueProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeAttaqueProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

/** Attaque create fragment.
 *
 * This fragment gives you an interface to edit a Attaque.
 *
 * @see android.app.Fragment
 */
public class AttaqueEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Attaque model = new Attaque();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** puissance View. */
    protected EditText puissanceView;
    /** degats View. */
    protected EditText degatsView;
    /** The typeAttaque chooser component. */
    protected SingleEntityWidget typeAttaqueWidget;
    /** The typeAttaque Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeAttaque>
            typeAttaqueAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.attaque_nom);
        this.puissanceView = (EditText) view.findViewById(
                R.id.attaque_puissance);
        this.degatsView = (EditText) view.findViewById(
                R.id.attaque_degats);
        this.typeAttaqueAdapter =
                new SingleEntityWidget.EntityAdapter<TypeAttaque>() {
            @Override
            public String entityToString(TypeAttaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeAttaqueWidget =
            (SingleEntityWidget) view.findViewById(R.id.attaque_typeattaque_button);
        this.typeAttaqueWidget.setAdapter(this.typeAttaqueAdapter);
        this.typeAttaqueWidget.setTitle(R.string.attaque_typeattaque_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.puissanceView.setText(String.valueOf(this.model.getPuissance()));
        this.degatsView.setText(String.valueOf(this.model.getDegats()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setPuissance(Integer.parseInt(
                    this.puissanceView.getEditableText().toString()));

        this.model.setDegats(Integer.parseInt(
                    this.degatsView.getEditableText().toString()));

        this.model.setTypeAttaque(this.typeAttaqueAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.attaque_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.puissanceView.getText().toString().trim())) {
            error = R.string.attaque_puissance_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.degatsView.getText().toString().trim())) {
            error = R.string.attaque_degats_invalid_field_error;
        }
        if (this.typeAttaqueAdapter.getSelectedItem() == null) {
            error = R.string.attaque_typeattaque_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_attaque_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Attaque) intent.getParcelableExtra(
                AttaqueContract.PARCEL);

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
        private final Attaque entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final AttaqueEditFragment fragment,
                    final Attaque entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.attaque_progress_save_title),
                    this.ctx.getString(
                            R.string.attaque_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new AttaqueProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("AttaqueEditFragment", e.getMessage());
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
                        R.string.attaque_error_edit));
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
        private AttaqueEditFragment fragment;
        /** typeAttaque list. */
        private ArrayList<TypeAttaque> typeAttaqueList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final AttaqueEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.attaque_progress_load_relations_title),
                this.ctx.getString(
                    R.string.attaque_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeAttaqueList = 
                new TypeAttaqueProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onTypeAttaqueLoaded(this.typeAttaqueList);

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
     * Called when typeAttaque have been loaded.
     * @param items The loaded items
     */
    protected void onTypeAttaqueLoaded(ArrayList<TypeAttaque> items) {
        this.typeAttaqueAdapter.loadData(items);
        
        if (this.model.getTypeAttaque() != null) {
            for (TypeAttaque item : items) {
                if (item.getId() == this.model.getTypeAttaque().getId()) {
                    this.typeAttaqueAdapter.selectItem(item);
                }
            }
        }
    }
}
