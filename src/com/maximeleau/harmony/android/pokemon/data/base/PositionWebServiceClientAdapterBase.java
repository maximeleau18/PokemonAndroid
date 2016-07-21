/**************************************************************************
 * PositionWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;

import com.maximeleau.harmony.android.pokemon.entity.Zone;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PositionWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PositionWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Position> {
    /** PositionWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PositionWSClientAdapter";

    /** JSON Object Position pattern. */
    protected static String JSON_OBJECT_POSITION = "Position";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_X attributes. */
    protected static String JSON_X = "x";
    /** JSON_Y attributes. */
    protected static String JSON_Y = "y";
    /** JSON_ZONE attributes. */
    protected static String JSON_ZONE = "zone";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Position REST Columns. */
    public static String[] REST_COLS = new String[]{
            PositionContract.COL_ID,
            PositionContract.COL_X,
            PositionContract.COL_Y,
            PositionContract.COL_ZONE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PositionWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PositionWebServiceClientAdapterBase(Context context,
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
    public PositionWebServiceClientAdapterBase(Context context,
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
    public PositionWebServiceClientAdapterBase(Context context,
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
    public PositionWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Positions in the given list. Uses the route : Position.
     * @param positions : The list in which the Positions will be returned
     * @return The number of Positions returned
     */
    public int getAll(List<Position> positions) {
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
                result = extractItems(json, "Positions", positions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                positions = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "position";
    }

    /**
     * Retrieve one Position. Uses the route : Position/%id%.
     * @param position : The Position to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Position position) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        position.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, position)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                position = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Position. Uses the route : Position/%id%.
     * @param position : The Position to retrieve (set the  ID)
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
     * Update a Position. Uses the route : Position/%id%.
     * @param position : The Position to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Position position) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        position.getId(),
                        REST_FORMAT),
                    itemToJson(position));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, position);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Position. Uses the route : Position/%id%.
     * @param position : The Position to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Position position) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        position.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Positions associated with a Zone. Uses the route : zone/%Zone_id%/position.
     * @param positions : The list in which the Positions will be returned
     * @param zone : The associated zone
     * @return The number of Positions returned
     */
    public int getByZone(List<Position> positions, Zone zone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        zone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Positions", positions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                positions = null;
            }
        }

        return result;
    }

    /**
     * Get the Positions associated with a Zone. Uses the route : zone/%Zone_id%/position.
     * @param positions : The list in which the Positions will be returned
     * @param zone : The associated zone
     * @return The number of Positions returned
     */
    public int getByZonepositionsInternal(List<Position> positions, Zone zone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        zone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Positions", positions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                positions = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Position Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PositionWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Position from a JSONObject describing a Position.
     * @param json The JSONObject describing the Position
     * @param position The returned Position
     * @return true if a Position was found. false if not
     */
    public boolean extract(JSONObject json, Position position) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PositionWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PositionWebServiceClientAdapter.JSON_ID)) {
                    position.setId(
                            json.getInt(PositionWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PositionWebServiceClientAdapter.JSON_X)
                        && !json.isNull(PositionWebServiceClientAdapter.JSON_X)) {
                    position.setX(
                            json.getInt(PositionWebServiceClientAdapter.JSON_X));
                }

                if (json.has(PositionWebServiceClientAdapter.JSON_Y)
                        && !json.isNull(PositionWebServiceClientAdapter.JSON_Y)) {
                    position.setY(
                            json.getInt(PositionWebServiceClientAdapter.JSON_Y));
                }

                if (json.has(PositionWebServiceClientAdapter.JSON_ZONE)
                        && !json.isNull(PositionWebServiceClientAdapter.JSON_ZONE)) {

                    try {
                        ZoneWebServiceClientAdapter zoneAdapter =
                                new ZoneWebServiceClientAdapter(this.context);
                        Zone zone =
                                new Zone();

                        if (zoneAdapter.extract(
                                json.optJSONObject(
                                        PositionWebServiceClientAdapter.JSON_ZONE),
                                        zone)) {
                            position.setZone(zone);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Zone data");
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
        String id = json.optString(PositionWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[4];
                if (json.has(PositionWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PositionWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PositionWebServiceClientAdapter.JSON_X)) {
                    row[1] = json.getString(PositionWebServiceClientAdapter.JSON_X);
                }
                if (json.has(PositionWebServiceClientAdapter.JSON_Y)) {
                    row[2] = json.getString(PositionWebServiceClientAdapter.JSON_Y);
                }
                if (json.has(PositionWebServiceClientAdapter.JSON_ZONE)) {
                    JSONObject zoneJson = json.getJSONObject(
                            PositionWebServiceClientAdapter.JSON_ZONE);
                    row[3] = zoneJson.getString(
                            ZoneWebServiceClientAdapter.JSON_ID);
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
     * Convert a Position to a JSONObject.
     * @param position The Position to convert
     * @return The converted Position
     */
    public JSONObject itemToJson(Position position) {
        JSONObject params = new JSONObject();
        try {
            params.put(PositionWebServiceClientAdapter.JSON_ID,
                    position.getId());
            params.put(PositionWebServiceClientAdapter.JSON_X,
                    position.getX());
            params.put(PositionWebServiceClientAdapter.JSON_Y,
                    position.getY());

            if (position.getZone() != null) {
                ZoneWebServiceClientAdapter zoneAdapter =
                        new ZoneWebServiceClientAdapter(this.context);

                params.put(PositionWebServiceClientAdapter.JSON_ZONE,
                        zoneAdapter.itemIdToJson(position.getZone()));
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
    public JSONObject itemIdToJson(Position item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PositionWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Position to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PositionWebServiceClientAdapter.JSON_ID,
                    values.get(PositionContract.COL_ID));
            params.put(PositionWebServiceClientAdapter.JSON_X,
                    values.get(PositionContract.COL_X));
            params.put(PositionWebServiceClientAdapter.JSON_Y,
                    values.get(PositionContract.COL_Y));
            ZoneWebServiceClientAdapter zoneAdapter =
                    new ZoneWebServiceClientAdapter(this.context);

            params.put(PositionWebServiceClientAdapter.JSON_ZONE,
                    zoneAdapter.contentValuesToJson(values));
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
            List<Position> items,
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
                Position item = new Position();
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
            List<Position> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
