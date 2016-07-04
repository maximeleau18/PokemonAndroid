/**************************************************************************
 * PersonnageNonJoueurShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueur;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.harmony.view.DeleteDialog;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.maximeleau.harmony.android.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.maximeleau.harmony.android.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/** PersonnageNonJoueur show fragment.
 *
 * This fragment gives you an interface to show a PersonnageNonJoueur.
 * 
 * @see android.app.Fragment
 */
public class PersonnageNonJoueurShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PersonnageNonJoueur model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** description View. */
    protected TextView descriptionView;
    /** profession View. */
    protected TextView professionView;
    /** objets View. */
    protected TextView objetsView;
    /** dresseur View. */
    protected TextView dresseurView;
    /** arenes View. */
    protected TextView arenesView;
    /** pokemons View. */
    protected TextView pokemonsView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PersonnageNonJoueur. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_nom);
        this.descriptionView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_description);
        this.professionView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_profession);
        this.objetsView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_objets);
        this.dresseurView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_dresseur);
        this.arenesView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_arenes);
        this.pokemonsView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueur_pokemons);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.personnagenonjoueur_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.personnagenonjoueur_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getDescription() != null) {
            this.descriptionView.setText(this.model.getDescription());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setText(
                    String.valueOf(this.model.getProfession().getId()));
        }
        if (this.model.getObjets() != null) {
            String objetsValue = "";
            for (Objet item : this.model.getObjets()) {
                objetsValue += item.getId() + ",";
            }
            this.objetsView.setText(objetsValue);
        }
        if (this.model.getDresseur() != null) {
            this.dresseurView.setText(
                    String.valueOf(this.model.getDresseur().getId()));
        }
        if (this.model.getArenes() != null) {
            String arenesValue = "";
            for (Arene item : this.model.getArenes()) {
                arenesValue += item.getId() + ",";
            }
            this.arenesView.setText(arenesValue);
        }
        if (this.model.getPokemons() != null) {
            String pokemonsValue = "";
            for (Pokemon item : this.model.getPokemons()) {
                pokemonsValue += item.getId() + ",";
            }
            this.pokemonsView.setText(pokemonsValue);
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_personnagenonjoueur_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PersonnageNonJoueur) intent.getParcelableExtra(PersonnageNonJoueurContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PersonnageNonJoueur to get the data from.
     */
    public void update(PersonnageNonJoueur item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurShowFragment.this.onPersonnageNonJoueurLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/profession"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurShowFragment.this.onProfessionLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/objets"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurShowFragment.this.onObjetsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/dresseur"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurShowFragment.this.onDresseurLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/arenes"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurShowFragment.this.onArenesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokemons"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurShowFragment.this.onPokemonsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onPersonnageNonJoueurLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PersonnageNonJoueurContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onProfessionLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setProfession(ProfessionContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setProfession(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onObjetsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setObjets(ObjetContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setObjets(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onDresseurLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setDresseur(DresseurContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setDresseur(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onArenesLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setArenes(AreneContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setArenes(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPokemonsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPokemons(PokemonContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPokemons(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PersonnageNonJoueurEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PersonnageNonJoueurEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PersonnageNonJoueurContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private PersonnageNonJoueur item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PersonnageNonJoueurSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PersonnageNonJoueur item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PersonnageNonJoueurProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PersonnageNonJoueurShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

