/**************************************************************************
 * CombatShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.combat;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.harmony.view.DeleteDialog;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.maximeleau.harmony.android.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.maximeleau.harmony.android.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.CombatProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.CombatProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

/** Combat show fragment.
 *
 * This fragment gives you an interface to show a Combat.
 * 
 * @see android.app.Fragment
 */
public class CombatShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Combat model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** lanceLe View. */
    protected TextView lanceLeView;
    /** duree View. */
    protected TextView dureeView;
    /** pokemon1 View. */
    protected TextView pokemon1View;
    /** pokemon2 View. */
    protected TextView pokemon2View;
    /** dresseur1 View. */
    protected TextView dresseur1View;
    /** dresseur2 View. */
    protected TextView dresseur2View;
    /** dresseur1Vainqueur View. */
    protected CheckBox dresseur1VainqueurView;
    /** dresseur2Vainqueur View. */
    protected CheckBox dresseur2VainqueurView;
    /** pokemon1Vainqueur View. */
    protected CheckBox pokemon1VainqueurView;
    /** pokemon2Vainqueur View. */
    protected CheckBox pokemon2VainqueurView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Combat. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.lanceLeView =
            (TextView) view.findViewById(
                    R.id.combat_lancele);
        this.dureeView =
            (TextView) view.findViewById(
                    R.id.combat_duree);
        this.pokemon1View =
            (TextView) view.findViewById(
                    R.id.combat_pokemon1);
        this.pokemon2View =
            (TextView) view.findViewById(
                    R.id.combat_pokemon2);
        this.dresseur1View =
            (TextView) view.findViewById(
                    R.id.combat_dresseur1);
        this.dresseur2View =
            (TextView) view.findViewById(
                    R.id.combat_dresseur2);
        this.dresseur1VainqueurView =
            (CheckBox) view.findViewById(
                    R.id.combat_dresseur1vainqueur);
        this.dresseur1VainqueurView.setEnabled(false);
        this.dresseur2VainqueurView =
            (CheckBox) view.findViewById(
                    R.id.combat_dresseur2vainqueur);
        this.dresseur2VainqueurView.setEnabled(false);
        this.pokemon1VainqueurView =
            (CheckBox) view.findViewById(
                    R.id.combat_pokemon1vainqueur);
        this.pokemon1VainqueurView.setEnabled(false);
        this.pokemon2VainqueurView =
            (CheckBox) view.findViewById(
                    R.id.combat_pokemon2vainqueur);
        this.pokemon2VainqueurView.setEnabled(false);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.combat_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.combat_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getLanceLe() != null) {
            this.lanceLeView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getLanceLe()));
        }
        this.dureeView.setText(String.valueOf(this.model.getDuree()));
        if (this.model.getPokemon1() != null) {
            this.pokemon1View.setText(
                    String.valueOf(this.model.getPokemon1().getId()));
        }
        if (this.model.getPokemon2() != null) {
            this.pokemon2View.setText(
                    String.valueOf(this.model.getPokemon2().getId()));
        }
        if (this.model.getDresseur1() != null) {
            this.dresseur1View.setText(
                    String.valueOf(this.model.getDresseur1().getId()));
        }
        if (this.model.getDresseur2() != null) {
            this.dresseur2View.setText(
                    String.valueOf(this.model.getDresseur2().getId()));
        }
        this.dresseur1VainqueurView.setChecked(this.model.isDresseur1Vainqueur());
        this.dresseur2VainqueurView.setChecked(this.model.isDresseur2Vainqueur());
        this.pokemon1VainqueurView.setChecked(this.model.isPokemon1Vainqueur());
        this.pokemon2VainqueurView.setChecked(this.model.isPokemon2Vainqueur());
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
                        R.layout.fragment_combat_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Combat) intent.getParcelableExtra(CombatContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Combat to get the data from.
     */
    public void update(Combat item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    CombatProviderAdapter.COMBAT_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CombatShowFragment.this.onCombatLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokemon1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CombatShowFragment.this.onPokemon1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokemon2"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CombatShowFragment.this.onPokemon2Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/dresseur1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CombatShowFragment.this.onDresseur1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/dresseur2"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CombatShowFragment.this.onDresseur2Loaded(c);
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
    public void onCombatLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            CombatContract.cursorToItem(
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
    public void onPokemon1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPokemon1(PokemonContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPokemon1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPokemon2Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPokemon2(PokemonContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPokemon2(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onDresseur1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setDresseur1(DresseurContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setDresseur1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onDresseur2Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setDresseur2(DresseurContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setDresseur2(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the CombatEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    CombatEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(CombatContract.PARCEL, this.model);
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
        private Combat item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build CombatSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Combat item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new CombatProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                CombatShowFragment.this.onPostDelete();
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

