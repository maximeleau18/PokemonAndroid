/**************************************************************************
 * TypeDePokemonEvolutionEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution;

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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;

import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonEvolutionProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/** TypeDePokemonEvolution create fragment.
 *
 * This fragment gives you an interface to edit a TypeDePokemonEvolution.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonEvolutionEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected TypeDePokemonEvolution model = new TypeDePokemonEvolution();

    /** curr.fields View. */
    /** The evolueEn chooser component. */
    protected SingleEntityWidget evolueEnWidget;
    /** The evolueEn Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeDePokemon>
            evolueEnAdapter;
    /** The estEvolueEn chooser component. */
    protected SingleEntityWidget estEvolueEnWidget;
    /** The estEvolueEn Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeDePokemon>
            estEvolueEnAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.evolueEnAdapter =
                new SingleEntityWidget.EntityAdapter<TypeDePokemon>() {
            @Override
            public String entityToString(TypeDePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.evolueEnWidget =
            (SingleEntityWidget) view.findViewById(R.id.typedepokemonevolution_evolueen_button);
        this.evolueEnWidget.setAdapter(this.evolueEnAdapter);
        this.evolueEnWidget.setTitle(R.string.typedepokemonevolution_evolueen_dialog_title);
        this.estEvolueEnAdapter =
                new SingleEntityWidget.EntityAdapter<TypeDePokemon>() {
            @Override
            public String entityToString(TypeDePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.estEvolueEnWidget =
            (SingleEntityWidget) view.findViewById(R.id.typedepokemonevolution_estevolueen_button);
        this.estEvolueEnWidget.setAdapter(this.estEvolueEnAdapter);
        this.estEvolueEnWidget.setTitle(R.string.typedepokemonevolution_estevolueen_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {


        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setEvolueEn(this.evolueEnAdapter.getSelectedItem());

        this.model.setEstEvolueEn(this.estEvolueEnAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (this.evolueEnAdapter.getSelectedItem() == null) {
            error = R.string.typedepokemonevolution_evolueen_invalid_field_error;
        }
        if (this.estEvolueEnAdapter.getSelectedItem() == null) {
            error = R.string.typedepokemonevolution_estevolueen_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_typedepokemonevolution_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (TypeDePokemonEvolution) intent.getParcelableExtra(
                TypeDePokemonEvolutionContract.PARCEL);

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
        private final TypeDePokemonEvolution entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypeDePokemonEvolutionEditFragment fragment,
                    final TypeDePokemonEvolution entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.typedepokemonevolution_progress_save_title),
                    this.ctx.getString(
                            R.string.typedepokemonevolution_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypeDePokemonEvolutionProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypeDePokemonEvolutionEditFragment", e.getMessage());
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
                        R.string.typedepokemonevolution_error_edit));
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
        private TypeDePokemonEvolutionEditFragment fragment;
        /** evolueEn list. */
        private ArrayList<TypeDePokemon> evolueEnList;
        /** estEvolueEn list. */
        private ArrayList<TypeDePokemon> estEvolueEnList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypeDePokemonEvolutionEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.typedepokemonevolution_progress_load_relations_title),
                this.ctx.getString(
                    R.string.typedepokemonevolution_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.evolueEnList = 
                new TypeDePokemonProviderUtils(this.ctx).queryAll();
            this.estEvolueEnList = 
                new TypeDePokemonProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onEvolueEnLoaded(this.evolueEnList);
            this.fragment.onEstEvolueEnLoaded(this.estEvolueEnList);

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
     * Called when evolueEn have been loaded.
     * @param items The loaded items
     */
    protected void onEvolueEnLoaded(ArrayList<TypeDePokemon> items) {
        this.evolueEnAdapter.loadData(items);
        
        if (this.model.getEvolueEn() != null) {
            for (TypeDePokemon item : items) {
                if (item.getId() == this.model.getEvolueEn().getId()) {
                    this.evolueEnAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when estEvolueEn have been loaded.
     * @param items The loaded items
     */
    protected void onEstEvolueEnLoaded(ArrayList<TypeDePokemon> items) {
        this.estEvolueEnAdapter.loadData(items);
        
        if (this.model.getEstEvolueEn() != null) {
            for (TypeDePokemon item : items) {
                if (item.getId() == this.model.getEstEvolueEn().getId()) {
                    this.estEvolueEnAdapter.selectItem(item);
                }
            }
        }
    }
}
