/**************************************************************************
 * CombatCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.combat;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.widget.DateTimeWidget;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.CombatProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.DresseurProviderUtils;

/**
 * Combat create fragment.
 *
 * This fragment gives you an interface to create a Combat.
 */
public class CombatCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Combat model = new Combat();

    /** Fields View. */
    /** lanceLe DateTime View. */
    protected DateTimeWidget lanceLeView;
    /** duree View. */
    protected EditText dureeView;
    /** The pokemon1 chooser component. */
    protected SingleEntityWidget pokemon1Widget;
    /** The pokemon1 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Pokemon> 
                pokemon1Adapter;
    /** The pokemon2 chooser component. */
    protected SingleEntityWidget pokemon2Widget;
    /** The pokemon2 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Pokemon> 
                pokemon2Adapter;
    /** The dresseur1 chooser component. */
    protected SingleEntityWidget dresseur1Widget;
    /** The dresseur1 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Dresseur> 
                dresseur1Adapter;
    /** The dresseur2 chooser component. */
    protected SingleEntityWidget dresseur2Widget;
    /** The dresseur2 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Dresseur> 
                dresseur2Adapter;
    /** dresseur1Vainqueur View. */
    protected CheckBox dresseur1VainqueurView;
    /** dresseur2Vainqueur View. */
    protected CheckBox dresseur2VainqueurView;
    /** pokemon1Vainqueur View. */
    protected CheckBox pokemon1VainqueurView;
    /** pokemon2Vainqueur View. */
    protected CheckBox pokemon2VainqueurView;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.lanceLeView =
                (DateTimeWidget) view.findViewById(R.id.combat_lancele);
        this.dureeView =
            (EditText) view.findViewById(R.id.combat_duree);
        this.pokemon1Adapter = 
                new SingleEntityWidget.EntityAdapter<Pokemon>() {
            @Override
            public String entityToString(Pokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemon1Widget =
            (SingleEntityWidget) view.findViewById(R.id.combat_pokemon1_button);
        this.pokemon1Widget.setAdapter(this.pokemon1Adapter);
        this.pokemon1Widget.setTitle(R.string.combat_pokemon1_dialog_title);
        this.pokemon2Adapter = 
                new SingleEntityWidget.EntityAdapter<Pokemon>() {
            @Override
            public String entityToString(Pokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemon2Widget =
            (SingleEntityWidget) view.findViewById(R.id.combat_pokemon2_button);
        this.pokemon2Widget.setAdapter(this.pokemon2Adapter);
        this.pokemon2Widget.setTitle(R.string.combat_pokemon2_dialog_title);
        this.dresseur1Adapter = 
                new SingleEntityWidget.EntityAdapter<Dresseur>() {
            @Override
            public String entityToString(Dresseur item) {
                return String.valueOf(item.getId());
            }
        };
        this.dresseur1Widget =
            (SingleEntityWidget) view.findViewById(R.id.combat_dresseur1_button);
        this.dresseur1Widget.setAdapter(this.dresseur1Adapter);
        this.dresseur1Widget.setTitle(R.string.combat_dresseur1_dialog_title);
        this.dresseur2Adapter = 
                new SingleEntityWidget.EntityAdapter<Dresseur>() {
            @Override
            public String entityToString(Dresseur item) {
                return String.valueOf(item.getId());
            }
        };
        this.dresseur2Widget =
            (SingleEntityWidget) view.findViewById(R.id.combat_dresseur2_button);
        this.dresseur2Widget.setAdapter(this.dresseur2Adapter);
        this.dresseur2Widget.setTitle(R.string.combat_dresseur2_dialog_title);
        this.dresseur1VainqueurView =
                (CheckBox) view.findViewById(R.id.combat_dresseur1vainqueur);
        this.dresseur2VainqueurView =
                (CheckBox) view.findViewById(R.id.combat_dresseur2vainqueur);
        this.pokemon1VainqueurView =
                (CheckBox) view.findViewById(R.id.combat_pokemon1vainqueur);
        this.pokemon2VainqueurView =
                (CheckBox) view.findViewById(R.id.combat_pokemon2vainqueur);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getLanceLe() != null) {
            this.lanceLeView.setDateTime(this.model.getLanceLe());
        }
        this.dureeView.setText(String.valueOf(this.model.getDuree()));
        this.dresseur1VainqueurView.setChecked(this.model.isDresseur1Vainqueur());
        this.dresseur2VainqueurView.setChecked(this.model.isDresseur2Vainqueur());
        this.pokemon1VainqueurView.setChecked(this.model.isPokemon1Vainqueur());
        this.pokemon2VainqueurView.setChecked(this.model.isPokemon2Vainqueur());

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setLanceLe(this.lanceLeView.getDateTime());

        this.model.setDuree(Integer.parseInt(
                    this.dureeView.getEditableText().toString()));

        this.model.setPokemon1(this.pokemon1Adapter.getSelectedItem());

        this.model.setPokemon2(this.pokemon2Adapter.getSelectedItem());

        this.model.setDresseur1(this.dresseur1Adapter.getSelectedItem());

        this.model.setDresseur2(this.dresseur2Adapter.getSelectedItem());

        this.model.setDresseur1Vainqueur(this.dresseur1VainqueurView.isChecked());

        this.model.setDresseur2Vainqueur(this.dresseur2VainqueurView.isChecked());

        this.model.setPokemon1Vainqueur(this.pokemon1VainqueurView.isChecked());

        this.model.setPokemon2Vainqueur(this.pokemon2VainqueurView.isChecked());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (this.lanceLeView.getDateTime() == null) {
            error = R.string.combat_lancele_invalid_field_error;
        }
        if (this.pokemon1Adapter.getSelectedItem() == null) {
            error = R.string.combat_pokemon1_invalid_field_error;
        }
        if (this.pokemon2Adapter.getSelectedItem() == null) {
            error = R.string.combat_pokemon2_invalid_field_error;
        }
        if (this.dresseur1Adapter.getSelectedItem() == null) {
            error = R.string.combat_dresseur1_invalid_field_error;
        }
        if (this.dresseur2Adapter.getSelectedItem() == null) {
            error = R.string.combat_dresseur2_invalid_field_error;
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
                R.layout.fragment_combat_create,
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
        private final Combat entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final CombatCreateFragment fragment,
                final Combat entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.combat_progress_save_title),
                    this.ctx.getString(
                            R.string.combat_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new CombatProviderUtils(this.ctx).insert(
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
                                R.string.combat_error_create));
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
        private CombatCreateFragment fragment;
        /** pokemon1 list. */
        private ArrayList<Pokemon> pokemon1List;
        /** pokemon2 list. */
        private ArrayList<Pokemon> pokemon2List;
        /** dresseur1 list. */
        private ArrayList<Dresseur> dresseur1List;
        /** dresseur2 list. */
        private ArrayList<Dresseur> dresseur2List;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final CombatCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.combat_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.combat_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.pokemon1List = 
                new PokemonProviderUtils(this.ctx).queryAll();
            this.pokemon2List = 
                new PokemonProviderUtils(this.ctx).queryAll();
            this.dresseur1List = 
                new DresseurProviderUtils(this.ctx).queryAll();
            this.dresseur2List = 
                new DresseurProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.pokemon1Adapter.loadData(this.pokemon1List);
            this.fragment.pokemon2Adapter.loadData(this.pokemon2List);
            this.fragment.dresseur1Adapter.loadData(this.dresseur1List);
            this.fragment.dresseur2Adapter.loadData(this.dresseur2List);
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
