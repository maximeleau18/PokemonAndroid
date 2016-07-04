/**************************************************************************
 * PersonnageNonJoueurBadgeShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge;


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
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.harmony.view.DeleteDialog;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.maximeleau.harmony.android.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.maximeleau.harmony.android.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurBadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurBadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;

/** PersonnageNonJoueurBadge show fragment.
 *
 * This fragment gives you an interface to show a PersonnageNonJoueurBadge.
 * 
 * @see android.app.Fragment
 */
public class PersonnageNonJoueurBadgeShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PersonnageNonJoueurBadge model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** personnageNonJoueur View. */
    protected TextView personnageNonJoueurView;
    /** badge View. */
    protected TextView badgeView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PersonnageNonJoueurBadge. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.personnageNonJoueurView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueurbadge_personnagenonjoueur);
        this.badgeView =
            (TextView) view.findViewById(
                    R.id.personnagenonjoueurbadge_badge);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.personnagenonjoueurbadge_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.personnagenonjoueurbadge_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getPersonnageNonJoueur() != null) {
            this.personnageNonJoueurView.setText(
                    String.valueOf(this.model.getPersonnageNonJoueur().getId()));
        }
        if (this.model.getBadge() != null) {
            this.badgeView.setText(
                    String.valueOf(this.model.getBadge().getId()));
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
                        R.layout.fragment_personnagenonjoueurbadge_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PersonnageNonJoueurBadge) intent.getParcelableExtra(PersonnageNonJoueurBadgeContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PersonnageNonJoueurBadge to get the data from.
     */
    public void update(PersonnageNonJoueurBadge item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurBadgeShowFragment.this.onPersonnageNonJoueurBadgeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/personnagenonjoueur"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurBadgeShowFragment.this.onPersonnageNonJoueurLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badge"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PersonnageNonJoueurBadgeShowFragment.this.onBadgeLoaded(c);
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
    public void onPersonnageNonJoueurBadgeLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PersonnageNonJoueurBadgeContract.cursorToItem(
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
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBadgeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setBadge(BadgeContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setBadge(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PersonnageNonJoueurBadgeEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PersonnageNonJoueurBadgeEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PersonnageNonJoueurBadgeContract.PARCEL, this.model);
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
        private PersonnageNonJoueurBadge item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PersonnageNonJoueurBadgeSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PersonnageNonJoueurBadge item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PersonnageNonJoueurBadgeProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PersonnageNonJoueurBadgeShowFragment.this.onPostDelete();
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

