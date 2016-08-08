package com.maximeleau.harmony.android.pokemon.view.connexion;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.data.DresseurWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.base.DresseurWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.view.chooseaction.ChooseActionActivity;
import com.maximeleau.harmony.android.pokemon.view.choosepokemon.ChoosePokemonActivity;
import com.maximeleau.harmony.android.pokemon.view.connectedchooseaction.ConnectedChooseActionActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Maxime Léau on 01/08/2016.
 */
public class ConnexionFragment extends Fragment {

    protected Button btnConnexion;
    protected Button btnRetour;
    protected EditText loginTxt;
    protected EditText passwordTxt;
    /** Model data. */
    protected Dresseur model = new Dresseur();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connexion, container, false);
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(final View view) {
        this.loginTxt = (EditText) view.findViewById(R.id.connexion_login);
        this.passwordTxt = (EditText) view.findViewById(R.id.connexion_password);
        this.btnConnexion = (Button) view.findViewById(R.id.connexion_button);
        this.btnRetour = (Button) view.findViewById(R.id.connexion_back_button);

        // Binding click
        this.btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnexionFragment.this.validateData()){
                    ConnexionFragment.this.saveData();
                    new ConnexionTask(ConnexionFragment.this, ConnexionFragment.this.model).execute();
                }
            }
        });

        this.btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ConnexionFragment.this.getActivity(), ChooseActionActivity.class);
                ConnexionFragment.this.getActivity().startActivity(intent);
            }
        });
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setLogin(this.loginTxt.getEditableText().toString());

        this.model.setMotDePasse(this.passwordTxt.getEditableText().toString());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                this.loginTxt.getText().toString().trim())) {
            error = R.string.connexion_login_invalid_field;
        }
        if (Strings.isNullOrEmpty(
                this.passwordTxt.getText().toString().trim())) {
            error = R.string.connexion_password_invalid_field;
        }

        if (error > 0) {
            Toast.makeText(this.getActivity(),
                    this.getActivity().getString(error),
                    Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class ConnexionTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Dresseur entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public ConnexionTask(final ConnexionFragment fragment,
                        final Dresseur entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.connexion_inprogress_title),
                    this.ctx.getString(
                            R.string.connexon_inprogress_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                DresseurWebServiceClientAdapter webService = new DresseurWebServiceClientAdapter(this.ctx);
                result = webService.connectDresseur(this.entity);
            } catch (Exception e) {
                android.util.Log.e("ConnexionFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result < 0) {

                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.connexion_error_connexion));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
                builder.show();
            } else {
                final Intent intent = new Intent(this.ctx, ConnectedChooseActionActivity.class);
                intent.putExtra("dresseur", (Serializable) this.entity);
                this.ctx.startActivity(intent);
            }

            this.progress.dismiss();
        }
    }
}
