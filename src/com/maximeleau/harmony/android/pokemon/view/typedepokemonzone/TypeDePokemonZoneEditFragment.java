/**************************************************************************
 * TypeDePokemonZoneEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonzone;

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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;

import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.ZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/** TypeDePokemonZone create fragment.
 *
 * This fragment gives you an interface to edit a TypeDePokemonZone.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonZoneEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected TypeDePokemonZone model = new TypeDePokemonZone();

    /** curr.fields View. */
    /** The zone chooser component. */
    protected SingleEntityWidget zoneWidget;
    /** The zone Adapter. */
    protected SingleEntityWidget.EntityAdapter<Zone>
            zoneAdapter;
    /** The typeDePokemon chooser component. */
    protected SingleEntityWidget typeDePokemonWidget;
    /** The typeDePokemon Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeDePokemon>
            typeDePokemonAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.zoneAdapter =
                new SingleEntityWidget.EntityAdapter<Zone>() {
            @Override
            public String entityToString(Zone item) {
                return String.valueOf(item.getId());
            }
        };
        this.zoneWidget =
            (SingleEntityWidget) view.findViewById(R.id.typedepokemonzone_zone_button);
        this.zoneWidget.setAdapter(this.zoneAdapter);
        this.zoneWidget.setTitle(R.string.typedepokemonzone_zone_dialog_title);
        this.typeDePokemonAdapter =
                new SingleEntityWidget.EntityAdapter<TypeDePokemon>() {
            @Override
            public String entityToString(TypeDePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeDePokemonWidget =
            (SingleEntityWidget) view.findViewById(R.id.typedepokemonzone_typedepokemon_button);
        this.typeDePokemonWidget.setAdapter(this.typeDePokemonAdapter);
        this.typeDePokemonWidget.setTitle(R.string.typedepokemonzone_typedepokemon_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {


        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setZone(this.zoneAdapter.getSelectedItem());

        this.model.setTypeDePokemon(this.typeDePokemonAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (this.zoneAdapter.getSelectedItem() == null) {
            error = R.string.typedepokemonzone_zone_invalid_field_error;
        }
        if (this.typeDePokemonAdapter.getSelectedItem() == null) {
            error = R.string.typedepokemonzone_typedepokemon_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_typedepokemonzone_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (TypeDePokemonZone) intent.getParcelableExtra(
                TypeDePokemonZoneContract.PARCEL);

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
        private final TypeDePokemonZone entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypeDePokemonZoneEditFragment fragment,
                    final TypeDePokemonZone entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.typedepokemonzone_progress_save_title),
                    this.ctx.getString(
                            R.string.typedepokemonzone_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypeDePokemonZoneProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypeDePokemonZoneEditFragment", e.getMessage());
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
                        R.string.typedepokemonzone_error_edit));
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
        private TypeDePokemonZoneEditFragment fragment;
        /** zone list. */
        private ArrayList<Zone> zoneList;
        /** typeDePokemon list. */
        private ArrayList<TypeDePokemon> typeDePokemonList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypeDePokemonZoneEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.typedepokemonzone_progress_load_relations_title),
                this.ctx.getString(
                    R.string.typedepokemonzone_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.zoneList = 
                new ZoneProviderUtils(this.ctx).queryAll();
            this.typeDePokemonList = 
                new TypeDePokemonProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onZoneLoaded(this.zoneList);
            this.fragment.onTypeDePokemonLoaded(this.typeDePokemonList);

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
     * Called when zone have been loaded.
     * @param items The loaded items
     */
    protected void onZoneLoaded(ArrayList<Zone> items) {
        this.zoneAdapter.loadData(items);
        
        if (this.model.getZone() != null) {
            for (Zone item : items) {
                if (item.getId() == this.model.getZone().getId()) {
                    this.zoneAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when typeDePokemon have been loaded.
     * @param items The loaded items
     */
    protected void onTypeDePokemonLoaded(ArrayList<TypeDePokemon> items) {
        this.typeDePokemonAdapter.loadData(items);
        
        if (this.model.getTypeDePokemon() != null) {
            for (TypeDePokemon item : items) {
                if (item.getId() == this.model.getTypeDePokemon().getId()) {
                    this.typeDePokemonAdapter.selectItem(item);
                }
            }
        }
    }
}
