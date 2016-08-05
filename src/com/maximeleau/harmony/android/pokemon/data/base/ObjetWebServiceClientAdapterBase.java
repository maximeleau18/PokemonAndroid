/**************************************************************************
 * ObjetWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
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
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;

import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ObjetWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ObjetWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Objet> {
    /** ObjetWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "ObjetWSClientAdapter";

    /** JSON Object Objet pattern. */
    protected static String JSON_OBJECT_OBJET = "Objet";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_QUANTITE attributes. */
    protected static String JSON_QUANTITE = "quantite";
    /** JSON_URLIMAGE attributes. */
    protected static String JSON_URLIMAGE = "urlImage";
    /** JSON_TYPEOBJET attributes. */
    protected static String JSON_TYPEOBJET = "typeObjet";
    /** JSON_PERSONNAGENONJOUEUR attributes. */
    protected static String JSON_PERSONNAGENONJOUEUR = "personnageNonJoueur";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Objet REST Columns. */
    public static String[] REST_COLS = new String[]{
            ObjetContract.COL_ID,
            ObjetContract.COL_NOM,
            ObjetContract.COL_QUANTITE,
            ObjetContract.COL_URLIMAGE,
            ObjetContract.COL_TYPEOBJET_ID,
            ObjetContract.COL_PERSONNAGENONJOUEUR_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public ObjetWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public ObjetWebServiceClientAdapterBase(Context context,
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
    public ObjetWebServiceClientAdapterBase(Context context,
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
    public ObjetWebServiceClientAdapterBase(Context context,
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
    public ObjetWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Objets in the given list. Uses the route : Objet.
     * @param objets : The list in which the Objets will be returned
     * @return The number of Objets returned
     */
    public int getAll(List<Objet> objets) {
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
                result = extractItems(json, "Objets", objets);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objets = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "objet";
    }

    /**
     * Retrieve one Objet. Uses the route : Objet/%id%.
     * @param objet : The Objet to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Objet objet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        objet.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, objet)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objet = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Objet. Uses the route : Objet/%id%.
     * @param objet : The Objet to retrieve (set the  ID)
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
     * Update a Objet. Uses the route : Objet/%id%.
     * @param objet : The Objet to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Objet objet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        objet.getId(),
                        REST_FORMAT),
                    itemToJson(objet));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, objet);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Objet. Uses the route : Objet/%id%.
     * @param objet : The Objet to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Objet objet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        objet.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Objets associated with a TypeObjet. Uses the route : typeobjet/%TypeObjet_id%/objet.
     * @param objets : The list in which the Objets will be returned
     * @param typeobjet : The associated typeobjet
     * @return The number of Objets returned
     */
    public int getByTypeObjet(List<Objet> objets, TypeObjet typeObjet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjet.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Objets", objets);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objets = null;
            }
        }

        return result;
    }

    /**
     * Get the Objets associated with a PersonnageNonJoueur. Uses the route : personnagenonjoueur/%PersonnageNonJoueur_id%/objet.
     * @param objets : The list in which the Objets will be returned
     * @param personnagenonjoueur : The associated personnagenonjoueur
     * @return The number of Objets returned
     */
    public int getByPersonnageNonJoueur(List<Objet> objets, PersonnageNonJoueur personnageNonJoueur) {
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
                result = this.extractItems(json, "Objets", objets);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objets = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Objet Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(ObjetWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Objet from a JSONObject describing a Objet.
     * @param json The JSONObject describing the Objet
     * @param objet The returned Objet
     * @return true if a Objet was found. false if not
     */
    public boolean extract(JSONObject json, Objet objet) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(ObjetWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(ObjetWebServiceClientAdapter.JSON_ID)) {
                    objet.setId(
                            json.getInt(ObjetWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(ObjetWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(ObjetWebServiceClientAdapter.JSON_NOM)) {
                    objet.setNom(
                            json.getString(ObjetWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(ObjetWebServiceClientAdapter.JSON_QUANTITE)
                        && !json.isNull(ObjetWebServiceClientAdapter.JSON_QUANTITE)) {
                    objet.setQuantite(
                            json.getInt(ObjetWebServiceClientAdapter.JSON_QUANTITE));
                }

                if (json.has(ObjetWebServiceClientAdapter.JSON_URLIMAGE)
                        && !json.isNull(ObjetWebServiceClientAdapter.JSON_URLIMAGE)) {
                    objet.setUrlImage(
                            json.getString(ObjetWebServiceClientAdapter.JSON_URLIMAGE));
                }

                if (json.has(ObjetWebServiceClientAdapter.JSON_TYPEOBJET)
                        && !json.isNull(ObjetWebServiceClientAdapter.JSON_TYPEOBJET)) {

                    try {
                        TypeObjetWebServiceClientAdapter typeObjetAdapter =
                                new TypeObjetWebServiceClientAdapter(this.context);
                        TypeObjet typeObjet =
                                new TypeObjet();

                        if (typeObjetAdapter.extract(
                                json.optJSONObject(
                                        ObjetWebServiceClientAdapter.JSON_TYPEOBJET),
                                        typeObjet)) {
                            objet.setTypeObjet(typeObjet);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeObjet data");
                    }
                }

                if (json.has(ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)
                        && !json.isNull(ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {

                    try {
                        PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                                new PersonnageNonJoueurWebServiceClientAdapter(this.context);
                        PersonnageNonJoueur personnageNonJoueur =
                                new PersonnageNonJoueur();

                        if (personnageNonJoueurAdapter.extract(
                                json.optJSONObject(
                                        ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR),
                                        personnageNonJoueur)) {
                            objet.setPersonnageNonJoueur(personnageNonJoueur);
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
        String id = json.optString(ObjetWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[6];
                if (json.has(ObjetWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(ObjetWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ObjetWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(ObjetWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(ObjetWebServiceClientAdapter.JSON_QUANTITE)) {
                    row[2] = json.getString(ObjetWebServiceClientAdapter.JSON_QUANTITE);
                }
                if (json.has(ObjetWebServiceClientAdapter.JSON_URLIMAGE)) {
                    row[3] = json.getString(ObjetWebServiceClientAdapter.JSON_URLIMAGE);
                }
                if (json.has(ObjetWebServiceClientAdapter.JSON_TYPEOBJET)) {
                    JSONObject typeObjetJson = json.getJSONObject(
                            ObjetWebServiceClientAdapter.JSON_TYPEOBJET);
                    row[4] = typeObjetJson.getString(
                            TypeObjetWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {
                    JSONObject personnageNonJoueurJson = json.getJSONObject(
                            ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR);
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
     * Convert a Objet to a JSONObject.
     * @param objet The Objet to convert
     * @return The converted Objet
     */
    public JSONObject itemToJson(Objet objet) {
        JSONObject params = new JSONObject();
        try {
            params.put(ObjetWebServiceClientAdapter.JSON_ID,
                    objet.getId());
            params.put(ObjetWebServiceClientAdapter.JSON_NOM,
                    objet.getNom());
            params.put(ObjetWebServiceClientAdapter.JSON_QUANTITE,
                    objet.getQuantite());
            params.put(ObjetWebServiceClientAdapter.JSON_URLIMAGE,
                    objet.getUrlImage());

            if (objet.getTypeObjet() != null) {
                TypeObjetWebServiceClientAdapter typeObjetAdapter =
                        new TypeObjetWebServiceClientAdapter(this.context);

                params.put(ObjetWebServiceClientAdapter.JSON_TYPEOBJET,
                        typeObjetAdapter.itemIdToJson(objet.getTypeObjet()));
            }

            if (objet.getPersonnageNonJoueur() != null) {
                PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                        new PersonnageNonJoueurWebServiceClientAdapter(this.context);

                params.put(ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                        personnageNonJoueurAdapter.itemIdToJson(objet.getPersonnageNonJoueur()));
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
    public JSONObject itemIdToJson(Objet item) {
        JSONObject params = new JSONObject();
        try {
            params.put(ObjetWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Objet to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(ObjetWebServiceClientAdapter.JSON_ID,
                    values.get(ObjetContract.COL_ID));
            params.put(ObjetWebServiceClientAdapter.JSON_NOM,
                    values.get(ObjetContract.COL_NOM));
            params.put(ObjetWebServiceClientAdapter.JSON_QUANTITE,
                    values.get(ObjetContract.COL_QUANTITE));
            params.put(ObjetWebServiceClientAdapter.JSON_URLIMAGE,
                    values.get(ObjetContract.COL_URLIMAGE));
            TypeObjetWebServiceClientAdapter typeObjetAdapter =
                    new TypeObjetWebServiceClientAdapter(this.context);

            params.put(ObjetWebServiceClientAdapter.JSON_TYPEOBJET,
                    typeObjetAdapter.contentValuesToJson(values));
            PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurWebServiceClientAdapter(this.context);

            params.put(ObjetWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
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
            List<Objet> items,
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
                Objet item = new Objet();
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
            List<Objet> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
