/**************************************************************************
 * TypeDePokemonZoneWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;

import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonZoneWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeDePokemonZoneWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeDePokemonZone> {
    /** TypeDePokemonZoneWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeDePokemonZoneWSClientAdapter";

    /** JSON Object TypeDePokemonZone pattern. */
    protected static String JSON_OBJECT_TYPEDEPOKEMONZONE = "TypeDePokemonZone";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_ZONE attributes. */
    protected static String JSON_ZONE = "zone";
    /** JSON_TYPEDEPOKEMON attributes. */
    protected static String JSON_TYPEDEPOKEMON = "typeDePokemon";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeDePokemonZone REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeDePokemonZoneContract.COL_ID,
            TypeDePokemonZoneContract.COL_ZONE_ID,
            TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeDePokemonZoneWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeDePokemonZoneWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonZoneWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonZoneWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonZoneWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeDePokemonZones in the given list. Uses the route : TypeDePokemonZone.
     * @param typeDePokemonZones : The list in which the TypeDePokemonZones will be returned
     * @return The number of TypeDePokemonZones returned
     */
    public int getAll(List<TypeDePokemonZone> typeDePokemonZones) {
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
                result = extractItems(json, "TypeDePokemonZones", typeDePokemonZones);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonZones = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typedepokemonzone";
    }

    /**
     * Retrieve one TypeDePokemonZone. Uses the route : TypeDePokemonZone/%id%.
     * @param typeDePokemonZone : The TypeDePokemonZone to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeDePokemonZone typeDePokemonZone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemonZone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, typeDePokemonZone)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonZone = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeDePokemonZone. Uses the route : TypeDePokemonZone/%id%.
     * @param typeDePokemonZone : The TypeDePokemonZone to retrieve (set the  ID)
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
     * Update a TypeDePokemonZone. Uses the route : TypeDePokemonZone/%id%.
     * @param typeDePokemonZone : The TypeDePokemonZone to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeDePokemonZone typeDePokemonZone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemonZone.getId(),
                        REST_FORMAT),
                    itemToJson(typeDePokemonZone));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeDePokemonZone);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeDePokemonZone. Uses the route : TypeDePokemonZone/%id%.
     * @param typeDePokemonZone : The TypeDePokemonZone to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeDePokemonZone typeDePokemonZone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemonZone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the TypeDePokemonZones associated with a Zone. Uses the route : zone/%Zone_id%/typedepokemonzone.
     * @param typeDePokemonZones : The list in which the TypeDePokemonZones will be returned
     * @param zone : The associated zone
     * @return The number of TypeDePokemonZones returned
     */
    public int getByZone(List<TypeDePokemonZone> typeDePokemonZones, Zone zone) {
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
                result = this.extractItems(json, "TypeDePokemonZones", typeDePokemonZones);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonZones = null;
            }
        }

        return result;
    }

    /**
     * Get the TypeDePokemonZones associated with a TypeDePokemon. Uses the route : typedepokemon/%TypeDePokemon_id%/typedepokemonzone.
     * @param typeDePokemonZones : The list in which the TypeDePokemonZones will be returned
     * @param typedepokemon : The associated typedepokemon
     * @return The number of TypeDePokemonZones returned
     */
    public int getByTypeDePokemon(List<TypeDePokemonZone> typeDePokemonZones, TypeDePokemon typeDePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "TypeDePokemonZones", typeDePokemonZones);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonZones = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid TypeDePokemonZone Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeDePokemonZone from a JSONObject describing a TypeDePokemonZone.
     * @param json The JSONObject describing the TypeDePokemonZone
     * @param typeDePokemonZone The returned TypeDePokemonZone
     * @return true if a TypeDePokemonZone was found. false if not
     */
    public boolean extract(JSONObject json, TypeDePokemonZone typeDePokemonZone) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID)) {
                    typeDePokemonZone.setId(
                            json.getInt(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE)
                        && !json.isNull(TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE)) {

                    try {
                        ZoneWebServiceClientAdapter zoneAdapter =
                                new ZoneWebServiceClientAdapter(this.context);
                        Zone zone =
                                new Zone();

                        if (zoneAdapter.extract(
                                json.optJSONObject(
                                        TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE),
                                        zone)) {
                            typeDePokemonZone.setZone(zone);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Zone data");
                    }
                }

                if (json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON)
                        && !json.isNull(TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON)) {

                    try {
                        TypeDePokemonWebServiceClientAdapter typeDePokemonAdapter =
                                new TypeDePokemonWebServiceClientAdapter(this.context);
                        TypeDePokemon typeDePokemon =
                                new TypeDePokemon();

                        if (typeDePokemonAdapter.extract(
                                json.optJSONObject(
                                        TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON),
                                        typeDePokemon)) {
                            typeDePokemonZone.setTypeDePokemon(typeDePokemon);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeDePokemon data");
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
        String id = json.optString(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE)) {
                    JSONObject zoneJson = json.getJSONObject(
                            TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE);
                    row[1] = zoneJson.getString(
                            ZoneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON)) {
                    JSONObject typeDePokemonJson = json.getJSONObject(
                            TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON);
                    row[2] = typeDePokemonJson.getString(
                            TypeDePokemonWebServiceClientAdapter.JSON_ID);
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
     * Convert a TypeDePokemonZone to a JSONObject.
     * @param typeDePokemonZone The TypeDePokemonZone to convert
     * @return The converted TypeDePokemonZone
     */
    public JSONObject itemToJson(TypeDePokemonZone typeDePokemonZone) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID,
                    typeDePokemonZone.getId());

            if (typeDePokemonZone.getZone() != null) {
                ZoneWebServiceClientAdapter zoneAdapter =
                        new ZoneWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE,
                        zoneAdapter.itemIdToJson(typeDePokemonZone.getZone()));
            }

            if (typeDePokemonZone.getTypeDePokemon() != null) {
                TypeDePokemonWebServiceClientAdapter typeDePokemonAdapter =
                        new TypeDePokemonWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON,
                        typeDePokemonAdapter.itemIdToJson(typeDePokemonZone.getTypeDePokemon()));
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
    public JSONObject itemIdToJson(TypeDePokemonZone item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeDePokemonZone to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_ID,
                    values.get(TypeDePokemonZoneContract.COL_ID));
            ZoneWebServiceClientAdapter zoneAdapter =
                    new ZoneWebServiceClientAdapter(this.context);

            params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_ZONE,
                    zoneAdapter.contentValuesToJson(values));
            TypeDePokemonWebServiceClientAdapter typeDePokemonAdapter =
                    new TypeDePokemonWebServiceClientAdapter(this.context);

            params.put(TypeDePokemonZoneWebServiceClientAdapter.JSON_TYPEDEPOKEMON,
                    typeDePokemonAdapter.contentValuesToJson(values));
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
            List<TypeDePokemonZone> items,
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
                TypeDePokemonZone item = new TypeDePokemonZone();
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
            List<TypeDePokemonZone> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
