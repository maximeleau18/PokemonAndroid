/**************************************************************************
 * TypeDePokemonEvolutionWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonEvolutionWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeDePokemonEvolutionWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeDePokemonEvolution> {
    /** TypeDePokemonEvolutionWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeDePokemonEvolutionWSClientAdapter";

    /** JSON Object TypeDePokemonEvolution pattern. */
    protected static String JSON_OBJECT_TYPEDEPOKEMONEVOLUTION = "TypeDePokemonEvolution";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_EVOLUEEN attributes. */
    protected static String JSON_EVOLUEEN = "evolueEn";
    /** JSON_ESTEVOLUEEN attributes. */
    protected static String JSON_ESTEVOLUEEN = "estEvolueEn";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeDePokemonEvolution REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeDePokemonEvolutionContract.COL_ID,
            TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID,
            TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeDePokemonEvolutionWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeDePokemonEvolutionWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonEvolutionWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonEvolutionWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonEvolutionWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeDePokemonEvolutions in the given list. Uses the route : TypeDePokemonEvolution.
     * @param typeDePokemonEvolutions : The list in which the TypeDePokemonEvolutions will be returned
     * @return The number of TypeDePokemonEvolutions returned
     */
    public int getAll(List<TypeDePokemonEvolution> typeDePokemonEvolutions) {
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
                result = extractItems(json, "TypeDePokemonEvolutions", typeDePokemonEvolutions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonEvolutions = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typedepokemonevolution";
    }

    /**
     * Retrieve one TypeDePokemonEvolution. Uses the route : TypeDePokemonEvolution/%id%.
     * @param typeDePokemonEvolution : The TypeDePokemonEvolution to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeDePokemonEvolution typeDePokemonEvolution) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemonEvolution.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, typeDePokemonEvolution)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonEvolution = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeDePokemonEvolution. Uses the route : TypeDePokemonEvolution/%id%.
     * @param typeDePokemonEvolution : The TypeDePokemonEvolution to retrieve (set the  ID)
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
     * Update a TypeDePokemonEvolution. Uses the route : TypeDePokemonEvolution/%id%.
     * @param typeDePokemonEvolution : The TypeDePokemonEvolution to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeDePokemonEvolution typeDePokemonEvolution) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemonEvolution.getId(),
                        REST_FORMAT),
                    itemToJson(typeDePokemonEvolution));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeDePokemonEvolution);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeDePokemonEvolution. Uses the route : TypeDePokemonEvolution/%id%.
     * @param typeDePokemonEvolution : The TypeDePokemonEvolution to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeDePokemonEvolution typeDePokemonEvolution) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemonEvolution.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the TypeDePokemonEvolutions associated with a TypeDePokemon. Uses the route : typedepokemon/%TypeDePokemon_id%/typedepokemonevolution.
     * @param typeDePokemonEvolutions : The list in which the TypeDePokemonEvolutions will be returned
     * @param typedepokemon : The associated typedepokemon
     * @return The number of TypeDePokemonEvolutions returned
     */
    public int getByEvolueEn(List<TypeDePokemonEvolution> typeDePokemonEvolutions, TypeDePokemon typeDePokemon) {
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
                result = this.extractItems(json, "TypeDePokemonEvolutions", typeDePokemonEvolutions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonEvolutions = null;
            }
        }

        return result;
    }

    /**
     * Get the TypeDePokemonEvolutions associated with a TypeDePokemon. Uses the route : typedepokemon/%TypeDePokemon_id%/typedepokemonevolution.
     * @param typeDePokemonEvolutions : The list in which the TypeDePokemonEvolutions will be returned
     * @param typedepokemon : The associated typedepokemon
     * @return The number of TypeDePokemonEvolutions returned
     */
    public int getByEstEvolueEn(List<TypeDePokemonEvolution> typeDePokemonEvolutions, TypeDePokemon typeDePokemon) {
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
                result = this.extractItems(json, "TypeDePokemonEvolutions", typeDePokemonEvolutions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonEvolutions = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid TypeDePokemonEvolution Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeDePokemonEvolution from a JSONObject describing a TypeDePokemonEvolution.
     * @param json The JSONObject describing the TypeDePokemonEvolution
     * @param typeDePokemonEvolution The returned TypeDePokemonEvolution
     * @return true if a TypeDePokemonEvolution was found. false if not
     */
    public boolean extract(JSONObject json, TypeDePokemonEvolution typeDePokemonEvolution) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID)) {
                    typeDePokemonEvolution.setId(
                            json.getInt(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN)
                        && !json.isNull(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN)) {

                    try {
                        TypeDePokemonWebServiceClientAdapter evolueEnAdapter =
                                new TypeDePokemonWebServiceClientAdapter(this.context);
                        TypeDePokemon evolueEn =
                                new TypeDePokemon();

                        if (evolueEnAdapter.extract(
                                json.optJSONObject(
                                        TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN),
                                        evolueEn)) {
                            typeDePokemonEvolution.setEvolueEn(evolueEn);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeDePokemon data");
                    }
                }

                if (json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN)
                        && !json.isNull(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN)) {

                    try {
                        TypeDePokemonWebServiceClientAdapter estEvolueEnAdapter =
                                new TypeDePokemonWebServiceClientAdapter(this.context);
                        TypeDePokemon estEvolueEn =
                                new TypeDePokemon();

                        if (estEvolueEnAdapter.extract(
                                json.optJSONObject(
                                        TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN),
                                        estEvolueEn)) {
                            typeDePokemonEvolution.setEstEvolueEn(estEvolueEn);
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
        String id = json.optString(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN)) {
                    JSONObject evolueEnJson = json.getJSONObject(
                            TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN);
                    row[1] = evolueEnJson.getString(
                            TypeDePokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN)) {
                    JSONObject estEvolueEnJson = json.getJSONObject(
                            TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN);
                    row[2] = estEvolueEnJson.getString(
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
     * Convert a TypeDePokemonEvolution to a JSONObject.
     * @param typeDePokemonEvolution The TypeDePokemonEvolution to convert
     * @return The converted TypeDePokemonEvolution
     */
    public JSONObject itemToJson(TypeDePokemonEvolution typeDePokemonEvolution) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID,
                    typeDePokemonEvolution.getId());

            if (typeDePokemonEvolution.getEvolueEn() != null) {
                TypeDePokemonWebServiceClientAdapter evolueEnAdapter =
                        new TypeDePokemonWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN,
                        evolueEnAdapter.itemIdToJson(typeDePokemonEvolution.getEvolueEn()));
            }

            if (typeDePokemonEvolution.getEstEvolueEn() != null) {
                TypeDePokemonWebServiceClientAdapter estEvolueEnAdapter =
                        new TypeDePokemonWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN,
                        estEvolueEnAdapter.itemIdToJson(typeDePokemonEvolution.getEstEvolueEn()));
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
    public JSONObject itemIdToJson(TypeDePokemonEvolution item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeDePokemonEvolution to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ID,
                    values.get(TypeDePokemonEvolutionContract.COL_ID));
            TypeDePokemonWebServiceClientAdapter evolueEnAdapter =
                    new TypeDePokemonWebServiceClientAdapter(this.context);

            params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_EVOLUEEN,
                    evolueEnAdapter.contentValuesToJson(values));
            TypeDePokemonWebServiceClientAdapter estEvolueEnAdapter =
                    new TypeDePokemonWebServiceClientAdapter(this.context);

            params.put(TypeDePokemonEvolutionWebServiceClientAdapter.JSON_ESTEVOLUEEN,
                    estEvolueEnAdapter.contentValuesToJson(values));
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
            List<TypeDePokemonEvolution> items,
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
                TypeDePokemonEvolution item = new TypeDePokemonEvolution();
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
            List<TypeDePokemonEvolution> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
