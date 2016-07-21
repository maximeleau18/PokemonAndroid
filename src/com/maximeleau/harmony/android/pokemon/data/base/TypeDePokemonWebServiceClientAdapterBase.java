/**************************************************************************
 * TypeDePokemonWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeDePokemonWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeDePokemon> {
    /** TypeDePokemonWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeDePokemonWSClientAdapter";

    /** JSON Object TypeDePokemon pattern. */
    protected static String JSON_OBJECT_TYPEDEPOKEMON = "TypeDePokemon";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_ATTAQUE attributes. */
    protected static String JSON_ATTAQUE = "attaque";
    /** JSON_DEFENSE attributes. */
    protected static String JSON_DEFENSE = "defense";
    /** JSON_PV attributes. */
    protected static String JSON_PV = "pv";
    /** JSON_NUMPOKEDEX attributes. */
    protected static String JSON_NUMPOKEDEX = "numPokedex";
    /** JSON_URLIMAGE attributes. */
    protected static String JSON_URLIMAGE = "urlImage";
    /** JSON_POKEMONS attributes. */
    protected static String JSON_POKEMONS = "pokemons";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeDePokemon REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeDePokemonContract.COL_ID,
            TypeDePokemonContract.COL_NOM,
            TypeDePokemonContract.COL_ATTAQUE,
            TypeDePokemonContract.COL_DEFENSE,
            TypeDePokemonContract.COL_PV,
            TypeDePokemonContract.COL_NUMPOKEDEX,
            TypeDePokemonContract.COL_URLIMAGE
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeDePokemonWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeDePokemonWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeDePokemons in the given list. Uses the route : TypeDePokemon.
     * @param typeDePokemons : The list in which the TypeDePokemons will be returned
     * @return The number of TypeDePokemons returned
     */
    public int getAll(List<TypeDePokemon> typeDePokemons) {
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
                result = extractItems(json, "TypeDePokemons", typeDePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemons = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typedepokemon";
    }

    /**
     * Retrieve one TypeDePokemon. Uses the route : TypeDePokemon/%id%.
     * @param typeDePokemon : The TypeDePokemon to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeDePokemon typeDePokemon) {
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
                if (extract(json, typeDePokemon)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemon = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeDePokemon. Uses the route : TypeDePokemon/%id%.
     * @param typeDePokemon : The TypeDePokemon to retrieve (set the  ID)
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
     * Update a TypeDePokemon. Uses the route : TypeDePokemon/%id%.
     * @param typeDePokemon : The TypeDePokemon to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeDePokemon typeDePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemon.getId(),
                        REST_FORMAT),
                    itemToJson(typeDePokemon));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeDePokemon);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeDePokemon. Uses the route : TypeDePokemon/%id%.
     * @param typeDePokemon : The TypeDePokemon to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeDePokemon typeDePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }



    /**
     * Tests if the json is a valid TypeDePokemon Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeDePokemonWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeDePokemon from a JSONObject describing a TypeDePokemon.
     * @param json The JSONObject describing the TypeDePokemon
     * @param typeDePokemon The returned TypeDePokemon
     * @return true if a TypeDePokemon was found. false if not
     */
    public boolean extract(JSONObject json, TypeDePokemon typeDePokemon) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_ID)) {
                    typeDePokemon.setId(
                            json.getInt(TypeDePokemonWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_NOM)) {
                    typeDePokemon.setNom(
                            json.getString(TypeDePokemonWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE)) {
                    typeDePokemon.setAttaque(
                            json.getInt(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE)) {
                    typeDePokemon.setDefense(
                            json.getInt(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_PV)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_PV)) {
                    typeDePokemon.setPv(
                            json.getInt(TypeDePokemonWebServiceClientAdapter.JSON_PV));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX)) {
                    typeDePokemon.setNumPokedex(
                            json.getInt(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE)) {
                    typeDePokemon.setUrlImage(
                            json.getString(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE));
                }

                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_POKEMONS)
                        && !json.isNull(TypeDePokemonWebServiceClientAdapter.JSON_POKEMONS)) {
                    ArrayList<Pokemon> pokemons =
                            new ArrayList<Pokemon>();
                    PokemonWebServiceClientAdapter pokemonsAdapter =
                            new PokemonWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(TypeDePokemonWebServiceClientAdapter.JSON_POKEMONS);
                        pokemonsAdapter.extractItems(
                                json, TypeDePokemonWebServiceClientAdapter.JSON_POKEMONS,
                                pokemons);
                        typeDePokemon.setPokemons(pokemons);
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
        String id = json.optString(TypeDePokemonWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[7];
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE)) {
                    row[2] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE);
                }
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE)) {
                    row[3] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE);
                }
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_PV)) {
                    row[4] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_PV);
                }
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX)) {
                    row[5] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX);
                }
                if (json.has(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE)) {
                    row[6] = json.getString(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE);
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
     * Convert a TypeDePokemon to a JSONObject.
     * @param typeDePokemon The TypeDePokemon to convert
     * @return The converted TypeDePokemon
     */
    public JSONObject itemToJson(TypeDePokemon typeDePokemon) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_ID,
                    typeDePokemon.getId());
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_NOM,
                    typeDePokemon.getNom());
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE,
                    typeDePokemon.getAttaque());
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE,
                    typeDePokemon.getDefense());
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_PV,
                    typeDePokemon.getPv());
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX,
                    typeDePokemon.getNumPokedex());
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE,
                    typeDePokemon.getUrlImage());

            if (typeDePokemon.getPokemons() != null) {
                PokemonWebServiceClientAdapter pokemonsAdapter =
                        new PokemonWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonWebServiceClientAdapter.JSON_POKEMONS,
                        pokemonsAdapter.itemsIdToJson(typeDePokemon.getPokemons()));
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
    public JSONObject itemIdToJson(TypeDePokemon item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeDePokemon to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_ID,
                    values.get(TypeDePokemonContract.COL_ID));
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_NOM,
                    values.get(TypeDePokemonContract.COL_NOM));
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_ATTAQUE,
                    values.get(TypeDePokemonContract.COL_ATTAQUE));
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_DEFENSE,
                    values.get(TypeDePokemonContract.COL_DEFENSE));
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_PV,
                    values.get(TypeDePokemonContract.COL_PV));
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_NUMPOKEDEX,
                    values.get(TypeDePokemonContract.COL_NUMPOKEDEX));
            params.put(TypeDePokemonWebServiceClientAdapter.JSON_URLIMAGE,
                    values.get(TypeDePokemonContract.COL_URLIMAGE));
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
            List<TypeDePokemon> items,
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
                TypeDePokemon item = new TypeDePokemon();
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
            List<TypeDePokemon> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
