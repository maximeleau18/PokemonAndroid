/**************************************************************************
 * PersonnageNonJoueurEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueur;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.widget.MultiEntityWidget;
import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.ProfessionProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.ObjetProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.DresseurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.AreneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/** PersonnageNonJoueur create fragment.
 *
 * This fragment gives you an interface to edit a PersonnageNonJoueur.
 *
 * @see android.app.Fragment
 */
public class PersonnageNonJoueurEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PersonnageNonJoueur model = new PersonnageNonJoueur();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** description View. */
    protected EditText descriptionView;
    /** urlImage View. */
    protected EditText urlImageView;
    /** The profession chooser component. */
    protected SingleEntityWidget professionWidget;
    /** The profession Adapter. */
    protected SingleEntityWidget.EntityAdapter<Profession>
            professionAdapter;
    /** The objets chooser component. */
    protected MultiEntityWidget objetsWidget;
    /** The objets Adapter. */
    protected MultiEntityWidget.EntityAdapter<Objet>
            objetsAdapter;
    /** The dresseurs chooser component. */
    protected MultiEntityWidget dresseursWidget;
    /** The dresseurs Adapter. */
    protected MultiEntityWidget.EntityAdapter<Dresseur>
            dresseursAdapter;
    /** The arenes chooser component. */
    protected MultiEntityWidget arenesWidget;
    /** The arenes Adapter. */
    protected MultiEntityWidget.EntityAdapter<Arene>
            arenesAdapter;
    /** The pokemons chooser component. */
    protected MultiEntityWidget pokemonsWidget;
    /** The pokemons Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokemon>
            pokemonsAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.personnagenonjoueur_nom);
        this.descriptionView = (EditText) view.findViewById(
                R.id.personnagenonjoueur_description);
        this.urlImageView = (EditText) view.findViewById(
                R.id.personnagenonjoueur_urlimage);
        this.professionAdapter =
                new SingleEntityWidget.EntityAdapter<Profession>() {
            @Override
            public String entityToString(Profession item) {
                return String.valueOf(item.getId());
            }
        };
        this.professionWidget =
            (SingleEntityWidget) view.findViewById(R.id.personnagenonjoueur_profession_button);
        this.professionWidget.setAdapter(this.professionAdapter);
        this.professionWidget.setTitle(R.string.personnagenonjoueur_profession_dialog_title);
        this.objetsAdapter =
                new MultiEntityWidget.EntityAdapter<Objet>() {
            @Override
            public String entityToString(Objet item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.personnagenonjoueur_objets_button);
        this.objetsWidget.setAdapter(this.objetsAdapter);
        this.objetsWidget.setTitle(R.string.personnagenonjoueur_objets_dialog_title);
        this.dresseursAdapter =
                new MultiEntityWidget.EntityAdapter<Dresseur>() {
            @Override
            public String entityToString(Dresseur item) {
                return String.valueOf(item.getId());
            }
        };
        this.dresseursWidget = (MultiEntityWidget) view.findViewById(
                        R.id.personnagenonjoueur_dresseurs_button);
        this.dresseursWidget.setAdapter(this.dresseursAdapter);
        this.dresseursWidget.setTitle(R.string.personnagenonjoueur_dresseurs_dialog_title);
        this.arenesAdapter =
                new MultiEntityWidget.EntityAdapter<Arene>() {
            @Override
            public String entityToString(Arene item) {
                return String.valueOf(item.getId());
            }
        };
        this.arenesWidget = (MultiEntityWidget) view.findViewById(
                        R.id.personnagenonjoueur_arenes_button);
        this.arenesWidget.setAdapter(this.arenesAdapter);
        this.arenesWidget.setTitle(R.string.personnagenonjoueur_arenes_dialog_title);
        this.pokemonsAdapter =
                new MultiEntityWidget.EntityAdapter<Pokemon>() {
            @Override
            public String entityToString(Pokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.personnagenonjoueur_pokemons_button);
        this.pokemonsWidget.setAdapter(this.pokemonsAdapter);
        this.pokemonsWidget.setTitle(R.string.personnagenonjoueur_pokemons_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getDescription() != null) {
            this.descriptionView.setText(this.model.getDescription());
        }
        if (this.model.getUrlImage() != null) {
            this.urlImageView.setText(this.model.getUrlImage());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setDescription(this.descriptionView.getEditableText().toString());

        this.model.setUrlImage(this.urlImageView.getEditableText().toString());

        this.model.setProfession(this.professionAdapter.getSelectedItem());

        this.model.setObjets(this.objetsAdapter.getCheckedItems());

        this.model.setDresseurs(this.dresseursAdapter.getCheckedItems());

        this.model.setArenes(this.arenesAdapter.getCheckedItems());

        this.model.setPokemons(this.pokemonsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.personnagenonjoueur_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.descriptionView.getText().toString().trim())) {
            error = R.string.personnagenonjoueur_description_invalid_field_error;
        }
        if (this.professionAdapter.getSelectedItem() == null) {
            error = R.string.personnagenonjoueur_profession_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_personnagenonjoueur_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PersonnageNonJoueur) intent.getParcelableExtra(
                PersonnageNonJoueurContract.PARCEL);

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
        private final PersonnageNonJoueur entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PersonnageNonJoueurEditFragment fragment,
                    final PersonnageNonJoueur entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.personnagenonjoueur_progress_save_title),
                    this.ctx.getString(
                            R.string.personnagenonjoueur_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PersonnageNonJoueurProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PersonnageNonJoueurEditFragment", e.getMessage());
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
                        R.string.personnagenonjoueur_error_edit));
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
        private PersonnageNonJoueurEditFragment fragment;
        /** profession list. */
        private ArrayList<Profession> professionList;
        /** objets list. */
        private ArrayList<Objet> objetsList;
        /** dresseurs list. */
        private ArrayList<Dresseur> dresseursList;
        /** arenes list. */
        private ArrayList<Arene> arenesList;
        /** pokemons list. */
        private ArrayList<Pokemon> pokemonsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PersonnageNonJoueurEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.personnagenonjoueur_progress_load_relations_title),
                this.ctx.getString(
                    R.string.personnagenonjoueur_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.professionList = 
                new ProfessionProviderUtils(this.ctx).queryAll();
            this.objetsList = 
                new ObjetProviderUtils(this.ctx).queryAll();
            this.dresseursList = 
                new DresseurProviderUtils(this.ctx).queryAll();
            this.arenesList = 
                new AreneProviderUtils(this.ctx).queryAll();
            this.pokemonsList = 
                new PokemonProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onProfessionLoaded(this.professionList);
            this.fragment.onObjetsLoaded(this.objetsList);
            this.fragment.onDresseursLoaded(this.dresseursList);
            this.fragment.onArenesLoaded(this.arenesList);
            this.fragment.onPokemonsLoaded(this.pokemonsList);

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
     * Called when profession have been loaded.
     * @param items The loaded items
     */
    protected void onProfessionLoaded(ArrayList<Profession> items) {
        this.professionAdapter.loadData(items);
        
        if (this.model.getProfession() != null) {
            for (Profession item : items) {
                if (item.getId() == this.model.getProfession().getId()) {
                    this.professionAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when objets have been loaded.
     * @param items The loaded items
     */
    protected void onObjetsLoaded(ArrayList<Objet> items) {
        this.objetsAdapter.loadData(items);
        ArrayList<Objet> modelItems = new ArrayList<Objet>();
        for (Objet item : items) {
            if (item.getPersonnageNonJoueur() != null && item.getPersonnageNonJoueur().getId() == this.model.getId()) {
                modelItems.add(item);
                this.objetsAdapter.checkItem(item, true);
            }
        }
        this.model.setObjets(modelItems);
    }
    /**
     * Called when dresseurs have been loaded.
     * @param items The loaded items
     */
    protected void onDresseursLoaded(ArrayList<Dresseur> items) {
        this.dresseursAdapter.loadData(items);
        ArrayList<Dresseur> modelItems = new ArrayList<Dresseur>();
        for (Dresseur item : items) {
            if (item.getPersonnageNonJoueur() != null && item.getPersonnageNonJoueur().getId() == this.model.getId()) {
                modelItems.add(item);
                this.dresseursAdapter.checkItem(item, true);
            }
        }
        this.model.setDresseurs(modelItems);
    }
    /**
     * Called when arenes have been loaded.
     * @param items The loaded items
     */
    protected void onArenesLoaded(ArrayList<Arene> items) {
        this.arenesAdapter.loadData(items);
        ArrayList<Arene> modelItems = new ArrayList<Arene>();
        for (Arene item : items) {
            if (item.getMaitre() != null && item.getMaitre().getId() == this.model.getId()) {
                modelItems.add(item);
                this.arenesAdapter.checkItem(item, true);
            }
        }
        this.model.setArenes(modelItems);
    }
    /**
     * Called when pokemons have been loaded.
     * @param items The loaded items
     */
    protected void onPokemonsLoaded(ArrayList<Pokemon> items) {
        this.pokemonsAdapter.loadData(items);
        ArrayList<Pokemon> modelItems = new ArrayList<Pokemon>();
        for (Pokemon item : items) {
            if (item.getPersonnageNonJoueur() != null && item.getPersonnageNonJoueur().getId() == this.model.getId()) {
                modelItems.add(item);
                this.pokemonsAdapter.checkItem(item, true);
            }
        }
        this.model.setPokemons(modelItems);
    }
}
