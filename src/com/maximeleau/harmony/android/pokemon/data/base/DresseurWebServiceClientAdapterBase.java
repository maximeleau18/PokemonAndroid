/**************************************************************************
 * DresseurWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 13, 2016
 *
 **************************************************************************/

package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.List;


import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.maximeleau.harmony.android.pokemon.data.*;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit DresseurWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class DresseurWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Dresseur> {
    /** DresseurWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "DresseurWSClientAdapter";

    /** JSON Object Dresseur pattern. */
    protected static String JSON_OBJECT_DRESSEUR = "Dresseur";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PRENOM attributes. */
    protected static String JSON_PRENOM = "prenom";
    /** JSON_LOGIN attributes. */
    protected static String JSON_LOGIN = "login";
    /** JSON_MOTDEPASSE attributes. */
    protected static String JSON_MOTDEPASSE = "motDePasse";
    /** JSON_PERSONNAGENONJOUEUR attributes. */
    protected static String JSON_PERSONNAGENONJOUEUR = "personnageNonJoueur";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Dresseur REST Columns. */
    public static String[] REST_COLS = new String[]{
            DresseurContract.COL_ID,
            DresseurContract.COL_NOM,
            DresseurContract.COL_PRENOM,
            DresseurContract.COL_LOGIN,
            DresseurContract.COL_MOTDEPASSE,
            DresseurContract.COL_PERSONNAGENONJOUEUR_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public DresseurWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public DresseurWebServiceClientAdapterBase(Context context,
        Integer port) {
        this(context, null, port);
    }

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     */
    public DresseurWebServiceClientAdapterBase(Context context,
            String host, Integer port) {
        this(context, host, port, null);
    }

    /**
     * Constructor with overriden port, host and scheme.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     */
    public DresseurWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme) {
        this(context, host, port, scheme, null);
    }

    /**
     * Constructor with overriden port, host, scheme and prefix.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     * @param prefix The overriden prefix
     */
    public DresseurWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Dresseurs in the given list. Uses the route : Dresseur.
     * @param dresseurs : The list in which the Dresseurs will be returned
     * @return The number of Dresseurs returned
     */
    public int getAll(List<Dresseur> dresseurs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "%s",
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = extractItems(json, "Dresseurs", dresseurs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseurs = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "dresseur";
    }

    /**
     * Retrieve one Dresseur. Uses the route : Dresseur/%id%.
     * @param dresseur : The Dresseur to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Dresseur dresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, dresseur)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseur = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Dresseur. Uses the route : Dresseur/%id%.
     * @param dresseur : The Dresseur to retrieve (set the  ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public Cursor query(final int id) {
        MatrixCursor result = new MatrixCursor(REST_COLS);
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        id,
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extractCursor(json, result);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                result = null;
            }
        }

        return result;
    }

    /**
     * Update a Dresseur. Uses the route : Dresseur/%id%.
     * @param dresseur : The Dresseur to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Dresseur dresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseur.getId(),
                        REST_FORMAT),
                    itemToJson(dresseur));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, dresseur);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Dresseur. Uses the route : Dresseur/%id%.
     * @param dresseur : The Dresseur to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Dresseur dresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Dresseur associated with a PersonnageNonJoueur. Uses the route : personnagenonjoueur/%PersonnageNonJoueur_id%/dresseur.
     * @param dresseur : The Dresseur that will be returned
     * @param personnagenonjoueur : The associated personnagenonjoueur
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByPersonnageNonJoueur(Dresseur dresseur, PersonnageNonJoueur personnageNonJoueur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, dresseur);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseur = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Dresseur Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(DresseurWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Dresseur from a JSONObject describing a Dresseur.
     * @param json The JSONObject describing the Dresseur
     * @param dresseur The returned Dresseur
     * @return true if a Dresseur was found. false if not
     */
    public boolean extract(JSONObject json, Dresseur dresseur) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(DresseurWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(DresseurWebServiceClientAdapter.JSON_ID)) {
                    dresseur.setId(
                            json.getInt(DresseurWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(DresseurWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(DresseurWebServiceClientAdapter.JSON_NOM)) {
                    dresseur.setNom(
                            json.getString(DresseurWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(DresseurWebServiceClientAdapter.JSON_PRENOM)
                        && !json.isNull(DresseurWebServiceClientAdapter.JSON_PRENOM)) {
                    dresseur.setPrenom(
                            json.getString(DresseurWebServiceClientAdapter.JSON_PRENOM));
                }

                if (json.has(DresseurWebServiceClientAdapter.JSON_LOGIN)
                        && !json.isNull(DresseurWebServiceClientAdapter.JSON_LOGIN)) {
                    dresseur.setLogin(
                            json.getString(DresseurWebServiceClientAdapter.JSON_LOGIN));
                }

                if (json.has(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE)
                        && !json.isNull(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE)) {
                    dresseur.setMotDePasse(
                            json.getString(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE));
                }

                if (json.has(DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)
                        && !json.isNull(DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {

                    try {
                        PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                                new PersonnageNonJoueurWebServiceClientAdapter(this.context);
                        PersonnageNonJoueur personnageNonJoueur =
                                new PersonnageNonJoueur();

                        if (personnageNonJoueurAdapter.extract(
                                json.optJSONObject(
                                        DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR),
                                        personnageNonJoueur)) {
                            dresseur.setPersonnageNonJoueur(personnageNonJoueur);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PersonnageNonJoueur data");
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public boolean extractCursor(JSONObject json, MatrixCursor cursor) {
        boolean result = false;
        String id = json.optString(DresseurWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[6];
                if (json.has(DresseurWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(DresseurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(DresseurWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(DresseurWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(DresseurWebServiceClientAdapter.JSON_PRENOM)) {
                    row[2] = json.getString(DresseurWebServiceClientAdapter.JSON_PRENOM);
                }
                if (json.has(DresseurWebServiceClientAdapter.JSON_LOGIN)) {
                    row[3] = json.getString(DresseurWebServiceClientAdapter.JSON_LOGIN);
                }
                if (json.has(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE)) {
                    row[4] = json.getString(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE);
                }
                if (json.has(DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {
                    JSONObject personnageNonJoueurJson = json.getJSONObject(
                            DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR);
                    row[5] = personnageNonJoueurJson.getString(
                            PersonnageNonJoueurWebServiceClientAdapter.JSON_ID);
                }

                cursor.addRow(row);
                result = true;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Convert a Dresseur to a JSONObject.
     * @param dresseur The Dresseur to convert
     * @return The converted Dresseur
     */
    public JSONObject itemToJson(Dresseur dresseur) {
        JSONObject params = new JSONObject();
        try {
            params.put(DresseurWebServiceClientAdapter.JSON_ID,
                    dresseur.getId());
            params.put(DresseurWebServiceClientAdapter.JSON_NOM,
                    dresseur.getNom());
            params.put(DresseurWebServiceClientAdapter.JSON_PRENOM,
                    dresseur.getPrenom());
            params.put(DresseurWebServiceClientAdapter.JSON_LOGIN,
                    dresseur.getLogin());
            params.put(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE,
                    dresseur.getMotDePasse());

            if (dresseur.getPersonnageNonJoueur() != null) {
                PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                        new PersonnageNonJoueurWebServiceClientAdapter(this.context);

                params.put(DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                        personnageNonJoueurAdapter.itemIdToJson(dresseur.getPersonnageNonJoueur()));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Convert a <T> to a JSONObject.
     * @param item The <T> to convert
     * @return The converted <T>
     */
    public JSONObject itemIdToJson(Dresseur item) {
        JSONObject params = new JSONObject();
        try {
            params.put(DresseurWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Dresseur to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(DresseurWebServiceClientAdapter.JSON_ID,
                    values.get(DresseurContract.COL_ID));
            params.put(DresseurWebServiceClientAdapter.JSON_NOM,
                    values.get(DresseurContract.COL_NOM));
            params.put(DresseurWebServiceClientAdapter.JSON_PRENOM,
                    values.get(DresseurContract.COL_PRENOM));
            params.put(DresseurWebServiceClientAdapter.JSON_LOGIN,
                    values.get(DresseurContract.COL_LOGIN));
            params.put(DresseurWebServiceClientAdapter.JSON_MOTDEPASSE,
                    values.get(DresseurContract.COL_MOTDEPASSE));
            PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurWebServiceClientAdapter(this.context);

            params.put(DresseurWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                    personnageNonJoueurAdapter.contentValuesToJson(values));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @param limit Limit the number of items to parse
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Dresseur> items,
            int limit) throws JSONException {

        JSONArray itemArray = json.optJSONArray(paramName);

        int result = -1;

        if (itemArray != null) {
            int count = itemArray.length();

            if (limit > 0 && count > limit) {
                count = limit;
            }

            for (int i = 0; i < count; i++) {
                JSONObject jsonItem = itemArray.getJSONObject(i);
                Dresseur item = new Dresseur();
                this.extract(jsonItem, item);
                if (item!=null) {
                    synchronized (items) {
                        items.add(item);
                    }
                }
            }
        }

        if (!json.isNull("Meta")) {
            JSONObject meta = json.optJSONObject("Meta");
            result = meta.optInt("nbt",0);
        }

        return result;
    }

    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Dresseur> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
