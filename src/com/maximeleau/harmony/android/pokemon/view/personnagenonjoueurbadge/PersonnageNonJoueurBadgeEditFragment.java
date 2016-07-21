/**************************************************************************
 * PersonnageNonJoueurBadgeEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge;

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
import android.widget.Toast;

import com.google.common.base.Strings;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;

import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurBadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.BadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;

/** PersonnageNonJoueurBadge create fragment.
 *
 * This fragment gives you an interface to edit a PersonnageNonJoueurBadge.
 *
 * @see android.app.Fragment
 */
public class PersonnageNonJoueurBadgeEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PersonnageNonJoueurBadge model = new PersonnageNonJoueurBadge();

    /** curr.fields View. */
    /** The personnageNonJoueur chooser component. */
    protected SingleEntityWidget personnageNonJoueurWidget;
    /** The personnageNonJoueur Adapter. */
    protected SingleEntityWidget.EntityAdapter<PersonnageNonJoueur>
            personnageNonJoueurAdapter;
    /** The badge chooser component. */
    protected SingleEntityWidget badgeWidget;
    /** The badge Adapter. */
    protected SingleEntityWidget.EntityAdapter<Badge>
            badgeAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.personnageNonJoueurAdapter =
                new SingleEntityWidget.EntityAdapter<PersonnageNonJoueur>() {
            @Override
            public String entityToString(PersonnageNonJoueur item) {
                return String.valueOf(item.getId());
            }
        };
        this.personnageNonJoueurWidget =
            (SingleEntityWidget) view.findViewById(R.id.personnagenonjoueurbadge_personnagenonjoueur_button);
        this.personnageNonJoueurWidget.setAdapter(this.personnageNonJoueurAdapter);
        this.personnageNonJoueurWidget.setTitle(R.string.personnagenonjoueurbadge_personnagenonjoueur_dialog_title);
        this.badgeAdapter =
                new SingleEntityWidget.EntityAdapter<Badge>() {
            @Override
            public String entityToString(Badge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget =
            (SingleEntityWidget) view.findViewById(R.id.personnagenonjoueurbadge_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.personnagenonjoueurbadge_badge_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {


        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setPersonnageNonJoueur(this.personnageNonJoueurAdapter.getSelectedItem());

        this.model.setBadge(this.badgeAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (this.personnageNonJoueurAdapter.getSelectedItem() == null) {
            error = R.string.personnagenonjoueurbadge_personnagenonjoueur_invalid_field_error;
        }
        if (this.badgeAdapter.getSelectedItem() == null) {
            error = R.string.personnagenonjoueurbadge_badge_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_personnagenonjoueurbadge_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PersonnageNonJoueurBadge) intent.getParcelableExtra(
                PersonnageNonJoueurBadgeContract.PARCEL);

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
        private final PersonnageNonJoueurBadge entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PersonnageNonJoueurBadgeEditFragment fragment,
                    final PersonnageNonJoueurBadge entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.personnagenonjoueurbadge_progress_save_title),
                    this.ctx.getString(
                            R.string.personnagenonjoueurbadge_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PersonnageNonJoueurBadgeProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PersonnageNonJoueurBadgeEditFragment", e.getMessage());
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
                        R.string.personnagenonjoueurbadge_error_edit));
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
        private PersonnageNonJoueurBadgeEditFragment fragment;
        /** personnageNonJoueur list. */
        private ArrayList<PersonnageNonJoueur> personnageNonJoueurList;
        /** badge list. */
        private ArrayList<Badge> badgeList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PersonnageNonJoueurBadgeEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.personnagenonjoueurbadge_progress_load_relations_title),
                this.ctx.getString(
                    R.string.personnagenonjoueurbadge_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.personnageNonJoueurList = 
                new PersonnageNonJoueurProviderUtils(this.ctx).queryAll();
            this.badgeList = 
                new BadgeProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onPersonnageNonJoueurLoaded(this.personnageNonJoueurList);
            this.fragment.onBadgeLoaded(this.badgeList);

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
    /**
     * Called when badge have been loaded.
     * @param items The loaded items
     */
    protected void onBadgeLoaded(ArrayList<Badge> items) {
        this.badgeAdapter.loadData(items);
        
        if (this.model.getBadge() != null) {
            for (Badge item : items) {
                if (item.getId() == this.model.getBadge().getId()) {
                    this.badgeAdapter.selectItem(item);
                }
            }
        }
    }
}
