/**************************************************************************
 * PositionEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.position;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.entity.Zone;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragment;

import com.maximeleau.harmony.android.pokemon.harmony.widget.SingleEntityWidget;
import com.maximeleau.harmony.android.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;

import com.maximeleau.harmony.android.pokemon.provider.utils.PositionProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.utils.ZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/** Position create fragment.
 *
 * This fragment gives you an interface to edit a Position.
 *
 * @see android.app.Fragment
 */
public class PositionEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Position model = new Position();

    /** curr.fields View. */
    /** x View. */
    protected EditText xView;
    /** y View. */
    protected EditText yView;
    /** The zone chooser component. */
    protected SingleEntityWidget zoneWidget;
    /** The zone Adapter. */
    protected SingleEntityWidget.EntityAdapter<Zone>
            zoneAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.xView = (EditText) view.findViewById(
                R.id.position_x);
        this.yView = (EditText) view.findViewById(
                R.id.position_y);
        this.zoneAdapter =
                new SingleEntityWidget.EntityAdapter<Zone>() {
            @Override
            public String entityToString(Zone item) {
                return String.valueOf(item.getId());
            }
        };
        this.zoneWidget =
            (SingleEntityWidget) view.findViewById(R.id.position_zone_button);
        this.zoneWidget.setAdapter(this.zoneAdapter);
        this.zoneWidget.setTitle(R.string.position_zone_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        this.xView.setText(String.valueOf(this.model.getX()));
        this.yView.setText(String.valueOf(this.model.getY()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setX(Integer.parseInt(
                    this.xView.getEditableText().toString()));

        this.model.setY(Integer.parseInt(
                    this.yView.getEditableText().toString()));

        this.model.setZone(this.zoneAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.xView.getText().toString().trim())) {
            error = R.string.position_x_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.yView.getText().toString().trim())) {
            error = R.string.position_y_invalid_field_error;
        }
        if (this.zoneAdapter.getSelectedItem() == null) {
            error = R.string.position_zone_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_position_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Position) intent.getParcelableExtra(
                PositionContract.PARCEL);

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
        private final Position entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PositionEditFragment fragment,
                    final Position entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.position_progress_save_title),
                    this.ctx.getString(
                            R.string.position_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PositionProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PositionEditFragment", e.getMessage());
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
                        R.string.position_error_edit));
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
        private PositionEditFragment fragment;
        /** zone list. */
        private ArrayList<Zone> zoneList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PositionEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.position_progress_load_relations_title),
                this.ctx.getString(
                    R.string.position_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.zoneList = 
                new ZoneProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onZoneLoaded(this.zoneList);

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
     * Called when zone have been loaded.
     * @param items The loaded items
     */
    protected void onZoneLoaded(ArrayList<Zone> items) {
        this.zoneAdapter.loadData(items);
        
        if (this.model.getZone() != null) {
            for (Zone item : items) {
                if (item.getId() == this.model.getZone().getId()) {
                    this.zoneAdapter.selectItem(item);
                }
            }
        }
    }
}
