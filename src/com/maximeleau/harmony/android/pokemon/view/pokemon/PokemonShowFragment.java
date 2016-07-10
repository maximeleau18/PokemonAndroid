/**************************************************************************
 * PokemonShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.pokemon;


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
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.harmony.view.DeleteDialog;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.maximeleau.harmony.android.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.maximeleau.harmony.android.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/** Pokemon show fragment.
 *
 * This fragment gives you an interface to show a Pokemon.
 * 
 * @see android.app.Fragment
 */
public class PokemonShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Pokemon model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** surnom View. */
    protected TextView surnomView;
    /** niveau View. */
    protected TextView niveauView;
    /** captureLe View. */
    protected TextView captureLeView;
    /** attaque1 View. */
    protected TextView attaque1View;
    /** attaque2 View. */
    protected TextView attaque2View;
    /** attaque3 View. */
    protected TextView attaque3View;
    /** attaque4 View. */
    protected TextView attaque4View;
    /** typeDePokemon View. */
    protected TextView typeDePokemonView;
    /** personnageNonJoueur View. */
    protected TextView personnageNonJoueurView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Pokemon. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.surnomView =
            (TextView) view.findViewById(
                    R.id.pokemon_surnom);
        this.niveauView =
            (TextView) view.findViewById(
                    R.id.pokemon_niveau);
        this.captureLeView =
            (TextView) view.findViewById(
                    R.id.pokemon_capturele);
        this.attaque1View =
            (TextView) view.findViewById(
                    R.id.pokemon_attaque1);
        this.attaque2View =
            (TextView) view.findViewById(
                    R.id.pokemon_attaque2);
        this.attaque3View =
            (TextView) view.findViewById(
                    R.id.pokemon_attaque3);
        this.attaque4View =
            (TextView) view.findViewById(
                    R.id.pokemon_attaque4);
        this.typeDePokemonView =
            (TextView) view.findViewById(
                    R.id.pokemon_typedepokemon);
        this.personnageNonJoueurView =
            (TextView) view.findViewById(
                    R.id.pokemon_personnagenonjoueur);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokemon_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokemon_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getSurnom() != null) {
            this.surnomView.setText(this.model.getSurnom());
        }
        this.niveauView.setText(String.valueOf(this.model.getNiveau()));
        if (this.model.getCaptureLe() != null) {
            this.captureLeView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getCaptureLe()));
        }
        if (this.model.getAttaque1() != null) {
            this.attaque1View.setText(
                    String.valueOf(this.model.getAttaque1().getId()));
        }
        if (this.model.getAttaque2() != null) {
            this.attaque2View.setText(
                    String.valueOf(this.model.getAttaque2().getId()));
        }
        if (this.model.getAttaque3() != null) {
            this.attaque3View.setText(
                    String.valueOf(this.model.getAttaque3().getId()));
        }
        if (this.model.getAttaque4() != null) {
            this.attaque4View.setText(
                    String.valueOf(this.model.getAttaque4().getId()));
        }
        if (this.model.getTypeDePokemon() != null) {
            this.typeDePokemonView.setText(
                    String.valueOf(this.model.getTypeDePokemon().getId()));
        }
        if (this.model.getPersonnageNonJoueur() != null) {
            this.personnageNonJoueurView.setText(
                    String.valueOf(this.model.getPersonnageNonJoueur().getId()));
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
                        R.layout.fragment_pokemon_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Pokemon) intent.getParcelableExtra(PokemonContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Pokemon to get the data from.
     */
    public void update(Pokemon item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokemonProviderAdapter.POKEMON_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onPokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onAttaque1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque2"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onAttaque2Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque3"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onAttaque3Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque4"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onAttaque4Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/typedepokemon"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onTypeDePokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/personnagenonjoueur"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonShowFragment.this.onPersonnageNonJoueurLoaded(c);
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
    public void onPokemonLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokemonContract.cursorToItem(
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
    public void onAttaque1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque1(AttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque2Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque2(AttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque2(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque3Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque3(AttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque3(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque4Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque4(AttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque4(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onTypeDePokemonLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setTypeDePokemon(TypeDePokemonContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setTypeDePokemon(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPersonnageNonJoueurLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPersonnageNonJoueur(PersonnageNonJoueurContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPersonnageNonJoueur(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokemonEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokemonEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokemonContract.PARCEL, this.model);
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
        private Pokemon item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokemonSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Pokemon item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokemonProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokemonShowFragment.this.onPostDelete();
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

