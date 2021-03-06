/**************************************************************************
 * AreneCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.arene;

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
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.AreneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.BadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PositionProviderUtils;

/**
 * Arene create fragment.
 *
 * This fragment gives you an interface to create a Arene.
 */
public class AreneCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Arene model = new Arene();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** The maitre chooser component. */
    protected SingleEntityWidget maitreWidget;
    /** The maitre Adapter. */
    protected SingleEntityWidget.EntityAdapter<PersonnageNonJoueur> 
                maitreAdapter;
    /** The badge chooser component. */
    protected SingleEntityWidget badgeWidget;
    /** The badge Adapter. */
    protected SingleEntityWidget.EntityAdapter<Badge> 
                badgeAdapter;
    /** The position chooser component. */
    protected SingleEntityWidget positionWidget;
    /** The position Adapter. */
    protected SingleEntityWidget.EntityAdapter<Position> 
                positionAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.arene_nom);
        this.maitreAdapter = 
                new SingleEntityWidget.EntityAdapter<PersonnageNonJoueur>() {
            @Override
            public String entityToString(PersonnageNonJoueur item) {
                return String.valueOf(item.getId());
            }
        };
        this.maitreWidget =
            (SingleEntityWidget) view.findViewById(R.id.arene_maitre_button);
        this.maitreWidget.setAdapter(this.maitreAdapter);
        this.maitreWidget.setTitle(R.string.arene_maitre_dialog_title);
        this.badgeAdapter = 
                new SingleEntityWidget.EntityAdapter<Badge>() {
            @Override
            public String entityToString(Badge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget =
            (SingleEntityWidget) view.findViewById(R.id.arene_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.arene_badge_dialog_title);
        this.positionAdapter = 
                new SingleEntityWidget.EntityAdapter<Position>() {
            @Override
            public String entityToString(Position item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionWidget =
            (SingleEntityWidget) view.findViewById(R.id.arene_position_button);
        this.positionWidget.setAdapter(this.positionAdapter);
        this.positionWidget.setTitle(R.string.arene_position_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setMaitre(this.maitreAdapter.getSelectedItem());

        this.model.setBadge(this.badgeAdapter.getSelectedItem());

        this.model.setPosition(this.positionAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.arene_nom_invalid_field_error;
        }
        if (this.maitreAdapter.getSelectedItem() == null) {
            error = R.string.arene_maitre_invalid_field_error;
        }
        if (this.badgeAdapter.getSelectedItem() == null) {
            error = R.string.arene_badge_invalid_field_error;
        }
        if (this.positionAdapter.getSelectedItem() == null) {
            error = R.string.arene_position_invalid_field_error;
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
                R.layout.fragment_arene_create,
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
        private final Arene entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final AreneCreateFragment fragment,
                final Arene entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.arene_progress_save_title),
                    this.ctx.getString(
                            R.string.arene_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new AreneProviderUtils(this.ctx).insert(
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
                                R.string.arene_error_create));
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
        private AreneCreateFragment fragment;
        /** maitre list. */
        private ArrayList<PersonnageNonJoueur> maitreList;
        /** badge list. */
        private ArrayList<Badge> badgeList;
        /** position list. */
        private ArrayList<Position> positionList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final AreneCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.arene_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.arene_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.maitreList = 
                new PersonnageNonJoueurProviderUtils(this.ctx).queryAll();
            this.badgeList = 
                new BadgeProviderUtils(this.ctx).queryAll();
            this.positionList = 
                new PositionProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.maitreAdapter.loadData(this.maitreList);
            this.fragment.badgeAdapter.loadData(this.badgeList);
            this.fragment.positionAdapter.loadData(this.positionList);
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
