/**************************************************************************
 * DresseurShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.dresseur;


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
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.harmony.view.DeleteDialog;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.maximeleau.harmony.android.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.maximeleau.harmony.android.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.DresseurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.DresseurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/** Dresseur show fragment.
 *
 * This fragment gives you an interface to show a Dresseur.
 * 
 * @see android.app.Fragment
 */
public class DresseurShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Dresseur model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** prenom View. */
    protected TextView prenomView;
    /** login View. */
    protected TextView loginView;
    /** motDePasse View. */
    protected TextView motDePasseView;
    /** personnageNonJoueur View. */
    protected TextView personnageNonJoueurView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Dresseur. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.dresseur_nom);
        this.prenomView =
            (TextView) view.findViewById(
                    R.id.dresseur_prenom);
        this.loginView =
            (TextView) view.findViewById(
                    R.id.dresseur_login);
        this.motDePasseView =
            (TextView) view.findViewById(
                    R.id.dresseur_motdepasse);
        this.personnageNonJoueurView =
            (TextView) view.findViewById(
                    R.id.dresseur_personnagenonjoueur);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.dresseur_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.dresseur_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getPrenom() != null) {
            this.prenomView.setText(this.model.getPrenom());
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getMotDePasse() != null) {
            this.motDePasseView.setText(this.model.getMotDePasse());
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
                        R.layout.fragment_dresseur_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Dresseur) intent.getParcelableExtra(DresseurContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Dresseur to get the data from.
     */
    public void update(Dresseur item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    DresseurProviderAdapter.DRESSEUR_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    DresseurShowFragment.this.onDresseurLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/personnagenonjoueur"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    DresseurShowFragment.this.onPersonnageNonJoueurLoaded(c);
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
    public void onDresseurLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            DresseurContract.cursorToItem(
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
     * Calls the DresseurEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    DresseurEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(DresseurContract.PARCEL, this.model);
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
        private Dresseur item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build DresseurSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Dresseur item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new DresseurProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                DresseurShowFragment.this.onPostDelete();
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

