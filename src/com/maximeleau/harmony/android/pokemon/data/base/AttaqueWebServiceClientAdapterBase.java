/**************************************************************************
 * AttaqueWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
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
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;

import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AttaqueWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class AttaqueWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Attaque> {
    /** AttaqueWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "AttaqueWSClientAdapter";

    /** JSON Object Attaque pattern. */
    protected static String JSON_OBJECT_ATTAQUE = "Attaque";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PUISSANCE attributes. */
    protected static String JSON_PUISSANCE = "puissance";
    /** JSON_DEGATS attributes. */
    protected static String JSON_DEGATS = "degats";
    /** JSON_TYPEATTAQUE attributes. */
    protected static String JSON_TYPEATTAQUE = "typeAttaque";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Attaque REST Columns. */
    public static String[] REST_COLS = new String[]{
            AttaqueContract.COL_ID,
            AttaqueContract.COL_NOM,
            AttaqueContract.COL_PUISSANCE,
            AttaqueContract.COL_DEGATS,
            AttaqueContract.COL_TYPEATTAQUE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public AttaqueWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public AttaqueWebServiceClientAdapterBase(Context context,
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
    public AttaqueWebServiceClientAdapterBase(Context context,
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
    public AttaqueWebServiceClientAdapterBase(Context context,
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
    public AttaqueWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Attaques in the given list. Uses the route : Attaque.
     * @param attaques : The list in which the Attaques will be returned
     * @return The number of Attaques returned
     */
    public int getAll(List<Attaque> attaques) {
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
                result = extractItems(json, "Attaques", attaques);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                attaques = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "attaque";
    }

    /**
     * Retrieve one Attaque. Uses the route : Attaque/%id%.
     * @param attaque : The Attaque to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, attaque)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                attaque = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Attaque. Uses the route : Attaque/%id%.
     * @param attaque : The Attaque to retrieve (set the  ID)
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
     * Update a Attaque. Uses the route : Attaque/%id%.
     * @param attaque : The Attaque to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    itemToJson(attaque));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, attaque);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Attaque. Uses the route : Attaque/%id%.
     * @param attaque : The Attaque to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Attaques associated with a TypeAttaque. Uses the route : typeattaque/%TypeAttaque_id%/attaque.
     * @param attaques : The list in which the Attaques will be returned
     * @param typeattaque : The associated typeattaque
     * @return The number of Attaques returned
     */
    public int getByTypeAttaque(List<Attaque> attaques, TypeAttaque typeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Attaques", attaques);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                attaques = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Attaque Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(AttaqueWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Attaque from a JSONObject describing a Attaque.
     * @param json The JSONObject describing the Attaque
     * @param attaque The returned Attaque
     * @return true if a Attaque was found. false if not
     */
    public boolean extract(JSONObject json, Attaque attaque) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(AttaqueWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(AttaqueWebServiceClientAdapter.JSON_ID)) {
                    attaque.setId(
                            json.getInt(AttaqueWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(AttaqueWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(AttaqueWebServiceClientAdapter.JSON_NOM)) {
                    attaque.setNom(
                            json.getString(AttaqueWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(AttaqueWebServiceClientAdapter.JSON_PUISSANCE)
                        && !json.isNull(AttaqueWebServiceClientAdapter.JSON_PUISSANCE)) {
                    attaque.setPuissance(
                            json.getInt(AttaqueWebServiceClientAdapter.JSON_PUISSANCE));
                }

                if (json.has(AttaqueWebServiceClientAdapter.JSON_DEGATS)
                        && !json.isNull(AttaqueWebServiceClientAdapter.JSON_DEGATS)) {
                    attaque.setDegats(
                            json.getInt(AttaqueWebServiceClientAdapter.JSON_DEGATS));
                }

                if (json.has(AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE)
                        && !json.isNull(AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE)) {

                    try {
                        TypeAttaqueWebServiceClientAdapter typeAttaqueAdapter =
                                new TypeAttaqueWebServiceClientAdapter(this.context);
                        TypeAttaque typeAttaque =
                                new TypeAttaque();

                        if (typeAttaqueAdapter.extract(
                                json.optJSONObject(
                                        AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE),
                                        typeAttaque)) {
                            attaque.setTypeAttaque(typeAttaque);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeAttaque data");
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
        String id = json.optString(AttaqueWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[5];
                if (json.has(AttaqueWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(AttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(AttaqueWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(AttaqueWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(AttaqueWebServiceClientAdapter.JSON_PUISSANCE)) {
                    row[2] = json.getString(AttaqueWebServiceClientAdapter.JSON_PUISSANCE);
                }
                if (json.has(AttaqueWebServiceClientAdapter.JSON_DEGATS)) {
                    row[3] = json.getString(AttaqueWebServiceClientAdapter.JSON_DEGATS);
                }
                if (json.has(AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE)) {
                    JSONObject typeAttaqueJson = json.getJSONObject(
                            AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE);
                    row[4] = typeAttaqueJson.getString(
                            TypeAttaqueWebServiceClientAdapter.JSON_ID);
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
     * Convert a Attaque to a JSONObject.
     * @param attaque The Attaque to convert
     * @return The converted Attaque
     */
    public JSONObject itemToJson(Attaque attaque) {
        JSONObject params = new JSONObject();
        try {
            params.put(AttaqueWebServiceClientAdapter.JSON_ID,
                    attaque.getId());
            params.put(AttaqueWebServiceClientAdapter.JSON_NOM,
                    attaque.getNom());
            params.put(AttaqueWebServiceClientAdapter.JSON_PUISSANCE,
                    attaque.getPuissance());
            params.put(AttaqueWebServiceClientAdapter.JSON_DEGATS,
                    attaque.getDegats());

            if (attaque.getTypeAttaque() != null) {
                TypeAttaqueWebServiceClientAdapter typeAttaqueAdapter =
                        new TypeAttaqueWebServiceClientAdapter(this.context);

                params.put(AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE,
                        typeAttaqueAdapter.itemIdToJson(attaque.getTypeAttaque()));
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
    public JSONObject itemIdToJson(Attaque item) {
        JSONObject params = new JSONObject();
        try {
            params.put(AttaqueWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Attaque to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(AttaqueWebServiceClientAdapter.JSON_ID,
                    values.get(AttaqueContract.COL_ID));
            params.put(AttaqueWebServiceClientAdapter.JSON_NOM,
                    values.get(AttaqueContract.COL_NOM));
            params.put(AttaqueWebServiceClientAdapter.JSON_PUISSANCE,
                    values.get(AttaqueContract.COL_PUISSANCE));
            params.put(AttaqueWebServiceClientAdapter.JSON_DEGATS,
                    values.get(AttaqueContract.COL_DEGATS));
            TypeAttaqueWebServiceClientAdapter typeAttaqueAdapter =
                    new TypeAttaqueWebServiceClientAdapter(this.context);

            params.put(AttaqueWebServiceClientAdapter.JSON_TYPEATTAQUE,
                    typeAttaqueAdapter.contentValuesToJson(values));
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
            List<Attaque> items,
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
                Attaque item = new Attaque();
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
            List<Attaque> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
