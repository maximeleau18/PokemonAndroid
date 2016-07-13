/**************************************************************************
 * BadgeWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit BadgeWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class BadgeWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Badge> {
    /** BadgeWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "BadgeWSClientAdapter";

    /** JSON Object Badge pattern. */
    protected static String JSON_OBJECT_BADGE = "Badge";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Badge REST Columns. */
    public static String[] REST_COLS = new String[]{
            BadgeContract.COL_ID,
            BadgeContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public BadgeWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public BadgeWebServiceClientAdapterBase(Context context,
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
    public BadgeWebServiceClientAdapterBase(Context context,
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
    public BadgeWebServiceClientAdapterBase(Context context,
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
    public BadgeWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Badges in the given list. Uses the route : Badge.
     * @param badges : The list in which the Badges will be returned
     * @return The number of Badges returned
     */
    public int getAll(List<Badge> badges) {
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
                result = extractItems(json, "Badges", badges);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                badges = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "badge";
    }

    /**
     * Retrieve one Badge. Uses the route : Badge/%id%.
     * @param badge : The Badge to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Badge badge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        badge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, badge)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                badge = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Badge. Uses the route : Badge/%id%.
     * @param badge : The Badge to retrieve (set the  ID)
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
     * Update a Badge. Uses the route : Badge/%id%.
     * @param badge : The Badge to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Badge badge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        badge.getId(),
                        REST_FORMAT),
                    itemToJson(badge));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, badge);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Badge. Uses the route : Badge/%id%.
     * @param badge : The Badge to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Badge badge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        badge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid Badge Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(BadgeWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Badge from a JSONObject describing a Badge.
     * @param json The JSONObject describing the Badge
     * @param badge The returned Badge
     * @return true if a Badge was found. false if not
     */
    public boolean extract(JSONObject json, Badge badge) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(BadgeWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(BadgeWebServiceClientAdapter.JSON_ID)) {
                    badge.setId(
                            json.getInt(BadgeWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(BadgeWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(BadgeWebServiceClientAdapter.JSON_NOM)) {
                    badge.setNom(
                            json.getString(BadgeWebServiceClientAdapter.JSON_NOM));
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
        String id = json.optString(BadgeWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(BadgeWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(BadgeWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(BadgeWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(BadgeWebServiceClientAdapter.JSON_NOM);
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
     * Convert a Badge to a JSONObject.
     * @param badge The Badge to convert
     * @return The converted Badge
     */
    public JSONObject itemToJson(Badge badge) {
        JSONObject params = new JSONObject();
        try {
            params.put(BadgeWebServiceClientAdapter.JSON_ID,
                    badge.getId());
            params.put(BadgeWebServiceClientAdapter.JSON_NOM,
                    badge.getNom());
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
    public JSONObject itemIdToJson(Badge item) {
        JSONObject params = new JSONObject();
        try {
            params.put(BadgeWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Badge to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(BadgeWebServiceClientAdapter.JSON_ID,
                    values.get(BadgeContract.COL_ID));
            params.put(BadgeWebServiceClientAdapter.JSON_NOM,
                    values.get(BadgeContract.COL_NOM));
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
            List<Badge> items,
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
                Badge item = new Badge();
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
            List<Badge> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}