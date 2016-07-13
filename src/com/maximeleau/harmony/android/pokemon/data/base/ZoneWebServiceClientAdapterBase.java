/**************************************************************************
 * ZoneWebServiceClientAdapterBase.java, pokemon Android
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
import java.util.ArrayList;

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
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ZoneWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ZoneWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Zone> {
    /** ZoneWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "ZoneWSClientAdapter";

    /** JSON Object Zone pattern. */
    protected static String JSON_OBJECT_ZONE = "Zone";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_ARENES attributes. */
    protected static String JSON_ARENES = "arenes";
    /** JSON_BADGES attributes. */
    protected static String JSON_BADGES = "badges";
    /** JSON_POSITIONS attributes. */
    protected static String JSON_POSITIONS = "positions";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Zone REST Columns. */
    public static String[] REST_COLS = new String[]{
            ZoneContract.COL_ID,
            ZoneContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public ZoneWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public ZoneWebServiceClientAdapterBase(Context context,
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
    public ZoneWebServiceClientAdapterBase(Context context,
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
    public ZoneWebServiceClientAdapterBase(Context context,
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
    public ZoneWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Zones in the given list. Uses the route : Zone.
     * @param zones : The list in which the Zones will be returned
     * @return The number of Zones returned
     */
    public int getAll(List<Zone> zones) {
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
                result = extractItems(json, "Zones", zones);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                zones = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "zone";
    }

    /**
     * Retrieve one Zone. Uses the route : Zone/%id%.
     * @param zone : The Zone to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Zone zone) {
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
                if (extract(json, zone)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                zone = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Zone. Uses the route : Zone/%id%.
     * @param zone : The Zone to retrieve (set the  ID)
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
     * Update a Zone. Uses the route : Zone/%id%.
     * @param zone : The Zone to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Zone zone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        zone.getId(),
                        REST_FORMAT),
                    itemToJson(zone));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, zone);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Zone. Uses the route : Zone/%id%.
     * @param zone : The Zone to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Zone zone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        zone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }





    /**
     * Tests if the json is a valid Zone Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(ZoneWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Zone from a JSONObject describing a Zone.
     * @param json The JSONObject describing the Zone
     * @param zone The returned Zone
     * @return true if a Zone was found. false if not
     */
    public boolean extract(JSONObject json, Zone zone) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(ZoneWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(ZoneWebServiceClientAdapter.JSON_ID)) {
                    zone.setId(
                            json.getInt(ZoneWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(ZoneWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(ZoneWebServiceClientAdapter.JSON_NOM)) {
                    zone.setNom(
                            json.getString(ZoneWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(ZoneWebServiceClientAdapter.JSON_ARENES)
                        && !json.isNull(ZoneWebServiceClientAdapter.JSON_ARENES)) {
                    ArrayList<Arene> arenes =
                            new ArrayList<Arene>();
                    AreneWebServiceClientAdapter arenesAdapter =
                            new AreneWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(ZoneWebServiceClientAdapter.JSON_ARENES);
                        arenesAdapter.extractItems(
                                json, ZoneWebServiceClientAdapter.JSON_ARENES,
                                arenes);
                        zone.setArenes(arenes);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(ZoneWebServiceClientAdapter.JSON_BADGES)
                        && !json.isNull(ZoneWebServiceClientAdapter.JSON_BADGES)) {
                    ArrayList<Badge> badges =
                            new ArrayList<Badge>();
                    BadgeWebServiceClientAdapter badgesAdapter =
                            new BadgeWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(ZoneWebServiceClientAdapter.JSON_BADGES);
                        badgesAdapter.extractItems(
                                json, ZoneWebServiceClientAdapter.JSON_BADGES,
                                badges);
                        zone.setBadges(badges);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(ZoneWebServiceClientAdapter.JSON_POSITIONS)
                        && !json.isNull(ZoneWebServiceClientAdapter.JSON_POSITIONS)) {
                    ArrayList<Position> positions =
                            new ArrayList<Position>();
                    PositionWebServiceClientAdapter positionsAdapter =
                            new PositionWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(ZoneWebServiceClientAdapter.JSON_POSITIONS);
                        positionsAdapter.extractItems(
                                json, ZoneWebServiceClientAdapter.JSON_POSITIONS,
                                positions);
                        zone.setPositions(positions);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
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
        String id = json.optString(ZoneWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(ZoneWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(ZoneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ZoneWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(ZoneWebServiceClientAdapter.JSON_NOM);
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
     * Convert a Zone to a JSONObject.
     * @param zone The Zone to convert
     * @return The converted Zone
     */
    public JSONObject itemToJson(Zone zone) {
        JSONObject params = new JSONObject();
        try {
            params.put(ZoneWebServiceClientAdapter.JSON_ID,
                    zone.getId());
            params.put(ZoneWebServiceClientAdapter.JSON_NOM,
                    zone.getNom());

            if (zone.getArenes() != null) {
                AreneWebServiceClientAdapter arenesAdapter =
                        new AreneWebServiceClientAdapter(this.context);

                params.put(ZoneWebServiceClientAdapter.JSON_ARENES,
                        arenesAdapter.itemsIdToJson(zone.getArenes()));
            }

            if (zone.getBadges() != null) {
                BadgeWebServiceClientAdapter badgesAdapter =
                        new BadgeWebServiceClientAdapter(this.context);

                params.put(ZoneWebServiceClientAdapter.JSON_BADGES,
                        badgesAdapter.itemsIdToJson(zone.getBadges()));
            }

            if (zone.getPositions() != null) {
                PositionWebServiceClientAdapter positionsAdapter =
                        new PositionWebServiceClientAdapter(this.context);

                params.put(ZoneWebServiceClientAdapter.JSON_POSITIONS,
                        positionsAdapter.itemsIdToJson(zone.getPositions()));
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
    public JSONObject itemIdToJson(Zone item) {
        JSONObject params = new JSONObject();
        try {
            params.put(ZoneWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Zone to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(ZoneWebServiceClientAdapter.JSON_ID,
                    values.get(ZoneContract.COL_ID));
            params.put(ZoneWebServiceClientAdapter.JSON_NOM,
                    values.get(ZoneContract.COL_NOM));
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
            List<Zone> items,
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
                Zone item = new Zone();
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
            List<Zone> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
