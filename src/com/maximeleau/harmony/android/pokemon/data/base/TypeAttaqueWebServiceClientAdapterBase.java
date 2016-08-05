/**************************************************************************
 * TypeAttaqueWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeAttaqueWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeAttaqueWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeAttaque> {
    /** TypeAttaqueWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeAttaqueWSClientAdapter";

    /** JSON Object TypeAttaque pattern. */
    protected static String JSON_OBJECT_TYPEATTAQUE = "TypeAttaque";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeAttaque REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeAttaqueContract.COL_ID,
            TypeAttaqueContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeAttaqueWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeAttaqueWebServiceClientAdapterBase(Context context,
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
    public TypeAttaqueWebServiceClientAdapterBase(Context context,
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
    public TypeAttaqueWebServiceClientAdapterBase(Context context,
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
    public TypeAttaqueWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeAttaques in the given list. Uses the route : TypeAttaque.
     * @param typeAttaques : The list in which the TypeAttaques will be returned
     * @return The number of TypeAttaques returned
     */
    public int getAll(List<TypeAttaque> typeAttaques) {
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
                result = extractItems(json, "TypeAttaques", typeAttaques);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeAttaques = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typeattaque";
    }

    /**
     * Retrieve one TypeAttaque. Uses the route : TypeAttaque/%id%.
     * @param typeAttaque : The TypeAttaque to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeAttaque typeAttaque) {
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
                if (extract(json, typeAttaque)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeAttaque = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeAttaque. Uses the route : TypeAttaque/%id%.
     * @param typeAttaque : The TypeAttaque to retrieve (set the  ID)
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
     * Update a TypeAttaque. Uses the route : TypeAttaque/%id%.
     * @param typeAttaque : The TypeAttaque to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeAttaque typeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeAttaque.getId(),
                        REST_FORMAT),
                    itemToJson(typeAttaque));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeAttaque);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeAttaque. Uses the route : TypeAttaque/%id%.
     * @param typeAttaque : The TypeAttaque to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeAttaque typeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid TypeAttaque Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeAttaqueWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeAttaque from a JSONObject describing a TypeAttaque.
     * @param json The JSONObject describing the TypeAttaque
     * @param typeAttaque The returned TypeAttaque
     * @return true if a TypeAttaque was found. false if not
     */
    public boolean extract(JSONObject json, TypeAttaque typeAttaque) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeAttaqueWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeAttaqueWebServiceClientAdapter.JSON_ID)) {
                    typeAttaque.setId(
                            json.getInt(TypeAttaqueWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeAttaqueWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(TypeAttaqueWebServiceClientAdapter.JSON_NOM)) {
                    typeAttaque.setNom(
                            json.getString(TypeAttaqueWebServiceClientAdapter.JSON_NOM));
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
        String id = json.optString(TypeAttaqueWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(TypeAttaqueWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeAttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeAttaqueWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(TypeAttaqueWebServiceClientAdapter.JSON_NOM);
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
     * Convert a TypeAttaque to a JSONObject.
     * @param typeAttaque The TypeAttaque to convert
     * @return The converted TypeAttaque
     */
    public JSONObject itemToJson(TypeAttaque typeAttaque) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeAttaqueWebServiceClientAdapter.JSON_ID,
                    typeAttaque.getId());
            params.put(TypeAttaqueWebServiceClientAdapter.JSON_NOM,
                    typeAttaque.getNom());
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
    public JSONObject itemIdToJson(TypeAttaque item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeAttaqueWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeAttaque to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeAttaqueWebServiceClientAdapter.JSON_ID,
                    values.get(TypeAttaqueContract.COL_ID));
            params.put(TypeAttaqueWebServiceClientAdapter.JSON_NOM,
                    values.get(TypeAttaqueContract.COL_NOM));
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
            List<TypeAttaque> items,
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
                TypeAttaque item = new TypeAttaque();
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
            List<TypeAttaque> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
