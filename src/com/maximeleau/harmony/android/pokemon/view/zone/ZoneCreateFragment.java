/**************************************************************************
 * ZoneCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.zone;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
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
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.widget.MultiEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.utils.ZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.AreneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.BadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PositionProviderUtils;

/**
 * Zone create fragment.
 *
 * This fragment gives you an interface to create a Zone.
 */
public class ZoneCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Zone model = new Zone();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** The arenes chooser component. */
    protected MultiEntityWidget arenesWidget;
    /** The arenes Adapter. */
    protected MultiEntityWidget.EntityAdapter<Arene> 
                arenesAdapter;
    /** The badges chooser component. */
    protected MultiEntityWidget badgesWidget;
    /** The badges Adapter. */
    protected MultiEntityWidget.EntityAdapter<Badge> 
                badgesAdapter;
    /** The positions chooser component. */
    protected MultiEntityWidget positionsWidget;
    /** The positions Adapter. */
    protected MultiEntityWidget.EntityAdapter<Position> 
                positionsAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.zone_nom);
        this.arenesAdapter = 
                new MultiEntityWidget.EntityAdapter<Arene>() {
            @Override
            public String entityToString(Arene item) {
                return String.valueOf(item.getId());
            }
        };
        this.arenesWidget =
            (MultiEntityWidget) view.findViewById(R.id.zone_arenes_button);
        this.arenesWidget.setAdapter(this.arenesAdapter);
        this.arenesWidget.setTitle(R.string.zone_arenes_dialog_title);
        this.badgesAdapter = 
                new MultiEntityWidget.EntityAdapter<Badge>() {
            @Override
            public String entityToString(Badge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgesWidget =
            (MultiEntityWidget) view.findViewById(R.id.zone_badges_button);
        this.badgesWidget.setAdapter(this.badgesAdapter);
        this.badgesWidget.setTitle(R.string.zone_badges_dialog_title);
        this.positionsAdapter = 
                new MultiEntityWidget.EntityAdapter<Position>() {
            @Override
            public String entityToString(Position item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionsWidget =
            (MultiEntityWidget) view.findViewById(R.id.zone_positions_button);
        this.positionsWidget.setAdapter(this.positionsAdapter);
        this.positionsWidget.setTitle(R.string.zone_positions_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setArenes(this.arenesAdapter.getCheckedItems());

        this.model.setBadges(this.badgesAdapter.getCheckedItems());

        this.model.setPositions(this.positionsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.zone_nom_invalid_field_error;
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
        final View view = inflater.inflate(
                R.layout.fragment_zone_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Zone entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final ZoneCreateFragment fragment,
                final Zone entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.zone_progress_save_title),
                    this.ctx.getString(
                            R.string.zone_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new ZoneProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.zone_error_create));
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
        private ZoneCreateFragment fragment;
        /** arenes list. */
        private ArrayList<Arene> arenesList;
        /** badges list. */
        private ArrayList<Badge> badgesList;
        /** positions list. */
        private ArrayList<Position> positionsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ZoneCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.zone_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.zone_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.arenesList = 
                new AreneProviderUtils(this.ctx).queryAll();
            this.badgesList = 
                new BadgeProviderUtils(this.ctx).queryAll();
            this.positionsList = 
                new PositionProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.arenesAdapter.loadData(this.arenesList);
            this.fragment.badgesAdapter.loadData(this.badgesList);
            this.fragment.positionsAdapter.loadData(this.positionsList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
