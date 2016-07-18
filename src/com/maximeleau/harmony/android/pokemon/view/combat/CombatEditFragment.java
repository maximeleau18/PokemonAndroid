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
import com.maximeleau.harmony.android.pokemon.harmony.widget.MultiEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.CombatProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.CombatProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.DresseurProviderUtils;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
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
    protected MultiEntityWidget pokemon1Widget;
    /** The pokemon1 Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokemon>
            pokemon1Adapter;
    /** The pokemon2 chooser component. */
    protected MultiEntityWidget pokemon2Widget;
    /** The pokemon2 Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokemon>
            pokemon2Adapter;
    /** The dresseur1 chooser component. */
    protected MultiEntityWidget dresseur1Widget;
    /** The dresseur1 Adapter. */
    protected MultiEntityWidget.EntityAdapter<Dresseur>
            dresseur1Adapter;
    /** The dresseur2 chooser component. */
    protected MultiEntityWidget dresseur2Widget;
    /** The dresseur2 Adapter. */
    protected MultiEntityWidget.EntityAdapter<Dresseur>
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
                new MultiEntityWidget.EntityAdapter<Pokemon>() {
            @Override
            public String entityToString(Pokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemon1Widget = (MultiEntityWidget) view.findViewById(
                        R.id.combat_pokemon1_button);
        this.pokemon1Widget.setAdapter(this.pokemon1Adapter);
        this.pokemon1Widget.setTitle(R.string.combat_pokemon1_dialog_title);
        this.pokemon2Adapter =
                new MultiEntityWidget.EntityAdapter<Pokemon>() {
            @Override
            public String entityToString(Pokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemon2Widget = (MultiEntityWidget) view.findViewById(
                        R.id.combat_pokemon2_button);
        this.pokemon2Widget.setAdapter(this.pokemon2Adapter);
        this.pokemon2Widget.setTitle(R.string.combat_pokemon2_dialog_title);
        this.dresseur1Adapter =
                new MultiEntityWidget.EntityAdapter<Dresseur>() {
            @Override
            public String entityToString(Dresseur item) {
                return String.valueOf(item.getId());
            }
        };
        this.dresseur1Widget = (MultiEntityWidget) view.findViewById(
                        R.id.combat_dresseur1_button);
        this.dresseur1Widget.setAdapter(this.dresseur1Adapter);
        this.dresseur1Widget.setTitle(R.string.combat_dresseur1_dialog_title);
        this.dresseur2Adapter =
                new MultiEntityWidget.EntityAdapter<Dresseur>() {
            @Override
            public String entityToString(Dresseur item) {
                return String.valueOf(item.getId());
            }
        };
        this.dresseur2Widget = (MultiEntityWidget) view.findViewById(
                        R.id.combat_dresseur2_button);
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

        this.model.setPokemon1(this.pokemon1Adapter.getCheckedItems());

        this.model.setPokemon2(this.pokemon2Adapter.getCheckedItems());

        this.model.setDresseur1(this.dresseur1Adapter.getCheckedItems());

        this.model.setDresseur2(this.dresseur2Adapter.getCheckedItems());

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
        if (this.pokemon1Adapter.getCheckedItems().isEmpty()) {
            error = R.string.combat_pokemon1_invalid_field_error;
        }
        if (this.pokemon2Adapter.getCheckedItems().isEmpty()) {
            error = R.string.combat_pokemon2_invalid_field_error;
        }
        if (this.dresseur1Adapter.getCheckedItems().isEmpty()) {
            error = R.string.combat_dresseur1_invalid_field_error;
        }
        if (this.dresseur2Adapter.getCheckedItems().isEmpty()) {
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
    /** pokemon1 list. */
        private ArrayList<Pokemon> associatedPokemon1List;
        /** pokemon2 list. */
        private ArrayList<Pokemon> pokemon2List;
    /** pokemon2 list. */
        private ArrayList<Pokemon> associatedPokemon2List;
        /** dresseur1 list. */
        private ArrayList<Dresseur> dresseur1List;
    /** dresseur1 list. */
        private ArrayList<Dresseur> associatedDresseur1List;
        /** dresseur2 list. */
        private ArrayList<Dresseur> dresseur2List;
    /** dresseur2 list. */
        private ArrayList<Dresseur> associatedDresseur2List;

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
            Uri pokemon1Uri = CombatProviderAdapter.COMBAT_URI;
            pokemon1Uri = Uri.withAppendedPath(pokemon1Uri, 
                                    String.valueOf(this.fragment.model.getId()));
            pokemon1Uri = Uri.withAppendedPath(pokemon1Uri, "pokemon1");
            android.database.Cursor pokemon1Cursor = 
                    this.ctx.getContentResolver().query(
                            pokemon1Uri,
                            new String[]{PokemonContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPokemon1List = new ArrayList<Pokemon>();
            if (pokemon1Cursor != null && pokemon1Cursor.getCount() > 0) {
                while (pokemon1Cursor.moveToNext()) {
                    int pokemon1Id = pokemon1Cursor.getInt(
                            pokemon1Cursor.getColumnIndex(PokemonContract.COL_ID));
                    for (Pokemon pokemon1 : this.pokemon1List) {
                        if (pokemon1.getId() ==  pokemon1Id) {
                            this.associatedPokemon1List.add(pokemon1);
                        }
                    }
                }
                pokemon1Cursor.close();
            }
            this.pokemon2List = 
                new PokemonProviderUtils(this.ctx).queryAll();
            Uri pokemon2Uri = CombatProviderAdapter.COMBAT_URI;
            pokemon2Uri = Uri.withAppendedPath(pokemon2Uri, 
                                    String.valueOf(this.fragment.model.getId()));
            pokemon2Uri = Uri.withAppendedPath(pokemon2Uri, "pokemon2");
            android.database.Cursor pokemon2Cursor = 
                    this.ctx.getContentResolver().query(
                            pokemon2Uri,
                            new String[]{PokemonContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPokemon2List = new ArrayList<Pokemon>();
            if (pokemon2Cursor != null && pokemon2Cursor.getCount() > 0) {
                while (pokemon2Cursor.moveToNext()) {
                    int pokemon2Id = pokemon2Cursor.getInt(
                            pokemon2Cursor.getColumnIndex(PokemonContract.COL_ID));
                    for (Pokemon pokemon2 : this.pokemon2List) {
                        if (pokemon2.getId() ==  pokemon2Id) {
                            this.associatedPokemon2List.add(pokemon2);
                        }
                    }
                }
                pokemon2Cursor.close();
            }
            this.dresseur1List = 
                new DresseurProviderUtils(this.ctx).queryAll();
            Uri dresseur1Uri = CombatProviderAdapter.COMBAT_URI;
            dresseur1Uri = Uri.withAppendedPath(dresseur1Uri, 
                                    String.valueOf(this.fragment.model.getId()));
            dresseur1Uri = Uri.withAppendedPath(dresseur1Uri, "dresseur1");
            android.database.Cursor dresseur1Cursor = 
                    this.ctx.getContentResolver().query(
                            dresseur1Uri,
                            new String[]{DresseurContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedDresseur1List = new ArrayList<Dresseur>();
            if (dresseur1Cursor != null && dresseur1Cursor.getCount() > 0) {
                while (dresseur1Cursor.moveToNext()) {
                    int dresseur1Id = dresseur1Cursor.getInt(
                            dresseur1Cursor.getColumnIndex(DresseurContract.COL_ID));
                    for (Dresseur dresseur1 : this.dresseur1List) {
                        if (dresseur1.getId() ==  dresseur1Id) {
                            this.associatedDresseur1List.add(dresseur1);
                        }
                    }
                }
                dresseur1Cursor.close();
            }
            this.dresseur2List = 
                new DresseurProviderUtils(this.ctx).queryAll();
            Uri dresseur2Uri = CombatProviderAdapter.COMBAT_URI;
            dresseur2Uri = Uri.withAppendedPath(dresseur2Uri, 
                                    String.valueOf(this.fragment.model.getId()));
            dresseur2Uri = Uri.withAppendedPath(dresseur2Uri, "dresseur2");
            android.database.Cursor dresseur2Cursor = 
                    this.ctx.getContentResolver().query(
                            dresseur2Uri,
                            new String[]{DresseurContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedDresseur2List = new ArrayList<Dresseur>();
            if (dresseur2Cursor != null && dresseur2Cursor.getCount() > 0) {
                while (dresseur2Cursor.moveToNext()) {
                    int dresseur2Id = dresseur2Cursor.getInt(
                            dresseur2Cursor.getColumnIndex(DresseurContract.COL_ID));
                    for (Dresseur dresseur2 : this.dresseur2List) {
                        if (dresseur2.getId() ==  dresseur2Id) {
                            this.associatedDresseur2List.add(dresseur2);
                        }
                    }
                }
                dresseur2Cursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setPokemon1(this.associatedPokemon1List);
            this.fragment.onPokemon1Loaded(this.pokemon1List);
            this.fragment.model.setPokemon2(this.associatedPokemon2List);
            this.fragment.onPokemon2Loaded(this.pokemon2List);
            this.fragment.model.setDresseur1(this.associatedDresseur1List);
            this.fragment.onDresseur1Loaded(this.dresseur1List);
            this.fragment.model.setDresseur2(this.associatedDresseur2List);
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
        this.pokemon1Adapter.setCheckedItems(this.model.getPokemon1());
    }
    /**
     * Called when pokemon2 have been loaded.
     * @param items The loaded items
     */
    protected void onPokemon2Loaded(ArrayList<Pokemon> items) {
        this.pokemon2Adapter.loadData(items);
        this.pokemon2Adapter.setCheckedItems(this.model.getPokemon2());
    }
    /**
     * Called when dresseur1 have been loaded.
     * @param items The loaded items
     */
    protected void onDresseur1Loaded(ArrayList<Dresseur> items) {
        this.dresseur1Adapter.loadData(items);
        this.dresseur1Adapter.setCheckedItems(this.model.getDresseur1());
    }
    /**
     * Called when dresseur2 have been loaded.
     * @param items The loaded items
     */
    protected void onDresseur2Loaded(ArrayList<Dresseur> items) {
        this.dresseur2Adapter.loadData(items);
        this.dresseur2Adapter.setCheckedItems(this.model.getDresseur2());
    }
}
