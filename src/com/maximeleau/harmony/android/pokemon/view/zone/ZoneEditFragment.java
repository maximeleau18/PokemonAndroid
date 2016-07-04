/**************************************************************************
 * ZoneEditFragment.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;
import com.maximeleau.harmony.android.pokemon.harmony.widget.MultiEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.ZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.AreneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.BadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.PositionProviderUtils;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;

/** Zone create fragment.
 *
 * This fragment gives you an interface to edit a Zone.
 *
 * @see android.app.Fragment
 */
public class ZoneEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Zone model = new Zone();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.zone_nom);
        this.arenesAdapter =
                new MultiEntityWidget.EntityAdapter<Arene>() {
            @Override
            public String entityToString(Arene item) {
                return String.valueOf(item.getId());
            }
        };
        this.arenesWidget = (MultiEntityWidget) view.findViewById(
                        R.id.zone_arenes_button);
        this.arenesWidget.setAdapter(this.arenesAdapter);
        this.arenesWidget.setTitle(R.string.zone_arenes_dialog_title);
        this.badgesAdapter =
                new MultiEntityWidget.EntityAdapter<Badge>() {
            @Override
            public String entityToString(Badge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgesWidget = (MultiEntityWidget) view.findViewById(
                        R.id.zone_badges_button);
        this.badgesWidget.setAdapter(this.badgesAdapter);
        this.badgesWidget.setTitle(R.string.zone_badges_dialog_title);
        this.positionsAdapter =
                new MultiEntityWidget.EntityAdapter<Position>() {
            @Override
            public String entityToString(Position item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.zone_positions_button);
        this.positionsWidget.setAdapter(this.positionsAdapter);
        this.positionsWidget.setTitle(R.string.zone_positions_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_zone_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Zone) intent.getParcelableExtra(
                ZoneContract.PARCEL);

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
        private final Zone entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final ZoneEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new ZoneProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("ZoneEditFragment", e.getMessage());
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
                        R.string.zone_error_edit));
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
        private ZoneEditFragment fragment;
        /** arenes list. */
        private ArrayList<Arene> arenesList;
    /** arenes list. */
        private ArrayList<Arene> associatedArenesList;
        /** badges list. */
        private ArrayList<Badge> badgesList;
    /** badges list. */
        private ArrayList<Badge> associatedBadgesList;
        /** positions list. */
        private ArrayList<Position> positionsList;
    /** positions list. */
        private ArrayList<Position> associatedPositionsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ZoneEditFragment fragment) {
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
            Uri arenesUri = ZoneProviderAdapter.ZONE_URI;
            arenesUri = Uri.withAppendedPath(arenesUri, 
                                    String.valueOf(this.fragment.model.getId()));
            arenesUri = Uri.withAppendedPath(arenesUri, "arenes");
            android.database.Cursor arenesCursor = 
                    this.ctx.getContentResolver().query(
                            arenesUri,
                            new String[]{AreneContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedArenesList = new ArrayList<Arene>();
            if (arenesCursor != null && arenesCursor.getCount() > 0) {
                while (arenesCursor.moveToNext()) {
                    int arenesId = arenesCursor.getInt(
                            arenesCursor.getColumnIndex(AreneContract.COL_ID));
                    for (Arene arenes : this.arenesList) {
                        if (arenes.getId() ==  arenesId) {
                            this.associatedArenesList.add(arenes);
                        }
                    }
                }
                arenesCursor.close();
            }
            this.badgesList = 
                new BadgeProviderUtils(this.ctx).queryAll();
            Uri badgesUri = ZoneProviderAdapter.ZONE_URI;
            badgesUri = Uri.withAppendedPath(badgesUri, 
                                    String.valueOf(this.fragment.model.getId()));
            badgesUri = Uri.withAppendedPath(badgesUri, "badges");
            android.database.Cursor badgesCursor = 
                    this.ctx.getContentResolver().query(
                            badgesUri,
                            new String[]{BadgeContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedBadgesList = new ArrayList<Badge>();
            if (badgesCursor != null && badgesCursor.getCount() > 0) {
                while (badgesCursor.moveToNext()) {
                    int badgesId = badgesCursor.getInt(
                            badgesCursor.getColumnIndex(BadgeContract.COL_ID));
                    for (Badge badges : this.badgesList) {
                        if (badges.getId() ==  badgesId) {
                            this.associatedBadgesList.add(badges);
                        }
                    }
                }
                badgesCursor.close();
            }
            this.positionsList = 
                new PositionProviderUtils(this.ctx).queryAll();
            Uri positionsUri = ZoneProviderAdapter.ZONE_URI;
            positionsUri = Uri.withAppendedPath(positionsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            positionsUri = Uri.withAppendedPath(positionsUri, "positions");
            android.database.Cursor positionsCursor = 
                    this.ctx.getContentResolver().query(
                            positionsUri,
                            new String[]{PositionContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPositionsList = new ArrayList<Position>();
            if (positionsCursor != null && positionsCursor.getCount() > 0) {
                while (positionsCursor.moveToNext()) {
                    int positionsId = positionsCursor.getInt(
                            positionsCursor.getColumnIndex(PositionContract.COL_ID));
                    for (Position positions : this.positionsList) {
                        if (positions.getId() ==  positionsId) {
                            this.associatedPositionsList.add(positions);
                        }
                    }
                }
                positionsCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setArenes(this.associatedArenesList);
            this.fragment.onArenesLoaded(this.arenesList);
            this.fragment.model.setBadges(this.associatedBadgesList);
            this.fragment.onBadgesLoaded(this.badgesList);
            this.fragment.model.setPositions(this.associatedPositionsList);
            this.fragment.onPositionsLoaded(this.positionsList);

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
     * Called when arenes have been loaded.
     * @param items The loaded items
     */
    protected void onArenesLoaded(ArrayList<Arene> items) {
        this.arenesAdapter.loadData(items);
        this.arenesAdapter.setCheckedItems(this.model.getArenes());
    }
    /**
     * Called when badges have been loaded.
     * @param items The loaded items
     */
    protected void onBadgesLoaded(ArrayList<Badge> items) {
        this.badgesAdapter.loadData(items);
        this.badgesAdapter.setCheckedItems(this.model.getBadges());
    }
    /**
     * Called when positions have been loaded.
     * @param items The loaded items
     */
    protected void onPositionsLoaded(ArrayList<Position> items) {
        this.positionsAdapter.loadData(items);
        this.positionsAdapter.setCheckedItems(this.model.getPositions());
    }
}
