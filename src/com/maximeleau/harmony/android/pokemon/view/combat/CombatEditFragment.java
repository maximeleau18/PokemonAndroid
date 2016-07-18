/**************************************************************************
 * CombatEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.combat;

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
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

/** Combat create fragment.
 *
 * This fragment gives you an interface to edit a Combat.
 *
 * @see android.app.Fragment
 */
public class CombatEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Combat model = new Combat();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.lanceLeView = (DateTimeWidget) view.findViewById(
                R.id.combat_lancele);
        this.dureeView = (EditText) view.findViewById(
                R.id.combat_duree);
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
        this.dresseur1VainqueurView = (CheckBox) view.findViewById(
                R.id.combat_dresseur1vainqueur);
        this.dresseur2VainqueurView = (CheckBox) view.findViewById(
                R.id.combat_dresseur2vainqueur);
        this.pokemon1VainqueurView = (CheckBox) view.findViewById(
                R.id.combat_pokemon1vainqueur);
        this.pokemon2VainqueurView = (CheckBox) view.findViewById(
                R.id.combat_pokemon2vainqueur);
    }

    /** Load data from model to curr.fields view. */
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

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_combat_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Combat) intent.getParcelableExtra(
                CombatContract.PARCEL);

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
        private final Combat entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final CombatEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new CombatProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("CombatEditFragment", e.getMessage());
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
                        R.string.combat_error_edit));
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
        private CombatEditFragment fragment;
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
        public LoadTask(final CombatEditFragment fragment) {
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
            this.fragment.onPokemon1Loaded(this.pokemon1List);
            this.fragment.onPokemon2Loaded(this.pokemon2List);
            this.fragment.onDresseur1Loaded(this.dresseur1List);
            this.fragment.onDresseur2Loaded(this.dresseur2List);

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
     * Called when pokemon1 have been loaded.
     * @param items The loaded items
     */
    protected void onPokemon1Loaded(ArrayList<Pokemon> items) {
        this.pokemon1Adapter.loadData(items);
        
        if (this.model.getPokemon1() != null) {
            for (Pokemon item : items) {
                if (item.getId() == this.model.getPokemon1().getId()) {
                    this.pokemon1Adapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when pokemon2 have been loaded.
     * @param items The loaded items
     */
    protected void onPokemon2Loaded(ArrayList<Pokemon> items) {
        this.pokemon2Adapter.loadData(items);
        
        if (this.model.getPokemon2() != null) {
            for (Pokemon item : items) {
                if (item.getId() == this.model.getPokemon2().getId()) {
                    this.pokemon2Adapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when dresseur1 have been loaded.
     * @param items The loaded items
     */
    protected void onDresseur1Loaded(ArrayList<Dresseur> items) {
        this.dresseur1Adapter.loadData(items);
        
        if (this.model.getDresseur1() != null) {
            for (Dresseur item : items) {
                if (item.getId() == this.model.getDresseur1().getId()) {
                    this.dresseur1Adapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when dresseur2 have been loaded.
     * @param items The loaded items
     */
    protected void onDresseur2Loaded(ArrayList<Dresseur> items) {
        this.dresseur2Adapter.loadData(items);
        
        if (this.model.getDresseur2() != null) {
            for (Dresseur item : items) {
                if (item.getId() == this.model.getDresseur2().getId()) {
                    this.dresseur2Adapter.selectItem(item);
                }
            }
        }
    }
}
