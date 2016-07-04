/**************************************************************************
 * ZoneShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.zone;


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
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.harmony.view.DeleteDialog;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader;
import com.maximeleau.harmony.android.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.maximeleau.harmony.android.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.maximeleau.harmony.android.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.ZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;

/** Zone show fragment.
 *
 * This fragment gives you an interface to show a Zone.
 * 
 * @see android.app.Fragment
 */
public class ZoneShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Zone model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** arenes View. */
    protected TextView arenesView;
    /** badges View. */
    protected TextView badgesView;
    /** positions View. */
    protected TextView positionsView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Zone. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.zone_nom);
        this.arenesView =
            (TextView) view.findViewById(
                    R.id.zone_arenes);
        this.badgesView =
            (TextView) view.findViewById(
                    R.id.zone_badges);
        this.positionsView =
            (TextView) view.findViewById(
                    R.id.zone_positions);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.zone_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.zone_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getArenes() != null) {
            String arenesValue = "";
            for (Arene item : this.model.getArenes()) {
                arenesValue += item.getId() + ",";
            }
            this.arenesView.setText(arenesValue);
        }
        if (this.model.getBadges() != null) {
            String badgesValue = "";
            for (Badge item : this.model.getBadges()) {
                badgesValue += item.getId() + ",";
            }
            this.badgesView.setText(badgesValue);
        }
        if (this.model.getPositions() != null) {
            String positionsValue = "";
            for (Position item : this.model.getPositions()) {
                positionsValue += item.getId() + ",";
            }
            this.positionsView.setText(positionsValue);
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
                        R.layout.fragment_zone_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Zone) intent.getParcelableExtra(ZoneContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Zone to get the data from.
     */
    public void update(Zone item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ZoneProviderAdapter.ZONE_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ZoneShowFragment.this.onZoneLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/arenes"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ZoneShowFragment.this.onArenesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badges"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ZoneShowFragment.this.onBadgesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/positions"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ZoneShowFragment.this.onPositionsLoaded(c);
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
    public void onZoneLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ZoneContract.cursorToItem(
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
    public void onBadgesLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setBadges(BadgeContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setBadges(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPositionsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPositions(PositionContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPositions(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the ZoneEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ZoneEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ZoneContract.PARCEL, this.model);
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
        private Zone item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ZoneSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Zone item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ZoneProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ZoneShowFragment.this.onPostDelete();
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

